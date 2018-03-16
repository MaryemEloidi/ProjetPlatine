package platine.lille1.univ.fr.finegardens;

import android.app.Fragment;
import android.support.test.espresso.IdlingResource;

import platine.lille1.univ.fr.finegardens.activities.MainActivity;

public class MainActivityIdlingResource implements IdlingResource {

    private ResourceCallback resourceCallback;
    private MainActivity activity;
    private boolean isIdle;

    public MainActivityIdlingResource(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public String getName() {
        return MainActivityIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        if (isIdle) return true;
        if (activity == null) return false;

        Fragment f = activity.getFragmentManager().findFragmentById(R.id.map);

        isIdle = f == null;

        if (isIdle) {
            resourceCallback.onTransitionToIdle();
        }

        return isIdle;
    }

    @Override
    public void registerIdleTransitionCallback(
            ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}