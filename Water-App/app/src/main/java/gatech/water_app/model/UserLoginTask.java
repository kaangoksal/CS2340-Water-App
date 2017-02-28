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

    public static void addUser(String username, String password, String address, String email, Title title) {
        users.add(Math.abs(new User(username, password).hashCode()) % (users.size() + 1), new User(username, password, address, email, title));
    }

    public static User retrieveUser(String username, String password) {
        int hashcode = new User(username, password).hashCode();
        hashcode = Math.abs(hashcode) % (users.size());
        return users.get(hashcode);

    }


}