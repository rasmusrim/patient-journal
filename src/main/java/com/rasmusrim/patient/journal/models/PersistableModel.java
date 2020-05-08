package com.rasmusrim.patient.journal.models;

import com.rasmusrim.patient.journal.services.DatabaseService;
import org.hibernate.Transaction;

abstract public class PersistableModel {
    abstract public long getId();

    public void save() {

    }
}
