package autoregistration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.URL;
import java.time.Duration;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    // Will improve more in the future
    public String webContent(WebDriver driver, String contentID) {
        String content = "";

        try {
            // Find the expected element within a page
            WebDriverWait waitForElement = waitForElement(driver);
            waitForElement.until(ExpectedConditions
                    .visibilityOfNestedElementsLocatedBy(By.className(contentID), By.className("availability_count")));
            content = driver.findElement(By.className(contentID)).getText();
        } catch (NoSuchElementException e) {
            // Do nothing
        } catch (Exception e) {
            System.out.println("Element of the content is not found within 10 seconds");
            System.out.println("Retrying to connect to the server again");
            driver.navigate().refresh();
            content = webContent(driver, contentID);
        }

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

    private WebDriverWait waitForElement(WebDriver driver) {
        return (WebDriverWait) new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofMillis(1000))
                .withMessage("Element not found");
    }

    private WebDriver login(WebDriver driver) {
        try {
            WebDriverWait waitForElement = waitForElement(driver);

            // Find username field
            WebElement userField = waitForElement.until(ExpectedConditions
                    .visibilityOf(driver.findElement(By.xpath("//input[@id='weblogin_netid']"))));
            userField.sendKeys(username);

            // Find password field
            WebElement passField = waitForElement.until(ExpectedConditions
                    .visibilityOf(driver.findElement(By.xpath("//input[@id='weblogin_password']"))));
            passField.sendKeys(password);

            // Find login button
            WebElement loginButton = waitForElement.until(ExpectedConditions
                    .visibilityOf(driver.findElement(By.xpath("//input[@id='submit_button']"))));
            loginButton.click();
        } catch (Exception e) {
            if (!(e instanceof NoSuchElementException)) {
                System.out.println("Failed to log in within 10 seconds");
                System.out.println("Retrying to connect to the server again");
                driver.navigate().refresh();
                driver = login(driver);
            }
        }

        return driver;
    }

    public WebDriver setup(URL link) {
        WebDriver driver = webAccess(link);
        driver = login(driver);
        return driver;
    }

    public void courseCheck(WebDriver driver, String content) throws IOException {
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
                        if (checkIfRegistered(driver, course, code)) {
                            wanted.remove(course);
                        }
                        driver.navigate().to(url);
                    }
                }
            }
        }
    }

    private void register(WebDriver driver, String SLN) throws IOException {
        URL registerSite = new URL("https://sdb.admin.uw.edu/students/uwnetid/register.asp");
        driver.navigate().to(registerSite);

        try {
            WebDriverWait waitForElement = waitForElement(driver);

            // Find the class to drop
            WebElement dropClass = waitForElement.until(ExpectedConditions
                    .visibilityOf(driver.findElement(By.xpath("//input[@name='action1']"))));
            dropClass.click();

            // Find the field to input new class
            WebElement inputClass = waitForElement.until(ExpectedConditions
                    .visibilityOf(driver.findElement(By.xpath("//input[@name='sln6']"))));
            inputClass.sendKeys(SLN);

            // Find the submit button
            WebElement submitButton = waitForElement.until(ExpectedConditions
                    .visibilityOf(driver.findElement(By.xpath("//input[@value=' Update Schedule ']"))));
            submitButton.click();
        } catch (Exception e) {
            if (!(e instanceof NoSuchElementException)) {
                System.out.println("Failed to register for a class within 10 seconds");
                System.out.println("Retrying to connect to the server again");
                driver.navigate().refresh();
                register(driver, SLN);
            }
        }
    }

    public boolean checkIfRegistered(WebDriver driver, String course, String SLN) {
        String sourceCode = driver.getPageSource();
        String patternStr = "[0-9]{3}";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(course);
        matcher.find();
        int startNum = matcher.start();
        course = course.substring(0, startNum) + " " + course.substring(startNum);
        return sourceCode.contains(SLN) && sourceCode.contains(course);
    }
}
