package se.kjellstrand.volleydemo;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;

/**
 * Created by erbsman on 7/25/13.
 */
public class DemoApi {

    private final RequestQueue mQueue;

    public DemoApi(RequestQueue queue) {
        mQueue = queue;
    }

    @SuppressWarnings("unchecked")
    public Request<Day[]> getDemoItemsPage(Listener<Day[]> listener,
                             Response.ErrorListener errorListener) {
        return mQueue.add(new DaysRequest(listener, errorListener));
    }

}
