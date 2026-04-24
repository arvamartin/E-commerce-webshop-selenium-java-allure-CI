package framework.utils;

import java.util.List;

public enum SidebarElementExpected {

    ALL_ITEMS("allItemText", sidebarElementCss()),

    ABOUT("aboutText", sidebarElementCss()),

    LOGOUT("logoutText", sidebarElementCss());

    private final String textKey;
    private final List<CssExpectation> cssExpectations;

    SidebarElementExpected(String textKey, List<CssExpectation> cssExpectations) {
        this.textKey = textKey;
        this.cssExpectations = List.copyOf(cssExpectations);
    }

    public String getTextKey() {
        return textKey;
    }

    public List<CssExpectation> getCssExpectations() {
        return cssExpectations;
    }

    private static List<CssExpectation> sidebarElementCss() {
        return List.of(
                CssExpectation.contains("font-family", "panelElementsFontFamily1", "panelElementsFontFamily2"),
                CssExpectation.exact("color", "panelElementsTextColor"),
                CssExpectation.exact("border-bottom-color", "elementBorderBottomColor")
        );
    }
}
