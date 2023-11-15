# Release 3
In the third release, our team is committed to completing the remaining features to ensure the product matches with our initial vision. While we have made significant progress in implementing new functionality, our primary focus has been on implementing a REST-API for remote access, in accordance with the task requirements. Additionally, we have improved the user interface, aiming to create a more user-friendly and aesthetically pleasing experience. 

We've introduced several exciting new features in our latest update. Firstly, users can now filter recipes based on their preferences directly from the main interface. For instance, selecting the 'gluten free' checkbox will display only gluten free recipes, with additional options for vegan and lactose free diets.

Additionally, we've expanded the cookbook functionality, allowing users to edit existing recipes. This feature is particularly useful for those who've tried a recipe and wish to make personal adjustments for a more optimal result.

Moreover, each recipe now includes a 'favorite' button, represented by a star icon. By pressing this button, users can easily add recipes to their favorites list. When the 'View favorites' option is checked, the app will display only these favored recipes, simplifying the search for beloved dishes.

Finally, we've implemented a 'filter by type' feature, enabling users to sort recipes into categories such as Breakfast, Lunch, Dinner, Dessert, and an 'Unknown' category for recipes that don't fit the standard classifications. These enhancements are designed to make the cooking experience more personalized and enjoyable.

The UI is now configured for interaction with a REST-API. We used springboot to create a the server that works together with our frontend. Read about the REST-API [here](/cookbook-project/springboot/readme.md)

---

## Task managment
In Release 3, we structured the further development into two distinct parts: Developing a REST-API and enhancing the application with additional features based on new user stories.

To foster effective collaboration, the team chose pair programming as our primary approach. Consequently, two group members concentrated on developing the REST-API, while the others focused on implementing the new functionality. This task distribution strategy ensured that each team member could concentrate on a specific area, thereby optimizing efficiency and productivity.

The tasks were assigned by spinning a wheel, due to the high interest in the creating REST-API task. Consequently, the functionality enhancement task was allocated to the two remaining team members.

In addition to our structured approach to task distribution and pair programming, our team adopted the Scrum framework to enhance our project management and workflow. Scrum was very helpful in our development process because it focuses on being flexible, making steady progress, and working well as a team. We organized our work into sprints, each with a set of defined goals and tasks, allowing us to focus on delivering specific features or improvements in a time-boxed manner. Regularly meetings ensured continuous communication and quick resolution of impediments, while sprint reviews and retrospectives at the end of each sprint provided opportunities for reflection and adaptation. We, as a group, decided together in our meetings when each issue should be completed. This approach not only improved our efficiency and adaptability in the face of changing requirements but also fostered a strong sense of teamwork and shared responsibility.

---

## Methods for code quality 
GitLab has significantly improved our workflow in various ways, including the enhancement of code quality. Merge requests undergo approval and completion exclusively by team members who are tasked with reviewing them. They ensure that the author's code meets all necessary requirements. This process not only elevates code quality through rigorous bug and defect checks but also promotes a deeper understanding of the code among team members, thereby fostering a comprehensive grasp of the entire project.

To improve each other's code we have implemented code reviews in our work habits. This practice allows us to directly comment on specific parts of the code, facilitating more targeted and effective feedback. By engaging in code reviews, team members can pinpoint areas that need refinement, suggest improvements, and share insights that might not be immediately apparent to the original author. This collaborative approach not only enhances the overall quality of our code but also fosters a culture of continuous learning and mutual support within our team.

**_Checkstyle_** and **_Spotbugs_** have all been instrumental in maintaining high code quality within our project. The use of checkstyle, along with Javadoc comments, has significantly enhanced the readability of our code, making it more accessible and understandable, even for those not intimately familiar with the project. Jacoco have emerged as invaluable for monitoring test coverage.  
**Spotbugs** has been exceptionally helpful in identifying elusive bugs and vulnereablities. It is great because it shows us exactly where problems are in the code, using a simple GUI, instead of making us look through complicated error messages.  
We have also used **checkstyle** to improve the consistency and quality of your codebase, make code reviews more efficient, and help maintain a high standard of coding across a team. We use google_checks as our checkstyle configuration file. 

---

## Tests 
In this release, we have expanded our test classes to ensure comprehensive coverage across our entire codebase, encompassing both existing and new code. As previously noted, we have JaCoCo to report code coverage. We aim for high test coverage, testing all relevant portions of the code to ensure robustness and reliability. JaCoCo gives us a clear picture of which parts of our app have been tested and which haven't. This helps our developers know where to write more tests, making sure our app works well.
Read more about individual test in the module readme files.

We have also implemented an **_integration test_** to assess the collective functionality of individual modules when they are merged and tested together as a cohesive unit. The primary purpose of integration testing is to identify any discrepancies, faults, or deficiencies between the interacting modules. Unlike unit tests, which verify the functionality of a specific section of code (like a class or method), integration tests validate the functionality, performance, and reliability of a combination of modules.

---

## Using Gitlab as a tool for management and workflow 
Initially, all members of our team were relatively unfamiliar with GitLab, which initially limited our development workflow. Our limited experience with Git and branching further compounded this challenge, leading us to work directly on the master branch during the first release. We soon recognized that this approach was suboptimal.

By the time we commenced work on Release 2, we had significantly improved our workflow. We began utilizing issues and separate branches, moving away from the sole reliance on the master branch. This shift greatly enhanced our efficiency and organization.

As we progressed, the team's proficiency with GitLab's functionalities grew. We adopted a systematic approach for creating milestones and issues within GitLab, and we standardized our commit message format. This structured approach carried us through Releases 2 and 3 more smoothly.

Furthermore, GitLab's merge requests became an invaluable tool. Making us able to comment on dodgy code or defect functionality. It allowed us to thoroughly review each other's code, ensuring quality and consistency before any merges into the master branch. This process not only enhanced our code quality but also fostered a collaborative and learning-centric environment within the team.


## Release 3 user interface
We decided to attach a stylesheet to the fxml files to give the user interface a graphical improvement. We also added filters for recipe type. Favorite, Vegan, Lactose free og Gluten free.  

![](/assets/Release3App.png)

---

## Reflection of the project and further development 
Our journey with GitLab as a management and workflow tool was a new experience for all of the group members. Throughout this course, we have gained new knowledge and skills. We have been introduced to new technologies like Maven, Spring boot, JaCoCo and different testing frameworks. Additionally, our competence in GitLab and Git, particularly regarding collaboration, handling commits, branching, and issue management, has grown significantly.

After the third release the Cookbook app is well functioning. Looking ahead, if we were to have another iteration, our primary focus would be on enhancing the graphical user interface. For instance, we'd redesign the Recipe View scene to be more visually appealing. Another potential improvement could be introducing a log in feature, allowing users to access a personalized cookbook. We would also refine the code to ensure all ingredient names are consistently capitalized and other similar improvements in the GUI.

Looking back at our workflow from release 1, we have improved massively, especially with the use of branching and merge requests. This has improved the stability of our project and has made it a lot easier to split up and focus on different tasks. Using Gitlabs **_issue_** feature, along with the **_issue board_** has made structuring the project a lot easier. After making this workflow change, we have been able to work more efficiently and have been able to focus on the tasks at hand, we would never go back to our release 1 workflow without branches and issues.

In conclusion, we are proud of what we have achieved after the third release and are satisfied with our educational progress. The experience of working collaboratively as a team on this project has been especially rewarding.

--- 

[_**<** Return to gr2322_](../../readme.md)
