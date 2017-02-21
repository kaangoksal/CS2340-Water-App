package gatech.water_app.controller.Controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gatech.water_app.R;


import gatech.water_app.model.UserLoginTask;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button signInButton;
    Button cancelButton;
    int counterAttempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        username = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passwordInput);
        signInButton = (Button) findViewById(R.id.sign_in_button);
        cancelButton = (Button) findViewById(R.id.login_cancel);
        counterAttempt = 5;

        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserLoginTask.attemptLogin(username.getText().toString(), password.getText().toString())) {
                    //ENTER THE SYSTEM
                    afterLogin();
                } else {
                    counterAttempt--;
                    Toast.makeText(getApplicationContext(), "Incorrect Credentials, Attempts Left " + counterAttempt, Toast.LENGTH_SHORT).show();

                    if (counterAttempt == 0) {
                        signInButton.setEnabled(false);
                    }
                }
            }
        });
        }

    public void cancelButtonAction(View v) {
        finish();
    }

    private void afterLogin() {
        Intent intent = new Intent(this, Logout.class);
        startActivity(intent);
    }



}

