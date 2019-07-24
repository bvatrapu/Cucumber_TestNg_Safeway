package com.Safeway.pages;

import com.Safeway.constants.dataKey;
import com.testomation.base.ConfigTestData;
import com.testomation.constatnts.GlobalConstants;
import com.testomation.driverconfig.DriverBase;
import com.testomation.utilities.report.Log;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class SignInPage extends DriverBase {
    private RemoteWebDriver driver=null;
    public SignInPageObjects signInPageObjects;

    public SignInPage(){
        driver = ConfigTestData.remoteDriver.get();
        signInPageObjects = new SignInPageObjects();
        PageFactory.initElements(driver,signInPageObjects);
    }

    // Varibles Declarions
    List<WebElement> pageElements = new ArrayList<WebElement>();

    /*
            ###################################################################################
            ######################                                      #######################
            ######################    RESERVATION PAGE   -  BUSINESS METHODS   #######################
            ######################                                      #######################
            ###################################################################################
     */

    public boolean verifyPage(String url){
        boolean status=true;
        if(!driver.getCurrentUrl().contains(url)){
            status=false;
        }
        return status;
    }

    public void clearAllCookies(){
        deleteAllCookies();
    }

    public boolean signInText() throws Exception {
        return verifyLabelText(signInPageObjects.headertxtSignIn,"For your security, we do not recommend checking this box if you are using a public device.", GlobalConstants.VERIFY_TEXT_PARTIAL);
    }

    public void enterEmailIdPassword() throws Exception {
        System.out.println("userid::"+readTestdata(dataKey.SignIn_UserName.getpropVal()));
        enterdata(signInPageObjects.inputEmailId,readTestdata(dataKey.SignIn_UserName.getpropVal()));
        enterdata(signInPageObjects.inputPassword,readTestdata(dataKey.SignIn_Password.getpropVal()));
    }

    public void submitSignIn() throws Exception {
        click_JavaScript(signInPageObjects.btnSignIn);
    }

    /*
           ###################################################################################
           ######################                                      #######################
           ######################    RESERVATION PAGE   -  BUSINESS OBJECTS   #######################
           ######################                                      #######################
           ###################################################################################
       */

    class SignInPageObjects{
        @FindBy(xpath = "//p[@class='warningKMSIText']")
        private WebElement headertxtSignIn;

        @FindBy(id = "label-email")
        @CacheLookup
        private WebElement inputEmailId;

        @FindBy(id = "label-password")
        @CacheLookup
        private WebElement inputPassword;

        @FindBy(id = "btnSignIn")
        @CacheLookup
        private WebElement btnSignIn;
    }
}
