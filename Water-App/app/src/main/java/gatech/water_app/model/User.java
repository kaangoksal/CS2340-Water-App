package gatech.water_app.model;
import java.util.List;
import java.util.Arrays;
/**
 * Created by Alex Thien An Le on 2/20/2017. Edited by John Ho 2/22/2017
 * Standard user class with all getters and setters available
 */

public class User {

    public static List<String> titles = Arrays.asList("Administrator","Worker","Manager","User");

    private String username;
    private String password;
    private String address;
    private String email;
    private Title title;

    /**
     *
     * Constructor for User including everything
     * @param username the user's username
     * @param password the user's pass
     * @param address the user's address
     * @param email the user's email
     * @param title the user's title
     */
    public User(String username, String password, String address, String email, Title title) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.email = email;
        this.title = title;
    }

    /**
     *
     * Constructor for User including everything except address
     * @param username the user's username
     * @param password the user's pass
     * @param email the user's email
     * @param title the user's title
     */
    public User(String username, String password, String email, Title title) {
        this(username, password, null, email, title);
    }

    public User(String username, String password) {
        this(username, password, null, null);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!password.equals(user.password)) return false;
        return username.equals(user.username);

    }

    @Override
    public int hashCode() {
        int result = password.hashCode();
        result = 31 * result + username.hashCode();
        return result;
    }

}