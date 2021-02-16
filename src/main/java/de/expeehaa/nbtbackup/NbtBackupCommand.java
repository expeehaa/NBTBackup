package de.expeehaa.nbtbackup;

import com.mojang.brigadier.context.CommandContext;
import de.expeehaa.nbtbackup.core.BackupCreator;
import net.minecraft.server.command.ServerCommandSource;

public class NbtBackupCommand {
    private final NbtBackup nbtBackup;

    public NbtBackupCommand(NbtBackup nbtBackup){
        this.nbtBackup = nbtBackup;
    }

    public int run(CommandContext<ServerCommandSource> context) {
        try {
            new BackupCreator(nbtBackup.getConfig(), context.getSource()).create();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}
