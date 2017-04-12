package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gatech.water_app.model.UserLoginTask;

import gatech.water_app.R;

/**
 * registration page
 */
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

                Editable userNameText = username.getText();
                Editable userEmailText = email.getText();
                Editable passwordText = password.getText();
                Editable addressText = address.getText();
                if ("".equals(userNameText.toString())
                        || "".equals(passwordText.toString())
                        || "".equals(userEmailText.toString())) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "One of your fields was empty!" ,
                            Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    new HTTPRegisterTask().execute(userNameText.toString(), passwordText
                            .toString(), addressText.toString(), userEmailText.toString());
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
        @Override
        protected Boolean doInBackground(String[] params) {
            try {
                return UserLoginTask.addUser(params[0], params[1], params[2], params[3]);
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
                Log.d("[Login]", "Registration Failed");
                Toast toast = Toast.makeText(getApplicationContext(), "Registration Failed" ,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    /**
     * Goes to the login page
     */
    private void afterEdit() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_left);
    }

}
