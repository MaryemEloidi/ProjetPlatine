package platine.lille1.univ.fr.finegardens;

import android.content.ComponentName;
import android.support.test.espresso.intent.Intents;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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
 * Created by cactus on 16/03/2018.
 */

public class AjoutJardinEspresso
{
    @Before
    public void setUp() throws Exception{
        Intents.init();
    }
    @Test
    public void testAjouterJardin(){
        onView(withId(R.id.input_nom)).perform(typeText("Parcnomtest"),closeSoftKeyboard());
        onView(withId(R.id.input_adresse)).perform(typeText("Parcadressetest"),closeSoftKeyboard());
        onView(withId(R.id.input_description)).perform(typeText("input_description"),closeSoftKeyboard());
        onView(withId(R.id.btn_ajouter)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(),MainActivity.class)));

    }
    @After
    public void tearDown(){
        Intents.release();
    }
}
