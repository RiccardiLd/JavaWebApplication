package model;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * The DataAccess class. Responsible for opening a hibernate session and communicating with the database.
 */
class DataAccess {
    /**
     * Creates a new user from the inputs.
     * @param email the email provided by the user.
     * @param password the password provided by the user.
     * @return the created user.
     */
    User createUser(String email, String password) {
        Transaction transaction = null;
        User user = null;
        try (Session session = HibernateSession.openSession()) {
            transaction = session.beginTransaction();
            user = new User(email, password);
            // Without this next line, the lazy initialization of user_set will fail.
            Hibernate.initialize(user.getUserSet());
            // When the user is created we automatically sign them in.
            user.setLastSignIn();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Deletes a user from the database.
     * @param email the email of the user who will be removed.
     */
    void deleteUser(String email) {
        Transaction transaction = null;
        try (Session session = HibernateSession.openSession()) {
            transaction = session.beginTransaction();
            // Set the login to false, the user is not logging in here.
            User user = getUserByEmail(email, false);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an instance of {@code User} from the database.
     * @param email the email of the user.
     * @param login will be {@code True} if the user is logging in. {@code False} otherwise.
     * @return the user if user exists, {@code null} otherwise.
     */
    User getUserByEmail(String email, boolean login) {
        User user = null;
        try (Session session = HibernateSession.openSession()) {
            Transaction transaction = session.beginTransaction();
            user = session.get(User.class, email);
            // Without this next line, the lazy initialization of user_set will fail.
            Hibernate.initialize(user.getUserSet());
            // Is the user logging in?
            if (login) {
                user.setLastSignIn();
            }
            transaction.commit();
        } catch (NullPointerException ignored) {
            /* User does not exist in the database. */
            /* No transaction.rollback() required as there were no changes in the database. */
            /* Do nothing. */
        }
        return user;
    }

    /**
     * Adds a Key/Value pair to the user's set.
     * @param email the email of the user.
     * @param key The Key of the Key/Value pair.
     * @param value The Value of the Key/Value pair.
     * @return {@code true} if the operation was completed successfully, {@code false} otherwise.
     */
    boolean userAddSet(String email, String key, String value) {
        Transaction transaction = null;
        boolean success = false;
        try (Session session = HibernateSession.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, email);
            success = user.addUserSet(key, value);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return success;
    }


    /**
     * Removes a Key/Value pair from the user's set.
     * @param email the email of the user.
     * @param key The Key of the Key/Value pair.
     * @param value The Value of the Key/Value pair.
     * @return {@code true} if the operation was completed successfully, {@code false} otherwise.
     */
    boolean userRemoveSet(String email, String key, String value) {
        Transaction transaction = null;
        boolean success = false;
        try (Session session = HibernateSession.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, email);
            success = user.removeUserSet(key, value);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return success;
    }
}
