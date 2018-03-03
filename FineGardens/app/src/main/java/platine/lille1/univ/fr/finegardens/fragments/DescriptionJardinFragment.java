package platine.lille1.univ.fr.finegardens.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


import java.util.ArrayList;

import platine.lille1.univ.fr.finegardens.R;
import platine.lille1.univ.fr.finegardens.entities.Comment;

/**
 * Created by cactus on 16/02/2018.
 */

public class DescriptionJardinFragment extends Fragment {
    private Button marker;
    FloatingActionButton fab;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> mComments = new ArrayList<>();
    private FirebaseListAdapter<Comment> adapter;
    ListView listOfComments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_description_jardin, container, false);
        View rootView = inflater.inflate(R.layout.fragment_description_jardin, container, false);

        listOfComments = (ListView)rootView.findViewById(R.id.list_of_messages);
        //arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mComments);

        //listOfComments.setAdapter(arrayAdapter);

        return rootView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        marker = (Button)getView().findViewById(R.id.markerBTN);
        marker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=parc+de+la+citadelle,+Voie+Piétonne+du+Bois+de+la+Deûle"));
                startActivity(intent);
            }
        });
        displayComments();
        FloatingActionButton fab =
                (FloatingActionButton)getView().findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)getView().findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference().child("Commentaire")
                        .push()
                        .setValue(new Comment("-L3hezw1i-2kf91504PK",input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getEmail())
                        );

                // Clear the input
                input.setText("");
            }
        });

    }
    public void displayComments(){

        Query query = FirebaseDatabase.getInstance().getReference().child("Commentaire").orderByKey();
        FirebaseListOptions<Comment> options = new FirebaseListOptions.Builder<Comment>()
                .setQuery(query, Comment.class)
                .setLayout(R.layout.commentlayout)
                .setLifecycleOwner(this)   //Added this
                .build();
        adapter = new FirebaseListAdapter<Comment>(options){
            @Override
            protected void populateView(View v, Comment model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);


                // Set their text
                messageText.setText(model.getCommentText());
                messageUser.setText(model.getCommentUser());                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getCommentTime()));

            }
        };
        listOfComments.setAdapter(adapter);


    }


}
