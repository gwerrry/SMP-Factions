package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import com.factions.FactionManager;
import com.players.FactionMember;
import com.players.FactionRole;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class KickPlayer {
    public static void runSubCommand(Player player, OfflinePlayer victim) {
        if (victim != null) {
            FactionManager manager = SMPFactions.factionManager;
            if (manager.doesPlayerHaveFaction(player)) {
                FactionMember member = manager.getFactionMember(player);
                if (member.getRole() == FactionRole.LEADER) {
                    Faction f = manager.getPlayerFaction(player);
                    Faction f2 = manager.getPlayerFaction((Player) victim);
                    if (f == f2) {
                        if (member.getUUID() == victim.getUniqueId()) {
                            player.sendMessage("§cYou can't do that to yourself...");
                            return;
                        }
                        manager.removePlayer(manager.getFactionMember((Player) victim));
                        player.sendMessage("§aSuccessfully kicked " + victim.getName() + " from your faction!");
                        if (victim.isOnline())
                            ((Player) victim).sendMessage("§cYou've been kicked from the faction!");

                        for (FactionMember m : f.getMembers()) {
                            if (m.getPlayer().isOnline())
                                m.getPlayer().sendMessage("§4" + victim.getName() + "§c HAS BEEN KICKED FROM THE FACTION.");
                        }
                    } else {
                        player.sendMessage("§cYou are not in the same faction as that player!");
                    }
                } else {
                    player.sendMessage("§cYou must be the leader of the faction to do that!");
                }
            } else {
                player.sendMessage("§cYou are not in a faction!");
            }
        } else {
            player.sendMessage("§cThat player doesn't even exist bro...");
        }
    }
}
