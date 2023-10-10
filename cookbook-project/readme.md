# __Cookbook__

## _Project description_

The Cookbook is an application containing recipes, along with the ingredients needed for each respective recipe. The app will provide inspiration concerning what meals to make. On the app's homepage, the user can scroll through the list of recipes. When clicking the recipes, you get a more detailed description on how to make the dish.

As we progress through the iterations, we will further develop the app to allow users to filter recipes based on different cuisines, such as Italian og Mexican. Additionally, users will also be able to search for desired recipes.

In addition to this, users will be able to add and remove recipes from the cookbook.


![Bildebeskrivelse](/assets/IllustrationOfApp.jpeg)

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

## Workflow

In this release, our team has prioritized focusing on productive work habits, achieving a significantly enhanced workflow by utilizing issues for the release. We've done so by making a milestone for each release. For the milestone we have created issues which show up as 'open', 'in progress' or 'closed' on the issue board, showcasing the tasks in progress and those that have been concluded. Each issue is assigned to its respective milestone and user story if relevant. The issues har also labeled appropriately, ensuring developers are aware of the development stage to which the issues pertain. Adopting this approach affords us several advantages. Primarily, it facilitates the allocation of developers to diverse areas of the development, enabling each member in the group to work on different type of tasks. Secondly, it provides a clear visual representation of the remaining work for each project component, potentially leading to more effective delegation within the team.

**_Brancing on issue:_**  
Moreover, each issue possesses an associated branch where we have implemented changes pertinent to that specific issue, ensuring structured and transparent traceability of modifications back to their respective tasks. Each branch is is named in following template for issue x - (user story y):  
x-(us-y-)short description. This provides a clear and logical system, whereby any adjustments or enhancements can be easily attributed and traced to their originating issues, enhancing both accountability and clarity in our developmental workflow. This also makes merging the different branches into the master branch easier, as we can easily see which branches are ready to be merged.

**_Commit messages:_**  
In programming and software development, it is essential to maintain a clear and concise log of changes, or 'commits,' in the source code. This practice allows developers to track and understand the history and development of a project. A good 'commit' message should provide clear, concise, and useful notes about what, why, and how changes have been made. We have used the following template for commit messages:

[ID] description of task done

Slightly more detailed description

Closes #issue

Co-authored by: co-author

**_Merging branches to master:_**  
We have also ensured that every merge request is reviewed by a secondary part. This layer of oversight ensures the master branch maintains its integrity and safeguarding against potential errors.

**_Pair programming:_**  
In this release, we have been pair programming. We've found that by working closely together, we have not only enhanced our collective knowledge and skills, but also gained good insight into every part of the applications functionality.