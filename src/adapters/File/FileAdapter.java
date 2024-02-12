package adapters.File;

import java.io.File;


public class FileAdapter {
    static String  basePath= "src/resources/maps/";
    public static File isFileExists(String p_fileName){
        String l_fileName = p_fileName.endsWith(".map") ? p_fileName : p_fileName + ".map";
        File l_file= new File(basePath+l_fileName);
        if(l_file.exists()) return l_file;
        return null;
    }
}
