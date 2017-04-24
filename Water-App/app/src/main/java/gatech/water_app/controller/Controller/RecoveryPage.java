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

        Log.d("log", "memes");

    }

    public void cancelButtonAction(View v) {
        finish();
    }
    public void sendEmail(View v) {
        new emailSender().execute(0);
    }

    private class emailSender extends AsyncTask<Object, User, Integer> {

        @Override
        protected Integer doInBackground(Object... params) {
            try {
                GMailSender sender = new GMailSender("team74waterapp@gmail.com", "nicememe");
                sender.sendMail("memes", "memes", "samgilson98@gmail.com", "samgilson98@gmail.com");
                Toast.makeText(RecoveryPage.this, "Mail Send Successfully.....", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

}
