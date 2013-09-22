package se.kjellstrand.volleydemo;

import java.io.File;

import android.app.Application;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by erbsman on 7/25/13.
 */
public class VolleyDemoApplication extends Application {
    
    private static final int DEFAULT_DISK_USAGE_BYTES = 10 * 1024 * 1024; // 10 Mb

    private static final String TAG = VolleyDemoApplication.class.getCanonicalName();
   
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
        
        // get a path to the internal cache dir.
        File cacheDir = getCacheDir();
        if (cacheDir == null) {
            // get a path to the external cache dir, if no internal exists.
            cacheDir = getExternalCacheDir();
        }
        
        if(cacheDir != null){            
            DNMCache imageCache = new DNMCache(cacheDir, DEFAULT_DISK_USAGE_BYTES);
            imageCache.initialize();
            mImageLoader = new ImageLoader(mRequestQueue, imageCache);
        } else {
            Toast.makeText(getApplicationContext(), "Failed to create a disk cache, exiting.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public DemoApi getApi() {
        return mApi;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
