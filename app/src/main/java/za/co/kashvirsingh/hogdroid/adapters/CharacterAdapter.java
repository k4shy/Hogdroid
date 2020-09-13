package za.co.kashvirsingh.hogdroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import za.co.kashvirsingh.hogdroid.R;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    private List<JSONObject> characterData;
    private LayoutInflater layoutInflater;
    private ItemClickListener clickListener;

    public CharacterAdapter(Context context, List<JSONObject> data) {
        this.layoutInflater = LayoutInflater.from(context);

        this.characterData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.character_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            String name = characterData.get(position).get("name").toString();
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
            if (clickListener != null) {
                try {
                    clickListener.onItemClick(view, characterData.get(getAdapterPosition()).getString("_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setOnItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return characterData.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, String position);
    }
}
