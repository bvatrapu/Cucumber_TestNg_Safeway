package com.Safeway.base;

import com.Safeway.constants.ProjectConstants;
import com.testomation.base.ConfigBase;
import com.testomation.utilities.generic.Generic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

public class TestBase extends ConfigBase {
    // Variable declarions

    public String TEST_DATA_FOLDER_PATH=null;
    public static String BANNER = null;

    @Parameters({"banner"})
    @BeforeMethod(alwaysRun = true)
    public void initProjectSetup(String banner) throws Exception {

        if(System.getProperty("banner")==null) {
            BANNER = banner.toLowerCase();
        } else{
            BANNER = System.getProperty("banner").toLowerCase();
        }
        TEST_DATA_FOLDER_PATH=BANNER;
        loadTestData(TEST_DATA_FOLDER_PATH,configTestData.testEnvironment);
    }
    public static ResourceBundle loadTestData(String tdpath, String env){
        ClassLoader loader=null;
        try {
            File file = new File(Generic.getTestDataPath(tdpath));
            URL[] urls = {file.toURI().toURL()};
            loader = new URLClassLoader(urls);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rbTestdata = ResourceBundle.getBundle(env, Locale.getDefault(), loader);
    }
}
