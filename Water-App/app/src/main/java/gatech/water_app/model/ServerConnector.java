package gatech.water_app.model;

import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Alex Thien An Le on 2/23/2017.
 * Modified by Kaan Goksal
 * This file has the http requests to communicate with the server.
 */

public class ServerConnector {

    public static User attemptLogin(String email, String password) throws IOException {

        OkHttpClient client = new OkHttpClient();

        String authentication = email + ":" + password;
        byte[] authentication_bytes = authentication.getBytes("UTF-8");
        String base64_encoded = Base64.encodeToString(authentication_bytes, Base64.DEFAULT);
        base64_encoded = "Basic " + base64_encoded;
        base64_encoded = base64_encoded.replace("\n", "");

        String bodyString = "";
//        try {
//            JSONObject bodyJson = new JSONObject();
//            bodyJson.put("email", email);
//            bodyJson.put("password", password);
//            bodyJson.put("username", "");
//            bodyJson.put("token", "");
//            bodyString = bodyJson.toString();
//
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }

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

        Log.d("ServerConnector", "Server returned response for attemp login = " +responseString );
        JSONObject received;
        User retrieveduser = null;
        try {

            received = new JSONObject(responseString);
            retrieveduser = new User(received.getString("email"), received.getString("password"));
            retrieveduser.setUsername(received.getString("username"));

            received.getString("created_at");

            String title =  received.getString("account_type");
            if (title.equals("User") || title.equals("user")){
                retrieveduser.setTitle(Title.USER);
            } else if (title.equals("Worker") || title.equals("worker")) {
                retrieveduser.setTitle(Title.WORKER);
            } else if (title.equals("Admin") || title.equals("admin")) {
                retrieveduser.setTitle(Title.ADMIN);
            } else if (title.equals("Manager") || title.equals("manager")){
                retrieveduser.setTitle(Title.MANAGER);
            }

            Log.d("ServerConnector", "JsonObj " + received.toString());

        } catch (JSONException E){
            Log.d("ServerConnector", "get Reports Json problem! " +responseString + E.getMessage() + " " +E.getLocalizedMessage() + " " + E.toString() );
        }

        return retrieveduser;

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

    public static boolean editUser(String username, String password, String email) throws IOException{

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

    public static boolean addReport(User user, JSONObject reportJson) throws IOException{

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

    public static JSONArray getReports(User user) throws IOException{

        String base64_encoded = parseBase64BasicAuth(user);

        String bodyString = "";

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, bodyString);
        Request request = new Request.Builder()
                .url("http://umb.kaangoksal.com:5235/get_water_reports")
                .post(body)
                .addHeader("Authorization", base64_encoded)
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        //Yo WTF you can only check the response object once! FUCK JAVA #LAME
        //Log.d("[HTTP]", "Server Response " + response.toString() + "\n" + response.body().string());
        Log.d("ServerConnector", "Server returned response for get reports = " +responseString );
        JSONObject received;
        JSONArray returnarray = null;
        try {
            received = new JSONObject(responseString);
            Log.d("ServerConnector", "JsonObj " + received.toString());
            returnarray = received.getJSONArray("reports");
        } catch (JSONException E){
            Log.d("ServerConnector", "get Reports Json problem! " +responseString + E.getMessage() + " " +E.getLocalizedMessage() + " " + E.toString() );
        }


        return returnarray;

    }

    public static ArrayList<WaterSourceReport> getSourceReports(User user) throws IOException{

        String base64_encoded = parseBase64BasicAuth(user);

        String bodyString = "";

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, bodyString);
        Request request = new Request.Builder()
                .url("http://umb.kaangoksal.com:5235/get_water_source_reports")
                .post(body)
                .addHeader("Authorization", base64_encoded)
                .build();

        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        //Yo WTF you can only check the response object once! FUCK JAVA #LAME
        //Log.d("[HTTP]", "Server Response " + response.toString() + "\n" + response.body().string());
        Log.d("ServerConnector", "Server returned response for get reports = " +responseString );
        JSONObject received;
        JSONArray jsonArray = null;
        ArrayList<WaterSourceReport> returnarray = new ArrayList<WaterSourceReport>();
        try {
            received = new JSONObject(responseString);
            Log.d("ServerConnector", "JsonObj " + received.toString());
            jsonArray = received.getJSONArray("reports");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject reportJsonChild = jsonArray.getJSONObject(i);

                returnarray.add(WaterSourceReport.fromJSONObject(reportJsonChild));
                Log.e("SourceView", "Populating the list " + reportJsonChild.toString());
            }

        } catch (JSONException E){
            Log.d("ServerConnector", "get Reports Json problem! " +responseString + E.getMessage() + " " +E.getLocalizedMessage() + " " + E.toString() );
        }


        return returnarray;

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
