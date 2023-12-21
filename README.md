# Fallen Champions
A dungeon crawler game. 
Select a hero with unique abilities and go exploring within a treacherous dungeon packed with monsters, potions, and traps.

# Software Design
## Design Patterns
In this project, we have implemented several design patterns to ensure the code is well-structured, maintainable, and scalable. Here is a brief overview:

**Model-View-Controller (MVC):**  
The project is structured following the MVC architectural pattern. The model consists of classes and enums representing the game's logic, the view handles the user interface, and the controller mediates between the model and the view, managing user input and updating the view based on changes in the model.

**Singleton:**  
This pattern is used to ensure a single instance of `TUI`, `AudioManager`, and `Game` classes, providing a global point of access to these resources throughout the application.

**Momento:**  
This pattern is utilized to implement the save/load functionality. By leveraging Java's serialization mechanism, we can capture and store the current state of the game, allowing the player to resume their progress at a later time.

**Builder:**  
We use this pattern to construct different types of dungeons. The `DungeonBuilderSmall`, `DungeonBuilderMedium`, and `DungeonBuilderLarge` classes are concrete builders that create dungeons of varying sizes, providing flexibility in the gameplay experience.

**Factory:**  
This pattern is employed to create different types of heroes. The `HeroFactory` class has a `buildHero(HeroTypes)` method that takes a `HeroTypes` enum as an argument and returns a new instance of the corresponding hero type. This approach centralizes hero creation and makes the code more maintainable.

**Mock Object:**  
During the development of this project, we utilized the Mock Object pattern to facilitate effective unit testing. This pattern was particularly useful in isolating the behavior of individual components and ensuring that our tests were not affected by dependencies.

For instance, when testing the game logic in the Game class, we created mock objects for the Hero and Dungeon classes. These mock objects simulated the behavior of real Hero and Dungeon instances, allowing us to test the Game class in a controlled environment.


# What I learned

**Software Development Concepts and Practices**:
* Using major design patterns such as MVC, singleton, momento, builder, factory, and mock object
* Closely following SOLID and OO principles for secure, readable, manageable, and maintainable code
* Defensive Programming Techniques
* Writing multithreaded applications
* Utilizing asynchronous programming for text input
* Using event-driven programming
* Testing and debugging
* Writing descriptive and helpful documentation
* Learned and experienced the software development cycle by:
   * Writing a Software Requirements and Specifications (SRS) document
   * Designing UML diagrams to follow
   * Implementing the designs in code
   * Deploying game as an installable application
  
**Team**:
* Working and communicating effectively within a team
* Leadership and planning skills within a team
* Practicing pair programming with teammates

**Agile Development**:
* Making small, incremental changes via user stories
* Using agile project management tools such as Pivotal Tracker
* Using version control tools such as Git

**External Libraries**:
* Connecting and reading from SQLite for monster data and hero suffixes
* Using JavaFX for GUI
