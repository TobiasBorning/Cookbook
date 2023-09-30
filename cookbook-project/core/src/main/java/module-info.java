module core {
    exports cookbook.core to persistence, ui;

    opens cookbook.core to com.google.gson;
}
 