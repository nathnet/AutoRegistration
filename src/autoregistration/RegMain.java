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
        AutoRegistration register = new SeleniumRegistration(url, "user", "pass");

        String content = register.webContent();

        System.out.println(content);
    }
}
