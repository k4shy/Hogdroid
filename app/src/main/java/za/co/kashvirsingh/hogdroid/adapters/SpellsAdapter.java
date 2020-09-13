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

public class SpellsAdapter extends RecyclerView.Adapter<SpellsAdapter.ViewHolder> {

    private List<JSONObject> spellData;
    private LayoutInflater layoutInflater;
    private ItemClickListener clickListener;

    public SpellsAdapter(Context context, List<JSONObject> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.spellData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.spell_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpellsAdapter.ViewHolder holder, int position) {

        try {
            String name = spellData.get(position).get("spell").toString();
            holder.spellName.setText(name);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView spellName;
        ViewHolder(View itemView) {
            super(itemView);
            spellName = itemView.findViewById(R.id.spell_name);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                try {
                    clickListener.onItemClick(view, spellData.get(getAdapterPosition()).getString("_id"));
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
        return spellData.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, String id);
    }
}
