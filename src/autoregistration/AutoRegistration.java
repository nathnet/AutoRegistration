package autoregistration;

import java.io.IOException;

public interface AutoRegistration {
    // Crawl to a particular site with given URL and return BufferedReader of content
    String webContent() throws IOException, InterruptedException;
}
