/**
 * Author: <Zhuofei Yan>
 * Student ID: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package enums;

import exceptions.InvalidFormatException;
import utils.Messages;

/**
 * The MediaType enum defines the different types of media supported in the system:
 * SONG, PODCAST, and SHORTCLIP. It includes methods to validate and retrieve media types
 * based on input from files or user input.
 */
public enum MediaType {

    /**
     * Represents music-related media, such as songs or tracks.
     */
    SONG,

    /**
     * Represents podcast content, often structured into episodes or series.
     */
    PODCAST,

    /**
     * Represents brief video clips or other short-form media content.
     */
    SHORTCLIP;

    /**
     * Retrieves a MediaType from a string input read from a file.
     *
     * @param type A string representing the media type read from a file.
     * @return The corresponding MediaType.
     * @throws InvalidFormatException If the input string does not match any valid media type.
     */
    public static MediaType getFromFileLine(String type) throws InvalidFormatException {
        MediaType result = checkType(type);
        if (result == null) {
            throw new InvalidFormatException("Incorrect Media Type. Skipping this line.");
        }
        return result;
    }

    /**
     * Retrieves a MediaType based on user input.
     *
     * @param type A string input provided by the user.
     * @return The corresponding {@code MediaType}.
     * @throws InvalidFormatException If the input string is invalid or does not match a known media type.
     */
    public static MediaType getFromInput(String type) throws InvalidFormatException {
        MediaType result = checkType(type);
        if (result == null) {
            throw new InvalidFormatException(Messages.INVALID_INPUT);
        }
        return result;
    }

    /**
     * Helper method that checks if the given string matches any defined {@code MediaType} value.
     * The comparison is case-insensitive.
     *
     * @param type A string representing the media type to be checked.
     * @return The matching {@code MediaType}, or {@code null} if no match is found.
     */
    private static MediaType checkType(String type) {
        for (MediaType mediaType : MediaType.values()) {
            if (mediaType.name().equalsIgnoreCase(type)) {
                return mediaType;
            }
        }
        return null;
    }
}
