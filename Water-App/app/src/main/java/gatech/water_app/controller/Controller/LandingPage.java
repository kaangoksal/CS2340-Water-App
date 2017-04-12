package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import gatech.water_app.R;
import gatech.water_app.model.User;

/**
 * makes the landing page
 */
public class LandingPage extends AppCompatActivity {
//    String email;
//    String password;
    User loginUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent extrasIntent = getIntent();
        Bundle extras = extrasIntent.getExtras();
//        email = extras.getString("username");
//        password = extras.getString("password");
        loginUser =(User)extras.getSerializable("user");
        assert loginUser != null;
        Log.d("LandingPage", "User received Email = " +
                loginUser.getEmail() + " " + loginUser.getPassword() );

        setContentView(R.layout.activity_logout);

        TextView text1 = (TextView) findViewById(R.id.textView3);
        text1.setText(loginUser.getEmail());

    }

    /**
     * Goes to the page where users can create new source reports
     * @param view the view you are attempting to reach
     */
    public void addSourceReport(View view) {
        Intent intent = new Intent(this, SubmitSourceReport.class);
        Bundle bundle1 = new Bundle();
//        bundle1.putString("username", email);
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    /**
     * Goes to the edit user profile page
     * @param view the view you are attempting to reach
     */
    public void startEditPage(View view) {
        Intent intent = new Intent(this, EditPage.class);
        Bundle bundle1 = new Bundle();
//        bundle1.putString("password", password);
//        bundle1.putString("email", email);
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * Goes the list of source reports page
     * @param view the view you are attempting to reach
     */
    public void startSourceView(View view) {
        Intent intent = new Intent(this, SourceView.class);
        Bundle bundle1 = new Bundle();

        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * Goes to google maps page
     * @param view the view you are attempting to reach
     */
    public void startWaterMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        Bundle bundle1 = new Bundle();
//        bundle1.putString("password", password);
//        bundle1.putString("email", email);
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * logs the person out
     * @param view view you are attempting to reach
     */
    public void logout(View view) {
        Intent intent = new Intent(this, welcomePage.class);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_left);
    }
}
