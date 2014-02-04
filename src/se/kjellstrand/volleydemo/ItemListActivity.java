package se.kjellstrand.volleydemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class ItemListActivity extends Activity {

    private Listener<Day[]> listener = new Listener<Day[]>() {

        @Override
        public void onResponse(Day[] dayList) {
            for (Day day : dayList) {
                Log.d("tag", "--- day ---");
                for (Event event : day.events) {
                    Log.d("tag", "Event subtitle: " + event.subtitle);
                    for (String image : event.images) {
                        Log.d("tag", "image: " + image + "\n");
                    }
                }
            }
        }
    };
    private ErrorListener errorListener = new ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("tag", "Error: " + error.getMessage());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VolleySingleton.getInstance().getApi().getDemoItemsPage(listener, errorListener);

    }

}
