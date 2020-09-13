package za.co.kashvirsingh.hogdroid.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;

import za.co.kashvirsingh.hogdroid.interfaces.Callback;

import static za.co.kashvirsingh.hogdroid.Constants.API_KEY;
import static za.co.kashvirsingh.hogdroid.Constants.CHARACTERS;
import static za.co.kashvirsingh.hogdroid.Constants.HOUSE;
import static za.co.kashvirsingh.hogdroid.Constants.SPELLS;
import static za.co.kashvirsingh.hogdroid.Constants.urlCHARACTERS;
import static za.co.kashvirsingh.hogdroid.Constants.urlHOUSE;
import static za.co.kashvirsingh.hogdroid.Constants.urlSPELLS;

public class APIRequests {

    private String TAG = APIRequests.class.getSimpleName();

    RequestQueue queue;
    String url = "https://www.google.com";
    String baseURL = "https://www.potterapi.com/v1";
    String key = "?key="+API_KEY;

    HashMap<String, String> params;


    public APIRequests() {

    }

    public JSONArray sendRequest(Context context, String type, final Callback callback) {
        String url = baseURL + "";
        final JSONArray[] resArray = {null};
        final String[] res = {""};
        queue = Volley.newRequestQueue(context);


        switch (type.toUpperCase()) {
            case HOUSE:
                url = url + urlHOUSE+key;
                break;
            case CHARACTERS:
                url = url + urlCHARACTERS+key;
                break;
            case SPELLS:
                url = url + urlSPELLS+key;
                break;

        }



        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        callback.onSuccessResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getLocalizedMessage() + "");
            }
        });

        Log.e(TAG, url);
        queue.add(stringRequest);
        return resArray[0];

    }
}
