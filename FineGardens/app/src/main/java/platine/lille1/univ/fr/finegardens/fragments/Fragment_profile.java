package platine.lille1.univ.fr.finegardens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import platine.lille1.univ.fr.finegardens.R;

/**
 * Created by cactus on 16/02/2018.
 */

public class Fragment_profile extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_layout, container, false);

    }
}
