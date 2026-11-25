package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzex {
    private static final Class<?> zza = zza("libcore.io.Memory");
    private static final boolean zzb;

    static boolean zza() {
        return (zza == null || zzb) ? false : true;
    }

    static Class<?> zzb() {
        return zza;
    }

    private static <T> Class<T> zza(String str) {
        try {
            return (Class<T>) Class.forName(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    static {
        zzb = zza("org.robolectric.Robolectric") != null;
    }
}
