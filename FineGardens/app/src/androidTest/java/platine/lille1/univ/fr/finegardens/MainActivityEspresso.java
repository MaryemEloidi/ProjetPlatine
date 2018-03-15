package platine.lille1.univ.fr.finegardens;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import platine.lille1.univ.fr.finegardens.activities.MainActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * Created by cactus on 14/03/2018.
 */

public class MainActivityEspresso  {

    UiDevice device = UiDevice.getInstance(getInstrumentation());
    UiObject marker = device.findObject(new UiSelector().descriptionContains("marker title"));





}

