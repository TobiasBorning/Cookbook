# Ui module

The ui module is responsible for the application's user interface.  
It contains four classes; [CookbookApp](/cookbook-project/ui/src/main/java/cookbook/ui/CookbookApp.java), [AppController](/cookbook-project/ui/src/main/java/cookbook/ui/AppContoller.java), [AddRecipeController](/cookbook-project/ui/src/main/java/cookbook/ui/AddRecipeContoller.java), [RecipeViewController](/cookbook-project/ui/src/main/java/cookbook/ui/RecipeViewContoller.java), all located inside the [cookbook.ui](/cookbook-project/ui/src/main/java/cookbook/ui/) package.
The three controllers are responsible for transfering information between scenes, setting up the app, making dynamic changes and handeling input from the user. The [CookbookApp](/cookbook-project/ui/src/main/java/cookbook/ui/CookbookApp.java) class is responsible for running the application.  
Inside the [_resources_](/cookbook-project/ui/src/main/resources/) folder, you will find the FXML files responsible for the layout of the different scenes in the application.  
The ui module requires access to the core and persistence module in order to function properly.


Tests for the classes in UI:  
[CookbookAppTest](/gr2322/cookbook-project/ui/src/test/java/cookbook/ui/CookbookAppTest.java)

This module has its own pom.xml file, which is responsible for building the module with its respected dependencies and plugins, such as JaCoCo, Checkstyle and Spotbugs.