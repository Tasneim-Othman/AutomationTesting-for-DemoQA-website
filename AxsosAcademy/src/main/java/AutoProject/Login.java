package AutoProject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Login {

    private WebDriver driver;

    private By usernameField = By.id("userName");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login");
    private By profileWrapper = By.id("profile-wrapper");
    private By errorMessage = By.id("error-message"); 

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/login");
        Reporter.log("Navigated to login page", true);
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
            {"Tasneim", "Tas12345@", true},  
            {"sfgh", "2345ASas@", false},  
            {"", "", false}  
        };
    }

    @Test(dataProvider = "loginData")
    public void testLoginForm(String username, String password, boolean expectedResult) {
        try {
           
            driver.findElement(usernameField).sendKeys(username);
            Reporter.log("Entered username: " + username, true);

            driver.findElement(passwordField).sendKeys(password);
            Reporter.log("Entered password", true);

            driver.findElement(loginButton).click();
            Reporter.log("Clicked login button", true);

            if (expectedResult) {
                WebElement profile = driver.findElement(profileWrapper);
                Assert.assertTrue(profile.isDisplayed(), "Login failed");
                Reporter.log("Login successful, profile is displayed", true);
            } else {
                WebElement error = driver.findElement(errorMessage);
                Assert.assertTrue(error.isDisplayed(), "Error message not displayed for failed login");
                Reporter.log("Login failed, error message displayed", true);
            }

        } catch (Exception e) {
            Reporter.log("Exception occurred during login test : " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
            Reporter.log("Closed browser and quit WebDriver", true);
        }
    }
}
