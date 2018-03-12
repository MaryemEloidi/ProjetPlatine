package platine.lille1.univ.fr.finegardens.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import platine.lille1.univ.fr.finegardens.R;
import platine.lille1.univ.fr.finegardens.activities.DescriptionJardinActivity;
import platine.lille1.univ.fr.finegardens.entities.Jardin;
import platine.lille1.univ.fr.finegardens.services.JardinsListAdapter;

/**
 * Created by cactus on 12/03/2018.
 */

public class ListeJardins extends Fragment {
    ListView listOfJardins;
    ArrayAdapter<Jardin> adapterJardin;
    ArrayList<Jardin> jardins;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.listejardinslayout, container, false);
        listOfJardins = (ListView) mView.findViewById(R.id.jardinsListe);
        jardins = new ArrayList<Jardin>();
        adapterJardin = new JardinsListAdapter(mView.getContext(), jardins);
        listOfJardins.setAdapter(adapterJardin);
        return mView;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayJardins();
        listOfJardins.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Jardin j = jardins.get(i);
                final String nom_jardin = j.getNom();
                final String id_jardin = j.getId();
                final String des = j.getDescription();

                Intent intent = new Intent(getActivity().getBaseContext(),
                        DescriptionJardinActivity.class);
                intent.putExtra("JARDIN-NOM", nom_jardin);
                intent.putExtra("JARDIN-ID", id_jardin);

                startActivity(intent);

            }
        });
    }
    public void displayJardins(){
        DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference().child("Jardins");
        mdatabase.addChildEventListener(new ChildEventListener() {
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
