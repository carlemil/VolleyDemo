package se.kjellstrand.volleydemo;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;

/**
 * Created by erbsman on 7/25/13.
 */
public class DaysRequest extends Request<Day[]> {

    private static final String TAG = DaysRequest.class.getCanonicalName();
    private final Response.Listener<Day[]> mListener;

    private final Gson mGson = new Gson();

    public DaysRequest(Response.Listener<Day[]> listener, Response.ErrorListener errorListener) {
        super(Method.GET, "http://app.explovia.com/api/v1/events", errorListener);
        mListener = listener;
    }

    @Override
    protected void deliverResponse(Day[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<Day[]> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        Log.d(TAG, "jsonString: " + jsonString);
        Day[] demoResponse = mGson.fromJson(jsonString, Day[].class);
        return Response.success(demoResponse, getCacheEntry());
    }
}
