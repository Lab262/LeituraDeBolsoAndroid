package lab262.leituradebolso.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import cz.msebera.android.httpclient.Header;
import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.Extensions.FeedbackManager;
import lab262.leituradebolso.Extensions.LayoutManager;
import lab262.leituradebolso.Model.UserModel;
import lab262.leituradebolso.Persistence.DBManager;
import lab262.leituradebolso.R;
import lab262.leituradebolso.ReadingDay.ReadingDayActivity;
import lab262.leituradebolso.Register.RegisterActivity;
import lab262.leituradebolso.Requests.UserRequest;

public class InitialActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton, registerButton, facebookButton;
    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        getInstanceViews();
        setPropertyView();
    }

    private void getInstanceViews(){
        loginButton = (Button)findViewById(R.id.loginButton);
        registerButton = (Button)findViewById(R.id.registerButton);
        facebookButton = (Button)findViewById(R.id.facebookButton);
    }

    private void setPropertyView(){
        loginButton.setTypeface(LayoutManager.sharedInstance().typefaceQuicksandBold);
        registerButton.setTypeface(LayoutManager.sharedInstance().typefaceQuicksandBold);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        facebookButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginButton:
                ActivityManager.changeActivity(InitialActivity.this, LoginActivity.class);
                break;
            case R.id.registerButton:
                ActivityManager.changeActivity(InitialActivity.this, RegisterActivity.class);
                break;
            case R.id.facebookButton:
                loginFacebook();
                break;
        }
    }

    private void loginWithFacebook(String emailFacebook, String  idFacebook){
        progressDialog = FeedbackManager.createProgressDialog(this,getString(R.string.placeholder_progress_dialog));
        UserRequest.loginFacebookUser(emailFacebook,idFacebook, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                FeedbackManager.dismissProgressDialog(progressDialog);
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
                ActivityManager.changeActivityAndRemoveParentActivity(InitialActivity.this, ReadingDayActivity.class);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                FeedbackManager.feedbackErrorResponse(getApplicationContext(),null,statusCode,errorResponse);
            }

        });
    }

    private void loginFacebook(){
        configureFacebook();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(UserRequest.keyFieldEmailFacebook));
    }

    private void configureFacebook(){

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        String emailFacebook = "";
                                        String idFacebook = "";
                                        try {
                                            emailFacebook = object.getString(UserRequest.keyFieldEmailFacebook);
                                            idFacebook = object.getString(UserRequest.keyFieldIDFacebook);
                                            loginWithFacebook(emailFacebook,idFacebook);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        String valueParams =  UserRequest.keyFieldIDFacebook + ","+UserRequest.keyFieldEmailFacebook;
                        parameters.putString(UserRequest.keyFieldsLoginFacebook,valueParams);
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        FeedbackManager.createToast(getApplicationContext(),getString(R.string.placeholder_cancel_login),true);
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        FeedbackManager.createToast(getApplicationContext(),exception.getLocalizedMessage(),true);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        FeedbackManager.dismissProgressDialog(progressDialog);
        super.onDestroy();
    }
}
