package lab262.leituradebolso;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Login.InitialActivity;
import lab262.leituradebolso.Persistence.DBManager;
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

                if (DBManager.getCachedUser()==null || DBManager.getCachedUser().getToken()==null){
                    ActivityManager.changeActivity(LaunchScreenActivity.this, InitialActivity.class);
                }else {
                   ActivityManager.changeActivity(LaunchScreenActivity.this, ReadingDayActivity.class);
                }
                finish();

            }
        }, secondsDelayLaunch);
    }

}
