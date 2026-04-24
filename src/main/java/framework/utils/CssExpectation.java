package framework.utils;

import java.util.List;
import java.util.Objects;

public record CssExpectation(String cssProperty, CssMatchType matchType, List<String> expectedValueKeys) {

    public CssExpectation {
        Objects.requireNonNull(cssProperty, "cssProperty must not be null");
        Objects.requireNonNull(matchType, "matchType must not be null");
        Objects.requireNonNull(expectedValueKeys, "expectedValueKeys must not be null");

        if (expectedValueKeys.isEmpty()) {
            throw new IllegalArgumentException("expectedValueKeys must not be empty");
        }

        expectedValueKeys = List.copyOf(expectedValueKeys);

        if (matchType == CssMatchType.EXACT && expectedValueKeys.size() != 1) {
            throw new IllegalArgumentException("EXACT match requires exactly one expected value key");
        }
    }

    public static CssExpectation exact(String cssProperty, String expectedValueKey) {
        return new CssExpectation(cssProperty, CssMatchType.EXACT, List.of(expectedValueKey));
    }

    public static CssExpectation contains(String cssProperty, String... expectedValueKeys) {
        return new CssExpectation(cssProperty, CssMatchType.CONTAINS_ALL, List.of(expectedValueKeys));
    }
}
