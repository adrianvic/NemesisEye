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
            return sub.execute(commandSender, Arrays.copyOfRange(strings, 1, strings.length));
        }
        return false;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String [] strings) {
        if (strings.length == 1) {
            return new ArrayList<>(Commands.getAll().keySet());
        }
        Subcommand sub = Commands.get(strings[0].toLowerCase());
        if (sub != null) {
            return sub.onTabComplete(commandSender, Arrays.copyOfRange(strings, 1, strings.length));
        }
        return List.of();
    }
}
