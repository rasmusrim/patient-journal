package com.rasmusrim.patient.journal.controllers;

import com.rasmusrim.patient.journal.models.Patient;
import com.rasmusrim.patient.journal.repositories.PatientRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class PatientFormController {
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    private Patient patient;
    private MainFormController mainFormController;

    public void closeForm(MouseEvent event) {
        mainFormController.searchForPatients();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

    public void OkButtonClicked(MouseEvent event) {
        System.out.println("Patient: " + patient);

        if(!isValid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Both fields as mandatory");
            alert.showAndWait();
        } else {
            patient.setFirstName(firstName.getText());
            patient.setLastName(lastName.getText());

            var patientRepository = new PatientRepository();
            patientRepository.persist(patient);
            closeForm(event);

        }
    }

    private boolean isValid() {
        return firstName.getText().length() > 0 && lastName.getText().length() > 0;

    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        firstName.setText(patient.getFirstName());
        lastName.setText(patient.getLastName());
    }

    public void setMainFormController(MainFormController mainFormController) {
        this.mainFormController = mainFormController;
    }
}
