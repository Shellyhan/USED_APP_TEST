package com.testCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestCase2_2 {
	
//	For demonstration purpose, printing result as test result output.
	
//	Precondition: GPS on, Wifi on
//	Test case: Login with wrong email + password, check if login failed
//	(Note: Appium does not support Toast Message detection, unable to find the error message pop up, thus check if still stay in login page after button clicked.)
	
	
	AppiumDriver<MobileElement> driver;
	static Boolean passed = false;
	
	public void setUp() throws MalformedURLException {
		System.out.println("Setup...");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", "Android");
		cap.setCapability("deviceName", "test device");
		cap.setCapability("appPackage", "ca.used");
		cap.setCapability("appActivity", "ca.used.login.LoginActivity");
		cap.setCapability("clearSystemFiles", true);
		
		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Testing started...");
	}
	
	
	public void hideKeyBoard() {
	    try{driver.hideKeyboard();}
	    catch(Exception e){}
	}
	
	
	public void signInWithEmailWrongCombination() {	
		driver.findElementById("ca.used:id/login_email_et").click();
		
//		Combinations to test the correct error message:
//		1. empty email (tested)
//		2. invalid email
//		3. empty password
//		4. invalid password
		
		driver.getKeyboard().sendKeys("Wrong email");
		hideKeyBoard();
		driver.findElementById("ca.used:id/login_password_et").click();
		driver.getKeyboard().sendKeys("123456");
		hideKeyBoard();
		driver.findElementById("ca.used:id/login_sign_in_button").click();
	
//		Check login failure:
		try {
			driver.findElementById("com.android.packageinstaller:id/permission_message");
		} catch (Exception e) {
			System.out.println("Login not successful.");
//			System.out.println(e);
			passed = true;
		}
	}

	
	public void tearDown() {
		driver.quit();
	}
	
	
	public static void main(String[] args) throws MalformedURLException {
		TestCase2_2 obj = new TestCase2_2();
		obj.setUp();
		obj.signInWithEmailWrongCombination();
		obj.tearDown();
		if(passed) {
			System.out.println("Passed.");
		} else {
			System.out.println("Failed.");
		}
	}
}
