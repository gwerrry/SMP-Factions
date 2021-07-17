package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import com.players.FactionMember;
import com.players.FactionRole;
import org.bukkit.entity.Player;

public class FactionSetSpawn {
    public static void runSubCommand(Player player) {
        if (SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
            FactionMember member = SMPFactions.factionManager.getFactionMember(player);
            if (member.getRole() != FactionRole.LEADER) {
                player.sendMessage("§cYou must be the leader of the faction to do that!");
            } else {
                Faction faction = SMPFactions.factionManager.getPlayerFaction(player);
                faction.setSpawn(player.getLocation());
                player.sendMessage("§aSuccessfully set your faction's spawn!");
                for (FactionMember m : faction.getMembers()) {
                    if (m.getPlayer().isOnline())
                        m.getPlayer().sendMessage("§aThe faction spawn has been set!");
                }
            }
        } else {
            player.sendMessage("§cYou are not in a faction!");
        }
    }
}
