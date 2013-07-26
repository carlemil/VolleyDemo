package se.kjellstrand.volleydemo;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;

/**
 * Created by erbsman on 7/25/13.
 */
public class DemoItemDetailsRequest extends Request<DemoItem> {

    private static final String TAG = DemoItemDetailsRequest.class.getCanonicalName();
    private final Response.Listener<DemoItem> mListener;

    private final Gson mGson = new Gson();

    public DemoItemDetailsRequest(String path, Response.Listener<DemoItem> listener, Response.ErrorListener errorListener) {
        super(Method.GET, getUrl(path), errorListener);
        mListener = listener;
    }

    @Override
    protected void deliverResponse(DemoItem response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<DemoItem> parseNetworkResponse(NetworkResponse response) {
        String jsonString = new String(response.data);
        Log.d(TAG, "jsonString: " + jsonString);
        DemoItem demoResponse = mGson.fromJson(jsonString, DemoItem.class);
        return Response.success(demoResponse, getCacheEntry());
    }

    private static String getUrl(String path) {
        return "http://assignment.golgek.mobi" + path;
    }
}
