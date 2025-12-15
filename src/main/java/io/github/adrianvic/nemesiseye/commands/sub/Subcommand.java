package io.github.adrianvic.nemesiseye.commands.sub;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface Subcommand {
    String description();
    String usage();
    @SuppressWarnings("SameReturnValue")
    boolean execute(CommandSender commandSender, String[] strings);
    List<String> onTabComplete(CommandSender sender, String[] strings);
}