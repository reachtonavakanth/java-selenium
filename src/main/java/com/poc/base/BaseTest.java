package com.poc.base;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.poc.reports.MyExtentReport;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.poc.pages.HomePage;
import com.poc.utils.Utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.time.Duration;

public class BaseTest extends Utils {

    public static WebDriver driver = null;
    public String propFile = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "data.properties";
    public String[] config = null;
    public String[] sheet = {"testdata", "config"};
    public String dataFile = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "TestData.xlsx";

    /*
     * @author:Navakanth Description: To launch browser & Maximize browser window
     */
    @BeforeMethod
    public void driverInit() {
        config = toReadExcelData(dataFile, sheet[1], "C1");
        if (config[2].equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (config[2].equalsIgnoreCase("msedge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (config[2].equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
        } else {
            log().fatal("Please enter correct browser in config sheet");
        }
        addLog(config[2] + " is launched");
        driver.manage().window().maximize();
        addLog("Browser Window is Maximized");
    }

    @BeforeTest
    public void before() {
        String strFile = "logs" + File.separator + getDateTime();
        File logFile = new File(strFile);
        if (!logFile.exists()) {
            logFile.mkdirs();
        }
        ThreadContext.put("ROUTINGKEY", strFile);
    }

    public WebDriver getDriver() {
        return driver;
    }

    /*
     * @author:Navakanth Description: To navigate to a specific url
     */
    public HomePage navigateTo(String url) {
        driver.get(url);
        addLog("Navigated to : " + url);
        return new HomePage(driver);
    }

    /*
     * @author:Navakanth Description: To get page title
     */
    public String getPageTitle() {
        addLog("Page title is : " + driver.getTitle());
        return driver.getTitle();
    }

    /*
     * @author:Navakanth Description: To click on a specific web element
     */
    public void clickOn(WebElement ele) {
        ele.click();
        addLog("Clicked on " + ele);
    }

    /*
     * @author:Navakanth Description: To input text in a web element
     */
    public void enterIn(WebElement ele, String text) {
        ele.clear();
        ele.sendKeys(text);
        addLog("Entered " + text + " in " + ele);
    }

    /*
     * @author:Navakanth Description: To scroll web page based on pixel
     */
    public void scrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,80)");
    }

    /*
     * @author:Navakanth Description: To scroll web page to a specific element
     */
    public void scrollToELe(WebElement ele) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true)", ele);
    }

    /*
     * @author:Navakanth Description: To wait for a specific element
     */

    public void waitForEle(WebElement ele) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(ele));
       // addLogs("Waiting for Ele" + ele);

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public void addLog(String msg) {
        log().info(msg);
    }

    public void addLogs(String msg) {
        MyExtentReport.getTest().log(Status.INFO, msg);
        log().info(msg);
    }
}
