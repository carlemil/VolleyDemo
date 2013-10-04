package se.kjellstrand.volleydemo;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by erbsman on 7/25/13.
 */
public class VolleyDemoApplication extends Application {
   
    private static VolleyDemoApplication sInstance;

    private DemoApi mApi;
    
    private ImageLoader mImageLoader;

    private RequestQueue mRequestQueue;

    public static VolleyDemoApplication get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

		mRequestQueue = Volley.newRequestQueue(this);
		mApi = new DemoApi(mRequestQueue);

		BitmapLruCache imageCache = new BitmapLruCache();
		mImageLoader = new ImageLoader(mRequestQueue, imageCache);
	}

    public DemoApi getApi() {
        return mApi;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
