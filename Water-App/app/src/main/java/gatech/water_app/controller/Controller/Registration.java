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
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import gatech.water_app.model.Title;
import gatech.water_app.model.UserLoginTask;

import gatech.water_app.R;

public class Registration extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText email;
    EditText address;
    Button registerButton;
    Button cancelButton;
    Spinner newSpin;
    String[] arraySpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        username = (EditText) findViewById(R.id.usernameIn);
        password = (EditText) findViewById(R.id.passwordIn);
        email = (EditText) findViewById(R.id.emailIn);
        address = (EditText) findViewById(R.id.addressIn);
        registerButton = (Button) findViewById(R.id.submitreg);
        cancelButton = (Button) findViewById(R.id.cancel);
        newSpin = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, Title.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newSpin.setAdapter(adapter);

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
                Log.d("[Login]", "Registeration Failed");
                Toast.makeText(getApplicationContext(), "Registration Failed" ,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void afterEdit() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
