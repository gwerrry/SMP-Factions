package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import org.bukkit.entity.Player;

import java.util.List;

public class FactionList {

    public static void runSubCommand(Player player) {
        List<Faction> factions = SMPFactions.factionManager.getFactions();
        player.sendMessage("§dList of Factions");
        for (Faction faction : factions) {
            player.sendMessage("§d► §a" + faction.getName());
        }
    }

}
