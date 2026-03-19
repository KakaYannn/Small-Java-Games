# Pacman Maze Game

A small command-line Pacman game written in Java. The player selects one of three maze layouts, moves Pacman through the maze with `W/A/S/D`, and tries to reach the food pellet while minimizing wall hits and unnecessary moves.

## Overview

The program starts from a text-based main menu where you can:

- choose a maze type
- start a new game
- resume a paused game
- view completed game scores
- exit

Each maze is generated from:

- `mazeLength`
- `mazeWidth`
- `seed`

The seed is used to place the food pellet deterministically.

## Requirements

- Java 17 or newer

This project does not use Maven or Gradle. It can be compiled directly with `javac`.

## Project Structure

- `src/GameEngine.java`: main entry point, menu flow, score history, and overall game control
- `src/Maze.java`: maze rendering, movement rules, collision handling, pause/resume state, and scoring
- `src/FoodGenerator.java`: seeded random food-position generator
- `src/Constants.java`: shared constants for controls, scoring, and menu options

## How To Compile

From the project root:

```bash
javac -d out src/*.java
```

## How To Run

Run the game with three command-line arguments:

```bash
java -cp out GameEngine <mazeLength> <mazeWidth> <seed>
```

Example:

```bash
java -cp out GameEngine 10 8 12345
```

Argument rules:

- `mazeLength` must be a positive integer
- `mazeWidth` must be a positive integer
- `seed` must be a positive integer

If the arguments are invalid, the program exits with:

```text
Invalid Inputs to set layout. Exiting the program now.
```

## Controls

During gameplay:

- `W`: move up
- `A`: move left
- `S`: move down
- `D`: move right
- `Q`: pause and save the current game

## Maze Types

From the main menu, choose one of these maze layouts:

1. Lower triangle maze
2. Upper triangle maze
3. Horizontal maze

Maze display symbols:

- `#`: outer boundary
- `P`: Pacman
- `*`: food pellet
- `.`: walkable path
- `-`: wall

## Scoring

The game uses the following score formula:

```text
Score = 20 * Food - 0.5 * hits - 0.25 * moves
```

In practice, the implementation starts each game at `20` points and deducts penalties as you play:

- hitting a wall or boundary: `-0.5`
- each valid move: `-0.25`

The game ends when Pacman reaches the food pellet. The completed game is then stored in the score table.

## Gameplay Flow

1. Launch the program with valid arguments.
2. Select option `1` to create a maze.
3. Choose a maze type.
4. Select option `2` to start playing.
5. Move Pacman to the food pellet.
6. Use option `3` to resume a paused game if needed.
7. Use option `4` to view completed game results.

## Notes

- A maze must be created before starting or resuming a game.
- If you start a new game while another one is paused, the program lets you discard the previous game or return to the menu.
- Food placement is reproducible for the same maze dimensions, seed, and maze type.
- The repository currently includes compiled `.class` files in `out/production/untitled`, but you can regenerate fresh output with the compile command above.
