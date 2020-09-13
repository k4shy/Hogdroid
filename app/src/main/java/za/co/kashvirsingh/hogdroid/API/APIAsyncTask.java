package za.co.kashvirsingh.hogdroid.API;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

import za.co.kashvirsingh.hogdroid.interfaces.EventListener;

import static za.co.kashvirsingh.hogdroid.Constants.API_KEY;
import static za.co.kashvirsingh.hogdroid.Constants.CHARACTERS;
import static za.co.kashvirsingh.hogdroid.Constants.HOUSE;
import static za.co.kashvirsingh.hogdroid.Constants.SPELLS;
import static za.co.kashvirsingh.hogdroid.Constants.urlCHARACTERS;
import static za.co.kashvirsingh.hogdroid.Constants.urlHOUSE;
import static za.co.kashvirsingh.hogdroid.Constants.urlSPELLS;

public class APIAsyncTask extends AsyncTask<Void, Void, HashMap<String, String>> {

    private String TAG = APIRequests.class.getSimpleName();

    RequestQueue queue;
    String baseURL = "https://www.potterapi.com/v1";
    String key = "?key="+API_KEY;

    private EventListener callback;
    private Context context;
    private String type;

    public APIAsyncTask(EventListener cb, Context context, String type) {
        callback = cb;
        this.context = context;
        this.type = type;
    }

    @Override
    protected HashMap<String, String> doInBackground(Void... voids) {
        String url =  "";
        queue = Volley.newRequestQueue(context);


        switch (type.toUpperCase()) {
            case HOUSE:
                String houseUrl = baseURL + urlHOUSE+key;
                StringRequest houseRequest = new StringRequest(Request.Method.GET, houseUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put(HOUSE,response);
                        callback.onEventCompleted(map);                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getLocalizedMessage() + "");
                    }
                });
                queue.add(houseRequest);
                break;
            case CHARACTERS:
                String charactersUrl = baseURL + urlCHARACTERS+key;
                StringRequest charactersRequest = new StringRequest(Request.Method.GET, charactersUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put(CHARACTERS,response);
                        callback.onEventCompleted(map);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getLocalizedMessage() + "");
                    }
                });
                        queue.add(charactersRequest);

                break;
            case SPELLS:
                String spellsUrl = baseURL + urlSPELLS+key;
                StringRequest spellsRequest = new StringRequest(Request.Method.GET, spellsUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put(SPELLS,response);
                        callback.onEventCompleted(map);            }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getLocalizedMessage() + "");
                    }
                });
                        queue.add(spellsRequest);

                break;

        }


        return null;
    }

    @Override
    protected void onPostExecute(HashMap<String, String> result) {
        if(callback != null) {
            callback.onEventCompleted(result);
        }
    }

}
