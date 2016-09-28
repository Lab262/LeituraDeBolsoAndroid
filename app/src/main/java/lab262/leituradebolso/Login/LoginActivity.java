package lab262.leituradebolso.Login;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingDay.ReadingDayActivity;
import lab262.leituradebolso.Requests.UserRequest;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Typeface typeFaceQuick, typeFaceComfortaa,typeFaceComfortaaThin;
    private TextView loginTextView;
    private EditText emailEditText, passwordEditText;
    private Button forgotPasswordButton, loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getInstanceViews();
        setPropertyView();
    }

    private void getInstanceViews(){
        typeFaceQuick =Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");
        typeFaceComfortaa =Typeface.createFromAsset(getAssets(),"fonts/Comfortaa_Regular.ttf");
        typeFaceComfortaaThin =Typeface.createFromAsset(getAssets(),"fonts/Comfortaa_Thin.ttf");

        loginTextView = (TextView) findViewById(R.id.loginTextView);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        forgotPasswordButton = (Button) findViewById(R.id.forgotPasswordButton);
        loginButton = (Button) findViewById(R.id.loginButton);
    }

    private void setPropertyView(){
        loginTextView.setTypeface(typeFaceQuick);
        forgotPasswordButton.setTypeface(typeFaceComfortaa);
        emailEditText.setTypeface(typeFaceComfortaaThin);
        passwordEditText.setTypeface(typeFaceComfortaaThin);
        loginButton.setTypeface(typeFaceQuick);
        loginButton.setOnClickListener(this);
    }

    private void loginUser(){
        UserRequest.loginUser(emailEditText.getText().toString(), passwordEditText.getText().toString(),new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                ActivityManager.changeActivityAndRemoveParentActivity(LoginActivity.this, ReadingDayActivity.class);
                System.out.println(statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //TODO: Alerta para falha
                try {
                    System.out.println(errorResponse.get("message").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:
                loginUser();
                break;
        }
    }
}
