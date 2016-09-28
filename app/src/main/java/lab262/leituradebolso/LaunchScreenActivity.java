package lab262.leituradebolso;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;


import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.Header;
import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Extensions.ApplicationState;
import lab262.leituradebolso.Login.InitialActivity;
import lab262.leituradebolso.Model.UserModel;
import lab262.leituradebolso.ReadingDay.ReadingDayActivity;
import lab262.leituradebolso.Requests.UserRequest;

public class LaunchScreenActivity extends Activity {

    private int secondsDelayLaunch = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        //testCreateUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //TODO: Modificar a forma de inicializar o State
                ApplicationState applicationState = new ApplicationState(true,false,14,new Date());

                ActivityManager.changeActivity(LaunchScreenActivity.this, ReadingDayActivity.class);

                finish();

            }
        }, secondsDelayLaunch);
    }

    private void testCreateUser(){
        UserModel userModel = new UserModel("teste@test.com");
        UserRequest.createAccountUser(userModel,"1234",new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject jsonObject = (JSONObject) ((JSONObject)((JSONObject)response.get("user")).get("data")).get("attributes");
                    System.out.println(jsonObject.get("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    System.out.println(errorResponse.get("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

}
