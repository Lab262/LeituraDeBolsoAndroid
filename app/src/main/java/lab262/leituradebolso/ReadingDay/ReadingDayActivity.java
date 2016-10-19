package lab262.leituradebolso.ReadingDay;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import io.realm.RealmResults;
import lab262.leituradebolso.Configuration.ConfigurationActivity;
import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Extensions.FeedbackManager;
import lab262.leituradebolso.Extensions.NotificationsManager;
import lab262.leituradebolso.Model.EmojiModel;
import lab262.leituradebolso.Model.ReadingModel;
import lab262.leituradebolso.Model.UserModel;
import lab262.leituradebolso.Model.UserReadingModel;
import lab262.leituradebolso.Persistence.DBManager;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingHistory.ReadingHistoryActivity;
import lab262.leituradebolso.Requests.ReadingRequest;
import lab262.leituradebolso.Requests.Requester;
import lab262.leituradebolso.Requests.UserReadingRequest;

public class ReadingDayActivity extends AppCompatActivity implements View.OnClickListener {

    private Typeface typeFace;
    private ImageButton likeButton, historyButton, configurationButton, shareButton;
    private Boolean likeReading;
    private TextView titleTextView, emojiTextView, timeTextView, readingTextView, authorTextView;
    private ReadingModel currentReadingModel;
    private UserReadingModel currentUserReadingModel;
    private ScrollView layoutReadingDay;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureActionBar();
        setContentView(R.layout.activity_reading_day);
        getInstanceViews();
        setPropertyView();
        setNotifications();
        Bundle bundleExtras = getIntent().getExtras();
        if (bundleExtras!=null){
            String idReading = bundleExtras.getString("modelreading");
            RealmResults<ReadingModel> readingModelRealmResults = (RealmResults<ReadingModel>)
                    DBManager.getAllByParameter(ReadingModel.class,"idReading",idReading);
            currentReadingModel = readingModelRealmResults.first();
            RealmResults<UserReadingModel> userReadingModelRealmResults = (RealmResults<UserReadingModel>)
                    DBManager.getAllByParameter(UserReadingModel.class,"idReading",idReading);
            currentUserReadingModel = userReadingModelRealmResults.first();
            setReading();
            hideHistoryButton();
        }else {
            if (differenceDaysEnter()==0){
                setLastReadingDay();
            }else {
                getAllUserReadings();
                NotificationsManager.cancelAllNotifications(this);
                NotificationsManager.setReadingDaysNotifications(this,DBManager.getCachedUser().getHourNotification().getTime());
            }
        }
    }

    private void setLastReadingDay(){
        RealmResults<ReadingModel> realmResults = (RealmResults<ReadingModel>)
                DBManager.getAll(ReadingModel.class);
        currentReadingModel = realmResults.last();
        RealmResults<UserReadingModel> userReadingModelRealmResults = (RealmResults<UserReadingModel>)
                DBManager.getAllByParameter(UserReadingModel.class,"idReading",currentReadingModel.idReading);
        currentUserReadingModel = userReadingModelRealmResults.first();
        setReading();
    }

    private void configureActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled (false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
    }

    private void getInstanceViews(){
        typeFace =Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");
        likeButton = (ImageButton) findViewById(R.id.likeButton);
        shareButton = (ImageButton) findViewById(R.id.shareButton);

        titleTextView = (TextView) findViewById(R.id.titleTextView);
        emojiTextView = (TextView) findViewById(R.id.emojiTextView);
        timeTextView = (TextView) findViewById(R.id.timeTextView);
        readingTextView = (TextView) findViewById(R.id.readingTextView);
        authorTextView = (TextView) findViewById(R.id.authorTextView);

        layoutReadingDay = (ScrollView) findViewById(R.id.layoutReadingDay);
    }

    private void setPropertyView(){
        TextView textView = (TextView) findViewById(R.id.titleActionBarTextView);
        textView.setTypeface(typeFace);
        textView.setText(R.string.title_activity_reading);

        historyButton = (ImageButton) findViewById(R.id.leftButton);
        configurationButton = (ImageButton) findViewById(R.id.rightButton);

        configurationButton.setBackgroundResource(R.drawable.ic_configuration);

        likeButton.setOnClickListener(this);
        historyButton.setOnClickListener(this);
        configurationButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);
    }

    private void setNotifications(){
        NotificationsManager.setReadingDaysNotifications(this,DBManager.getCachedUser().getHourNotification().getTime());
    }

    private void hideHistoryButton(){
        historyButton.setVisibility(View.INVISIBLE);
    }

    private int differenceDaysEnter(){
        UserModel user = DBManager.getCachedUser();
        int differenceDays = user.getDifferenceBetweenDateNow();
        return differenceDays;
    }

    private void getAllUserReadings(){
        UserModel user = DBManager.getCachedUser();
        UserReadingRequest.getAllUserReadings(user,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = (JSONArray) response.get(Requester.keyData);
                    if (jsonArray.length()>0){
                        ArrayList<String> arrayIDsReadings = new ArrayList<String>();
                        for (int i=0; i<jsonArray.length(); i++){

                            JSONObject userReading = jsonArray.getJSONObject(i).getJSONObject(Requester.keyAttributes);

                            UserReadingModel userReadingModel = new UserReadingModel(userReading);
                            arrayIDsReadings.add(userReadingModel.getIdReading());
                            DBManager.saveObject(userReadingModel);
                        }
                        getDifferentReadings(arrayIDsReadings);
                    }else {
                        getReadingDay(differenceDaysEnter());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                FeedbackManager.feedbackErrorResponse(getApplicationContext(),null,statusCode,errorResponse);
            }

        });
    }

    private void getDifferentReadings(ArrayList<String> arrayIDsReadings){

        //Get all ids locally
        ReadingModel [] allReadingsModels = ReadingModel.getAllReadingData();
        ArrayList<String> allReadingsModelsIDs = new ArrayList<String>();
        for (ReadingModel readingModel : allReadingsModels){
            allReadingsModelsIDs.add(readingModel.idReading);
        }

        //Difference betwenn arrays
        ArrayList<String> differentReadingsIDs = arrayIDsReadings;
        differentReadingsIDs.removeAll(allReadingsModelsIDs);

        //Get readings
        if (differentReadingsIDs.size()>0){
            getReadings(differentReadingsIDs);
        }else {
            getReadingDay(differenceDaysEnter());
        }
    }

    private void getReadings(ArrayList<String> differentReadingsIDs){
        final UserModel user = DBManager.getCachedUser();
        ReadingRequest.getReadings(user.getToken(), differentReadingsIDs,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = (JSONArray) response.get(Requester.keyData);

                    for (int i=0; i<jsonArray.length(); i++){

                        //Create Reading in realm
                        JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject(Requester.keyAttributes);
                        ReadingModel readingModel = new ReadingModel(jsonObject);
                        DBManager.saveObject(readingModel);
                    }
                    getReadingDay(differenceDaysEnter());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                FeedbackManager.feedbackErrorResponse(getApplicationContext(),null,statusCode,errorResponse);
            }

        });
    }

    private void getReadingDay(int numberDaysOut){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_progress_dialog));
        final UserModel user = DBManager.getCachedUser();
        UserReadingRequest.getUserReadingsOfTheWeek(user,numberDaysOut,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = (JSONArray) response.get(Requester.keyData);

                    if (jsonArray.length()>0){
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject(Requester.keyAttributes);
                            ReadingModel readingModel = new ReadingModel(jsonObject);
                            UserReadingModel userReadingModel = new UserReadingModel(readingModel.idReading);
                            if (currentReadingModel==null && currentUserReadingModel==null){
                                userReadingModel.setIsRead(true);
                                currentReadingModel = readingModel;
                                currentUserReadingModel = userReadingModel;
                            }
                            DBManager.saveObject(readingModel);
                            DBManager.saveObject(userReadingModel);
                            createUserReading(userReadingModel);
                        }
                        setReading();
                    }else {
                        setLastReadingDay();
                    }
                    user.updateLastSessionTimeInterval(Calendar.getInstance().getTime().getTime());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                FeedbackManager.feedbackErrorResponse(getApplicationContext(),progressDialog,statusCode,errorResponse);
            }

        });
    }

    private void createUserReading(UserReadingModel userReadingModel){
        UserReadingRequest.createUserReading(userReadingModel,DBManager.getCachedUser().getId(),new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                FeedbackManager.feedbackErrorResponse(getApplicationContext(),null,statusCode,errorResponse);
            }

        });

    }

    private void updateUserReading(){
        UserReadingRequest.updateUserReadings(DBManager.getCachedUser(),currentUserReadingModel,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                FeedbackManager.feedbackErrorResponse(getApplicationContext(),null,statusCode,errorResponse);
            }
        });
    }

    private void setReading(){
        titleTextView.setText(currentReadingModel.title);
        timeTextView.setText(currentReadingModel.duration);
        readingTextView.setText(currentReadingModel.textReading);
        authorTextView.setText(currentReadingModel.author);

        StringBuilder allEmojis = new StringBuilder();
        for(EmojiModel emoji : currentReadingModel.emojis) {
            if(allEmojis.length() > 0) {
                allEmojis.append(" "); // some divider between the different texts
            }
            allEmojis.append(emoji.getEmojiByUnicode());
        }
        emojiTextView.setText(allEmojis.toString());

        if (!currentUserReadingModel.getIsRead()){
            currentUserReadingModel.updateIsRead(true);
            updateUserReading();
        }

        likeReading = currentUserReadingModel.getFavorite();
        if (likeReading){
            likeButton.setBackgroundResource(R.drawable.liked_circle);
        }
    }

    private void setNoturneMode(){
        titleTextView.setTextColor(Color.WHITE);
        readingTextView.setTextColor(Color.WHITE);
        authorTextView.setTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layoutReadingDay.setBackgroundColor(getResources().getColor(R.color.colorNoturne,null));
        }else {
            layoutReadingDay.setBackgroundColor(getResources().getColor(R.color.colorNoturne));
        }
    }

    private void resetNoturneMode(){
        titleTextView.setTextColor(Color.BLACK);
        readingTextView.setTextColor(Color.BLACK);
        authorTextView.setTextColor(Color.BLACK);
        layoutReadingDay.setBackgroundColor(Color.WHITE);
    }

    private void shareReading(){
        Intent intentShare =new Intent(android.content.Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(android.content.Intent.EXTRA_SUBJECT,"Leitura de Bolso");
        intentShare.putExtra(android.content.Intent.EXTRA_TEXT, currentReadingModel.textReading);
        startActivity(Intent.createChooser(intentShare,getString(R.string.placeholder_share_title)));
    }

    private void likeReading(){
        if (likeReading){
            likeReading = false;
            likeButton.setBackgroundResource(R.drawable.like_circle);
            currentUserReadingModel.updateIsFavorite(false);
        }else {
            likeReading = true;
            likeButton.setBackgroundResource(R.drawable.liked_circle);
            currentUserReadingModel.updateIsFavorite(true);
        }
        updateUserReading();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.likeButton:
                likeReading();
                break;

            //History Button
            case R.id.leftButton:
                ActivityManager.changeActivity(ReadingDayActivity.this, ReadingHistoryActivity.class);
                break;

            //Configuration Button
            case R.id.rightButton:
                ActivityManager.changeActivity(ReadingDayActivity.this, ConfigurationActivity.class);
                break;

            case R.id.shareButton:
                shareReading();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Set text size
        UserModel user = DBManager.getCachedUser();
        readingTextView.setTextSize(user.getTextSize());

        if (haveNotReadReading()){
            historyButton.setBackgroundResource(R.drawable.ic_alert_history_reading);
        }else {
            historyButton.setBackgroundResource(R.drawable.ic_history_reading);
        }

        //Configure Noturne Mode
        if (user.getNoturneMode()){
            setNoturneMode();
        }else {
            resetNoturneMode();
        }
    }

    private Boolean haveNotReadReading(){
        RealmResults<UserReadingModel> realmResults = (RealmResults<UserReadingModel>)
                DBManager.getAllByParameter(UserReadingModel.class,"isRead",false);
        if (realmResults.size()>0){
            return true;
        }else {
            return false;
        }
    }
}
