module com.example.interface3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.interface3 to javafx.fxml;
    exports com.example.interface3;
}