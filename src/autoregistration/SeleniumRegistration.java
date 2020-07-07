package autoregistration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;

public class SeleniumRegistration {

    private URL url;
    private String username;
    private String password;
    private Set wanted;

    public SeleniumRegistration(URL url, String username, String password, Set<String> wanted) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.wanted = wanted;
    }

    public String webContent(WebDriver driver, String contentID) throws InterruptedException {
        Thread.sleep(3000 + (long) (Math.random() * 1000));

        String content = driver.findElement(By.className(contentID)).getText();

        return content;
    }

    private WebDriver webAccess(URL link) {
        String chromeDriverPath = "library/chromedriver/chromedriver.exe" ;
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200",
                "--ignore-certificate-errors", "--silent","--incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.get("" + link);

        return driver;
    }

    private WebDriver login(WebDriver driver) {
        driver.findElement(By.xpath("//input[@id='weblogin_netid']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@id='weblogin_password']")).sendKeys(password);

        driver.findElement(By.xpath("//input[@id='submit_button']")).click();

        return driver;
    }

    public WebDriver setup(URL link) {
        WebDriver driver = webAccess(link);
        driver = login(driver);
        return driver;
    }

    public void courseCheck(WebDriver driver, String content) throws InterruptedException, IOException {
        BufferedReader reader = new BufferedReader(new StringReader(content));
        while (reader.readLine() != null) {
            String[] courseWithSection = reader.readLine().split(" ");
            String course = courseWithSection[0] + courseWithSection[1];
            String[] SLN = reader.readLine().split(" ");
            String code = SLN[1];
            String[] seats = reader.readLine().split("/");
            int now = Integer.parseInt(seats[0].trim());
            int full = Integer.parseInt(seats[1].replaceAll("\\D", "").trim());
            for (int i = 0; i < 3; i += 1) {
                reader.readLine();
            }
            // Test to check number
            // System.out.println(now + "/" + full);

            if (now < full) {
                if (wanted.contains(course)) {
                    if (!course.equalsIgnoreCase("INFO350")) {
                        register(driver, code);
                    }
                }
            }
        }
    }

    private void register(WebDriver driver, String SLN) throws IOException, InterruptedException {
        URL registerSite = new URL("https://sdb.admin.uw.edu/students/uwnetid/register.asp");
        driver.navigate().to(registerSite);

        driver.findElement(By.xpath("//input[@name='action1']")).click();
        driver.findElement(By.xpath("//input[@name='sln6']")).sendKeys(SLN);
        driver.findElement(By.xpath("//input[@value=' Update Schedule ']")).click();

        driver.navigate().to(url);
    }
}
