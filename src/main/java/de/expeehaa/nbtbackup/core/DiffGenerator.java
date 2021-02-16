package de.expeehaa.nbtbackup.core;

import de.expeehaa.nbtbackup.core.filehandler.AbstractFileHandler;
import de.expeehaa.nbtbackup.core.filehandler.CopyFileHandler;
import de.expeehaa.nbtbackup.core.filehandler.JsonFileHandler;
import de.expeehaa.nbtbackup.core.filehandler.NbtFileHandler;
import de.expeehaa.nbtbackup.core.filehandler.exception.FileHandlerException;

import java.nio.file.Path;
import java.util.regex.Pattern;

public class DiffGenerator {
    private static final String PATCH_EXTENSION = ".nbtpatch";

    private final JsonFileHandler jsonDiffHandler = new JsonFileHandler();
    private final NbtFileHandler nbtDiffHandler = new NbtFileHandler();
    private final CopyFileHandler copyFileHandler = new CopyFileHandler();

    public void generateDiffFile(Path original, Path compare, Path destination) throws FileHandlerException {
        destination = Path.of(destination.toString() + PATCH_EXTENSION);
        AbstractFileHandler fileHandler = getFileHandler(original);

        fileHandler.handleFile(original, compare, destination);
    }

    private AbstractFileHandler getFileHandler(Path original){
        String filename = original.getFileName().toString();

        if(Pattern.compile("\\.json\\z").matcher(filename).find()){
            return jsonDiffHandler;
        }else if(Pattern.compile("\\.mca\\z").matcher(filename).find()){
            return nbtDiffHandler;
        }else if(Pattern.compile("\\.dat\\z").matcher(filename).find()){
            return nbtDiffHandler;
        }else{
            return copyFileHandler;
        }
    }
}
