# __Cookbook__

## Project description

The Cookbook is an application containing recipes, along with the ingredients needed for each respective recipe. The app will provide inspiration concerning what meals to make. On the app's homepage, the user can scroll through the list of recipes. When clicking the recipes, you get a more detailed description on how to make the dish.

As we progress through the iterations, we will further develop the app to allow users to filter recipes based on different cuisines, such as Italian og Mexican. Additionally, users will also be able to search for desired recipes.

In addition to this, users will be able to add and remove recipes from the cookbook.


![Bildebeskrivelse](/assets/IllustrationOfApp.jpeg)


## User story

A student is finishing up her day at school, and is ready to go home. She is very tired, and can't be bothered to think of what to make for dinner. She opens the cookbook app, and scrolls until she finds something she likes. She decides to go for Spaghetti carbonara. She stops by the grocery store on her way home from school, and buys the ingredients she needs. Finally, she prepares dinner following the instructions that belongs to the recipe.


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

## _Project structure_
---
The the project has two modules; core and ui. The core module contains the business logic and the ui module contains the user interface. The ui module handles the user interface and interaction. The project is built using maven.
Diagram of the project structure [here](/ProjectStructure.jpeg).

## _Modules_
---
### ___core___
Read about the __core__ module [here](/cookbook-project/core/readme.md)

### ___ui___
Read about the __ui__ module [here](/cookbook-project/ui/readme.md)
