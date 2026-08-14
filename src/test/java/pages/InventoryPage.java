package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Model para la página de Inventario/Productos de SauceDemo.
 */
public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By titleText = By.className("title");
    private final By shoppingCartLink = By.className("shopping_cart_link");
    private final By burgerMenuButton = By.id("react-burger-menu-btn");
    private final By logoutSidebarLink = By.id("logout_sidebar_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(titleText)).getText();
    }

    public boolean isShoppingCartDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartLink)).isDisplayed();
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(burgerMenuButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutSidebarLink)).click();
    }
}
