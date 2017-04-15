package lab262.leituradebolso.Configuration;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.Calendar;

import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Extensions.FeedbackManager;
import lab262.leituradebolso.Extensions.LayoutManager;
import lab262.leituradebolso.Extensions.NotificationsManager;
import lab262.leituradebolso.Login.InitialActivity;
import lab262.leituradebolso.Model.UserModel;
import lab262.leituradebolso.Persistence.DBManager;
import lab262.leituradebolso.R;


public class ConfigurationActivity extends AppCompatActivity implements Switch.OnCheckedChangeListener, DiscreteSeekBar.OnProgressChangeListener,
TimePickerDialog.OnTimeSetListener, View.OnClickListener{

    private DiscreteSeekBar discreteSeekBar;
    private Switch notificationSwitch, noturneModeSwitch;
    private static final int CONSTANT_PROGRESS_BAR = 2;
    private static final int CONSTANTE_PROGRESS_BAR_MIN = 7;
    private static final int CONSTANTE_PROGRESS_BAR_MAX = 14;
    private TextView receiveReadingTextView, mensageTextView, heightTextView, hourReadingTextView;
    private LinearLayout linearLayout;
    private Calendar currentCalendar;
    private LinearLayout layoutHourReceive;
    private View viewLayoutHourReceive, viewLine0, viewLine1, viewLine2, viewLine3;
    private int positionLayoutHourReceive;
    private Button logoutButton;
    private UserModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        configureActionBar();
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
        positionLayoutHourReceive = layoutHourReceive.getChildCount();
        logoutButton = (Button) findViewById(R.id.logoutButton);
        viewLine0 = findViewById(R.id.viewLine0);
        viewLine1 = findViewById(R.id.viewLine1);
        viewLine2 = findViewById(R.id.viewLine2);
        viewLine3 = findViewById(R.id.viewLine3);
    }

    private void configureActionBar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled (false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        Button leftButton = (Button) findViewById(R.id.leftButton);
        leftButton.setVisibility(View.GONE);
        Button rightButton = (Button) findViewById(R.id.rightButton);
        rightButton.setVisibility(View.GONE);
        ImageView rightButtonImage = (ImageView) findViewById(R.id.rightImageView);
        rightButtonImage.setVisibility(View.GONE);
        ImageView leftButtonImage = (ImageView) findViewById(R.id.leftImageView);
        leftButtonImage.setVisibility(View.GONE);

        TextView textView = (TextView) findViewById(R.id.titleActionBarTextView);
        textView.setTypeface(LayoutManager.sharedInstance().typefaceQuicksandBold);
        textView.setText(R.string.title_activity_configuration);
    }

    private void setPropertyView(){
        currentUser = DBManager.getCachedUser();

        discreteSeekBar.setNumericTransformer(new DiscreteSeekBar.NumericTransformer() {
            @Override
            public int transform(int value) {
                return value * CONSTANT_PROGRESS_BAR;
            }
        });

        discreteSeekBar.setMin(CONSTANTE_PROGRESS_BAR_MIN);
        discreteSeekBar.setMax(CONSTANTE_PROGRESS_BAR_MAX);

        setTitle(R.string.title_activity_configuration);
        notificationSwitch.setChecked(currentUser.getReciveNotification());
        noturneModeSwitch.setChecked(currentUser.getNoturneMode());

        int progressAditionalBar = (currentUser.getTextSize()/CONSTANT_PROGRESS_BAR);
        discreteSeekBar.setProgress(progressAditionalBar);

        notificationSwitch.setOnCheckedChangeListener(this);
        noturneModeSwitch.setOnCheckedChangeListener(this);
        discreteSeekBar.setOnProgressChangeListener(this);

        notificationSwitch.setTypeface(LayoutManager.sharedInstance().typefaceComfortaaRegular);
        receiveReadingTextView.setTypeface(LayoutManager.sharedInstance().typefaceComfortaaRegular);
        hourReadingTextView.setTypeface(LayoutManager.sharedInstance().typefaceComfortaaRegular);
        noturneModeSwitch.setTypeface(LayoutManager.sharedInstance().typefaceComfortaaRegular);
        heightTextView.setTypeface(LayoutManager.sharedInstance().typefaceComfortaaRegular);
        logoutButton.setTypeface(LayoutManager.sharedInstance().typefaceComfortaaRegular);
        mensageTextView.setTypeface(LayoutManager.sharedInstance().typefaceMerriweatherLight);

        //Configure Noturne Mode
        if (currentUser.getNoturneMode()){
            setNoturneMode();
        }else {
            resetNoturneMode();
        }

        currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(currentUser.getHourNotification());

        hourReadingTextView.setText(getStringHourNotification());
        hourReadingTextView.setOnClickListener(this);

        logoutButton.setOnClickListener(this);

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
        logoutButton.setTextColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorNoturne,null));

        }else {
            linearLayout.setBackgroundColor(getResources().getColor(R.color.colorNoturne));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewLine0.setBackground(getDrawable(R.color.grey_line_layout_10_opc));
            viewLine1.setBackground(getDrawable(R.color.grey_line_layout_10_opc));
            viewLine2.setBackground(getDrawable(R.color.grey_line_layout_10_opc));
            viewLine3.setBackground(getDrawable(R.color.grey_line_layout_10_opc));
            viewLayoutHourReceive.setBackground(getDrawable(R.color.grey_line_layout_10_opc));
        }else {
            viewLine0.setBackground(getResources().getDrawable(R.color.grey_line_layout_10_opc));
            viewLine1.setBackground(getResources().getDrawable(R.color.grey_line_layout_10_opc));
            viewLine2.setBackground(getResources().getDrawable(R.color.grey_line_layout_10_opc));
            viewLine3.setBackground(getResources().getDrawable(R.color.grey_line_layout_10_opc));
            viewLayoutHourReceive.setBackground(getResources().getDrawable(R.color.grey_line_layout_10_opc));
        }

    }

    private void resetNoturneMode(){
        receiveReadingTextView.setTextColor(Color.BLACK);
        mensageTextView.setTextColor(Color.BLACK);
        heightTextView.setTextColor(Color.BLACK);
        notificationSwitch.setTextColor(Color.BLACK);
        noturneModeSwitch.setTextColor(Color.BLACK);
        linearLayout.setBackgroundColor(Color.WHITE);
        logoutButton.setTextColor(Color.BLACK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewLine0.setBackground(getDrawable(R.color.grey_line_layout));
            viewLine1.setBackground(getDrawable(R.color.grey_line_layout));
            viewLine2.setBackground(getDrawable(R.color.grey_line_layout));
            viewLine3.setBackground(getDrawable(R.color.grey_line_layout));
            viewLayoutHourReceive.setBackground(getDrawable(R.color.grey_line_layout));
        }else {
            viewLine0.setBackground(getResources().getDrawable(R.color.grey_line_layout));
            viewLine1.setBackground(getResources().getDrawable(R.color.grey_line_layout));
            viewLine2.setBackground(getResources().getDrawable(R.color.grey_line_layout));
            viewLine3.setBackground(getResources().getDrawable(R.color.grey_line_layout));
            viewLayoutHourReceive.setBackground(getResources().getDrawable(R.color.grey_line_layout));
        }
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
            case 0:
                stringFormatTime = " AM";
                //Validation Hour
                if (hours==0){
                    stringHours = "12";
                }
                break;
            case 1:
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
        linearLayout.addView(layoutHourReceive,positionLayoutHourReceive);
        linearLayout.addView(viewLayoutHourReceive,positionLayoutHourReceive+1);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        compoundButton.setChecked(b);
        switch (compoundButton.getId()){
            case R.id.notificationSwitch:
                currentUser.updateReciveNotification(b);
                //Configure Layout Hour Reading
                if (notificationSwitch.isChecked()){
                    showLayoutHourReceive();
                    NotificationsManager.setReadingDaysNotifications(this,currentUser.getHourNotification());
                }else {
                    hideLayoutHourReceive();
                    NotificationsManager.cancelAllNotifications(this);
                }
                break;
            case R.id.noturneModeSwitch:
                currentUser.updateNoturneMode(b);
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
        currentUser.updateTextSize(seekBar.getProgress()*CONSTANT_PROGRESS_BAR);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND,0);
        currentCalendar = calendar;
        currentUser.updateHourNotification(calendar.getTimeInMillis());
        hourReadingTextView.setText(getStringHourNotification());
        NotificationsManager.cancelAllNotifications(this);
        NotificationsManager.setReadingDaysNotifications(this,currentUser.getHourNotification());
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
            case R.id.logoutButton:
                DBManager.getCachedUser().logoutUser();
                FeedbackManager.createToast(getApplicationContext(),getString(R.string.placeholder_success_logout),false);
                ActivityManager.changeActivityAndRemoveParentActivity(ConfigurationActivity.this, InitialActivity.class);
                break;
        }
    }
}
