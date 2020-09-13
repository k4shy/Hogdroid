package za.co.kashvirsingh.hogdroid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import za.co.kashvirsingh.hogdroid.API.APIRequests;
import za.co.kashvirsingh.hogdroid.adapters.CharacterAdapter;
import za.co.kashvirsingh.hogdroid.interfaces.Callback;

import static za.co.kashvirsingh.hogdroid.Constants.CHARACTERS;
import static za.co.kashvirsingh.hogdroid.Constants.SPELLS;

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
                    houseImage.setBackgroundResource(R.drawable.gryffindor_house);
                    break;
                case "ravenclaw":
                    houseImage.setBackgroundResource(R.drawable.ravenclaw_house);
                    break;
                case "slytherin":
                    houseImage.setBackgroundResource(R.drawable.slytherin_house);
                    break;
                case "hufflepuff":
                    houseImage.setBackgroundResource(R.drawable.hufflepuff_house);
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
        }
    }

    @Override
    public void onBackPressed() {
        list = new ArrayList<>();
        adapter.notifyDataSetChanged();
    }
}