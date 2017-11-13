package com.testCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.validator.Var;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestCase1_1 {
	
// Make sure you only have one device in adb
// AppiumDriver does not support currentActivity(), to use the method, apk needed.
// As a result, for page recognition, will use specific element detection (However, this should be avoid in real testing).
// For demonstration purpose, printing result as test result output.
	
//	Precondition: GPS on, Wifi on
//	( Wifi/Data and GPS cannot be controlled by appium for real device connection, thus not able to automated for now )
//	Test case: Login without account, accept location permission, check account type
	
	
	AppiumDriver<MobileElement> driver;
	static Boolean passed = false;
	
	public void setUp() throws MalformedURLException {
		System.out.println("Setup...");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", "Android");
		cap.setCapability("deviceName", "test device");
		cap.setCapability("appPackage", "ca.used");
		cap.setCapability("appActivity", "ca.used.login.LoginActivity");
//		For testing with apk available:
//		cap.setCapability("app", path+"//ca.used.apk");
//		cap.setCapability("gpsEnabled", false);
		cap.setCapability("clearSystemFiles", true);
		
		try {
			driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Testing started...");
	}
	
	
	
	public void signInSkip() {	
		
//		Click the Skip button to sign in:
		driver.findElementById("ca.used:id/login_skip_button").click();
		
	}
	
	
	
	public void locationPermission() {
//		Look for the location permission pop up:
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
	
	
	public void checkAccountType() {
		
//		Navigate to the account page:
		driver.findElementByXPath("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='3']").click();
		
//		Check the account status:
		if (driver.findElementById("ca.used:id/anonymous_profile_anonymous_sign_in_button") !=  null) {
			System.out.println("Logged in anonymously");
			passed = true;
		} else {
			System.out.println("Error in account page");
		}
	}
	
	
	public void tearDown() {
		driver.quit();
	}

	
	public static void main(String[] args) throws MalformedURLException {
		TestCase1_1 obj = new TestCase1_1();
		obj.setUp();
		obj.signInSkip();
		obj.locationPermission();
		obj.locationPermissionAccept();
		obj.checkAccountType();
		obj.tearDown();
		if(passed) {
			System.out.println("Passed.");
		} else {
			System.out.println("Failed.");
		}
	}
}
