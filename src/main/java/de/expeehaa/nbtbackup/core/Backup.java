package de.expeehaa.nbtbackup.core;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class Backup implements Comparable {
    private final String backupName;
    private final Path backupPath;
    // Path of all files in the backup relative to backupPath.
    private final Path[] backupFilePaths;

    public Backup(String backupName, Path backupPath, Path[] backupFilePaths) {
        this.backupName = backupName;
        this.backupPath = backupPath;
        this.backupFilePaths = backupFilePaths;
    }

    public Path getBackupPath() {
        return backupPath;
    }

    public Path[] getBackupFilePaths() {
        return backupFilePaths;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return compareTo((Backup) o);
    }

    public int compareTo(@NotNull Backup o){
        return backupPath.getFileName().compareTo(o.backupPath.getFileName());
    }
}
