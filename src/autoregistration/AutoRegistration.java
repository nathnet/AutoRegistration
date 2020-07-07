package autoregistration;

import java.io.IOException;
import java.net.URL;

// Not currently in use, for future iteration for alternative solutions
public interface AutoRegistration {
    // Crawl to a particular site with given URL and return BufferedReader of content
    String webContent(URL url) throws IOException, InterruptedException;
}
