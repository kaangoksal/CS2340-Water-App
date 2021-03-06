package gatech.water_app.controller.Controller;
import android.text.Editable;
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

    private EditText email;
    private EditText password;
    private User loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        email = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passwordInput);
        Button signInButton = (Button) findViewById(R.id.sign_in_button);



        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable passwordText = password.getText();
                Editable emailText = email.getText();
                loginUser = new User("", passwordText.toString(), emailText.toString());
                new HTTPLoginTask().execute(loginUser);
            }
        });
    }

    //HI Guys, since we are sending an http request, it needs to be async
    //you cant execute http requests in the main thread, because it would freeze the app
    //so we need a task which executes by itself and when it is done it does something (after login)
    private class HTTPLoginTask extends AsyncTask<Object, Integer, User> {
        @Override
        protected User doInBackground(Object[] params) {
            try {
                return UserLoginTask.attemptLogin((User)params[0]);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(User database_User) {
            if (database_User != null) {
                afterLogin(database_User);
            }else {
                Log.d("[Login]", "Login failed http returned false");
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Login Failed. Please try again!"
                        ,Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    /**
     * Does not login
     * @param v the view you are attempting to reach
     */
    public void cancelButtonAction(View v) {
        finish();
    }

    private void afterLogin(User database_User) {
        Title userTitle = database_User.getTitle();
        if (userTitle.equals(Title.USER)) {
            loginUser = database_User;
            Intent intent = new Intent(this, LandingPage.class);
            Bundle bundle1 = new Bundle();
            intent.putExtras(bundle1);
            intent.putExtra("user", loginUser);
            startActivity(intent);
            this.overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_left);
        } else if (userTitle.equals(Title.WORKER)) {
            loginUser = database_User;
            Intent intent = new Intent(this, WorkerLandingPage.class);
            intent.putExtra("user", loginUser);
            startActivity(intent);
            this.overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_left);
        } else if (userTitle.equals(Title.MANAGER)) {
            loginUser = database_User;
            Intent intent = new Intent(this, ManagerLandingPage.class);
            intent.putExtra("user", loginUser);
            startActivity(intent);
            this.overridePendingTransition(R.anim.slide_in_left,
                    R.anim.slide_out_left);
        }
    }



}

