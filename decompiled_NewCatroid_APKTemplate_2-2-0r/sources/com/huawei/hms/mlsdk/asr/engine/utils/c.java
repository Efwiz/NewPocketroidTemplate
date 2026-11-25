package com.huawei.hms.mlsdk.asr.engine.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import java.util.Locale;

/* JADX INFO: compiled from: CountryResolver.java */
/* JADX INFO: loaded from: classes10.dex */
public class c {
    private String a;

    public c(Context context, boolean z) {
        this.a = GrsBaseInfo.CountryCodeSource.UNKNOWN;
        if (context == null) {
            throw new NullPointerException("context must be not null.Please provide app's Context");
        }
        try {
            b();
            if (c()) {
                SmartLogger.d("com.huawei.hms.mlsdk.asr.engine.utils.c", "getCountryCode get country code from {%s}", GrsBaseInfo.CountryCodeSource.VENDOR_COUNTRY);
            } else {
                a(context, z);
                if (c()) {
                    SmartLogger.d("com.huawei.hms.mlsdk.asr.engine.utils.c", "getCountryCode get country code from {%s}", GrsBaseInfo.CountryCodeSource.SIM_COUNTRY);
                } else {
                    this.a = a("get", "ro.product.locale.region", "android.os.SystemProperties", GrsBaseInfo.CountryCodeSource.UNKNOWN);
                    SmartLogger.e("com.huawei.hms.mlsdk.asr.engine.utils.c", "getLocaleCountryCode=" + this.a);
                    if (!"cn".equalsIgnoreCase(this.a)) {
                        SmartLogger.e("com.huawei.hms.mlsdk.asr.engine.utils.c", "getLocaleCountryCode from system language is not reliable.");
                        this.a = GrsBaseInfo.CountryCodeSource.UNKNOWN;
                    }
                    if (c()) {
                        SmartLogger.d("com.huawei.hms.mlsdk.asr.engine.utils.c", "getCountryCode get country code from {%s}", GrsBaseInfo.CountryCodeSource.LOCALE_INFO);
                    }
                }
            }
        } catch (Exception unused) {
            SmartLogger.w("com.huawei.hms.mlsdk.asr.engine.utils.c", "get CountryCode error");
        }
        this.a = this.a.toUpperCase(Locale.ENGLISH);
    }

    private void b() {
        if (Build.BRAND.equals("HONOR")) {
            this.a = a("get", "msc.sys.country", "android.os.SystemProperties", GrsBaseInfo.CountryCodeSource.UNKNOWN);
        } else {
            this.a = a("get", "ro.hw.country", "android.os.SystemProperties", GrsBaseInfo.CountryCodeSource.UNKNOWN);
        }
        SmartLogger.e("com.huawei.hms.mlsdk.asr.engine.utils.c", com.huawei.hms.mlsdk.asr.o.a.a("getVendorCountry=").append(this.a).toString());
        if ("eu".equalsIgnoreCase(this.a) || "la".equalsIgnoreCase(this.a)) {
            SmartLogger.e("com.huawei.hms.mlsdk.asr.engine.utils.c", "getVendorCountry equals eu or la ,not reliable");
            this.a = GrsBaseInfo.CountryCodeSource.UNKNOWN;
            return;
        }
        String str = this.a;
        if (str == null || str.length() != 2) {
            this.a = GrsBaseInfo.CountryCodeSource.UNKNOWN;
        }
    }

    private boolean c() {
        return !GrsBaseInfo.CountryCodeSource.UNKNOWN.equals(this.a);
    }

    public String a() {
        return this.a;
    }

    private void a(Context context, boolean z) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService("phone");
        if (telephonyManager != null) {
            if (!z || telephonyManager.getPhoneType() == 2) {
                this.a = telephonyManager.getSimCountryIso();
                SmartLogger.e("com.huawei.hms.mlsdk.asr.engine.utils.c", com.huawei.hms.mlsdk.asr.o.a.a("getSimCountryCode by not enableNetwork, countryCode=").append(this.a).toString());
            } else {
                this.a = telephonyManager.getNetworkCountryIso();
                SmartLogger.e("com.huawei.hms.mlsdk.asr.engine.utils.c", com.huawei.hms.mlsdk.asr.o.a.a("getSimCountryCode by enableNetwork, countryCode=").append(this.a).toString());
            }
        }
        String str = this.a;
        if (str == null || str.length() != 2) {
            this.a = GrsBaseInfo.CountryCodeSource.UNKNOWN;
        }
    }

    private static String a(String str, String str2, String str3, String str4) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            try {
                Class<?> cls = Class.forName(str3);
                return (String) cls.getMethod(str, String.class, String.class).invoke(cls, str2, str4);
            } catch (Exception e) {
                SmartLogger.e("c", "getProperty catch exception: ", e);
                return str4;
            }
        }
        SmartLogger.w("c", "reflect class for method has exception.");
        return str4;
    }
}
