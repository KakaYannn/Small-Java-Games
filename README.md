# Small-Java-Games

A collection of small Java game and utility projects, including two maze-based Pacman-style games and an audio playlist manager.

## Projects

### 1) Pacman Maze Game
- Location: `Pacman Maze Game/`
- A single-player Pacman-like maze game where the player selects a maze type, collects food, and avoids obstacles.
- Main source: `Pacman Maze Game/src/GameEngine.java`

### 2) Maze Hunt Pacman
- Location: `Maze Hunt Pacman/`
- Multiplayer/competitive variant with seeded mazes, power-ups, monsters, and scoring.
- Main source: `Maze Hunt Pacman/src/GameEngine.java`

### 3) EchoPlay (Musify)
- Location: `EchoPlay/`
- A playlist manager for music, podcasts, and short clips sourced from local text files.
- Main source: `EchoPlay/MusifyApp.java`

## Requirements

- Java JDK 8 or higher (JDK 11+ recommended)
- A Java IDE (IntelliJ IDEA, Eclipse, NetBeans) or command-line tools (`javac`/`java`)

## Running the Projects

### Run via IDE (recommended)
1. Open the project folder in your IDE.
2. Import as a Java project.
3. Locate the `main` class for the desired project and run it.

### Run via command line

From the workspace root, compile and run each project separately.

#### Pacman Maze Game
```bash
cd "Pacman Maze Game"
javac -d out -sourcepath src $(find src -name "*.java")
java -cp out GameEngine
```

#### Maze Hunt Pacman
```bash
cd "Maze Hunt Pacman"
javac -d out -sourcepath src $(find src -name "*.java")
java -cp out GameEngine
```

#### EchoPlay (Musify)
```bash
cd EchoPlay
javac -d out -sourcepath src $(find src -name "*.java")
java -cp out MusifyApp
```

> Note: Some projects assume the current working directory contains data files (e.g., playlists in `EchoPlay/data/`). Run from the project directory so relative paths resolve correctly.

## Project Structure

- `Pacman Maze Game/` - Maze-based Pacman-style game sources.
- `Maze Hunt Pacman/` - Competitive maze game sources.
- `EchoPlay/` - Playlist manager and supporting data files.

## License
This repository includes sample Java projects. Check `LICENSE` for the full license text.
