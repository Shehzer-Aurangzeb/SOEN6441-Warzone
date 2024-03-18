package adapters.FileAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Adapter class for file-related operations.
 */

public class FileAdapter {
    public static final String BASE_PATH = "src/main/resources/maps/";
    private static PrintWriter pw;

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

    /**
     * Creates a new File instance for a file with the specified filename in the predefined base path.
     *
     * @param p_fileName The name of the file to be created. If it does not end with ".map", the extension is appended.
     * @return A File object representing the file to be created in the predefined base path.
     */
    public static File createFile(String p_fileName){
        String l_fileName= p_fileName.endsWith(".map")?p_fileName:p_fileName+".map";
        return new File(BASE_PATH + l_fileName);
    }
}
