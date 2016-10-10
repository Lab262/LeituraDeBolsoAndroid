package lab262.leituradebolso;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Extensions.ApplicationState;
import lab262.leituradebolso.Login.InitialActivity;
import lab262.leituradebolso.ReadingDay.ReadingDayActivity;

public class LaunchScreenActivity extends Activity {

    private int secondsDelayLaunch = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        //Initialize Relm
        Realm.init(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //TODO: Modificar a forma de inicializar o State
                ApplicationState applicationState = new ApplicationState(true,false,14,new Date());

                ActivityManager.changeActivity(LaunchScreenActivity.this, InitialActivity.class);

                finish();

            }
        }, secondsDelayLaunch);
    }

}
