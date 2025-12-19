package io.github.adrianvic.nemesiseye.commands;

import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.commands.sub.*;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.*;

public class EyeCore {
    private final Glimmer glim = Nemesis.getInstance().getGlimmer();

    public EyeCore() {}

    public boolean onCommand(CommandSender commandSender, Command command, String s, String [] strings) {
        if (strings.length == 0) {
            glim.sendMessage(commandSender, """
                    %sEye of Nemesis%s version %s%s%s
                    Usage: '/eye <command>'
                    Use '/eye help' for a list of available commands
                    """.formatted(ChatColor.AQUA, ChatColor.WHITE, ChatColor.GRAY, Nemesis.getInstance().getDescription().getVersion(), ChatColor.WHITE));
        } else {
            Subcommand sub = Commands.get(strings[0].toLowerCase());
            if (sub == null) {
                commandSender.sendMessage("Unknown command, try '/eye help' to list available commands.");
                return true;
            }
            else if (commandSender.hasPermission(sub.permission())) {
                return sub.execute(commandSender, Arrays.copyOfRange(strings, 1, strings.length));
            } else {
                Nemesis.getInstance().getLogger().info("does not have %s".formatted(sub.permission()));
                Commands.sendNoPermissionError(commandSender);
                return true;
            }
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String [] strings) {
        if (strings.length == 1) {
            Map<String, Subcommand> cmds = new HashMap<>();
            for (Map.Entry<String, Subcommand> e : Commands.getAll().entrySet()) {
                if (e.getValue().hasPermission(commandSender)) {
                    cmds.put(e.getKey(), e.getValue());
                    cmds.put(e.getKey(), e.getValue());
                }
            }
            return new ArrayList<>(cmds.keySet());
        }
        Subcommand sub = Commands.get(strings[0].toLowerCase());
        if (sub != null && commandSender.hasPermission(sub.permission())) {
            return sub.onTabComplete(commandSender, Arrays.copyOfRange(strings, 1, strings.length));
        }
        return List.of();
    }
}
