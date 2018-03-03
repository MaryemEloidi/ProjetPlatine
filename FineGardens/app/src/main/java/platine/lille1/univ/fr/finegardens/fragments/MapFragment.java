package platine.lille1.univ.fr.finegardens.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import platine.lille1.univ.fr.finegardens.DescriptionJardinActivity;
import platine.lille1.univ.fr.finegardens.R;
import platine.lille1.univ.fr.finegardens.entities.Jardin;

import static java.lang.Integer.parseInt;

/**
 * Created by cactus on 16/02/2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DatabaseReference mdatabase;
    public Jardin jardin;
    public String nomJardin;
    public String des;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Jardins");

        LatLng france = new LatLng(46.4892672, 2.7810699);


        googleMap.moveCamera(CameraUpdateFactory.zoomTo(6.1f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(france));
        mdatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                jardin = dataSnapshot.getValue(Jardin.class);


                // Get recorded latitude and longitude

                double latitude = jardin.getLatitude();
                double longitude = jardin.getLongitude();
                   nomJardin = jardin.getNom();

                  des = jardin.getDescription();



                // Create LatLng for each locations
                LatLng mLatlng = new LatLng(latitude, longitude);


                Marker m = googleMap.addMarker(new MarkerOptions()
                        .position(mLatlng)
                        .title(nomJardin)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_marker))
                        .snippet(des)

                );
                m.setTag(jardin);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                final String nom_jardin = marker.getTitle();
                final String id_jardin = marker.getTag().toString();
                final String des = marker.getSnippet();

               // new Handler().postDelayed(new Runnable() {

                   // @Override
                   // public void run() {
                        Intent i = new Intent(getActivity().getBaseContext(),
                                DescriptionJardinActivity.class);
                        i.putExtra("JARDIN-NOM", nom_jardin);
                        i.putExtra("JARDIN-ID", id_jardin);
                       // i.putExtra("JARDIN-DESCRIPTION", des);

                        startActivity(i);

                   // }
               /// }, 0);
                return false;
            }

        });

    }
}

