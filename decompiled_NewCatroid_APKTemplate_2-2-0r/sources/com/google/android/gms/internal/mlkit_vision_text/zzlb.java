package com.google.android.gms.internal.mlkit_vision_text;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzlb {
    private static zzlb zza;

    private zzlb() {
    }

    public static synchronized zzlb zza() {
        if (zza == null) {
            zza = new zzlb();
        }
        return zza;
    }
}
