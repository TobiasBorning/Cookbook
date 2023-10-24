module core {
    exports cookbook.core to persistence, ui, springboot;

    opens cookbook.core to com.google.gson;
}
 