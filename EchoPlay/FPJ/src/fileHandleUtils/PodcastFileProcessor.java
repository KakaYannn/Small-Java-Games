/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package fileHandleUtils;

import enums.PodcastCategory;
import exceptions.InvalidFormatException;
import exceptions.InvalidLineException;
import interfaces.BaseFileProcessor;
import model.playlist.media.Media;
import model.playlist.media.Podcast;
import utils.Constants;
import utils.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * The PodcastFileProcessor class is responsible for processing and validating podcast media files.
 * It implements the BaseFileProcessor interface to provide functionality for reading, validating,
 * and creating Podcast objects from file data.
 */
public class PodcastFileProcessor implements BaseFileProcessor {

    private FileHandler fileHandler;

    /**
     * Constructs a PodcastFileProcessor with the specified file handler for file operations.
     *
     * @param fileHandler The FileHandler used to read and write files.
     */
    public PodcastFileProcessor(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    /**
     * Validates that the number of columns in the podcast file os valid.
     *
     * @param columnSize The actual number of columns in the file line.
     * @param mediaType  The type of media being processed.
     * @throws InvalidLineException If the column size does not match the expected size.
     */
    @Override
    public void validateColumnSize(int columnSize, String mediaType) throws InvalidLineException {
        int fixedColumnSize = 8;
        if (columnSize != fixedColumnSize) {
            String message = String.format(Messages.INCOMPLETE_DETAILS, mediaType);
            throw new InvalidLineException(message);
        }
    }

    /**
     * Processes the podcast data from the specified file path and returns a list of Media objects.
     * It validates the column size, parses the podcast data, and handles any formatting issues.
     *
     * @param filePath The path to the file containing podcast data.
     * @return A list of Media objects containing Podcast information.
     */
    @Override
    public List<Media> process(String filePath) {
        List<Media> result = new ArrayList<>();

        List<String> playlistLines = fileHandler.read(filePath, false);
        for (String playlistLine : playlistLines) {
            if (playlistLine.isEmpty()) {
                continue;
            }

            // Media column size validation.
            String[] playListItems = playlistLine.split(",");
            try {
                validateColumnSize(playListItems.length, "Podcast");
            } catch (InvalidLineException e) {
                System.out.println(e.getMessage());
                continue;
            }

            String name = playListItems[Constants.FILE_COLUMN_ONE];
            String description = playListItems[Constants.FILE_COLUMN_TWO];
            String hostName = playListItems[Constants.FILE_COLUMN_THREE];

            PodcastCategory category;
            try {
                category = PodcastCategory.getFromFileLine(playListItems[Constants.FILE_COLUMN_FOUR].toUpperCase());
            } catch (InvalidFormatException e) {
                System.out.println(e.getMessage());
                continue;
            }

            String seriesName = playListItems[Constants.FILE_COLUMN_FIVE];

            int episodeNumber;
            try {
                episodeNumber = Integer.parseInt(playListItems[Constants.FILE_COLUMN_SIX]);
            } catch (NumberFormatException e) {
                System.out.println(Messages.INVALID_EPISODE_NUMBER);
                continue;
            }

            int durationInMins;
            try {
                durationInMins = Integer.parseInt(playListItems[Constants.FILE_COLUMN_SEVEN]);
            } catch (NumberFormatException e) {
                System.out.println(Messages.INVALID_DURATION);
                continue;
            }

            String captionFileName = playListItems[Constants.FILE_COLUMN_EIGHT];
            Media media = new Podcast(name, description, hostName, category, seriesName, episodeNumber,
                    durationInMins, captionFileName);

            // Load caption.
            String captionFile = Constants.PATH_MEDIA_TEXT + media.getCaptionFileName();
            List<String> captionLines = fileHandler.read(captionFile, true);
            media.setCaption(captionLines);

            result.add(media);
        }
        return result;
    }
}
