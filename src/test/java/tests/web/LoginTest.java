package tests.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.InventoryPage;
import pages.LoginPage;
import utils.ScreenshotUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Suite de Pruebas de Automatización para el Módulo de Login (JUnit 5 + Selenium WebDriver).
 */
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private final String BASE_URL = "https://www.saucedemo.com/";

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless=new"); // Comentado para VER la ventana de Chrome abrirse visualmente en tu monitor
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        loginPage.navigateToLoginPage(BASE_URL);
    }

    @Test
    @DisplayName("TC-LOG-001: Login exitoso con usuario y contraseña válidos")
    public void testSuccessfulLogin() {
        loginPage.login("standard_user", "secret_sauce");

        // Assertions (Validaciones)
        assertEquals("Products", inventoryPage.getPageTitle(), "El título de la página debería ser 'Products'");
        assertTrue(inventoryPage.isShoppingCartDisplayed(), "El icono del carrito debería estar visible");
        assertTrue(driver.getCurrentUrl().contains("/inventory.html"), "La URL debe contener /inventory.html");
    }

    @Test
    @DisplayName("TC-LOG-002: Login fallido con contraseña incorrecta")
    public void testLoginWithInvalidPassword() {
        loginPage.login("standard_user", "wrong_password");

        // Assertion
        String expectedError = "Epic sadface: Username and password do not match any user in this service";
        assertEquals(expectedError, loginPage.getErrorMessageText(), "El mensaje de error no coincide");
    }

    @Test
    @DisplayName("TC-LOG-005: Intentar login con campo Password vacío")
    public void testEmptyPasswordLogin() {
        loginPage.login("standard_user", "");

        // Assertion
        String expectedError = "Epic sadface: Password is required";
        assertEquals(expectedError, loginPage.getErrorMessageText(), "Debería mostrar error de contraseña requerida");
    }

    @Test
    @DisplayName("TC-LOG-007: Intento de login con usuario bloqueado")
    public void testLockedOutUserLogin() {
        loginPage.login("locked_out_user", "secret_sauce");

        // Assertion
        String expectedError = "Epic sadface: Sorry, this user has been locked out.";
        assertEquals(expectedError, loginPage.getErrorMessageText(), "Debería mostrar mensaje de usuario bloqueado");
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (driver != null) {
            // 📸 Captura de pantalla automática al finalizar cada test como evidencia
            String methodName = testInfo.getTestMethod().map(m -> m.getName()).orElse("test");
            ScreenshotUtils.takeScreenshot(driver, methodName);

            driver.quit(); // Cierra el navegador y libera el proceso ChromeDriver
        }
    }
}
