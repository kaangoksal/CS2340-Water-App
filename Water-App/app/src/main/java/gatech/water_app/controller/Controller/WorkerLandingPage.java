package gatech.water_app.controller.Controller;

/**
 * The landing page if you login as a worker
 * Created by John on 3/10/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import gatech.water_app.R;

public class WorkerLandingPage extends LandingPage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");
        password = extras.getString("password");
        setContentView(R.layout.activity_worker_landing);
        TextView text1 = (TextView) findViewById(R.id.textView3);

        text1.setText("Hello " + email);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public void addPurityReport(View view) {
        Intent intent = new Intent(this, SubmitPurityReport.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("email", email);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

    public void startPurityView(View view) {
        Intent intent = new Intent(this, PurityView.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("password", password);
        bundle1.putString("email", email);
        intent.putExtras(bundle1);
        startActivity(intent);
    }

}