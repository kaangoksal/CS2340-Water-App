package gatech.water_app.model;

import java.io.IOException;

/**
 * Created by Alex Thien An Le on 2/15/2017.
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 *
 */

public class UserLoginTask {

    /**
     * attempt login of the user
     * @param halfUser the user attempting to log in
     * @return the actual user
     */
    public static User attemptLogin(User halfUser){
        try {
            return ServerConnector.attemptLogin(halfUser.getEmail(), halfUser.getPassword());
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }
//

    /**
     * attempt to add a user to database
     * @param username username
     * @param password password
     * @param address address
     * @param email email
     * @return whether they logged in
     */
    public static boolean addUser(String username, String password, String address, String email){
        try {
            return ServerConnector.addUser(username, password, address, email);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * attempt to edit user
     * @param username username
     * @param password password
     * @param email email
     * @return whether the user was edited
     */
    public static boolean attemptEditUser(String username, String password, String email) {
        try {
            return ServerConnector.editUser(username, password, email);
        } catch (IOException e) {
            return false;
        }
    }

}
