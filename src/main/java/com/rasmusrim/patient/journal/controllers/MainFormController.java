package com.rasmusrim.patient.journal.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainFormController {
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
}
