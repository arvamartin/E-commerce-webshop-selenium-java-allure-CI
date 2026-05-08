package framework.core;

import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Elements {

    private final List<WebElement> elements;

    public Elements(List<WebElement> elements) {
        this.elements = elements;
    }

    public List<String> getTexts() {
        return elements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public Elements shouldHaveText(String... expectedTexts) {
        List<String> actualTexts = getTexts();

        for (String expected : expectedTexts) {
            assertThat(actualTexts, hasItem(expected));
        }
        return this;
    }

    public Elements shouldHaveExactTexts(String... expectedTexts) {
        assertThat(getTexts(), contains(expectedTexts));
        return this;
    }

    public Elements shouldHaveSize(int expectedSize) {
        assertThat(elements.size(), equalTo(expectedSize));
        return this;
    }
}
