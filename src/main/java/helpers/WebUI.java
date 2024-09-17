package helpers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v128.network.Network;
import org.openqa.selenium.devtools.v128.network.model.Headers;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import constants.GlobalConstants;
import driver.DriverManager;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

//import com.google.common.util.concurrent.Uninterruptibles;

public class WebUI {
	 /**
     * The SoftAssert object is created
     */
    private static SoftAssert softAssert = new SoftAssert();

    /**
     * Stop the Soft Assert of TestNG
     */
    public static void stopSoftAssertAll() {
        softAssert.assertAll();
    }


    public static void smartWait() {
        if (GlobalConstants.ACTIVE_PAGE_LOADED.trim().toLowerCase().equals("true")) {
            waitForPageLoaded();
        }
        if (GlobalConstants.SLEEP_TIME > 4) {
            sleep(GlobalConstants.SLEEP_TIME);
        }
    }

    public static String getPathDownloadDirectory() {
        String path = "";
        String machine_name = System.getProperty("user.home");
        path = machine_name + File.separator + "Downloads";
        return path;
    }

//    @Step("Get to URL {0} with authentication")
    public static void getToUrlAuthentication(String url, String username, String password) {
        // Get the devtools from the running drivers and create a session
        DevTools devTools = ((HasDevTools) DriverManager.getDriver()).getDevTools();
        devTools.createSession();

        // Enable the Network domain of devtools
        devTools.send(Network.enable(Optional.of(100000), Optional.of(100000), Optional.of(100000)));
        String auth = username + ":" + password;

        // Encoding the username and password using Base64 (java.util)
        String encodeToString = Base64.getEncoder().encodeToString(auth.getBytes());

        // Pass the network header -> Authorization : Basic <encoded String>
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "Basic " + encodeToString);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

   // Load the application url
        openWebsite(url);
//        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(3));
    }

//    @Step("Verify HTML5 message of element {0} required field")
    public static boolean verifyHTML5RequiredField(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        boolean verifyRequired = (Boolean) js.executeScript("return arguments[0].required;", getWebElement(by));
        return verifyRequired;
    }
    
//    @Step("Verify HTML5 message of element {0} valid")
    public static boolean verifyHTML5ValidValueField(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        boolean verifyValid = (Boolean) js.executeScript("return arguments[0].validity.valid;", getWebElement(by));
        return verifyValid;
    }
    
    public static boolean verifyHTML5ValidValueField(WebElement elm) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        boolean verifyValid = (Boolean) js.executeScript("return arguments[0].validity.valid;", elm);
        return verifyValid;
    }

//    @Step("Get HTML5 message of element {0}")
    public static String getHTML5MessageField(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        String message = (String) js.executeScript("return arguments[0].validationMessage;", getWebElement(by));
        return message;
    }

    public static void setWindowSize(int widthPixel, int heightPixel) {
        DriverManager.getDriver().manage().window().setSize(new Dimension(widthPixel, heightPixel));
    }

    public static void setWindowPosition(int X, int Y) {
        DriverManager.getDriver().manage().window().setPosition(new Point(X, Y));
    }

    public static void maximizeWindow() {
        DriverManager.getDriver().manage().window().maximize();
    }

    public static void minimizeWindow() {
        DriverManager.getDriver().manage().window().minimize();
    }

    public static void screenshotElement(By by, String elementName) {
        File scrFile = getWebElement(by).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("./" + elementName + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JavascriptExecutor getJsExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        return js;
    }

    public static WebElement getWebElement(By by) {
        return DriverManager.getDriver().findElement(by);
    }
    
    public static void sleep(double second) {
        try {
            Thread.sleep((long) (second * 1000));
        } catch (InterruptedException e) {
        }
    }

//    @Step("Set text on Alert {0}")
    public static void setTextAlert(String text) {
        sleep(GlobalConstants.SHORT_TIME);
        DriverManager.getDriver().switchTo().alert().sendKeys(text);
    }

//    @Step("Check element visible {0}")
    public static boolean isElementVisible(By by, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    @Step("Verify element visible {0}")
    public static boolean verifyElementVisible(By by) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(GlobalConstants.SHORT_TIME));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            Assert.fail("❌ The element is NOT visible. " + by);
            return false;
        }
    }
    
//    @Step("Verify element visible {0} with timeout {1} second")
    public static boolean verifyElementVisible(By by, int timeout) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            Assert.fail("❌ The element is NOT visible. " + by);
            return false;
        }
    }

//    @Step("Verify element visible {0}")
    public static boolean verifyElementVisible(By by, String message) {
        smartWait();
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(GlobalConstants.SHORT_TIME));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            if (message.isEmpty()) {
                Assert.fail("The element is NOT visible. " + by);
            } else {
                Assert.fail(message + by);
            }
            return false;
        }
    }

//    @Step("Verify element visible {0} with timeout {1} second")
    public static boolean verifyElementVisible(By by, int timeout, String message) {
        smartWait();
//        LogUtils.info("Verify element visible " + by);
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            if (message.isEmpty()) {
//                LogUtils.error("The element is not visible. " + by);
                Assert.fail("The element is NOT visible. " + by);
            } else {
//                LogUtils.error(message + by);
                Assert.fail(message + by);
            }
            return false;
        }
    }

//    /**
//     * Scroll an element into the visible area of the browser window. (at TOP)
//     *
//     * @param by Represent a web element as the By object
//     */
//    @Step("Scroll to element {0}")
    public static void scrollToElementAtTop(By by) {
        smartWait();

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", getWebElement(by));
//        LogUtils.info("Scroll to element " + by);
    }

//    /**
//     * Scroll an element into the visible area of the browser window. (at BOTTOM)
//     *
//     * @param by Represent a web element as the By object
//     */
//    @Step("Scroll to element {0}")
    public static void scrollToElementAtBottom(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
//        LogUtils.info("Scroll to element " + by);
    }

//    /**
//     * Scroll an element into the visible area of the browser window. (at TOP)
//     *
//     * @param webElement Represent a web element as the By object
//     */
//    @Step("Scroll to element {0}")
    public static void scrollToElementAtTop(WebElement webElement) {
        smartWait();

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", webElement);
//        LogUtils.info("Scroll to element " + webElement);
    }

//    /**
//     * Scroll an element into the visible area of the browser window. (at BOTTOM)
//     *
//     * @param webElement Represent a web element as the By object
//     */
//    @Step("Scroll to element {0}")
    public static void scrollToElementAtBottom(WebElement webElement) {
        smartWait();

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", webElement);
    }

//    @Step("Reload page")
    public static void reloadPage() {
        smartWait();

        DriverManager.getDriver().navigate().refresh();
        waitForPageLoaded();
//        LogUtils.info("Reloaded page " + DriverManager.getDriver().getCurrentUrl());
    }

//    @Step("Open website with URL {0}")
    public static void openWebsite(String URL) {
        sleep(GlobalConstants.SLEEP_TIME);

        DriverManager.getDriver().get(URL);
        waitForPageLoaded();
//        if (ExtentTestManager.getExtentTest() != null) {
//            ExtentReportManager.pass("Open website with URL: " + URL);
//        }
//        AllureManager.saveTextLog("Open website with URL: " + URL);
//
//        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

//    @Step("Set text on text box")
    public static void setText(By by, String value) {
        smartWait();
        waitForElementVisible(by).sendKeys(value);
//        if (ExtentTestManager.getExtentTest() != null) {
//            ExtentReportManager.pass("Set text " + value + " on " + by);
//        }
//        AllureManager.saveTextLog("Set text " + value + " on " + by);
//
//        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

//    @Step("Set text on text box and press key")
    public static void setText(By by, String value, Keys keys) {
        smartWait();
        waitForElementVisible(by).sendKeys(value, keys);
//        if (ExtentTestManager.getExtentTest() != null) {
//            ExtentReportManager.pass("Set text " + value + " on " + by + " and press key " + keys.name());
//        }
//        AllureManager.saveTextLog("Set text " + value + " on " + by + " and press key " + keys.name());
//
//        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

//    @Step("Clear text in text box")
    public static void clearText(By by) {
        smartWait();
        waitForElementVisible(by).clear();
//
//        if (ExtentTestManager.getExtentTest() != null) {
//            ExtentReportManager.pass("Clear text in textbox " + by);
//        }
//        AllureManager.saveTextLog("Clear text in textbox");
//        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

//    @Step("Click on the element {0}")
    public static void clickElement(By by) {
        smartWait();
        waitForElementClickable(by).click();
//        if (ExtentTestManager.getExtentTest() != null) {
//            ExtentReportManager.pass("Clicked on the element " + by);
//        }
//        AllureManager.saveTextLog("Clicked on the element " + by);
//
//        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

//    @Step("Click on the element {0} with timeout {1}s")
    public static void clickElement(By by, int timeout) {
        smartWait();
        waitForElementClickable(by, timeout).click();
//
//        if (ExtentTestManager.getExtentTest() != null) {
//            ExtentReportManager.pass("Clicked on the element " + by);
//        }
//        AllureManager.saveTextLog("Clicked on the element " + by);
//
//        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

//    @Step("Click on the element by Javascript {0}")
    public static void clickElementWithJs(By by) {
        smartWait();
        waitForElementPresent(by);
        //Scroll to element với Javascript Executor
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
        //Click with JS
        js.executeScript("arguments[0].click();", getWebElement(by));

//        if (ExtentTestManager.getExtentTest() != null) {
//            ExtentReportManager.pass("Click on element with JS: " + by);
//        }
//        AllureManager.saveTextLog("Click on element with JS: " + by);
//        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());
    }

//    @Step("Right click on element {0}")
    public static void rightClickElement(By by) {
        smartWait();
        Actions action = new Actions(DriverManager.getDriver());
        action.contextClick(waitForElementVisible(by)).build().perform();
//        if (ExtentTestManager.getExtentTest() != null) {
//            ExtentReportManager.pass("Right click on element " + by);
//        }
//        addScreenshotToReport(Thread.currentThread().getStackTrace()[1].getMethodName() + "_" + DateUtils.getCurrentDateTime());

    }

    public static WebElement waitForElementVisible(By by, int timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut - 10));

            boolean check = isElementVisible(by, 10);
            if (check) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } else {
                scrollToElementAtTop(by);
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            }
        } catch (Throwable error) {
            Assert.fail("❌ Timeout waiting for the element Visible. " + by.toString());
        }
        return null;
    }


    public static WebElement waitForElementVisible(By by) {
        waitForElementPresent(by);

        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(GlobalConstants.SHORT_TIME - 10));
            boolean check = isElementVisible(by, 10);
            if (check) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } else {
                scrollToElementAtBottom(by);
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            }
        } catch (Throwable error) {
            Assert.fail("❌ Timeout waiting for the element Visible. " + by.toString());
        }
        return null;
    }

    public static WebElement waitForElementClickable(By by, long timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            return wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable error) {
            Assert.fail("❌ Timeout waiting for the element ready to click. " + by.toString());
        }
        return null;
    }

    public static WebElement waitForElementClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(GlobalConstants.SHORT_TIME), Duration.ofMillis(500));
            return wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable error) {
            Assert.fail("❌ Timeout waiting for the element ready to click. " + by.toString());
        }
        return null;
    }

    public static WebElement waitForElementPresent(By by, long timeOut) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut), Duration.ofMillis(500));
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            Assert.fail("❌ Timeout waiting for the element to exist. " + by.toString());
        }

        return null;
    }


    public static WebElement waitForElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(GlobalConstants.SHORT_TIME));
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            Assert.fail("❌ Element not exist. (waitForElementPresent) " + by.toString());
        }
        return null;
    }

    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(GlobalConstants.NORMAL_TIMEOUT));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");

        //Get JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for page load. (" + GlobalConstants.NORMAL_TIMEOUT + "s)");
            }
        }
    }

    public static void waitForPageLoaded(int timeOut) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeOut));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");

        //Get JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for page load. (" + GlobalConstants.NORMAL_TIMEOUT + "s)");
            }
        }
    }
}
