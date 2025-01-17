package AutoProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserWindowTest {

    private WebDriver driver;

    private By alertFrameWindow = By.xpath("//h5[text()='Alerts, Frame & Windows']");
    private By browserWindows = By.xpath("//span[text()='Browser Windows']");
    private By newTabButton = By.id("tabButton");
    private By newWindowButton = By.id("windowButton");
    private By newWindowMessageButton = By.id("messageWindowButton");

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/");
        Reporter.log("Navigated to https://demoqa.com/", true);
    }

    @Test
    public void testNewTab() {
        try {
            navigateToBrowserWindows();
            WebElement newTab = driver.findElement(newTabButton);
            Assert.assertTrue(newTab.isDisplayed(), "New Tab button is not displayed");
            Reporter.log("Clicked on New Tab button", true);
            newTab.click();
            switchToNewWindow("New Tab");
            Reporter.log("Tested New Tab button successfully", true);
        } catch (Exception e) {
            Reporter.log("Exception occurred during New Tab test: " + e.getMessage(), true);
        }
    }

    @Test
    public void testNewWindow() {
        try {
            navigateToBrowserWindows();
            WebElement newWindow = driver.findElement(newWindowButton);
            Assert.assertTrue(newWindow.isDisplayed(), "New Window button is not displayed");
            Reporter.log("Clicked on New Window button", true);
            newWindow.click();
            switchToNewWindow("New Window");
            Reporter.log("Tested New Window button successfully", true);
        } catch (Exception e) {
            Reporter.log("Exception occurred during New Window test: " + e.getMessage(), true);
        }
    }

    @Test
    public void testNewWindowMessage() {
        try {
            navigateToBrowserWindows();
            WebElement newWindowMessage = driver.findElement(newWindowMessageButton);
            Assert.assertTrue(newWindowMessage.isDisplayed(), "New Window Message button is not displayed");
            Reporter.log("Clicked on New Window Message button", true);
            newWindowMessage.click();
            switchToNewWindow("New Window Message");
            Reporter.log("Tested New Window Message button successfully", true);
        } catch (Exception e) {
            Reporter.log("Exception occurred during New Window Message test: " + e.getMessage(), true);
        }
    }

    private void navigateToBrowserWindows() throws InterruptedException {
        WebElement alertFrameWindowElement = driver.findElement(alertFrameWindow);
        alertFrameWindowElement.click();
        Thread.sleep(2000);
        WebElement browserWindowsElement = driver.findElement(browserWindows);
        browserWindowsElement.click();
        Thread.sleep(2000); 
    }

    private void switchToNewWindow(String windowType) {
        String originalWindow = driver.getWindowHandle();
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(currentUrl, originalWindow, "Failed to switch to " + windowType);
        Reporter.log("Switched to " + windowType, true);
        driver.close();
        driver.switchTo().window(originalWindow);
        Reporter.log("Closed " + windowType + " and switched back to original window", true);
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
            Reporter.log("Closed browser and quit WebDriver", true);
        }
    }
}
