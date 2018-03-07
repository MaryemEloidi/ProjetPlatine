package platine.lille1.univ.fr.finegardens;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.PublicKey;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import platine.lille1.univ.fr.finegardens.entities.Comment;
import platine.lille1.univ.fr.finegardens.entities.Jardin;
import platine.lille1.univ.fr.finegardens.entities.Note;
import platine.lille1.univ.fr.finegardens.fragments.CommentsFragment;

public class DescriptionJardinActivity extends AppCompatActivity {
    private TextView commentslink;
    private TextView jardin_nom;
    private TextView jardin_adresse;
    private TextView jardin_description;
    private RatingBar jarind_rating;
    private DatabaseReference mdatabase;
    private Button itiniraire;
    private Button commentBTN;
    private Button likeBTN;
    private ImageView imageJardin;
    private TextView ratingText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_jardin);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        jardin_nom = findViewById(R.id.jardin_nom);
        final String jardinNom = getIntent().getStringExtra("JARDIN-NOM");
        jardin_nom.setText(jardinNom);
        jardin_adresse = findViewById(R.id.jardin_adresse);
        jardin_description = findViewById(R.id.jardin_description);
        imageJardin = findViewById(R.id.image_jardin);
        jarind_rating = findViewById(R.id.jardin_rating_avg);
        ratingText = findViewById(R.id.ratingTv);
        noteAVGJardin();
        itiniraire = findViewById(R.id.markerBTN);
        commentBTN = findViewById(R.id.commentBTN);
        likeBTN = findViewById(R.id.likeBTN);
        isJardinFavorite();
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
        itiniraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String adresseItiniraire = jardin_adresse.getText().toString();
                adresseItiniraire.replaceAll(" ","+");

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+jardinNom.replaceAll(" ","+")+","+adresseItiniraire));
                startActivity(intent);
            }
        });
        commentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DescriptionJardinActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.add_commentlayout, null);
                final EditText comment = (EditText) mView.findViewById(R.id.commentEDT);
                final TextView title = (TextView) mView.findViewById(R.id.title_dialog);
                final RatingBar noteUserJardin = mView.findViewById(R.id.btnRating);
                Button validerAjoutComment = (Button) mView.findViewById(R.id.btn_valider_comment);
                Button annulerAjoutComment = (Button) mView.findViewById(R.id.btn_annuler_comm);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                validerAjoutComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!comment.getText().toString().isEmpty()|| !String.valueOf(noteUserJardin.getRating()).isEmpty()) {
                            if(!comment.getText().toString().isEmpty()) {
                                FirebaseDatabase.getInstance()
                                        .getReference().child("Commentaire")
                                        .push()
                                        .setValue(new Comment(getIntent().getExtras().getString("JARDIN-ID"), comment.getText().toString(),
                                                FirebaseAuth.getInstance()
                                                        .getCurrentUser()
                                                        .getEmail())
                                        );
                            }
                            if(!String.valueOf(noteUserJardin.getRating()).isEmpty()){
                                FirebaseDatabase.getInstance()
                                        .getReference().child("Note")
                                        .push()
                                        .setValue(new Note(getIntent().getExtras().getString("JARDIN-ID"), noteUserJardin.getRating(),
                                                FirebaseAuth.getInstance()
                                                        .getCurrentUser()
                                                        .getEmail())
                                        );
                                noteAVGJardin();
                            }

                        }else{
                            Toast.makeText(DescriptionJardinActivity.this,"Vous n'avez pas laissé de commentaire",Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                annulerAjoutComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        likeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String currentUserID = FirebaseAuth.getInstance()
                        .getCurrentUser().getUid();
                final DatabaseReference ref = FirebaseDatabase.getInstance()
                        .getReference().child("Utilisateurs").child(currentUserID).child("favoris");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(getIntent().getExtras().getString("JARDIN-ID"))){
                           ref.child(getIntent().getExtras().getString("JARDIN-ID")).removeValue();
                            likeBTN.setBackgroundResource(R.drawable.heart_icon);
                        }
                        else{
                            ref.child(getIntent().getExtras().getString("JARDIN-ID")).setValue(true);
                            likeBTN.setBackgroundResource(R.drawable.heart_plein);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //FirebaseDatabase.getInstance()
                      //  .getReference().child("Utilisateurs").child(currentUserID).child("favoris").child(getIntent().getExtras().getString("JARDIN-ID")).setValue(true);
            }
        });


    }
    public void isJardinFavorite(){
        String currentUserID = FirebaseAuth.getInstance()
                .getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child("Utilisateurs").child(currentUserID).child("favoris").child(getIntent().getExtras().getString("JARDIN-ID"));

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean isFav = dataSnapshot.getValue(Boolean.class);
                if(isFav!=null && isFav){
                    likeBTN.setBackgroundResource(R.drawable.heart_plein);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //TODO a debeuguer !
    public void countCommentJardin(){

        FirebaseDatabase.getInstance()
                .getReference().child("Commentaire")
                .orderByChild("jardinID").equalTo(getIntent().getExtras().getString("JARDIN-ID")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               final long count = dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        String urlImage = jardin.getImageUrl();
        jardin_description.setText(description);
        jardin_adresse.setText(adresse);

        Glide.with(this)
                .load(urlImage)
                .into(imageJardin);

    }
    public void noteAVGJardin(){
        FirebaseDatabase.getInstance()
                .getReference().child("Note")
                .orderByChild("jardinID").equalTo(getIntent().getExtras().getString("JARDIN-ID")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> noteChildren = dataSnapshot.getChildren();
                float sumNote = 0;
                int countNote = 0;
                for (DataSnapshot note : noteChildren) {
                    Note n = note.getValue(Note.class);
                    sumNote+= n.getRating();
                    countNote++;
                    Log.d("Note",String.valueOf(n.getRating()));
                }
                float noteAVG = sumNote/countNote;
                Log.d("note AVG: ",""+noteAVG);
                jarind_rating.setRating(noteAVG);
                DecimalFormat df = new DecimalFormat("#.0");
                ratingText.setText(df.format(noteAVG));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}
