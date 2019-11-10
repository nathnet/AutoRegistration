package autoregistration;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegMain {

    @Test
    public void main() throws IOException, InterruptedException {
        URL url = new URL("https://notify.uw.edu/");
        SeleniumRegistration register = new SeleniumRegistration(url, "user", "pass");

        WebDriver content = register.webAccess();
        Thread.sleep(3000);

        System.out.println(content.findElement(By.tagName("body")).getText());
    }
}
