package com.testCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestCase2_1 {
	
//	For demonstration purpose, printing result as test result output.
//	Precondition: GPS on, Wifi on
//	Test case: Login with correct email + password, accept location permission, check account type
//	(Note: Appium does not support Toast Message detection, unable to find potential the error message pop up, thus check if continue to next view after button clicked.)
	
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
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Testing started...");
	}
	
	
	public void hideKeyBoard() {
	    try{driver.hideKeyboard();}
	    catch(Exception e){}
	}
	
	
	public void signInWithEmail() {	
		driver.findElementById("ca.used:id/login_email_et").click();
		driver.getKeyboard().sendKeys("shellshell456@gmail.com");
		hideKeyBoard();
		driver.findElementById("ca.used:id/login_password_et").click();
		driver.getKeyboard().sendKeys("123456");
		hideKeyBoard();
		driver.findElementById("ca.used:id/login_sign_in_button").click();
		
//		Check login:
// 		Note: Appium (UIAuotmation) does not support Toast Message detection, unable to find the Toast Message message of test input field.
		if (driver.findElementsById("com.android.packageinstaller:id/permission_message") == null) {
			System.out.println("Login not successful.");
			System.exit(0);	
		}

	}
	
	public void locationPermission() {
				
		if (driver.findElementById("com.android.packageinstaller:id/permission_message") != null) {
			System.out.println("Location permission popped up.");
		} else {
			System.out.println("Location permission failed to pop up.");
			System.exit(0);
		}
	}
	
	
	public void locationPermissionAccept() {
		driver.findElementById("com.android.packageinstaller:id/permission_allow_button").click();
		
//		Check if go to home page:
		if (driver.findElementById("ca.used:id/item_feed_item_card_view") != null) {
			System.out.println("Ads successfully loaded.");
		} else {
			System.out.println("Failed to locate.");
		}
	}
	
	
	public void checkAccount() {
		
//		Navigate to the account page:
		driver.findElementByXPath("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='3']").click();
		
//		Check the account status:
		if (driver.findElementById("ca.used:id/profile_toolbar_settings_iv") !=  null) {
			System.out.println("Logged in with account");
			passed = true;
		} else if (driver.findElementById("ca.used:id/anonymous_profile_anonymous_sign_in_button") !=  null) {
			System.out.println("Logged in anonymously");
		} else {
			System.out.println("Error in account page");
		}
	}
	
	
	public void tearDown() {
		driver.quit();
	}
	
	
	public static void main(String[] args) throws MalformedURLException {
		TestCase2_1 obj = new TestCase2_1();
		obj.setUp();
		obj.signInWithEmail();
		obj.locationPermission();
		obj.locationPermissionAccept();
		obj.checkAccount();
		obj.tearDown();
		if(passed) {
			System.out.println("Passed.");
		} else {
			System.out.println("Failed.");
		}
	}
}
