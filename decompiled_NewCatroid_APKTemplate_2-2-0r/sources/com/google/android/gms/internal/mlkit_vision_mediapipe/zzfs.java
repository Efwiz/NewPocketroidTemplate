package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzfs {
    private static final zzfs zza;
    private static final zzfs zzb;

    private zzfs() {
    }

    abstract void zza(Object obj, long j);

    abstract <L> void zza(Object obj, Object obj2, long j);

    static zzfs zza() {
        return zza;
    }

    static zzfs zzb() {
        return zzb;
    }

    static {
        zzfr zzfrVar = null;
        zza = new zzfu();
        zzb = new zzft();
    }
}
