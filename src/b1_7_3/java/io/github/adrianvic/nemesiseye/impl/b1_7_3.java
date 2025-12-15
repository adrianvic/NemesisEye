package io.github.adrianvic.nemesiseye.impl;

import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.impl.commands.Eye;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyParsers;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class b1_7_3 implements Glimmer {
    JavaPlugin plugin;
    PluginManager pm;
    ConfigurationEx config;

    @Override
    public File loadConfigFile() {
        config = new ConfigurationEx("settings.yml", new Log());
        config.load();
        return config.getConfig();
    }

    @Override
    public List<Policy> loadPoliciesFromFile(File file) {
        List<?> rawPolicies = config.getList("Policies");

        if (rawPolicies == null) {
            return new ArrayList<>();
        }

        List<Map<?, ?>> result = new ArrayList<>(rawPolicies.size());

        for (Object entry : rawPolicies) {
            if (entry instanceof Map<?,?> m) {
                result.add(m);
            }
        }

        List<Policy> allPolicies = new ArrayList<>();
        for (Map<?, ?> policyMap : result) {
            if (policyMap.get("type") != null && policyMap.get("type") instanceof String type) {
                allPolicies.add(PolicyParsers.get(type).parse(policyMap));
            }
        }

        return allPolicies;
    }

    @Override
    public void onLoad() {
        plugin = Nemesis.getInstance();
        pm = Nemesis.getInstance().getPluginManager();
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, new EntityEventListener(), Event.Priority.Normal, plugin);
        pm.registerEvent(Event.Type.BLOCK_BREAK, new BlockEventListener(), Event.Priority.Normal, plugin);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, new PlayerEventListener(), Event.Priority.Normal, plugin);
        plugin.getCommand("eye").setExecutor(new Eye());
    }

    @Override
    public ItemStack getItemInMainHandHumanEntity(HumanEntity entity) {
        return entity.getItemInHand();
    }

    @Override
    public void sendMessage(CommandSender commandSender, String text) {
        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            commandSender.sendMessage(line);
        }
    }

    @Override
    public boolean hasItemMeta(ItemStack item) {
        return false;
    }

    @Override
    public List<World> getWorlds() {
        return plugin.getServer().getWorlds();
    }

    @Override
    public boolean hasEnchantment(ItemStack item, Map<String, String> valuesmap) {
        return false;
    }

    @Override
    public boolean hasAnyEnchantment(ItemStack itemStack) {
        return false;
    }
}
