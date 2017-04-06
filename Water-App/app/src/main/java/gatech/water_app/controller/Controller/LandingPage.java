package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import gatech.water_app.R;
import gatech.water_app.model.User;

public class LandingPage extends AppCompatActivity {
//    String email;
//    String password;
    User loginUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
//        email = extras.getString("username");
//        password = extras.getString("password");
        loginUser =(User)extras.getSerializable("user");
        Log.d("LandingPage", "User received Email = " + loginUser.getEmail() + " " + loginUser.getPassword() );

        setContentView(R.layout.activity_logout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView text1 = (TextView) findViewById(R.id.textView3);

        text1.setText("Hello " + loginUser.getEmail());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * Goes to the page where users can create new source reports
     * @param view
     */
    public void addSourceReport(View view) {
        Intent intent = new Intent(this, SubmitSourceReport.class);
        Bundle bundle1 = new Bundle();
//        bundle1.putString("username", email);
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    /** Logs the user out and returns them to the homepage
    public void loginout(View view) {
        Intent intent = new Intent(this, welcomePage.class);
        startActivity(intent);
    }

    /**
     * Goes to the edit user profile page
     * @param view
     */
    public void startEditPage(View view) {
        Intent intent = new Intent(this, EditPage.class);
        Bundle bundle1 = new Bundle();
//        bundle1.putString("password", password);
//        bundle1.putString("email", email);
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    /**
     * Goes the list of source reports page
     * @param view
     */
    public void startSourceView(View view) {
        Intent intent = new Intent(this, SourceView.class);
        Bundle bundle1 = new Bundle();

        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    /**
     * Goes to google maps page
     * @param view
     */
    public void startWaterMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        Bundle bundle1 = new Bundle();
//        bundle1.putString("password", password);
//        bundle1.putString("email", email);
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, welcomePage.class);
        startActivity(intent);
    }
}
