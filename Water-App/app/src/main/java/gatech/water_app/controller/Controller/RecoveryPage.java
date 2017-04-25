package gatech.water_app.controller.Controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gatech.water_app.R;
import gatech.water_app.model.ServerConnector;
import gatech.water_app.model.User;
import gatech.water_app.model.GMailSender;

public class RecoveryPage extends Activity {
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        email = (EditText) findViewById(R.id.emailEditText);
        Button submitButton = (Button) findViewById(R.id.submitButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable emailText = email.getText();
                sendEmail(emailText.toString());
                Toast.makeText(RecoveryPage.this, "Mail Send Successfully.....", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void cancelButtonAction(View v) {
        finish();
    }
    public void sendEmail(String email) {
        new emailSender().execute(email);
    }

    private class emailSender extends AsyncTask<Object, User, Integer> {

        @Override
        protected Integer doInBackground(Object... params) {
            try {
                User retrieved = ServerConnector.getUserPassword((String) params[0]);
                GMailSender sender = new GMailSender("team74waterapp@gmail.com", "nicememe");
                sender.sendMail("Your Password", retrieved.getPassword(), "team74waterapp@gmail.com", (String) params[0]);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

}
