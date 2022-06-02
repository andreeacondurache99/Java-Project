module com.example.interface3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.interface3 to javafx.fxml;
    exports com.example.interface3;
    exports com.example.interface3.Authentication;
    opens com.example.interface3.Authentication to javafx.fxml;
    exports com.example.interface3.Menius;
    opens com.example.interface3.Menius to javafx.fxml;
    exports com.example.interface3.Menius.Scrollers;
    opens com.example.interface3.Menius.Scrollers to javafx.fxml;
}