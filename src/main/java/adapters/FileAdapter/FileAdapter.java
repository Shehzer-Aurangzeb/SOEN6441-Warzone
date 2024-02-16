package adapters.FileAdapter;

import java.io.File;

/**
 * Adapter class for file-related operations.
 */

public class FileAdapter {
    private static final String BASE_PATH = "src/main/resources/maps/";

    /**
     * Checks if a file exists in the specified directory.
     *
     * @param p_fileName The name of the file to check.
     * @return The File object if it exists, otherwise null.
     */
    public static File isFileExists(String p_fileName) {
        String l_fileName = p_fileName.endsWith(".map") ? p_fileName : p_fileName + ".map";
        File l_file = new File(BASE_PATH + l_fileName);
        if (l_file.exists()) return l_file;
        return null;
    }
}
