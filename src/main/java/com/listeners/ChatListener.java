package com.listeners;

import com.SMPFactions;
import com.players.FactionMember;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (message.startsWith("#")) {
            if (SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
                message = message.replace("#", " ");
                for (FactionMember factionMember : SMPFactions.factionManager.getPlayerFaction(player).getMembers()) {
                    if (factionMember.getPlayer().isOnline())
                        factionMember.getPlayer().sendMessage("§b§l(FACTION CHAT) §d" + player.getName() + " → " + message);
                }
                event.setCancelled(true);
            }
        }
    }
}
