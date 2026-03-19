/**
 * Author: <Zhuofei Yan>
 * Student ID: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package exceptions;

/**
 * The InvalidFormatException indicates that an error has occurred due to invalid data or file format.
 */
public class InvalidFormatException extends Exception {

    /**
     * Default constructor of a new InvalidFormatException .
     */
    public InvalidFormatException() {
    }

    /**
     * Creates a new InvalidFormatException with the specified detail message.
     *
     * @param message A string that provides additional information about the exception.
     */
    public InvalidFormatException(String message) {
        super(message);
    }
}
