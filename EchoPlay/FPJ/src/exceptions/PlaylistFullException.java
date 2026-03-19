/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package exceptions;

import enums.MediaType;

/**
 * PlaylistFullException is thrown when an attempt is made to add more media to a playlist that has reached its capacity.
 */
public class PlaylistFullException extends Exception {

    /**
     * Default constructor of a new PlaylistFullException .
     */
    public PlaylistFullException() {
    }

    /**
     * Constructs a new PlaylistFullException with a detailed message indicating the playlist is full.
     *
     * @param playlistName The name of the playlist that is full.
     * @param mediaType    The type of media that was attempted to be added to the full playlist.
     */
    public PlaylistFullException(String playlistName, MediaType mediaType) {
        super("Playlist " + playlistName + " is full. " +
                "You cannot add new " + mediaType.toString().toLowerCase() + " to this playlist.");
    }
}
