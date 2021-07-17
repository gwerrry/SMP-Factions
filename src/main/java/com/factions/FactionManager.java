package com.factions;

import com.SMPFactions;
import com.Storage;
import com.players.FactionMember;
import com.players.FactionRole;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactionManager {
    private final HashMap<FactionMember, Faction> players;
    private final List<Faction> factions;

    public FactionManager() {
        players = new HashMap<>();
        factions = new ArrayList<>();
    }

    public boolean createFaction(Faction faction) {
        for (Faction existing : factions) {
            if (existing.getName().equalsIgnoreCase(faction.getName()))
                return false;
        }

        factions.add(faction);
        SMPFactions.storage.createFaction(faction);
        SMPFactions.storage.updateMembers(faction);
        return true;
    }

    public void addFaction(Faction faction) {
        if (getFactionByName(faction.getName()) == null)
            factions.add(faction);
    }

    public void promotePlayer(FactionMember member, FactionMember promoter, Faction faction) {
        if (member.getRole() == FactionRole.MEMBER)
            member.setRole(FactionRole.OFFICER);
        if (member.getRole() == FactionRole.OFFICER) {
            member.setRole(FactionRole.LEADER);
            promoter.setRole(FactionRole.OFFICER);
            promoter.getPlayer().sendMessage("§aSuccessfully transferred ownership.");
        }
        SMPFactions.storage.updateMembers(faction);
    }

    public void demotePlayer(FactionMember member, Faction faction) {
        if (member.getRole() == FactionRole.OFFICER)
            member.setRole(FactionRole.MEMBER);
        SMPFactions.storage.updateMembers(faction);
    }

    public boolean doesFactionExist(String name) {
        for (Faction faction : factions) {
            if (faction.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }

        return false;

    }

    public void addPlayer(FactionMember member, Faction faction) {
        players.put(member, faction);
        faction.addMember(member);
    }

    public void removePlayer(FactionMember member) {
        Faction faction = players.get(member);
        faction.removeMember(member);
        players.remove(member);
        SMPFactions.storage.updateMembers(faction);
    }

    public void updateAllFactionEnemies() {
        Storage storage = SMPFactions.storage;
        for (Faction faction : factions) {
            storage.updateEnemies(faction);
        }
    }

    public FactionMember getFactionMember(Player player) {
        for (Map.Entry<FactionMember, Faction> toString : players.entrySet()) {
            if (toString.getKey().getUUID() == player.getUniqueId()) {
                return toString.getKey();
            }
        }
        return null;
    }

    public Faction getPlayerFaction(Player player) {
        return players.get(getFactionMember(player));
    }

    public boolean doesPlayerHaveFaction(Player player) {
        String uuid = player.getUniqueId().toString();
        for (Map.Entry<FactionMember, Faction> memberData : players.entrySet()) {
            FactionMember member = memberData.getKey();
            if (member.getUUID().toString().equalsIgnoreCase(uuid)) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteFaction(String name) {
        Faction toDelete = null;
        for (Faction faction : factions) {
            if (faction.getName().equalsIgnoreCase(name)) {
                toDelete = faction;
                break;
            }
        }

        if (toDelete != null) {
            for (FactionMember member : toDelete.getMembers()) {
                for (Map.Entry<FactionMember, Faction> player : players.entrySet()) {
                    if (player.getKey().getPlayer() == member.getPlayer()) {
                        players.remove(player.getKey());
                        break;
                    }
                }
            }
            for (Faction faction : factions) {
                if (faction.getEnemies().contains(toDelete))
                    faction.silentRemoveEnemy(toDelete);
            }

            SMPFactions.storage.deleteFaction(toDelete);
            factions.remove(toDelete);
            updateAllFactionEnemies();
            return true;
        }

        return false;
    }

    public List<Faction> getFactions() {
        return factions;
    }

    public HashMap<FactionMember, Faction> getPlayers() {
        return players;
    }

    public void silentAddEnemy(Faction faction1, Faction faction2) {
        boolean has1 = false;
        boolean has2 = false;

        for (Faction f : factions) {
            if (has1 && has2)
                break;
            if (f == faction1) {
                faction1.silentAddEnemy(faction2);
                has1 = true;
            } else if (f == faction2) {
                faction2.silentAddEnemy(faction1);
                has2 = true;
            }
        }
    }

    public Faction getFactionByName(String factionName) {
        for (Faction f : factions) {
            if (f.getName().equalsIgnoreCase(factionName)) {
                return f;
            }
        }
        return null;
    }
}
