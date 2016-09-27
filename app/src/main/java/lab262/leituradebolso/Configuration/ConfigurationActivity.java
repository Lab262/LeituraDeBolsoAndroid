package lab262.leituradebolso.Configuration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.Calendar;

import lab262.leituradebolso.Extensions.ApplicationState;
import lab262.leituradebolso.R;


public class ConfigurationActivity extends AppCompatActivity implements Switch.OnCheckedChangeListener, DiscreteSeekBar.OnProgressChangeListener,
TimePickerDialog.OnTimeSetListener, View.OnClickListener{

    private DiscreteSeekBar discreteSeekBar;
    private Switch notificationSwitch, noturneModeSwitch;
    private static final int CONSTANT_PROGRESS_BAR = 2;
    private TextView receiveReadingTextView, mensageTextView, heightTextView, hourReadingTextView;
    private LinearLayout linearLayout;
    private Calendar currentCalendar;
    private LinearLayout layoutHourReceive;
    private View viewLayoutHourReceive;
    private LinearLayout.LayoutParams layoutParamsHourReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        getViews();
        setPropertyView();
    }

    private void getViews(){
        discreteSeekBar = (DiscreteSeekBar) findViewById(R.id.textSizeSeekBar);
        notificationSwitch = (Switch) findViewById(R.id.notificationSwitch);
        noturneModeSwitch = (Switch) findViewById(R.id.noturneModeSwitch);
        receiveReadingTextView = (TextView) findViewById(R.id.receiveReadingTextView);
        mensageTextView = (TextView) findViewById(R.id.mensageTextView);
        heightTextView = (TextView) findViewById(R.id.heightTextView);
        linearLayout = (LinearLayout) findViewById(R.id.activity_configuration);
        hourReadingTextView = (TextView) findViewById(R.id.hourReadingTextView);
        layoutHourReceive = (LinearLayout) findViewById(R.id.layoutHourReceive);
        viewLayoutHourReceive = findViewById(R.id.viewLayoutHourReceive);
        layoutParamsHourReceive = (LinearLayout.LayoutParams) layoutHourReceive.getLayoutParams();
    }

    private void setPropertyView(){
        discreteSeekBar.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                return value * CONSTANT_PROGRESS_BAR;
            }
        });

        setTitle(R.string.title_activity_configuration);
        notificationSwitch.setChecked(ApplicationState.sharedState().getReciveNotification());
        noturneModeSwitch.setChecked(ApplicationState.sharedState().getNoturneMode());

        int progressAditionalBar = (ApplicationState.sharedState().getTextSize()/CONSTANT_PROGRESS_BAR);
        discreteSeekBar.setProgress(progressAditionalBar);

        notificationSwitch.setOnCheckedChangeListener(this);
        noturneModeSwitch.setOnCheckedChangeListener(this);
        discreteSeekBar.setOnProgressChangeListener(this);

        //Configure Noturne Mode
        if (ApplicationState.sharedState().getNoturneMode()){
            setNoturneMode();
        }else {
            resetNoturneMode();
        }

        currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(ApplicationState.sharedState().getHourNotification());

        hourReadingTextView.setText(getStringHourNotification());
        hourReadingTextView.setOnClickListener(this);

        //Configure Layout Hour Reading
        if (notificationSwitch.isChecked()==false){
            hideLayoutHourReceive();
        }
    }

    private void setNoturneMode(){
        receiveReadingTextView.setTextColor(Color.WHITE);
        mensageTextView.setTextColor(Color.WHITE);
        heightTextView.setTextColor(Color.WHITE);
        notificationSwitch.setTextColor(Color.WHITE);
        noturneModeSwitch.setTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorNoturne,null));
        }else {
            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorNoturne));
        }
    }

    private void resetNoturneMode(){
        receiveReadingTextView.setTextColor(Color.BLACK);
        mensageTextView.setTextColor(Color.BLACK);
        heightTextView.setTextColor(Color.BLACK);
        notificationSwitch.setTextColor(Color.BLACK);
        noturneModeSwitch.setTextColor(Color.BLACK);
        linearLayout.setBackgroundColor(Color.WHITE);
    }

    private String getStringHourNotification(){


        int isAM = currentCalendar.get(Calendar.AM_PM);
        String stringFormatTime = new String();

        int minutes = currentCalendar.get(Calendar.MINUTE);
        String stringMinutes = String.valueOf(minutes);

        int hours = currentCalendar.get(Calendar.HOUR);
        String stringHours = String.valueOf(hours);

        //Validation format hour
        switch (isAM){
            case 1:
                stringFormatTime = " AM";
                //Validation Hour
                if (hours==0){
                    stringHours = "12";
                }
                break;
            case 0:
                stringFormatTime = " PM";
                break;
        }

        //Validation minutes
        if (minutes<10){
            stringMinutes = "0" + stringMinutes;
        }

        return  stringHours + ":" + stringMinutes + stringFormatTime;
    }

    private void hideLayoutHourReceive(){
        linearLayout.removeView(layoutHourReceive);
        linearLayout.removeView(viewLayoutHourReceive);
    }

    private void showLayoutHourReceive(){
        linearLayout.addView(layoutHourReceive,2);
        linearLayout.addView(viewLayoutHourReceive,3);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        compoundButton.setChecked(b);
        switch (compoundButton.getId()){
            case R.id.notificationSwitch:
                ApplicationState.sharedState().setReciveNotification(b);
                //Configure Layout Hour Reading
                if (notificationSwitch.isChecked()){
                    showLayoutHourReceive();
                }else {
                    hideLayoutHourReceive();
                }
                break;
            case R.id.noturneModeSwitch:
                ApplicationState.sharedState().setNoturneMode(b);
                //Configure Noturne Mode
                if (b==true){
                    setNoturneMode();
                }else {
                    resetNoturneMode();
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
        ApplicationState.sharedState().setTextSize(seekBar.getProgress()*CONSTANT_PROGRESS_BAR);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        currentCalendar = calendar;
        ApplicationState.sharedState().setHourNotification(calendar.getTime());
        hourReadingTextView.setText(getStringHourNotification());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hourReadingTextView:
                int hours = currentCalendar.get(Calendar.HOUR);
                int minutes = currentCalendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,R.style.TimePickerDialogTheme, this, hours,minutes, false);
                timePickerDialog.show();
                break;
        }
    }
}
