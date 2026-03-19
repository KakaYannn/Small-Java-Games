/**
 * Author: <Zhuofei Yan>
 * Student Id: <1638660>
 * Email: <zhuofeiyan@student.unimelb.edu.au>
 */

package fileHandleUtils;

import utils.Messages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The FileHandler class provides utility methods for reading from and writing to files.
 */
public class FileHandler {

    /**
     * Default constructor for the FileHandler class.
     */
    public FileHandler() {
    }

    /**
     * Reads the contents of a file.
     *
     * @param filePath      The path of the file to be read.
     * @param isCaptionFile A flag indicating if the file is a caption file. Displays a different error message accordingly.
     * @return A list of strings containing the file's contents, line by line.
     */
    public List<String> read(String filePath, boolean isCaptionFile) {
        List<String> result = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            if (isCaptionFile) {
                System.out.println(Messages.INVALID_MISSING_CAPTION);
            } else {
                System.out.println(Messages.INVALID_MISSING_FILE);
            }
        }
        return result;
    }

    /**
     * Saves the provided content to a file at the specified location.
     *
     * @param fileName The name of the file where the content will be saved.
     * @param lines    The content to be written to the file, provided as a StringBuilder.
     */
    public void save(String fileName, StringBuilder lines) {
        PrintWriter writer = null;
        try {
            File file = new File(fileName);
            writer = new PrintWriter(file);
            writer.print(lines);
            writer.flush();
        } catch (IOException e) {
            System.out.println(Messages.INVALID_MISSING_FILE);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
