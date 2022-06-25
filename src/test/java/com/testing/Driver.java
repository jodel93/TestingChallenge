package com.testing;

import com.testing.enums.Browser;
import com.testing.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@ComponentScan(basePackages = {"com.testing.base", "com.testing.pages"})
@PropertySource("classpath:/properties/config.properties")
public class Driver {

    @Value("${browser.type:chrome}")
    private Browser browserType;

    @Value("${remote.run:false}")
    private boolean isRemote;

    private WebDriver driver;
    private String hubHost;
    private DesiredCapabilities desiredCapabilities;

    @Bean
    public WebDriver webDriver() throws MalformedURLException {
        if(isRemote){

            if(System.getProperty("HOST") != null)
                hubHost = System.getProperty("HOST");
            else hubHost = "localhost";

            if(System.getProperty("BROWSER") != null &&
                    System.getProperty("BROWSER").equalsIgnoreCase("firefox")) {
                desiredCapabilities = DesiredCapabilities.firefox();
            }

            else if(System.getProperty("BROWSER") != null &&
                    System.getProperty("BROWSER").equalsIgnoreCase("edge")){
                desiredCapabilities = DesiredCapabilities.edge();
            }

            else
            {
                desiredCapabilities = DesiredCapabilities.chrome();
            }

            return new RemoteWebDriver(new URL("http://"+ hubHost + ":4444/wd/hub"), desiredCapabilities);
        }

        return DriverFactory.setUpDriver(browserType);
    }
}