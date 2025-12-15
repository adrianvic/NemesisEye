package io.github.adrianvic.nemesiseye.commands.sub;

import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.commands.Commands;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Help implements Subcommand {
    private final Glimmer glim = Nemesis.getInstance().getGlimmer();

    @Override
    public boolean execute(CommandSender commandSender, String[] strings) {
        List<String> rstr = new ArrayList<>();
        Map<String, Subcommand> allSubcommands = Commands.getAll();
        if (allSubcommands.isEmpty()) {
            rstr.add("No commands found.");
        }
        for (Map.Entry<String, Subcommand> e : allSubcommands.entrySet()) {
            rstr.add("""
            %s - %s
            Usage: /eye %s %s
            """.formatted(e.getKey(), e.getValue().description(), e.getKey(), e.getValue()));
        }

        glim.sendMessage(commandSender, String.join("\n", rstr));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] strings) {
        return List.of();
    }

    @Override
    public String description() {
        return "Lists all commands.";
    }

    @Override
    public String usage() {
        return "";
    }
}
