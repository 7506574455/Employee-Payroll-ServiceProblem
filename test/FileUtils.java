package test;

import java.io.File;

public class FileUtils {
    public static boolean deleteFiles(File fileName) {
        File[] allContents = fileName.listFiles();
        if (allContents != null){
            for (File file : allContents)
                deleteFiles(file);
        }
        return fileName.delete();
    }
}
