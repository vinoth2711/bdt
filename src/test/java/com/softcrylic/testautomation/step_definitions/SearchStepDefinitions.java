package com.softcrylic.testautomation.step_definitions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import cucumber.annotation.After;
import cucumber.annotation.Before;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.softcrylic.testautomation.pages.*;

import static junit.framework.Assert.assertTrue;

public class SearchStepDefinitions {
    private WebDriver driver;
    private HomePage home;
    private SearchResultPage searchResult;

    @Before 
    public void prepare() throws MalformedURLException {
    	String url = "http://mayur_softcrylic:1034911c-db71-4c8e-8e12-7831abf6adf7@ondemand.saucelabs.com:80/wd/hub";
    	System.out.println("Running at: "+url);
    	 DesiredCapabilities capabillities = DesiredCapabilities.firefox();
         capabillities.setCapability("version", "5");
         capabillities.setCapability("platform", Platform.XP);
         capabillities.setCapability("name", "Testing Selenium 2 with Java on Sauce");

         this.driver = new RemoteWebDriver(
        		 new URL(url),
            capabillities);
         driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    	//DesiredCapabilities cap = DesiredCapabilities.firefox();
    	//cap.setCapability("version", "12.0");
    	//System.out.println("Changes reverted!");
        //driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
    }

    @After
    public void cleanUp() {
        driver.close();
    }

    @Given("^I want to know the weather forecast for coming days$")
    public void prepareHomePage() {
        home = new HomePage(driver);
    }

    @When("^I search for (.*)$")
    public void search(String location) {
        searchResult = home.searchFor(location);
    }

    @Then("^I should be able to get a weather forecast for (.*)$")
    public void assertTheSearchResult(String locationName) {
        LocationPage location = searchResult.clickOnTopSearchResultLink();
        String actualHeadLine = location.getHeadLine();

        assertTrue(actualHeadLine.contains(locationName));
    }
}