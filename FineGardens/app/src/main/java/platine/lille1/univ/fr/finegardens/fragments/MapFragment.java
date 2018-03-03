package platine.lille1.univ.fr.finegardens.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> 714bc41841ef99141cdbd416ec56384e2be28418
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

<<<<<<< HEAD
import static java.lang.Integer.parseInt;

/**
 * Created by cactus on 16/02/2018.
 */

=======
>>>>>>> 714bc41841ef99141cdbd416ec56384e2be28418
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
<<<<<<< HEAD
    private DatabaseReference mdatabase;
    public Jardin jardin;
    public String nomJardin;
    public String des;
=======

>>>>>>> 714bc41841ef99141cdbd416ec56384e2be28418
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
    public void onMapReady(GoogleMap googleMap) {
        DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference().child("Jardins");

        LatLng france = new LatLng(46.4892672, 2.7810699);

<<<<<<< HEAD

        googleMap.moveCamera(CameraUpdateFactory.zoomTo(6.1f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(france));
=======
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.zoomTo(6.1f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(france));
>>>>>>> 714bc41841ef99141cdbd416ec56384e2be28418
        mdatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                jardin = dataSnapshot.getValue(Jardin.class);

                // Get recorded latitude and longitude
<<<<<<< HEAD

                double latitude = jardin.getLatitude();
                double longitude = jardin.getLongitude();
                   nomJardin = jardin.getNom();

                  des = jardin.getDescription();


=======
                long latitude = jardin.getLatitude();
                long longitude = jardin.getLongitude();
>>>>>>> 714bc41841ef99141cdbd416ec56384e2be28418

                // Create LatLng for each locations
                LatLng mLatlng = new LatLng(latitude, longitude);

<<<<<<< HEAD

                Marker m = googleMap.addMarker(new MarkerOptions()
=======
                mMap.addMarker(new MarkerOptions()
>>>>>>> 714bc41841ef99141cdbd416ec56384e2be28418
                        .position(mLatlng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_marker))
                        .snippet(des)

                );
<<<<<<< HEAD
                m.setTag(jardin);

=======
>>>>>>> 714bc41841ef99141cdbd416ec56384e2be28418
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

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
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

<<<<<<< HEAD
                        startActivity(i);
=======
                    @Override
                    public void run() {
                        Fragment descriptionFragment = new DescriptionJardinFragment();
                        FragmentManager fragmentManager  = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flContent, descriptionFragment).commit();
                    }
                }, 300);
>>>>>>> 714bc41841ef99141cdbd416ec56384e2be28418

                   // }
               /// }, 0);
                return false;
            }

        });
<<<<<<< HEAD

=======
>>>>>>> 714bc41841ef99141cdbd416ec56384e2be28418
    }
}

