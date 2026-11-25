package org.catrobat.catroid.web;

import android.content.SharedPreferences;
import android.util.Log;
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.OkHttpClient;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.eclipse.jgit.transport.AmazonS3;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ServerAuthenticator.class
  classes14.dex
 */
/* JADX INFO: compiled from: ServerAuthenticator.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001,B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#H\u0007J\u0006\u0010$\u001a\u00020%J\u001e\u0010&\u001a\u00020%2\u0006\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u0003J\u0018\u0010*\u001a\u00020%2\u0006\u0010+\u001a\u00020\u00032\u0006\u0010 \u001a\u00020!H\u0007R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R(\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00138\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001a\u001a\n \u001b*\u0004\u0018\u00010\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000f\"\u0004\b\u001d\u0010\u0011¨\u0006-"}, d2 = {"Lorg/catrobat/catroid/web/ServerAuthenticator;", "", "username", "", AmazonS3.Keys.PASSWORD, "token", "okHttpClient", "Lokhttp3/OkHttpClient;", "baseUrl", "sharedPreferences", "Landroid/content/SharedPreferences;", "taskListener", "Lorg/catrobat/catroid/web/ServerAuthenticator$TaskListener;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lokhttp3/OkHttpClient;Ljava/lang/String;Landroid/content/SharedPreferences;Lorg/catrobat/catroid/web/ServerAuthenticator$TaskListener;)V", "getPassword", "()Ljava/lang/String;", "setPassword", "(Ljava/lang/String;)V", "postValues", "Ljava/util/HashMap;", "getPostValues$annotations", "()V", "getPostValues", "()Ljava/util/HashMap;", "getSharedPreferences", "()Landroid/content/SharedPreferences;", "tag", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getUsername", "setUsername", "isInvalidResponse", "", "acceptedStatusCode", "", "resultJsonObject", "Lorg/json/JSONObject;", "performCatrobatLogin", "", "performCatrobatRegister", Constants.EXTRA_USER_EMAIL, "language", "country", "performTask", "serverUrl", "TaskListener", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ServerAuthenticator {
    private final String baseUrl;
    private final OkHttpClient okHttpClient;
    private String password;
    private final HashMap<String, String> postValues;
    private final SharedPreferences sharedPreferences;
    private final String tag;
    private final TaskListener taskListener;
    private final String token;
    private String username;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/web/ServerAuthenticator$TaskListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ServerAuthenticator.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\b\u0010\b\u001a\u00020\u0003H&¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/web/ServerAuthenticator$TaskListener;", "", "onError", "", "statusCode", "", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "", "onSuccess", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface TaskListener {
        void onError(int statusCode, String errorMessage);

        void onSuccess();
    }

    public static /* synthetic */ void getPostValues$annotations() {
    }

    public ServerAuthenticator(String username, String password, String token, OkHttpClient okHttpClient, String baseUrl, SharedPreferences sharedPreferences, TaskListener taskListener) {
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(password, "password");
        Intrinsics.checkNotNullParameter(token, "token");
        Intrinsics.checkNotNullParameter(okHttpClient, "okHttpClient");
        Intrinsics.checkNotNullParameter(baseUrl, "baseUrl");
        Intrinsics.checkNotNullParameter(sharedPreferences, "sharedPreferences");
        Intrinsics.checkNotNullParameter(taskListener, "taskListener");
        this.username = username;
        this.password = password;
        this.token = token;
        this.okHttpClient = okHttpClient;
        this.baseUrl = baseUrl;
        this.sharedPreferences = sharedPreferences;
        this.taskListener = taskListener;
        this.postValues = new HashMap<>();
        this.tag = "ServerAuthenticator";
    }

    public final String getUsername() {
        return this.username;
    }

    public final void setUsername(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.username = str;
    }

    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.password = str;
    }

    public final SharedPreferences getSharedPreferences() {
        return this.sharedPreferences;
    }

    public final HashMap<String, String> getPostValues() {
        return this.postValues;
    }

    public final void performCatrobatRegister(String userEmail, String language, String country) {
        Intrinsics.checkNotNullParameter(userEmail, "userEmail");
        Intrinsics.checkNotNullParameter(language, "language");
        Intrinsics.checkNotNullParameter(country, "country");
        this.postValues.put(ServerAuthenticationConstants.CATROBAT_USERNAME_KEY, this.username);
        this.postValues.put(ServerAuthenticationConstants.CATROBAT_PASSWORD_KEY, this.password);
        this.postValues.put(ServerAuthenticationConstants.CATROBAT_EMAIL_KEY, userEmail);
        if (!Intrinsics.areEqual(this.token, Constants.NO_TOKEN)) {
            this.postValues.put("token", this.token);
        }
        this.postValues.put(ServerAuthenticationConstants.CATROBAT_COUNTRY_KEY, country);
        this.postValues.put(SharedPreferenceKeys.DEVICE_LANGUAGE, language);
        String str = this.baseUrl + ServerAuthenticationConstants.REGISTRATION_URL_APPENDING;
        Integer SERVER_RESPONSE_REGISTER_OK = ServerAuthenticationConstants.SERVER_RESPONSE_REGISTER_OK;
        Intrinsics.checkNotNullExpressionValue(SERVER_RESPONSE_REGISTER_OK, "SERVER_RESPONSE_REGISTER_OK");
        performTask(str, SERVER_RESPONSE_REGISTER_OK.intValue());
    }

    public final void performCatrobatLogin() {
        this.postValues.put(ServerAuthenticationConstants.CATROBAT_USERNAME_KEY, this.username);
        this.postValues.put(ServerAuthenticationConstants.CATROBAT_PASSWORD_KEY, this.password);
        if (!Intrinsics.areEqual(this.token, Constants.NO_TOKEN)) {
            this.postValues.put("token", this.token);
        }
        String str = this.baseUrl + ServerAuthenticationConstants.LOGIN_URL_APPENDING;
        Integer SERVER_RESPONSE_TOKEN_OK = ServerAuthenticationConstants.SERVER_RESPONSE_TOKEN_OK;
        Intrinsics.checkNotNullExpressionValue(SERVER_RESPONSE_TOKEN_OK, "SERVER_RESPONSE_TOKEN_OK");
        performTask(str, SERVER_RESPONSE_TOKEN_OK.intValue());
    }

    public final void performTask(String serverUrl, int acceptedStatusCode) {
        Intrinsics.checkNotNullParameter(serverUrl, "serverUrl");
        try {
            JSONObject jSONObject = new JSONObject(CatrobatWebClientKt.performCallWith(this.okHttpClient, CatrobatWebClientKt.createFormEncodedRequest(this.postValues, serverUrl)));
            if (isInvalidResponse(acceptedStatusCode, jSONObject)) {
                this.taskListener.onError(jSONObject.optInt("statusCode"), jSONObject.optString(ServerAuthenticationConstants.JSON_ANSWER));
                return;
            }
            String strOptString = jSONObject.optString("token");
            SharedPreferences.Editor editorEdit = this.sharedPreferences.edit();
            editorEdit.putString("token", strOptString);
            editorEdit.putString("username", this.username);
            String strOptString2 = jSONObject.optString("email");
            Intrinsics.checkNotNull(strOptString2);
            if (strOptString2.length() > 0) {
                editorEdit.putString("email", strOptString2);
            }
            editorEdit.apply();
            this.taskListener.onSuccess();
        } catch (WebConnectionException e) {
            String message = e.getMessage();
            if (message != null) {
                Log.e(this.tag, message);
            }
            this.taskListener.onError(e.getStatusCode(), null);
        }
    }

    public final boolean isInvalidResponse(int acceptedStatusCode, JSONObject resultJsonObject) {
        Intrinsics.checkNotNullParameter(resultJsonObject, "resultJsonObject");
        int iOptInt = resultJsonObject.optInt("statusCode");
        String strOptString = resultJsonObject.optString(ServerAuthenticationConstants.JSON_ANSWER);
        String strOptString2 = resultJsonObject.optString("token");
        if (acceptedStatusCode != iOptInt) {
            Log.i(this.tag, "Not accepted StatusCode: " + iOptInt + "; Server Answer: " + strOptString);
            return true;
        }
        if (strOptString2.length() == 32) {
            return false;
        }
        Log.e(this.tag, "Invlaid TokenError: " + strOptString2 + "; StatusCode: " + iOptInt + " Server Answer: " + strOptString);
        return true;
    }
}
