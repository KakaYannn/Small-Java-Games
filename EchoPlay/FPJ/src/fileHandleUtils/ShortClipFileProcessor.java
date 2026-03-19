/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package fileHandleUtils;

import exceptions.InvalidLineException;
import interfaces.BaseFileProcessor;
import model.playlist.media.Media;
import model.playlist.media.ShortClip;
import utils.Constants;
import utils.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * The ShortClipFileProcessor class is responsible for processing and validating short clip media files.
 * It implements the BaseFileProcessor interface to provide functionality for reading, validating,
 * and creating ShortClip objects from file data.
 */
public class ShortClipFileProcessor implements BaseFileProcessor {

    private FileHandler fileHandler;

    /**
     * Constructs a ShortClipFileProcessor with the specified file handler for file operations.
     *
     * @param fileHandler The FileHandler used to read and write files.
     */
    public ShortClipFileProcessor(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    /**
     * Validates that the number of columns in the short clip file is valid.
     *
     * @param columnSize The actual number of columns in the file line.
     * @param mediaType  The type of media being processed.
     * @throws InvalidLineException If the column size does not match the expected size.
     */
    @Override
    public void validateColumnSize(int columnSize, String mediaType) throws InvalidLineException {
        int fixedColumnSize = 5;
        if (columnSize != fixedColumnSize) {
            String message = String.format(Messages.INCOMPLETE_DETAILS, mediaType);
            throw new InvalidLineException(message);
        }
    }

    /**
     * Processes the short clip data from the specified file path and returns a list of Media objects.
     * It validates the column size, parses the short clip data, and handles any formatting issues.
     *
     * @param filePath The path to the file containing short clip data.
     * @return A list of Media objects containing ShortClip information.
     */
    @Override
    public List<Media> process(String filePath) {
        List<Media> result = new ArrayList<>();

        List<String> playlistLines = fileHandler.read(filePath, false);

        for (String playlistLine : playlistLines) {
            if (playlistLine.isEmpty()) {
                continue;
            }

            String[] playListItems = playlistLine.split(",");
            try {
                validateColumnSize(playListItems.length, "ShortClip");
            } catch (InvalidLineException e) {
                System.out.println(e.getMessage());
                continue;
            }

            // Build media.
            String name = playListItems[Constants.FILE_COLUMN_ONE];
            String description = playListItems[Constants.FILE_COLUMN_TWO];
            String artistName = playListItems[Constants.FILE_COLUMN_THREE];

            int durationInMins;
            try {
                durationInMins = Integer.parseInt(playListItems[Constants.FILE_COLUMN_FOUR]);
            } catch (NumberFormatException e) {
                System.out.println(Messages.INVALID_DURATION);
                continue;
            }

            String captionFileName = playListItems[Constants.FILE_COLUMN_FIVE];
            Media media = new ShortClip(name, description, durationInMins, captionFileName, artistName);

            // Load caption.
            String captionFile = Constants.PATH_MEDIA_TEXT + media.getCaptionFileName();
            List<String> captionLines = fileHandler.read(captionFile, true);
            media.setCaption(captionLines);

            result.add(media);
        }
        return result;
    }
}
