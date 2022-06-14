# Across The Sands

## Idea

The game I would like to make is based on the book, Dune. The game will focus on the survival of the player as they try to cross a desert to reach a town or some end objective.

During the day, the player is able to walk and search for materials. They can look for weapon items, for example. However, they risk getting eaten by large enemies called sandworms. During the night, the player must settle down and fight multiple enemies using the materials they gathered throughout the day. After multiple in-game days, the player will be able to reach their objective and win the game.

The player will use the keyboard to move and interact with the materials they gather, and use the mouse to aim weapons.

- WASD keys to move
- Mouse to aim and click on object
- E key to interact with items
- R key to reload weapons

## Layout

The main game takes place on a 2-dimensional plane.

## Programming Challenges

- Trigonometry
  - Calculating projectiles paths
  - Calculating movement direction, mouse direction from player
- Map
  - Displaying a miniature map in the corner of the screen
  - Processing the map in memory, loading/unloading parts of map
  - Storing/loading the map from file
- Saving the player/game state to file so the user can close and reopen the game
- Handling event systems
  - Different events occur at different frequencies, for example the day and night cycle, enemy spawning, and world updating
  - Detecting collisions and creating hitboxes for animated characters
- Sharing information between the different game entities
  - How do the enemies know where the player is?
  - How do the enemies know where they can move?
- Enemies
  - Generating enemies into the world
  - Creating “smart” and varied enemy behaviour
  - Pathfinding

## Timeline

- [x] Finish class diagram
- [x] Submit proposal
- [x] Finish switching screens and menu screens
- [x] Finish game entity superclass
- [x] Finish hitboxes
- [x] Finish player that is animated and can move
- [x] Finish game map system
- [x] Finish mini map
- [ ] Finish adding sprites
- [ ] Finish item classes
- [ ] Finish player attacks
- [ ] Finish player inventory system
- [ ] Finish player user interface
- [ ] Finish enemies and enemy behaviour
- [ ] Finish adding sounds
- [ ] Finish debugging game
- [ ] Finish adding comments
- [ ] Finish video
- [ ] Submit project

## Sprite Credits

- [Player](https://craftpix.net/freebies/free-desert-enemy-sprite-sheets-pixel-art/)