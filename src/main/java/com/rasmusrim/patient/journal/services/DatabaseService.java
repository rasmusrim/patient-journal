package com.rasmusrim.patient.journal.services;

import com.rasmusrim.patient.journal.models.Patient;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DatabaseService {
    private static DatabaseService instance = null;
    private SessionFactory sessionFactory;

    // private constructor restricted to this class itself
    private DatabaseService() {
        Configuration conf = new Configuration().configure(getClass().getResource("/hibernate.cfg.xml"));
        conf.addAnnotatedClass(Patient.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();

        sessionFactory = conf.buildSessionFactory(serviceRegistry);
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    // static method to create instance of Singleton class
    public static DatabaseService getInstance() {
        if (instance == null)
            instance = new DatabaseService();

        return instance;
    }
}
