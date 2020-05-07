package com.rasmusrim.patient.journal;

import com.rasmusrim.patient.journal.models.Patient;
import com.rasmusrim.patient.journal.services.DatabaseService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import javax.xml.crypto.Data;
import java.util.EnumSet;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainForm.fxml"));
        primaryStage.setTitle("Patient journal");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        DatabaseService.createConnection();
        addDummyData();

    }

    private void addDummyData() {

        Patient patient = new Patient();
        patient.setFirstName("Rasmus");
        patient.setLastName("Rimestad");

        patient.save();


    }

    public static void main(String[] args) {
        launch(args);
    }


}
