/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package interfaces;

import exceptions.InvalidLineException;

import java.io.FileNotFoundException;

/**
 * The Storable interface defines methods for objects to be loaded and written back to the original file.
 * Classes implementing this interface need to manage their own storage and retrieval operations.
 */
public interface Storable {

    /**
     * Loads the data from the file.
     *
     * @throws InvalidLineException If the data in the file is not in a valid format.
     */
    void loadFromFile() throws InvalidLineException;

    /**
     * Writes the data back to the file.
     *
     * @throws FileNotFoundException If the file cannot be found or created for writing.
     */
    void writeBack() throws FileNotFoundException;
}
