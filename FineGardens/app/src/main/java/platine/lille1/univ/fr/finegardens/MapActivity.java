package platine.lille1.univ.fr.finegardens;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.maps.model.*;

import platine.lille1.univ.fr.finegardens.entities.Jardin;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class MapActivity extends MainActivity
        implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DatabaseReference mdatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.content_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Jardins");
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
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

                double latitude = jardin.getLatitude();
                double longitude = jardin.getLongitude();
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
