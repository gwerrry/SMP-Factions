package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import com.players.FactionMember;
import com.players.FactionRole;
import org.bukkit.entity.Player;

public class CreateFaction {

    public static void runSubCommand(Player player, String factionName) {
        FactionMember member = new FactionMember(player.getUniqueId(), FactionRole.LEADER);
        Faction faction = new Faction(factionName, "", member);
        if (SMPFactions.factionManager.createFaction(faction)) {
            SMPFactions.factionManager.addPlayer(member, faction);
            player.sendMessage("§aSuccessfully create the §9§l" + factionName + " §afaction!\n§aDo \"/faction help\" to see what you can do!");
        } else {
            player.sendMessage("§cThat faction already exists.");
        }
    }

}
