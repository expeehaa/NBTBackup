package de.expeehaa.nbtbackup.core.filehandler;

import com.google.common.hash.Hashing;
import de.expeehaa.nbtbackup.core.filehandler.exception.FileHandlerException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class AbstractFileHandler {
    public abstract void handleFile(Path original, Path compare, Path destination) throws FileHandlerException;

    protected String readFileContent(Path file) throws IOException {
        if(file.toAbsolutePath().toFile().exists()){
            return new String(Files.readAllBytes(file));
        }else{
            return null;
        }
    }

    protected boolean haveSameChecksums(File file1, File file2) throws IOException {
        if(file1.exists() && file2.exists()){
            String hash1 = com.google.common.io.Files.hash(file1, Hashing.crc32()).toString();
            String hash2 = com.google.common.io.Files.hash(file2, Hashing.crc32()).toString();

            return hash1.equalsIgnoreCase(hash2);
        }else{
            return !file1.exists() && !file2.exists();
        }
    }
}
