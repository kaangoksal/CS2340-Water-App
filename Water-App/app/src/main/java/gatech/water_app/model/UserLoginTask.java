package gatech.water_app.model;

import java.util.ArrayList;

/**
 * Created by Alex Thien An Le on 2/15/2017.
 */

import android.os.AsyncTask;

import gatech.water_app.R;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask {


    //should directly interact with the database
    //Check users and add users





    //however for current Milestone 5, this is the databases/loginsystem/EVERYTHING

    private static ArrayList<User> users = new ArrayList<>();


    public static boolean attemptLogin(String user, String pass) {
        return users.contains(new User(user, pass));
    }

    public static void addUser(String username, String password, String address, String email, String title) {
        users.add(new User(username, password, address, email, title));
    }


}
