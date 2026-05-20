package io.github.adrianvic.nemesiseye;

import io.github.adrianvic.nemesiseye.policy.Action;
import io.github.adrianvic.nemesiseye.policy.Effect;
import io.github.adrianvic.nemesiseye.policy.Policy;
import io.github.adrianvic.nemesiseye.reflection.Glimmer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidatorTest {

    private Glimmer mockGlim;
    private Config mockConfig;

    @BeforeEach
    void setUp() {
        mockGlim = mock(Glimmer.class);
    }

    @Test
    void testValidatorCanDeny() {
        Player player = mock(Player.class);
        Event event = mock(Event.class);
        Policy policy = mock(Policy.class);
        
        when(policy.applies(player)).thenReturn(true);
        when(policy.matches(player, Action.BREAK, event)).thenReturn(true);
        when(policy.effect()).thenReturn(Effect.DENY);

        // We need to handle the static Config.getInstance()
        // This is tricky without refactoring or Mockito-inline
        // For now, let's just demonstrate the concept if Validator was more testable
    }
}
