package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import com.players.FactionMember;
import com.players.FactionRole;
import org.bukkit.entity.Player;

public class EnemyFaction {
    public static void runSubCommand(Player player, String name) {
        if (SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
            FactionMember member = SMPFactions.factionManager.getFactionMember(player);
            if (member.getRole() != FactionRole.LEADER) {
                player.sendMessage("§cYou must be the leader of the faction to do that!");
            } else {
                Faction faction = SMPFactions.factionManager.getPlayerFaction(player);
                Faction enemy = SMPFactions.factionManager.getFactionByName(name);

                if (enemy == null) {
                    player.sendMessage("§cThat faction doesn't exist.");
                    return;
                }
                if (faction == enemy) {
                    player.sendMessage("§cYou can't become enemies with yourself.");
                    return;
                }
                if (faction.getEnemies().contains(enemy)) {
                    player.sendMessage("§cYou are already enemies with that faction!");
                    return;
                }
                faction.addEnemy(enemy);

            }
        } else {
            player.sendMessage("§cYou are not in a faction!");
        }
    }
}
