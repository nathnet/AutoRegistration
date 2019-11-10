package autoregistration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Registration implements AutoRegistration {

    private URL url;

    public Registration(URL url) {
        this.url = url;
    }

    @Override
    public StringBuilder webContent() throws IOException {
        BufferedReader content = new BufferedReader(new InputStreamReader(url.openStream()));

        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = content.readLine()) != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }
        content.close();

        return sb;
    }
}
