package org.catrobat.catroid.retrofit;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/ErrorInterceptor.class
  classes14.dex
 */
/* JADX INFO: compiled from: ErrorInterceptor.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/retrofit/ErrorInterceptor;", "Lokhttp3/Interceptor;", "()V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ErrorInterceptor implements Interceptor {
    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) throws IOException {
        String string;
        Intrinsics.checkNotNullParameter(chain, "chain");
        Response responseProceed = chain.proceed(chain.request());
        if (!(!responseProceed.isSuccessful()) || !(!responseProceed.isRedirect())) {
            return responseProceed;
        }
        ResponseBody responseBodyBody = responseProceed.body();
        MediaType mediaType = responseBodyBody != null ? responseBodyBody.get$contentType() : null;
        ResponseBody responseBodyBody2 = responseProceed.body();
        if (responseBodyBody2 == null || (string = responseBodyBody2.toString()) == null) {
            string = "";
        }
        return responseProceed.newBuilder().body(ResponseBody.INSTANCE.create(mediaType, string)).code(responseProceed.code()).build();
    }
}
