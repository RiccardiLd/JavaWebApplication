package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.ejb.Singleton;

/**
 * This class serves the single purpose of creating the SessionFactory Object for the entire application.
 * A SessionFactory needs only to be instantiated once, thus the Singleton annotation.
 * It is package-private, since it sees no use outside the {@code model} package.
 */
@Singleton
class HibernateSession {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            ourSessionFactory = configuration.addAnnotatedClass(User.class)
                    .addAnnotatedClass(UserSet.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Opens the session.
     * @return the {@code Session}.
     */
    static Session openSession() {
        return ourSessionFactory.openSession();
    }
}
