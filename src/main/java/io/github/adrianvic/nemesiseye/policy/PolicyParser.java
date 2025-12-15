package io.github.adrianvic.nemesiseye.policy;

import java.util.Map;

public interface PolicyParser {
    Policy parse(Map<?, ?> raw);
}
