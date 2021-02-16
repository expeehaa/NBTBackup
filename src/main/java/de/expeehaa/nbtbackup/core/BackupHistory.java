package de.expeehaa.nbtbackup.core;

import de.expeehaa.nbtbackup.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class BackupHistory {
    private final Config config;
    private final String backupName;
    private final Pattern backupFolderPathPattern;
    private final Path[] backupFolderPaths;
    private final HashMap<Path, BackupFileHistory> backupFileHistories;

    private BackupHistory(Config config, String backupName, Pattern backupFolderPathPattern, Path[] backupFolderPaths, HashMap<Path, BackupFileHistory> backupFileHistories) {
        this.config = config;
        this.backupName = backupName;
        this.backupFolderPathPattern = backupFolderPathPattern;
        this.backupFolderPaths = backupFolderPaths;
        this.backupFileHistories = backupFileHistories;
    }

    public File getLastBackupFolder() throws IOException {
        Optional<Path> lastBackupPath = backupFolderPaths.max(Comparator.naturalOrder());

        return lastBackupPath.map(Path::toFile).orElse(null);
    }

    public File getBackupFolderForDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

        return config.getBackupsPath().resolve(new File(backupName + "_" + sdf.format(date)).toPath()).toFile();
    }

    public static class BackupHistoryFactory {
        private final Config config;
        private final String backupName;

        private BackupHistoryFactory(Config config, String backupName) {
            this.config = config;
            this.backupName = backupName;
        }

        public static BackupHistory build(Config config, String backupName) throws IOException {
            BackupHistoryFactory factory = new BackupHistoryFactory(config, backupName);

            Pattern backupFolderPathPattern = factory.getBackupFolderPathPattern();
            Path[] backupFolderPaths = factory.getBackupFolderPaths(backupFolderPathPattern);
            SortedSet<Backup> backups = factory.getBackups(backupFolderPaths);
            HashMap<Path, BackupFileHistory> backupFileHistories = factory.getBackupFileHistories(backups);

            return new BackupHistory(config, backupName, backupFolderPathPattern, backupFolderPaths, backupFileHistories);
        }

        // Sorting Strings matching the Pattern must be equivalent to sorting backups by the time they were created.
        private Pattern getBackupFolderPathPattern() {
            return Pattern.compile("\\A" + Pattern.quote(backupName) + "_\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}\\z");
        }

        private Path[] getBackupFolderPaths(Pattern backupPattern) throws IOException {
            return Files.list(config.getBackupsPath()).filter(p -> p.toFile().isDirectory() && backupPattern.matcher(p.getFileName().toString()).find()).sorted().toArray(Path[]::new);
        }

        // key:   Path of the backup folder relative to the backups folder.
        // value: Paths of the files in the backup folder relative to the backup folder itself (therefore also relative to the world folder).
        private SortedSet<Backup> getBackups(Path[] backupPaths) throws IOException {
            SortedSet<Backup> backups = new TreeSet<>();

            for (Path backupPath : backupPaths) {
                Path[] paths = Files.walk(backupPath).filter(FileModificationAction::isFileModification).map(backupPath::relativize).toArray(Path[]::new);
                backups.add(new Backup(backupName, backupPath, paths));
            }

            return backups;
        }

        // key:   Path of a world file relative to the world folder (or a backup folder if it was deleted).
        // value: BackupFileHistory of the world file.
        private HashMap<Path, BackupFileHistory> getBackupFileHistories(SortedSet<Backup> backups) {
            backups.
        }
    }
}
