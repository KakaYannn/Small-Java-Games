# Small Java Games

This repository collects three small Java console projects: two Pacman-style maze games and one media playlist manager. Each project is self-contained, uses plain `javac`/`java`, and can be run independently from its own folder.

## Projects

### Pacman Maze Game

Location: `Pacman Maze Game/`

A single-player maze game where the player chooses a maze layout, moves with `W/A/S/D`, avoids penalties from walls, and tries to reach the food pellet with the highest score possible.

Main files:

- `Pacman Maze Game/src/GameEngine.java`
- `Pacman Maze Game/src/Maze.java`
- `Pacman Maze Game/src/FoodGenerator.java`
- `Pacman Maze Game/src/Constants.java`

Runtime arguments:

```bash
java -cp out GameEngine <mazeLength> <mazeWidth> <seed>
```

### Maze Hunt Pacman

Location: `Maze Hunt Pacman/`

A larger Pacman variant with single-player and multiplayer modes, seeded maze generation, special food, monsters, pause/resume support, and score comparison between players.

Main files:

- `Maze Hunt Pacman/src/GameEngine.java`
- `Maze Hunt Pacman/src/Maze.java`
- `Maze Hunt Pacman/src/LocationGenerator.java`
- `Maze Hunt Pacman/src/ScoreBoard.java`

Runtime arguments:

```bash
java -cp out GameEngine <mazeLength> <mazeWidth> <seed>
```

### EchoPlay

Location: `EchoPlay/`

A command-line playlist manager for songs, podcasts, and short clips backed by text files. The source code, sample data, and generated Javadoc now live directly under `EchoPlay/`.

Main files:

- `EchoPlay/src/MusifyApp.java`
- `EchoPlay/src/model/ListOfPlaylists.java`
- `EchoPlay/src/model/playlist/Playlist.java`
- `EchoPlay/src/fileHandleUtils/FileHandler.java`

Runtime arguments:

```bash
java -cp out MusifyApp [username] [playlist-file]
```

If a playlist file is provided, it is loaded from `EchoPlay/data/`.

## Requirements

- Java 17 or newer is the safest choice across the repository
- A terminal, or any Java IDE such as IntelliJ IDEA or Eclipse

There is no shared Maven or Gradle build. Each project is compiled and run separately.

## Build And Run

### Pacman Maze Game

```bash
cd "Pacman Maze Game"
mkdir -p out
javac -d out src/*.java
java -cp out GameEngine 10 8 12345
```

### Maze Hunt Pacman

```bash
cd "Maze Hunt Pacman"
mkdir -p out
javac -d out src/*.java
java -cp out GameEngine 12 10 42
```

### EchoPlay

```bash
cd EchoPlay
mkdir -p out
find src -name '*.java' -print0 | xargs -0 javac -d out
java -cp out MusifyApp
```

Load sample playlist data:

```bash
cd EchoPlay
java -cp out MusifyApp Alice playlists1.txt
```

Or use the helper script from the repository root:

```bash
./run.sh echoplay
```

## Gameplay And Features

### Pacman Maze Game

- Three maze layouts: lower triangle, upper triangle, and horizontal maze
- Main menu flow for creating a maze, starting a game, resuming a paused game, and viewing scores
- Deterministic food placement based on the supplied seed
- Score penalties for wall hits and movement

### Maze Hunt Pacman

- Single-player and two-player modes
- Three maze layouts using the same command-line dimensions and seed model
- Monsters and special food generated from the seed
- Pause/resume support and end-of-round score comparison in multiplayer mode

### EchoPlay

- Create, inspect, modify, remove, and play playlists from a terminal menu
- Supports songs, podcasts, and short clips
- Reads and writes playlist data under `EchoPlay/data/`
- Includes generated Javadoc in `EchoPlay/JAVADOC/`

## Repository Notes

- Each project keeps its own source files and documentation inside its own directory.
- `run.sh` provides a quick compile-and-run entry point for `pacman`, `maze-hunt`, and `echoplay`.

## License

See `LICENSE` for the repository license.
