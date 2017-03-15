package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import gatech.water_app.model.User;

import gatech.water_app.model.UserLoginTask;

import gatech.water_app.R;

public class EditPage extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText email;
    Button editButton;
    Button cancelButton;

    User curUser;
    User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        curUser = UserLoginTask.retrieveUser(extras.getString("username"), extras.getString("password"));

        TextView curUsername = (TextView) findViewById(R.id.textView4);
        TextView curPass = (TextView) findViewById(R.id.textView5);
        TextView curEmail = (TextView) findViewById(R.id.textView6);


        curUsername.setText("Current Email: " + curUser.getUsername());
        curPass.setText("Current Password: " + curUser.getPassword());
        curEmail.setText("Current Email: " + curUser.getEmail());

        username = (EditText) findViewById(R.id.userNameInput);
        password = (EditText) findViewById(R.id.passwordInput);
        email = (EditText) findViewById(R.id.emailInput);
        editButton = (Button) findViewById(R.id.doneEditing);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        editButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              if (username.getText().toString() == null
                                                      || password.getText().toString() == null
                                                      || email.getText().toString() == null) {
                                                  Toast.makeText(getApplicationContext(), "One of your fields was null!" ,Toast.LENGTH_SHORT).show();
                                              } else {
                                                  new HTTPEditTask().execute(username.getText().toString(),password.getText().toString(), email.getText().toString());
                                                  newUser = new User(username.getText().toString(),password.getText().toString(), email.getText().toString());
                                              }
                                          }
                                      });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
        }
        }
        );




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private class HTTPEditTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String[] params) {
            try {
                return UserLoginTask.attemptEditUser(params[0], params[1], params[2]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                afterEdit();
            }else {
                Log.d("[EditPage]", "Edit failed http returned false");
            }
        }
    }

    public void afterEdit() {
        UserLoginTask.editUser(newUser);
        Intent intent = new Intent(this, LandingPage.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("pass", newUser.getPassword());
        bundle1.putString("username", newUser.getUsername());
        intent.putExtras(bundle1);
        startActivity(intent);


//        Intent intent = new Intent(this, LandingPage.class);
//        Bundle bundle1 = new Bundle();
//        bundle1.putString("pass", password.getText().toString());
//        bundle1.putString("username", username.getText().toString());
//        intent.putExtras(bundle1);
//        startActivity(intent);
    }

}
