package org.catrobat.catroid.web;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Okio__JvmOkioKt;
import org.catrobat.catroid.common.Constants;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/CatrobatServerCalls.class
  classes14.dex
 */
/* JADX INFO: compiled from: CatrobatServerCalls.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001:\u0003&'(B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J'\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\u001e\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0006J\u0006\u0010\u0013\u001a\u00020\tJ.\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0010\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0006H\u0002J\u0010\u0010 \u001a\u00020\u00062\b\u0010!\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\"\u001a\u00020\u00062\b\u0010!\u001a\u0004\u0018\u00010\u0006J\u000e\u0010#\u001a\u00020\t2\u0006\u0010$\u001a\u00020\u0006J\u000e\u0010%\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/catrobat/catroid/web/CatrobatServerCalls;", "", "okHttpClient", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;)V", "tag", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "checkOAuthToken", "", "id", "oauthProvider", "context", "Landroid/content/Context;", "(Ljava/lang/String;Ljava/lang/String;Landroid/content/Context;)Ljava/lang/Boolean;", "checkToken", "token", "username", "baseUrl", "deleteTestUserAccountsOnServer", "downloadProject", "", "url", FirebaseAnalytics.Param.DESTINATION, "Ljava/io/File;", "successCallback", "Lorg/catrobat/catroid/web/CatrobatServerCalls$DownloadSuccessCallback;", "errorCallback", "Lorg/catrobat/catroid/web/CatrobatServerCalls$DownloadErrorCallback;", "progressCallback", "Lorg/catrobat/catroid/web/CatrobatServerCalls$DownloadProgressCallback;", "getRequest", "getSurvey", "language", "getTags", "isEMailAvailable", "email", "isUserNameAvailable", "DownloadErrorCallback", "DownloadProgressCallback", "DownloadSuccessCallback", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CatrobatServerCalls {
    private final OkHttpClient okHttpClient;
    private final String tag;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/CatrobatServerCalls$DownloadErrorCallback.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CatrobatServerCalls.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/web/CatrobatServerCalls$DownloadErrorCallback;", "", "onError", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", CrashHianalyticsData.MESSAGE, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface DownloadErrorCallback {
        void onError(int code, String message);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/CatrobatServerCalls$DownloadProgressCallback.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CatrobatServerCalls.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/web/CatrobatServerCalls$DownloadProgressCallback;", "", "onProgress", "", "progress", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface DownloadProgressCallback {
        void onProgress(long progress);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/CatrobatServerCalls$DownloadSuccessCallback.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CatrobatServerCalls.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lorg/catrobat/catroid/web/CatrobatServerCalls$DownloadSuccessCallback;", "", "onSuccess", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface DownloadSuccessCallback {
        void onSuccess();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CatrobatServerCalls() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public CatrobatServerCalls(OkHttpClient okHttpClient) {
        Intrinsics.checkNotNullParameter(okHttpClient, "okHttpClient");
        this.okHttpClient = okHttpClient;
        this.tag = "CatrobatServerCalls";
    }

    public /* synthetic */ CatrobatServerCalls(OkHttpClient okHttpClient, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CatrobatWebClient.INSTANCE.getClient() : okHttpClient);
    }

    public final boolean checkToken(String token, String username, String baseUrl) throws WebConnectionException {
        Intrinsics.checkNotNullParameter(token, "token");
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(baseUrl, "baseUrl");
        try {
            HashMap map = new HashMap();
            map.put("token", token);
            map.put("username", username);
            JSONObject jSONObject = new JSONObject(CatrobatWebClientKt.performCallWith(this.okHttpClient, CatrobatWebClientKt.createFormEncodedRequest(map, baseUrl + ServerAuthenticationConstants.CHECK_TOKEN_URL)));
            int i = jSONObject.getInt("statusCode");
            String strOptString = jSONObject.optString(ServerAuthenticationConstants.JSON_ANSWER);
            Integer num = ServerAuthenticationConstants.SERVER_RESPONSE_TOKEN_OK;
            if (num == null || i != num.intValue()) {
                throw new WebConnectionException(i, "server response token ok, but error: " + strOptString);
            }
            return true;
        } catch (JSONException e) {
            throw new WebConnectionException(1001, Log.getStackTraceString(e));
        }
    }

    private final String getRequest(String url) throws WebConnectionException {
        return CatrobatWebClientKt.performCallWith(this.okHttpClient, new Request.Builder().url(url).build());
    }

    public final String getTags(String language) {
        String str = ServerAuthenticationConstants.FILE_TAG_URL_HTTP;
        if (language != null) {
            try {
                str = "https://t.me/NewCatroidDevsapi/tags/getTags.json?language=" + language;
            } catch (WebConnectionException e) {
                Log.e(this.tag, Log.getStackTraceString(e));
                return "";
            }
        }
        return getRequest(str);
    }

    public final String getSurvey(String language) {
        String str = ServerAuthenticationConstants.FILE_SURVEY_URL_HTTP;
        if (language != null) {
            try {
                str = ServerAuthenticationConstants.FILE_SURVEY_URL_HTTP + language;
            } catch (WebConnectionException e) {
                Log.e(this.tag, Log.getStackTraceString(e));
                return "";
            }
        }
        return getRequest(str);
    }

    public final Boolean checkOAuthToken(String id2, String oauthProvider, Context context) throws WebConnectionException {
        String stackTraceString;
        int i;
        Intrinsics.checkNotNullParameter(id2, "id");
        Intrinsics.checkNotNullParameter(oauthProvider, "oauthProvider");
        try {
            HashMap map = new HashMap();
            map.put("id", id2);
            if (!Intrinsics.areEqual(oauthProvider, Constants.GOOGLE_PLUS)) {
                throw new WebConnectionException(-1, "OAuth provider not supported!");
            }
            stackTraceString = CatrobatWebClientKt.performCallWith(this.okHttpClient, CatrobatWebClientKt.createFormEncodedRequest(map, ServerAuthenticationConstants.CHECK_GOOGLE_TOKEN_URL));
            JSONObject jSONObject = new JSONObject(stackTraceString);
            i = jSONObject.getInt("statusCode");
            Integer num = ServerAuthenticationConstants.SERVER_RESPONSE_TOKEN_OK;
            if (num != null && i == num.intValue()) {
                String strOptString = jSONObject.optString("email");
                String strOptString2 = jSONObject.optString("username");
                boolean z = jSONObject.getBoolean(ServerAuthenticationConstants.OAUTH_TOKEN_AVAILABLE);
                if (z && Intrinsics.areEqual(oauthProvider, Constants.GOOGLE_PLUS)) {
                    PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Constants.GOOGLE_USERNAME, strOptString2).putString(Constants.GOOGLE_EMAIL, strOptString).apply();
                }
                return Boolean.valueOf(z);
            }
            throw new WebConnectionException(i, stackTraceString);
        } catch (JSONException e) {
            stackTraceString = Log.getStackTraceString(e);
            Intrinsics.checkNotNullExpressionValue(stackTraceString, "getStackTraceString(...)");
            i = 1001;
        }
    }

    public final boolean isEMailAvailable(String email) throws WebConnectionException {
        Intrinsics.checkNotNullParameter(email, "email");
        try {
            HashMap map = new HashMap();
            map.put("email", email);
            String strPerformCallWith = CatrobatWebClientKt.performCallWith(this.okHttpClient, CatrobatWebClientKt.createFormEncodedRequest(map, ServerAuthenticationConstants.CHECK_EMAIL_AVAILABLE_URL));
            JSONObject jSONObject = new JSONObject(strPerformCallWith);
            int i = jSONObject.getInt("statusCode");
            Integer num = ServerAuthenticationConstants.SERVER_RESPONSE_TOKEN_OK;
            if (num == null || i != num.intValue()) {
                throw new WebConnectionException(i, strPerformCallWith);
            }
            return jSONObject.getBoolean(ServerAuthenticationConstants.EMAIL_AVAILABLE);
        } catch (JSONException e) {
            throw new WebConnectionException(1001, Log.getStackTraceString(e));
        }
    }

    public final boolean isUserNameAvailable(String username) throws WebConnectionException {
        Intrinsics.checkNotNullParameter(username, "username");
        try {
            HashMap map = new HashMap();
            map.put("username", username);
            String strPerformCallWith = CatrobatWebClientKt.performCallWith(this.okHttpClient, CatrobatWebClientKt.createFormEncodedRequest(map, ServerAuthenticationConstants.CHECK_USERNAME_AVAILABLE_URL));
            JSONObject jSONObject = new JSONObject(strPerformCallWith);
            int i = jSONObject.getInt("statusCode");
            Integer num = ServerAuthenticationConstants.SERVER_RESPONSE_TOKEN_OK;
            if (num == null || i != num.intValue()) {
                throw new WebConnectionException(i, strPerformCallWith);
            }
            return jSONObject.getBoolean(ServerAuthenticationConstants.USERNAME_AVAILABLE);
        } catch (JSONException e) {
            Log.e(this.tag, Log.getStackTraceString(e));
            throw new WebConnectionException(1001, "");
        }
    }

    public final boolean deleteTestUserAccountsOnServer() throws WebConnectionException {
        try {
            String request = getRequest("");
            int i = new JSONObject(request).getInt("statusCode");
            Integer num = ServerAuthenticationConstants.SERVER_RESPONSE_TOKEN_OK;
            if (num == null || i != num.intValue()) {
                throw new WebConnectionException(i, request);
            }
            return true;
        } catch (JSONException e) {
            throw new WebConnectionException(1001, Log.getStackTraceString(e));
        }
    }

    public final void downloadProject(String url, File destination, DownloadSuccessCallback successCallback, DownloadErrorCallback errorCallback, final DownloadProgressCallback progressCallback) {
        OkHttpClient okHttpClientBuild;
        Intrinsics.checkNotNullParameter(url, "url");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(successCallback, "successCallback");
        Intrinsics.checkNotNullParameter(errorCallback, "errorCallback");
        Intrinsics.checkNotNullParameter(progressCallback, "progressCallback");
        Request requestBuild = new Request.Builder().url(url).build();
        OkHttpClient.Builder builderNewBuilder = this.okHttpClient.newBuilder();
        builderNewBuilder.networkInterceptors().add(new Interceptor() { // from class: org.catrobat.catroid.web.CatrobatServerCalls$$ExternalSyntheticLambda0
            @Override // okhttp3.Interceptor
            public final Response intercept(Interceptor.Chain chain) {
                return CatrobatServerCalls.downloadProject$lambda$0(progressCallback, chain);
            }
        });
        if (StringsKt.startsWith$default(url, "http://", false, 2, (Object) null)) {
            okHttpClientBuild = builderNewBuilder.connectionSpecs(CollectionsKt.listOf(ConnectionSpec.CLEARTEXT)).build();
        } else {
            okHttpClientBuild = builderNewBuilder.build();
        }
        try {
            Response responseExecute = okHttpClientBuild.newCall(requestBuild).execute();
            if (responseExecute.isSuccessful()) {
                BufferedSink bufferedSinkBuffer = Okio.buffer(Okio__JvmOkioKt.sink$default(destination, false, 1, null));
                ResponseBody responseBodyBody = responseExecute.body();
                if (responseBodyBody != null) {
                    bufferedSinkBuffer.writeAll(responseBodyBody.get$this_asResponseBody());
                }
                bufferedSinkBuffer.close();
                successCallback.onSuccess();
                return;
            }
            Log.v(this.tag, "Download not successful");
            errorCallback.onError(responseExecute.code(), "Download failed! HTTP Status code was " + responseExecute.code());
        } catch (IOException e) {
            Log.e(this.tag, Log.getStackTraceString(e));
            errorCallback.onError(1002, "I/O Exception");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Response downloadProject$lambda$0(DownloadProgressCallback progressCallback, Interceptor.Chain chain) throws IOException {
        Intrinsics.checkNotNullParameter(progressCallback, "$progressCallback");
        Intrinsics.checkNotNullParameter(chain, "chain");
        Response responseProceed = chain.proceed(chain.request());
        return responseProceed.newBuilder().body(new ProgressResponseBody(responseProceed.body(), progressCallback)).build();
    }
}
