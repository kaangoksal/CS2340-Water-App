package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import gatech.water_app.model.User;

import gatech.water_app.model.UserLoginTask;

import gatech.water_app.R;

/**
 * Creates the page that does the editing of the profile.
 */
public class EditPage extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private EditText email;

    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent extraIntent = getIntent();
        Bundle extras = extraIntent.getExtras();
        User loginUser = (User) extras.getSerializable("user");



        TextView curUsername = (TextView) findViewById(R.id.textView4);
        TextView curPass = (TextView) findViewById(R.id.textView5);
        TextView curEmail = (TextView) findViewById(R.id.textView6);
        assert loginUser != null;
        String curUserString = "Current Username: " + loginUser.getUsername();
        String curPassString = "Current Password: " + loginUser.getPassword();
        String curEmailString = "Current Email: " + loginUser.getEmail();


        curUsername.setText(curUserString);
        curPass.setText(curPassString);
        curEmail.setText(curEmailString);

        username = (EditText) findViewById(R.id.userNameInput);
        password = (EditText) findViewById(R.id.passwordInput);
        email = (EditText) findViewById(R.id.emailInput);
        Button editButton = (Button) findViewById(R.id.doneEditing);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        editButton.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Editable userNameText = username.getText();
                                              Editable userEmailText = email.getText();
                                              Editable passwordText = password.getText();
                                              new HTTPEditTask().execute(userNameText
                                                      .toString(),passwordText
                                                      .toString(), userEmailText.toString());
                                              newUser = new User(userNameText
                                                      .toString(),passwordText.toString(),
                                                      userEmailText.toString());
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
                Snackbar made = Snackbar.make(view,
                        "Replace with your own action", Snackbar.LENGTH_LONG);
                Snackbar action = made.setAction("Action", null);
                action.show();
            }
        });
    }

    private class HTTPEditTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String[] params) {
            try {
                return UserLoginTask.attemptEditUser(params[0], params[1], params[2]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                afterEdit();
            }else {
                Log.d("[EditPage]", "Edit failed http returned false");
            }
        }
    }

    /**
     * after Editing, go back to the page you came from.
     */
    private void afterEdit() {
//        UserLoginTask.editUser(newUser);
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
