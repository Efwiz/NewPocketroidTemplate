package com.huawei.hms.mlsdk.t;

import android.text.TextUtils;
import android.util.Log;
import java.util.regex.Pattern;

/* JADX INFO: compiled from: SmartLog.java */
/* JADX INFO: loaded from: classes10.dex */
public class i0 {
    private static final Pattern a = Pattern.compile("[0-9]*[a-z|A-Z]*[一-龥]*");

    private static String a(String str, boolean z) {
        StringBuilder sb = new StringBuilder(512);
        if (!TextUtils.isEmpty(str)) {
            if (z) {
                if (!TextUtils.isEmpty(str)) {
                    int length = str.length();
                    int i = 1;
                    if (1 == length) {
                        str = String.valueOf('*');
                    } else {
                        StringBuilder sb2 = new StringBuilder(length);
                        for (int i2 = 0; i2 < length; i2++) {
                            char cCharAt = str.charAt(i2);
                            if (a.matcher(String.valueOf(cCharAt)).matches()) {
                                if (i % 2 == 0) {
                                    cCharAt = '*';
                                }
                                i++;
                            }
                            sb2.append(cCharAt);
                        }
                        str = sb2.toString();
                    }
                }
                sb.append(str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static void a(String str, String str2) {
    }

    public static void b(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.e("ML_TTS_" + str, a(str2, false));
    }

    public static void c(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.i("ML_TTS_" + str, a(str2, false));
    }

    public static void d(String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.w("ML_TTS_" + str, a(str2, false));
    }

    public static void a(String str, String str2, boolean z) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        Log.i("ML_TTS_" + str, a(str2, z));
    }
}
