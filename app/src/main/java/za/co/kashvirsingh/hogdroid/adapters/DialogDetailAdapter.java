package za.co.kashvirsingh.hogdroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import za.co.kashvirsingh.hogdroid.R;

public class DialogDetailAdapter extends ArrayAdapter {
    Context mContext;
    int resourceID;
    ArrayList<String> names;

    public DialogDetailAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resourceID = resource;
        this.names = objects;
    }

    @Override
    public String getItem(int position) {
        return names.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        row = inflater.inflate(resourceID, parent, false);

        TextView heading = row.findViewById(R.id.info_heading);
        TextView name = row.findViewById(R.id.info_name);

        String info = names.get(position);
        String[] split = info.split(":");
        heading.setText(split[0]);
        name.setText(split[1]);
        return row;
    }

}
