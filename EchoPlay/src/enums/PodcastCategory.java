/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package enums;

import exceptions.InvalidFormatException;
import utils.Messages;

/**
 * The PodcastCategory enum represents various categories for podcasts, such as EDUCATION, HEALTH, TECHNOLOGY, COMEDY, and NEWS.
 * It provides methods to retrieve a category from either a file input or user input, with validation.
 */
public enum PodcastCategory {
    /**
     * Represents educational podcasts.
     */
    EDUCATION,

    /**
     * Represents health-related podcasts.
     */
    HEALTH,

    /**
     * Represents technology-focused podcasts.
     */
    TECHNOLOGY,

    /**
     * Represents comedy podcasts.
     */
    COMEDY,

    /**
     * Represents news podcasts.
     */
    NEWS;

    /**
     * Retrieves the PodcastCategory from a file input string. If the input does not match a valid category,
     * it throws an InvalidFormatException.
     *
     * @param type The category type as a string read from a file.
     * @return The matching PodcastCategory.
     * @throws InvalidFormatException If the input does not match any valid category.
     */
    public static PodcastCategory getFromFileLine(String type) throws InvalidFormatException {
        PodcastCategory result = checkCategory(type);
        if (result == null) {
            throw new InvalidFormatException("Incorrect Category for Podcast. Skipping this line.");
        }
        return result;
    }

    /**
     * Retrieves the PodcastCategory from a user input string. If the input does not match a valid category,
     * it throws an InvalidFormatException.
     *
     * @param type The category type as a string input by the user.
     * @return The matching PodcastCategory.
     * @throws InvalidFormatException If the input does not match any valid category.
     */
    public static PodcastCategory getFromInput(String type) throws InvalidFormatException {
        PodcastCategory result = checkCategory(type);
        if (result == null) {
            throw new InvalidFormatException(Messages.INVALID_INPUT);
        }
        return result;
    }

    /**
     * Helper method to retrieve the PodcastCategory that matches a given string.
     * It performs a case-insensitive match against the defined enum values.
     *
     * @param type The category type as a string.
     * @return The matching PodcastCategory, or null if no match is found.
     */
    private static PodcastCategory checkCategory (String type) {
        for (PodcastCategory podcastCategory : PodcastCategory.values()) {
            if (podcastCategory.name().equalsIgnoreCase(type)) {
                return podcastCategory;
            }
        }
        return null;
    }
}
