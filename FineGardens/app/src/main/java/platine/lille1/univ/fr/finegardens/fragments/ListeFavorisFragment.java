package platine.lille1.univ.fr.finegardens.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import platine.lille1.univ.fr.finegardens.JardinsListAdapter;
import platine.lille1.univ.fr.finegardens.R;
import platine.lille1.univ.fr.finegardens.entities.Comment;
import platine.lille1.univ.fr.finegardens.entities.Jardin;
import platine.lille1.univ.fr.finegardens.entities.Note;

/**
 * Created by cactus on 06/03/2018.
 */

public class ListeFavorisFragment extends Fragment {
    ArrayList<String> listJardinsID;
    ArrayList<Jardin> jardins;
    private FirebaseListAdapter<Jardin> adapter;
    ArrayAdapter<Jardin> adapterJardin;
    ListView listOfFavs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View mView = inflater.inflate(R.layout.liste_favorislayout, container, false);
        listOfFavs = (ListView) mView.findViewById(R.id.favorisList);
        jardins = new ArrayList<Jardin>();
        adapterJardin = new JardinsListAdapter(mView.getContext(), jardins);
        listOfFavs.setAdapter(adapterJardin);
        return mView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listJardinsID = new ArrayList<String>();
        getListJardinID();

        listOfFavs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Jardin j = jardins.get(i);
                
            }
        });

    }
    public void getListJardinID(){
        String currentUserID = FirebaseAuth.getInstance()
                .getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("Utilisateurs").child(currentUserID).child("favoris").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> favorisChildren = dataSnapshot.getChildren();
                for (DataSnapshot fav : favorisChildren) {
                    String jardinID = fav.getKey();
                    Log.d("IDJARDIN : ",""+jardinID);
                    listJardinsID.add(jardinID);
                }
                displayFavs(listJardinsID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void displayFavs(ArrayList<String> list) {
        Log.d("diplayFavs","entering");

        for(int i = 0; i<list.size(); i++) {
            Query query = FirebaseDatabase.getInstance().getReference()
                    .child("Jardins").orderByChild("id")
                    .equalTo(list.get(i));
            query.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Jardin jardin = dataSnapshot.getValue(Jardin.class);
                    jardins.add(jardin);
                    adapterJardin.notifyDataSetChanged();
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

        }

    }

}
