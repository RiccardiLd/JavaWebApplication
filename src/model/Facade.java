package model;

import javax.ejb.Stateless;

@Stateless
public class Facade {
    private DataAccess dataAccess;

    public Facade() {
        dataAccess = new DataAccess();
    }

    /**
     * Calls the {@code DataAccess createUser()} function and verifies if the creation of a new user was successful.
     * Called if the user clicks on "Sign Up" in the sign in or sign up page.
     * @param email the email provided by the user.
     * @param password the password provided by the user.
     * @param passwordConfirmation the password confirmation provided by the user.
     * @return {@code True} if user was created successfully. {@code False} otherwise.
     */
    public boolean newUser(String email, String password, String passwordConfirmation) {
        User user = getUserByEmail(email, false);
        boolean userExists = (user != null);
        try {
            String hash = Password.getSaltedHash(password);
            user = dataAccess.createUser(email, hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user != null
                && !userExists
                && password.equals(passwordConfirmation);
    }

    /**
     * Verifies the user's authenticity by checking if passwords match and email is on the database.
     * Called when the user clicks on "Sign In" in the sign in or sign up page.
     * @param email the email provided by the user.
     * @param password the password provided by the user.
     * @return {@code True} if user exists and has provided the correct password. {@code False} otherwise.
     */
    public boolean authenticateUser(String email, String password) {
        // Set login to true, user is logging in here.
        User user = dataAccess.getUserByEmail(email, true);
        boolean match = false;
        try {
            match = Password.check(password, user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user != null
                && match
                && user.getEmail().equals(email);
    }

    /**
     * Calls the {@code DataAccess getUserByEmail()} function to retrieve an instance of {@code User} from the database.
     * @param email the email of the user.
     * @param login will be {@code True} if the user is logging in. {@code False} otherwise.
     * @return the user.
     */
    public User getUserByEmail(String email, boolean login) {
        return dataAccess.getUserByEmail(email, login);
    }

    /**
     * Calls the {@code DataAccess userAddSet()} function to add a Key/Value pair associated with the user to the database.
     * @param email the email of the user.
     * @param key The Key of the Key/Value pair.
     * @param value The Value of the Key/Value pair.
     * @return {@code true} if the operation was completed successfully, {@code false} otherwise.
     */
    public boolean userAddSet(String email, String key, String value) {
        return dataAccess.userAddSet(email, key, value);
    }

    /**
     * Calls the {@code DataAccess userRemoveSet()} function to delete one of the user"s Key/Value pair from the database.
     * @param email the email of the user.
     * @param key The Key of the Key/Value pair.
     * @param value The Value of the Key/Value pair.
     * @return {@code true} if the operation was completed successfully, {@code false} otherwise.
     */
    public boolean userRemoveSet(String email, String key, String value) {
        return dataAccess.userRemoveSet(email, key, value);
    }

    /**
     * Calls the {@code DataAccess deleteUser()} function to delete a user from the database.
     * @param email the email of the user who will be removed.
     */
    public void deleteUser(String email) {
        dataAccess.deleteUser(email);
    }
}
