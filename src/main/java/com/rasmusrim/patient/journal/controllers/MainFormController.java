package com.rasmusrim.patient.journal.controllers;

import com.rasmusrim.patient.journal.models.Patient;
import com.rasmusrim.patient.journal.services.DatabaseService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.h2.engine.Database;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.List;

public class MainFormController {
    @FXML
    private TextField searchField;

    @FXML
    protected void showAddNewForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PatientForm.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add new patient");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void searchForPatients(KeyEvent keyEvent) {
        if (!searchField.getText().trim().equals("")) {

            var databaseService = DatabaseService.getInstance();
            var session = databaseService.getSession();
            session.beginTransaction();

            List result = session.createQuery("from Patient where firstName LIKE '%" + searchField.getText() + "%' OR lastName LIKE '%" + searchField.getText() + "'").list();
            for (Patient patient : (List<Patient>) result) {
                System.out.println(patient.getFirstName() + " " + patient.getLastName());
            }

            session.close();
        }
    }


}
