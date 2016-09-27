package lab262.leituradebolso.ReadingDay;

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

import java.util.ArrayList;

import lab262.leituradebolso.Configuration.ConfigurationActivity;
import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Extensions.ApplicationState;
import lab262.leituradebolso.Model.ReadingModel;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingHistory.ReadingHistoryActivity;

public class ReadingDayActivity extends AppCompatActivity implements View.OnClickListener {

    private Typeface typeFace;
    private ImageButton likeButton, historyButton, configurationButton;
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
        Bundle bundleExtras = getIntent().getExtras();
        if (bundleExtras!=null){
            currentReadingModel = (ReadingModel) bundleExtras.get("modelreading");
            setReading();
            hideHistoryButton();
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

        ArrayList<String> emojis = new ArrayList<String>();
        emojis.add(ReadingModel.getEmijoByUnicode(0x1F601));
        emojis.add(ReadingModel.getEmijoByUnicode(0x1F602));
        emojis.add(ReadingModel.getEmijoByUnicode(0x1F603));

        StringBuilder allEmojis = new StringBuilder();
        for(String emoji : emojis) {
            if(allEmojis.length() > 0) {
                allEmojis.append(" "); // some divider between the different texts
            }
            allEmojis.append(emoji);
        }
        emojiTextView.setText(allEmojis.toString());
    }

    private void hideHistoryButton(){
        historyButton.setVisibility(View.INVISIBLE);
    }

    private void setReading(){
        titleTextView.setText(currentReadingModel.title);
        timeTextView.setText(currentReadingModel.duration);
        readingTextView.setText(currentReadingModel.textReading);
        authorTextView.setText(currentReadingModel.author);

        StringBuilder allEmojis = new StringBuilder();
        for(String emoji : currentReadingModel.emojis) {
            if(allEmojis.length() > 0) {
                allEmojis.append(" "); // some divider between the different texts
            }
            allEmojis.append(emoji);
        }
        emojiTextView.setText(allEmojis.toString());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.likeButton:
                if (likeReading){
                    likeReading = false;
                    likeButton.setBackgroundResource(R.drawable.like_circle);
                }else {
                    likeReading = true;
                    likeButton.setBackgroundResource(R.drawable.liked_circle);
                }
                break;

            //History Button
            case R.id.leftButton:
                ActivityManager.changeActivity(ReadingDayActivity.this, ReadingHistoryActivity.class);
                break;

            //Configuration Button
            case R.id.rightButton:
                ActivityManager.changeActivity(ReadingDayActivity.this, ConfigurationActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Set text size
        readingTextView.setTextSize(ApplicationState.sharedState().getTextSize());

        //Configure Noturne Mode
        if (ApplicationState.sharedState().getNoturneMode()){
            setNoturneMode();
        }else {
            resetNoturneMode();
        }
    }
}
