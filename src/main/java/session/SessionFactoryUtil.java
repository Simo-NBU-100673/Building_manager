package session;

import entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionFactoryUtil {
    //TODO somehow this must be gotten in runtime from maybe ENV variable
    //"test.hibernate.cfg.xml" : "hibernate.cfg.xml"
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        try {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(Apartment.class);
                configuration.addAnnotatedClass(Building.class);
                configuration.addAnnotatedClass(Company.class);
                configuration.addAnnotatedClass(Contract.class);
                configuration.addAnnotatedClass(Employee.class);
                configuration.addAnnotatedClass(Owner.class);
                configuration.addAnnotatedClass(Paymentshistory.class);
                configuration.addAnnotatedClass(Pet.class);
                configuration.addAnnotatedClass(Resident.class);
                configuration.addAnnotatedClass(Tax.class);
//            configuration.addAnnotatedClass(TaxPK.class);

                ServiceRegistry serviceRegistry
                        = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
        }catch (Exception e) {
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }

        return sessionFactory;
    }
}