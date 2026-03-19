/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package model.playlist.media;

import enums.PodcastCategory;
import exceptions.MediaNotFoundException;
import utils.Constants;

/**
 * The Podcast class represents a specific type of media.
 * It extends the Media class by introducing podcast-specific fields such as category,
 * series name, and episode number, along with relevant behavior.
 */
public class Podcast extends Media {

    private PodcastCategory category;
    private String seriesName;
    private int episodeNumber;

    /**
     * Default constructor for the Podcast class.
     */
    public Podcast() {
    }

    /**
     * Constructs a new Podcast object with the specified attributes.
     *
     * @param name            The name of the podcast.
     * @param description     The description of the podcast.
     * @param hostName        The host(s) of the podcast.
     * @param category        The category of the podcast.
     * @param seriesName      The name of the podcast series.
     * @param episodeNumber   The episode number of the podcast in the series.
     * @param durationInMins  The duration of the podcast in minutes.
     * @param captionFileName The filename containing captions for the podcast.
     */
    public Podcast(String name, String description, String hostName, PodcastCategory category, String seriesName,
                   int episodeNumber, int durationInMins, String captionFileName) {
        super(name, description, hostName, durationInMins, captionFileName);

        this.category = category;
        this.seriesName = seriesName;
        this.episodeNumber = episodeNumber;
    }

    /**
     * Plays the podcast by printing its details and captions if available.
     *
     * @throws MediaNotFoundException If the podcast captions cannot be found or displayed.
     */
    @Override
    public void play() throws MediaNotFoundException {
        System.out.printf("Playing Podcast: %s by %s for %s mins. This podcast is about %s%n", getName(),
                hostName.replaceAll(Constants.ARTIST_NAME_PARTITION, ","), getDurationInMins(), getDescription());
        if (getCaption() == null || getCaption().isEmpty()) {
            throw new MediaNotFoundException("Cannot show captions for podcast. Media not found.");
        }

        System.out.println("Here are the contents of the podcast.");
        for (String line : getCaption()) {
            System.out.println(line);
        }
    }

    /**
     * Prints the details of the podcast for display in a format.
     *
     * @param idNumber The ID number used to identify the podcast in a list.
     */
    @Override
    public void printDetails(int idNumber) {
        System.out.printf(Constants.PODCAST_DATA_FORMATTER, idNumber,
                name, hostName.replaceAll(Constants.ARTIST_NAME_PARTITION, ","), description, category, seriesName,
                episodeNumber, durationInMins);
    }

    /**
     * Writes the podcast's content back to a storage format.
     *
     * @param content The StringBuilder to which the podcast content is appended.
     */
    @Override
    public void writeBackContent(StringBuilder content) {
        content.append(name).append(",")
                .append(description).append(",")
                .append(hostName).append(",")
                .append(category.toString()).append(",")
                .append(seriesName).append(",")
                .append(episodeNumber).append(",")
                .append(durationInMins).append(",")
                .append(captionFileName);
    }
}
