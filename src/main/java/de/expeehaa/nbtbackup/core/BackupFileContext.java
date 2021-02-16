package de.expeehaa.nbtbackup.core;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class BackupFileContext {
    private final HashMap<Path, ArrayList<Path>> files;

    public BackupFileContext(HashMap<Path, ArrayList<Path>> files) {
        this.files = files;
    }

    public HashMap<Path, ArrayList<Path>> getAndRemoveMatchingFiles(Path path) {
        HashMap<Path, Path[]> matches = new HashMap<>();

        for (Entry<Path, ArrayList<Path>> backupFiles : files.entrySet()){
            Arrays.stream(backupFiles.getValue()).
        }

        return matches;
    }
}
