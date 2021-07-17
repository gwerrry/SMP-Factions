package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import com.players.FactionMember;
import com.players.FactionRole;
import org.bukkit.entity.Player;

public class DeleteFaction {
    public static void runSubCommand(Player player) {
        if (SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
            FactionMember member = SMPFactions.factionManager.getFactionMember(player);
            if (member.getRole() != FactionRole.LEADER) {
                player.sendMessage("§cYou must be the leader of the faction to do that!");
            } else {
                Faction faction = SMPFactions.factionManager.getPlayerFaction(player);
                if (SMPFactions.factionManager.deleteFaction(faction.getName())) {
                    player.sendMessage("§aYou successfully deleted your faction :(");
                    for (FactionMember m : faction.getMembers()) {
                        if (m.getPlayer().isOnline())
                            m.getPlayer().sendMessage("§cYOUR FACTION HAS BEEN DISBANDED.");
                    }
                } else {
                    player.sendMessage("§cThe faction failed to delete! :D");
                }
            }
        } else {
            player.sendMessage("§cYou are not in a faction!");
        }
    }
}
