package gatech.water_app.model;

/**
 * Manager class which extends User which has every getter and setter
 * Created by John on 2/22/2017.
*/

class Manager extends Worker {
    /**
     * Constructor for Manager including everything
     *
     * @param username the manager's username
     * @param password the manager's pass
     * @param address  the manager's address
     * @param email    the manager's email
     * @param title    the manager's title
     */
    Manager(String username, String password, String address, String email, Title title) {
        super(username, password, address, email, title);
    }

    /**
     * Constructor for Manager including everything except address
     *
     * @param username the manager's username
     * @param password the manager's pass
     * @param email    the manager's email
     * @param title    the manager's title
     */
    Manager(String username, String password, String email, Title title) {
        super(username, password, email, title);
    }

    /**
     * Constructor for Manager including everything except address
     *
     * @param username the manager's username
     * @param password the manager's pass
     */
    Manager(String username, String password) {
        super(username, password);
    }

}
