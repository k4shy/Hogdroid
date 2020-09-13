package za.co.kashvirsingh.hogdroid;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import za.co.kashvirsingh.hogdroid.Utils.DevTools;
import za.co.kashvirsingh.hogdroid.adapters.CharacterAdapter;
import za.co.kashvirsingh.hogdroid.adapters.DialogDetailAdapter;
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
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_menu:
                        initHouse(recyclerView, houseList, characterList);
                        break;
                    case R.id.charater_menu:
                        initCharacters(recyclerView, characterList);
                        break;
                    case R.id.spell_menu:
                        initSpells(recyclerView, spellList);
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
                JSONObject obj = new JSONObject(res.getString(i));
                houseList.add(obj);
            }

            characterList = new ArrayList<>();
            res = new JSONArray(getIntent().getStringExtra(CHARACTERS));
            for (int i = 0; i < res.length(); i++) {
                JSONObject obj = new JSONObject(res.getString(i));
                characterList.add(obj);
            }

            spellList = new ArrayList<>();
            res = new JSONArray(getIntent().getStringExtra(SPELLS));
            for (int i = 0; i < res.length(); i++) {
                JSONObject obj = new JSONObject(res.getString(i));
                spellList.add(obj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMenuItem(bottomNavigationView);
    }

    private void checkMenuItem(BottomNavigationView bottomNavigationView) {
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

        characterAdapter.setOnItemClickListener(new CharacterAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, String id) {
                for (JSONObject character : characterList) {
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

    private void initSpells(RecyclerView recyclerView, List<JSONObject> list) {
        SpellsAdapter spellsAdapter;

        spellsAdapter = new SpellsAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(spellsAdapter);

        spellsAdapter.setOnItemClickListener(new SpellsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, String id) {
                for (JSONObject spell : spellList) {
                    try {
                        String spellID = spell.getString("_id");
                        if (spellID.equals(id)) {
                            final Dialog dialog = new Dialog(view.getContext());
                            dialog.setContentView(R.layout.dialog_layout);
                            dialog.setTitle(spell.getString("spell") + " Info");
                            ListView myNames = dialog.findViewById(R.id.List);
                            ArrayList<String> spellList = new ArrayList<>();
                            Iterator<String> keys = spell.keys();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                if (key.equals("_id") || key.equals("__v")) {
                                    continue;
                                } else {
                                    spellList.add(DevTools.splitCamelCase(key.substring(0, 1).toUpperCase() + key.substring(1)) + ":" + spell.getString(key));
                                }
                            }

                            DialogDetailAdapter adapter = new DialogDetailAdapter(view.getContext(), R.layout.dialog_info_item, spellList);
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