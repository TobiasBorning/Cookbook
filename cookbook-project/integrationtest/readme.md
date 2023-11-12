# Integrationtest module

This module is responsile for running an integration test. It ensures that the modules work together as expected. The test fills a cookbook with three recipes, then writes it to the [remote-cookbook.json](/cookbook-project/persistence/remote-cookbook.json) file. After that, it reads the cookbook from the file, fetches it from the API, UI and through the CookbookAccess object linked to the UI controller. At last, it checks that the recipes are the same as the ones that were written to the file.

---
```bash
# To run the integration test, run the following command in the integrationtest directory:

mvn verify
```
---
### Plugins
The Spring Boot Maven plugin is used to start the application in the pre-integration-test phase, and stop it in the post-integration-test phase. The test is run by the Failsafe Maven plugin in the integration-test phase.

---
### Link to the integration test class:  
[CookbookAppIT](/cookbook-project/integrationtest/src/test/java/cookbook/integrationtest/CookbookAppIT.java) - Test that the modules work together as expected. 

---
This module has its own pom.xml file, which is responsible for building the module with its respected dependencies and plugins, such as JaCoCo, Checkstyle and Spotbugs, as well as the Spring Boot Maven plugin and the Failsafe Maven plugin.

[< Return to _Cookbook Project_](/cookbook-project/readme.md)



