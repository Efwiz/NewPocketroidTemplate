package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdc implements zzcy {
    private long zza;

    private static native void zza(long j);

    private static native void zzb(long j);

    private static native void zzc(long j);

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzcy
    public final void zza() {
        long j = this.zza;
        if (j != 0) {
            zza(j);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzcy
    public final void zzb() {
        long j = this.zza;
        if (j != 0) {
            zzb(j);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzcy
    public final void zzc() {
        long j = this.zza;
        if (j != 0) {
            zzc(j);
            this.zza = 0L;
        }
    }

    zzdc(long j) {
        this.zza = j;
    }
}
