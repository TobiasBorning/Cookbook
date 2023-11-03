# Ui module

The ui module is responsible for the application's user interface. The controllers are responsible for transfering information between scenes, setting up the app, making dynamic changes and handeling input from the user.  
Inside the [_resources_](/cookbook-project/ui/src/main/resources/) folder, you will find the FXML files responsible for the layout of the different scenes in the application.  
The ui module requires access to the core and persistence module in order to function properly.

### _Packages_:
* **[cookbook.ui](/cookbook-project/ui/src/main/java/cookbook/ui/)** - Contains the controllers for the different scenes in the application. As well as the main class for running the application.
* **[coookbook.accessdata](/cookbook-project/ui/src/main/java/cookbook/ui/)** - Contains the logic for reading and writing to the json file. This package contains two classes, one for reading and writing to a local json file and one for reading and writing to a remote json file. Both implementing the same interface for use in the controllers.

### _Classes_:
* **[CookbookApp](/cookbook-project/ui/src/main/java/cookbook/ui/CookbookApp.java)** - This class is responsible for running the application.
* **[AppController](/cookbook-project/ui/src/main/java/cookbook/ui/AppContoller.java)** - This class is responsible for setting up the application and switching between scenes. Linked to the [App.fxml](/cookbook-project/ui/src/main/resources/App.fxml) file.
* **[AddRecipeController](/cookbook-project/ui/src/main/java/cookbook/ui/AddRecipeContoller.java)** - This class is responsible for adding a recipe to the cookbook. Linked to the [AddRecipe.fxml](/cookbook-project/ui/src/main/resources/AddRecipe.fxml) file.
* **[RecipeViewController](/cookbook-project/ui/src/main/java/cookbook/ui/RecipeViewContoller.java)** - This class is responsible for displaying a recipe. Linked to the [RecipeView.fxml](/cookbook-project/ui/src/main/resources/RecipeView.fxml) file.
* **[EditRecipeController](/cookbook-project/ui/src/main/java/cookbook/ui/EditRecipeContoller.java)** - This class is responsible for editing a recipe. Linked to the [EditRecipe.fxml](/cookbook-project/ui/src/main/resources/EditRecipe.fxml)** file.
* **[LocalCookbookAccess](/cookbook-project/ui/src/main/java/cookbook/ui/LocalCookbookAccess.java)** - This class is responsible for reading and writing to the json file. Using the [CookbookHandler](/cookbook-project/persistence/src/main/java/cookbook/json/CookbookHandler.java) class from the [persistence](/cookbook-project/persistence/readme.md) module. Implements the [CookbookAccess](/cookbook-project/ui/src/main/java/cookbook/accessdata/CookbookAccess.java) interface 
* **[RemoteCookbookAccess](/cookbook-project/ui/src/main/java/cookbook/ui/RemoteCookbookAccess.java)** - This class is responsible for reading and writing to the json file on a remote server using the REST API from the [springboot](/cookbook-project/springboot/readme.md) module. Implements from the [CookbookAccess](/cookbook-project/ui/src/main/java/cookbook/accessdata/CookbookAccess.java) interface 
### _Tests_:  
* **[CookbookAppTest](/gr2322/cookbook-project/ui/src/test/java/cookbook/ui/CookbookAppTest.java)** - Tests the App using FxRobot for UI testing.
---
This module has its own pom.xml file, which is responsible for building the module with its respected dependencies and plugins, such as JaCoCo, Checkstyle and Spotbugs.