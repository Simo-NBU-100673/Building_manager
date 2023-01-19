package session;

import entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionFactoryUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
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

        return sessionFactory;
    }
}