package gatech.water_app.model;

import android.util.Log;
import java.util.ArrayList;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

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


    public static boolean attemptLogin(String user, String pass) throws IOException{

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType,
//                "{\n\t\"email\" : \"kaangoksal@groopapp.com\", " +
                "{\n\t\"email\" : \""+ user + "\", " +
               // "\n\t\"password\" : \"cukubik\"," +
                        "\n\t\"password\" : \"" + pass + "\"," +
                "\n\t\"token\" : \"\"," +
                "\n\t\"username\" : \"\"\n}\n");
        Request request = new Request.Builder()
                .url("http://35.157.30.110:5235/login")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        //Yo WTF you can only check the response object once! FUCK JAVA #LAME
        //Log.d("[HTTP]", "Server Response " + response.toString() + "\n" + response.body().string());

        Log.d("[HTTP]", "Server Response " + responseString + responseString.indexOf("Successful"));
        if (responseString.indexOf("Failed") > 0) {
            return false;
        } else if (responseString.indexOf("Successful") > 0) {
            return true;
        }

       return false;
    }

    public static boolean addUser(String username, String password, String address, String email) throws IOException{
        //users.add(Math.abs(new User(username, password).hashCode()) % (users.size() + 1), new User(username, password, address, email, title));
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType,
                "{\n\t\"email\" : \""+ email +"\", " +
                        "\n\t\"password\" : \""+ password +"\"," +
                        "\n\t\"token\" : \"\"," +
                        "\n\t\"username\" : \""+username+"\"\n}\n");
        Request request = new Request.Builder()
                .url("http://35.157.30.110:5235/register")
                .post(body)
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "611c31bc-c038-0149-8e56-b45591edd8ae")
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();

        Log.d("[HTTP]", "Server Response " + responseString + responseString.indexOf("Successful"));
        if (responseString.indexOf("Failed") > 0) {
            return false;
        } else if (responseString.indexOf("Account") > 0) {
            return true;
        }

        return false;
    }

    //Connect to database to get actually user
    public static User retrieveUser(String username, String password) {
        return new User(username, password, "hello", Title.USER);
    }

    //The new object may have a new username, password, or email
    public static void editUser(User editProfile) {

    }

}
