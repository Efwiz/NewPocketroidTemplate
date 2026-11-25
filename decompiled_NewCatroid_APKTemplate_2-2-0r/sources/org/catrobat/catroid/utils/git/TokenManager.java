package org.catrobat.catroid.utils.git;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import java.io.IOException;
import java.security.GeneralSecurityException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/TokenManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: TokenManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\r\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/utils/git/TokenManager;", "", "()V", "KEY_AUTH_TOKEN", "", "PREF_FILE_NAME", "clearToken", "", "context", "Landroid/content/Context;", "getEncryptedPrefs", "Landroid/content/SharedPreferences;", "getToken", "saveToken", "token", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TokenManager {
    public static final TokenManager INSTANCE = new TokenManager();
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String PREF_FILE_NAME = "github_secure_prefs";

    private TokenManager() {
    }

    private final SharedPreferences getEncryptedPrefs(Context context) throws GeneralSecurityException, IOException {
        SharedPreferences sharedPreferencesCreate = EncryptedSharedPreferences.create(PREF_FILE_NAME, MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC), context, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        Intrinsics.checkNotNullExpressionValue(sharedPreferencesCreate, "create(...)");
        return sharedPreferencesCreate;
    }

    public final void saveToken(Context context, String token) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(token, "token");
        getEncryptedPrefs(context).edit().putString(KEY_AUTH_TOKEN, token).apply();
    }

    public final String getToken(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return getEncryptedPrefs(context).getString(KEY_AUTH_TOKEN, null);
    }

    public final void clearToken(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        getEncryptedPrefs(context).edit().remove(KEY_AUTH_TOKEN).apply();
    }
}
