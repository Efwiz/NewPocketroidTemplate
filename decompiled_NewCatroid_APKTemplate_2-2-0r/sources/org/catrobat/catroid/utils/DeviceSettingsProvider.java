package org.catrobat.catroid.utils;

import android.content.Context;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/DeviceSettingsProvider.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DeviceSettingsProvider {
    private static final String ANONYMIZED_COUNTRY_DEFAULT_ANTARCTICA = "aq";
    private static final String EMPTY_EMAIL_ADDRESS = "";

    public static String getUserEmail(Context context) {
        return "";
    }

    public static String getUserCountryCode() {
        return ANONYMIZED_COUNTRY_DEFAULT_ANTARCTICA;
    }

    private DeviceSettingsProvider() {
        throw new AssertionError("No.");
    }
}
