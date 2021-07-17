package com.commands.sub;

import com.SMPFactions;
import com.factions.Faction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FactionSpawn {
    public static void runSubCommand(final Player player) {
        if (SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
            Faction faction = SMPFactions.factionManager.getPlayerFaction(player);
            if (faction.getSpawn() == null || faction.getSpawn().getWorld() == null) {
                player.sendMessage("§cYour faction does not have a spawn set yet!");
            } else {
                Bukkit.getScheduler().runTaskAsynchronously(SMPFactions.instance, () -> {
                    try {
                        Location loc = player.getLocation();
                        player.sendMessage("§9Teleporting to your faction spawn in 3 seconds... Do not move.");

                        Thread.sleep(1000);
                        if (loc.getX() != player.getLocation().getX() || loc.getY() != player.getLocation().getY() || loc.getZ() != player.getLocation().getZ()) {
                            player.sendMessage("§cTeleport failed, you moved.");
                            return;
                        }

                        player.sendMessage("§9Teleporting to your faction spawn in 2 seconds... Do not move.");

                        Thread.sleep(1000);
                        if (loc.getX() != player.getLocation().getX() || loc.getY() != player.getLocation().getY() || loc.getZ() != player.getLocation().getZ()) {
                            player.sendMessage("§cTeleport failed, you moved.");
                            return;
                        }

                        player.sendMessage("§9Teleporting to your faction spawn in 1 seconds... Do not move.");

                        Thread.sleep(1000);
                        if (loc.getX() != player.getLocation().getX() || loc.getY() != player.getLocation().getY() || loc.getZ() != player.getLocation().getZ()) {
                            player.sendMessage("§cTeleport failed, you moved.");
                            return;
                        }

                        player.sendMessage("§aTeleporting to your faction spawn!");

                        player.teleport(faction.getSpawn());
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        } else {
            player.sendMessage("§cYou are not in a faction!");
        }
    }

}
