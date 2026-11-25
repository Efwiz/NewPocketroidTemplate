package io.noties.markwon.image.network;

import android.net.Uri;
import io.noties.markwon.image.ImageItem;
import io.noties.markwon.image.SchemeHandler;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: loaded from: classes11.dex */
public class OkHttpNetworkSchemeHandler extends SchemeHandler {
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private final Call.Factory factory;

    public static OkHttpNetworkSchemeHandler create() {
        return create(new OkHttpClient());
    }

    public static OkHttpNetworkSchemeHandler create(OkHttpClient okHttpClient) {
        return create((Call.Factory) okHttpClient);
    }

    public static OkHttpNetworkSchemeHandler create(Call.Factory factory) {
        return new OkHttpNetworkSchemeHandler(factory);
    }

    OkHttpNetworkSchemeHandler(Call.Factory factory) {
        this.factory = factory;
    }

    @Override // io.noties.markwon.image.SchemeHandler
    public ImageItem handle(String str, Uri uri) {
        try {
            Response responseExecute = this.factory.newCall(new Request.Builder().url(str).tag(str).build()).execute();
            if (responseExecute == null) {
                throw new IllegalStateException("Could not obtain network response: " + str);
            }
            ResponseBody responseBodyBody = responseExecute.body();
            InputStream inputStreamByteStream = responseBodyBody != null ? responseBodyBody.byteStream() : null;
            if (inputStreamByteStream == null) {
                throw new IllegalStateException("Response does not contain body: " + str);
            }
            return ImageItem.withDecodingNeeded(NetworkSchemeHandler.contentType(responseExecute.header("Content-Type")), inputStreamByteStream);
        } catch (Throwable th) {
            throw new IllegalStateException("Exception obtaining network resource: " + str, th);
        }
    }

    @Override // io.noties.markwon.image.SchemeHandler
    public Collection<String> supportedSchemes() {
        return Arrays.asList("http", "https");
    }
}
