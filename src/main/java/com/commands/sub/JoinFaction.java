package com.commands.sub;

import com.SMPFactions;
import com.commands.FactionCommand;
import com.factions.Faction;
import com.players.FactionMember;
import com.players.FactionRole;
import org.bukkit.entity.Player;

public class JoinFaction {
    public static void runSubCommand(Player player, String name) {
        if (!SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
            Faction faction = SMPFactions.factionManager.getFactionByName(name);
            if (faction == null) {
                player.sendMessage("§cThat faction does not exist!");
                return;
            }
            if (!FactionCommand.invites.get(player).contains(faction)) {
                player.sendMessage("§cYou do not have an invite to that faction!");
                return;
            }

            SMPFactions.factionManager.addPlayer(new FactionMember(player.getUniqueId(), FactionRole.MEMBER), faction);
            for (FactionMember m : faction.getMembers()) {
                if (m.getPlayer().isOnline())
                    m.getPlayer().sendMessage("§3" + player.getName() + " §ahas joined the faction!");
            }
            player.sendMessage("§aSuccessfully joined the " + faction.getName() + " faction!");
            FactionCommand.invites.remove(player);
        } else {
            player.sendMessage("§cYou are in a faction!");
        }
    }
}
