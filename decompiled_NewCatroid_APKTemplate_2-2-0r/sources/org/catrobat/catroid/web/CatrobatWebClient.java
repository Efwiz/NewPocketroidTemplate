package org.catrobat.catroid.web;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/CatrobatWebClient.class
  classes14.dex
 */
/* JADX INFO: compiled from: CatrobatWebClient.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/web/CatrobatWebClient;", "", "()V", "client", "Lokhttp3/OkHttpClient;", "getClient", "()Lokhttp3/OkHttpClient;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CatrobatWebClient {
    public static final CatrobatWebClient INSTANCE = new CatrobatWebClient();
    private static final OkHttpClient client = new OkHttpClient.Builder().connectionSpecs(CollectionsKt.listOf(ConnectionSpec.MODERN_TLS)).build();

    private CatrobatWebClient() {
    }

    public final OkHttpClient getClient() {
        return client;
    }
}
