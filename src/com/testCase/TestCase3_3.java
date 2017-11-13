package com.testCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestCase3_3 {
	
//	Precondition: GPS on, Wifi on
//	Test case: Login with Facebook account, accept location permission, check account type, check connection with Facebook


	AppiumDriver<MobileElement> driver;
	static Boolean passed = false;
	
	public void setUp() throws MalformedURLException {
		System.out.println("Running...");
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
	
	
	public void signInWithFacebook() {	
		driver.findElementById("ca.used:id/login_facebook_button").click();
		
//		Check Facebook login:


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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
//		Check if go to home page:
		if (driver.findElementById("ca.used:id/item_feed_item_card_view") != null) {
			System.out.println("Ads successfully loaded.");
		} else {
			System.out.println("Failed to locate.");
		}
	}
	
	
	public void checkAccount() {
// Navigate to the account page:
		driver.findElementByXPath("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='3']").click();
		
// Check the account status:
		if (driver.findElementById("ca.used:id/profile_toolbar_settings_iv") !=  null) {
			System.out.println("Logged in with account");
		} else if (driver.findElementById("ca.used:id/anonymous_profile_anonymous_sign_in_button") !=  null) {
			System.out.println("Logged in anonymously");
		} else {
			System.out.println("Error in account page");
		}
		
	}
	
	
//	Check Facebook connectivity:
	public void checkFacebookConnectivity() {
		
		
		
	}
	
	
	
	public void logout() {
		driver.findElementByXPath("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='3']").click();
		if (driver.findElementById("ca.used:id/fresh_loading_button_text_tv").getText() == "Disconnect Facebook account") {
			
			driver.findElementById("ca.used:id/settings_account_connect_facebook_button").click();
			
		}
	}
	
	
	
	public void tearDown() {
		driver.quit();
	}
	

	
	public static void main(String[] args) throws MalformedURLException {
		TestCase3_3 obj = new TestCase3_3();
		obj.setUp();
		obj.signInWithFacebook();
		obj.locationPermission();
		obj.locationPermissionAccept();
		obj.checkAccount();
//		obj.checkFacebookConnectivity();
//		obj.logout();
		obj.tearDown();
		if(passed) {
			System.out.println("Passed.");
		} else {
			System.out.println("Failed.");
		}
	}

}
