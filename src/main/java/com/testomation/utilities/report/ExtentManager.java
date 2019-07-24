package com.testomation.utilities.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Protocol;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.testomation.base.ConfigTestData;
import com.testomation.constatnts.GlobalConstants;
import com.testomation.utilities.generator.ScreenshotGenarator;
import com.testomation.utilities.generic.Generic;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

public class ExtentManager{

    public static ExtentHtmlReporter extentHtmlReporter;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    private static ExtentManager extentManager;
    //Extent Report Declarations
    private static String scenarioName = "";
    private ScreenshotGenarator screenshotGenarator=null;

    public static String reportPath = null;
    HashMap<String, String> enviornmentHashmap;

    public ExtentManager() {


    }



    public static void createReportFile(String reportName) {
        //reportPath = GlobalConstants.workDir + Generic.readConfigProp("reports.path") + reportName;
        reportPath =  GlobalConstants.REPORTS_PATH + reportName;
        File path = new File(reportPath);
        extentHtmlReporter = new ExtentHtmlReporter(path);
        extentReports = new ExtentReports();
        extentReports.attachReporter(extentHtmlReporter);

    }

    public static ExtentManager getExtentManager(String testName) {
        if (extentManager == null) {
            extentManager = new ExtentManager();
        }

        if (!scenarioName.equalsIgnoreCase(testName)) {
            extentTest = extentReports.createTest(testName);
            extentTest.getModel().setStartTime(Calendar.getInstance().getTime());
            scenarioName = testName;
        }
        return extentManager;
    }



    public void setSystemInfo(HashMap<String, String> hsmap){
        Set set = hsmap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            extentReports.setSystemInfo(mentry.getKey().toString(),mentry.getValue().toString());
        }
    }
    public static void setSystemInfo(String mobileOS,String env,String platfom,String mobileDevice,String app){

    }
    public static void setSystemInfo(String mobileOS,String env,String platfom,String mobileDevice,String browser,String banner){

    }

    public static void setHtmlConfig(String docTitle){
        // make the charts visible on report open
        extentHtmlReporter.config().setChartVisibilityOnOpen(true);
        // report title
        extentHtmlReporter.config().setDocumentTitle(docTitle);
        // encoding, default = UTF-8
        extentHtmlReporter.config().setEncoding("UTF-8");
        // protocol (http, https)
        extentHtmlReporter.config().setProtocol(Protocol.HTTPS);
        // report or build name
        extentHtmlReporter.config().setReportName(docTitle);
        // chart location - top, bottom
        extentHtmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        // theme - standard, dark
        extentHtmlReporter.config().setTheme(Theme.STANDARD);
        // set timeStamp format
        //extentHtmlReporter.config().setTimeStampFormat("mm/dd/yyyy hh:mm:ss a");
        // add custom css
        extentHtmlReporter.config().setCSS("css-string");
        // add custom javascript
        extentHtmlReporter.config().setJS("js-string");
    }

    public void createTest(String testName){
       extentTest =  extentReports.createTest(testName);
    }

    public void endReport() {
        extentTest.getModel().setEndTime(Calendar.getInstance().getTime());
        extentReports.flush();
    }

    public static void addfinalstep(Status status,String scenario){
        switch (status) {
            case PASS:
                extentTest.log(Status.PASS, MarkupHelper.createLabel(scenario, ExtentColor.GREEN));
                break;
            case FAIL:
                extentTest.log(Status.FAIL, MarkupHelper.createLabel(scenario, ExtentColor.RED));
                break;
        }
    }
    public void addExecutionStep(Status status, String message){

        try {
            switch (status) {
                case INFO:
                    extentTest.log(Status.INFO, MarkupHelper.createLabel(message, ExtentColor.BLUE));
                    Log.info(message);

                    break;
                case PASS:
                    if(Generic.readConfigProp("extent.screenshot.add.status.pass").equalsIgnoreCase("on")){
                        extentTest.pass(MarkupHelper.createLabel(message+MediaEntityBuilder.createScreenCaptureFromPath(screenshotGenarator.getScreenshot(null)), ExtentColor.GREEN));
                    } else{
                        extentTest.pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
                    }

                    break;
                case FAIL:
                    if(Generic.readConfigProp("extent.screenshot.add.status.fail").equalsIgnoreCase("on")){
                        extentTest.fail(MarkupHelper.createLabel(message+MediaEntityBuilder.createScreenCaptureFromPath(screenshotGenarator.getScreenshot(null)), ExtentColor.RED));
                    } else{
                        extentTest.fail(MarkupHelper.createLabel(message, ExtentColor.RED));
                    }

                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void addException(Status status, Throwable t){
        switch (status) {
            case PASS:
                extentTest.pass(t);
                break;
            case FAIL:
                extentTest.fail(t);
                break;
        }
    }

    public void addfinalStatus(Status status){
        switch (status) {
            case PASS:
                extentTest.pass(MarkupHelper.createLabel("Final Test Status: PASS", ExtentColor.GREEN));
                break;
            case FAIL:
                extentTest.fail(MarkupHelper.createLabel("Final Test Status: FAIL", ExtentColor.RED));
                break;
            case SKIP:
                extentTest.skip(MarkupHelper.createLabel("Final Test Status: SKIP", ExtentColor.BLUE));
                break;
        }
    }
    public void assignGroup(String group){
        extentTest.assignCategory(group);
    }
    public void assignLog(String log){
        extentReports.setTestRunnerOutput(log);
    }


}
