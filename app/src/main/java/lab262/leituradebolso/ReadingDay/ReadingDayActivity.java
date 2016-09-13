package lab262.leituradebolso.ReadingDay;

import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingHistory.ReadingHistoryActivity;

public class ReadingDayActivity extends AppCompatActivity implements View.OnClickListener {

    private Typeface typeFace;
    private ImageButton likeButton, historyButton, configurationButton;
    private Boolean likeReading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureActionBar();
        setContentView(R.layout.activity_reading_day);
        getInstanceViews();
        setPropertyView();
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
        }
    }
}
