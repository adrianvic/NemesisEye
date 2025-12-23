package io.github.adrianvic.nemesiseye.impl;

import io.github.adrianvic.nemesiseye.DataShifter;
import io.github.adrianvic.nemesiseye.Nemesis;
import io.github.adrianvic.nemesiseye.impl.commands.Eye;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.policy.PolicyParsers;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class r1_21 implements Glimmer {
    @Override
    public File loadConfigFile() {
        File file = new File(Nemesis.getInstance().getDataFolder(), "settings.yml");

        if (!file.exists())
            Nemesis.getInstance().saveResource("settings.yml", false);

        return file;
    }

    @Override
    public List<Policy> loadPoliciesFromFile(File file) {
        YamlConfiguration config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Map<?, ?>> rawPolicies = config.getMapList("Policies");
        List<Policy> allPolicies = new ArrayList<>();

        for (Map<?, ?> policyMap : rawPolicies) {
            if (policyMap.get("type") != null && policyMap.get("type") instanceof String type) {
                System.out.println(type);
                allPolicies.add(PolicyParsers.get(type).parse(policyMap));
            }
        }

        return allPolicies;
    }

    @Override
    public void onLoad() {
        PluginManager pm = Nemesis.getInstance().getPluginManager();
        Nemesis.getInstance().getCommand("eye").setExecutor(new Eye());
        pm.registerEvents(new EventListener(), Nemesis.getInstance());
    }

    @Override
    public ItemStack getItemInMainHandHumanEntity(HumanEntity entity) {
        return entity.getInventory().getItemInMainHand();
    }

    @Override
    public void sendMessage(CommandSender commandSender, String text) {
        commandSender.sendMessage(text);
    }

    @Override
    public boolean hasItemMeta(ItemStack item) {
        return item.getItemMeta() != null;
    }

    @Override
    public List<World> getWorlds() {
        return Bukkit.getWorlds();
    }

    @Override
    public boolean hasEnchantment(ItemStack item, Map<String, String> valuesmap) {
        Map<Enchantment, Integer> enchantments = item.getEnchantments();

        for (Map.Entry<Enchantment, Integer> ench : enchantments.entrySet()) {
            String enchKey = ench.getKey().getKey().getKey();
            String enchLevel = ench.getValue().toString();

            for (Map.Entry<String, String> rule : valuesmap.entrySet()) {
                if (
                        DataShifter.safeMatches(rule.getKey(), enchKey) &&
                                DataShifter.safeMatches(rule.getValue(), enchLevel)
                ) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAnyEnchantment(ItemStack item) {
        return !(item.getItemMeta().getEnchants().isEmpty());
    }
}
