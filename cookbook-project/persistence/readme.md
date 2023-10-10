# Persistence module

This module is responsible for reading and writing the cookbook to a JSON file. It contains the cookbook.json package, which contains the [CookbookHandler](/cookbook-project/persistence/src/main/java/cookbook/json/CookbookHandler.java) class. This class is responsible for reading and writing to the internal representation of the cookbook to the [_cookbook.json_](/cookbook-project/persistence/cookbook.json) file, located in the root of this module.  
The peristence module requires access to the core module in order to function properly.

### Gson
The CookbookHandler class uses the Gson library to read and write to the json file. Gson is a Java library that can be used to convert Java Objects into their JSON representation. It can also be used to convert a JSON string to an equivalent Java object. Gson converts the Cookbook and Recipe objects to a fitting JSON format. This library makes it possible to read and write to the json file without having to manually parse the json file. Therefore we only have one class(CookbookHandler) doing all the serialization and deserialization of the cookbook.

The module also contains tests for all the CookbookHandler class, located inside the [test](/cookbook-project/persistence/src/test/) folder.

This module has its own pom.xml file, which is responsible for building the module with its respected dependencies and plugins, such as JaCoCo, Checkstyle, Spotbugs and Gson.