package lab262.leituradebolso.Configuration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import lab262.leituradebolso.Extensions.ApplicationState;
import lab262.leituradebolso.R;


public class ConfigurationActivity extends AppCompatActivity implements Switch.OnCheckedChangeListener, DiscreteSeekBar.OnProgressChangeListener {

    private DiscreteSeekBar discreteSeekBar;
    private Switch notificationSwitch, noturneModeSwitch;
    private static final int CONSTANT_PROGRESS_BAR = 2;

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
