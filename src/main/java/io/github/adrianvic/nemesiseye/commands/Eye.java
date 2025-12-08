package io.github.adrianvic.nemesiseye.commands;

import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.commands.sub.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Eye implements CommandExecutor, TabCompleter {
    private final Map<String, Subcommand> subs = new HashMap<>();

    public Eye() {
        register(new Reload());
        register(new ListPolicies());
        register(new PolicyInfo());
        register(new CurrentPolicies());
    }

    private void register(Subcommand sub) {
        subs.put(sub.name(), sub);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage("""
                    %sEye of Nemesis%s version %s
                    Usage: '/eye <command>'
                    Use '/eye help' for a list of available commands
                    """.formatted(ChatColor.RED, ChatColor.RESET, Nemesis.getInstance().getDescription().getVersion()));
        } else {
            Subcommand sub = subs.get(strings[0].toLowerCase());
            if (sub == null) {
                commandSender.sendMessage("Unknown command, try '/eye help' to list available commands.");
                return true;
            }
            return sub.execute(commandSender, Arrays.copyOfRange(strings, 1, strings.length));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length == 1) {
            return new ArrayList<>(subs.keySet());
        }
        Subcommand sub = subs.get(strings[0].toLowerCase());
        if (sub != null) {
            return sub.onTabComplete(commandSender, Arrays.copyOfRange(strings, 1, strings.length));
        }
        return List.of();
    }
}
