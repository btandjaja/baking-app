package com.btandjaja.www.bakingrecipes.utilities;

import android.content.Context;
import android.net.Uri;

import com.btandjaja.www.bakingrecipes.R;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class NetworkUtils {

    /**
     * Builds the URL used to query movie.
     *
     * @return The URL to use to query the movie server.
     */
    public static URL buildUrl(Context context) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(context.getString(R.string.scheme))
                .authority(context.getString(R.string.authority))
                .appendPath(context.getString(R.string.append_path_1))
                .appendPath(context.getString(R.string.append_path_2))
                .appendPath(context.getString(R.string.append_path_3))
                .appendPath(context.getString(R.string.append_path_4))
                .appendPath(context.getString(R.string.append_path_5));
        URL url = null;
        try {
            url = new URL(URLDecoder.decode(builder.build().toString(), "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
