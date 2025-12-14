package io.github.adrianvic.nemesiseye.impl.commands;

import io.github.adrianvic.nemesiseye.commands.EyeCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Eye implements CommandExecutor {
    private final EyeCore core;

    public Eye() {
        core = new EyeCore();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String [] strings) {
        return core.onCommand(commandSender, command, s, strings);
    }
}