package com;

import com.factions.Faction;
import com.factions.FactionManager;
import com.players.FactionMember;
import com.players.FactionRole;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.*;

public class Storage {


    private final File f;

    public Storage(File f) {
        this.f = f;
        try {
            loadFactions();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public boolean createFaction(Faction faction) {
        File file = new File(f.getPath() + SMPFactions.pathSeparator + "factions" + SMPFactions.pathSeparator + faction.getName() + ".yml");
        file.getParentFile().mkdirs();
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
            conf.set("faction-name", faction.getName());
            conf.set("faction-description", faction.getDescription());
            List<Map<String, String>> maps = new ArrayList<>();
            FactionMember leader = faction.getMembers().get(0);
            Map<String, String> map = new HashMap<>();
            map.put(leader.getUUID().toString(), leader.getRole().toString());
            maps.add(map);
            conf.set("members", maps);
            conf.set("enemies", new ArrayList<>());
            conf.set("world", "");
            conf.set("spawn-x", "");
            conf.set("spawn-y", "");
            conf.set("spawn-z", "");
            conf.set("spawn-yaw", "");
            conf.set("spawn-pitch", "");
            conf.save(file);
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public void setSpawnLocation(Faction faction, Location location) {
        File file = new File(f.getPath() + SMPFactions.pathSeparator + "factions" + SMPFactions.pathSeparator + faction.getName() + ".yml");
        try {
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
            conf.set("world", location.getWorld().getName());
            conf.set("spawn-x", location.getX());
            conf.set("spawn-y", location.getY());
            conf.set("spawn-z", location.getZ());
            conf.set("spawn-yaw", location.getYaw());
            conf.set("spawn-pitch", location.getPitch());
            conf.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void setDescription(Faction faction, String description) {
        File file = new File(f.getPath() + SMPFactions.pathSeparator + "factions" + SMPFactions.pathSeparator + faction.getName() + ".yml");
        try {
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
            conf.set("faction-description", description);
            conf.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void deleteFaction(Faction faction) {
        File file = new File(f.getPath() + SMPFactions.pathSeparator + "factions" + SMPFactions.pathSeparator + faction.getName() + ".yml");
        if (file.exists()) {
            file.delete();
        }
    }

    public boolean updateMembers(Faction faction) {
        File file = new File(f.getPath() + SMPFactions.pathSeparator + "factions" + SMPFactions.pathSeparator + faction.getName() + ".yml");
        if (!file.exists())
            return false;
        try {
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
            List<Map<String, String>> maps = new ArrayList<>();
            for (FactionMember member : faction.getMembers()) {
                HashMap<String, String> newMember = new HashMap<>();
                newMember.put(member.getUUID().toString(), member.getRole().toString());
                maps.add(newMember);
            }
            conf.set("members", maps);
            conf.save(file);
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public void updateEnemies(Faction faction) {
        File file = new File(f.getPath() + SMPFactions.pathSeparator + "factions" + SMPFactions.pathSeparator + faction.getName() + ".yml");
        if (!file.exists())
            return;
        try {
            YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
            List<String> newEnemies = new ArrayList<>();
            for (Faction enemyFaction : faction.getEnemies()) {
                newEnemies.add(enemyFaction.getName());
            }
            conf.set("enemies", newEnemies);
            conf.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void loadFactions() throws IOException {
        FactionManager manager = SMPFactions.factionManager;
        File file = new File(f.getPath() + SMPFactions.pathSeparator + "factions");
        file.getParentFile().mkdirs();
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                Reader targetReader = new FileReader(files[i]);
                YamlConfiguration conf = YamlConfiguration.loadConfiguration(targetReader);
                String name = conf.getString("faction-name");
                String description = conf.getString("faction-description");
                List<FactionMember> members = new ArrayList<>();
                for (Map<?, ?> memberData : conf.getMapList("members")) {
                    for (Map.Entry<?, ?> a : memberData.entrySet()) {
                        UUID u = UUID.fromString((String) a.getKey());
                        FactionRole role = FactionRole.valueOf((String) a.getValue());

                        FactionMember member = new FactionMember(u, role);
                        members.add(member);

                    }
                }
                Location loc = new Location(Bukkit.getWorld(conf.getString("world")), conf.getDouble("spawn-x"), conf.getDouble("spawn-y"), conf.getDouble("spawn-z"), (float) conf.getDouble("spawn-yaw"), (float) conf.getDouble("spawn-pitch"));
                Faction faction = new Faction(name, description, members, loc);

                for (FactionMember member : faction.getMembers())
                    manager.addPlayer(member, faction);

                manager.addFaction(faction);
                targetReader.close();
            }

            loadEnemies();
        }
    }

    private void loadEnemies() {

        File file = new File(f.getPath() + SMPFactions.pathSeparator + "factions");
        file.getParentFile().mkdirs();
        File[] files = file.listFiles();
        FactionManager manager = SMPFactions.factionManager;

        for (int i = 0; i < files.length; i++) {
            Reader targetReader = null;
            try {
                targetReader = new FileReader(files[i]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Bukkit.getLogger().severe("FAILED TO LOAD FACTION " + files[0].getName());
                continue;
            }

            YamlConfiguration conf = YamlConfiguration.loadConfiguration(targetReader);

            List<Faction> factions = manager.getFactions();
            String factionName = conf.getString("faction-name");
            Faction faction1 = manager.getFactionByName(factionName);
            if (faction1 == null) {
                Bukkit.getLogger().severe("FAILED TO LOAD FACTION " + factionName);
                continue;
            }
            for (String data : conf.getStringList("enemies")) {
                for (Faction faction2 : factions) {
                    if (faction2.getName().equalsIgnoreCase(data)) {
                        manager.silentAddEnemy(faction1, faction2);
                        break;
                    }
                }
            }
        }
    }

}
