package autoregistration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.net.URL;

public class SeleniumRegistration implements AutoRegistration {

    private URL url;
    private String username;
    private String password;

    public SeleniumRegistration(URL url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public StringBuilder webContent() throws IOException {
        return null;
    }

    public WebDriver webAccess() throws IOException {
        String chromeDriverPath = "library/chromedriver/chromedriver.exe" ;
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments(/*"--headless", "--disable-gpu", */"--window-size=1920,1200",
                "--ignore-certificate-errors", "--silent","--incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://notify.uw.edu");
        driver.findElement(By.xpath("//input[@id='weblogin_netid']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@id='weblogin_password']")).sendKeys(password);

        driver.findElement(By.xpath("//input[@id='submit_button']")).click();
        return driver;
    }
}
