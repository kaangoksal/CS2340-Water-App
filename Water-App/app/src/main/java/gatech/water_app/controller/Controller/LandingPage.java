package gatech.water_app.controller.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import gatech.water_app.R;

public class LandingPage extends AppCompatActivity {
    String username;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        password = extras.getString("pass");
        setContentView(R.layout.activity_logout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView text1 = (TextView) findViewById(R.id.textView3);
        text1.setText("Hello " + username);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void loginout(View view) {
        Intent intent = new Intent(this, welcomePage.class);
        startActivity(intent);
    }

    public void startEditPage(View view) {
        Intent intent = new Intent(this, EditPage.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("pass", password);
        bundle1.putString("username", username);
        intent.putExtras(bundle1);
        startActivity(intent);
    }



}
