package tests;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static aquality.selenium.browser.AqualityServices.getBrowser;

public abstract class BaseTest {
    protected static final ISettingsFile CONFIG_DATA = new JsonSettingsFile("config-data.json");
    private static final String URL = CONFIG_DATA.getValue("/URL").toString();

    @BeforeMethod
    public static void setUp() {
        getBrowser().maximize();
        getBrowser().goTo(URL);
        getBrowser().waitForPageToLoad();
    }

    @AfterMethod
    public static void tearDown() {
        getBrowser().quit();
    }
}
