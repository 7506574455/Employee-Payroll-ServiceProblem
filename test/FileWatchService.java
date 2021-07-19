package test;

import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.StandardWatchEventKinds.*;
import java.util.HashMap;
import java.util.Map;


public class FileWatchService {
//    private static final WatchEvent.Kind ENTRY_CREATE = ;
    private final WatchService watcher;
    private final Map<WatchKey, Path> dirWatches;

    public FileWatchService(Path dir) throws IOException {
        this.watcher= (WatchService) FileSystems.getDefault().newWatchService();
        this.dirWatches = new HashMap<WatchKey,Path>();
        scanAndRegisterDirectories(dir);
    }
    private void registerDirWatchers(Path dir)throws IOException{
        WatchKey key = dir.register((WatchService) watcher,
                ENTRY_CREATE,
                ENTRY_DELETE,
                ENTRY_MODIFY);
        dirWatches.put(key,dir);
    }

    private void scanAndRegisterDirectories(final Path start) throws IOException {
        Files.walkFileTree(start,new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                registerDirWatchers(dir);
                return FileVisitResult.CONTINUE;

            }
        });

    }
    @SuppressWarnings({"rawtypes","unchecked"})
    public void processEvents() throws InterruptedException {
        while (true){
            WatchKey key;
            try {
                key = watcher.take();
            }catch (InterruptedException x){
                return;
            }

            Path dir = dirWatches.get(key);
            if(dir==null)continue;
            for (WatchEvent<?> event: key.pollEvents()){
                WatchEvent.Kind kind = event.kind();
                Path name = ((WatchEvent<Path>)event).context();
                Path child = dir.resolve(name);
                System.out.format("%s : %s\n", event.kind().name(),child);
                if (kind==ENTRY_CREATE){
                    try {
                        if(Files.isDirectory(child))
                            scanAndRegisterDirectories(child);
                    } catch (IOException e) {
                        System.out.println();
                    }
                }else if(kind.equals(ENTRY_DELETE)){
                    if(Files.isDirectory(child))
                        dirWatches.remove(key);

                }

            }
            boolean valid = key.reset();
            if (!valid){
                dirWatches.remove(key);
                if(dirWatches.isEmpty())
                    break;
            }

        }
    }

}