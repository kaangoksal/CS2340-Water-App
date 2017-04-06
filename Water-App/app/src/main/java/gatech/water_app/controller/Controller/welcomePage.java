package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import gatech.water_app.R;
import gatech.water_app.controller.Controller.LoginActivity;
import gatech.water_app.controller.Controller.Registration;

public class welcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

    }

    public void startLoginPage(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void startRegistrationPage(View view) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

}
