package io.github.adrianvic.nemesiseye.commands.sub;

import io.github.adrianvic.nemesiseye.Config;
import io.github.adrianvic.nemesiseye.policy.Policy;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ListPolicies implements Subcommand {

    @Override
    public String name() {
        return "listpolicies";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        List<String> rstr = new ArrayList<>();
        for (Policy p : Config.getInstance().getPolicies()) {
            rstr.add(p.name());
        }
        commandSender.sendMessage(String.join(", ", rstr) + ".");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
