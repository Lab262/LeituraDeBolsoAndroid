package lab262.leituradebolso.Login;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import lab262.leituradebolso.Extensions.ActivityManager;
import lab262.leituradebolso.R;
import lab262.leituradebolso.Register.RegisterActivity;

public class InitialActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton, registerButton;
    private Typeface typeFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
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

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
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
        }
    }
}
