# __Persistence module__

This module is responsible for reading and writing the cookbook to a JSON file. It uses the Gson library to serialize and deserialize the cookbook and recipe objects.
The peristence module requires access to the core module in order to function properly. The persistence module also contains the stored json files, containing the [local](/cookbook-project/persistence/cookbook.json), [remote](/cookbook-project/persistence/remote-cookbook.json), and [test](/cookbook-project/persistence/test.json) cookbooks. As well as a [default cookbook](/cookbook-project/persistence/default-cookbook.json) if a cookbook is overwritten by mistake.

---
### **Gson**
The CookbookHandler class uses the Gson library to read and write to the json file. Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. Gson converts the Cookbook and Recipe objects to a fitting JSON format. This library makes it possible to read and write to the json file without having to manually parse the json file. Therefore we only have one class(CookbookHandler) doing all the serialization and deserialization of the cookbook.

---
### _**Packages**_:
* **[cookbook.json](/cookbook-project/persistence/src/main/java/cookbook/json/)** - Contains the CookbookHandler class, which is responsible for reading and writing to the json file.

### _**Classes**_:
* **[CookbookHandler](/cookbook-project/persistence/src/main/java/cookbook/json/CookbookHandler.java)** - This class is responsible for reading and writing to the json file. It uses the Gson library to serialize and deserialize the cookbook and recipe objects.

### _**Tests**_
* [CookbookHandlerTest](/gr2322/cookbook-project/persistence/src/test/java/cookbook/json/CookbookHandlerTest.java) - Tests the CookbookHandler class. 

---
This module has its own pom.xml file, which is responsible for building the module with its respected dependencies and plugins, such as JaCoCo, Checkstyle, Spotbugs and Gson.

---
[< Return to _Cookbook Project_](/cookbook-project/readme.md)