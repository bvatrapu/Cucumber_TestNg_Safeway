package com.testomation.driverconfig.drivers;


import com.testomation.base.ConfigTestData;
import com.testomation.driverconfig.driverbase.BaseMobileDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class Android extends BaseMobileDriver<AppiumDriver, DesiredCapabilities, Android> {
    private ConfigTestData configTestData=null;
    public Android(ConfigTestData configTestData) {
        this.configTestData = configTestData;
    }

    @Override
    public DesiredCapabilities getDefaultOptions() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (configTestData.testNetowk.equalsIgnoreCase("cloud")) {
            if(ConfigTestData.mobile_cloud_env.equalsIgnoreCase("public")){
                capabilities.setCapability("user", ConfigTestData.mobile_cloud_public_userid);
                capabilities.setCapability("password", ConfigTestData.mobile_cloud_public_password);
            } else{
                capabilities.setCapability("user", ConfigTestData.mobile_cloud_private_userid);
                capabilities.setCapability("password", ConfigTestData.mobile_cloud_private_password);
            }
        }
        capabilities.setCapability(MobileCapabilityType.UDID, configTestData.mb_udid);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, configTestData.mb_deviceName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, configTestData.mb_platformName);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, configTestData.mb_platformVersion);
        capabilities.setCapability("appPackage", configTestData.mb_appPackage);
        capabilities.setCapability("appActivity", configTestData.mb_appActivity);
        capabilities.setCapability("newCommandTimeout", 9999*5);
        return capabilities;
    }

  protected DesiredCapabilities getOptions(DesiredCapabilities capabilities) {
    return capabilities == null ? getDefaultOptions() : capabilities;
  }

  @Override
  public AppiumDriver buildMobileDriver(DesiredCapabilities options)
      throws MalformedURLException {
          if (StringUtils.equalsIgnoreCase(configTestData.testNetowk, "cloud")) {
              if (StringUtils.equalsIgnoreCase(ConfigTestData.mobile_cloud_env, "public")) {
                  configTestData.androidDriver = setMobileAppDriverManage(new AndroidDriver(new URL(ConfigTestData.mobile_cloud_public_url), getOptions(options)));
              } else {
                  configTestData.androidDriver = setMobileAppDriverManage(new AndroidDriver(new URL(ConfigTestData.mobile_cloud_private_url), getOptions(options)));
              }
          } else {
              configTestData.androidDriver = setMobileAppDriverManage(new AndroidDriver(new URL(ConfigTestData.localGridHub), getOptions(options)));
          }
      return configTestData.androidDriver;
  }

}
