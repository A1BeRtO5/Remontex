module com.example.programm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.programm to javafx.fxml;
    exports com.example.programm;
}