package lab262.leituradebolso.Login;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;


import cz.msebera.android.httpclient.Header;
import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Extensions.FeedbackManager;
import lab262.leituradebolso.Extensions.LayoutManager;
import lab262.leituradebolso.Model.UserModel;
import lab262.leituradebolso.Persistence.DBManager;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingDay.ReadingDayActivity;
import lab262.leituradebolso.Requests.UserRequest;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, AlertDialog.OnClickListener {

    private EditText emailEditText, passwordEditText;
    private Button forgotPasswordButton, loginButton;
    private EditText emailForgotPasswordEditText;
    private ProgressDialog progressDialog;

    private static int defaultTextSize = 16;
    private static int defaultFirstPaddingValue = 30;
    private static int defaultSecondPaddingValue = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getInstanceViews();
        setPropertyView();
    }

    private void getInstanceViews(){
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        forgotPasswordButton = (Button) findViewById(R.id.forgotPasswordButton);
        loginButton = (Button) findViewById(R.id.loginButton);
    }

    private void setPropertyView(){
        forgotPasswordButton.setTypeface(LayoutManager.sharedInstance().typefaceComfortaaRegular);
        emailEditText.setTypeface(LayoutManager.sharedInstance().typefaceComfortaaThin);
        passwordEditText.setTypeface(LayoutManager.sharedInstance().typefaceComfortaaThin);
        loginButton.setTypeface(LayoutManager.sharedInstance().typefaceQuicksandBold);
        loginButton.setOnClickListener(this);
        forgotPasswordButton.setOnClickListener(this);
    }

    private void loginUser(){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_progress_dialog));
        UserRequest.loginUser(emailEditText.getText().toString(), passwordEditText.getText().toString(),new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                FeedbackManager.createToast(getApplicationContext(),getString(R.string.placeholder_success_login),true);

                //Create user with JSONObject
                UserModel userLogged = new UserModel(response);

                //Verify if the same user in previous database
                UserModel userDatabase = DBManager.getCachedUser();
                if (userDatabase!=null){
                    if (!userLogged.getEmail().matches(userDatabase.getEmail())){
                        //Save user in Relm.
                        DBManager.deleteDatabase();
                        DBManager.saveObject(userLogged);
                    }else {
                        DBManager.getCachedUser().updateToken(userLogged.getToken());
                    }
                }else {
                    DBManager.saveObject(userLogged);
                }
                ActivityManager.changeActivityAndRemoveParentActivity(LoginActivity.this, ReadingDayActivity.class);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                FeedbackManager.feedbackErrorResponse(getApplicationContext(),progressDialog,statusCode,errorResponse);
            }

        });
    }

    private void createAlertDialog(){

        //Create custom edit text
        emailForgotPasswordEditText = new EditText(this);
        emailForgotPasswordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailForgotPasswordEditText.setHint(getString(R.string.placeholder_alert_forgot_hint));
        emailForgotPasswordEditText.setTypeface(LayoutManager.sharedInstance().typefaceComfortaaThin);
        emailForgotPasswordEditText.setPaddingRelative(defaultFirstPaddingValue,defaultFirstPaddingValue,defaultFirstPaddingValue,defaultSecondPaddingValue);

        //Create custom title
        TextView textView = new TextView(this);
        textView.setTypeface(LayoutManager.sharedInstance().typefaceQuicksandBold);
        textView.setText(getString(R.string.title_alert_forgot_password));
        textView.setTextSize(defaultTextSize);
        textView.setPadding(defaultFirstPaddingValue,defaultFirstPaddingValue,defaultFirstPaddingValue,defaultSecondPaddingValue);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailForgotPasswordEditText.setHintTextColor(getColor(R.color.colorPrimary));
            emailForgotPasswordEditText.setTextColor(getColor(R.color.colorPrimary));
            emailForgotPasswordEditText.setLinkTextColor(getColor(R.color.colorPrimary));
            textView.setTextColor(getColor(R.color.colorPrimary));
        }else {
            emailForgotPasswordEditText.setHintTextColor(getResources().getColor(R.color.colorPrimary));
            emailForgotPasswordEditText.setTextColor(getResources().getColor(R.color.colorPrimary));
            emailForgotPasswordEditText.setLinkTextColor(getResources().getColor(R.color.colorPrimary));
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        //Create Alert
        AlertDialog.Builder forgotAlert = new AlertDialog.Builder(this,R.style.AlertDialogCustomTheme);
        forgotAlert.setCustomTitle(textView);
        forgotAlert.setView(emailForgotPasswordEditText);
        forgotAlert.setPositiveButton(getString(R.string.placeholder_alert_forgot_positive_button), this);
        forgotAlert.setNegativeButton(getString(R.string.placeholder_alert_forgot_negative_button), null);
        forgotAlert.show();
    }

    private void forgotPassword(){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_progress_dialog));
        UserRequest.forgotUser(emailForgotPasswordEditText.getText().toString(),new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                try {
                    String message = response.getString(UserRequest.keyMessageForgotPassword);
                    FeedbackManager.createToast(getApplicationContext(),message,false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                FeedbackManager.feedbackErrorResponse(getApplicationContext(),progressDialog,statusCode,errorResponse);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:
                loginUser();
                break;
            case R.id.forgotPasswordButton:
                createAlertDialog();
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        forgotPassword();
    }
}
