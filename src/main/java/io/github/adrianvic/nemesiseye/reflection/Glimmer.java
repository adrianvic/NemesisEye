package io.github.adrianvic.nemesiseye.reflection;

import io.github.adrianvic.nemesiseye.policy.Policy;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface Glimmer {
    void onLoad();

    List<World> getWorlds();

    // Configuration
    File loadConfigFile();
    List<Policy> loadPoliciesFromFile(File file);
    List<Policy> getApplyingPoliciesForEntity(HumanEntity entity, List<Policy> policies);

    // Items
    boolean hasItemMeta(ItemStack item);
    boolean hasEnchantment(ItemStack item, Map<String, String> valuesmap);
    boolean hasAnyEnchantment(ItemStack itemStack);
    ItemStack getItemInMainHandHumanEntity(HumanEntity entity);

    // Commands
    void sendMessage(CommandSender commandSender, String text);

    class Box {
        public final double x1, y1, z1, x2, y2, z2;

        public Box(double x1, double y1, double z1, double x2, double y2, double z2) {
            this.x1 = Math.min(x1, x2);
            this.y1 = Math.min(y1, y2);
            this.z1 = Math.min(z1, z2);
            this.x2 = Math.max(x1, x2);
            this.y2 = Math.max(y1, y2);
            this.z2 = Math.max(z1, z2);
        }

        public boolean contains(double x, double y, double z) {
            return x >= x1 && x <= x2
                    && y >= y1 && y <= y2
                    && z >= z1 && z <= z2;
        }
        public boolean contains(Vector v) {
            return v.getX() >= x1 && v.getX() <= x2
                    && v.getY() >= y1 && v.getY() <= y2
                    && v.getZ() >= z1 && v.getZ() <= z2;
        }


        public static Box of(Location loc1, Location loc2) { return new Box(loc1.getX(), loc1.getY(), loc1.getZ(), loc2.getX(), loc2.getY(), loc2.getZ()); }
    }

}
