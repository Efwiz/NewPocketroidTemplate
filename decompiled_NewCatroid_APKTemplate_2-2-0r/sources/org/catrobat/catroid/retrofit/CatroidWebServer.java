package org.catrobat.catroid.retrofit;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/CatroidWebServer.class
  classes14.dex
 */
/* JADX INFO: compiled from: RetrofitWebServer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/catrobat/catroid/retrofit/CatroidWebServer;", "", "()V", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CatroidWebServer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final WebService getWebService(String str) {
        return INSTANCE.getWebService(str);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/CatroidWebServer$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: RetrofitWebServer.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/retrofit/CatroidWebServer$Companion;", "", "()V", "getWebService", "Lorg/catrobat/catroid/retrofit/WebService;", "baseUrl", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final WebService getWebService(String baseUrl) {
            Intrinsics.checkNotNullParameter(baseUrl, "baseUrl");
            Object objCreate = new Retrofit.Builder().baseUrl(baseUrl).client(new OkHttpClient.Builder().writeTimeout(15L, TimeUnit.SECONDS).connectionSpecs(CollectionsKt.listOf(ConnectionSpec.MODERN_TLS)).addInterceptor(new Interceptor() { // from class: org.catrobat.catroid.retrofit.CatroidWebServer$Companion$getWebService$$inlined$-addInterceptor$1
                @Override // okhttp3.Interceptor
                public final Response intercept(Interceptor.Chain chain) {
                    Intrinsics.checkNotNullParameter(chain, "chain");
                    String language = Locale.getDefault().getLanguage();
                    Request.Builder builderNewBuilder = chain.request().newBuilder();
                    Intrinsics.checkNotNull(language);
                    return chain.proceed(builderNewBuilder.addHeader("Accept-Language", language).build());
                }
            }).addInterceptor(new ErrorInterceptor()).build()).addConverterFactory(MoshiConverterFactory.create()).build().create(WebService.class);
            Intrinsics.checkNotNullExpressionValue(objCreate, "create(...)");
            return (WebService) objCreate;
        }
    }

    private CatroidWebServer() {
    }
}
