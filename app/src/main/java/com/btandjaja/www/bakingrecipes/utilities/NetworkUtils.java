package com.btandjaja.www.bakingrecipes.utilities;

import android.content.Context;
import android.net.Uri;

import com.btandjaja.www.bakingrecipes.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Scanner;

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
                .appendPath(context.getString(R.string.append_path));
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

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
