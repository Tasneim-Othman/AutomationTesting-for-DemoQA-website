package AutoProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Reporter;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Alerts {
    WebDriver driver;

    private By simpleAlertButton = By.id("alertButton");
    private By confirmBoxButton = By.id("confirmButton");
    private By promptBoxButton = By.id("promtButton");

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demoqa.com/alerts");
        Reporter.log("Browser opened and navigated to DemoQA Alerts page.", true);
    }

    @Test
    public void testSimpleAlert() {
        driver.findElement(simpleAlertButton).click();
        Reporter.log("Simple Alert button clicked.", true);

        Alert simpleAlert = driver.switchTo().alert();
        String simpleAlertText = simpleAlert.getText();
        Reporter.log("Simple Alert Text: " + simpleAlertText, true);
        Assert.assertEquals(simpleAlertText, "You clicked a button", "Alert text doesn't match the expected value.");
        simpleAlert.accept();
        Reporter.log("Simple Alert accepted.", true);
    }

    @Test
    public void testConfirmBoxAccept() {
        driver.findElement(confirmBoxButton).click();
        Reporter.log("Confirm Box button clicked.", true);

        Alert confirmAcceptAlert = driver.switchTo().alert();
        String confirmAcceptText = confirmAcceptAlert.getText();
        Reporter.log("Confirm Box Text: " + confirmAcceptText, true);
        Assert.assertEquals(confirmAcceptText, "Do you confirm action?", "Confirm Box text doesn't match the expected value.");
        confirmAcceptAlert.accept();
        Reporter.log("Confirm Box accepted.", true);
    }

    @Test
    public void testConfirmBoxDismiss() {
        driver.findElement(confirmBoxButton).click();
        Reporter.log("Confirm Box button clicked.", true);

        Alert confirmDismissAlert = driver.switchTo().alert();
        String confirmDismissText = confirmDismissAlert.getText();
        Reporter.log("Confirm Box Text: " + confirmDismissText, true);
        Assert.assertEquals(confirmDismissText, "Do you confirm action?", "Confirm Box text doesn't match the expected value.");
        confirmDismissAlert.dismiss();
        Reporter.log("Confirm Box dismissed.", true);
    }

    @Test
    public void testPromptBox() {
        driver.findElement(promptBoxButton).click();
        Reporter.log("Prompt Box button clicked.", true);

        Alert promptAlert = driver.switchTo().alert();
        String promptText = promptAlert.getText();
        Reporter.log("Prompt Box Text: " + promptText, true);
        Assert.assertEquals(promptText, "Please enter your name", "Prompt Box text doesn't match the expected value.");

        String responseText = "Test Response";
        promptAlert.sendKeys(responseText);
        Reporter.log("Text '" + responseText + "' entered into Prompt Box.", true);

        promptAlert.accept();
        Reporter.log("Prompt Box accepted.", true);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
        Reporter.log("Browser closed.", true);
    }
}
