package com.Safeway.stepDefinations;

import com.aventstack.extentreports.Status;
import com.testomation.utilities.report.Report;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HomePageSteps extends StepManager {

    @Given("^User navigates to HomePage$")
    public void user_navigates_to_HomePage()  {
        Report.report(getStepName());
        homePage.navigateToHomePage();
    }

    @Then("^User Verifies \"([^\"]*)\" Page is displayed$")
    public void verifyPage(String url) throws Exception {
        Report.verify(homePage.verifyPage(url),getStepName());
    }

    @When("^Click on \"([^\"]*)\" link in the primary navigation$")
    public void clickShopLinkInPrimaryNavigation(String linkName) throws Exception {
        Report.report(getStepName());
        homePage.clickLinkFromPrimaryNavigation(linkName);
    }

    @Then("^Verify searchBar should be displayed$")
    public void verify_searchBar_should_be_displayed() throws Exception {
        Report.verify(homePage.isSearchTabDisplay(),getStepName());
    }

    @When("^User Clicks Sign In/Up  Button in header and modal$")
    public void clickSigninUpButtoninHeader() throws Exception {
        Report.report(getStepName());
        homePage.clickSigninup();
    }


    @When("^User Clicks Sign In Button in header and modal$")
    public void clickSignInButton() throws Throwable {
        Report.report(getStepName());
        homePage.homeSignIn();
    }

    @Then("^User Verifies Sign In or Sign Up modal at www pages should be displayed$")
    public void verifySignInorSignupModelisDisplayed() throws Throwable {
        Report.verify(homePage.homePageVerifySignUpModel(),getStepName());
    }



}
