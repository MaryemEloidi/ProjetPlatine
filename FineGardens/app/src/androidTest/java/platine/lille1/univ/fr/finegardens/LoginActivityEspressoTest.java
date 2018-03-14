package platine.lille1.univ.fr.finegardens;

import android.content.ComponentName;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import platine.lille1.univ.fr.finegardens.activities.LoginActivity;
import platine.lille1.univ.fr.finegardens.activities.MainActivity;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by cactus on 14/03/2018.
 */

public class LoginActivityEspressoTest {
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);
    @Test
    public void ensureLogin(){
        onView(withId(R.id.input_email)).perform(typeText("test@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.input_password)).perform(typeText("test12"),closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(),MainActivity.class)));

    }
}
