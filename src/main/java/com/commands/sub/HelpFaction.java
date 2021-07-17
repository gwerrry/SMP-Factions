package com.commands.sub;

import org.bukkit.entity.Player;

/**
 * Thanks to MalTheLegend104 for writing out all of the messages in this file!
 */
public class HelpFaction {


    public static void runSubCommand(Player player) {
        // General faction commands
        player.sendMessage("§dFaction Help");
        player.sendMessage("§2/faction help - Shows this menu.");
        player.sendMessage("§2/faction list - Lists all current factions.");
        player.sendMessage("§2/faction leave - Leave your current faction.");
        player.sendMessage("§2/fc <message> - Send a message to other players in your faction.");

        // Owner commands
        player.sendMessage("§aFaction Owner Commands");
        player.sendMessage("§2/faction create <name> - Creates a faction with the given name.");
        player.sendMessage("§2/faction delete - Deletes the faction that you own.");
        player.sendMessage("§2/faction invite <player> - Invite the player to the faction.");
        player.sendMessage("§2/faction kick <player> - Kick the player from the faction.");
        player.sendMessage("§2/faction spawn - Teleport to your faction home.");
        player.sendMessage("§2/faction home - Teleport to your faction home.");
        player.sendMessage("§2/faction promote <player> - Grants the player the listed role.");
        player.sendMessage("§2/faction demote <player> - Grants the player the listed role.");
        player.sendMessage("§2/faction setspawn <player> <role> - Set home for your faction to your current location.");
        player.sendMessage("§2/faction sethome <player> <role> - Set home for your faction to your current location.");
        // Potential commands
        // player.sendMessage("/faction transferowner <player> - Transfers ownership to the given player.");
        // player.sendMessage("/faction boop - boop players in your faction");


    }
}