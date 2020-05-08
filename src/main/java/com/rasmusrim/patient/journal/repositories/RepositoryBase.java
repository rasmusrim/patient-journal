package com.rasmusrim.patient.journal.repositories;

import com.rasmusrim.patient.journal.models.PersistableModel;
import com.rasmusrim.patient.journal.services.DatabaseService;
import org.hibernate.Transaction;

public class RepositoryBase {
    public PersistableModel persist(PersistableModel entity) {
        var databaseService = DatabaseService.getInstance();
        var session = databaseService.getSession();

        if (entity.getId() == 0) {
            session.persist(entity);
        } else {
            session.merge(entity);

        }

        Transaction transact = session.beginTransaction();
        transact.commit();
        session.close();

        return entity;
    }

}
