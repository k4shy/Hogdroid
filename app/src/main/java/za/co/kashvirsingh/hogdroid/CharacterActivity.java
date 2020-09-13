package za.co.kashvirsingh.hogdroid;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import za.co.kashvirsingh.hogdroid.API.APIRequests;
import za.co.kashvirsingh.hogdroid.Utils.DevTools;
import za.co.kashvirsingh.hogdroid.adapters.CharacterAdapter;
import za.co.kashvirsingh.hogdroid.adapters.DialogDetailAdapter;
import za.co.kashvirsingh.hogdroid.interfaces.Callback;

import static za.co.kashvirsingh.hogdroid.Constants.CHARACTERS;

public class CharacterActivity extends AppCompatActivity {
    CharacterAdapter adapter;
    List<JSONObject> list = new ArrayList<>();
    ImageView houseImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_list);

        houseImage = findViewById(R.id.house_img_char);
        RecyclerView recyclerView = findViewById(R.id.character_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String house = getIntent().getStringExtra("house_name");
        if (house.length() > 0) {
            switch (house.toLowerCase()) {
                case "gryffindor":
                    houseImage.setImageResource(R.drawable.gryffindor_house);
                    break;
                case "ravenclaw":
                    houseImage.setImageResource(R.drawable.ravenclaw_house);
                    break;
                case "slytherin":
                    houseImage.setImageResource(R.drawable.slytherin_house);
                    break;
                case "hufflepuff":
                    houseImage.setImageResource(R.drawable.hufflepuff_house);
                    break;
            }

            try {
                JSONArray res = new JSONArray(getIntent().getStringExtra("members"));
                for (int i = 0; i < res.length(); i++) {
                    JSONObject obj = new JSONObject(res.getString(i));
                    list.add(obj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                if (getIntent().getStringExtra("TYPE").equals(CHARACTERS)) {
                    new APIRequests().sendRequest(this, "characters", new Callback() {
                        @Override
                        public void onSuccessResponse(String result) {
                            try {
                                JSONArray responseResult = new JSONArray(result);
                                for (int i = 0; i < responseResult.length(); i++) {
                                    JSONObject obj = new JSONObject(responseResult.getString(i));
                                    list.add(obj);
                                }
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.getMessage();
                            }

                        }
                    });
                }
            }

            adapter = new CharacterAdapter(this, list);
            recyclerView.setAdapter(adapter);


            adapter.setOnItemClickListener(new CharacterAdapter.ItemClickListener() {
                @Override
                public void onItemClick(View view, String id) {
                    for (JSONObject character : list) {
                        try {
                            String characterID = character.getString("_id");
                            if (characterID.equals(id)) {
                                final Dialog dialog = new Dialog(view.getContext());
                                dialog.setContentView(R.layout.dialog_layout);
                                dialog.setTitle(character.getString("name") + " Info");
                                ListView myNames = dialog.findViewById(R.id.List);
                                ArrayList<String> characterList = new ArrayList<>();
                                Iterator<String> keys = character.keys();
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    if (key.equals("_id") || key.equals("__v")) {
                                        continue;
                                    } else {
                                        characterList.add(DevTools.splitCamelCase(key.substring(0, 1).toUpperCase() + key.substring(1)) + ":" + character.getString(key));

                                    }
                                }
                                if (characterList.size() > 1) {
                                    characterList.remove(characterList.size() - 1);
                                }
                                DialogDetailAdapter adapter = new DialogDetailAdapter(view.getContext(), R.layout.dialog_info_item, characterList);
                                myNames.setAdapter(adapter);
                                dialog.show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

        }
    }
}