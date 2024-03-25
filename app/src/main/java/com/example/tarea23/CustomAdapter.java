package com.example.tarea23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarea23.Photograph;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Photograph> {

    private Context context;
    private List<Photograph> photographs;

    public CustomAdapter(Context context, List<Photograph> photographs) {
        super(context, R.layout.list_item, photographs);
        this.context = context;
        this.photographs = photographs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.list_item, parent, false);
        }

        ImageView imageView = listItemView.findViewById(R.id.imageView);
        TextView textViewDescription = listItemView.findViewById(R.id.textViewDescription);

        Photograph photograph = photographs.get(position);
        imageView.setImageBitmap(photograph.getImage());
        textViewDescription.setText(photograph.getDescription());

        return listItemView;
    }
}
