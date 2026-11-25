package com.koushikdutta.async.http;

import android.net.Uri;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncHttpPost extends AsyncHttpRequest {
    public static final String METHOD = "POST";

    public AsyncHttpPost(String str) {
        this(Uri.parse(str));
    }

    public AsyncHttpPost(Uri uri) {
        super(uri, "POST");
    }
}
