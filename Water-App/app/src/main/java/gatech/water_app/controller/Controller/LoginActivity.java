package gatech.water_app.controller.Controller;
import android.util.Log;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import 	android.os.AsyncTask;
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
                new HTTPLoginTask().execute(username.getText().toString(),password.getText().toString());
//                Log.d("[Interaction]", "User button login pressed");
//                try {
//                    if (UserLoginTask.attemptLogin(username.getText().toString(), password.getText().toString())) {
//                        //ENTER THE SYSTEM
//                        //afterLogin();
//                        new DownloadImageTask().execute(username.getText().toString(),password.getText().toString());
//                    } else {
//                        counterAttempt--;
//                        Toast.makeText(getApplicationContext(), "Incorrect Credentials, Attempts Left " + counterAttempt, Toast.LENGTH_SHORT).show();
//                        Log.d("[Interaction]", "User Login failed");
//                        if (counterAttempt == 0) {
//                            signInButton.setEnabled(false);
//                        }
//                    }
//                } catch (Exception e) {
//                    Log.d("[Interaction]", "Exception");
//                    e.printStackTrace();
//                }
            }
        });
    }
    //HI GUYZ, since we are sending an http request, it needs to be async
    //you cant execute http requests in the main thread, because it would freeze the app
    //so we need a task which executes by itself and when it is done it does something (afterlogin)
    private class HTTPLoginTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String[] params) {
            try {
                return UserLoginTask.attemptLogin(params[0], params[1]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                afterLogin();
            }else {
                Log.d("[Login]", "Login failed http returned false");
            }
        }
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

