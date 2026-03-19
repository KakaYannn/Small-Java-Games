/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package model.playlist;

import java.util.*;

import enums.*;
import exceptions.*;
import fileHandleUtils.FileHandler;
import interfaces.BaseFileProcessor;
import interfaces.Storable;
import model.playlist.media.*;
import utils.Constants;
import utils.Messages;

/**
 * Represents a playlist that can store one of types of media.It leverages generics to manage different
 * media types and provides functions for adding, removing, viewing, and playing media items.
 *
 * @param <T> The type of media stored in this playlist, which extends Media.
 */
public class Playlist<T extends Media> implements Storable {
    private String name;
    private MediaType mediaType;
    private String fileName;
    private HashMap<String, T> mediaMap;
    private BaseFileProcessor baseFileProcessor;
    private FileHandler fileHandler;

    /**
     * Default constructor of a new Playlist.
     */
    public Playlist() {
    }

    /**
     * Constructs a new Playlist with the specified name, media type, and file name.
     *
     * @param name      The name of the playlist.
     * @param mediaType The type of media stored in the playlist.
     * @param fileName  The name of the file associated with this playlist.
     */
    public Playlist(String name, MediaType mediaType, String fileName) {
        this.name = name;
        this.mediaType = mediaType;
        this.fileName = fileName;
        this.mediaMap = new LinkedHashMap<>();
    }

    /**
     * Loads the playlist data from a file from the list of playlists.
     */
    @Override
    public void loadFromFile() {
        try {
            String filePath = Constants.PATH_PLAYLIST + fileName;
            List<Media> mediaList = baseFileProcessor.process(filePath);
            for (Media media : mediaList) {
                mediaMap.put(media.getName(), (T) media);
            }
        } catch (InvalidFormatException | InvalidLineException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves the playlist data to the playlist file.
     */
    @Override
    public void writeBack() {
        String playlistFilePath = Constants.PATH_PLAYLIST + fileName;
        StringBuilder playlistContent = new StringBuilder();
        int lineNumber = 1;
        for (HashMap.Entry<String, T> entry : mediaMap.entrySet()) {
            Media media = entry.getValue();
            media.writeBackContent(playlistContent);
            if (lineNumber < mediaMap.size()) {
                playlistContent.append("\n");
            }
            lineNumber++;
        }
        fileHandler.save(playlistFilePath, playlistContent);
    }

    /**
     * Modifies the playlist by displaying a menu to the user, allowing them to view, add, remove,
     * or exit the playlist.
     */
    public void modifyPlaylistMenu() {
        boolean prompt = true;
        while (prompt) {
            System.out.println("Please select one of the options.\n" +
                    "1. View the playlist.\n" +
                    "2. Add a new " + mediaType.toString().toLowerCase() + ".\n" +
                    "3. Remove a " + mediaType.toString().toLowerCase() + ".\n" +
                    "4. Exit and go back to main menu.");
            String modifySelection = Constants.keyboard.nextLine();
            switch (modifySelection) {
                case "1":
                    viewMedia();
                    break;
                case "2":
                    try {
                        addNewMedia();
                    } catch (PlaylistFullException | InvalidFormatException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    removeMedia();
                    break;
                case "4":
                    prompt = false;
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * Adds new media to the playlist.
     *
     * @throws PlaylistFullException  If the playlist has reached 5 counts.
     * @throws InvalidFormatException If there is an error with the input format.
     */
    public void addNewMedia() throws PlaylistFullException, InvalidFormatException {
        if (mediaMap.size() >= Constants.MAX_MEDIA_COUNT) {
            throw new PlaylistFullException(name, mediaType);
        }

        System.out.print(Messages.ENTER_MEDIA_TITLE);
        String title = Constants.keyboard.nextLine();
        System.out.print(Messages.ENTER_MEDIA_DESCRIPTION);
        String description = Constants.keyboard.nextLine();
        System.out.print(Messages.ENTER_MEDIA_DURATION);
        int duration;
        try {
            duration = Integer.parseInt(Constants.keyboard.nextLine());
        } catch (NumberFormatException e) {
            throw new InvalidFormatException(Messages.INVALID_INPUT);
        }
        System.out.print(Messages.ENTER_CAPTION_FILENAME);
        String captionFileName = Constants.keyboard.nextLine();
        String captionFilepath = Constants.PATH_MEDIA_TEXT + captionFileName;
        List<String> captionLines = fileHandler.read(captionFilepath, true);

        switch (mediaType) {
            case MediaType.SONG:
                putInPlaylist(createSong(title, description, duration, captionFileName, captionLines));
                break;
            case MediaType.PODCAST:
                putInPlaylist(createPodcast(title, description, duration, captionFileName, captionLines));
                break;
            case MediaType.SHORTCLIP:
                putInPlaylist(createShortClip(title, description, duration, captionFileName, captionLines));
                break;
            default:
                break;
        }
    }

    /**
     * Displays all media in the playlist.
     */
    public void viewMedia() {
        if (mediaMap.isEmpty()) {
            System.out.println("No " + mediaType.toString().toLowerCase() + " in the playlist to view.");
            return;
        }

        switch (mediaType) {
            case SONG:
                System.out.printf(Constants.SONG_PLAYLIST_HEADER, "Id", "Title", "Artist Name", "Description",
                        "Genre", "Duration In Mins");
                System.out.println("---------------------------------------------------------------------------------" +
                        "--------------------------------------------");
                break;
            case PODCAST:
                System.out.printf(Constants.PODCAST_PLAYLIST_HEADER, "Id", "Title", "Host Name(s)", "Description",
                        "Category", "Series Name", "Episode#", "Duration In Mins");
                System.out.println("-----------------------------------------------------------------------------------" +
                        "-----------------------------------------------------------------------------");
                break;
            case SHORTCLIP:
                System.out.printf(Constants.SHORTCLIP_PLAYLIST_HEADER, "Id", "Title", "Artist Name", "Description",
                        "Duration In Mins");
                System.out.println("-----------------------------------------------------------------------------------" +
                        "-------------------------------");
                break;
        }
        int lineNumber = 1;
        for (HashMap.Entry<String, T> entry : mediaMap.entrySet()) {
            entry.getValue().printDetails(lineNumber);
            lineNumber++;
        }
    }

    /**
     * Plays all media contents in the playlist.
     */
    public void playContents() {
        if (mediaMap.isEmpty()) {
            System.out.println("No " + mediaType.toString().toLowerCase() + " in the playlist to play.");
            return;
        }
        for (HashMap.Entry<String, T> entry : mediaMap.entrySet()) {
            System.out.println("-----------------------------------------------------------------------------------");
            try {
                entry.getValue().play();
            } catch (MediaNotFoundException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }

    /**
     * Returns the name of the playlist.
     *
     * @return The name of the playlist.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of media stored in the playlist.
     *
     * @return The media type.
     */
    public MediaType getMediaType() {
        return mediaType;
    }

    /**
     * Returns the file name associated with the playlist.
     *
     * @return The file name.
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the base file processor used to handle media file loading and saving.
     *
     * @param baseFileProcessor The file processor to be used.
     */
    public void setBaseFileHandler(BaseFileProcessor baseFileProcessor) {
        this.baseFileProcessor = baseFileProcessor;
    }

    /**
     * Sets the file handler used to manage file operations for the playlist.
     *
     * @param fileHandler The file handler to be used.
     */
    public void setFileHandler(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    /**
     * Sets the name of the playlist.
     *
     * @param name The new name of the playlist.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Removes a media item from the playlist by prompting the user for the media title.
     */
    private void removeMedia() {
        if (mediaMap.isEmpty()) {
            System.out.println("You can not remove media from an empty list.");
        } else {
            System.out.print("Enter the " + mediaType.toString().toLowerCase() + " to remove: ");
            String removeInput = Constants.keyboard.nextLine();
            String keyToRemove = null;
            for (String key : mediaMap.keySet()) {
                if (key.equalsIgnoreCase(removeInput)) {
                    keyToRemove = key;
                    break;
                }
            }
            if (keyToRemove != null) {
                mediaMap.remove(keyToRemove);
                System.out.println(Messages.MEDIA_REMOVED);
            } else {
                System.out.println("No such media found with title: " + removeInput);
            }
        }
    }

    /**
     * Creates a new song media item based on user input.
     *
     * @param title           The title of the song.
     * @param description     The description of the song.
     * @param duration        The duration of the song in minutes.
     * @param captionFileName The file name for song lyrics.
     * @param captionLines    The lyrics of the song.
     * @return The song object created.
     * @throws InvalidFormatException If the song genre format is invalid.
     */
    private T createSong(String title, String description, int duration, String captionFileName,
                         List<String> captionLines) throws InvalidFormatException {
        System.out.print(Messages.ENTER_GENRE);
        String input = Constants.keyboard.nextLine().toUpperCase();
        SongGenre genre = SongGenre.getFromInput(input);
        StringBuilder artistBuilder = new StringBuilder();
        String artistInput;
        do {
            System.out.print(Messages.ENTER_ARTIST_NAME);
            artistInput = Constants.keyboard.nextLine();
            if (!artistInput.equalsIgnoreCase(Constants.OPTION_QUIT)) {
                artistBuilder.append(artistInput).append(Constants.ARTIST_NAME_PARTITION);
            }
        } while (!artistInput.equalsIgnoreCase(Constants.OPTION_QUIT));
        if (!artistBuilder.isEmpty() && artistBuilder.charAt(artistBuilder.length() - 1) == '#') {
            artistBuilder.deleteCharAt(artistBuilder.length() - 1);
        }
        String artistNames = artistBuilder.toString();
        Media song = new Song(title, description, artistNames, genre, duration, captionFileName);
        song.setCaption(captionLines);
        return (T) song;
    }

    /**
     * Creates a new podcast media item based on user input.
     *
     * @param title           The title of the podcast.
     * @param description     The description of the podcast.
     * @param duration        The duration of the podcast in minutes.
     * @param captionFileName The file name for podcast captions.
     * @param captionLines    The captions of the podcast.
     * @return The podcast object created.
     * @throws InvalidFormatException If the podcast category format is invalid.
     */
    private T createPodcast(String title, String description, int duration, String captionFileName,
                            List<String> captionLines) throws InvalidFormatException {
        System.out.print(Messages.ENTER_CATEGORY);
        PodcastCategory category = PodcastCategory.getFromInput(Constants.keyboard.nextLine().toUpperCase());
        StringBuilder hostBuilder = new StringBuilder();
        String hostInput;
        do {
            System.out.print(Messages.ENTER_HOST_NAME);
            hostInput = Constants.keyboard.nextLine();
            if (!hostInput.equalsIgnoreCase(Constants.OPTION_QUIT)) {
                hostBuilder.append(hostInput);
                hostBuilder.append(Constants.ARTIST_NAME_PARTITION);
            }
        } while (!hostInput.equalsIgnoreCase(Constants.OPTION_QUIT));
        if (!hostBuilder.isEmpty() && hostBuilder.charAt(hostBuilder.length() - 1) == '#') {
            hostBuilder.deleteCharAt(hostBuilder.length() - 1);
        }
        String hostNames = hostBuilder.toString();
        System.out.print(Messages.ENTER_SERIES_NAME);
        String seriesName = Constants.keyboard.nextLine();
        System.out.print(Messages.ENTER_EPISODE_NO);
        int episodeNumber = Integer.parseInt(Constants.keyboard.nextLine());
        Media podcast = new Podcast(title, description, hostNames, category, seriesName,
                episodeNumber, duration, captionFileName);
        podcast.setCaption(captionLines);
        return (T) podcast;
    }

    /**
     * Creates a new short clip media item based on user input.
     *
     * @param title           The title of the short clip.
     * @param description     The description of the short clip.
     * @param duration        The duration of the short clip in minutes.
     * @param captionFileName The file name for short clip captions.
     * @param captionLines    The captions of the short clip.
     * @return The short clip object created.
     */
    private T createShortClip(String title, String description, int duration, String captionFileName,
                              List<String> captionLines) {
        System.out.print(Messages.ENTER_SINGLE_ARTIST_NAME);
        String artistName = Constants.keyboard.nextLine();
        ShortClip shortClip = new ShortClip(title, description, duration, captionFileName, artistName);
        shortClip.setCaption(captionLines);
        return (T) shortClip;
    }

    /**
     * Puts the media item into the playlist's media map.
     *
     * @param media The media item to add to the playlist.
     */
    private void putInPlaylist(T media) {
        mediaMap.put(media.getName(), media);
    }

}
