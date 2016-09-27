package lab262.leituradebolso.Login;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import lab262.leituradebolso.R;

public class LoginActivity extends AppCompatActivity {

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
    }
}
