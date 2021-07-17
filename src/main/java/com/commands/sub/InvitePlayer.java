package com.commands.sub;

import com.SMPFactions;
import com.commands.FactionCommand;
import com.factions.Faction;
import com.factions.FactionManager;
import com.players.FactionMember;
import com.players.FactionRole;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class InvitePlayer {
    public static void runSubCommand(Player player, OfflinePlayer victim) {
        if (victim != null && victim.isOnline()) {
            FactionManager manager = SMPFactions.factionManager;
            if (manager.doesPlayerHaveFaction(player)) {
                FactionMember member = manager.getFactionMember(player);
                if (member.getRole() == FactionRole.OFFICER || member.getRole() == FactionRole.LEADER) {
                    Faction f = manager.getPlayerFaction(player);
                    if (FactionCommand.invites.containsKey((Player) victim)) {
                        if (!FactionCommand.invites.get((Player) victim).contains(f)) {
                            FactionCommand.invites.get((Player) victim).add(f);
                        } else {
                            player.sendMessage("§cThey're already invited to this faction.");
                        }
                    } else {
                        List<Faction> fax = new ArrayList<>();
                        fax.add(f);
                        FactionCommand.invites.put((Player) victim, fax);
                        sendClickableCommand((Player) victim, "§aYou have been invited to the the " + f.getName() + " faction. Click this message to accept. (Expires in 5 minutes)", "f join " + f.getName());
                        new Thread(() -> {
                            for (int i = 0; i <= 5; i++) {
                                try {
                                    Thread.sleep(60000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                if (!(FactionCommand.invites.get(player) == null))
                                    return;
                                if (!FactionCommand.invites.get(player).contains(f))
                                    return;
                            }
                            FactionCommand.invites.get(player).remove(f);
                            if (FactionCommand.invites.get(player).isEmpty())
                                FactionCommand.invites.remove(player);
                        }).start();
                    }

                } else {
                    player.sendMessage("§cYOU MUST BE OFFICER OR ABOVE TO INVITE PLAYERS TO THE FACTION!");
                }
            } else {
                player.sendMessage("§cYou are not in a faction!");
            }
        } else {
            player.sendMessage("§cThat player doesn't even exist bro...(Or they just aren't online)");
        }
    }

    private static void sendClickableCommand(Player player, String message, String command) {
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(message));
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + command));
        player.spigot().sendMessage(component);
    }
}
