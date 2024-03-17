package log;

import utils.Observer;

import java.io.*;

/**
 * A class implementing Observer which observes LogEntryBuffer and writes to log file
 */
public class LogEntryWriter implements Observer, Serializable {
    /**
     * File name for logger
     */
    private String l_Filename = "logs";

    /**
     * log entry writer
     */
    public LogEntryWriter() {
        clearLogs();
    }

    /**
     * A function to receive the update from Subject. It then sends the message to be written to LogFile.
     *
     * @param p_s the message to be updated
     */
    public void update(String p_s) {
        writeLogFile(p_s);
    }

    /**
     * A function to write the actions of the game to a logfile named demolog.
     *
     * @param p_str The message to be written to the log file.
     */
    public void writeLogFile(String p_str) {
        PrintWriter l_WriteData = null;
        try {
            l_WriteData = new PrintWriter(new BufferedWriter(new FileWriter("src/main/resources/logFiles/" + l_Filename + ".txt", true)));
            l_WriteData.println(p_str);

        } catch (Exception p_Exception) {
            System.out.println(p_Exception.getMessage());
        } finally {
            l_WriteData.close();
        }
    }

    /**
     * This method is used to clear the log file before a new game starts.
     */
    @Override
    public void clearLogs() {
        try {
            File l_File = new File("src/main/logFiles/" + l_Filename + ".txt");
            if (l_File.exists()) {
                l_File.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}