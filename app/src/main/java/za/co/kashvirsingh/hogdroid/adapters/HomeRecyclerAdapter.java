package za.co.kashvirsingh.hogdroid.adapters;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import za.co.kashvirsingh.hogdroid.CharacterActivity;
import za.co.kashvirsingh.hogdroid.R;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

    private List<JSONObject> homeData;
    private List<JSONObject> characterData;
    private LayoutInflater layoutInflater;
    private ItemClickListener clickListener;

    boolean showContent = true;
    int rotationAngle = 0;

    public HomeRecyclerAdapter(Context context, List<JSONObject> data, List<JSONObject> characterData) {
        this.layoutInflater = LayoutInflater.from(context);
        this.homeData = data;
        this.characterData = characterData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.home_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String house = "";
        String headofHouse = "n/a";
        String mascot = "n/a";
        String houseGhost = "n/a";
        String founder = "n/a";
        String school = "n/a";
        StringBuilder values = new StringBuilder("n/a");
        StringBuilder color = new StringBuilder("n/a");
        List<String> membersList = new ArrayList<>();

        try {
            house = homeData.get(position).get("name").toString();
            if(house.length() > 0) {
                switch (house.toLowerCase()) {
                    case "gryffindor":
                        holder.houseImage.setBackgroundResource(R.drawable.gryffindor_house);
                        break;
                    case "ravenclaw":
                        holder.houseImage.setBackgroundResource(R.drawable.ravenclaw_house);
                        break;
                    case "slytherin":
                        holder.houseImage.setBackgroundResource(R.drawable.slytherin_house);
                        break;
                    case "hufflepuff":
                        holder.houseImage.setBackgroundResource(R.drawable.hufflepuff_house);
                        break;
                }
            }
            headofHouse = homeData.get(position).get("headOfHouse").toString();
            mascot = homeData.get(position).get("mascot").toString();
            houseGhost = homeData.get(position).get("houseGhost").toString();
            founder = homeData.get(position).get("founder").toString();
            school = homeData.get(position).get("school").toString();
            if (homeData.get(position).get("values").toString().startsWith("[")) {
                values = new StringBuilder();
                String prefix = "";
                JSONArray valuesArray = new JSONArray(homeData.get(position).get("values").toString());
                for (int i = 0; i < valuesArray.length(); i++) {
                    values.append(prefix);
                    prefix = ", ";
                    values.append(valuesArray.getString(i));
                }
            }
            if (homeData.get(position).get("colors").toString().startsWith("[")) {
                color = new StringBuilder();
                String prefix = "";
                JSONArray colorsArray = new JSONArray(homeData.get(position).get("colors").toString());
                for (int i = 0; i < colorsArray.length(); i++) {
                    color.append(prefix);
                    prefix = ", ";
                    color.append(colorsArray.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.houseName.setText(house);
        holder.txtMoreMascot.setText(mascot);
        holder.txtMoreHeadofHouse.setText(headofHouse);
        holder.txtMoreHouseGhost.setText(houseGhost);
        holder.txtMoreFounder.setText(founder);
        holder.txtMoreSchool.setText(school);
        holder.txtMoreValues.setText(values.toString());
        holder.txtMoreColor.setText(color.toString());
        holder.btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showContent) {
                    holder.txtMoreMascotLabel.setVisibility(View.VISIBLE);
                    holder.txtMoreMascot.setVisibility(View.VISIBLE);
                    holder.txtMoreHeadofHouseLabel.setVisibility(View.VISIBLE);
                    holder.txtMoreHeadofHouse.setVisibility(View.VISIBLE);
                    holder.txtMoreHouseGhostLabel.setVisibility(View.VISIBLE);
                    holder.txtMoreHouseGhost.setVisibility(View.VISIBLE);
                    holder.txtMoreFounderLabel.setVisibility(View.VISIBLE);
                    holder.txtMoreFounder.setVisibility(View.VISIBLE);
                    holder.txtMoreSchoolLabel.setVisibility(View.VISIBLE);
                    holder.txtMoreSchool.setVisibility(View.VISIBLE);
                    holder.txtMoreValuesLabel.setVisibility(View.VISIBLE);
                    holder.txtMoreValues.setVisibility(View.VISIBLE);
                    holder.txtMoreColorLabel.setVisibility(View.VISIBLE);
                    holder.txtMoreColor.setVisibility(View.VISIBLE);
                    ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation",rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle%360;
                    showContent = false;
                } else {
                    holder.txtMoreMascotLabel.setVisibility(View.GONE);
                    holder.txtMoreMascot.setVisibility(View.GONE);
                    holder.txtMoreHeadofHouseLabel.setVisibility(View.GONE);
                    holder.txtMoreHeadofHouse.setVisibility(View.GONE);
                    holder.txtMoreHouseGhostLabel.setVisibility(View.GONE);
                    holder.txtMoreHouseGhost.setVisibility(View.GONE);
                    holder.txtMoreFounderLabel.setVisibility(View.GONE);
                    holder.txtMoreFounder.setVisibility(View.GONE);
                    holder.txtMoreSchoolLabel.setVisibility(View.GONE);
                    holder.txtMoreSchool.setVisibility(View.GONE);
                    holder.txtMoreValuesLabel.setVisibility(View.GONE);
                    holder.txtMoreValues.setVisibility(View.GONE);
                    holder.txtMoreColorLabel.setVisibility(View.GONE);
                    holder.txtMoreColor.setVisibility(View.GONE);
                    ObjectAnimator anim = ObjectAnimator.ofFloat(v, "rotation",rotationAngle, rotationAngle + 180);
                    anim.setDuration(500);
                    anim.start();
                    rotationAngle += 180;
                    rotationAngle = rotationAngle%360;
                    showContent = true;
                }
            }
        });
        String finalHouse = house;
        holder.houseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (homeData.get(position).get("members").toString().startsWith("[")) {
                        JSONArray membersArray = new JSONArray(homeData.get(position).get("members").toString());
                        for (int i = 0; i < membersArray.length(); i++) {
                            membersList.add(membersArray.getString(i));
                        }
                    }
                    List<JSONObject> jsonObjects = compareLists(characterData, membersList);
                    ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) v.getContext(), holder.houseImage, "imageMain");
                    Intent in = new Intent((Activity) v.getContext(), CharacterActivity.class);
                    in.putExtra("members", jsonObjects.toString());
                    if (finalHouse.length() > 0) {
                        switch (finalHouse.toLowerCase()) {
                            case "gryffindor":
                                in.putExtra("house_name", "gryffindor");
                                break;
                            case "ravenclaw":
                                in.putExtra("house_name", "ravenclaw");
                                break;
                            case "slytherin":
                                in.putExtra("house_name", "slytherin");
                                break;
                            case "hufflepuff":
                                in.putExtra("house_name", "hufflepuff");
                                break;
                        }
                    }
                    v.getContext().startActivity(in, activityOptionsCompat.toBundle());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private List<JSONObject> compareLists(List<JSONObject> completeList, List<String> keyList) {
        List<JSONObject> resList = new ArrayList<>();
        for(String key : keyList) {
            for (int i = 0; i < completeList.size(); i++) {
                JSONObject jsonObject = completeList.get(i);
                try {
                    if(jsonObject.get("_id").equals(key)){
                        resList.add(jsonObject);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return resList;
    }

    @Override
    public int getItemCount() {
        return homeData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView houseName;
        ImageView houseImage;
        Button btnLoadMore;
        TextView txtMoreMascot;
        TextView txtMoreMascotLabel;
        TextView txtMoreHeadofHouse;
        TextView txtMoreHeadofHouseLabel;
        TextView txtMoreHouseGhost;
        TextView txtMoreHouseGhostLabel;
        TextView txtMoreFounder;
        TextView txtMoreFounderLabel;
        TextView txtMoreSchool;
        TextView txtMoreSchoolLabel;
        TextView txtMoreValues;
        TextView txtMoreValuesLabel;
        TextView txtMoreColor;
        TextView txtMoreColorLabel;
        ViewHolder(View itemView) {
            super(itemView);
            houseName = itemView.findViewById(R.id.house_name);
            txtMoreMascotLabel = itemView.findViewById(R.id.txt_more_mascot_label);
            txtMoreMascot = itemView.findViewById(R.id.txt_more_mascot);
            txtMoreHeadofHouseLabel = itemView.findViewById(R.id.txt_more_head_of_house_label);
            txtMoreHeadofHouse = itemView.findViewById(R.id.txt_more_head_of_house);
            txtMoreHeadofHouseLabel = itemView.findViewById(R.id.txt_more_head_of_house_label);
            txtMoreHouseGhost = itemView.findViewById(R.id.txt_more_house_ghost);
            txtMoreHouseGhostLabel = itemView.findViewById(R.id.txt_more_house_ghost_label);
            txtMoreFounder = itemView.findViewById(R.id.txt_more_founder);
            txtMoreFounderLabel = itemView.findViewById(R.id.txt_more_founder_label);
            txtMoreSchool = itemView.findViewById(R.id.txt_more_school);
            txtMoreSchoolLabel = itemView.findViewById(R.id.txt_more_school_label);
            txtMoreValues = itemView.findViewById(R.id.txt_more_values);
            txtMoreValuesLabel = itemView.findViewById(R.id.txt_more_values_label);
            txtMoreColor = itemView.findViewById(R.id.txt_more_colors);
            txtMoreColorLabel = itemView.findViewById(R.id.txt_more_colors_label);
            btnLoadMore = itemView.findViewById(R.id.btn_load_more);
            houseImage = itemView.findViewById(R.id.house_img);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }

        }
    }

    JSONObject getItem(int id) {
        return homeData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
