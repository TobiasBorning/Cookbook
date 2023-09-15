module ui {
    requires core;
    requires javafx.controls;
    requires javafx.fxml;

    opens cookbook.ui to javafx.graphics, javafx.fxml;
}
