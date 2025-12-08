package io.github.adrianvic.nemesiseye.policy;

import java.util.List;

public interface Policy {
    String name();
    List<PolicyNode> nodes();
    boolean allowlist();

    default PolicyParser getParser() {
        return PolicyParsers.get("");
    }
}
