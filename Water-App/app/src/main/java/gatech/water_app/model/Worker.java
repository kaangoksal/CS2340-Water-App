package gatech.water_app.model;

/**
 *
 * Worker class which extends User which has every getter and setter
 * Created by John on 2/22/2017.
 */

class Worker extends User {

    /**
     *
     * Constructor for Worker including everything
     * @param username the worker's username
     * @param password the worker's pass
     * @param address the worker's address
     * @param email the worker's email
     * @param title the worker's title
     */
    Worker(String username, String password, String address, String email, Title title) {
        super(username, password, address, email, title);
    }

    /**
     *
     * Constructor for Worker including everything except address
     * @param username the worker's username
     * @param password the worker's pass
     * @param email the worker's email
     * @param title the worker's title
     */
    Worker(String username, String password, String email, Title title) {
        super(username, password, email, title);
    }

    Worker(String email, String password) {
        super(email, password);
    }

}
