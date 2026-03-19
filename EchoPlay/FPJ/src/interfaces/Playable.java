/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package interfaces;

import exceptions.MediaNotFoundException;

/**
 * The Playable interface defines the method for media items that can be played.
 */
public interface Playable {

    /**
     * Plays the media content.
     *
     * @throws MediaNotFoundException If the media content cannot be found or loaded.
     */
    void play() throws MediaNotFoundException;
}
