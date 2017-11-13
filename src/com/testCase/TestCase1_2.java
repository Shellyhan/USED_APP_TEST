package com.testCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestCase1_2 {

// For demonstration purpose, printing result as test result output.
	
//	Precondition: GPS on, Wifi on
//	Test case: Login without account, deny location permission, check options to set location
	
	
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
	
	
	public void locationPermissionDeny() {
//		Deny request:
		driver.findElementById("com.android.packageinstaller:id/permission_deny_button").click();
		
//		Check if go to location selection page:
		if (driver.findElementById("ca.used:id/select_region_search_et") != null) {
			System.out.println("Loacation selection enabled.");
			passed = true;
		} else {
			System.out.println("Location selection not enabled.");
		}
	}
	
	
	public void tearDown() {
		driver.quit();
	}

	
	public static void main(String[] args) throws MalformedURLException {
		TestCase1_2 obj = new TestCase1_2();
		obj.setUp();
		obj.signInSkip();
		obj.locationPermission();
		obj.locationPermissionDeny();
		obj.tearDown();
		if(passed) {
			System.out.println("Passed.");
		} else {
			System.out.println("Failed.");
		}
	}
}
