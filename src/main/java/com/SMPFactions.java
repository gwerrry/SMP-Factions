package com;

import com.commands.FactionChatCommand;
import com.commands.FactionCommand;
import com.factions.FactionManager;
import com.listeners.ChatListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class SMPFactions extends JavaPlugin {

    public static FactionManager factionManager;
    public static Storage storage;
    public static String pathSeparator;
    public static SMPFactions instance;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

    }

    @Override
    public void onLoad() {
        instance = this;
        factionManager = new FactionManager();
        String os = System.getProperty("os.name");

        if (os.contains("win")) {
            pathSeparator = "\\";
        } else {
            pathSeparator = "/";
        }

        storage = new Storage(new File(getDataFolder() + pathSeparator));
        try {
            storage.loadFactions();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        registerCommand(new FactionChatCommand());
        registerCommand(new FactionCommand());
    }

    @Override
    public void onDisable() {

    }


    private void registerCommand(Command command) {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            commandMap.register(command.getLabel(), command);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
