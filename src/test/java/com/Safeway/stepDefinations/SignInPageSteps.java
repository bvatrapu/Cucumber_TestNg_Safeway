package com.Safeway.stepDefinations;

import com.testomation.utilities.report.Report;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SignInPageSteps extends StepManager {

    @And("^Clear All Cookies$")
    public void clear_all_cookies() throws Exception {
        Report.report(getStepName());
        signInPage.clearAllCookies();
    }

    @Then("^User Validates Sign Message in www page$")
    public void user_Validates_Sign_Message_in_www_page() throws Throwable {
        Report.verify(signInPage.signInText(),getStepName());
    }

    @When("^User Enters Email ID and Password$")
    public void user_Enters_Email_ID_and_Password() throws Exception {
        Report.report(getStepName());
        signInPage.enterEmailIdPassword();
    }
    @Then("^User Clicks Submit Button$")
    public void user_Submits_Signin_validates() throws Exception {
        Report.report(getStepName());
        signInPage.submitSignIn();
    }
}
