package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gatech.water_app.R;


import gatech.water_app.model.UserLoginTask;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button signInButton;
    Button cancelButton;
    int counterAttempt;

    public String getUserName() {
        return username.getText().toString();
    }
    public String getPassWord() {
        return password.getText().toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        username = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passwordInput);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        cancelButton = (Button) findViewById(R.id.login_cancel);
        counterAttempt = 5;

        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserLoginTask.attemptLogin(username.getText().toString(), password.getText().toString())) {
                    //ENTER THE SYSTEM
                    afterLogin();
                } else {
                    counterAttempt--;
                    Toast.makeText(getApplicationContext(), "Incorrect Credentials, Attempts Left " + counterAttempt, Toast.LENGTH_SHORT).show();

                    if (counterAttempt == 0) {
                        signInButton.setEnabled(false);
                    }
                }
            }
        });
        }

    public void cancelButtonAction(View v) {
        finish();
    }

    private void afterLogin() {
        Intent intent = new Intent(this, LandingPage.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("pass", password.getText().toString());
        bundle1.putString("username", username.getText().toString());
        intent.putExtras(bundle1);
        startActivity(intent);
    }



}

