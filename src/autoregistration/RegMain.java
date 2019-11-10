package autoregistration;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;

public class RegMain {

    @Test
    public void main() throws IOException, InterruptedException {
        URL url = new URL("https://notify.uw.edu/");
        for (int i = 1; i <= 1; i += 1) {
            SeleniumRegistration register = new SeleniumRegistration(url, "user", "pass");

            // String content = register.webContent();

            register.courseCheck();

            /*System.out.println(content);
            System.out.println(i);*/
        }
    }
}
