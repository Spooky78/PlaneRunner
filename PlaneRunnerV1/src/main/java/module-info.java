module com.example.planerunner {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.planerunner to javafx.fxml;
    exports com.example.planerunner;
}