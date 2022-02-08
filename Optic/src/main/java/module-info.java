module com.example.optic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.optic.bean to javafx.base;
    opens com.example.optic.entities to javafx.base;
    opens com.example.optic to javafx.fxml;
    exports com.example.optic;
    exports com.example.optic.app_controllers;
    exports com.example.optic.bean;
    exports com.example.optic.entities;
    opens com.example.optic.app_controllers to javafx.fxml;
    exports com.example.optic.first_ui;
    opens com.example.optic.first_ui to javafx.fxml;
    exports com.example.optic.utilities;
    opens com.example.optic.utilities to javafx.fxml;
}