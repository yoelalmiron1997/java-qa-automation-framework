package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utilidad para capturar evidencias (Screenshots) de la ejecución de Selenium WebDriver.
 */
public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR = "target/screenshots/";

    /**
     * Captura una pantalla del estado actual del navegador y la guarda en target/screenshots/.
     * 
     * @param driver Instancia de WebDriver activa.
     * @param testName Nombre del caso de prueba o descripción de la evidencia.
     * @return Ruta absoluta del archivo guardado.
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            return null;
        }

        try {
            // Crear la carpeta target/screenshots si no existe
            Path directoryPath = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Generar nombre de archivo único con timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            Path destinationPath = directoryPath.resolve(fileName);

            // Tomar la captura mediante TakesScreenshot
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshotFile.toPath(), destinationPath);

            System.out.println("📸 Evidencia guardada en: " + destinationPath.toAbsolutePath());
            return destinationPath.toAbsolutePath().toString();

        } catch (IOException e) {
            System.err.println("❌ Error guardando captura de pantalla: " + e.getMessage());
            return null;
        }
    }
}
