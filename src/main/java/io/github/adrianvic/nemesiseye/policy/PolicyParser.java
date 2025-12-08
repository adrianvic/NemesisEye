package io.github.adrianvic.nemesiseye.policy;

import java.util.List;

public interface PolicyParser {
    List<Policy> parse(List<?> raw);
}
