package za.co.kashvirsingh.hogdroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import za.co.kashvirsingh.hogdroid.adapters.CharacterAdapter;
import za.co.kashvirsingh.hogdroid.adapters.HomeRecyclerAdapter;
import za.co.kashvirsingh.hogdroid.adapters.SpellsAdapter;

import static za.co.kashvirsingh.hogdroid.Constants.CHARACTERS;
import static za.co.kashvirsingh.hogdroid.Constants.HOUSE;
import static za.co.kashvirsingh.hogdroid.Constants.SPELLS;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;

    List<JSONObject> houseList = new ArrayList<>();
    List<JSONObject> characterList = new ArrayList<>();
    List<JSONObject> spellList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                JSONArray res;
                switch (item.getItemId()) {
                    case R.id.home_menu:
                            initHouse(recyclerView,houseList,characterList);
                        Toast.makeText(MainActivity.this, "House", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.charater_menu:
 initCharacters(recyclerView,characterList);
                        Toast.makeText(MainActivity.this, "Characters", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.spell_menu:
  initSpells(recyclerView,spellList);
                        Toast.makeText(MainActivity.this, "Spells", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                return true;
            }
        });

        initLists();
        imageView = findViewById(R.id.house_img);


    }

    private void initLists(){
        JSONArray res;
        try {
            houseList = new ArrayList<>();
            res = new JSONArray(getIntent().getStringExtra(HOUSE));
            for (int i = 0; i < res.length(); i++) {
                System.out.println("HOUSE LIST " + i + " : " + res.getString(i));
                JSONObject obj = new JSONObject(res.getString(i));
                houseList.add(obj);
            }

            characterList = new ArrayList<>();
            res = new JSONArray(getIntent().getStringExtra(CHARACTERS));
            for (int i = 0; i < res.length(); i++) {
                System.out.println("Char LIST " + i + " : " + res.getString(i));
                JSONObject obj = new JSONObject(res.getString(i));
                characterList.add(obj);
            }

            spellList = new ArrayList<>();
            res = new JSONArray(getIntent().getStringExtra(SPELLS));
            for (int i = 0; i < res.length(); i++) {
                System.out.println("Spell LIST " + i + " : " + res.getString(i));
                JSONObject obj = new JSONObject(res.getString(i));
                spellList.add(obj);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMenuItem(bottomNavigationView);
    }

    private void checkMenuItem(BottomNavigationView bottomNavigationView){
        MenuItem item = bottomNavigationView.getMenu().findItem(bottomNavigationView.getSelectedItemId());
        switch (item.getItemId()) {
            case R.id.home_menu:
                houseList = new ArrayList<>();
                JSONArray res = null;
                try {
                    res = new JSONArray(getIntent().getStringExtra(HOUSE));
                    for (int i = 0; i < res.length(); i++) {
                        System.out.println("HOUSE LIST " + i + " : " + res.getString(i));
                        JSONObject obj = new JSONObject(res.getString(i));
                        houseList.add(obj);
                    }
                    initHouse(recyclerView,houseList,characterList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(MainActivity.this, "Houses", Toast.LENGTH_SHORT).show();
                break;
            case R.id.charater_menu:

                break;
            case R.id.spell_menu:

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

    }

      private void initHouse(RecyclerView recyclerView, List<JSONObject> list, List<JSONObject> characterList) {
        HomeRecyclerAdapter homeAdapter;
        homeAdapter = new HomeRecyclerAdapter(this, list, characterList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(homeAdapter);
    }

    private void initCharacters(RecyclerView recyclerView, List<JSONObject> list) {
        CharacterAdapter characterAdapter;

        characterAdapter = new CharacterAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(characterAdapter);

    }

    private void initSpells(RecyclerView recyclerView, List<JSONObject> list) {
        SpellsAdapter spellsAdapter;

        spellsAdapter = new SpellsAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(spellsAdapter);
    }

}