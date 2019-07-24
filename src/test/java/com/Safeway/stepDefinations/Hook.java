package com.Safeway.stepDefinations;

import com.aventstack.extentreports.Status;
import com.testomation.base.ConfigBase;
import com.testomation.base.ConfigTestData;
import com.testomation.constatnts.GlobalConstants;
import com.testomation.utilities.report.ExtentManager;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Hook extends ConfigBase {

    public static String scenarioName;
    ExtentManager extentManager;
    RemoteWebDriver driver;

    @Before
    public void startUp(Scenario scenario) throws Exception {
        driver = ConfigTestData.remoteDriver.get();
        scenarioName = scenario.getName();
        extentManager = ExtentManager.getExtentManager(scenarioName);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("driver::"+driver);
            try {
                final byte[] screenshot =
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (Exception e) {
            }
            scenario.embed(
                    ("Browser URL: " + driver.getCurrentUrl()).getBytes(), "text/plain");
            extentManager.addfinalstep(
                    Status.FAIL,
                    "Scenario '"
                            + scenario.getName()
                            + "' is failed. Please check log output for more details.");
        } else {
            extentManager.addfinalstep(Status.PASS,"Scenario '" + scenario.getName() + "' is passed.");
        }
        extentManager.assignGroup(GlobalConstants.GROUP_NAME);
        driver.quit();
    }
}
