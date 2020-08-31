package appUtils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class Utilities {
    AndroidDriver<AndroidElement> driver;

    public Utilities(AndroidDriver<AndroidElement> driver) throws FileNotFoundException {
        this.driver = driver;
    }

    public String getProperty(String key) throws IOException {
        String value = null;
        try {
            FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\DataProperties.properties");
            Properties prop = new Properties();
            prop.load(file);
            value = prop.getProperty(key);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public void scrollToElement(String text) {
        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + text + "\").instance(0))").click();
    }

}

