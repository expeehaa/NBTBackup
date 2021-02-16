package de.expeehaa.nbtbackup.core;

import de.expeehaa.nbtbackup.Config;
import de.expeehaa.nbtbackup.core.filehandler.CopyFileHandler;
import de.expeehaa.nbtbackup.core.filehandler.exception.FileHandlerException;
import de.expeehaa.nbtbackup.mixin.MixedMinecraftServer;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.storage.LevelStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.stream.Collectors;

public class BackupCreator {
    private Config config;
    private ServerCommandSource source;

    public BackupCreator(Config config, ServerCommandSource source) {
        this.config = config;
        this.source = source;
    }

    public void create() throws IOException, FileHandlerException {
        source.getMinecraftServer().getPlayerManager().broadcastChatMessage(new LiteralText("Starting backup."), MessageType.CHAT, Util.NIL_UUID);

        BackupHistory backupHistory = BackupHistory.BackupHistoryFactory.build(config, getLevelName());

        File worldFolder = getWorldFolder();
        File lastBackupFolder = backupHistory.getLastBackupFolder();
        File newBackupFolder = backupHistory.getBackupFolderForDate(new Date());

        if (lastBackupFolder == null) {
            backupByCopying(worldFolder, null, newBackupFolder);
        } else {
            switch (config.getBackupMethod()) {
                case COPY:
                    backupByCopying(worldFolder, lastBackupFolder, newBackupFolder);
                    break;
                case DIFF:
                    backupByDiffing(worldFolder, lastBackupFolder, newBackupFolder);
                    break;
            }
        }

        source.getMinecraftServer().getPlayerManager().broadcastChatMessage(new LiteralText("Backup done."), MessageType.CHAT, Util.NIL_UUID);
    }

    private void backupByCopying(File worldFolder, File compareFolder, File destinationFolder) throws IOException, FileHandlerException {
        CopyFileHandler copyFileHandler = new CopyFileHandler();

        for (Path p : Files.find(worldFolder.toPath(), Integer.MAX_VALUE, (filePath, fileAttr) -> fileAttr.isRegularFile(), FileVisitOption.FOLLOW_LINKS).collect(Collectors.toSet())) {
            Path relative = worldFolder.toPath().relativize(p);

            Path compare = compareFolder != null ? compareFolder.toPath().resolve(relative) : null;
            Path destination = destinationFolder.toPath().resolve(relative);

            copyFileHandler.handleFile(relative, compare, destination);
        }
    }

    private void backupByDiffing(File worldFolder, File compareFolder, File destinationFolder) throws IOException, FileHandlerException {
        DiffGenerator generator = new DiffGenerator();

        for (Path p : Files.find(worldFolder.toPath(), Integer.MAX_VALUE, (filePath, fileAttr) -> fileAttr.isRegularFile(), FileVisitOption.FOLLOW_LINKS).collect(Collectors.toSet())) {
            Path relative = worldFolder.toPath().relativize(p);

            Path compare = compareFolder.toPath().resolve(relative);
            Path destination = destinationFolder.toPath().resolve(relative);

            generator.generateDiffFile(relative, compare, destination);
        }
    }

    private LevelStorage.Session getSession() {
        return ((MixedMinecraftServer) source.getMinecraftServer()).getSession();
    }

    private File getWorldFolder() {
        return getSession().getWorldDirectory(RegistryKey.of(Registry.DIMENSION, DimensionType.OVERWORLD_REGISTRY_KEY.getValue()));
    }

    private String getLevelName() {
        return getSession().getDirectoryName();
    }
}
