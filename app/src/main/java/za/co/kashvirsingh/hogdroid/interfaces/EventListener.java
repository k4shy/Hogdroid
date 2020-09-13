package za.co.kashvirsingh.hogdroid.interfaces;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public interface EventListener {
    public void onEventCompleted(HashMap<String, String> result);
    public void onEventFailed();
}
