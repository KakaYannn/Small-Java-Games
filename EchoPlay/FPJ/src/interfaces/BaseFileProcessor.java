/**
 * Author: <Zhuofei Yan>
 * Student ID: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package interfaces;

import exceptions.InvalidFormatException;
import exceptions.InvalidLineException;
import model.playlist.media.Media;

import java.util.List;

/**
 * The BaseFileProcessor interface defines the operations required to process media files.
 * It provides methods for validating the file's column structure and parsing media objects from files.
 */
public interface BaseFileProcessor {

    /**
     * Checks whether the number of columns in a media file is valid.
     *
     * @param columnSize The actual number of columns.
     * @param mediaType  A string representing the type of media.
     * @throws InvalidLineException If the column is not valid, this exception will be thrown.
     */
    void validateColumnSize(int columnSize, String mediaType) throws InvalidLineException;

    /**
     * Reads and processes a media file from the given file path, returning a list of media objects.
     *
     * @param filePath The path to the media file to be read and processed.
     * @return A list of Media objects parsed from the file.
     * @throws InvalidFormatException If the file format is incorrect or incompatible.
     * @throws InvalidLineException   If the file contains improperly structured or invalid lines.
     */
    List<Media> process(String filePath) throws InvalidFormatException, InvalidLineException;
}
