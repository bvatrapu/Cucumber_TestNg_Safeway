/**
 *
 *	
 *	14-Aug-2016
 */
package com.Safeway.runners;

import com.Safeway.base.TestBase;
import com.testomation.constatnts.GlobalConstants;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


@CucumberOptions(features = { "classpath:features/SignInPage.feature" },
		glue = {"classpath:com.Safeway.stepDefinations"},
		plugin = {"pretty","html:report", "json:reports.json",
				"rerun:target/rerun.txt", "com.testomation.utilities.report.CustomFormatter","com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html" },

		tags = {"@Regression","~@Smoke"})

public class SignInFeatureRunner extends TestBase {

	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws Exception {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() throws Exception {
		testNGCucumberRunner.finish();
	}
	@Test(groups = "SignInFeature", description = "Runs Cucumber Feature", dataProvider = "features")
	public void HomeFeature(CucumberFeatureWrapper cucumberFeature) {
		GlobalConstants.GROUP_NAME="SignInFeature";
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}
	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures();
	}
}
