package com.testing;

import com.testing.utils.ConfigurationManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = {"com.testing.base", "com.testing.pages"})
public class Driver {

    private WebDriver driver;
    private final String localDriverPath = System.getProperty("user.dir") + "/src/test/resources/drivers/%s";

    @Bean
    public WebDriver webDriver(){
        String browser = ConfigurationManager.getInstance().getConfiguration("BROWSER");

        if (browser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", String.format(localDriverPath, "chromedriver.exe"));
            ChromeOptions option = new ChromeOptions();
//            option.addArguments("--headless");
            this.driver = new ChromeDriver(option);
        }

        if (browser.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.geckodriver.driver", String.format(localDriverPath, "geckodriver.exe"));
            this.driver = new FirefoxDriver();
        }

        if (browser.equalsIgnoreCase("edge")){
            System.setProperty("webdriver.edge.driver", String.format(localDriverPath, "msedgediver.exe"));
            this.driver = new EdgeDriver();
        }

        this.driver.manage().deleteAllCookies();
        this.driver.manage().window().maximize();

        return this.driver;
    }
}
