package me.l6d;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileUnit {
    private static Logger logger = LogManager.getLogger("FileUnit");

    public static InputStream loadFileInputStream(String filePath){
        File f = new File(filePath);
        try {
            if(!f.exists()) {
                f.createNewFile();
            }
            return new BufferedInputStream(new FileInputStream(f));
        } catch (IOException e) {
            logger.error("File load fail.", e);
            return null;
        }
    }
}
