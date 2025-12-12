package io.github.adrianvic.nemesiseye.impl.commands;

import io.github.adrianvic.nemesiseye.commands.EyeCore;
import io.github.adrianvic.nemesiseye.commands.sub.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Eye implements CommandExecutor, TabCompleter {
    private EyeCore core;

    public Eye() {
        core = new EyeCore();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return core.onCommand(commandSender, command, s, strings);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return core.onTabComplete(commandSender, command, s, strings);
    }
}