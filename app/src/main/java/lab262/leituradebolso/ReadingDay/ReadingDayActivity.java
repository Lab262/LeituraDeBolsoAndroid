package lab262.leituradebolso.ReadingDay;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import lab262.leituradebolso.R;

public class ReadingDayActivity extends AppCompatActivity {

    private Typeface typeFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled (false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);



        setContentView(R.layout.activity_reading_day);
        getInstanceViews();
        setPropertyView();
    }

    private void getInstanceViews(){
        typeFace =Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");
    }

    private void setPropertyView(){
        TextView textView = (TextView) findViewById(R.id.titleActionBarTextView);
        textView.setTypeface(typeFace);
        textView.setText(R.string.title_activity_reading);

        ImageButton historyButton = (ImageButton) findViewById(R.id.leftButton);
        ImageButton configurationButton = (ImageButton) findViewById(R.id.rightButton);

        historyButton.setBackgroundResource(R.drawable.ic_history_reading);
        configurationButton.setBackgroundResource(R.drawable.ic_configuration);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.reading_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
