package bruno.myapplication.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by bruno on 02/01/18.
 * Definicion de la conexion con las apis. Utilizo la libreria Volley.
 */

public class ApiConnection {
    private static ApiConnection mInstance;
    private RequestQueue mRequestQueue;
    private final ImageLoader mImageLoader;
    private final Context mContext;

    private ApiConnection(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized ApiConnection getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ApiConnection(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(@NonNull Request<T> req) {
        getRequestQueue().add(req);
    }

// --Commented out by Inspection START (11/01/18 12:20):
//    public ImageLoader getImageLoader() {
//        return mImageLoader;
//    }
// --Commented out by Inspection STOP (11/01/18 12:20)
}