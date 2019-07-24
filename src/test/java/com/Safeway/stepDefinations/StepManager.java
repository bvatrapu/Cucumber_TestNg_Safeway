package com.Safeway.stepDefinations;

import com.Safeway.common.PageManager;
import com.Safeway.pages.HomePage;
import com.Safeway.pages.SignInPage;
import com.testomation.utilities.report.ExtentManager;
import com.testomation.utilities.report.ThreadLocalStepDefinitionMatch;
import cucumber.runtime.StepDefinitionMatch;

public class StepManager  {

    StepDefinitionMatch stepMatch;
    public ExtentManager extentManager ;
    PageManager pageManager;
    // Page classes
    HomePage homePage;
    SignInPage signInPage;


    public StepManager() {
        initializePages();
        extentManager = ExtentManager.getExtentManager(Hook.scenarioName);
    }
    public void initializePages() {
        pageManager = new PageManager();
        homePage = pageManager.getHomePage();
        signInPage = pageManager.getSignInPage();
    }

    public String getStepName(){
        stepMatch = ThreadLocalStepDefinitionMatch.get();
        return stepMatch.getStepName();
    }

    public String getPattern(){
        stepMatch = ThreadLocalStepDefinitionMatch.get();
        return stepMatch.getPattern();
    }


}
