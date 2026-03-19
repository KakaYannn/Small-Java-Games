/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package model.playlist.media;

import exceptions.MediaNotFoundException;
import utils.Constants;

/**
 * The ShortClip class represents a type of media.
 * It extends the Media class and adds functionality tailored for managing and playing short clips.
 */
public class ShortClip extends Media {

    /**
     * Default constructor for the ShortClip class.
     */
    public ShortClip() {
    }

    /**
     * Constructs a new ShortClip object with the specified attributes.
     *
     * @param name            The name of the short clip.
     * @param description     The description of the short clip.
     * @param durationInMins  The duration of the short clip in minutes.
     * @param captionFileName The filename for captions or subtitles associated with the short clip.
     * @param hostName        The name of the artist or host associated with the short clip.
     */
    public ShortClip(String name, String description, int durationInMins, String captionFileName,
                     String hostName) {
        super(name, description, hostName, durationInMins, captionFileName);
    }

    /**
     * Plays the short clip by printing its details and displaying captions if available.
     *
     * @throws MediaNotFoundException If the captions for the short clip are not found.
     */
    @Override
    public void play() throws MediaNotFoundException {
        System.out.printf("Playing short clip: %s by %s for %s mins.%n", name, hostName, durationInMins);
        if (getCaption() == null || getCaption().isEmpty()) {
            throw new MediaNotFoundException("Cannot show captions for short clip. Media not found.");
        }

        System.out.println("Here are the contents of the short clip.");
        for (String line : getCaption()) {
            System.out.println(line);
        }
    }

    /**
     * Prints the details of the short clip for display in a format.
     *
     * @param idNumber The ID number used to identify the short clip in a list.
     */
    @Override
    public void printDetails(int idNumber) {
        System.out.printf(Constants.SHORTCLIP_DATA_FORMATTER, idNumber, name, hostName, description, durationInMins);
    }

    /**
     * Writes the short clip's content back to a storage format.
     *
     * @param content The StringBuilder to which the short clip content is appended.
     */
    @Override
    public void writeBackContent(StringBuilder content) {
        content.append(name).append(",")
                .append(description).append(",")
                .append(hostName).append(",")
                .append(durationInMins).append(",")
                .append(captionFileName);
    }
}
