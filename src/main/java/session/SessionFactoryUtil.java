package session;

import entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionFactoryUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        try {
            if (sessionFactory == null) {
                Configuration configuration = new Configuration();
                configuration.setProperty("hibernate.connection.url",getUrl());
                configuration.setProperty("hibernate.hbm2ddl.auto",getConfig());
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

                System.out.println(configuration.getProperties());

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

    //GETS THE CONFIG FOR HIBERNATE ACCORDING TO THE ENVIRONMENT VARIABLE (TEST OR PROD)
    public static String getUrl(){
        String configFile = "jdbc:mysql://localhost:33061/mydb";

        if (System.getProperty("test.env").equals("true")) {
            configFile = "jdbc:mysql://localhost:33061/mydb_test";
        }

        return configFile;
    }

    public static String getConfig(){
        String config = "update";

        if (System.getProperty("test.env").equals("true")) {
            config = "create-drop";
        }

        return config;
    }
}