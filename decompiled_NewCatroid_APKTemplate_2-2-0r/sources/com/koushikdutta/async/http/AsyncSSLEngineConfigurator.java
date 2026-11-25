package com.koushikdutta.async.http;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/* JADX INFO: loaded from: classes10.dex */
public interface AsyncSSLEngineConfigurator {
    void configureEngine(SSLEngine sSLEngine, AsyncHttpClientMiddleware.GetSocketData getSocketData, String str, int i);

    SSLEngine createEngine(SSLContext sSLContext, String str, int i);
}
