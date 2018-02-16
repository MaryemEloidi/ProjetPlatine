package platine.lille1.univ.fr.finegardens.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

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

        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.zoomTo(6.1f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(france));
        mdatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Jardin jardin = dataSnapshot.getValue(Jardin.class);

                // Get recorded latitude and longitude
                long latitude = jardin.getLatitude();
                long longitude = jardin.getLongitude();

                // Create LatLng for each locations
                LatLng mLatlng = new LatLng(latitude, longitude);

                mMap.addMarker(new MarkerOptions()
                        .position(mLatlng)
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

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Fragment descriptionFragment = new DescriptionJardinFragment();
                        FragmentManager fragmentManager  = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.flContent, descriptionFragment).commit();
                    }
                }, 300);

                return false;
            }
        });
    }
}

