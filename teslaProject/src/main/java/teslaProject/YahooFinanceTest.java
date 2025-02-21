import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class YahooFinanceTest {
    public static void main(String[] args) {
    	
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        try {
            // Navigate to Yahoo Finance
            driver.get("https://finance.yahoo.com/");
            
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Search for Tesla stock (TSLA)
            WebElement searchBox = driver.findElement(By.id("ybar-sbq"));
            
            searchBox.sendKeys("TSLA");
            Thread.sleep(2000); 

            // Verify autosuggest contains "TESLA Inc."
            WebElement firstSuggestion = driver.findElement(By.xpath("//ul[@role='listbox']//li[1]"));
            
            if (firstSuggestion.getText().contains("TESLA Inc.")) {
                System.out.println("Autosuggest verified.");
            } else {
                System.out.println("Autosuggest verification failed.");
            }

            firstSuggestion.click();
            
            Thread.sleep(3000);

            WebElement stockPriceElement = driver.findElement(By.xpath("//fin-streamer[@data-field='regularMarketPrice']"));
            
            double stockPrice = Double.parseDouble(stockPriceElement.getText().replace(",", ""));
            if (stockPrice > 200) {
                System.out.println("Stock price verification passed: " + stockPrice);
            } else {
                System.out.println("Stock price verification failed: " + stockPrice);
            }

            WebElement previousClose = driver.findElement(By.xpath("//td[@data-test='PREV_CLOSE-value']"));
            
            WebElement volume = driver.findElement(By.xpath("//td[@data-test='TD_VOLUME-value']"));
            
            System.out.println("Previous Close: " + previousClose.getText());
            
            System.out.println("Volume: " + volume.getText());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           
            driver.quit();
        }
    }
}
