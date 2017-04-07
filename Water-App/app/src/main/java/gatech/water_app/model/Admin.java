package gatech.water_app.model;

/**
 * Admin class which extends User which has every getter and setter
 * Created by John on 2/22/2017.
 */

class Admin extends Manager {
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

}
