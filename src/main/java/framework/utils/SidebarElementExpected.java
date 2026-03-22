package framework.utils;

import java.util.List;
import java.util.Map;

public enum SidebarElementExpected {

    ALL_ITEMS(
            "allItemText",
            Map.of(
                    "font-family", List.of("panelElementsFontFamily1", "panelElementsFontFamily2"),
                    "color", "panelElementsTextColor",
                    "border-bottom-color", "elementBorderBottomColor"
            )
    ),

    ABOUT(
            "aboutText",
            Map.of(
                    "font-family", List.of("panelElementsFontFamily1", "panelElementsFontFamily2"),
                    "color", "panelElementsTextColor",
                    "border-bottom-color", "elementBorderBottomColor"
            )
    ),

    LOGOUT(
            "logoutText",
            Map.of(
                    "font-family", List.of("panelElementsFontFamily1", "panelElementsFontFamily2"),
                    "color", "panelElementsTextColor",
                    "border-bottom-color", "elementBorderBottomColor"
            )
    );

    private final String textKey;
    private final Map<String, Object> css;

    SidebarElementExpected(String textKey, Map<String, Object> css) {
        this.textKey = textKey;
        this.css = css;
    }

    public String getTextKey() {
        return textKey;
    }

    public Map<String, Object> getCss() {
        return css;
    }
}
