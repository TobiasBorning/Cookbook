module persistence {
    requires com.google.gson;
    requires core;
    exports cookbook.json to ui, springboot;

    opens cookbook.json to com.google.gson;
}
