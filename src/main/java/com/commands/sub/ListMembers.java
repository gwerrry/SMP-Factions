package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import com.players.FactionMember;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ListMembers {
    public static void runSubCommand(Player player) {
        if (SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
            Faction faction = SMPFactions.factionManager.getPlayerFaction(player);
            player.sendMessage("§dList of members for your faction: ");
            for (FactionMember m : faction.getMembers()) {
                player.sendMessage("§a" + Bukkit.getOfflinePlayer(m.getUUID()).getName() + " §3→ " + m.getRole().toString());
            }
        } else {
            player.sendMessage("§cYou are not in a faction!");
        }
    }

    public static void runSubCommand(Player player, String name) {
        if (SMPFactions.factionManager.doesFactionExist(name)) {
            Faction faction = SMPFactions.factionManager.getFactionByName(name);
            player.sendMessage("§dList of members in the §3" + faction.getName() + " faction: ");
            for (FactionMember m : faction.getMembers()) {
                player.sendMessage("§a" + Bukkit.getOfflinePlayer(m.getUUID()).getName() + " §3→ " + m.getRole().toString());
            }
        } else {
            player.sendMessage("§cThat faction doesn't exist!");
        }
    }
}
