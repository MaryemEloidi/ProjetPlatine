package platine.lille1.univ.fr.finegardens.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
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

import platine.lille1.univ.fr.finegardens.R;
import platine.lille1.univ.fr.finegardens.entities.Jardin;

/**
 * Created by cactus on 16/02/2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DatabaseReference mdatabase;

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

        /*LatLng lille = new LatLng(50.6397538, 3.0381042999999863);
        LatLng sydney = new LatLng(50.6373832, 3.1463655999999673);
        LatLng france = new LatLng(46.4892672, 2.7810699);

        googleMap.addMarker(new MarkerOptions().position(lille).title("Marker in lille"));
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));*/

        googleMap.moveCamera(CameraUpdateFactory.zoomTo(6.1f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(france));
        mdatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Jardin jardin = dataSnapshot.getValue(Jardin.class);


                // Get recorded latitude and longitude

                long latitude = jardin.getLatitude();
                long longitude = jardin.getLongitude();
                String nomJardin = jardin.getNom();


                // Create LatLng for each locations
                LatLng mLatlng = new LatLng(latitude, longitude);


                googleMap.addMarker(new MarkerOptions()
                        .position(mLatlng)
                        .title(nomJardin)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_marker))
                );

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
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        googleMap.animateCamera(CameraUpdateFactory.
                                newLatLngZoom(marker.getPosition(), 15f));
                    }
                }, 300);

                return false;
            }
        });

    }
    }

