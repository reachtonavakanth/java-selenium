package com.poc.tests;

import com.poc.base.BaseTest;
import com.poc.pages.HomePage;
import com.poc.utils.Utils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class VerifySearchAndNavigationTests extends BaseTest {


    public String[] sc_01 = null;
    Utils util = new Utils();
    HomePage homePage = null;
    List<Float> pricesLowToHigh = null;
    List<Float> pricesHighToLow = null;

    @BeforeTest
    public void getData() {
        sc_01 = util.toReadExcelData(dataFile, sheet[0], "Sc_01");
    }

    @Test
    public void verifySearch() {
        homePage = navigateTo(config[1]);
        homePage.waitForSearchFld();
        String actual = homePage.getPageTitle();
        String expected = config[3];

        Assert.assertTrue(actual.equalsIgnoreCase(expected));
        homePage.searchProduct(sc_01[1]);

        Integer expected_matches = util.getNum(sc_01[2]);
        Integer actual_matches = homePage.matchingResults();
        Assert.assertTrue(actual_matches > expected_matches);

        homePage.getProductDetails();
        pricesLowToHigh = homePage.sortByLowest();
        homePage.comparePrices(pricesLowToHigh);
        pricesHighToLow = homePage.sortByHighest();
        homePage.comparePrices(pricesHighToLow);
    }

    @Test
    public void verifyPagination() {
        {
            homePage = navigateTo(config[1]);
            homePage.waitForSearchFld();
            String actual = homePage.getPageTitle();
            String expected = config[3];

            Assert.assertTrue(actual.equalsIgnoreCase(expected));
            homePage.searchProduct(sc_01[1]);

            Integer expected_matches = util.getNum(sc_01[2]);
            Integer actual_matches = homePage.matchingResults();
            Assert.assertTrue(actual_matches > expected_matches);
            homePage.verifyPaginationDisplay();
            homePage.navigatePages(2);
            homePage.navigatePages(3);
            homePage.navigatePages(4);
        }
    }
    @Test
    public void verifyTitle() {
        {
            homePage = navigateTo(config[1]);
            homePage.waitForSearchFld();
            String actual = homePage.getPageTitle()+"test";
            String expected = config[3];

            Assert.assertTrue(actual.equalsIgnoreCase(expected),"Title not matched");
        }
    }



}
