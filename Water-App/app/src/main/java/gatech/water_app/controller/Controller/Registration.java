package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gatech.water_app.model.UserLoginTask;

import gatech.water_app.R;

public class Registration extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText email;
    private EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        username = (EditText) findViewById(R.id.usernameIn);
        password = (EditText) findViewById(R.id.passwordIn);
        email = (EditText) findViewById(R.id.emailIn);
        address = (EditText) findViewById(R.id.addressIn);
        Button registerButton = (Button) findViewById(R.id.submitreg);
        Button cancelButton = (Button) findViewById(R.id.cancel);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")
                        || password.getText().toString().equals("")
                        || email.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "One of your fields was empty!" ,Toast.LENGTH_SHORT).show();
                } else {
                    //UserLoginTask.addUser(username.getText().toString(), password.getText().toString(), address.getText().toString(), email.getText().toString(), (Title) newSpin.getSelectedItem());
                    new HTTPRegisterTask().execute(username.getText().toString(), password.getText().toString(), address.getText().toString(), email.getText().toString());
                }
            }
        });


        cancelButton.setOnClickListener(
                new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finish();
                                            }
                                        }
        );


    }

    private class HTTPRegisterTask extends AsyncTask<String, Integer, Boolean> {
        protected Boolean doInBackground(String[] params) {
            try {
                return UserLoginTask.addUser(params[0], params[1], params[2], params[3]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {
            if (result) {
                afterEdit();
            }else {
                Log.d("[Login]", "Registration Failed");
                Toast.makeText(getApplicationContext(), "Registration Failed" ,Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Goes to the login page
     */
    private void afterEdit() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
