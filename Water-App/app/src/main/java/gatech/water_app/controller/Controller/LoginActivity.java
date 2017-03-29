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


import gatech.water_app.model.Title;
import gatech.water_app.model.User;
import gatech.water_app.model.UserLoginTask;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button signInButton;
    Button cancelButton;
    int counterAttempt;
    User loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        email = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passwordInput);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        cancelButton = (Button) findViewById(R.id.login_cancel);
        counterAttempt = 5;

        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser = new User("", password.getText().toString(), email.getText().toString());
                new HTTPLoginTask().execute(loginUser);
            }
        });
    }

    //HI GUYZ, since we are sending an http request, it needs to be async
    //you cant execute http requests in the main thread, because it would freeze the app
    //so we need a task which executes by itself and when it is done it does something (afterlogin)
    private class HTTPLoginTask extends AsyncTask<Object, Integer, User> {
        protected User doInBackground(Object[] params) {
            try {
                return UserLoginTask.attemptLogin((User)params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(User dbuser) {
            if (dbuser != null) {
                afterLogin(dbuser);
            }else {
                Log.d("[Login]", "Login failed http returned false");
            }
        }
    }

    public void cancelButtonAction(View v) {
        finish();
    }

    private void afterLogin(User dbuser) {
        if (dbuser.getTitle().equals(Title.USER)) {
            loginUser = dbuser;
            Intent intent = new Intent(this, LandingPage.class);
            Bundle bundle1 = new Bundle();
            intent.putExtras(bundle1);
            intent.putExtra("user", loginUser);
            startActivity(intent);
        } else if (dbuser.getTitle().equals(Title.WORKER)) {
            loginUser = dbuser;
            Intent intent = new Intent(this, WorkerLandingPage.class);
            intent.putExtra("user", loginUser);
            startActivity(intent);
        } else if (dbuser.getTitle().equals(Title.MANAGER)) {
            loginUser = dbuser;
            Intent intent = new Intent(this, ManagerLandingPage.class);
            intent.putExtra("user", loginUser);
            startActivity(intent);
        }
    }



}

