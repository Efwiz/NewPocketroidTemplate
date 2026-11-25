package org.catrobat.catroid.web;

import java.io.IOException;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/CatrobatWebClientKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: CatrobatWebClient.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u0003\u001a\u0012\u0010\u0005\u001a\u00020\u0003*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0001¨\u0006\b"}, d2 = {"createFormEncodedRequest", "Lokhttp3/Request;", "", "", "url", "performCallWith", "Lokhttp3/OkHttpClient;", "request", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CatrobatWebClientKt {
    public static final String performCallWith(OkHttpClient okHttpClient, Request request) throws WebConnectionException {
        String message;
        Intrinsics.checkNotNullParameter(okHttpClient, "<this>");
        Intrinsics.checkNotNullParameter(request, "request");
        int iCode = 1002;
        try {
            Response responseExecute = okHttpClient.newCall(request).execute();
            ResponseBody responseBodyBody = responseExecute.body();
            if (responseBodyBody != null) {
                return responseBodyBody.string();
            }
            iCode = responseExecute.code();
            message = responseExecute.message();
            throw new WebConnectionException(iCode, message);
        } catch (IOException e) {
            message = e.getMessage();
            if (message == null) {
                message = "Bad Connection";
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final Request createFormEncodedRequest(Map<String, String> map, String url) {
        Intrinsics.checkNotNullParameter(map, "<this>");
        Intrinsics.checkNotNullParameter(url, "url");
        FormBody.Builder builder = new FormBody.Builder(null, 1, 0 == true ? 1 : 0);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return new Request.Builder().url(url).post(builder.build()).build();
    }
}
