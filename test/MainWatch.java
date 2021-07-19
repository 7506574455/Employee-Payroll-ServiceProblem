package test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MainWatch {
    public static void main(String[] args) throws IOException, InterruptedException {
        String HOME ="/home/sanket/IdeaProjects/File-IO/data";
        String TEMP ="/temp";
        Path dir = Paths.get("c:/temp");
        new FileWatchService(dir).processEvents();
    }
}

