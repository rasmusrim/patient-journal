package com.rasmusrim.patient.journal;

import com.rasmusrim.patient.journal.models.Patient;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        createDatabase();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainForm.fxml"));
        primaryStage.setTitle("Patient journal");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void createDatabase() {

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUsername("");
        dataSourceConfig.setPassword("");
        dataSourceConfig.setUrl("jdbc:sqlite:patient-journal.sqlite");
        dataSourceConfig.setDriver("org.sqlite.JDBC");
        dataSourceConfig.setIsolationLevel(java.sql.Connection.TRANSACTION_SERIALIZABLE);

        Properties properties = new Properties();
        properties.put("ebean.db.ddl.generate", "true");
        //properties.put("ebean.db.ddl.run", "true");

        DatabaseConfig config = new DatabaseConfig();
        config.loadFromProperties(properties);
        config.setDataSourceConfig(dataSourceConfig);
        Database database = DatabaseFactory.create(config);

        Patient patient = new Patient();
        patient.setFirstName("Rasmus");
        patient.setLastName("Rasmus");
        patient.save();

        System.exit(0);
    }
}
