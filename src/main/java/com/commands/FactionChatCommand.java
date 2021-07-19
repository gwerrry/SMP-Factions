package com.commands;

import com.SMPFactions;
import com.players.FactionMember;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FactionChatCommand extends Command {

    public FactionChatCommand() {
        super("factionchat");
        setDescription("Faction chat");
        List<String> aliases = new ArrayList<>();
        aliases.add("fchat");
        aliases.add("teamchat");
        aliases.add("tchat");
        aliases.add("tc");
        aliases.add("fc");
        setAliases(aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (SMPFactions.factionManager.doesPlayerHaveFaction(player)) {
                if (args.length != 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String arg : args) {
                        stringBuilder.append(arg).append(" ");
                    }
                    String message = stringBuilder.toString();
                    for (FactionMember factionMember : SMPFactions.factionManager.getPlayerFaction(player).getMembers()) {
                        if (factionMember.getPlayer().isOnline())
                            factionMember.getPlayer().sendMessage("§b§l(FACTION CHAT) §d" + player.getName() + " → §3" + message);
                    }
                } else {
                    player.sendMessage("§cYou need to supply a message.");
                }
            } else {
                player.sendMessage("§cYou aren't in a faction...");
            }
        }
        return true;
    }
}
