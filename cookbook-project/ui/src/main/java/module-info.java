module ui {
    requires core;
    requires persistence;
    requires javafx.controls;
    requires javafx.fxml;

    opens cookbook.ui to javafx.graphics, javafx.fxml;
}
