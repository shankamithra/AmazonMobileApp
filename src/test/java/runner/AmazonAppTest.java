package runner;

import appUtils.PageBase;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.HomePage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class AmazonAppTest extends PageBase {

    @Test
    public void purchaseProduct() throws IOException, InterruptedException {
        service = startServer();
        AndroidDriver<AndroidElement> driver = capabilities("AmazonApp");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        HomePage homePage = new HomePage(driver);

        homePage.loginToApp();
        homePage.verifyUserLogin();
        homePage.changePreference();
        homePage.changeOrientation();
        homePage.searchForProduct();
        homePage.changeOrientation();
        homePage.performSwipe();
        homePage.getProductDetails();
        homePage.checkout();
        homePage.cartValidation();
        homePage.changeResolution();
    }

    @BeforeTest
    public void killAllNodes() throws IOException, InterruptedException {
        Runtime.getRuntime().exec("taskkill /F /IM node.exe");
        Thread.sleep(3000);
    }

    @AfterTest
    public void End() throws IOException {
        driver.quit();
    }


}
