package com.example.java_squad;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;

import com.robotium.solo.Solo;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for MainActivity. All the UI tests are written here. Robotium test framework is used
 */
public class MainActivityTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }
    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
    @Test
    public void testOpenEditProfileActivity() {
        // register next activity that need to be monitored.
        solo.assertCurrentActivity("Wrong activity", MainActivity.class); // if the launched activity != Mainactivity
        solo.clickOnButton("EDIT PROFILE");
        solo.assertCurrentActivity("Wrong activity", EditProfileActivity.class); // test if in ShowActivity
    }
    @Test
    public void testOpenShowAllOwnedExpActivity() {
        // register next activity that need to be monitored.
        solo.assertCurrentActivity("Wrong activity", MainActivity.class); // if the launched activity != Mainactivity
        solo.getButton(R.id.editProfile);
        solo.clickOnButton("EDIT PROFILE");
        solo.assertCurrentActivity("Wrong activity", EditProfileActivity.class); // test if in ShowActivity
    }
    /**
     * Close activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}
