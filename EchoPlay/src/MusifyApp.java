/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

import exceptions.InvalidFormatException;
import exceptions.InvalidLineException;
import exceptions.PlaylistFullException;
import fileHandleUtils.FileHandler;
import model.ListOfPlaylists;
import model.playlist.Playlist;
import model.playlist.media.Media;
import utils.Constants;
import utils.Messages;

import java.io.FileNotFoundException;

/**
 * The MusifyApp class is the entry of application, which allows user to manipulate its playlist.
 * It achieve the interaction between the user and Musify app
 * through a command-line interface and manages playlist operations.
 */
public class MusifyApp {

    private ListOfPlaylists listOfPlaylists;

    /**
     * Default constructor for the MusifyApp class.
     * This constructor is typically used when the application is launched without command-line arguments.
     */
    public MusifyApp() {
    }

    /**
     * The entry point of the Musify application. It sets up playlist data,
     * displays a welcome message, and initiates the main menu for user interaction.
     *
     * @param args Command-line arguments inputted at the beginning for initializing the name of the
     *             user and list of playlists information.
     */
    public static void main(String[] args) {
        MusifyApp app = new MusifyApp();
        app.handleFiles(args);
        app.displayWelcomeMessage(args);
        app.runMainMenu(args);
    }

    /**
     * Initializes the necessary files for the application, either loading an existing list of
     * playlists file or starting with a new list of playlists to allow the user to add new playlist.
     *
     * @param args Command-line arguments. If two arguments are provided, they represent the username
     *             and the list of playlists filename.
     */
    private void handleFiles(String[] args) {

        if (args.length == Constants.VALID_ARGS_LENGTH) {
            String userName = args[0];
            String playlistFileName = args[1];
            String playlistFilePath = Constants.PATH_ROOT + playlistFileName;
            listOfPlaylists = new ListOfPlaylists(userName, playlistFilePath, new FileHandler());
            try {
                listOfPlaylists.loadFromFile();
            } catch (InvalidLineException e) {
                System.out.println(e.getMessage());
            }
        } else {
            listOfPlaylists = new ListOfPlaylists();
        }

    }

    /**
     * Manages the primary menu for user interactions, offering options to
     * create, view, modify, and manage playlists.
     *
     * @param args Command-line arguments inputted by the user at the beginning.
     *             If no arguments are provided, an empty playlist is initialized.
     */
    private void runMainMenu(String[] args) {
        boolean runMenu = true;
        while (runMenu) {
            printMainMenu();
            try {
                String mainMenuSelection = Constants.keyboard.nextLine();
                switch (mainMenuSelection) {
                    case Constants.OPTION_CREAT_NEW_PLAYLIST: {
                        listOfPlaylists.createNewPlaylist();
                        break;
                    }
                    case Constants.OPTION_VIEW_ALL_PLAYLIST: {
                        if (isPlaylistsEmpty()) {
                            System.out.println(Messages.NO_PLAYLIST_FOUND);
                            break;
                        }
                        listOfPlaylists.viewAllPlaylist();
                        break;
                    }
                    case Constants.OPTION_EXIT: {
                        if (args.length != Constants.VALID_ARGS_LENGTH) {
                            listOfPlaylists.setPlaylistFilePath(Constants.PATH_ROOT + "playlists.txt");
                        }
                        if (!listOfPlaylists.getPlaylists().isEmpty()) {
                            listOfPlaylists.writeBack();
                        }

                        System.out.println("Exiting Musify. Goodbye, " + listOfPlaylists.getUserName() + ".");
                        runMenu = false;
                        break;
                    }
                    default: {
                        if (isPlaylistsEmpty()) {
                            System.out.println(Messages.NO_PLAYLIST_FOUND);
                            break;
                        }
                        System.out.print(Messages.ENTER_PLAYLIST_NAME);
                        String playlistNameInput = Constants.keyboard.nextLine();
                        Playlist<? extends Media> playlist = listOfPlaylists.getPlaylistByName(playlistNameInput);
                        if (playlist == null) {
                            System.out.println(Messages.NO_PLAYLIST_IN_NAME + playlistNameInput);
                            break;
                        }
                        switch (mainMenuSelection) {
                            case Constants.OPTION_VIEW_PLAYLIST_CONTENT:
                                playlist.viewMedia();
                                break;
                            case Constants.OPTION_REMOVE_PLAYLIST:
                                listOfPlaylists.removePlaylist(playlist);
                                break;
                            case Constants.OPTION_MODIFY_PLAYLIST:
                                playlist.modifyPlaylistMenu();
                                break;
                            case Constants.OPTION_PLAY_PLAYLIST:
                                playlist.playContents();
                                break;
                            default:
                                System.out.println(Messages.INVALID_INPUT);
                        }
                    }
                }
            } catch (FileNotFoundException | InvalidFormatException | PlaylistFullException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Prints the main menu options for the user.
     */
    private void printMainMenu() {
        System.out.println("Please select one of the options.");
        System.out.println("1. Create a new playlist.");
        System.out.println("2. View all playlist.");
        System.out.println("3. View contents of a playlist.");
        System.out.println("4. Remove a playlist.");
        System.out.println("5. Modify a playlist.");
        System.out.println("6. Play contents of a playlist.");
        System.out.println("7. Exit Musify.");
    }

    /**
     * Checks whether the list of playlists is empty.
     *
     * @return true if the list of playlists is empty.
     */
    private boolean isPlaylistsEmpty() {
        return listOfPlaylists.getPlaylists().isEmpty();
    }

    /**
     * Displays a welcome message to the user. If no playlist data was loaded from files,
     * the message informs the user accordingly.
     *
     * @param args Command-line arguments passed to determine whether playlist data was loaded.
     */
    private void displayWelcomeMessage(String[] args) {
        if (args.length == 0) {
            System.out.print("No Playlist data found to load.\n" +
                    "Welcome " + listOfPlaylists.getUserName() + ". Choose your music, podcasts or watch short clips.");
        } else {
            System.out.print("Data loading complete.\n" +
                    "Welcome " + listOfPlaylists.getUserName() + ". Choose your music, podcasts or watch short clips.");
        }
    }

}

