/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package exceptions;

/**
 * MediaNotFoundException indicates that a requested media file or media content (such as captions, lyrics, or a media resource) cannot be found.
 */
public class MediaNotFoundException extends Exception {

    /**
     * Default constructor of a new MediaNotFoundException.
     */
    public MediaNotFoundException() {
    }

    /**
     * Constructs a new MediaNotFoundException with the specified detail message.
     *
     * @param message The detail message that explains the reason for the exception.
     */
    public MediaNotFoundException(String message) {
        super(message);
    }
}
