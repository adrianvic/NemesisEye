package io.github.adrianvic.nemesiseye.commands.sub;

import io.github.adrianvic.nemesiseye.Config;
import io.github.adrianvic.nemesiseye.policy.LocationPolicy;
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
        List<LocationPolicy> policies = Config.getInstance().getLocationPolicies();
        for (LocationPolicy lp : policies) {
            if (lp.name().equals(strings[0])) {
                String locations = lp.locations().toString();

                commandSender.sendMessage(String.format("""
                        Showing info for policy "%s%s%s":
                        Type: %s
                        Locations: %s
                        Nodes: %s
                        %s
                        """, ChatColor.UNDERLINE, lp.name(), ChatColor.RESET, "location", locations, lp.nodes().size(), lp.allowlist() ? "Is allowlist" : "Is blacklist"));
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> rstr = new ArrayList<>();
        for (LocationPolicy p : Config.getInstance().getLocationPolicies()) {
            rstr.add(p.name());
        }
        return rstr;
    }
}
