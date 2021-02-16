package de.expeehaa.nbtbackup.core.filehandler;

import de.expeehaa.nbtbackup.core.filehandler.exception.FileHandlerException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyFileHandler extends AbstractFileHandler {
    @Override
    public void handleFile(Path original, Path compare, Path destination) throws FileHandlerException {
        try {
            if(compare == null || haveSameChecksums(original.toFile(), compare.toFile())){
                destination.toFile().getParentFile().mkdirs();
                Files.copy(original, destination);
            }
        } catch (IOException e) {
            throw new FileHandlerException(e);
        }
    }
}
