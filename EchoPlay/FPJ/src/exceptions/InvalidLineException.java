/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package exceptions;

/**
 * InvalidLineException indicates that a line of data or input contains an error, typically due to invalid
 * format or missing information.
 */
public class InvalidLineException extends Exception {

    /**
     * Default constructor of a new InvalidLineException.
     */
    public InvalidLineException() {
    }

    /**
     * Constructs a new InvalidLineException with the specified detail message.
     *
     * @param message The detail message that explains the reason for the exception.
     */
    public InvalidLineException(String message) {
        super(message);
    }
}
