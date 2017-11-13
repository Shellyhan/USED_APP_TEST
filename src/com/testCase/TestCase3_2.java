package com.testCase;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class TestCase3_2 {
	
//	Precondition: GPS on, Wifi on
//	Test case: Login with Facebook account, accept location permission, check connection status with Facebook, disconnect Facebook, logout, login with email, check if Facebook is disconnected


	AppiumDriver<MobileElement> driver;
	static Boolean passed = false;
	static Boolean emailLogin = false;
	
	public void setUp() throws MalformedURLException {
		System.out.println("Running...");
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("platformName", "Android");
		cap.setCapability("deviceName", "test device");
		cap.setCapability("appPackage", "ca.used");
		cap.setCapability("appActivity", "ca.used.login.LoginActivity");
		
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
	
	
	public void signInWithFacebook() {	
		driver.findElementById("ca.used:id/login_facebook_button").click();
		
//		Check Facebook login:
//		(Note: Cannot successfully disconnect Facebok account and registration email. Originally, it should be directed to the Facebook account setup page, instead of going to main page directly )
		if (driver.findElementsById("com.android.packageinstaller:id/permission_message") == null) {
			System.out.println("Login not successful.");
			System.out.println("Failed");
			System.exit(0);	
		}
	}
	
	
	public void signInWithEmail() {	
		driver.findElementById("ca.used:id/login_email_et").click();
		driver.getKeyboard().sendKeys("shellshell456@gmail.com");
		hideKeyBoard();
		driver.findElementById("ca.used:id/login_password_et").click();
		driver.getKeyboard().sendKeys("123456");
		hideKeyBoard();
		driver.findElementById("ca.used:id/login_sign_in_button").click();
		
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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
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
		} else if (driver.findElementById("ca.used:id/anonymous_profile_anonymous_sign_in_button") !=  null) {
			System.out.println("Logged in anonymously");
		} else {
			System.out.println("Error in account page");
			System.exit(0);
		}
		
	}
	
	
	
	public void checkFacebookConnectivity(boolean expectedConnectionStatus) {
		
//		Direct to the Account Setting view:
		driver.findElementById("ca.used:id/profile_toolbar_settings_iv").click();
		driver.findElementByXPath("//*[@class='android.support.v7.app.ActionBar$Tab' and @index='1']").click();
		
//		Check the connectivity of Facebook:
		String text = driver.findElementById("ca.used:id/fresh_loading_button_text_tv").getText();

		if (text.equalsIgnoreCase("Disconnect Facebook account") && expectedConnectionStatus) { // Facebook login and connected
			System.out.println("Connected with Facebook");
		} else if (text.equalsIgnoreCase("Connect Facebook account") && !expectedConnectionStatus) { // Email login and disconnected
			System.out.println("Not connected with Facebook");
			passed = true;
		} else {
			System.out.println("Error in Facebook connection status");
			System.exit(0);
		}
	}
	
	public void disconnectFacebook() {
		
		driver.findElementById("ca.used:id/fresh_loading_button_text_tv").click();
		
//		Check the connectivity of Facebook again:
		String text = driver.findElementById("ca.used:id/fresh_loading_button_text_tv").getText();
		if (text.equalsIgnoreCase("Connect Facebook account")) {
			System.out.println("Disconnected.");
		} else {
			System.out.println("Fail to disconnect Facebook.");
			System.exit(0);
		}
	}
	
	
	
	public void logout() {			
		driver.findElementById("ca.used:id/settings_account_log_out_tv").click();
	}
	
	
	
	public void tearDown() {
		driver.quit();
	}
	

	
	public static void main(String[] args) throws MalformedURLException {
		TestCase3_2 obj = new TestCase3_2();
		obj.setUp();
		obj.signInWithFacebook();
		obj.locationPermission();
		obj.locationPermissionAccept();
		obj.checkAccount();
		obj.checkFacebookConnectivity(true);
		obj.disconnectFacebook();
		obj.logout();
		obj.signInWithEmail();
//		obj.locationPermission();
//		obj.locationPermissionAccept();
		obj.checkAccount();
		obj.checkFacebookConnectivity(false);
		obj.tearDown();
		if(passed) {
			System.out.println("Passed.");
		} else {
			System.out.println("Failed.");
		}
	}

}
