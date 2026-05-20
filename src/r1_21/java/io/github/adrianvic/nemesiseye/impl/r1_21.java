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
    public boolean isAir(ItemStack item) {
        return item == null || item.getType().isAir();
    }

    @Override
    public boolean isGliding(org.bukkit.entity.Player player) {
        return player.isGliding();
    }

    @Override
    public void setGliding(org.bukkit.entity.Player player, boolean gliding) {
        player.setGliding(gliding);
    }

    @Override
    public boolean hasPermission(org.bukkit.command.CommandSender sender, String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public boolean isArmorEquipAttempt(org.bukkit.event.Event event) {
        if (!(event instanceof org.bukkit.event.inventory.InventoryClickEvent e)) {
            return false;
        }

        if (e.getSlotType() == org.bukkit.event.inventory.InventoryType.SlotType.ARMOR) {
            return true;
        }

        if (e.isShiftClick()) {
            return isArmor(e.getCurrentItem());
        }

        if (e.getClick() == org.bukkit.event.inventory.ClickType.NUMBER_KEY
                && e.getSlotType() == org.bukkit.event.inventory.InventoryType.SlotType.ARMOR
                && e.getWhoClicked() instanceof org.bukkit.entity.Player player) {
            return isArmor(
                    player.getInventory().getItem(e.getHotbarButton())
            );
        }

        return false;
    }

    @Override
    public ItemStack getEquippedItem(org.bukkit.event.Event event) {
        if (event instanceof org.bukkit.event.inventory.InventoryClickEvent e) {
            org.bukkit.event.inventory.InventoryType.SlotType slotType = e.getSlotType();

            if (e.getClick() == org.bukkit.event.inventory.ClickType.NUMBER_KEY // hotbar key swap
                    && slotType == org.bukkit.event.inventory.InventoryType.SlotType.ARMOR
                    && e.getWhoClicked() instanceof org.bukkit.entity.Player player) {
                return player.getInventory().getItem(e.getHotbarButton());
            }

            if (e.isShiftClick()) {
                ItemStack current = e.getCurrentItem();
                if (isArmor(current)) return current;
            }

            // regular click
            if (slotType == org.bukkit.event.inventory.InventoryType.SlotType.ARMOR) {
                ItemStack cursor = e.getCursor();
                if (isArmor(cursor)) return cursor;
            }
        }

        // Try Paper's PlayerArmorChangeEvent via reflection or just check if class exists
        try {
            if (event instanceof com.destroystokyo.paper.event.player.PlayerArmorChangeEvent e) {
                return e.getNewItem();
            }
        } catch (NoClassDefFoundError | Exception ignored) {}

        return null;
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

    @Override
    public boolean isArmor(ItemStack item) {
        if (item == null || item.getType().isAir()) {
           return false;
        }

        String name = item.getType().name();

        return name.endsWith("_HELMET")
                || name.endsWith("_CHESTPLATE")
                || name.endsWith("_LEGGINGS")
                || name.endsWith("_BOOTS")
                || item.getType() == org.bukkit.Material.ELYTRA;
    }
}
