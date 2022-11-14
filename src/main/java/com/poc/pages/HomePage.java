package com.poc.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(id = "gh-ac")
	private WebElement searchFld;
	@FindBy(id = "gh-btn")
	private WebElement searchBtn;
	@FindBy(xpath = "//h1[@class= 'srp-controls__count-heading']")
	private WebElement searchConfirmMsg;

	@FindBy(xpath = "//h1[@class= 'srp-controls__count-heading']//span")
	private WebElement searchResultsCnt;

	@FindBy(xpath = "//li[contains(@class ,'s-item s-item__pl-on-bottom s-item--watch-at-corner')]")
	private List<WebElement> searchResultsPerPage;

	@FindBy(xpath = "(//li[contains(@class ,'s-item s-item__pl-on-bottom s-item--watch-at-corner')])//span[@class = 's-item__price']")
	private List<WebElement> priceFld;

	@FindBy(xpath = "(//li[contains(@class ,'s-item s-item__pl-on-bottom s-item--watch-at-corner')])//span[@class = 's-item__dynamic s-item__purchaseOptionsWithIcon']")
	private List<WebElement> buyItNowORBestOfferFld;

	@FindBy(xpath = "(//li[contains(@class ,'s-item s-item__pl-on-bottom s-item--watch-at-corner')])//span[@class = 's-item__shipping s-item__logisticsCost']")
	private List<WebElement> postageFld;

	@FindBy(xpath = "(//li[contains(@class ,'s-item s-item__pl-on-bottom s-item--watch-at-corner')])//span[@class = 's-item__bids s-item__bidCount']")
	private List<WebElement> buyItNowORBestOffer;

	@FindBy(xpath = "//button[@type='previous']")
	private WebElement previousIcon;

	@FindBy(xpath = "//a[@type='next']")
	private WebElement nextIcon;

	@FindBy(xpath = "//ol[@class='pagination__items']//li")
	private List<WebElement> paginationOptions;

	@FindBy(xpath = "//button[@class='fake-menu-button__button btn btn--secondary']")
	private List<WebElement> bestMatch;

	@FindBy(xpath = "//span[text()='Lowest price']")
	private WebElement lowestPriceOptn;

	@FindBy(xpath = "//span[text()='Highest price']")
	private WebElement highestPriceOptn;

	/*
	 * @author:Navakanth
	 * Description: To search a product based on input
	 */
	
	public void searchProduct(String product) {
		enterIn(searchFld, product);
		clickOn(searchBtn);
	}

	/*
	 * @author:Navakanth
	 * Description: To get number of results after search
	 */
	public Integer matchingResults() {

		return Integer.valueOf(searchResultsCnt.getText());

	}

	/*
	 * @author:Navakanth
	 * Description: To sort results from Lowest to Highest
	 */
	
	public List<Float> sortByLowest() {
		waitForEle(bestMatch.get(0));
		bestMatch.get(0).click();
		waitForEle(lowestPriceOptn);
		lowestPriceOptn.click();
		return getPrices();
	}

	/*
	 * @author:Navakanth
	 * Description: To sort results from Highest to Lowest
	 */
	public List<Float> sortByHighest() {
		waitForEle(bestMatch.get(0));
		bestMatch.get(0).click();
		waitForEle(highestPriceOptn);
		highestPriceOptn.click();
		return getPrices();
	}

	/*
	 * @author:Navakanth
	 * Description: To store the prices of results to a List
	 */
	public List<Float> getPrices() {

		List<Float> prices = new ArrayList<Float>();

		int size = searchResultsPerPage.size();
		for (int i = 0; i < size; i++) {
			String price = "(//li[contains(@class ,'s-item s-item__pl-on-bottom s-item--watch-at-corner')])[" + (i + 1)
					+ "]//span[@class = 's-item__price']";

			waitForEle(driver.findElement(By.xpath(price)));

			List<WebElement> priceList = driver.findElements(By.xpath(price));
			if (priceList.size() > 0) {
				String val = (priceList.get(0).getText()).replace("£", "");
				Float f = Float.valueOf(val);
				Assert.assertTrue(f > 0);
				prices.add(f);

			} else {
				log("No Price Details Found ");
			}
		}

		return prices;

	}
	/*
	 * @author:Navakanth
	 * Description: To verify / compare whether the prices are sorted ascending / descending
	 */
	public void comparePrices(List<Float> list) {
		for (int i = 0; i < (list.size() - 1); i++) {
			if (Float.compare((list.get(i)), (list.get(i + 1))) == 0) {

				log("For " + i + "---" + (list.get(i)) + " " + (list.get(i + 1)) + " both are equal");
			} else if (Float.compare((list.get(i)), (list.get(i + 1))) < 0) {

				log("For " + i + "---" + (list.get(i)) + " " + (list.get(i + 1)) + " is second is greater");
			} else {

				log("For " + i + "---" + (list.get(i)) + " " + (list.get(i + 1)) + " is first is greater");
			}
		}
	}
	/*
	 * @author:Navakanth
	 * Description: To log or get Product details - Title, price, postage details, Buy it now and bid details
	 */

	public void getProductDetails() {
		System.out.println(searchResultsCnt.getText());
		int count = 0;
		int size = searchResultsPerPage.size();
		for (int i = 0; i < size; i++) {
			String title = "(//li[contains(@class ,'s-item s-item__pl-on-bottom s-item--watch-at-corner')])[" + (i + 1)
					+ "]//span[@role = 'heading']";

			String price = "(//li[contains(@class ,'s-item s-item__pl-on-bottom s-item--watch-at-corner')])[" + (i + 1)
					+ "]//span[@class = 's-item__price']";

			String buyItNow = "(//li[contains(@class ,'s-item s-item__pl-on-bottom s-item--watch-at-corner')])["
					+ (i + 1) + "]//span[@class = 's-item__dynamic s-item__purchaseOptionsWithIcon']";

			String postage = "(//li[contains(@class ,'s-item s-item__pl-on-bottom s-item--watch-at-corner')])["
					+ (i + 1) + "]//span[@class = 's-item__shipping s-item__logisticsCost']";

			String bids = "(//li[contains(@class ,'s-item s-item__pl-on-bottom s-item--watch-at-corner')])[" + (i + 1)
					+ "]//span[@class = 's-item__bids s-item__bidCount']";

			waitForEle(driver.findElement(By.xpath(price)));

			List<WebElement> titleList = driver.findElements(By.xpath(title));
			List<WebElement> priceList = driver.findElements(By.xpath(price));
			List<WebElement> buyItNowList = driver.findElements(By.xpath(buyItNow));
			List<WebElement> postageList = driver.findElements(By.xpath(postage));
			List<WebElement> bidsList = driver.findElements(By.xpath(bids));

			if (titleList.size() > 0) {
				log(titleList.get(0).getText());
			} else {
				log("*** No Title Found ***");
			}

			if (priceList.size() > 0) {
				log(priceList.get(0).getText());
			} else {
				log("*** No Price Found ***");
			}

			if (buyItNowList.size() > 0) {
				log(buyItNowList.get(0).getText());
			} else {
				log("*** No Buy details Found ***");
			}

			if (postageList.size() > 0) {
				log(postageList.get(0).getText());
			} else {
				log("*** No Postage details Found ***");
			}

			if (bidsList.size() > 0) {
				log(bidsList.get(0).getText());
			} else {
				log("*** No bids Found ***");
			}

			count = count + 1;
		}
		log("Items in the page : " + count);
	}

	/*
	 * @author:Navakanth
	 * Description: To click on next icon in pagination
	 */
	public void clickNextIcon() {
		nextIcon.click();
	}
	
	/*
	 * @author:Navakanth
	 * Description: To click on previous icon in pagination
	 */

	public void clickPreviousIcon() {
		previousIcon.click();
	}

	
	/*
	 * @author:Navakanth
	 * Description: To navigate through pagination based on page number
	 */
	
	public void navigatePages(int i) {

		paginationOptions.get(i - 1).click();
		log("Clicked on page number" + i);
	}
	/*
	 * @author:Navakanth
	 * Description: To pagination is displayed or not
	 */
	public void verifyPaginationDisplay() {
		if (paginationOptions.size() > 0) {
			log("Pagination is displayed");
		}
	}

	/*
	 * @author:Navakanth
	 * Description: To wait for Search Button before any action
	 */
	public void waitForSearchFld() {
		waitForEle(searchBtn);
	}

}
