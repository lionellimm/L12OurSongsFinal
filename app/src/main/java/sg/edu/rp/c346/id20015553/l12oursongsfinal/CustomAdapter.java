package sg.edu.rp.c346.id20015553.l12oursongsfinal;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Song> songList;

    public CustomAdapter(Context context, int resource,
                         ArrayList<Song> objects){
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        songList = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.textViewName);
        TextView tvYear = rowView.findViewById(R.id.textViewYear);
//        RatingBar rating = rowView.findViewById(R.id.ratingBar3);
        TextView tvStar = rowView.findViewById(R.id.textViewStars);
        TextView tvActor = rowView.findViewById(R.id.textViewActor);
        ImageView ivNew = rowView.findViewById(R.id.imageViewNew);
        // Obtain the Android Version information based on the position
        Song currentSong = songList.get(position);

        if(currentSong.getYearReleased()>=2019){
            ivNew.setImageResource(R.drawable.newwww);
        }
        else{
            ivNew.setImageResource(0);
        }

        // Set values to the TextView to display the corresponding information
        tvName.setText(currentSong.getTitle());
        tvYear.setText(String.valueOf(currentSong.getYearReleased()));
        tvStar.setText(String.valueOf(currentSong.getStars()) + "*");
//        rating.setRating(currentSong.getStars());
        tvActor.setText(currentSong.getSingers());

        return rowView;
    }
}
