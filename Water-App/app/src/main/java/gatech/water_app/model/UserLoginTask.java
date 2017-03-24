package gatech.water_app.model;

import android.util.Log;
import java.util.ArrayList;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alex Thien An Le on 2/15/2017.
 */

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask {

    public static User attemptLogin(User halfUser){
        try {

            return ServerConnector.attemptLogin(halfUser.getEmail(), halfUser.getPassword());
        } catch (IOException e) {
            return null;
        }
    }
//
    public static boolean addUser(String username, String password, String address, String email){
        try {
            return ServerConnector.addUser(username, password, address, email);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean attemptEditUser(String username, String password, String email) {
        try {
            return ServerConnector.editUser(username, password, email);
        } catch (IOException e) {
            return false;
        }
    }

}
