package framework.utils;

import java.util.List;
import java.util.Map;

public enum LoginPanelElementExpected {

    TITLE(
            "titleText",
            Map.of(
                    "font-family", List.of("titleTextFontFamily1", "titleTextFontFamily2"),
                    "font-size", "titleTextFontSize",
                    "color", "titleTextColor"
            )
    ),

    LOGIN_PANEL(
            null,
            Map.of(
                    "background-color", "loginPanelColor"
            )
    ),

    USERNAME_INPUT("usernamePlaceholder"),

    PASSWORD_INPUT("passwordPlaceholder"),

    LOGIN_BUTTON(
            "loginBtnText",
            Map.of(
                    "background-color", "loginBtnColor",
                    "border-radius", "loginBtnBorderRadius",
                    "color", "loginBtnTextColor"
            )
    );

    private final String textKeyOrPlaceholder;
    private final Map<String, Object> css;

    LoginPanelElementExpected(String textKeyOrPlaceholder, Map<String, Object> css) {
        this.textKeyOrPlaceholder = textKeyOrPlaceholder;
        this.css = css;
    }

    LoginPanelElementExpected(String textKeyOrPlaceholder) {
        this(textKeyOrPlaceholder, Map.of());
    }

    public String getTextKeyOrPlaceholder() {
        return textKeyOrPlaceholder;
    }

    public Map<String, Object> getCss() {
        return css;
    }
}