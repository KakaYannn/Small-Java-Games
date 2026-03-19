/**
 * Author: <Zhuofei Yan>
 * Student ID: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package utils;

import java.util.Scanner;

/**
 * The Constants class defines a collection of static constants used throughout
 * the Musify application.
 */
public final class Constants {

    /**
     * A shared Scanner in the program.
     */
    public static final Scanner keyboard = new Scanner(System.in);

    // Formatting constants for displaying playlist and media information.

    /**
     * Format string for playlist table headers.
     */
    public static final String PLAYLIST_HEADER_FORMATTER = "|%2s|%10s|%30s|%n";

    /**
     * Format string for displaying playlist details.
     */
    public static final String PLAYLIST_FORMATTER = "|%2s|%10s|%30s|%n";

    /**
     * Header format for a song playlist table.
     */
    public static final String SONG_PLAYLIST_HEADER =
            "|%2s|%30s|%30s|%30s|%10s|%16s|%n";

    /**
     * Format string for displaying song playlist data.
     */
    public static final String SONG_PLAYLIST_DATA_FORMATTER =
            "|%2d|%30s|%30s|%30s|%10s|%16d|%n";

    /**
     * Header format for a podcast playlist table.
     */
    public static final String PODCAST_PLAYLIST_HEADER =
            "|%2s|%30s|%30s|%30s|%15s|%20s|%8s|%16s|%n";

    /**
     * Format string for displaying podcast data.
     */
    public static final String PODCAST_DATA_FORMATTER =
            "|%2d|%30s|%30s|%30s|%15s|%20s|%8d|%16d|%n";

    /**
     * Header format for a short clip playlist.
     */
    public static final String SHORTCLIP_PLAYLIST_HEADER =
            "|%2s|%30s|%30s|%30s|%16s|%n";

    /**
     * Format string for short clip playlist data.
     */
    public static final String SHORTCLIP_DATA_FORMATTER =
            "|%2d|%30s|%30s|%30s|%16d|%n";

    // File path constants.

    /**
     * Root directory for storing all application data.
     */
    public static final String PATH_ROOT = "data/";

    /**
     * Directory for saving playlist files.
     */
    public static final String PATH_PLAYLIST = PATH_ROOT + "playlist/";

    /**
     * Directory for storing media text files such as captions or lyrics.
     */
    public static final String PATH_MEDIA_TEXT = PATH_ROOT + "mediatext/";

    // User input options.

    /**
     * Option to add a new media item to a playlist.
     */
    public static final String OPTION_ADD = "A";

    /**
     * Option to quit the current operation.
     */
    public static final String OPTION_QUIT = "Q";

    /**
     * Option to create a new playlist.
     */
    public static final String OPTION_CREAT_NEW_PLAYLIST = "1";

    /**
     * Option to view all available playlists.
     */
    public static final String OPTION_VIEW_ALL_PLAYLIST = "2";

    /**
     * Option to view the contents of a specific playlist.
     */
    public static final String OPTION_VIEW_PLAYLIST_CONTENT = "3";

    /**
     * Option to remove an existing playlist.
     */
    public static final String OPTION_REMOVE_PLAYLIST = "4";

    /**
     * Option to modify the contents of a playlist.
     */
    public static final String OPTION_MODIFY_PLAYLIST = "5";

    /**
     * Option to play a selected playlist.
     */
    public static final String OPTION_PLAY_PLAYLIST = "6";

    /**
     * Option to exit the application.
     */
    public static final String OPTION_EXIT = "7";

    // Other constants.

    /**
     * The required number of command-line arguments to initialize the application.
     */
    public static final int VALID_ARGS_LENGTH = 2;

    /**
     * The maximum number of media items allowed in a playlist.
     */
    public static final int MAX_MEDIA_COUNT = 5;

    /**
     * Character used to separate multiple artist names.
     */
    public static final String ARTIST_NAME_PARTITION = "#";

    // File column indices for data parsing.

    /**
     * File column with index 0.
     */
    public static final int FILE_COLUMN_ONE = 0;

    /**
     * File column with index 1.
     */
    public static final int FILE_COLUMN_TWO = 1;

    /**
     * File column with index 2.
     */
    public static final int FILE_COLUMN_THREE = 2;

    /**
     * File column with index 3.
     */
    public static final int FILE_COLUMN_FOUR = 3;

    /**
     * File column with index 4.
     */
    public static final int FILE_COLUMN_FIVE = 4;

    /**
     * File column with index 5.
     */
    public static final int FILE_COLUMN_SIX = 5;

    /**
     * File column with index 6.
     */
    public static final int FILE_COLUMN_SEVEN = 6;

    /**
     * File column with index 7.
     */
    public static final int FILE_COLUMN_EIGHT = 7;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Constants() {
    }
}
