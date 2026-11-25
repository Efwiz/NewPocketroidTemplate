package io.noties.markwon.image.network;

import android.net.Uri;
import io.noties.markwon.image.ImageItem;
import io.noties.markwon.image.SchemeHandler;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

/* JADX INFO: loaded from: classes11.dex */
public class NetworkSchemeHandler extends SchemeHandler {
    public static final String SCHEME_HTTP = "http";
    public static final String SCHEME_HTTPS = "https";

    public static NetworkSchemeHandler create() {
        return new NetworkSchemeHandler();
    }

    NetworkSchemeHandler() {
    }

    @Override // io.noties.markwon.image.SchemeHandler
    public ImageItem handle(String str, Uri uri) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode >= 200 && responseCode < 300) {
                return ImageItem.withDecodingNeeded(contentType(httpURLConnection.getHeaderField("Content-Type")), new BufferedInputStream(httpURLConnection.getInputStream()));
            }
            throw new IOException("Bad response code: " + responseCode + ", url: " + str);
        } catch (IOException e) {
            throw new IllegalStateException("Exception obtaining network resource: " + str, e);
        }
    }

    @Override // io.noties.markwon.image.SchemeHandler
    public Collection<String> supportedSchemes() {
        return Arrays.asList("http", "https");
    }

    static String contentType(String str) {
        if (str == null) {
            return null;
        }
        int iIndexOf = str.indexOf(59);
        return iIndexOf > -1 ? str.substring(0, iIndexOf) : str;
    }
}
