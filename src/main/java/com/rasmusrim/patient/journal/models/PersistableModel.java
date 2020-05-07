package com.rasmusrim.patient.journal.models;

import com.rasmusrim.patient.journal.services.DatabaseService;
import org.hibernate.Transaction;

abstract public class PersistableModel {
    public void save() {
        var databaseService = DatabaseService.getInstance();
        var session = databaseService.getSession();

        session.persist(this);
        Transaction transact = session.beginTransaction();
        transact.commit();
        session.close();

    }
}
