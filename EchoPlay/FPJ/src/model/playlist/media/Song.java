/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package model.playlist.media;

import enums.SongGenre;
import exceptions.MediaNotFoundException;
import utils.Constants;

/**
 * The Song class represents a type of media for songs.
 * It extends the Media class and provides specific functionality for playing and managing songs.
 */
public class Song extends Media {

    private SongGenre genre;

    /**
     * Default constructor for the Song class.
     */
    public Song() {
    }

    /**
     * Constructs a new Song object with the specified attributes.
     *
     * @param name            The name of the song.
     * @param description     The description of the song.
     * @param hostName        The name of the artist or host associated with the song.
     * @param genre           The genre of the song (e.g., Pop, Rock, Jazz).
     * @param durationInMins  The duration of the song in minutes.
     * @param captionFileName The filename containing captions or lyrics for the song.
     */
    public Song(String name, String description, String hostName, SongGenre genre, int durationInMins,
                String captionFileName) {
        super(name, description, hostName, durationInMins, captionFileName);
        this.genre = genre;
    }

    /**
     * Plays the song by printing its details and displaying the lyrics if available.
     *
     * @throws MediaNotFoundException If the lyrics for the song are not found.
     */
    @Override
    public void play() throws MediaNotFoundException {
        System.out.printf("Playing Song: %s by %s for %s mins.%n", name,
                hostName.replaceAll(Constants.ARTIST_NAME_PARTITION, ","), durationInMins);
        if (getCaption() == null || getCaption().isEmpty()) {
            throw new MediaNotFoundException("Cannot show lyrics. Media not found.");
        }

        System.out.println("Here are the lyrics to sing along.");
        for (String line : getCaption()) {
            System.out.println(line);
        }
    }

    /**
     * Prints the details of the song for display in a format.
     *
     * @param idNumber The ID number used to identify the song in a list.
     */
    @Override
    public void printDetails(int idNumber) {
        System.out.printf(Constants.SONG_PLAYLIST_DATA_FORMATTER, idNumber, name,
                hostName.replaceAll(Constants.ARTIST_NAME_PARTITION, ","), description, genre, durationInMins);
    }

    /**
     * Writes the song's content back to a storage format.
     *
     * @param content The StringBuilder to which the song content is appended.
     */
    @Override
    public void writeBackContent(StringBuilder content) {
        content.append(name).append(",")
                .append(description).append(",")
                .append(hostName).append(",")
                .append(genre.toString()).append(",")
                .append(durationInMins).append(",")
                .append(captionFileName);
    }
}
