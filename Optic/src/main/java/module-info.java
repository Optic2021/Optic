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
    opens com.example.optic.app_controllers to javafx.fxml;
    exports com.example.optic.FirstUI;
    opens com.example.optic.FirstUI to javafx.fxml;
}