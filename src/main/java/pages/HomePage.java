package pages;

import appUtils.PageBase;
import appUtils.Utilities;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.testng.Reporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.IOException;
import java.time.Duration;
import java.util.List;


public class HomePage extends PageBase {

    public HomePage(AndroidDriver<AndroidElement> driver) throws IOException {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    Utilities utilObj = new Utilities(driver);

    //Page Objects
    @AndroidFindBy(xpath = "//android.widget.Button[@text='Already a customer? Sign in']")
    public WebElement signIn;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='ap_email_login']")
    public WebElement emailId;

    @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='continue']")
    public WebElement continueButton;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='ap_password']")
    public WebElement password;

    @AndroidFindBy(xpath = "//android.widget.Button[@resource-id='signInSubmit']")
    public WebElement signInButton;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='com.amazon.mShop.android.shopping:id/rs_search_src_text']")
    public WebElement searchBox;

    @AndroidFindBy(xpath = "//android.view.View[@resource-id='bylineInfo']//android.widget.TextView")
    public WebElement tvBrandName;

    @AndroidFindBy(xpath = "//android.view.View[@resource-id='title_feature_div']//android.view.View")
    public WebElement tvDescription;

    @AndroidFindBy(xpath = "//android.view.View[@resource-id='price']//android.view.View//android.view.View")
    public WebElement tvPrice;

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/action_bar_cart")
    public WebElement topCartIcon;

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/action_bar_burger_icon")
    public WebElement hamburgerMenu;

    @AndroidFindBy(id = "com.amazon.mShop.android.shopping:id/gno_greeting_text_view")
    public WebElement usernameLogo;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Country/Region: United States']")
    public WebElement countryOption;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Country/Region: India']")
    public WebElement selectedCountry;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Done']")
    public WebElement doneButton;

    static String cartTvDescription;
    static String cartTvPrice;

    static String actualTvName;
    static String actualTvDescription;
    static String actualTvPrice;

    String loginUsername = utilObj.getProperty("username");
    String loginPassword = utilObj.getProperty("password");

    public void loginToApp() throws InterruptedException {
        signIn.click();
        emailId.sendKeys(loginUsername);
        continueButton.click();
        password.sendKeys(loginPassword);
        signInButton.click();
        Reporter.log("Logged in to the application", true);
        sleepSome(20000);
    }


    public void verifyUserLogin() throws InterruptedException {
        hamburgerMenu.click();
        sleepSome(3000);
        Assert.assertTrue(usernameLogo.isDisplayed(), "Username not displayed");
        Reporter.log("User login validation passed", true);

    }

    public void changePreference() throws InterruptedException {
        utilObj.scrollToElement("Settings");
        sleepSome(5000);
        List<AndroidElement> items1 = driver.findElements(By.id("com.amazon.mShop.android.shopping:id/anp_drawer_item"));
        items1.get(0).click();
        sleepSome(2000);
        countryOption.click();
        sleepSome(2000);
        List<AndroidElement> countries = driver.findElements(By.xpath("//android.widget.RadioButton"));
        countries.get(3).click();
        sleepSome(2000);
        Assert.assertTrue(selectedCountry.isDisplayed());
        doneButton.click();
        Reporter.log("Changed the country preference", true);
    }

    public void changeOrientation() throws InterruptedException {
        String orientation = driver.getOrientation().toString();
        Reporter.log("Handling screen orientation: Current Screen Orientation is " + driver.getOrientation());
        if (orientation.equals("PORTRAIT"))
            driver.rotate(org.openqa.selenium.ScreenOrientation.LANDSCAPE);
        else
            driver.rotate(org.openqa.selenium.ScreenOrientation.PORTRAIT);
        sleepSome(3000);
        Reporter.log("Screen orientation changed to : " + driver.getOrientation(), true);

    }

    public void searchForProduct() throws InterruptedException, IOException {
        String productName = utilObj.getProperty("searchProduct");
        searchBox.click();
        sleepSome(2000);
        driver.getKeyboard().sendKeys(productName);
        Reporter.log("Searching for Product : " + productName, true);
        sleepSome(3000);
        List<AndroidElement> items = driver.findElements(By.xpath("//android.widget.LinearLayout[@resource-id='com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_suggestions']"));
        items.get(0).click();
        sleepSome(3000);
        utilObj.scrollToElement("QLED");
        sleepSome(3000);

    }

    public void getProductDetails() {
        actualTvName = tvBrandName.getText();
        actualTvDescription = tvDescription.getText();
        utilObj.scrollToElement("EMI");
        actualTvPrice = tvPrice.getText();
        Reporter.log("Selected Product Details : \n Name : " + actualTvName + "\n Description : " + actualTvDescription + "\n Price : " + actualTvPrice, true);

    }

    public void checkout() throws InterruptedException {
        utilObj.scrollToElement("Add to Cart");
        sleepSome(5000);
        topCartIcon.click();
        Reporter.log("Product added to cart", true);

    }

    public void cartValidation() throws InterruptedException {
        boolean descriptionFlag = false;
        boolean priceFlag = false;
        String[] splited = actualTvPrice.split("\\s+");
        actualTvPrice = splited[1];
        sleepSome(8000);
        List<AndroidElement> textView = driver.findElements(By.className("android.widget.TextView"));
        for (int i = 0; i < textView.size(); i++) {
            if (textView.get(i).getText().contains(actualTvDescription.substring(0, 30))) {
                descriptionFlag = true;
                cartTvDescription = textView.get(i).getText();
                Reporter.log("Cart Description : " + cartTvDescription, true);
            }
        }
        for (int j = 0; j < textView.size(); j++) {
            if (textView.get(j).getText().contains(actualTvPrice)) {
                priceFlag = true;
                cartTvPrice = textView.get(j).getText();
                Reporter.log("Cart Price : " + cartTvPrice, true);
            }
        }

        Assert.assertTrue(descriptionFlag, "Cart description doesn't match");
        Assert.assertTrue(priceFlag, "Cart price doesn't match");
        Reporter.log("Comparing Product details with the cart Matches", true);

    }

    public void changeResolution() {
        Dimension windowSize = driver.manage().window().getSize();
        Reporter.log("Screen Resolution : " + windowSize.toString(), true);
        driver.manage().window().setSize(new Dimension(750, 1334));
        Reporter.log("Screen resolution changed to " + driver.manage().window().getSize().toString(), true);
    }

    public void performSwipe() throws InterruptedException {
        sleepSome(5000);
        Reporter.log("Performing swipe", true);
        Dimension size = driver.manage().window().getSize();
        Reporter.log("size :" + size, true);
        int startPoint = (int) (size.width * 0.99);
        int endPoint = (int) (size.width * 0.15);
        int ScreenPlace = (int) (size.height * 0.40);

        TouchAction ts = new TouchAction(driver);
        for (int i = 0; i <= 3; i++) {
            ts.press(PointOption.point(startPoint, ScreenPlace))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                    .moveTo(PointOption.point(endPoint, ScreenPlace)).release().perform();
            sleepSome(2000);
        }

    }
}
