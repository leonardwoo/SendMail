package me.l6d;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileUtil {
    private static Logger logger = LogManager.getLogger(FileUtil.class.getName());

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

    public static Reader loadFileReader(String filePath) {
        return new BufferedReader(
                new InputStreamReader(
                        loadFileInputStream(filePath)));
    }

    public static OutputStream saveFileOutputStream(String filePath) {
        File f = new File(filePath);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            return new BufferedOutputStream(new FileOutputStream(f));
        } catch (IOException e) {
            logger.error("File write fail.", e);
            return null;
        }
    }

    public static void saveFileWrite(String filePath, String contect) {
        Writer writer = new BufferedWriter(new OutputStreamWriter(saveFileOutputStream(filePath)));
        try {
            writer.write(contect);
            writer.flush();
        } catch (IOException e) {
            logger.error("Contect write fail.", e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
    }
}
