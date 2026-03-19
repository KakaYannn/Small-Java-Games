# EchoPlay

EchoPlay is a command-line Java media playlist manager that lets users load, create, edit, save, and play song, podcast, and short-clip playlists from local text files.

## Overview

This project is a file-backed CLI application built in Java. The main application lives under `FPJ/src` and starts from `MusifyApp`. Users can:

- load an existing playlist index file at startup
- create playlists for songs, podcasts, or short clips
- inspect playlist contents in table form
- add and remove media items interactively
- play media by printing captions or lyrics loaded from text files
- persist playlists and media back to disk on exit

## Project Structure

```text
FPJ/
├── src/
│   ├── MusifyApp.java
│   ├── enums/
│   ├── exceptions/
│   ├── fileHandleUtils/
│   ├── interfaces/
│   ├── model/
│   └── utils/
├── data/
│   ├── mediatext/
│   ├── playlist/
│   └── playlists*.txt
└── JAVADOC/
```

## Main Concepts

- `MusifyApp` provides the interactive command-line menu.
- `ListOfPlaylists` manages all playlists for one user and coordinates file loading/saving.
- `Playlist<T extends Media>` is a generic playlist that stores a single media type.
- `Song`, `Podcast`, and `ShortClip` define the supported media.
- `SongFileProcessor`, `PodcastFileProcessor`, and `ShortClipFileProcessor` parse playlist files and attach caption data.
- `FileHandler` centralizes file reads and writes.

## Features

- Generic playlist model typed by media category
- Interactive menu-driven workflow
- Support for three media types: `SONG`, `PODCAST`, `SHORTCLIP`
- Caption or lyric playback from plain text files
- Validation for malformed files and invalid input
- Automatic persistence when exiting the application
- Sample data included under `FPJ/data`

## Requirements

- Java 8 or newer
- Terminal/command prompt

## Compile

From the repository root:

```bash
mkdir -p /tmp/echoplay-classes
find FPJ/src -name '*.java' -print0 | xargs -0 javac -d /tmp/echoplay-classes
```

## Run

Run from the repository root so the relative `data/` paths resolve correctly.

Start with no existing playlist file:

```bash
cd FPJ
java -cp /tmp/echoplay-classes MusifyApp
```

Start with a username and an existing playlist index file:

```bash
cd FPJ
java -cp /tmp/echoplay-classes MusifyApp Alice playlists1.txt
```

When launched with two arguments, the program reads the playlist index from `data/<playlist-file>`.

## Menu Flow

The main menu supports:

1. Create a new playlist
2. View all playlists
3. View contents of a playlist
4. Remove a playlist
5. Modify a playlist
6. Play contents of a playlist
7. Exit

On exit, the app writes:

- the playlist index file in `FPJ/data/`
- each playlist's media file in `FPJ/data/playlist/`

If the app starts without a playlist index file argument, it defaults to saving the index as `data/playlists.txt` when exiting.

## Data Format

### Playlist Index File

Each line in a playlist index file has 3 comma-separated fields:

```text
<playlist name>,<media type>,<playlist file name>
```

Example:

```text
HEALTHFREAK,PODCAST,pod_health
DAILY Shorts,SHORTCLIP,short_daily
```

### Song Playlist File

Each song line has 6 comma-separated fields:

```text
<title>,<description>,<artist names>,<genre>,<duration mins>,<caption file>
```

Multiple artists are stored with `#` between names.

Example:

```text
Let her go,Mix by artists,Passenger#Ed Shereen,pop,4,let_her_go
```

### Podcast Playlist File

Each podcast line has 8 comma-separated fields:

```text
<title>,<description>,<host names>,<category>,<series>,<episode number>,<duration mins>,<caption file>
```

Multiple hosts are stored with `#` between names.

### Short Clip Playlist File

Each short clip line has 5 comma-separated fields:

```text
<title>,<description>,<artist name>,<duration mins>,<caption file>
```

### Caption Files

Caption and lyric files are stored under `FPJ/data/mediatext/`. During playback, the program prints each line in the caption file to the terminal.

## Notes and Constraints

- A playlist can contain at most 5 media items.
- Each playlist stores only one media type.
- Invalid or incomplete file lines are skipped with an error message.
- Missing caption files do not stop the application, but playback will report missing media text.
- File parsing uses commas as delimiters, so commas inside field values are not supported.

## Example Session

```text
java MusifyApp Alice playlists1.txt
Data loading complete.
Welcome Alice. Choose your music, podcasts or watch short clips.
Please select one of the options.
1. Create a new playlist.
2. View all playlist.
3. View contents of a playlist.
4. Remove a playlist.
5. Modify a playlist.
6. Play contents of a playlist.
7. Exit Musify.
```

## Documentation

Generated Javadoc is available in `FPJ/JAVADOC/index.html`.

## Suggested Project Name

`EchoPlay`

## One-Sentence Introduction

EchoPlay is a command-line Java media playlist manager for songs, podcasts, and short clips backed by simple text files.
