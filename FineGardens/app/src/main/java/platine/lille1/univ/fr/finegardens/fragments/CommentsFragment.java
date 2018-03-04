package platine.lille1.univ.fr.finegardens.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
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
 * Created by cactus on 18/02/2018.
 */

public class CommentsFragment extends Fragment {
    ArrayList<String> mComments = new ArrayList<>();
    private FirebaseListAdapter<Comment> adapter;
    ListView listOfComments;
    private String jardinID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.commentslayout, container, false);

        listOfComments = (ListView)rootView.findViewById(R.id.list_of_comments);
        setListViewHeightBasedOnChildren(listOfComments);
        Bundle args = getArguments();
        jardinID = args.getString("JARDIN-ID");
        Log.d("jardiID", ""+jardinID);
        return rootView;

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
