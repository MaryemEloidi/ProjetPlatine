package platine.lille1.univ.fr.finegardens;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import platine.lille1.univ.fr.finegardens.activities.DescriptionJardinActivity;
import platine.lille1.univ.fr.finegardens.activities.LoginActivity;
import platine.lille1.univ.fr.finegardens.activities.SignUpActivity;
import platine.lille1.univ.fr.finegardens.fragments.CommentsFragment;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;



import static android.support.test.espresso.assertion.ViewAssertions.matches;


/**
 * Created by cactus on 16/03/2018.
 */

public class DescriptionActivityEspresso {
    @Rule
    public ActivityTestRule<DescriptionJardinActivity> mActivityRule = new ActivityTestRule<>(DescriptionJardinActivity.class);
    @Before
    public void setUp() throws Exception{
        Intents.init();

    }
    @Test
    public void testAjoutDuJardinAuFavoris(){

        onView(withId(R.id.likeLayout)).perform((click()));


    }
    @Test
    public void testAccesItiniraire(){
        onView(withId(R.id.markerBTN)).perform(click());
        String uri = "http://maps.google.com/maps";
        mActivityRule.launchActivity(new Intent());
        intended(hasComponent(new ComponentName(getTargetContext(), uri)));

    }
    @Test
    public void testAccesCommentaireDialog(){
        onView(withId(R.id.commentBTN)).perform(click());
        onView(withId(R.id.commentEDT)).check(matches(isDisplayed()));


    }
    @Test
    public void testAfficherCommentaires(){
        onView(withId(R.id.commentLink)).perform(click());
        mActivityRule.launchActivity(new Intent());
        intended(hasComponent(new ComponentName(getTargetContext(), CommentsFragment.class)));
    }
    @After
    public void tearDown(){
        Intents.release();
    }

}
