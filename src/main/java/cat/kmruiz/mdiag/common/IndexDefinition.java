package cat.kmruiz.mdiag.common;

import java.util.List;

public record IndexDefinition(
        List<Rule> rules
) {
    public record Rule(String field, String value) {}
}
