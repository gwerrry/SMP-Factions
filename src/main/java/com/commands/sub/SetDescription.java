package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import com.players.FactionMember;
import com.players.FactionRole;
import org.bukkit.entity.Player;

public class SetDescription {
    public static void runSubCommand(Player player, String[] args) {
        if (SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
            FactionMember member = SMPFactions.factionManager.getFactionMember(player);
            if (member.getRole() != FactionRole.LEADER) {
                player.sendMessage("§cYou must be the leader of the faction to do that!");
            } else {
                Faction faction = SMPFactions.factionManager.getPlayerFaction(player);
                StringBuilder builder = new StringBuilder();
                for (int i = 2; i < args.length - 1; i++) {
                    builder.append(args[i]).append(" ");
                }
                faction.setDescription(builder.toString());
            }
        } else {
            player.sendMessage("§cYou are not in a faction!");
        }
    }
}
