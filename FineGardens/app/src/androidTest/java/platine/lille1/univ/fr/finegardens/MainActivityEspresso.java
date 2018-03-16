package platine.lille1.univ.fr.finegardens;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import platine.lille1.univ.fr.finegardens.activities.LoginActivity;
import platine.lille1.univ.fr.finegardens.activities.MainActivity;
import platine.lille1.univ.fr.finegardens.fragments.ListeJardins;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressMenuKey;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityEspresso  {

    private MainActivityIdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule2 = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception{
        Intents.init();
    }

    @Before
    public void registerIntentServiceIdlingResource() {
        MainActivity activity = mActivityRule2.getActivity();
        idlingResource = new MainActivityIdlingResource(activity);
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void ensureNavigationDrawerToListeJardins() {
        onView(withId(R.id.input_email)).perform(typeText("test@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText("test12"),closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        //mActivityRule.launchActivity(new Intent());

        onView(withId(R.id.drawer_layout)).perform(pressMenuKey());
        onView(withId(R.id.nav_les_jardins)).perform(click());

        //mActivityRule2.launchActivity(new Intent());

        intended(hasComponent(new ComponentName(getTargetContext(), ListeJardins.class)));
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @After
    public void unregisterIntentServiceIdlingResource() {
        Espresso.unregisterIdlingResources(idlingResource);
    }
}

