package io.github.adrianvic.nemesiseye.commands.sub;

import io.github.adrianvic.nemesiseye.Config;
import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class PolicyInfo implements Subcommand {
    private final Glimmer glim = Nemesis.getInstance().getGlimmer();

    @Override
    public boolean execute(CommandSender commandSender, String[] strings) {
        List<Policy> policies = Config.getInstance().getPolicies();
        for (Policy policy : policies) {
            if (policy.name().equals(strings[0])) {
                glim.sendMessage(commandSender, String.format("""
                        Showing info for policy %s%s%s:
                        Type: %s
                        Nodes: %s
                        %s
                        """, ChatColor.GREEN, policy.name(), ChatColor.WHITE, "location", policy.nodes().size(), policy.allowlist() ? "Is allowlist" : "Is blacklist"));
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> rstr = new ArrayList<>();
        for (Policy p : Config.getInstance().getPolicies()) {
            rstr.add(p.name());
        }
        return rstr;
    }

    @Override
    public String description() {
        return "Shows info for a specified policy.";
    }

    @Override
    public String usage() {
        return "<policy>";
    }
}
