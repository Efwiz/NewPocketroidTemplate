package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzce extends RuntimeException {
    public static zzce zza(String str, String str2, int i, int i2) {
        return new zzce(zzb(str, str2, i, i2), str2);
    }

    public static zzce zza(String str, String str2, int i) {
        return new zzce(zzb(str, str2, i, i + 1), str2);
    }

    public static zzce zzb(String str, String str2, int i) {
        return new zzce(zzb(str, str2, i, -1), str2);
    }

    public static zzce zza(String str, String str2) {
        return new zzce(str, str2);
    }

    private zzce(String str, String str2) {
        super(str);
    }

    private static String zzb(String str, String str2, int i, int i2) {
        if (i2 < 0) {
            i2 = str2.length();
        }
        StringBuilder sbAppend = new StringBuilder(str).append(": ");
        if (i > 8) {
            sbAppend.append("...").append((CharSequence) str2, i - 5, i);
        } else {
            sbAppend.append((CharSequence) str2, 0, i);
        }
        sbAppend.append('[').append(str2.substring(i, i2)).append(']');
        if (str2.length() - i2 > 8) {
            sbAppend.append((CharSequence) str2, i2, i2 + 5).append("...");
        } else {
            sbAppend.append((CharSequence) str2, i2, str2.length());
        }
        return sbAppend.toString();
    }

    @Override // java.lang.Throwable
    public final synchronized Throwable fillInStackTrace() {
        return this;
    }
}
