package za.co.kashvirsingh.hogdroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import za.co.kashvirsingh.hogdroid.R;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder>{

    private List<JSONObject> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public CharacterAdapter(Context context, List<JSONObject> data) {
        this.mInflater = LayoutInflater.from(context);

        this.mData = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.character_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            String name = mData.get(position).get("name").toString();
            holder.characterName.setText(name);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView characterName;
        ViewHolder(View itemView) {
            super(itemView);
            characterName = itemView.findViewById(R.id.character_name);


            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
