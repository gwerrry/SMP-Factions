package com.factions;

import com.SMPFactions;
import com.players.FactionMember;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class Faction {
    private final List<FactionMember> factionMembers;
    private final List<Faction> enemies;
    private final String name;
    private Location spawn;
    private String description;

    public Faction(String name, String description, FactionMember leader) {
        this.factionMembers = new ArrayList<>();
        this.enemies = new ArrayList<>();

        this.name = name;
        this.description = description;
        this.factionMembers.add(leader);
    }

    public Faction(String name, String description, List<FactionMember> members, Location spawn) {
        this.factionMembers = new ArrayList<>();
        this.enemies = new ArrayList<>();

        this.name = name;
        this.description = description;
        this.factionMembers.addAll(members);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        SMPFactions.storage.setDescription(this, description);
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
        SMPFactions.storage.setSpawnLocation(this, spawn);
    }

    public List<FactionMember> getMembers() {
        return factionMembers;
    }

    public void addMember(FactionMember member) {
        if (!factionMembers.contains(member))
            factionMembers.add(member);
    }

    public List<Faction> getEnemies() {
        return enemies;
    }

    public void addEnemy(Faction faction) {
        enemies.add(faction);
        faction.silentAddEnemy(this);

        for (FactionMember p : faction.getMembers()) {
            p.getPlayer().sendTitle("§c§lALERT", "§9§l" + name + " §c§lHAS DECLARED §4§lWAR§c§l!", 5, 5, 5);
        }
        for (FactionMember p : factionMembers) {
            p.getPlayer().sendTitle("§c§lALERT", "§c§lTHIS FACTION HAS DECLARED §4§lWAR ON §9§l" + faction.getName() + "", 5, 5, 5);
        }

        SMPFactions.storage.updateEnemies(faction);
        SMPFactions.storage.updateEnemies(this);
    }

    public void removeMember(FactionMember member) {
        factionMembers.remove(member);
    }

    public void silentAddEnemy(Faction faction) {
        if (!enemies.contains(faction))
            enemies.add(faction);
    }

    public void silentRemoveEnemy(Faction faction) {
        enemies.remove(faction);
    }
}
