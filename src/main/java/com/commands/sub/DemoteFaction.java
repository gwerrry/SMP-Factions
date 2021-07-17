package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import com.factions.FactionManager;
import com.players.FactionMember;
import com.players.FactionRole;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class DemoteFaction {
    public static void runSubCommand(Player player, OfflinePlayer victim) {
        if (victim != null) {
            FactionManager manager = SMPFactions.factionManager;
            if (manager.doesPlayerHaveFaction(player)) {
                FactionMember member = manager.getFactionMember(player);
                if (member.getRole() == FactionRole.LEADER) {
                    Faction f = manager.getPlayerFaction(player);
                    Faction f2 = manager.getPlayerFaction((Player) victim);
                    if (member.getUUID() == victim.getUniqueId()) {
                        player.sendMessage("§cYou can't do that to yourself...");
                        return;
                    }
                    if (f == f2) {
                        FactionMember promotedMember = manager.getFactionMember((Player) victim);
                        manager.promotePlayer(promotedMember, member, f);
                        player.sendMessage("§aSuccessfully promoted " + victim.getName() + "!");
                        for (FactionMember fm : f.getMembers()) {
                            if (fm.getPlayer().isOnline())
                                fm.getPlayer().sendMessage("§a" + victim.getName() + " has been promoted!");
                        }

                        if (victim.isOnline())
                            ((Player) victim).sendMessage("§aYou've been promoted! You are now " + promotedMember.getRole());

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
