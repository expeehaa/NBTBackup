package de.expeehaa.nbtbackup;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import static net.minecraft.server.command.CommandManager.literal;

public class NbtBackup implements ModInitializer {
    public static final String MOD_ID = "nbtbackup";
    public static final String MOD_NAME = "nbtbackup";

    private Config config;

    private final NbtBackupCommand nbtBackupCommand = new NbtBackupCommand(this);

    @Override
    public void onInitialize() {
        config = new Config();

        System.out.println("Config loaded.");

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(literal("backup").executes(nbtBackupCommand::run));
            dispatcher.register(literal("rconf").executes(cscs -> {
                config = new Config();
                System.out.println("Config reloaded.");
                return 0;
            }));
        });

        System.out.println("Commands registered.");
    }

    public Config getConfig(){
        return config;
    }
}