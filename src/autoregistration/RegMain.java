package autoregistration;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

import org.junit.Test;

public class RegMain {

    @Test
    public void main() throws IOException {
        URL url = new URL("https://notify.uw.edu/");
        AutoRegistration registor = new Registration(url);

        StringBuilder content = registor.webContent();

        System.out.println(content);
    }
}
