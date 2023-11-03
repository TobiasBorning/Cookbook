# Ui module

The ui module is responsible for the application's frontend. It is built using JavaFX and FXML. It uses FXML files and their respected controller for navigating between scenes. The frontend is responsible for displaying the recipes in the cookbook, adding a recipe, editing a recipe and deleting a recipe. The ui module requires access to the core, springboot and persistence module in order to function properly. It is set up to work with either a localy stored cookbook file or using requests to a REST-API.

---
### _**Packages**_:
* **[cookbook.ui](/cookbook-project/ui/src/main/java/cookbook/ui/)** - Contains the controllers for the different scenes in the application. As well as the main class for running the application.
* **[coookbook.accessdata](/cookbook-project/ui/src/main/java/cookbook/ui/)** - Contains the logic for reading and writing to the json file. This package contains two classes, one for reading and writing to a local json file and one for reading and writing to a remote json file. Both implementing the same interface for use in the controllers.

### _**Classes**_:
* **[CookbookApp](/cookbook-project/ui/src/main/java/cookbook/ui/CookbookApp.java)** - This class is responsible for running the application.
* **[AppController](/cookbook-project/ui/src/main/java/cookbook/ui/AppContoller.java)** - This class is responsible for setting up the application and switching between scenes. Linked to the [App.fxml](/cookbook-project/ui/src/main/resources/App.fxml) file.
* **[AddRecipeController](/cookbook-project/ui/src/main/java/cookbook/ui/AddRecipeContoller.java)** - This class is responsible for adding a recipe to the cookbook. Linked to the [AddRecipe.fxml](/cookbook-project/ui/src/main/resources/AddRecipe.fxml) file.
* **[RecipeViewController](/cookbook-project/ui/src/main/java/cookbook/ui/RecipeViewContoller.java)** - This class is responsible for displaying a recipe. Linked to the [RecipeView.fxml](/cookbook-project/ui/src/main/resources/RecipeView.fxml) file.
* **[EditRecipeController](/cookbook-project/ui/src/main/java/cookbook/ui/EditRecipeContoller.java)** - This class is responsible for editing a recipe. Linked to the [EditRecipe.fxml](/cookbook-project/ui/src/main/resources/EditRecipe.fxml)** file.
* **[LocalCookbookAccess](/cookbook-project/ui/src/main/java/cookbook/ui/LocalCookbookAccess.java)** - This class is responsible for reading and writing to the json file. Using the [CookbookHandler](/cookbook-project/persistence/src/main/java/cookbook/json/CookbookHandler.java) class from the [persistence](/cookbook-project/persistence/readme.md) module. Implements the [CookbookAccess](/cookbook-project/ui/src/main/java/cookbook/accessdata/CookbookAccess.java) interface 
* **[RemoteCookbookAccess](/cookbook-project/ui/src/main/java/cookbook/ui/RemoteCookbookAccess.java)** - This class is responsible for reading and writing to the json file on a remote server using the REST API from the [springboot](/cookbook-project/springboot/readme.md) module. Implements from the [CookbookAccess](/cookbook-project/ui/src/main/java/cookbook/accessdata/CookbookAccess.java) interface 


### _**Tests**_:  
* **[CookbookAppTest](/gr2322/cookbook-project/ui/src/test/java/cookbook/ui/CookbookAppTest.java)** - Tests the App using FxRobot for UI testing.
---
This module has its own pom.xml file, which is responsible for building the module with its respected dependencies and plugins, such as JaCoCo, Checkstyle and Spotbugs.

---
[< Return to _Cookbook Project_](/cookbook-project/readme.md)