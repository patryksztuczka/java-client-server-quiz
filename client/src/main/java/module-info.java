module com.company.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.company.client to javafx.fxml;
    exports com.company.client;
}