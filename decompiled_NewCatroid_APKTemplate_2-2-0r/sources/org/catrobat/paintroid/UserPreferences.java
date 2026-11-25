package org.catrobat.paintroid;

import android.content.SharedPreferences;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.common.ConstantsKt;

/* JADX INFO: compiled from: UserPreferences.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u001a\u001a\u00020\u001bH\u0016R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR$\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\r8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u000f\"\u0004\b\u0012\u0010\u0013R$\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e¢\u0006\f\u001a\u0004\b\u0015\u0010\t\"\u0004\b\u0016\u0010\u000bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u0004¨\u0006\u001d"}, d2 = {"Lorg/catrobat/paintroid/UserPreferences;", "", "preferences", "Landroid/content/SharedPreferences;", "(Landroid/content/SharedPreferences;)V", "value", "", "preferenceImageNumber", "getPreferenceImageNumber", "()I", "setPreferenceImageNumber", "(I)V", "preferenceLikeUsDialogValue", "", "getPreferenceLikeUsDialogValue", "()Z", "preferenceZoomWindowEnabled", "getPreferenceZoomWindowEnabled", "setPreferenceZoomWindowEnabled", "(Z)V", "preferenceZoomWindowZoomPercentage", "getPreferenceZoomWindowZoomPercentage", "setPreferenceZoomWindowZoomPercentage", "getPreferences", "()Landroid/content/SharedPreferences;", "setPreferences", "setPreferenceLikeUsDialogValue", "", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public class UserPreferences {
    public static final int initialZoomPercent = 100;
    private SharedPreferences preferences;

    public UserPreferences(SharedPreferences preferences) {
        Intrinsics.checkNotNullParameter(preferences, "preferences");
        this.preferences = preferences;
    }

    public final SharedPreferences getPreferences() {
        return this.preferences;
    }

    public final void setPreferences(SharedPreferences sharedPreferences) {
        Intrinsics.checkNotNullParameter(sharedPreferences, "<set-?>");
        this.preferences = sharedPreferences;
    }

    public boolean getPreferenceLikeUsDialogValue() {
        return this.preferences.getBoolean(ConstantsKt.SHOW_LIKE_US_DIALOG_SHARED_PREFERENCES_TAG, false);
    }

    public int getPreferenceImageNumber() {
        return this.preferences.getInt(ConstantsKt.IMAGE_NUMBER_SHARED_PREFERENCES_TAG, 0);
    }

    public void setPreferenceImageNumber(int i) {
        this.preferences.edit().putInt(ConstantsKt.IMAGE_NUMBER_SHARED_PREFERENCES_TAG, i).apply();
    }

    public boolean getPreferenceZoomWindowEnabled() {
        return this.preferences.getBoolean(ConstantsKt.ZOOM_WINDOW_ENABLED_SHARED_PREFERENCES_TAG, true);
    }

    public void setPreferenceZoomWindowEnabled(boolean z) {
        this.preferences.edit().putBoolean(ConstantsKt.ZOOM_WINDOW_ENABLED_SHARED_PREFERENCES_TAG, z).apply();
    }

    public int getPreferenceZoomWindowZoomPercentage() {
        return this.preferences.getInt(ConstantsKt.ZOOM_WINDOW_ZOOM_PERCENTAGE_SHARED_PREFERENCES_TAG, 100);
    }

    public void setPreferenceZoomWindowZoomPercentage(int i) {
        this.preferences.edit().putInt(ConstantsKt.ZOOM_WINDOW_ZOOM_PERCENTAGE_SHARED_PREFERENCES_TAG, i).apply();
    }

    public void setPreferenceLikeUsDialogValue() {
        this.preferences.edit().putBoolean(ConstantsKt.SHOW_LIKE_US_DIALOG_SHARED_PREFERENCES_TAG, true).apply();
    }
}
