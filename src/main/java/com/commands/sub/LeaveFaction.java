package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import com.players.FactionMember;
import com.players.FactionRole;
import org.bukkit.entity.Player;

public class LeaveFaction {
    public static void runSubCommand(Player player) {
        if (SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
            FactionMember member = SMPFactions.factionManager.getFactionMember(player);
            if (member.getRole() == FactionRole.LEADER) {
                player.sendMessage("§cYou must use /faction disband if you truly want to delete this faction!");
                player.sendMessage("§cYou can also transfer ownership by typing /f transfer <player> (The player must be online!)");
            } else {
                SMPFactions.factionManager.removePlayer(member);
                Faction faction = SMPFactions.factionManager.getPlayerFaction(player);
                for (FactionMember m : faction.getMembers()) {
                    if (m.getPlayer().isOnline())
                        m.getPlayer().sendMessage("§a" + player.getName() + "§c has left the faction!");
                }
            }
        } else {
            player.sendMessage("§cYou are not in a faction!");
        }
    }
}
