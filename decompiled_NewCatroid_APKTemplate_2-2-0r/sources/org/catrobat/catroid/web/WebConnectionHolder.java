package org.catrobat.catroid.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import okhttp3.ConnectionSpec;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/WebConnectionHolder.class
  classes14.dex
 */
/* JADX INFO: compiled from: WebConnectionHolder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0005J\u0006\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0015\u001a\u00020\u00142\b\u0010\u0012\u001a\u0004\u0018\u00010\u0005R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/web/WebConnectionHolder;", "", "()V", "connections", "", "Lorg/catrobat/catroid/web/WebConnection;", "getConnections", "()Ljava/util/List;", "setConnections", "(Ljava/util/List;)V", "okHttpClient", "Lokhttp3/OkHttpClient;", "getOkHttpClient", "()Lokhttp3/OkHttpClient;", "setOkHttpClient", "(Lokhttp3/OkHttpClient;)V", "addConnection", "", "connection", "onPause", "", "removeConnection", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WebConnectionHolder {
    private static final int MAX_CONNECTIONS = 10;
    private static final long TIMEOUT_DURATION = 60;
    private List<WebConnection> connections = new ArrayList(10);
    private OkHttpClient okHttpClient;

    /* JADX WARN: Multi-variable type inference failed */
    public WebConnectionHolder() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(null, 1, 0 == true ? 1 : 0);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClientBuild = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).connectTimeout(60L, TimeUnit.SECONDS).readTimeout(60L, TimeUnit.SECONDS).writeTimeout(60L, TimeUnit.SECONDS).connectionSpecs(CollectionsKt.listOf((Object[]) new ConnectionSpec[]{ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT})).dispatcher(new Dispatcher()).build();
        this.okHttpClient = okHttpClientBuild;
        okHttpClientBuild.dispatcher().setMaxRequests(10);
        this.okHttpClient.dispatcher().setMaxRequestsPerHost(10);
    }

    public final List<WebConnection> getConnections() {
        return this.connections;
    }

    public final void setConnections(List<WebConnection> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.connections = list;
    }

    public final OkHttpClient getOkHttpClient() {
        return this.okHttpClient;
    }

    public final void setOkHttpClient(OkHttpClient okHttpClient) {
        Intrinsics.checkNotNullParameter(okHttpClient, "<set-?>");
        this.okHttpClient = okHttpClient;
    }

    public final synchronized void onPause() {
        Iterator<T> it = this.connections.iterator();
        while (it.hasNext()) {
            ((WebConnection) it.next()).cancelCall();
        }
        this.connections.clear();
    }

    public final synchronized boolean addConnection(WebConnection connection) {
        boolean z;
        Intrinsics.checkNotNullParameter(connection, "connection");
        if (this.connections.size() < 10) {
            this.connections.add(connection);
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public final synchronized void removeConnection(WebConnection connection) {
        TypeIntrinsics.asMutableCollection(this.connections).remove(connection);
    }
}
