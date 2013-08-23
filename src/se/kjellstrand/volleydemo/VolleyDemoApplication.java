package se.kjellstrand.volleydemo;

import java.nio.ByteBuffer;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.Cache.Entry;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

/**
 * Created by erbsman on 7/25/13.
 */
public class VolleyDemoApplication extends Application {

    protected static final String TAG = null;

    private static VolleyDemoApplication sInstance;

    private DemoApi mApi;

    private final LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(20);

    private ImageLoader mImageLoader;

    public static VolleyDemoApplication get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        RequestQueue queue = Volley.newRequestQueue(this);
        mApi = new DemoApi(queue);

        final DiskBasedCache dbc = new DiskBasedCache(getCacheDir());

        ImageCache imageCache = new ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap bitmap) {
                if (mImageCache.get(key) == null) {
                    mImageCache.put(key, bitmap);
                    int bytes = bitmap.getByteCount();
                    // or we can calculate bytes this way. Use a different value
                    // than 4 if you don't use 32bit images.
                    // int bytes = b.getWidth()*b.getHeight()*4;
                    ByteBuffer buffer = ByteBuffer.allocate(bytes); // Create a
                                                                    // new
                                                                    // buffer
                    bitmap.copyPixelsToBuffer(buffer); // Move the byte data to
                                                       // the buffer
                    byte[] array = buffer.array();

                    Entry entry = new Entry();
                    entry.data = array;
                    dbc.put(key, entry);
                    Log.d(TAG, "Wrote bitmap to disk cache.");
                }
            }

            @Override
            public Bitmap getBitmap(String key) {
                Bitmap memBitmap = mImageCache.get(key);
                if (memBitmap != null) {
                    Log.d(TAG, "Found bitmap in mem cache.");
                    return memBitmap;
                } else {
                    Entry entry = dbc.get(key);
                    if (entry != null) {
                        Bitmap diskBitmap = BitmapFactory.decodeByteArray(entry.data, 0, entry.data.length);
                        mImageCache.put(key, diskBitmap);
                        Log.d(TAG, "Found bitmap in disk cache.");
                        return diskBitmap;
                    } else {
                        Log.d(TAG, "Bitmap not found in cache.");
                        return null;
                    }
                }
            }
        };

        mImageLoader = new ImageLoader(queue, imageCache);
    }

    public DemoApi getApi() {
        return mApi;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
