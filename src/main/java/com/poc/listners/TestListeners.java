package com.poc.listners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.poc.base.BaseTest;
import com.poc.reports.MyExtentReport;
import com.poc.utils.Utils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class TestListeners implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        try {
            MyExtentReport.startTest(result.getName(), result.getMethod().getDescription())
                     .assignCategory("Sanity")
                    .assignAuthor("Automation Team");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        MyExtentReport.getTest().log(Status.PASS, "Test Passed !!!");    }

    @Override
    public void onTestFailure(ITestResult result) {
        // ITestListener.super.onTestFailure(result);
        if (result.getThrowable() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            result.getThrowable().printStackTrace(pw);
        }

        System.out.println(BaseTest.driver +"---> driver ");
        TakesScreenshot scrShot =((TakesScreenshot)BaseTest.driver);
        File file=scrShot.getScreenshotAs(OutputType.FILE);

        Screenshot fullScreen=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(500)).takeScreenshot(BaseTest.driver);


        byte[] encoded = null;
        byte[] encodedFull = null;

        String imagePath = "Screenshots" + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator
               + Utils.getDateTime() + File.separator + result.getName() + ".png";

        String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;

        String imagePathFull = "Screenshots" + File.separator + result.getTestClass().getRealClass().getSimpleName() + File.separator
               + Utils.getDateTime() + File.separator + result.getName() + "Full.png";

        String completeImagePathFull = System.getProperty("user.dir") + File.separator + imagePathFull;

        try {
            FileUtils.copyFile(file, new File(imagePath));
            ImageIO.write(fullScreen.getImage(),"PNG",new File(imagePathFull));
            Reporter.log("This is the sample screenshot");
           // Reporter.log("<a href='"+ completeImagePath + "'> <img src='"+ completeImagePath + "' height='400' width='400'/> </a>");
            //Reporter.log("<a href='"+ completeImagePathFull + "'> <img src='"+ completeImagePathFull + "' height='400' width='400'/> </a>");
        } catch (IOException e) {

            System.out.println("In Catch !!");
            e.printStackTrace();
        }


        try {
            encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
            encodedFull = Base64.encodeBase64(FileUtils.readFileToByteArray(new File(imagePathFull)));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
     //   MyExtentReport.getTest().fail("Test Failed",MediaEntityBuilder.createScreenCaptureFromPath(completeImagePath).build());
        MyExtentReport.getTest().fail("Test Failed, below is the screenshot ",
             MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.US_ASCII)).build());

        MyExtentReport.getTest().fail("Test Failed , below is the Full page screenshot ",
             MediaEntityBuilder.createScreenCaptureFromBase64String(new String(encodedFull, StandardCharsets.US_ASCII)).build());

        MyExtentReport.getTest().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        MyExtentReport.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //  ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onStart(ITestContext context) {
        // ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            MyExtentReport.getReporter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}