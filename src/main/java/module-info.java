module com.example.fuck {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fuck to javafx.fxml;
    exports com.example.fuck;
}