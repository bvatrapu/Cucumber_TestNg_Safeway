package com.Safeway.common;

import com.Safeway.pages.HomePage;
import com.Safeway.pages.SignInPage;

public class PageManager {

    //private ConfigTestData configTestData;
    private HomePage homePage;
    private SignInPage signInPage;

    public HomePage getHomePage(){
        return (homePage==null)?homePage=new HomePage():homePage;
    }

    public SignInPage getSignInPage(){
        return (signInPage ==null)? signInPage =new SignInPage(): signInPage;
    }

}
