package lab262.leituradebolso;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;

import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Login.LoginActivity;

public class LaunchScreenActivity extends Activity {

    private int secondsDelayLaunch = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ActivityManager.changeActivity(LaunchScreenActivity.this, LoginActivity.class);

                finish();

            }
        }, secondsDelayLaunch);
    }
}
