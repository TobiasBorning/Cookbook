# __Cookbook__

## _Project description_

The Cookbook is an application containing recipes, along with the ingredients needed for each respective recipe. The app will provide inspiration concerning what meals to make. On the app's homepage, the user can scroll through the list of recipes. When clicking the recipes, you get a more detailed description on how to make the dish.

As we progress through the iterations, we will further develop the app to allow users to filter recipes based on different cuisines, such as Italian og Mexican. Additionally, users will also be able to search for desired recipes.

In addition to this, users will be able to add and remove recipes from the cookbook.

## _How to run the app_
---
```bash
# navigate to the cookbook-project directory:
% cd cookbook-project

# maven install:
% mvn clean install -DskipTests

# navigate to the ui directory:
% cd ui

# run the app
% mvn javafx:run
```
---

## _Project Arcitecture_
The the project buildt using maven.  
It has three modules; _core_, _ui_ and _persistence_, each responsible for different parts of the application.  
The _core module_ contains classes responsible for the internal representation of the cookbook.  
The _ui module_ contains classes responsible for the user interface.  
The _persistence module_ is responsible for reading and writing the cookbook to a json file.  
You can read more about the different modules below.

PlantUML diagram of the project structure [here](../assets/diagram.puml)  

---

## _Modules_

### ___core___
Read about the __core__ module [here](/cookbook-project/core/readme.md)

### ___ui___
Read about the __ui__ module [here](/cookbook-project/ui/readme.md)

### ___persistence___
Read about the __persistence__ module [here](/cookbook-project/persistence/readme.md)

### ___springboot___
Read about the __springboot__ module [here](/cookbook-project/springboot/readme.md)

### ___integrationtest___
Read about the __integrationtest__ module [here](/cookbook-project/integrationtest/readme.md)

---
## _User stories_

Read about the _user stories_ [here](../docs/userstories.md)

---

