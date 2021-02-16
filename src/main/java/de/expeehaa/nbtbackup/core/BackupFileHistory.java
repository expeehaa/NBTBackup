package de.expeehaa.nbtbackup.core;

import java.nio.file.Path;
import java.util.HashMap;

public class BackupFileHistory {
    // Path relative to world folder.
    private Path filePath;
    // Path relative to backups folder.
    private HashMap<Path, FileModificationAction> files;

    private BackupFileHistory(Path filePath, HashMap<Path, FileModificationAction> files){
        this.filePath = filePath;
        this.files = files;
    }

    public static BackupFileHistory fromFilePath(BackupFileContext context, Path filePath){

    }

}
