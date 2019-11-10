package autoregistration;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;

public interface AutoRegistration {
    // Crawl to a particular site with given URL and return BufferedReader of content
    StringBuilder webContent() throws IOException;
}
