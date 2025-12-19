package io.github.adrianvic.nemesiseye.commands.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.List;

public interface Subcommand {
    String description();
    String usage();
    @SuppressWarnings("SameReturnValue")
    boolean execute(CommandSender commandSender, String[] strings);
    List<String> onTabComplete(CommandSender sender, String[] strings);
    String permission();
    default boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(permission());
    }
}