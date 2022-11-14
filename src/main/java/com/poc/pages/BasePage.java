package com.poc.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.poc.base.BaseTest;

public class BasePage extends BaseTest{
	
	/*
	 * @author:Navakanth
	 * Description: To initialize Page Factory
	 */
	public BasePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	

}
