package adapters.FileAdapter;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

class FileAdapterTest {
    private static String fileName;
    private static String filePath;
    private static File file;
    @org.junit.jupiter.api.BeforeAll
    static void setUp() {
        fileName="testfile.map";
        filePath = FileAdapter.BASE_PATH + fileName;
        file = new File(filePath);
        // Create the file
        try {
            assertTrue(file.createNewFile(), "Failed to create test file");
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }
    @Test
    void isFileExists_FileExists() {
        // Test the existence check when the file exists
        File existingFile = FileAdapter.isFileExists(fileName);
        assertNotNull(existingFile, "File does not exist");
        assertEquals(file.getAbsolutePath(), existingFile.getAbsolutePath(), "Incorrect file returned");

        //clean up
        assertTrue(file.delete(), "Failed to delete test file");
    }
    @Test
    void isFileExists_FileDoesNotExist() {
        //file is deleted in first test case.
       fileName="testfile.map";
       //function should return null when file does not exist;
        assertNull(FileAdapter.isFileExists(fileName));
    }
}