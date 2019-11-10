package autoregistration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

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
    public String webContent() throws InterruptedException {
        WebDriver driver = webAccess();

        Thread.sleep(3000);

        String content = driver.findElement(By.className("notification_list")).getText();

        return content;
    }

    private WebDriver webAccess() {
        String chromeDriverPath = "library/chromedriver/chromedriver.exe" ;
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200",
                "--ignore-certificate-errors", "--silent","--incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://notify.uw.edu");
        driver.findElement(By.xpath("//input[@id='weblogin_netid']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@id='weblogin_password']")).sendKeys(password);

        driver.findElement(By.xpath("//input[@id='submit_button']")).click();
        return driver;
    }

    public void courseCheck() throws InterruptedException, IOException {
        BufferedReader reader = new BufferedReader(new StringReader(webContent()));
        while (reader.readLine() != null) {
            String course = reader.readLine();
            String code = reader.readLine();
            String[] seats = reader.readLine().split("/");
            int now = Integer.parseInt(seats[0].trim());
            int full = Integer.parseInt(seats[1].replaceAll("\\D", "").trim());
            for (int i = 0; i < 3; i += 1) {
                reader.readLine();
            }
            System.out.println(now + "/" + full);
        }
    }
}
