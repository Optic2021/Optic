module com.example.optic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;


    opens com.example.optic to javafx.fxml;
    exports com.example.optic;
    exports com.example.optic.AppControllers;
    opens com.example.optic.AppControllers to javafx.fxml;
}