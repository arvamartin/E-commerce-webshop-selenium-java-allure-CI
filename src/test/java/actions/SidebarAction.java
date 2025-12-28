package actions;

import framework.core.Browser;
import org.openqa.selenium.WebDriver;
import pages.components.Sidebar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SidebarAction extends BaseAction<SidebarAction>{

    private Sidebar sidebar;
    private final WebDriver driver;


    public SidebarAction() {
        this.sidebar = new Sidebar();
        driver = Browser.getDriver();
    }

    public SidebarAction navigateTo(String url) {
        sidebar.navigateTo(url);
        return this;
    }

    public SidebarAction openSidebar(){
        sidebar.getMenuBtn().click();

        assertThat(sidebar.getSidebarPanel().isDisplayed(), is(true));
        return this;
    }

    public SidebarAction logout(){
        sidebar.getLogoutBtn().click();
        return this;
    }


    public SidebarAction clickOnCloseCross(){
        sidebar.getCloseBtn().click();
        return this;
    }


}
