package io.github.adrianvic.nemesiseye.commands.sub;

import io.github.adrianvic.nemesiseye.Config;
import io.github.adrianvic.nemesiseye.commands.Commands;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Reload implements Subcommand {
    @Override
    public boolean execute(CommandSender commandSender, String [] strings) {
            Config.getInstance().load();
            commandSender.sendMessage("Reloading...");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }

    @Override
    public String permission() {
        return "nemsiseye.reload";
    }

    @Override
    public String description() {
        return "Reloads the plugin configuration file.";
    }

    @Override
    public String usage() {
        return "";
    }
}
