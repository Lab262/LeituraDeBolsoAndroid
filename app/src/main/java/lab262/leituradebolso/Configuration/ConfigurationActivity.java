package lab262.leituradebolso.Configuration;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import lab262.leituradebolso.Extensions.ApplicationState;
import lab262.leituradebolso.R;


public class ConfigurationActivity extends AppCompatActivity implements Switch.OnCheckedChangeListener, DiscreteSeekBar.OnProgressChangeListener {

    private DiscreteSeekBar discreteSeekBar;
    private Switch notificationSwitch, noturneModeSwitch;
    private static final int CONSTANT_PROGRESS_BAR = 2;
    private TextView receiveReadingTextView, mensageTextView, heightTextView;
    private LinearLayout linearLayout;

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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        compoundButton.setChecked(b);
        switch (compoundButton.getId()){
            case R.id.notificationSwitch:
                ApplicationState.sharedState().setReciveNotification(b);
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
}
