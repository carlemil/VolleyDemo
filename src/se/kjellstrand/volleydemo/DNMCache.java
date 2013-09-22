package se.kjellstrand.volleydemo;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader.ImageCache;

public class DNMCache extends DiskBasedCache implements ImageCache {

    private static final String TAG = DNMCache.class.getCanonicalName();

    private final LruCache<String, Bitmap> mImageCache =
            new LruCache<String, Bitmap>(50);

    public DNMCache(File cacheDir, int diskMaxUsage) {
        super(cacheDir, diskMaxUsage);
    }

    public DNMCache(File cacheDir) {
        super(cacheDir);
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        if (!memContains(key)) {
            putMemBitmap(key, bitmap);
            putDiskBitmap(key, bitmap);
        }
    }

    @Override
    public Bitmap getBitmap(String key) {
        Bitmap bitmap = getMemBitmap(key);
        if (bitmap == null) {
            bitmap = getDiskBitmap(key);
            if (bitmap != null) {
                putMemBitmap(key, bitmap);
            }
        }
        return bitmap;
    }

    public boolean memContains(String key) {
        return mImageCache.get(key) != null;
    }

    public Bitmap getMemBitmap(String key) {
        return mImageCache.get(key);
    }

    public void putMemBitmap(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }

    public Bitmap getDiskBitmap(String url) {
        final Entry requestedItem = get(url);
        if (requestedItem == null) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeByteArray(requestedItem.data,
                    0, requestedItem.data.length);
        } catch (OutOfMemoryError ome) {
            Log.d(TAG, "OOM.");
        }
        return bitmap;
    }

    public void putDiskBitmap(String url, Bitmap bitmap) {
        final Entry entry = new Entry();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream);
        entry.data = byteStream.toByteArray();
        put(url, entry);
    }

}
