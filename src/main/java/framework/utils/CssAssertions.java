package framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public final class CssAssertions {
    private static final Pattern RGBA_PATTERN = Pattern.compile(
            "rgba?\\((\\d+),\\s*(\\d+),\\s*(\\d+)(?:,\\s*([\\d.]+))?\\)"
    );
    private static final Pattern PIXEL_PATTERN = Pattern.compile("(-?\\d+(?:\\.\\d+)?)px");
    private static final int DEFAULT_COLOR_TOLERANCE = 2;
    private static final double DEFAULT_PIXEL_TOLERANCE = 0.5;

    private CssAssertions() {
    }

    public static void assertCss(String cssProperty, String actualValue, CssExpectation expectation, String... expectedValues) {
        switch (expectation.matchType()) {
            case EXACT -> assertThat("Unexpected CSS value for " + cssProperty, actualValue, equalTo(expectedValues[0]));
            case CONTAINS_ALL -> {
                for (String expectedValue : expectedValues) {
                    assertThat("Unexpected CSS value for " + cssProperty, actualValue, containsString(expectedValue));
                }
            }
            case COLOR_NEAR -> assertColorNear(cssProperty, actualValue, expectedValues[0]);
            case PIXEL_NEAR -> assertPixelNear(cssProperty, actualValue, expectedValues[0]);
        }
    }

    private static void assertColorNear(String cssProperty, String actualValue, String expectedValue) {
        int[] actual = parseColor(actualValue);
        int[] expected = parseColor(expectedValue);

        for (int i = 0; i < actual.length; i++) {
            int delta = Math.abs(actual[i] - expected[i]);
            if (delta > DEFAULT_COLOR_TOLERANCE) {
                throw new AssertionError(
                        "Unexpected CSS color for " + cssProperty + ". Expected near "
                                + expectedValue + " but was " + actualValue
                );
            }
        }
    }

    private static void assertPixelNear(String cssProperty, String actualValue, String expectedValue) {
        double actual = parsePixels(actualValue);
        double expected = parsePixels(expectedValue);

        if (Math.abs(actual - expected) > DEFAULT_PIXEL_TOLERANCE) {
            throw new AssertionError(
                    "Unexpected CSS size for " + cssProperty + ". Expected near "
                            + expectedValue + " but was " + actualValue
            );
        }
    }

    private static int[] parseColor(String value) {
        Matcher matcher = RGBA_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new AssertionError("Unsupported CSS color format: " + value);
        }

        return new int[]{
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3))
        };
    }

    private static double parsePixels(String value) {
        Matcher matcher = PIXEL_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new AssertionError("Unsupported CSS pixel format: " + value);
        }

        return Double.parseDouble(matcher.group(1));
    }
}
