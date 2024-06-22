package mas.lms.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utility class for managing Hibernate sessions.
 */
public class HibernateUtil {

    // Static instance of SessionFactory, created only once
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Builds the SessionFactory from the hibernate.cfg.xml configuration file.
     *
     * @return The built SessionFactory.
     */
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception to indicate that the initial SessionFactory creation failed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Returns the singleton SessionFactory instance.
     *
     * @return The singleton SessionFactory instance.
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Shuts down the Hibernate session factory.
     * Closes caches and connection pools.
     */
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
