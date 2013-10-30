package se.kjellstrand.volleydemo;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private BitmapLruCache mCache;
    private DemoApi mApi;

    private VolleySingleton(Context context, int cacheSize) {
        mRequestQueue = Volley.newRequestQueue(context);
        mCache = new BitmapLruCache(cacheSize);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
        mApi = new DemoApi(mRequestQueue);

    }

    public static VolleySingleton getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("Did you call VolleySingleton.initialize()?");
        }
        return mInstance;
    }

    public static VolleySingleton initialize(Context context, int cacheSize) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context, cacheSize);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }

    public DemoApi getApi() {
        return mApi;
    }

}
