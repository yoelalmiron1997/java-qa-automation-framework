package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model para la página de Login de SauceDemo.
 * 
 * REGLA DE ORO POM:
 * - Esta clase contiene Locators y Métodos de Interacción (Acciones).
 * - NO debe contener assertions (assertEquals, assertTrue, etc.).
 */
public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // 1. Locators (Privados y Encapsulados)
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    // 2. Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 3. Acciones de Página
    public void navigateToLoginPage(String url) {
        driver.get(url);
    }

    public void enterUsername(String username) {
        WebElement userInput = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        userInput.clear();
        userInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        passInput.clear();
        passInput.sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    /**
     * Helper de alto nivel para realizar el flujo de login completo.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    // 4. Métodos para obtener información de la página (para las Assertions en los Tests)
    public String getErrorMessageText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public boolean isErrorMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
    }
}
