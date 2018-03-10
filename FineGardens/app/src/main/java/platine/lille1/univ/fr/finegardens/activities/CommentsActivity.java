package platine.lille1.univ.fr.finegardens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import platine.lille1.univ.fr.finegardens.R;
import platine.lille1.univ.fr.finegardens.entities.Comment;


/**
 * Created by cactus on 08/03/2018.
 */

public class CommentsActivity extends AppCompatActivity {
    ArrayList<String> mComments = new ArrayList<>();
    private FirebaseListAdapter<Comment> adapter;
    ListView listOfComments;
    private String jardinID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentslayout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        listOfComments = (ListView)findViewById(R.id.list_of_comments);

        jardinID = getIntent().getStringExtra("JARDIN-ID");
        Log.d("jardiID", ""+jardinID);
        displayComments();

    }
    public void displayComments(){

        Query query = FirebaseDatabase.getInstance().getReference().child("Commentaire").orderByChild("jardinID").equalTo(jardinID);
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

    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
