package platine.lille1.univ.fr.finegardens.services;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import platine.lille1.univ.fr.finegardens.entities.Jardin;

/**
 * Created by cactus on 07/03/2018.
 */

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;
    private final Jardin mTag;

    public MyItem(double lat, double lng, String t, String s, Jardin tg) {
        mPosition = new LatLng(lat, lng);
        mTitle = t;
        mSnippet = s;
        mTag = tg;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
    public String getTitle(){
        return mTitle;
    }

    public String getSnippet(){
        return mSnippet;
    }

    public Jardin getTag(){
        return mTag;
    }

}