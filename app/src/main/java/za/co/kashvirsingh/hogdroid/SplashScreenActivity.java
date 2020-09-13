package za.co.kashvirsingh.hogdroid;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import za.co.kashvirsingh.hogdroid.API.APIAsyncTask;
import za.co.kashvirsingh.hogdroid.interfaces.EventListener;

import static za.co.kashvirsingh.hogdroid.Constants.CHARACTERS;
import static za.co.kashvirsingh.hogdroid.Constants.HOUSE;
import static za.co.kashvirsingh.hogdroid.Constants.SPELLS;

public class SplashScreenActivity extends AppCompatActivity implements EventListener {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(this, MainActivity.class);
        new APIAsyncTask(this,getApplicationContext(),HOUSE).execute();
        new APIAsyncTask(this,getApplicationContext(),CHARACTERS).execute();
        new APIAsyncTask(this,getApplicationContext(),SPELLS).execute();
    }

    @Override
    public void onEventCompleted(HashMap<String, String> result) {
        if(result != null && result.size()>0) {
                for (String key : result.keySet()) {
                    intent.putExtra(key, result.get(key));
                }
                if(intent.getExtras()!= null && (intent.getExtras().size() > 0 && intent.getExtras().size() == 3)){
                    startActivity(intent);
                    finish();
                }
        }
    }

    @Override
    public void onEventFailed() {

    }
}
