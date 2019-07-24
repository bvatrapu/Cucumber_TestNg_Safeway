package com.Safeway.pages;

import com.Safeway.base.TestBase;
import com.Safeway.constants.dataKey;
import com.testomation.base.ConfigTestData;
import com.testomation.driverconfig.DriverBase;
import com.testomation.utilities.report.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.*;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends DriverBase {
    private RemoteWebDriver driver=null;
    public HomePageObjects homePageObjects;


    public HomePage(){
        driver = ConfigTestData.remoteDriver.get();
        homePageObjects = new HomePageObjects();
        PageFactory.initElements(driver,homePageObjects);
    }

    // Varibles Declarions
    List<WebElement> pageElements = new ArrayList<WebElement>();
    /*
        ###################################################################################
        ######################                                      #######################
        ######################    HOME PAGE   -  BUSINESS METHODS   #######################
        ######################                                      #######################
        ###################################################################################
    */

    /**
     * Description: To open the Home Page
     */
    public void navigateToHomePage(){
        try{
           navigateToUrl(readTestdata(dataKey.HomePage.getpropVal()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Description: TO verify the Home page is displayed or not
     * @return : Boolean value i.e if page is correct : true, is not correct : false
     */
    public boolean verifyPage(String url) {
        boolean status=true;
        if(!driver.getCurrentUrl().contains(url)){
            status=false;
        }
        return status;
    }

    public void clickLinkFromPrimaryNavigation(String linkname){
        boolean result=true;
        switch (TestBase.BANNER){
            case "safeway":
            case "vons":
                switch (linkname){
                    case "Pharmacy":
                        click_JavaScript(homePageObjects.lnkPharmacy);
                        break;
                    case "Your Store":
                        click_JavaScript(homePageObjects.lnkYourStore);
                        break;
                }
                break;
        }
    }

    public boolean isSearchTabDisplay() throws Exception {
        boolean result=true;
        if(!isExist(homePageObjects.inputSearch)){
            result=false;
        }
        return result;
    }

    public void clickSigninup() throws Exception {
        click_JavaScript(homePageObjects.btnSignUp);
    }


    public boolean homePageVerifySignUpModel() throws Exception {
       return isExist(homePageObjects.modelSignup);
    }

    public void homeSignIn() throws Exception {
        click_JavaScript(homePageObjects.lnkSignIn);
    }
    /*
       ###################################################################################
       ######################                                      #######################
       ######################    HOME PAGE   -  BUSINESS OBJECTS   #######################
       ######################                                      #######################
       ###################################################################################
   */
    class HomePageObjects{

        // Primary Navigation Links
        @FindBy(xpath = "//li[contains(@class,'primary')]//a[contains(text(),'Pharmacy')]")
        public WebElement lnkPharmacy;

        @FindBy(xpath = "//li[contains(@class,'primary')]//a[contains(text(),'Your Store')]")
        public WebElement lnkYourStore;

        @FindBy(xpath = "//div[@class='search-container-wrapper']/input[@type='search']")
        public WebElement inputSearch;

        @FindBy(id = "sign-in-profile-text")
        private WebElement btnSignUp;

        @FindBy(xpath = "//div[@id='createAccountModalj4u']//div[@class='container create-account-modal']")
        private WebElement modelSignup;

        @FindBy(id = "linkToSignIn")
        private WebElement lnkSignIn;
    }
}
