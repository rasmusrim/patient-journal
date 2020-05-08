package com.rasmusrim.patient.journal.controllers;

import com.rasmusrim.patient.journal.models.Patient;
import com.rasmusrim.patient.journal.repositories.PatientRepository;
import javafx.application.Platform;
import javafx.beans.binding.ListBinding;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainFormController {
    @FXML
    private TextField searchQueryFieldControl;

    @FXML
    private ListView searchResultListControl;
    private ObservableList<Patient> searchResultList;

    @FXML
    private Text statusBar;

    @FXML
    public void initialize() {
        searchResultList = FXCollections.observableArrayList();
        searchResultListControl.setItems(searchResultList);

    }

    @FXML
    protected void showPatientForm(Patient patient) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PatientForm.fxml"));

        if (patient == null) {
            patient = new Patient();
        }

        Parent root = loader.load();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add new patient");
        stage.setScene(new Scene(root));

        PatientFormController controller = loader.<PatientFormController>getController();
        controller.setPatient(patient);

        stage.show();
    }

    public void searchForPatients(KeyEvent keyEvent) {

        if (searchQueryFieldControl.getText().trim().length() > 2) {
            long start = System.currentTimeMillis();

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    statusBar.setText("Querying database...");

                    String query = searchQueryFieldControl.getText().toLowerCase();

                    // Do not perform query unless there has been a second since query string was changed.
                    Thread.sleep(1000);
                    if (!searchQueryFieldControl.getText().toLowerCase().equals(query)) {
                        return null;
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            searchResultListControl.getItems().clear();
                        }
                    });

                    var patientRepository = new PatientRepository();
                    var result = patientRepository.searchByName(query);

                    // Do not populate list if query has changed.
                    if (!searchQueryFieldControl.getText().toLowerCase().equals(query)) {
                        return null;
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            for (Patient patient : result) {
                                searchResultList.add(patient);
                            }

                            statusBar.setText("Found " + result.size() + " patients matching '" + query + "' in " + (System.currentTimeMillis() - start) + "ms");
                        }
                    });

                    return null;

                }
            };

            var thread = new Thread(task);
            thread.start();

        } else {
            searchResultListControl.getItems().clear();
        }
    }


    public void searchResultListClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount() == 2) {
            Patient selectedPatient = (Patient) searchResultListControl.getSelectionModel().getSelectedItems().get(0);
            showPatientForm(selectedPatient);

        }

    }

    public void newPatientClicked(MouseEvent mouseEvent) throws IOException {
        showPatientForm(null);
    }
}
