package lab262.leituradebolso.Login;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import lab262.leituradebolso.R;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton, registerButton;
    private Typeface typeFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getInstanceViews();
        setPropertyView();
    }

    private void getInstanceViews(){
        typeFace =Typeface.createFromAsset(getAssets(),"fonts/Quicksand-Bold.otf");
        loginButton = (Button)findViewById(R.id.loginButton);
        registerButton = (Button)findViewById(R.id.registerButton);
    }

    private void setPropertyView(){
        loginButton.setTypeface(typeFace);
        registerButton.setTypeface(typeFace);
    }
}
