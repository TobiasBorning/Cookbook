module ui {
    requires core;
    requires persistence;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;

    opens cookbook.ui to javafx.graphics, javafx.fxml;
}
