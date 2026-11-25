package org.catrobat.catroid.utils.community;

import android.content.Context;
import android.content.SharedPreferences;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/community/CommunityTokenManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: CommunityTokenManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000bH\u0007J(\u0010\u0013\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/utils/community/CommunityTokenManager;", "", "()V", "KEY_TOKEN", "", "KEY_USERNAME", "KEY_USER_JSON", "PREFS_NAME", "clearSession", "", "context", "Landroid/content/Context;", "getPrefs", "Landroid/content/SharedPreferences;", "getToken", "getUserJson", "getUsername", "isLoggedIn", "", "saveSession", "token", "username", "userJson", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommunityTokenManager {
    public static final CommunityTokenManager INSTANCE = new CommunityTokenManager();
    private static final String KEY_TOKEN = "jwt_token";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_JSON = "user_json";
    private static final String PREFS_NAME = "newcatroid_community_prefs";

    private CommunityTokenManager() {
    }

    private final SharedPreferences getPrefs(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "getSharedPreferences(...)");
        return sharedPreferences;
    }

    @JvmStatic
    public static final void saveSession(Context context, String token, String username, String userJson) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(token, "token");
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(userJson, "userJson");
        INSTANCE.getPrefs(context).edit().putString(KEY_TOKEN, token).putString("username", username).putString(KEY_USER_JSON, userJson).apply();
    }

    @JvmStatic
    public static final String getToken(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return INSTANCE.getPrefs(context).getString(KEY_TOKEN, null);
    }

    @JvmStatic
    public static final String getUsername(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return INSTANCE.getPrefs(context).getString("username", null);
    }

    @JvmStatic
    public static final String getUserJson(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return INSTANCE.getPrefs(context).getString(KEY_USER_JSON, null);
    }

    @JvmStatic
    public static final void clearSession(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        INSTANCE.getPrefs(context).edit().clear().apply();
    }

    @JvmStatic
    public static final boolean isLoggedIn(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String token = getToken(context);
        return !(token == null || token.length() == 0);
    }
}
