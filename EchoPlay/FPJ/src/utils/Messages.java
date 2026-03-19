/**
 * Author: <Zhuofei Yan>
 * Student ID: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package utils;

/**
 * The Messages class serves as a centralized repository for predefined string messages
 * used throughout the Musify application.
 */
public final class Messages {

    /**
     * Message shown when no playlists are available.
     */
    public static final String NO_PLAYLIST_FOUND = "No playlists found.";

    /**
     * Message for invalid user input.
     */
    public static final String INVALID_INPUT = "Invalid Input";

    /**
     * Prompt for entering the name of a playlist.
     */
    public static final String ENTER_PLAYLIST_NAME = "Enter Playlist Name: ";

    /**
     * Prompt asking for the type of playlist to be entered.
     */
    public static final String ENTER_PLAYLIST_TYPE = "Enter Playlist Type: ";

    /**
     * Request for providing a filename to save a playlist.
     */
    public static final String ENTER_PLAYLIST_FILENAME =
            "Enter a filename to save the playlist: ";

    /**
     * Message shown when a specified playlist name is not found.
     */
    public static final String NO_PLAYLIST_IN_NAME =
            "No such playlist found with name: ";

    /**
     * Prompt for entering the title of a media item.
     */
    public static final String ENTER_MEDIA_TITLE = "Enter the title: ";

    /**
     * Prompt for providing a description of a media item.
     */
    public static final String ENTER_MEDIA_DESCRIPTION = "Enter the description: ";

    /**
     * Prompt asking for the duration of a media item in minutes.
     */
    public static final String ENTER_MEDIA_DURATION = "Enter duration in mins: ";

    /**
     * Request for entering a filename for media captions or lyrics.
     */
    public static final String ENTER_CAPTION_FILENAME =
            "Enter the filename for the captions or lyrics: ";

    /**
     * Prompt for adding an artist's name to a media item, with an option to quit.
     */
    public static final String ENTER_ARTIST_NAME =
            "Enter the artist Name or Q to stop entering the artist name: ";

    /**
     * Prompt asking for a host name when adding media, with an option to quit.
     */
    public static final String ENTER_HOST_NAME =
            "Enter the host Name or Q to stop entering the host name: ";

    /**
     * Prompt requesting a single artist's name.
     */
    public static final String ENTER_SINGLE_ARTIST_NAME = "Enter the artist Name: ";

    /**
     * Request for entering the category of a podcast.
     */
    public static final String ENTER_CATEGORY = "Add the category: ";

    /**
     * Prompt for specifying the genre of a song.
     */
    public static final String ENTER_GENRE = "Add the Genre: ";

    /**
     * Request to provide the series name of a podcast.
     */
    public static final String ENTER_SERIES_NAME = "Enter the series Name: ";

    /**
     * Request for entering a podcast episode number.
     */
    public static final String ENTER_EPISODE_NO = "Enter the episode Number: ";

    /**
     * Message confirming that a playlist was successfully removed.
     */
    public static final String PLAYLIST_REMOVED = "Playlist removed successfully.";

    /**
     * Message confirming that playlist data has been saved.
     */
    public static final String PLAYLIST_SAVED = "Playlist data saved.";

    /**
     * Message confirming that a media item was removed from a playlist.
     */
    public static final String MEDIA_REMOVED = "Media removed successfully.";

    /**
     * Message indicating a missing or invalid file.
     */
    public static final String INVALID_MISSING_FILE = "Invalid or missing file.";

    /**
     * Message indicating a missing or invalid caption file.
     */
    public static final String INVALID_MISSING_CAPTION =
            "Invalid or missing caption file.";

    /**
     * Message warning about an incorrectly formatted episode number,
     * resulting in the line being skipped.
     */
    public static final String INVALID_EPISODE_NUMBER =
            "Episode number not in correct format. Skipping this line.";

    /**
     * Message warning about an incorrectly formatted duration,
     * resulting in the line being skipped.
     */
    public static final String INVALID_DURATION =
            "Duration in mins not in correct format. Skipping this line.";

    /**
     * Template message used when media item details are incomplete,
     * resulting in the line being skipped.
     */
    public static final String INCOMPLETE_DETAILS =
            "%s details incomplete. Skipping this line.";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Messages() {
    }
}
