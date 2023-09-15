module core {
    requires com.google.gson;
    exports cookbook.core;
    exports cookbook.json;

    opens cookbook.core to com.google.gson;
}
