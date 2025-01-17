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

public class Register {

    private WebDriver driver;

    private By newUserButton = By.id("newUser");
    private By firstNameField = By.id("firstname");
    private By lastNameField = By.id("lastname");
    private By userNameField = By.id("userName");
    private By passwordField = By.id("password");
    private By registerButton = By.id("register");
    private By registrationSuccessMessage = By.id("registration-success");
    private By registrationErrorMessage = By.id("registration-error");

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demoqa.com/login");
        Reporter.log("Navigate to login page", true);
    }

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() {
        return new Object[][] {
            {"Tasneim", "Ahmad", "Tasneim", "Tas12345@"},
            {"67", "678", "4454", "Tas12345@"}
        };
    }

    @Test(dataProvider = "registrationData")
    public void testRegistrationForm(String firstName, String lastName, String userName, String password) {
        try {
           
            WebElement newUserBtn = driver.findElement(newUserButton);
            newUserBtn.click();
            Reporter.log("Click on New User button", true);

            driver.findElement(firstNameField).sendKeys(firstName);
            Reporter.log("Enter first name", true);

            driver.findElement(lastNameField).sendKeys(lastName);
            Reporter.log("Enter last name", true);

            driver.findElement(userNameField).sendKeys(userName);
            Reporter.log("Enter username", true);

            driver.findElement(passwordField).sendKeys(password);
            Reporter.log("Enter password", true);

            driver.findElement(registerButton).click();
            Reporter.log("Click Register button", true);

            WebElement messageToCheck = driver.findElement(registrationSuccessMessage);
            WebElement errorMessage = driver.findElement(registrationErrorMessage);

            if (isRegistrationSuccessful(firstName, lastName)) {
                Assert.assertTrue(messageToCheck.isDisplayed(), "Registration failed");
                Reporter.log("Validated registration success", true);
            } else {
                Assert.assertTrue(errorMessage.isDisplayed(), "Expected registration to fail");
                Reporter.log("Validated registration failure", true);
            }

        } catch (Exception e) {
            Reporter.log("Exception occurred during registration test: " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    private boolean isRegistrationSuccessful(String firstName, String lastName) {
        return !firstName.isEmpty() && !lastName.isEmpty();  
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
            Reporter.log("Close browser and quit WebDriver", true);
        }
    }
}
