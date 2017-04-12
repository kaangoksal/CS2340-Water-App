package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import gatech.water_app.R;
import gatech.water_app.model.User;

/**
 * worker landing page
 */
public class WorkerLandingPage extends LandingPage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        loginUser =(User)extras.getSerializable("user");
        setContentView(R.layout.activity_worker_landing);
        TextView text1 = (TextView) findViewById(R.id.textView3);

        text1.setText(loginUser.getEmail());


    }

    /**
     * Goes to the submit purity report page
     * @param view the view you are attempting to reach
     */
    public void addPurityReport(View view) {
        Intent intent = new Intent(this, SubmitPurityReport.class);
        Bundle bundle1 = new Bundle();
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    /**
     * Goes to the purity view page
     * @param view the view you are attempting to reach
     */
    public void startPurityView(View view) {
        Intent intent = new Intent(this, PurityView.class);
        Bundle bundle1 = new Bundle();
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

}