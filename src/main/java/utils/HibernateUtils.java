package utils;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure( "hibernate.cfg.xml").buildSessionFactory();
        }
        catch (Throwable ex) {
            System.err.println("Creation of SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
