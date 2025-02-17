// Import required libraries
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class selenium {
    public static void main(String[] args) {
        //here we are initializing driver for the browser and our browser is chrome here
        WebDriver driver = new ChromeDriver();
        //We then give the driver path to the website we have chose
        driver.get("https://aws.amazon.com/s3/pricing/"); // Open AWS S3 Pricing page

        //here we have a wait time to give enough time for selenium to find the elements
        // TASK 3 ATTEMPTED
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            //TASK 3 ATTEMPTED closed up the pop as the selenium cant go further to retrieve anything
            try {
                // here we are using css selector in order to click on the pop up button
                WebElement b1 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[aria-label='Close']")));
                b1.click();
                //this will be printed when the button is pressed, this is done in order to debug
                System.out.println("Closed the pop-up.");
            } catch (Exception e) {
                System.out.println("No pop-up found, continuing."); // throw an exception if there is no pop up
            }

            //here we are trying to get data from the website its a table and then we will store it in a csv file
            try { //task1
                //here we have gone to the website-inspected the table elements and then copied css selector path
                WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#aws-element-460808b2-3d0a-407a-9d2a-97009814657c table")));

                //Here we are getting all of the rows by using tr
                List<WebElement> rows = table.findElements(By.tagName("tr"));

                //here we are using the built-in java function in order to create a csv file
                FileWriter writer = new FileWriter("aws_pricing.csv");

                //here we will have a loop over the table in order to get elements of the table
                for (WebElement row : rows) {
                    List<WebElement> cells = row.findElements(By.tagName("td"));

                    //skip if the rows are empty
                    if (cells.isEmpty()) continue;

                    //here we are storing the data in to a variable called data
                    StringBuilder data = new StringBuilder();
                    for (WebElement cell : cells) {
                        data.append(cell.getText().trim()).append(",");
                    }

                    //After getting the data we will write into the table
                    writer.append(data.substring(0, data.length() - 1)).append("\n");
                }

                //Close CSV file
                writer.flush();
                writer.close();
                System.out.println("AWS S3 Pricing data saved to aws_pricing.csv");

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error saving CSV file.");
            }

            try {
                // Open CSV file
                FileWriter writer = new FileWriter("aws_pricing_combined.csv");
                writer.append("Service,Storage Type,Price per GB\n"); // CSV Header

                // TASK 1: Scrape AWS S3 Pricing
                driver.get("https://aws.amazon.com/s3/pricing/");
                System.out.println("Scraping AWS S3 Pricing...");

                try {
                    WebElement s3Table = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#aws-element-460808b2-3d0a-407a-9d2a-97009814657c table")));
                    List<WebElement> s3Rows = s3Table.findElements(By.tagName("tr"));

                    for (WebElement row : s3Rows) {
                        List<WebElement> cells = row.findElements(By.tagName("td"));
                        if (cells.isEmpty()) continue;

                        StringBuilder rowData = new StringBuilder();
                        rowData.append("AWS S3,"); // Label the data as S3 pricing
                        for (WebElement cell : cells) {
                            rowData.append(cell.getText().trim()).append(",");
                        }
                        writer.append(rowData.substring(0, rowData.length() - 1)).append("\n");
                    }
                } catch (Exception e) {
                    System.out.println("Error extracting AWS S3 pricing.");
                }

                // TASK 2: Scrape AWS EC2 Pricing
                driver.get("https://aws.amazon.com/ec2/pricing/");
                System.out.println("Scraping AWS EC2 Pricing...");

                try {
                    WebElement ec2Table = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table"))); // Adjust selector if needed
                    List<WebElement> ec2Rows = ec2Table.findElements(By.tagName("tr"));

                    for (WebElement row : ec2Rows) {
                        List<WebElement> cells = row.findElements(By.tagName("td"));
                        if (cells.isEmpty()) continue;

                        StringBuilder rowData = new StringBuilder();
                        rowData.append("AWS EC2,"); // Label the data as EC2 pricing
                        for (WebElement cell : cells) {
                            rowData.append(cell.getText().trim()).append(",");
                        }
                        writer.append(rowData.substring(0, rowData.length() - 1)).append("\n");
                    }
                } catch (Exception e) {
                    System.out.println("Error extracting AWS EC2 pricing.");
                }

                // Close CSV file
                writer.flush();
                writer.close();
                System.out.println("AWS S3 & EC2 Pricing data saved to aws_pricing_combined.csv");

            } catch (IOException e) {
                System.out.println("Error writing to file.");
                e.printStackTrace();
            }

            // TASK 1, here we are clicking a button
            try {
                //again using a css selector we will be spotting it
                WebElement b2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='registration/index.html']")));

                // Scroll to the button before clicking

                // Click the button
                b2.click();

                // Wait for redirection
                wait.until(ExpectedConditions.urlContains("portal.aws.amazon.com"));
                System.out.println("Clicked 'Get Started for Free' button.");

                //here we are redirecting to another page
                //task 2
                WebElement b3 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#m-nav > div.m-nav-header.lb-clearfix.m-nav-double-row > nav > a:nth-child(1)")));
                b3.click();
                b3.sendKeys("Aleena Ali Azeem");

            } catch (TimeoutException e) {
                System.out.println("complete");
            }
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
