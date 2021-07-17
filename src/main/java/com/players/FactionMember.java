package com.players;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FactionMember {
    private final UUID player;
    private FactionRole role;

    public FactionMember(UUID player, FactionRole role) {
        this.player = player;
        this.role = role;
    }

    public FactionRole getRole() {
        return role;
    }

    public void setRole(FactionRole role) {
        this.role = role;
    }

    public UUID getUUID() {
        return player;
    }

    public Player getPlayer() {
        OfflinePlayer p = Bukkit.getOfflinePlayer(player);
        if (p.isOnline()) {
            return (Player) p;
        }
        return null;
    }
}
