package platine.lille1.univ.fr.finegardens.services;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import platine.lille1.univ.fr.finegardens.R;
import platine.lille1.univ.fr.finegardens.entities.Jardin;

/**
 * Created by cactus on 07/03/2018.
 */

public class JardinsListAdapter extends ArrayAdapter<Jardin> {
    private final List<Jardin> list;
    private final Context context;
    public JardinsListAdapter(Context context, List<Jardin> list){
        super(context, R.layout.favoris_item_layout, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (convertView == null) {

            LayoutInflater inflator = LayoutInflater.from(context);
            view = inflator.inflate(R.layout.favoris_item_layout, null);

        }
        else {
            view = convertView;
        }
        TextView titre = (TextView) view.findViewById(R.id.title_jardin);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_jardin_LIST);

        // ViewHolder holder = (ViewHolder) view.getTag();
        titre.setText(list.get(position).getNom());
        Glide.with(getContext())
                .load(list.get(position).getImageUrl())
                .into(imageView);
        return view;
    }
}
