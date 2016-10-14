package lab262.leituradebolso.Register;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Extensions.FeedbackManager;
import lab262.leituradebolso.Login.InitialActivity;
import lab262.leituradebolso.Model.UserModel;
import lab262.leituradebolso.R;
import lab262.leituradebolso.Requests.UserRequest;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Typeface typeFaceQuick,typeFaceComfortaaThin;
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getInstanceViews();
        setPropertyView();
    }

    private void getInstanceViews(){
        typeFaceQuick =Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");
        typeFaceComfortaaThin =Typeface.createFromAsset(getAssets(),"fonts/Comfortaa_Thin.ttf");

        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);
        registerButton = (Button) findViewById(R.id.registerButton);
    }

    private void setPropertyView(){
        registerButton.setTypeface(typeFaceQuick);
        emailEditText.setTypeface(typeFaceComfortaaThin);
        passwordEditText.setTypeface(typeFaceComfortaaThin);
        confirmPasswordEditText.setTypeface(typeFaceComfortaaThin);

        registerButton.setOnClickListener(this);
    }

    private void registerUser(){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_progress_dialog));
        UserModel userModel = new UserModel(emailEditText.getText().toString());
        UserRequest.createAccountUser(userModel,passwordEditText.getText().toString(),new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                FeedbackManager.createToast(getApplicationContext(),getString(R.string.placeholder_success_register),false);
                ActivityManager.changeActivityAndRemoveParentActivity(RegisterActivity.this, InitialActivity.class);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                FeedbackManager.feedbackErrorResponse(getApplicationContext(),progressDialog,statusCode,errorResponse);
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerButton:
                if (passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())){
                    registerUser();
                }else {
                    FeedbackManager.createToast(getApplicationContext(),getString(R.string.placeholder_error_register_password),false);
                }
                break;
        }
    }
}
