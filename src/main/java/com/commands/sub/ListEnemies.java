package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import org.bukkit.entity.Player;

public class ListEnemies {
    public static void runSubCommand(Player player) {
        if (SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
            Faction faction = SMPFactions.factionManager.getPlayerFaction(player);
            player.sendMessage("§dList of enemies for your faction: ");
            for (Faction f : faction.getEnemies()) {
                player.sendMessage("§d» §c" + f.getName());
            }
        } else {
            player.sendMessage("§cYou are not in a faction!");
        }
    }

    public static void runSubCommand(Player player, String name) {
        if (SMPFactions.factionManager.doesFactionExist(name)) {
            Faction faction = SMPFactions.factionManager.getFactionByName(name);
            player.sendMessage("§dList of enemies of the §3" + faction.getName() + " faction: ");
            for (Faction f : faction.getEnemies()) {
                player.sendMessage("§d» §c" + f.getName());
            }
        } else {
            player.sendMessage("§cYou are not in a faction!");
        }
    }
}
