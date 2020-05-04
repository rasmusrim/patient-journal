package com.rasmusrim.patient.journal.controllers;

import com.rasmusrim.patient.journal.models.Patient;
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

    public void closeForm(MouseEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void OkButtonClicked(MouseEvent event) {
        if(!isValid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Both fields as mandatory");
            alert.showAndWait();
        } else {
            Patient patient = new Patient();
            patient.setFirstName(firstName.getText());
            patient.setLastName(lastName.getText());
        }
    }

    private boolean isValid() {
        return firstName.getText().length() > 0 && lastName.getText().length() > 0;

    }

}
