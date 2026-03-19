/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */
package model;

import enums.MediaType;
import exceptions.InvalidFormatException;
import exceptions.InvalidLineException;
import exceptions.PlaylistFullException;
import fileHandleUtils.*;
import interfaces.Storable;
import model.playlist.Playlist;
import model.playlist.media.Media;
import utils.Constants;
import utils.Messages;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ListOfPlaylists class contains a collection of Playlist objects.
 * It provides methods to load, view, create, and manage playlists, as well as handle file operations.
 */
public class ListOfPlaylists implements Storable {
    private String userName;
    private List<Playlist<? extends Media>> playlists;
    private String playlistFilePath;
    private FileHandler fileHandler;

    /**
     * Default constructor.
     * Initializes the username as "Stranger" and an empty list of playlists if no arguments provided by the
     * user at the beginning.
     */
    public ListOfPlaylists() {
        this.userName = "Stranger";
        this.playlists = new ArrayList<>();
        this.playlistFilePath = "";
        this.fileHandler = new FileHandler();
    }

    /**
     * Constructor that initializes a ListOfPlaylists with the provided username, file path,
     * and file handler.
     *
     * @param userName         The username associated with the list of playlists.
     * @param playlistFilePath The file path where the playlists data is stored.
     * @param fileHandler      The file handler to manage file operations.
     */
    public ListOfPlaylists(String userName, String playlistFilePath, FileHandler fileHandler) {
        this.userName = userName;
        this.playlists = new ArrayList<>();
        this.playlistFilePath = playlistFilePath;
        this.fileHandler = fileHandler;
    }

    /**
     * Loads each playlist data which is a single line from the file.
     * This method also call the function of Playlist class to load the media content for each playlist
     * from their respective files.
     *
     * @throws InvalidLineException If the data format in the file is invalid.
     */
    @Override
    public void loadFromFile() throws InvalidLineException {
        List<String> lines = fileHandler.read(playlistFilePath, false);
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            try {
                String[] items = line.split(",");
                if (items.length != 3) {
                    throw new InvalidLineException("Invalid Playlist data. Skipping this line.");
                }
                String playlistName = items[0];
                MediaType mediaType = MediaType.getFromFileLine(items[1].toUpperCase());
                String fileName = items[2];
                Playlist<? extends Media> currentPlaylist = new Playlist<>(playlistName, mediaType, fileName);
                setPlaylistFileHandler(currentPlaylist);
                this.playlists.add(currentPlaylist);
            } catch (InvalidFormatException e) {
                System.out.println("Incorrect Media Type. Skipping this line.");
            } catch (InvalidLineException e) {
                System.out.println(e.getMessage());
            }
        }

        for (Playlist<? extends Media> playlist : playlists) {
            playlist.loadFromFile();
        }
    }

    /**
     * Displays all playlists in a formatted list.
     * If no playlists are found, a message is printed indicating the absence of playlists.
     */
    public void viewAllPlaylist() {
        if (playlists.isEmpty()) {
            System.out.println(Messages.NO_PLAYLIST_FOUND);
            return;
        }
        System.out.println("Here are your playlists-");
        System.out.printf(Constants.PLAYLIST_HEADER_FORMATTER, "#", "Type", "Playlist Name");
        System.out.println("----------------------------------------------");

        int lineNumber = 1;
        for (Playlist<? extends Media> playlist : playlists) {
            System.out.printf(Constants.PLAYLIST_FORMATTER, lineNumber, playlist.getMediaType(), playlist.getName());
            lineNumber++;
        }
    }

    /**
     * Prompts the user to create a new playlist by entering its name, type, and filename.
     *
     * @throws InvalidFormatException If the playlist type is not valid.
     * @throws PlaylistFullException  If the playlist exceeds the maximum capacity.
     */
    public void createNewPlaylist() throws InvalidFormatException, PlaylistFullException {
        System.out.print(Messages.ENTER_PLAYLIST_NAME);
        String name = Constants.keyboard.nextLine();
        System.out.print(Messages.ENTER_PLAYLIST_TYPE);
        MediaType mediaType = MediaType.getFromInput(Constants.keyboard.nextLine().toUpperCase());
        System.out.print(Messages.ENTER_PLAYLIST_FILENAME);
        String filename = Constants.keyboard.nextLine();
        Playlist<? extends Media> newPlaylist = new Playlist<>(name, mediaType, filename);
        setPlaylistFileHandler(newPlaylist);
        playlists.add(newPlaylist);
        System.out.print("Add some " + mediaType + " to your Playlist.\n");

        boolean runLoop = true;
        while (runLoop) {
            System.out.print("Enter A to add a " + mediaType + " to the playlist or Q to quit adding: ");
            String selection = Constants.keyboard.nextLine().toUpperCase();
            switch (selection) {
                case Constants.OPTION_ADD:
                    newPlaylist.addNewMedia();
                    break;
                case Constants.OPTION_QUIT:
                    runLoop = false;
                    break;
                default:
                    System.out.println(Messages.INVALID_INPUT);
                    break;
            }
        }
    }

    /**
     * Removes a playlist from the list of playlists.
     *
     * @param playlist The Playlist object to be removed.
     */
    public void removePlaylist(Playlist<? extends Media> playlist) {
        playlists.remove(playlist);
        System.out.println(Messages.PLAYLIST_REMOVED);
    }

    /**
     * Writes the playlist data back to the file.
     * Each playlist is saved to its respective file, and the overall playlist data is saved to the
     * specified path.
     *
     * @throws FileNotFoundException If the file to write to is not found.
     */
    @Override
    public void writeBack() throws FileNotFoundException {
        StringBuilder playlistContent = new StringBuilder();
        int i = 1;
        for (Playlist<? extends Media> playlist : playlists) {
            StringBuilder sb = new StringBuilder();
            sb.append(playlist.getName())
                    .append(",")
                    .append(playlist.getMediaType().toString())
                    .append(",")
                    .append(playlist.getFileName());
            if (i < playlists.size()) {
                sb.append("\n");
            }
            playlistContent.append(sb);
            playlist.writeBack();
            i++;
        }
        fileHandler.save(playlistFilePath, playlistContent);
        System.out.println(Messages.PLAYLIST_SAVED);
    }

    /**
     * Retrieves a playlist by its name.
     *
     * @param input The name of the playlist to search for.
     * @return The Playlist object if found, or null if not found.
     */
    public Playlist<? extends Media> getPlaylistByName(String input) {
        for (Playlist<? extends Media> playlist : playlists) {
            if (playlist.getName().equalsIgnoreCase(input)) {
                return playlist;
            }
        }
        return null;
    }

    /**
     * Gets the username associated with the playlist collection.
     *
     * @return The username as a String.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Retrieves the list of playlists.
     *
     * @return A List of Playlist objects.
     */
    public List<Playlist<? extends Media>> getPlaylists() {
        return playlists;
    }

    /**
     * Sets the file path for saving the playlist data.
     *
     * @param playlistFilePath The file path as a String.
     */
    public void setPlaylistFilePath(String playlistFilePath) {
        this.playlistFilePath = playlistFilePath;
    }

    /**
     * Sets the appropriate file handler for the playlist based on its media type.
     *
     * @param playlist The Playlist object whose file handler is being set.
     */
    private void setPlaylistFileHandler(Playlist<? extends Media> playlist) {
        playlist.setFileHandler(fileHandler);
        switch (playlist.getMediaType()) {
            case MediaType.PODCAST:
                playlist.setBaseFileHandler(new PodcastFileProcessor(fileHandler));
                break;
            case MediaType.SHORTCLIP:
                playlist.setBaseFileHandler(new ShortClipFileProcessor(fileHandler));
                break;
            case MediaType.SONG:
                playlist.setBaseFileHandler(new SongFileProcessor(fileHandler));
                break;
        }
    }


}

