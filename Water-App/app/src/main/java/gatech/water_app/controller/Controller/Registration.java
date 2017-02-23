package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import gatech.water_app.model.User;

import gatech.water_app.R;

public class Registration extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText email;
    EditText address;
    Button editButton;
    Button cancelButton;
    Spinner newSpin;
    User curUser = new User("v","v","v","v","v");
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
        editButton = (Button) findViewById(R.id.submitreg);
        cancelButton = (Button) findViewById(R.id.cancel);
        newSpin = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, User.titles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newSpin.setAdapter(adapter);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString() == null
                        || password.getText().toString() == null
                        || email.getText().toString() == null) {
                    Toast.makeText(getApplicationContext(), "One of your fields was null!" ,Toast.LENGTH_SHORT).show();
                } else {
                    curUser.setUsername(username.getText().toString());
                    curUser.setPassword(password.getText().toString());
                    curUser.setEmail(email.getText().toString());
                    curUser.setAddress(address.getText().toString());
                    afterEdit();
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


    }

    public void afterEdit() {
        Intent intent = new Intent(this, LandingPage.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("pass", curUser.getPassword());
        bundle1.putString("username", curUser.getUsername());
        intent.putExtras(bundle1);
        startActivity(intent);
    }

}
