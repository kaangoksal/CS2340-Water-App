package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import gatech.water_app.R;
import gatech.water_app.controller.Controller.LoginActivity;
import gatech.water_app.controller.Controller.Registration;

/**
 * the welcome page
 */
public class welcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

    }

    /**
     * start login page
     * @param view view you are attempting to reach
     */
    public void startLoginPage(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * start registration page
     * @param view view you are attempting to reach
     */
    public void startRegistrationPage(View view) {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }

}
