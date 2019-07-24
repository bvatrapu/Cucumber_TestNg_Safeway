package com.testomation.base;

import com.testomation.constatnts.GlobalConstants;
import com.testomation.utilities.generator.ScreenshotGenarator;
import com.testomation.utilities.generic.Generic;
import com.testomation.utilities.report.ExtentManager;
import com.testomation.utilities.report.Log;
import com.testomation.utilities.report.Report;
import com.testomation.utilities.utils.DateUtils;
import com.testomation.utilities.utils.GridUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @Author Bharath Kumar Reddy V
 * @Date 10-June-2019
 */

public class ConfigBase {

    public ConfigTestData configTestData=new ConfigTestData();
    public ExtentManager extentManager = new ExtentManager();
    public ConfigDriver configDriver;
    public ScreenshotGenarator screenshotGenarator;
    public Generic generic;

   // public TestNGCucumberRunner testNGCucumberRunner;
    public static ResourceBundle rbTestdata;

    HashMap<String, String> enviornmentHashmap;
    public GridUtils seleniumGrid;
    public String reportName;
    public Report report = new Report(configTestData,extentManager);

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(){
        try {
            //Start Hub and Node
            seleniumGrid=new GridUtils();
            seleniumGrid.start_hub_node();
            Generic.readConfigProp();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @BeforeTest(alwaysRun = true)
    public void extentConfig(ITestContext context) {
        String groupName = null;
        configTestData.suiteXmlName = context.getSuite().getName();
//        groupName=generic.getSuiteXmlGroupName(context.getIncludedGroups());
        reportName = configTestData.suiteXmlName + "_[" + DateUtils.getTime() +"]_"+ DateUtils.getDate()+".html";
        extentManager.createReportFile(reportName);
    }

    @Parameters({"environment","platform","service"})
    @BeforeClass(alwaysRun = true)
    public void initSetup(String environment, String platformType, String service){
        if(System.getProperty("environment")==null) {
            configTestData.testEnvironment = environment.toLowerCase();
            configTestData.testservice = service.toLowerCase();
            configTestData.testPlatform = platformType.toLowerCase();
        }else {
            configTestData.testEnvironment = System.getProperty("environment").toLowerCase();
            configTestData.testservice = System.getProperty("service").toLowerCase();
            configTestData.testPlatform = System.getProperty("platform").toLowerCase();
        }
    }

    @Parameters({"browser","chromeDeviceName","network","mobileDevice","appName","udid","deviceName","platformName","platformVersion", "manufacturer"})
    @BeforeMethod(alwaysRun = true)
    public void initSetup(Method testMethod,@Optional("optional") String browser,@Optional("optional") String chromeDeviceName,@Optional("optional")String network,@Optional("optional")String mobileDevice,@Optional("optional")String appName,@Optional("optional")String udid,@Optional("optional") String deviceName,@Optional("optional")String platformName,@Optional("optional")String platformVersion,@Optional("optional")String manufacturer) throws Exception {
        initClass();
        if(System.getProperty("browser")!=null) {
            configTestData.testBrowser = System.getProperty("browser").toLowerCase();
            configTestData.chrome_deviceName = System.getProperty("chromeDeviceName").toLowerCase();
        }else {
            configTestData.testBrowser = browser.toLowerCase();
            configTestData.chrome_deviceName = chromeDeviceName.toLowerCase();
        }
        if(System.getProperty("mobileDevice")!=null) {
            configTestData.testNetowk = System.getProperty("network").toLowerCase();
            configTestData.testDeviceName = System.getProperty("mobileDevice").toLowerCase();
            configTestData.testAppName = System.getProperty("appName").toLowerCase();
            if(System.getProperty("udid")!=null){
                configTestData.mb_udid = System.getProperty("udid").toLowerCase();
                configTestData.mb_deviceName = System.getProperty("deviceName").toLowerCase();
                configTestData.mb_platformName = System.getProperty("platformName").toLowerCase();
                configTestData.mb_platformVersion = System.getProperty("platformVersion").toLowerCase();
                configTestData.mb_manufacturer = System.getProperty("manufacturer").toLowerCase();
            } else {
                if(configTestData.testPlatform.equalsIgnoreCase("mobile")){
                    generic.readMobileCapabilities(mobileDevice);
                }
            }

        } else {
            configTestData.testNetowk = network.toLowerCase();
            configTestData.testDeviceName = mobileDevice.toLowerCase();
            configTestData.testAppName = appName.toLowerCase();
            if(udid.equalsIgnoreCase("optional")) {
                if(configTestData.testPlatform.equalsIgnoreCase("mobile")) {
                    generic.readMobileCapabilities(mobileDevice);
                    if(configTestData.testservice.equalsIgnoreCase("APP")) {
                        generic.readMobileAppCapabilities(configTestData.testAppName.toLowerCase());
                    }
                }
            } else{
                configTestData.mb_udid = udid.toLowerCase();
                configTestData.mb_deviceName = deviceName.toLowerCase();
                configTestData.mb_platformName = platformName.toLowerCase();
                configTestData.mb_platformVersion = platformVersion.toLowerCase();
                configTestData.mb_manufacturer = manufacturer.toLowerCase();
            }
        }
        try {

            configTestData.testMethodName = testMethod.getName();
            configTestData.remoteDriver = new ThreadLocal<>();
            initDriver();
            //extentManager.createTest(configTestData.testMethodName);
        } catch (Exception e){
            e.printStackTrace();
        }
        Test testClass = testMethod.getAnnotation(Test.class);
        configTestData.groupName = testClass.groups()[0];
        Log.info(configTestData.testMethodName + " :: TestScript is Start");

    }

//    @AfterMethod(alwaysRun = true)
//    protected void afterMethod(ITestResult result) {
//        for(String group:result.getMethod().getGroups()){
//            System.out.println("group::"+group);
//            extentManager.assignGroup(group);
//        }
//        try{
//       //     ConfigTestData.remoteDriver.get().quit();
//            extentManager.addfinalStatus(configTestData.finalTestCaseStatus);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public void initClass(){
        configDriver = new ConfigDriver(configTestData);
        screenshotGenarator = new ScreenshotGenarator(configTestData);
        generic = new Generic(configTestData);
    }

    public void initDriver(){
        try {
            configDriver.initDriver();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        try{
            enviornmentHashmap = new HashMap<String, String>();
            if(configTestData.testPlatform.equalsIgnoreCase("desktop")){
                enviornmentHashmap.put("OS",Generic.getCurretnPlatform().toString());
                enviornmentHashmap.put("UserName",System.getProperty("user.name"));
                enviornmentHashmap.put("Environment",configTestData.testEnvironment.toUpperCase());
                enviornmentHashmap.put("Platform",configTestData.testPlatform.toUpperCase());
                enviornmentHashmap.put("Browser",configTestData.testBrowser.toUpperCase());
                extentManager.setSystemInfo(enviornmentHashmap);
            } else {
                if(configTestData.testAppName.equalsIgnoreCase("optional")){
                    if(configTestData.mb_platformName.equalsIgnoreCase("android")){
                        configTestData.testBrowser = "CHROME";
                    } if(configTestData.mb_platformName.equalsIgnoreCase("ios")){
                        configTestData.testBrowser = "SAFARI";
                    }
                    enviornmentHashmap.put("OS",configTestData.mb_platformName.toUpperCase());
                    enviornmentHashmap.put("UserName",System.getProperty("user.name"));
                    enviornmentHashmap.put("Environment",configTestData.testEnvironment.toUpperCase());
                    enviornmentHashmap.put("Platform",configTestData.testPlatform.toUpperCase());
                    enviornmentHashmap.put("Mobile Device",configTestData.mb_deviceName.toUpperCase());
                    enviornmentHashmap.put("Browser",configTestData.testBrowser);
                    extentManager.setSystemInfo(enviornmentHashmap);
                } else{
                    enviornmentHashmap.put("OS",configTestData.mb_platformName.toUpperCase());
                    enviornmentHashmap.put("UserName",System.getProperty("user.name"));
                    enviornmentHashmap.put("Environment",configTestData.testEnvironment.toUpperCase());
                    enviornmentHashmap.put("Platform",configTestData.testPlatform.toUpperCase());
                    enviornmentHashmap.put("Mobile Device",configTestData.mb_deviceName.toUpperCase());
                    enviornmentHashmap.put("App",configTestData.testAppName);
                    extentManager.setSystemInfo(enviornmentHashmap);
                }
            }


            extentManager.assignLog(generic.readFile(GlobalConstants.workDir+ Generic.readConfigProp("log.file")));
            extentManager.setHtmlConfig(configTestData.suiteXmlName);
            extentManager.endReport();
           // Xl.generateReport(GlobalConstants.REPORTS_PATH,reportName);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String readTestdata(String key){
        return rbTestdata.getString(key);
    }


}
