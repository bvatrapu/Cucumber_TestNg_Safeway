package com.Safeway.common;

import com.testomation.base.ConfigTestData;
import com.testomation.utilities.generic.Generic;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ProjectGeneric {

    private Generic generic = null;
    public ConfigTestData configTestData=null;
    RemoteWebDriver driver=null;

    public ProjectGeneric(ConfigTestData configTestData) {
        driver=ConfigTestData.remoteDriver.get();
        this.configTestData = configTestData;
        generic = new Generic(configTestData);
    }


}
