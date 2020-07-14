package autoregistration;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class RegMain {

    // Take user inputs for course codes, username, and password.
    // Refresh website to find courses that you specify.
    // If the course has space available, register for that course.
    // Otherwise, repeat the whole process.
    public static void main(String[] args) throws IOException, InterruptedException, NoSuchElementException {
        URL url = new URL("https://notify.uw.edu/");
        int trial = 0;
        int failure = 0;

        // Add course by course code with no space in between
        // i.e. CSE142, INFO101
        Set<String> wanted = new HashSet<String>();
        wanted.add("INFO310");
        wanted.add("INFO350");

        // For user and password input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Username: ");
        String username = br.readLine();
        System.out.print("Password: ");
        String password = br.readLine();
        // For user and password input in console
        /*Console console = System.console();
        String username = console.readLine("Username: ");
        String password = String.valueOf(console.readPassword("Password: "));*/

        // Create auto register and connect to NotifyUW
        SeleniumRegistration register = new SeleniumRegistration(url, username, password, wanted);
        WebDriver driver = register.setup(url);

        // For certain amount of trials
        //for (int trial = 1; trial <= 1000000; trial += 1) {
        while (true) {
            System.out.println("Trial no." + ++trial);

            try {
                // Read website's content and check for courses that are available for registration
                // Refresh every time after reading
                String content = register.webContent(driver, "notification_list");
                register.courseCheck(driver, content);
                driver.navigate().refresh();
            } catch (Exception e) {
                // If reading fails, scrap the old one and restart the crawler
                System.out.println("Connection failure no." + ++failure);
                e.printStackTrace();
                Thread.sleep(3000);
                driver = register.setup(url);
            }
        }

        // For when the program finishes running
        // driver.close();
    }
}
