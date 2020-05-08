package com.rasmusrim.patient.journal.repositories;

import com.rasmusrim.patient.journal.models.Patient;
import com.rasmusrim.patient.journal.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class PatientRepository extends RepositoryBase {
    public List<Patient> searchByName(String query) {
        var databaseService = DatabaseService.getInstance();
        var session = databaseService.getSession();
        session.beginTransaction();

        String clauses = makeClauses(query);

        var result = session.createQuery("from Patient where " + clauses + " ORDER BY lastName, firstName").list();
        session.close();

        return result;
    }

    private String makeClauses(String query) {
        var wordsInQuery = query.split(" ");

        var clauses = new ArrayList<String>();
        for (var word : wordsInQuery) {
            clauses.add("(lower(firstName) LIKE '" + word + "%' OR lower(lastName) LIKE '" + word + "%')");
        }

        return String.join(" AND ", clauses);

    }


}
