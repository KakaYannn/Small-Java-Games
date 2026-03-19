/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package model.playlist.media;

import exceptions.MediaNotFoundException;
import interfaces.Playable;

import java.util.ArrayList;
import java.util.List;

/**
 * The Media class is an abstract representation of various types of media such as songs, podcasts, or
 * short clips. Media implements the Playable interface, ensuring that all media types can be played.
 */
public abstract class Media implements Playable {
    /**
     * The name of the media.
     */
    protected String name;

    /**
     * A brief description of the media.
     */
    protected String description;

    /**
     * The name of the host or artist associated with the media.
     */
    protected String hostName;

    /**
     * The duration of the media in minutes.
     */
    protected int durationInMins;

    /**
     * The filename that contains captions or lyrics for the media.
     */
    protected String captionFileName;

    /**
     * A list of strings representing the captions or lyrics for the media.
     */
    protected List<String> caption;

    /**
     * Default constructor for Media.
     */
    public Media() {
    }

    /**
     * Constructs a new Media object with the specified attributes.
     *
     * @param name            The name of the media.
     * @param description     The description of the media.
     * @param hostName        The host or artist name associated with the media.
     * @param durationInMins  The duration of the media in minutes.
     * @param captionFileName The filename containing captions or lyrics for the media.
     */
    public Media(String name, String description, String hostName, int durationInMins, String captionFileName) {
        this.name = name;
        this.description = description;
        this.hostName = hostName;
        this.durationInMins = durationInMins;
        this.captionFileName = captionFileName;
        this.caption = new ArrayList<>();
    }

    /**
     * Plays the media.
     *
     * @throws MediaNotFoundException If the media content cannot be found or played.
     */
    public abstract void play() throws MediaNotFoundException;

    /**
     * Prints detailed information about the media, including its unique ID.
     *
     * @param idNumber The unique identifier for the media, typically used for displaying in lists.
     */
    public abstract void printDetails(int idNumber);

    /**
     * Writes the media's content back to a storage format.
     *
     * @param content The StringBuilder to which the content is written.
     */
    public abstract void writeBackContent(StringBuilder content);

    /**
     * Returns the name of the media.
     *
     * @return The name of the media.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the media.
     *
     * @return The description of the media.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the duration of the media in minutes.
     *
     * @return The duration of the media in minutes.
     */
    public int getDurationInMins() {
        return durationInMins;
    }

    /**
     * Returns the filename for the captions or lyrics of the media.
     *
     * @return The caption filename.
     */
    public String getCaptionFileName() {
        return captionFileName;
    }

    /**
     * Returns the captions or lyrics of the media.
     *
     * @return A list of strings representing the captions or lyrics.
     */
    public List<String> getCaption() {
        return caption;
    }

    /**
     * Sets the captions or lyrics for the media.
     *
     * @param caption A list of strings representing the captions or lyrics.
     */
    public void setCaption(List<String> caption) {
        this.caption = caption;
    }

}
