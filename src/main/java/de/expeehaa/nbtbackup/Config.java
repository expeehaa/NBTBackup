package de.expeehaa.nbtbackup;

import java.io.File;
import java.nio.file.Path;

public class Config {
    private String backupFolderPath = "backups/";
    private BackupMethod backupMethod = BackupMethod.DIFF;

    public String getBackupFolderPath() {
        return backupFolderPath;
    }

    private File getBackupsFolder(){
        return new File(getBackupFolderPath());
    }

    public Path getBackupsPath(){
        return getBackupsFolder().toPath();
    }

    public Path getOrCreateBackupsPath() {
        File folder = getBackupsFolder();
        folder.mkdirs();

        return folder.toPath();
    }

    public BackupMethod getBackupMethod() {
        return backupMethod;
    }

    public enum BackupMethod {
        COPY,
        DIFF
    }
}
