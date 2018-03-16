package platine.lille1.univ.fr.finegardens.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
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
import com.google.maps.android.clustering.ClusterManager;


import platine.lille1.univ.fr.finegardens.activities.DescriptionJardinActivity;
import platine.lille1.univ.fr.finegardens.services.MyItem;
import platine.lille1.univ.fr.finegardens.R;
import platine.lille1.univ.fr.finegardens.entities.Jardin;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mdatabase;
    private Jardin jardin;
    private String nomJardin;
    private String des;
    private ClusterManager<MyItem> mClusterManager;
    private FragmentActivity myContext;



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
        mClusterManager = new ClusterManager<MyItem>(getContext(), googleMap);

        LatLng france = new LatLng(46.4892672, 2.7810699);


        googleMap.moveCamera(CameraUpdateFactory.zoomTo(6.1f));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(france));
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
        }
       // googleMap.setOnCameraIdleListener(mClusterManager);
       // googleMap.setOnMarkerClickListener(mClusterManager);

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

                );

                //MyItem offsetItem = new MyItem(latitude, longitude,nomJardin,des,jardin);
               //mClusterManager.addItem(offsetItem);

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

        /*ClusterManager.OnClusterItemClickListener<MyItem> mClusterItemClickListener = new ClusterManager.OnClusterItemClickListener<MyItem>() {

            @Override
            public boolean onClusterItemClick(MyItem item) {
                final String nom_jardin = item.getTitle();
                final String id_jardin = item.getTag().toString();
                final String des = item.getSnippet();

                Intent i = new Intent(getActivity().getBaseContext(),
                        DescriptionJardinActivity.class);
                i.putExtra("JARDIN-NOM", nom_jardin);
                i.putExtra("JARDIN-ID", id_jardin);

                startActivity(i);
                return false;
            }
        };
        mClusterManager.setOnClusterItemClickListener(mClusterItemClickListener);

*/
//        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(final Marker marker) {
//                final String nom_jardin = marker.getTitle();
//                final String id_jardin = marker.getTag().toString();
//                final String des = marker.getSnippet();
//
//                        Intent i = new Intent(getActivity().getBaseContext(),
//                                DescriptionJardinActivity.class);
//                        i.putExtra("JARDIN-NOM", nom_jardin);
//                        i.putExtra("JARDIN-ID", id_jardin);
//
//                        startActivity(i);
//                return false;
//            }
//
//        });
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                final String nom_jardin = marker.getTitle();
                final String id_jardin = marker.getTag().toString();
                final String des = marker.getSnippet();

                Intent i = new Intent(getActivity().getBaseContext(),
                        DescriptionJardinActivity.class);
                i.putExtra("JARDIN-NOM", nom_jardin);
                i.putExtra("JARDIN-ID", id_jardin);

                startActivity(i);
            }
        });

    }
    /*private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 46.4892672;
        double lng = 2.7810699;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
    }*/
}

