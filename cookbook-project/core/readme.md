# Core module

The core module is responsible for the logic of the cookbook app. It contains the cookbook.core package, which contains the [Cookbook](/cookbook-project/core/src/main/java/cookbook/core/Cookbook.java) and [Recipe](/cookbook-project/core/src/main/java/cookbook/core/Recipe.java) classes. These are responsible for the internal representation of the cookbook.  

Tests for the classes in UI:  
[RecipeTest](/gr2322/cookbook-project/core/src/test/java/cookbook/core/RecipeTest.java)  
[CookbookTest](/gr2322/cookbook-project/core/src/test/java/cookbook/core/CookbookTest.java)

This module has its own pom.xml file, which is responsible for building the module with its respected dependencies and plugins, such as JaCoCo, Checkstyle and Spotbugs.




