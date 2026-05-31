package framework.utils;

import java.util.List;

public enum LoginPanelElementExpected {

    TITLE(
            TextAssertionType.TEXT,
            "titleText",
            List.of(
                    CssExpectation.contains("font-family", "titleTextFontFamily1", "titleTextFontFamily2"),
                    CssExpectation.pixelNear("font-size", "titleTextFontSize"),
                    CssExpectation.colorNear("color", "titleTextColor")
            )
    ),

    LOGIN_PANEL(
            List.of(
                    CssExpectation.colorNear("background-color", "loginPanelColor")
            )
    ),

    USERNAME_INPUT(TextAssertionType.PLACEHOLDER, "usernamePlaceholder"),

    PASSWORD_INPUT(TextAssertionType.PLACEHOLDER, "passwordPlaceholder"),

    LOGIN_BUTTON(
            TextAssertionType.VALUE,
            "loginBtnText",
            List.of(
                    CssExpectation.colorNear("background-color", "loginBtnColor"),
                    CssExpectation.pixelNear("border-radius", "loginBtnBorderRadius"),
                    CssExpectation.colorNear("color", "loginBtnTextColor")
            )
    );

    private final TextAssertionType textAssertionType;
    private final String textAssertionKey;
    private final List<CssExpectation> cssExpectations;

    LoginPanelElementExpected(TextAssertionType textAssertionType, String textAssertionKey, List<CssExpectation> cssExpectations) {
        this.textAssertionType = textAssertionType;
        this.textAssertionKey = textAssertionKey;
        this.cssExpectations = List.copyOf(cssExpectations);
    }

    LoginPanelElementExpected(TextAssertionType textAssertionType, String textAssertionKey) {
        this(textAssertionType, textAssertionKey, List.of());
    }

    LoginPanelElementExpected(List<CssExpectation> cssExpectations) {
        this(TextAssertionType.NONE, "", cssExpectations);
    }

    public TextAssertionType getTextAssertionType() {
        return textAssertionType;
    }

    public String getTextAssertionKey() {
        return textAssertionKey;
    }

    public List<CssExpectation> getCssExpectations() {
        return cssExpectations;
    }

    public enum TextAssertionType {
        NONE,
        TEXT,
        PLACEHOLDER,
        VALUE
    }
}
