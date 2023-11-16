# __GR2322 - IT901 - Cookbook__

This is group 22's IT1901 project. We have made a cookbook app that helps people store and organize their recipes.  
More info about the cookbook project can be found [here](/cookbook-project/readme.md). 

### **_Technologies used_**
- [**_Java_ 17.0.8**](https://www.oracle.com/java/technologies/downloads/#java17)
- [**_Maven_ 3.9.4**](https://maven.apache.org/download.cgi)
- [**_JUnit_ 5.10.0**](https://junit.org/junit5/docs/current/user-guide/)
- [**_JaCoCo_ 0.8.10**](https://mvnrepository.com/artifact/org.jacoco/jacoco-maven-plugin)
- [**_Checkstyle_ 10.3.4**](https://mvnrepository.com/artifact/com.puppycrawl.tools/checkstyle)
- [**_Spotbugs_ 4.7.3.6**](https://mvnrepository.com/artifact/com.github.spotbugs/spotbugs)
- [**_Gson_ 2.9.1**](https://mvnrepository.com/artifact/com.google.code.gson/gson)
- [**_JavaFX_ 17.0.8**](https://mvnrepository.com/artifact/org.openjfx)
- [**_Failsafe_ 3.0.0-M1**](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-failsafe-plugin)
- [**_Spring Boot_ 3.1.4**](https://mvnrepository.com/search?q=spring+boot)
- [**_Jpackage_ 1.4.0**](https://github.com/petr-panteleyev/jpackage-maven-plugin)

<div style="display: flex; align-items: center;">
    <a href="https://che.stud.ntnu.no/#https:/gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2322/gr2322">
        <img src="assets/eclipse-che.png" width="90" height="50"/>
    </a>
    <h3>Open with che</h3>
</div>

---

## _Release Documentation_
__[- Release 1 -](docs/release1/release1.md)  
[- Release 2 -](docs/release2/release2.md)  
[- Release 3 -](docs/release3/release3.md)__

---

## _Running the application_
A guide on how to run the application can be found in the _cookbook-project_ [readme file](/cookbook-project/readme.md)

---

## _Testing_
This project uses JUNIT 5 for testing.  
To run the tests follow the steps below:
```bash
# make sure you are in the cookbook-project directory
cd cookbook-project #(if not)

# run all tests (including integration test)
mvn clean install

# if you want to run tests in a specific module, navigate to a desired directory:
cd ui 
# or
cd core
# or
cd persistence
# or 
cd springboot

# run tests
mvn test

# to run integration test
cd integrationtest
# run test
mvn verify

```
Test coverage reports can be found in the `target/site/jacoco` directory inside each module(except for the integration test module which has no classes). Our approach to testing has been to use JaCoCo to get overview of which methods are being covered by the tests. We aim to cover all methods and scenarios critical to the app's functionality. This will result in a high degree of test coverage, but not neccessarily 100%.
More information about the individual test are found inside the module readme files.


## _Contributors_
---
Aurora Johansen  
Birk Strand Bjørnaa  
Hanna Jacobsen  
Tobias Borning

