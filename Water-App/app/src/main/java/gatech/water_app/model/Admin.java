package gatech.water_app.model;

/**
 * Admin class which extends User which has every getter and setter
 * Created by John on 2/22/2017.
 */

public class Admin extends Manager {
    /**
     * Constructor for Admin including everything
     *
     * @param username the admin's username
     * @param password the admin's pass
     * @param address  the admin's address
     * @param email    the admin's email
     * @param title    the admin's title
     */
    Admin(String username, String password, String address, String email, Title title) {
        super(username, password, address, email, title);
    }

    /**
     * Constructor for Admin including everything except address
     *
     * @param username the admin's username
     * @param password the admin's pass
     * @param email    the admin's email
     * @param title    the admin's title
     */
    Admin(String username, String password, String email, Title title) {
        super(username, password, email, title);
    }

    /**
     * Constructor for Admin including everything except address
     *
     * @param username the admin's username
     * @param password the admin's pass
     */
    Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void setTitle(Title title) {
        super.setTitle(title);
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public String getAddress() {
        return super.getAddress();
    }

    @Override
    public void setAddress(String address) {
        super.setAddress(address);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public Title getTitle() {
        return super.getTitle();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
