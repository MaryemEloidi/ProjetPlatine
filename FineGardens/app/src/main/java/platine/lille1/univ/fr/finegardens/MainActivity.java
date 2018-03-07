package platine.lille1.univ.fr.finegardens;

import android.content.Intent;
import android.icu.util.Freezable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import platine.lille1.univ.fr.finegardens.entities.Jardin;
import platine.lille1.univ.fr.finegardens.fragments.AjouterJardinFragment;
import platine.lille1.univ.fr.finegardens.fragments.CommentsFragment;
import platine.lille1.univ.fr.finegardens.fragments.DescriptionJardinFragment;
import platine.lille1.univ.fr.finegardens.fragments.Fragment_profile;
import platine.lille1.univ.fr.finegardens.fragments.ListeFavorisFragment;
import platine.lille1.univ.fr.finegardens.fragments.MapFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> mJardinsnames = new ArrayList<>();
    TextView login;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AjouterJardinFragment();
                FragmentManager fragmentManager  = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flContent, fragment).commit();

            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        login = (TextView)header.findViewById(R.id.login);
        email = (TextView)header.findViewById(R.id.email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            login.setText(user.getDisplayName());
            Log.d(user.getDisplayName(),"logiiiiiiiin :");
            email.setText(user.getEmail());
        }
        Fragment fragment = new MapFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();




//
//        ListView mListview = (ListView)findViewById(R.id.listview_jardins_list);
//        DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference().child("Jardins");
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mJardinsnames);
//        mListview.setAdapter(arrayAdapter);
//        mdatabase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//               Jardin jardin = dataSnapshot.getValue(Jardin.class);
//                String jardinvalue = String.valueOf(jardin);
//
//                mJardinsnames.add(jardinvalue);
//                arrayAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_les_jardins) {
            fragment = new MapFragment();
        } else if (id == R.id.nav_coup_coeurs) {
        fragment = new ListeFavorisFragment();

        } else if (id == R.id.nav_deja_visites) {


        } else if (id == R.id.nav_ajouter) {

            fragment = new AjouterJardinFragment();

        } else if (id == R.id.nav_liste_perso) {
        } else if (id == R.id.nav_share) {

        }else if (id == R.id.nav_send) {
            fragment = new Fragment_profile();

        }
        if(fragment!= null ){
            FragmentManager fragmentManager  = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.flContent, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
