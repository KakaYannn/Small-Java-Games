/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package enums;

import exceptions.InvalidFormatException;
import utils.Messages;

/**
 * The SongGenre enum represents various genres of songs, such as JAZZ, POP, ROCK, CLASSICAL, HIP_HOP, and ELECTRONIC.
 * It includes methods to retrieve a genre from file input or user input with validation.
 */
public enum SongGenre {
    /**
     * Represents jazz music.
     */
    JAZZ,

    /**
     * Represents pop music.
     */
    POP,

    /**
     * Represents rock music.
     */
    ROCK,

    /**
     * Represents classical music.
     */
    CLASSICAL,

    /**
     * Represents hip-hop music.
     */
    HIP_HOP,

    /**
     * Represents electronic music.
     */
    ELECTRONIC;

    /**
     * Retrieves the SongGenre from a file input string. If the input does not match a valid genre,
     * it throws an InvalidFormatException.
     *
     * @param type The genre type as a string read from a file.
     * @return The matching SongGenre.
     * @throws InvalidFormatException If the input does not match any valid genre.
     */
    public static SongGenre getFromFileLine(String type) throws InvalidFormatException {
        SongGenre result = checkGenre(type);
        if (result == null) {
            throw new InvalidFormatException("Incorrect Genre for Song. Skipping this line.");
        }
        return result;
    }

    /**
     * Retrieves the SongGenre from a user input string. If the input does not match a valid genre,
     * it throws an InvalidFormatException.
     *
     * @param type The genre type as a string input by the user.
     * @return The matching SongGenre.
     * @throws InvalidFormatException If the input does not match any valid genre.
     */
    public static SongGenre getFromInput(String type) throws InvalidFormatException {
        SongGenre result = checkGenre(type);
        if (result == null) {
            throw new InvalidFormatException(Messages.INVALID_INPUT);
        }
        return result;
    }

    /**
     * Helper method to retrieve the SongGenre that matches a given string.
     * It performs a case-insensitive match against the defined enum values.
     *
     * @param type The genre type as a string.
     * @return The matching SongGenre, or null if no match is found.
     */
    private static SongGenre checkGenre (String type) {
        for (SongGenre songGenre : SongGenre.values()) {
            if (songGenre.name().equalsIgnoreCase(type)) {
                return songGenre;
            }
        }
        return null;
    }
}
