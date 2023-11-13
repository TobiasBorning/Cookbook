# Release 3
In the third release, our team is committed to completing the remaining features to ensure the product matches with our initial vision. While we have made significant progress in implementing new functionality, our primary focus has been on establishing the API for remote access, in accordance with the task requirements. Additionally, we have improved the user interface, aiming to create a more user-friendly and aesthetically pleasing experience. 

We've introduced several exciting new features in our latest update. Firstly, users can now filter recipes based on their preferences directly from the main interface. For instance, selecting the 'gluten free' checkbox will display only gluten free recipes, with additional options for vegan and lactose free diets.

Additionally, we've expanded the cookbook functionality, allowing users to edit existing recipes. This feature is particularly useful for those who've tried a recipe and wish to make personal adjustments for a more delicious result.

Moreover, each recipe now includes a 'favorite' button, represented by a star icon. By pressing this button, users can easily add recipes to their favorites list. When the 'View favorites' option is checked, the app will display only these favored recipes, simplifying the search for beloved dishes.

Finally, we've implemented a 'filter by type' feature, enabling users to sort recipes into categories such as Breakfast, Lunch, Dinner, Dessert, and an 'Unknown' category for recipes that don't fit the standard classifications. These enhancements are designed to make the cooking experience more personalized and enjoyable.

SKRIVE OM REST-API!!^^

## Task managment
In Release 3, we structured the further development into two distinct parts: Developing a REST-API and enhancing the application with additional features based on new user stories.

To foster effective collaboration, the team chose pair programming as our primary approach. Consequently, two group members concentrated on developing the REST-API, while the others focused on implementing the new functionality. This task distribution strategy ensured that each team member could concentrate on a specific area, thereby optimizing efficiency and productivity.

The tasks were assigned by spinning a wheel, due to the high interest in the creating REST-API task. Consequently, the functionality enhancement task was allocated to the two remaining team members.

In addition to our structured approach to task distribution and pair programming, our team adopted the Scrum framework to enhance our project management and workflow. Scrum was very helpful in our development process because it focuses on being flexible, making steady progress, and working well as a team. We organized our work into sprints, each with a set of defined goals and tasks, allowing us to focus on delivering specific features or improvements in a time-boxed manner. Regularly meetings ensured continuous communication and quick resolution of impediments, while sprint reviews and retrospectives at the end of each sprint provided opportunities for reflection and adaptation. We, as a group, decided together in our meetings when each issue should be completed. This approach not only improved our efficiency and adaptability in the face of changing requirements but also fostered a strong sense of teamwork and shared responsibility.

## Methods for code quality 
GitLab has significantly improved our workflow in various ways, including the enhancement of code quality. Now, merge requests undergo approval and completion exclusively by team members who are tasked with reviewing them. They ensure that the author's code meets all necessary requirements. This process not only elevates code quality through rigorous bug and defect checks but also promotes a deeper understanding of the code among team members, thereby fostering a comprehensive grasp of the entire project.

"Checkstyle," "Spotbugs," and "JaCoCo" have all been instrumental in maintaining high code quality within our project. The use of Checkstyle, along with Javadoc, has significantly enhanced the readability of our code, making it more accessible and understandable, even for those not intimately familiar with the project. Jacoco and Spotbugs have emerged as invaluable for monitoring test coverage and facilitating debugging, respectively. Spotbugs has been exceptionally helpful in identifying elusive bugs or for its ability to present error messages clearly and understandably. Therefore, it’s advisable to integrate Spotbugs early in the development process to effectively detect bugs, although its implementation might require some effort. We have also used checkstyle to improve the consistency and quality of your codebase, make code reviews more efficient, and help maintain a high standard of coding across a team.

## Tests 
In this release, we have expanded our test classes to ensure comprehensive coverage across our entire codebase, encompassing both existing and new code. As previously noted, we employed JaCoCo to gauge the extent of our code coverage. We've achieved nearly 100% test coverage, meticulously testing all relevant portions of the code to ensure robustness and reliability. SpotBugs is great because it shows us exactly where problems are in the code, using a simple GUI, instead of making us look through complicated error messages. JaCoCo gives us a clear picture of which parts of our app have been tested and which haven't. This helps our developers know where to write more tests, making sure our app works well.

We have also implemented an integration test to assess the collective functionality of individual software modules when they are merged and tested together as a cohesive unit. The primary purpose of integration testing is to identify any discrepancies, faults, or deficiencies between the interacting modules. Unlike unit tests, which verify the functionality of a specific section of code (like a class or method), integration tests validate the functionality, performance, and reliability of a combination of modules. 

Additionally, we have developed the test classes LocalCookbookAccessTest and RemoteCookbookAccessTest. Furthermore, we have expanded our UI tests to cover the new functionalities added to our user interface.


## Using Gitlab as a tool for management and workflow 
Initially, all members of our team were relatively unfamiliar with GitLab, which initially limited our development workflow. Our limited experience with Git and branching further compounded this challenge, leading us to work directly on the master branch during the first release. We soon recognized that this approach was suboptimal.

By the time we commenced work on Release 2, we had significantly improved our workflow. We began utilizing issues and separate branches, moving away from the sole reliance on the master branch. This shift greatly enhanced our efficiency and organization.

As we progressed, the team's proficiency with GitLab's functionalities grew. We adopted a systematic approach for creating milestones and issues within GitLab, and we standardized our commit message format. This structured approach carried us through Releases 2 and 3 more smoothly.

Furthermore, the code review feature in GitLab's merge requests became an invaluable tool. It allowed us to thoroughly review each other's code, ensuring quality and consistency before any merges into the master branch. This process not only enhanced our code quality but also fostered a collaborative and learning-centric environment within the team.