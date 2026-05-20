package io.github.adrianvic.nemesiseye.impl;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ImplementationContractTest {

    private ServerMock server;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    static Stream<Glimmer> implementations() {
        return Stream.of(
                new b1_7_3(),
                new r1_21()
        );
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testIsArmor(Glimmer glim) {
        // Helmets
        assertTrue(glim.isArmor(new ItemStack(Material.IRON_HELMET)));
        assertTrue(glim.isArmor(new ItemStack(Material.DIAMOND_HELMET)));
        
        // Chestplates
        assertTrue(glim.isArmor(new ItemStack(Material.GOLDEN_CHESTPLATE)));
        
        // Non-armor
        assertFalse(glim.isArmor(new ItemStack(Material.STICK)));
        assertFalse(glim.isArmor(new ItemStack(Material.DIRT)));
        
        // Null/Air
        assertFalse(glim.isArmor(null));
        assertFalse(glim.isArmor(new ItemStack(Material.AIR)));
    }

    @ParameterizedTest
    @MethodSource("implementations")
    void testIsAir(Glimmer glim) {
        assertTrue(glim.isAir(null));
        assertTrue(glim.isAir(new ItemStack(Material.AIR)));
        assertFalse(glim.isAir(new ItemStack(Material.STONE)));
    }
}
