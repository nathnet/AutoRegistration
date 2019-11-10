package autoregistration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class SeleniumRegistration implements AutoRegistration {

    @Override
    public StringBuilder webContent() throws IOException {
        System.setProperty("webdriver.chrome.driver",
                "ChromeDriverPath");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1200x600");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://contentstack.built.io");
        driver.get("https://www.google.co.in/");
        System.out.println("title is: " + driver.getTitle());
        return null;
    }
}
