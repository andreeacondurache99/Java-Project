module com.example.scroller_s {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.scroller_s to javafx.fxml;
    exports com.example.scroller_s;
}