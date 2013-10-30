package se.kjellstrand.volleydemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

/**
 * Created by erbsman on 7/25/13.
 */
public class VolleyDemoApplication extends Application {

    private static VolleyDemoApplication sInstance;

    public static VolleyDemoApplication get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        int cacheSize = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass() / 8;
        VolleySingleton.initialize(getApplicationContext(), cacheSize);

    }

}
