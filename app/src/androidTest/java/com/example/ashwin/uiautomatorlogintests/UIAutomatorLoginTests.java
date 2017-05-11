package com.example.ashwin.uiautomatorlogintests;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by ashwin on 9/5/17.
 */

@RunWith(AndroidJUnit4.class)
public class UIAutomatorLoginTests {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private String BASIC_SAMPLE_PACKAGE = "com.example.ashwin.uiautomatorlogintests";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice mDevice;

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void login_success() throws UiObjectNotFoundException, InterruptedException {

        String username = "admin";
        String password = "password";

        // Go to login page
        new UiObject(new UiSelector().className("android.widget.Button").index(0)).click();

        // Type username
        UiObject usernameEditText = new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
        usernameEditText.click();
        usernameEditText.setText(username);

        // Type password
        UiObject passwordEditText = new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
        passwordEditText.click();
        passwordEditText.setText(password);

        // Click login button
        new UiObject(new UiSelector().className("android.widget.Button").instance(0)).click();

        // Check welcome text
        UiObject2 welcomeUserTextView = mDevice.wait(Until.findObject(By.res(BASIC_SAMPLE_PACKAGE, "welcomeUserTextView")), 500 /* wait for 500 ms */);
        assertThat(welcomeUserTextView.getText().trim(), is(equalTo("Welcome " + username)));
    }

    @Test
    public void login_fail_wrong_username() throws UiObjectNotFoundException, InterruptedException {

        String username = "user";
        String password = "password";

        // Go to login page
        new UiObject(new UiSelector().className("android.widget.Button").index(0)).click();

        // Type username
        UiObject usernameEditText = new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
        usernameEditText.click();
        usernameEditText.setText(username);

        // Type password
        UiObject passwordEditText = new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
        passwordEditText.click();
        passwordEditText.setText(password);

        // Click login button
        new UiObject(new UiSelector().className("android.widget.Button").instance(0)).click();

        // Check welcome text
        UiObject welcomeUserTextView = new UiObject(new UiSelector().className("android.widget.TextView"));
        assertNotEquals(welcomeUserTextView.getText(), is(contains("Welcome")));
    }

    @Test
    public void login_fail_wrong_password() throws UiObjectNotFoundException, InterruptedException {

        String username = "admin";
        String password = "admin";

        // Go to login page
        new UiObject(new UiSelector().className("android.widget.Button").index(0)).click();

        // Type username
        UiObject usernameEditText = new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
        usernameEditText.click();
        usernameEditText.setText(username);

        // Type password
        UiObject passwordEditText = new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
        passwordEditText.click();
        passwordEditText.setText(password);

        // Click login button
        new UiObject(new UiSelector().className("android.widget.Button").instance(0)).click();

        // Check welcome text
        UiObject welcomeUserTextView = new UiObject(new UiSelector().className("android.widget.TextView"));
        assertNotEquals(welcomeUserTextView.getText(), is(equalTo("Welcome admin")));
    }

    @Test
    public void login_fail_wrong_username_password() throws UiObjectNotFoundException, InterruptedException {

        String username = "user";
        String password = "user";

        // Go to login page
        new UiObject(new UiSelector().className("android.widget.Button").index(0)).click();

        // Type username
        UiObject usernameEditText = new UiObject(new UiSelector().className("android.widget.EditText").instance(0));
        usernameEditText.click();
        usernameEditText.setText(username);

        // Type password
        UiObject passwordEditText = new UiObject(new UiSelector().className("android.widget.EditText").instance(1));
        passwordEditText.click();
        passwordEditText.setText(password);

        // Click login button
        new UiObject(new UiSelector().className("android.widget.Button").instance(0)).click();

        // Check welcome text
        UiObject welcomeUserTextView = new UiObject(new UiSelector().className("android.widget.TextView"));
        assertNotEquals(welcomeUserTextView.getText(), is(contains("Welcome ")));
    }


    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */
    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
    
}
