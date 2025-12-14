package io.github.adrianvic.nemesiseye.commands;

import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.commands.sub.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.*;

public class EyeCore {
    public final Map<String, Subcommand> subs = new HashMap<>();

    public EyeCore() {
        register(new Reload());
        register(new ListPolicies());
        register(new PolicyInfo());
        register(new CurrentPolicies());
    }

    private void register(Subcommand sub) {
        subs.put(sub.name(), sub);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String [] strings) {
        if (strings.length == 0) {
            commandSender.sendMessage("""
                    Eye of Nemesis version %s
                    Usage: '/eye <command>'
                    Use '/eye help' for a list of available commands
                    """.formatted(Nemesis.getInstance().getDescription().getVersion()));
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

    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String [] strings) {
        if (strings.length == 1) {
            return new ArrayList<>(subs.keySet());
        }
        Subcommand sub = subs.get(strings[0].toLowerCase());
        if (sub != null) {
            return sub.onTabComplete(commandSender, Arrays.copyOfRange(strings, 1, strings.length));
        }
        return List.of();
    }

    public Map<String, Subcommand> getSubs() { return subs; }
}
