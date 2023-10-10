# __Cookbook__

## _Project description_

The Cookbook is an application containing recipes, along with the ingredients needed for each respective recipe. The app will provide inspiration concerning what meals to make. On the app's homepage, the user can scroll through the list of recipes. When clicking the recipes, you get a more detailed description on how to make the dish.

As we progress through the iterations, we will further develop the app to allow users to filter recipes based on different cuisines, such as Italian og Mexican. Additionally, users will also be able to search for desired recipes.

In addition to this, users will be able to add and remove recipes from the cookbook.


![Bildebeskrivelse](/assets/IllustrationOfApp.jpeg)

## _User stories_

**User story 1**

***Scrolling and description***

A student is finishing up her day at school, and is ready to go home. She is very tired, and can't be bothered to think of what to make for dinner. She opens the cookbook app, and scrolls until she finds something she likes. She decides to go for Spaghetti carbonara. She stops by the grocery store on her way home from school, and buys the ingredients she needs. Finally, she prepares dinner following the instructions that belongs to the recipe.

**User story 2**

***Add new recipe and remove***

Frank is a professional chef and owns his own restaurant. He wants to implement a new recipe from the cookbook to his menu. He tries out the recipe tikka masala, but has a better recipe himself. Therefore he wants to remove the old tikka masala recipe and replace it with a new and improved recipe. 

**User story 3**

**S*øking and filtrering***

Bert wants to impress his husband Ernie. Ernie is a fan of the italian cuisine. He filters the recipes in the cookbook app to view only italian recipes. He decides to make a pizza diavola. For dessert he wants to make a chocolate cake. He uses the search function to find the recipe.  

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

## _Project Arcitecture_
---
The the project buildt using maven.  
It has three modules; _core_, _ui_ and _persistence_, each responsible for different parts of the application.  
The _core module_ contains classes responsible for the internal representation of the cookbook.  
The _ui module_ contains classes responsible for the user interface.  
The _persistence module_ is responsible for reading and writing the cookbook to a json file.  
You can read more about the different modules below.

PlantUML diagram of the project structure [here](../assets/diagram.puml)  


## _Modules_
---
### ___core___
Read about the __core__ module [here](/cookbook-project/core/readme.md)

### ___ui___
Read about the __ui__ module [here](/cookbook-project/ui/readme.md)

### ___persistence___
Read about the __persistence__ module [here](/cookbook-project/persistence/readme.md)
