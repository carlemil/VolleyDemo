package se.kjellstrand.volleydemo;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageCache {

    public BitmapLruCache(int sizeInBytes) {
        // Div by 1024 to make the size a smaller number, giving space for
        // larger max size of the cache.
        super(sizeInBytes / 1024);
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        // Div by 1024 to make the size a smaller number, giving space for
        // larger max size of the cache.
        int size = bitmap.getRowBytes() * bitmap.getHeight() / 1024;
        return size;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Bitmap getBitmap(String key) {
        Bitmap bitmap = get(key);
        return bitmap;
    }

    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}