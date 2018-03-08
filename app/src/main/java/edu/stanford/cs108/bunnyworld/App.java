package edu.stanford.cs108.bunnyworld;

import android.app.Application;
import android.content.Context;

/**
 * Context for BunnyWorldDB
 */

/**
 * Code Source: https://stackoverflow.com/questions/4391720/how-can-i-get-a-resource-content-from-a-static-context
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
