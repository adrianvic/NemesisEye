package io.github.adrianvic.nemesiseye.commands.sub;

import io.github.adrianvic.nemesiseye.Validator;
import io.github.adrianvic.nemesiseye.policy.LocationPolicy;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.List;

public class CurrentPolicies implements Subcommand {
    @Override
    public String name() {
        return "currentpolicies";
    }

    @Override
    public boolean execute(CommandSender commandSender, String[] args) {
        List<LocationPolicy> policies = Validator.getPoliciesForEntity((HumanEntity) commandSender);
        List<String> pstrings = new ArrayList<>();
        for (LocationPolicy p : policies) {
            pstrings.add(" %s (%s nodes)".formatted(p.name(), p.nodes().size()));
        }
        if (pstrings.isEmpty()) {
            commandSender.sendMessage("No policies applying to you.");
            return true;
        }

        commandSender.sendMessage(String.join(", ", pstrings + "."));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
