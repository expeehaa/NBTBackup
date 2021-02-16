package de.expeehaa.nbtbackup.core;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public enum FileModificationAction {
    ADDS(".add.nbtbackup"),
    CHANGES(".diff.nbtbackup"),
    REMOVES(".rm.nbtbackup");

    private final String filenameExtension;

    FileModificationAction(String filenameExtension) {
        this.filenameExtension = filenameExtension;
    }

    public static boolean isFileModification(Path p) {
        return Files.isRegularFile(p) && Arrays.stream(FileModificationAction.values()).map(fma -> fma.filenameExtension).allMatch(p.getFileName()::endsWith);
    }
}
