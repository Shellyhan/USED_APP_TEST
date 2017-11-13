# USED_APP_TEST
(Test documentation in email attachment)

### Installation and setup
1. Install Java and set JAVA_HOME
2. Install Eclipse
3. Install Android Studio and set ANDROID_HOME
4. Install Node: ```brew install node``` (assuming Homebrew and Xcode installed)
5. Check Node and NPM: ```node -v``` and ```npm -v```
6. Install Appium: ```npm install -g appium```
7. Install Appium doctor to check setup: ```npm install -g appium-doctor```
8. Java-client and Selenium standalone are included in this project library


### Connect Android device:
1. plugin USB
2. enable developer mode on device
3. update android stuido libraries
4. look for device on terminal: ```adb devices```
5. test device connection: ```adb shell``` then ```logcat | grep "ca.used"```


### Start Testing:
1. Setup Appium server: ```appium``` and use default setup is sufficient
2. run test cases from src/com.testcase as Java application


### References:
1. http://blog.teamtreehouse.com/install-node-js-npm-mac
2. https://github.com/freeautomationlearning/Appium
3. https://developer.android.com/studio/command-line/adb.html
4. http://appium.io/slate/en/tutorial/android.html?ruby#native-android-automation
