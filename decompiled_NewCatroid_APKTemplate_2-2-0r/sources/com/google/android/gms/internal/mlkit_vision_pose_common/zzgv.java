package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzgv {
    private static final zzgv zza;
    private static final zzgv zzb;

    private zzgv() {
    }

    abstract void zza(Object obj, long j);

    abstract <L> void zza(Object obj, Object obj2, long j);

    static zzgv zza() {
        return zza;
    }

    static zzgv zzb() {
        return zzb;
    }

    static {
        zzgy zzgyVar = null;
        zza = new zzgx();
        zzb = new zzha();
    }
}
