package lab262.leituradebolso.ReadingDay;

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

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import io.realm.RealmResults;
import lab262.leituradebolso.Configuration.ConfigurationActivity;
import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Extensions.NotificationsManager;
import lab262.leituradebolso.Model.EmojiModel;
import lab262.leituradebolso.Model.ReadingModel;
import lab262.leituradebolso.Model.UserModel;
import lab262.leituradebolso.Persistence.DBManager;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingHistory.ReadingHistoryActivity;
import lab262.leituradebolso.Requests.ReadingRequest;
import lab262.leituradebolso.Requests.Requester;

public class ReadingDayActivity extends AppCompatActivity implements View.OnClickListener {

    private Typeface typeFace;
    private ImageButton likeButton, historyButton, configurationButton, shareButton;
    private Boolean likeReading;
    private TextView titleTextView, emojiTextView, timeTextView, readingTextView, authorTextView;
    private ReadingModel currentReadingModel;
    private ScrollView layoutReadingDay;


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
            setReading();
            hideHistoryButton();
        }else {
            if (verifiyIfFirstTimeDay()){
                getReadingDay();
                NotificationsManager.cancelAllNotifications(this);
                NotificationsManager.setReadingDaysNotifications(this,DBManager.getCachedUser().getHourNotification().getTime());
            }else {
                RealmResults<ReadingModel> realmResults = (RealmResults<ReadingModel>)
                        DBManager.getAllByParameter(ReadingModel.class,"idReading",DBManager.getCachedUser().getIdReadingDay());
                currentReadingModel = realmResults.first();
                setReading();
            }
        }
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

        historyButton.setBackgroundResource(R.drawable.ic_history_reading);
        configurationButton.setBackgroundResource(R.drawable.ic_configuration);

        likeReading = false;

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

    private Boolean verifiyIfFirstTimeDay(){

        UserModel user = DBManager.getCachedUser();
        int differenceDays = user.getDifferenceBetweenDateNow();
        if (differenceDays<0 || differenceDays==1){
            return true;
        }else {
            //TODO: Verificar quantos dias de diferenca
            return false;
        }


    }

    private void getReadingDay(){
        final UserModel user = DBManager.getCachedUser();
        ReadingRequest.getReadings(user.getToken(),new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = (JSONArray) response.get("data");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject(Requester.keyAttributes);
                        ReadingModel readingModel = new ReadingModel(jsonObject);
                        readingModel.isLiked = false;
                        readingModel.isRead = false;
                        if (currentReadingModel==null){
                            readingModel.isRead = true;
                            currentReadingModel = readingModel;
                        }
                        DBManager.saveObject(readingModel);
                    }
                    setReading();
                    user.updateLastSessionTimeInterval(Calendar.getInstance().getTime().getTime());
                    user.updateIdReadingDay(currentReadingModel.idReading);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
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
            allEmojis.append(emoji.getEmijoByUnicode());
        }
        emojiTextView.setText(allEmojis.toString());

        if (!currentReadingModel.isRead){
            //TODO: Update WS
            currentReadingModel.updateIsRead(true);
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
        }else {
            likeReading = true;
            likeButton.setBackgroundResource(R.drawable.liked_circle);
        }
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
    protected void onResume() {
        super.onResume();
        //Set text size
        UserModel user = DBManager.getCachedUser();
        readingTextView.setTextSize(user.getTextSize());

        //Configure Noturne Mode
        if (user.getNoturneMode()){
            setNoturneMode();
        }else {
            resetNoturneMode();
        }
    }
}
