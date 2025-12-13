package io.github.adrianvic.nemesiseye.commands.sub;

import io.github.adrianvic.nemesiseye.Config;
import io.github.adrianvic.nemesiseye.policy.Policy;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class PolicyInfo implements Subcommand {
    @Override
    public String name() {
        return "policyinfo";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] strings) {
        List<Policy> policies = Config.getInstance().getPolicies();
        for (Policy policy : policies) {
            if (policy.name().equals(strings[0])) {
                commandSender.sendMessage(String.format("""
                        Showing info for policy "%s":
                        Type: %s
                        Nodes: %s
                        %s
                        """, policy.name(), "location", policy.nodes().size(), policy.allowlist() ? "Is allowlist" : "Is blacklist"));
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
}
