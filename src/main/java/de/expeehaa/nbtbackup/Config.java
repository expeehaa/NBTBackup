package de.expeehaa.nbtbackup;

public class Config {
    private String backupFolderPath = "backups/";
    private BackupMethod backupMethod = BackupMethod.DIFF;

    public String getBackupFolderPath() {
        return backupFolderPath;
    }

    public BackupMethod getBackupMethod() {
        return backupMethod;
    }

    public enum BackupMethod {
        COPY,
        DIFF
    }
}
