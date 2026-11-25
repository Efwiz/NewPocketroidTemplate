package com.hcaptcha.sdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import java.io.Serializable;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
final class HCaptchaCompat {
    private HCaptchaCompat() {
    }

    public static ApplicationInfo getApplicationInfo(Context context) {
        return Build.VERSION.SDK_INT >= 33 ? context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.ApplicationInfoFlags.of(128L)) : context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
    }

    public static <T extends Parcelable> T getParcelable(Bundle bundle, String str, Class<T> cls) {
        return Build.VERSION.SDK_INT >= 33 ? (T) bundle.getParcelable(str, cls) : (T) bundle.getParcelable(str);
    }

    public static <T extends Serializable> T getSerializable(Bundle bundle, String str, Class<T> cls) {
        return Build.VERSION.SDK_INT >= 33 ? (T) bundle.getSerializable(str, cls) : (T) bundle.getSerializable(str);
    }
}
