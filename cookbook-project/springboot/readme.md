# __Springboot module__

This module is responsible for creating a REST API, which can be used by multiple frontends by using the proper REST API calls. The REST API is created using the Springboot framework. The frontend is set up to be able to use the REST API created by this module. To fetch data from the API, you can use different REST HTTP-requests when connected to http://localhost:8080/api  

**_We have implemented the following HTTP-requests:_**

* **GET _/cookbook_** - Returns the [remote cookbook](/cookbook-project/persistence/remote-cookbook.json). Will return status **_code 200 OK_** as well as the Cookbook.

* **GET _/cookbook/search/{searchString}_** - Returns a cookbook with recipes from the [remote cookbook](/cookbook-project/persistence/remote-cookbook.json) with a name containg the {searchString}. Will return status **_code 200 OK_** as well as the Cookbook containg the recipes matching the _searchString_. If no recipes match the preferences, the Cookbook will be empty.

* **GET _/cookbook/origin/{origin}_** - Returns all recipes from the [remote cookbook](/cookbook-project/persistence/remote-cookbook.json) with an origin matching {origin}. Will return status **_code 200 OK_** as well as the Cookbook containg the recipes matching the origin. If no recipes match the preferences, the Cookbook will be empty.

* **GET _/cookbook/type/{type}_**_ - Returns all recipes from the [remote cookbook](/cookbook-project/persistence/remote-cookbook.json) with an type matching {type}. Will return status **_code 200 OK_** as well as the Cookbook containg the recipes matching the type. If no recipes match the preferences, the Cookbook will be empty.

* **GET _/cookbook/preferences/{vlgString}_** - Returns all recipes from the [remote cookbook](/cookbook-project/persistence/remote-cookbook.json) with an preferences matching {vlgString}. vlg: Vegan, Lactosefree, Glutenfree. Ex: if you are vegan, but not lactose or gluten intolerant, the vlgString is TFF(True,False,False). Will return status **_code 200 OK_** as well as the Cookbook containg the recipes matching the preferences. If no recipes match the preferences, the Cookbook will be empty.

* **GET _/cookbook/favorites_** - Returns all recipes from the [remote cookbook](/cookbook-project/persistence/remote-cookbook.json) that are marked as favorite. Will return status **_code 200 OK_** as well as the Cookbook containg the recipes are favorited. If no recipes match the preferences, the Cookbook will be empty.

* **POST _/cookbook_** - Adds a recipe to the [remote cookbook](/cookbook-project/persistence/remote-cookbook.json). The recipe is sent as a JSON object in the body of the request. Returns status code **_200 (OK)_** if successful. Returns status code **_400 (Bad Request)_** if the recipe already exists in the cookbook.

* **POST _/cookbook/new_** - Overwrites the [remote cookbook](/cookbook-project/persistence/remote-cookbook.json) to contain an empty cookbook.

* **PUT _/cookbook/favorite/{recipeName}_** - Marks a recipe from the [remote cookbook](/cookbook-project/persistence/remote-cookbook.json) as favorite. Returns status code **_200 (OK)_** if successful. Returns status code **_400 (Bad Request)_** if the recipe does not exist.

* **PUT _/cookbook/recipe/{recipeName}_** - Updates a recipe from the [remote cookbook](/cookbook-project/persistence/remote-cookbook.json) to contain the information from the recipe sent as a JSON object in the body of the request. Returns status code **_200 (OK)_** if successful. Returns status code **_400 (Bad Request)_** if the recipe does not exist.

* **DELETE _/cookbook/recipe/{recipeName}_** - Deletes a recipe from the [remote cookbook](/cookbook-project/persistence/remote-cookbook.json) if matching {recipeName}. Returns status code **_200 (OK)_** if successful. Returns status code **_400 (Bad Request)_** if the recipe does not exist. 

---
### **The Springboot framework**
Springboot is a framework for building Java applications. It is used in this module to create a REST API, which is used by the frontend module to communicate with the backend. The REST API is created using the [CookbookController](/cookbook-project/persistence/src/main/java/cookbook/springboot/CookbookController.java) class. This class is responsible for mapping the HTTP requests to the correct methods in the [CookbookService](/cookbook-project/springboot/src/main/java/cookbook/springboot/CookbookService.java) class.

---
### **Starting the springboot server**
To start the springboot server, you need to navigate to the springboot module and run the following commands in the terminal:
```bash
# Navigate to the springboot module
% cd cookbook-project/springboot

# Build the module
% mvn clean install

# Start the springboot server
% mvn spring-boot:run

# The server is now running on http://localhost:8080/api
# Kill the terminal to stop the server
```

---

### _**Packages**_
* [cookbook.springboot](/cookbook-project/springboot/src/main/java/cookbook/springboot) - Contains the controller class, which is responsible for mapping the HTTP requests to the correct methods in the service class. Also contains the main class, which starts the springboot server.

### _**Classes**_
* [CookbookController](/cookbook-project/persistence/src/main/java/cookbook/springboot/CookbookController.java) - Responsible for mapping the HTTP requests to the correct methods in the [CookbookService](/cookbook-project/springboot/src/main/java/cookbook/springboot/CookbookService.java) class.
* [CookbookService](/cookbook-project/springboot/src/main/java/cookbook/springboot/CookbookService.java) - Responsible for handling the HTTP requests and returning the correct response.
* [CookbookModelApplication](/cookbook-project/springboot/src/main/java/cookbook/springboot/CookbookModelApplication.java) - The main class of the springboot module. Starts the springboot server.

### _**Tests**_
* [CookbookModelApplicationTest](/cookbook-project/springboot/src/test/java/cookbook/springboot/CookbookModelApplicationTest.java) - Tests the REST API using HTTP-requests with Mockito.

---

### **Testing the REST API**
We use `Mockito` to test the REST-API. Mockito is a Java based framwork for mocking objects. We use it to mock the HTTP-requests and test the REST-API using `MockMvc`. The `MockMvc` object is given the context of the classes in the _cookbook.springboot_ package, as well as a `GsonHttpMessageConverter` for parsing Cookbook objects. We use the _@AutoConfigureMockMvc_ to create the MockMvc. The `MockMvc` mocks _localhost:8080/api_, making it possible to test the api without actually running the server.

---
## Important Dependecies
* __Core__
* __Persistence__
* __GSON__

---
This module has its own pom.xml file, which is responsible for building the module with its respected dependencies and plugins, such as Springboot, JaCoCo, Checkstyle, Spotbugs and Gson.

---
[< Return to _Cookbook Project_](/cookbook-project/readme.md)