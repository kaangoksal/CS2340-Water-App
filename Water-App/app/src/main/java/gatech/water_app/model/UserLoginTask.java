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


    //should directly interact with the database
    //Check users and add users

    //however for current Milestone 5, this is the databases/loginsystem/EVERYTHING

    private static ArrayList<User> users = new ArrayList<>();


    public static boolean attemptLogin(String email, String password) throws IOException{

        OkHttpClient client = new OkHttpClient();

        String authentication = email + ":" + password;
        byte[] authentication_bytes = authentication.getBytes("UTF-8");
        String base64_encoded = Base64.encodeToString(authentication_bytes, Base64.DEFAULT);
        base64_encoded = "Basic " + base64_encoded;
        base64_encoded = base64_encoded.replace("\n", "");

        String bodyString = "";
        try {
            JSONObject bodyJson = new JSONObject();
            bodyJson.put("email", email);
            bodyJson.put("password", password);
            bodyJson.put("username", "");
            bodyJson.put("token", "");
            bodyString = bodyJson.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, bodyString);
        Request request = new Request.Builder()
                .url("http://umb.kaangoksal.com:5235/login")
                .post(body)
                .addHeader("Authorization", base64_encoded)
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

        String authentication = email + ":" + password;
        byte[] authentication_bytes = authentication.getBytes("UTF-8");
        String base64_encoded = Base64.encodeToString(authentication_bytes, Base64.DEFAULT);
        base64_encoded = "Basic " + base64_encoded;
        base64_encoded = base64_encoded.replace("\n", "");

        String bodyString = "";
        try {
            JSONObject bodyJson = new JSONObject();
            bodyJson.put("email", email);
            bodyJson.put("password", password);
            bodyJson.put("username", username);
            bodyJson.put("token", "");
            bodyJson.put("address", address);
            bodyString = bodyJson.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, bodyString);
        Request request = new Request.Builder()
                .url("http://umb.kaangoksal.com:5235/register")
                .post(body)
                .addHeader("Authorization", base64_encoded)
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

    public static boolean attemptEditUser(String username, String password, String email) throws IOException{

        String authentication = email + ":" + password;
        byte[] authentication_bytes = authentication.getBytes("UTF-8");
        String base64_encoded = Base64.encodeToString(authentication_bytes, Base64.DEFAULT);
        base64_encoded = "Basic " + base64_encoded;
        base64_encoded = base64_encoded.replace("\n", "");

        String bodyString = "";
        try {
            JSONObject bodyJson = new JSONObject();
            bodyJson.put("email", email);
            bodyJson.put("password", password);
            bodyJson.put("username", username);
            bodyJson.put("token", "");
            bodyString = bodyJson.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, bodyString);
        Request request = new Request.Builder()
                .url("http://umb.kaangoksal.com:5235/edit_user")
                .post(body)
                .addHeader("Authorization", base64_encoded)
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

    public static boolean addWaterReport(User user, JSONObject reportJson) throws IOException{

        String base64_encoded = parseBase64BasicAuth(user);

        String bodyString = reportJson.toString();

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, bodyString);
        Request request = new Request.Builder()
                .url("http://umb.kaangoksal.com:5235/add_water_report")
                .post(body)
                .addHeader("Authorization", base64_encoded)
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

    public static String parseBase64BasicAuth(User user)  {
        String authentication = user.getEmail() + ":" + user.getPassword();

        byte[] authentication_bytes;
        try {
            authentication_bytes = authentication.getBytes("UTF-8");
        } catch (IOException E){
            authentication_bytes ="Error".getBytes();
        }
        String base64_encoded = Base64.encodeToString(authentication_bytes, Base64.DEFAULT);
        base64_encoded = "Basic " + base64_encoded;
        base64_encoded = base64_encoded.replace("\n", "");
        return base64_encoded;
    }


}
