package lab262.leituradebolso.Register;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import lab262.leituradebolso.R;

public class RegisterActivity extends AppCompatActivity {

    private Typeface typeFaceQuick,typeFaceComfortaaThin;
    private TextView registerTextView;
    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;

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

        registerTextView = (TextView) findViewById(R.id.registerTextView);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);
        registerButton = (Button) findViewById(R.id.registerButton);
    }

    private void setPropertyView(){
        registerTextView.setTypeface(typeFaceQuick);
        registerButton.setTypeface(typeFaceQuick);
        emailEditText.setTypeface(typeFaceComfortaaThin);
        passwordEditText.setTypeface(typeFaceComfortaaThin);
        confirmPasswordEditText.setTypeface(typeFaceComfortaaThin);
    }
}
