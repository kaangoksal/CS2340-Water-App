package gatech.water_app.model;
import java.util.List;
import java.util.Arrays;
/**
 * Created by Alex Thien An Le on 2/20/2017.
 */

public class User {

    public static List<String> titles = Arrays.asList("Administrator","Worker","Manager","User");

    private String username;
    private String password;
    private String address;
    private String email;
    private String title;

    public User(String username, String password, String address, String email, String title) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.email = email;
        this.title = title;
    }

    User(String username, String password, String email, String title) {
        this(username, password, null, email, title);
    }

    User(String username, String password) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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
