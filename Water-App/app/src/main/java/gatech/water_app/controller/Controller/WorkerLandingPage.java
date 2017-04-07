package gatech.water_app.controller.Controller;

/**
 * The landing page if you login as a worker
 * Created by John on 3/10/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import gatech.water_app.R;
import gatech.water_app.model.User;

public class WorkerLandingPage extends LandingPage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        loginUser =(User)extras.getSerializable("user");
        setContentView(R.layout.activity_worker_landing);
        TextView text1 = (TextView) findViewById(R.id.textView3);

        text1.setText(loginUser.getEmail());


    }

    /**
     * Goes to the submit purity report page
     * @param view
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
     * @param view
     */
    public void startPurityView(View view) {
        Intent intent = new Intent(this, PurityView.class);
        Bundle bundle1 = new Bundle();
        intent.putExtra("user", loginUser);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

}