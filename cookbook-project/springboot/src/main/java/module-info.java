module springboot {
    requires com.google.gson;
    requires core;
    requires persistence;
    
    
    requires spring.web;
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.boot.autoconfigure;

    opens cookbook.springboot to spring.web, spring.beans, spring.context; 
}
