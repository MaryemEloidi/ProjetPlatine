package platine.lille1.univ.fr.finegardens;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import platine.lille1.univ.fr.finegardens.entities.Comment;
import platine.lille1.univ.fr.finegardens.entities.Jardin;
import platine.lille1.univ.fr.finegardens.fragments.CommentsFragment;

public class DescriptionJardinActivity extends AppCompatActivity {
    private TextView commentslink;
    private TextView jardin_nom;
    private TextView jardin_adresse;
    private TextView jardin_description;
    private DatabaseReference mdatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_jardin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        jardin_nom = findViewById(R.id.jardin_nom);
        String jardinNom = getIntent().getStringExtra("JARDIN-NOM");
        jardin_nom.setText(jardinNom);
        jardin_adresse = findViewById(R.id.jardin_adresse);
        jardin_description = findViewById(R.id.jardin_description);


        mdatabase = FirebaseDatabase.getInstance().getReference().child("Jardins");
        mdatabase.orderByChild("nom")
                .equalTo(jardinNom)
                .limitToFirst(1)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Jardin jardin = dataSnapshot.getValue(Jardin.class);

                        onJardinRetrieved(jardin);

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
        commentslink = findViewById(R.id.commentLink);
        commentslink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CommentsFragment fragment = new CommentsFragment();
                Bundle args = new Bundle();
                args.putString("JARDIN-ID", getIntent().getExtras().getString("JARDIN-ID"));
                fragment.setArguments(args);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();

            }
        });


    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
    public void onJardinRetrieved(Jardin jardin){
        String description = jardin.getDescription();
        String adresse = jardin.getAdresse();
        jardin_description.setText(description);
        jardin_adresse.setText(adresse);

    }



}
