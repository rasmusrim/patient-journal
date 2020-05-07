package com.rasmusrim.patient.journal.controllers;

import com.rasmusrim.patient.journal.models.Patient;
import com.rasmusrim.patient.journal.services.DatabaseService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainFormController {
    @FXML
    private TextField searchQueryField;

    @FXML
    private ListView searchResultList;

    @FXML
    private Text statusBar;

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

        if (searchQueryField.getText().trim().length() > 2) {
            long start = System.currentTimeMillis();

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            searchResultList.getItems().clear();
                        }
                    });

                    statusBar.setText("Querying database...");
                    var databaseService = DatabaseService.getInstance();
                    var session = databaseService.getSession();
                    session.beginTransaction();

                    String query = searchQueryField.getText().toLowerCase();

                    List result = session.createQuery("from Patient where lower(concat(firstName, ' ', lastName)) LIKE '%" + query + "%' ORDER BY lastName, firstName").list();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Adding " + result.size() + " patients.");

                            System.out.println(statusBar);
                            int i = 0;
                            for (Patient patient : (List<Patient>) result) {
                                searchResultList.getItems().add(patient.getLastName() + ", " + patient.getFirstName());
                            }
                            statusBar.setText("Found " + result.size() + " patients matching '" + query + "' in " + (System.currentTimeMillis() - start) + "ms");
                            session.close();

                        }
                    });

                    return null;

                }
            };
            var thread = new Thread(task);
            thread.start();


        } else {
            searchResultList.getItems().clear();
            statusBar.setText("You have to enter at least 3 characters to be able to search.");

        }
    }


}
