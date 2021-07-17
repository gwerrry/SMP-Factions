package com.listeners;

import com.SMPFactions;
import com.factions.Faction;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHurtListener implements Listener {

    @EventHandler
    public void onEntityHurt(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() == EntityType.PLAYER && event.getEntityType() == EntityType.PLAYER) {
            Faction faction1 = SMPFactions.factionManager.getPlayerFaction((Player) event.getDamager());
            Faction faction2 = SMPFactions.factionManager.getPlayerFaction((Player) event.getEntity());
            if (faction1 == faction2) {
                event.getDamager().sendMessage("§cYou can't hurt other faction members!");
                event.setCancelled(true);
            }
        }
    }
}
