package com.commands;

import com.SMPFactions;
import com.commands.sub.*;
import com.factions.Faction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactionCommand extends Command {

    public static HashMap<Player, List<Faction>> invites = new HashMap<>();


    public FactionCommand() {
        super("faction");
        setDescription("Main faction command.");
        List<String> aliases = new ArrayList<>();
        aliases.add("f");
        setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;


            try {
                switch (args[0].toLowerCase()) {
                    case "create": {
                        if (args.length != 2) {
                            player.sendMessage("§cUsage: /faction create <name>");
                        } else if (SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
                            player.sendMessage("§cError! You are already in a faction!");
                        } else {
                            CreateFaction.runSubCommand(player, args[1]);
                        }
                        break;
                    }
                    case "list": {
                        FactionList.runSubCommand(player);
                        break;
                    }
                    case "setdescription": {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: /faction setDescription <desc>");
                        } else {
                            SetDescription.runSubCommand(player, args);
                        }
                        break;
                    }
                    case "delete":
                    case "disband": {
                        DeleteFaction.runSubCommand(player);
                        break;
                    }
                    case "spawn":
                    case "home": {
                        FactionSpawn.runSubCommand(player);
                        break;
                    }
                    case "setspawn":
                    case "sethome": {
                        FactionSetSpawn.runSubCommand(player);
                        break;
                    }
                    case "leave": {
                        LeaveFaction.runSubCommand(player);
                        break;
                    }
                    case "listmembers": {
                        if (args.length == 1)
                            ListMembers.runSubCommand(player);
                        else
                            ListMembers.runSubCommand(player, args[1]);
                        break;
                    }
                    case "listenemies": {
                        if (args.length == 1)
                            ListEnemies.runSubCommand(player);
                        else
                            ListEnemies.runSubCommand(player, args[1]);
                        break;
                    }
                    case "kick": {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: /faction kick <playername>");
                        } else {
                            KickPlayer.runSubCommand(player, Bukkit.getOfflinePlayer(args[1]));
                        }
                        break;
                    }
                    case "invite": {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: /faction invite <playername>");
                        } else {
                            InvitePlayer.runSubCommand(player, Bukkit.getOfflinePlayer(args[1]));
                        }
                        break;
                    }
                    case "join": {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: /faction join <name>");
                        } else {
                            JoinFaction.runSubCommand(player, args[1]);
                        }
                        break;
                    }
                    case "enemy": {
                        player.sendMessage("§cComing soon...");

                        if (args.length < 2) {
                            player.sendMessage("§cUsage: /faction enemy <name>");
                        } else {
                            EnemyFaction.runSubCommand(player, args[1]);
                        }

                        break;
                    }
                    case "promote": {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: /faction promote <name>");
                        } else {
                            DemoteFaction.runSubCommand(player, Bukkit.getOfflinePlayer(args[1]));
                        }
                        break;
                    }
                    case "demote": {
                        if (args.length < 2) {
                            player.sendMessage("§cUsage: /faction demote <name>");
                        } else {
                            PromoteFaction.runSubCommand(player, Bukkit.getOfflinePlayer(args[1]));
                        }
                        break;
                    }
                    default:
                        HelpFaction.runSubCommand(player);
                        break;
                }
            } catch (Exception ignored) {
                HelpFaction.runSubCommand(player);
            }
        }
        return true;
    }
}
