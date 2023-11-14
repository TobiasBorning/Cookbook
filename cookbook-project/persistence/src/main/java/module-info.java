module persistence {
  requires com.google.gson;
  requires transitive core;
  exports cookbook.json to ui, springboot;

  opens cookbook.json to com.google.gson;
}
