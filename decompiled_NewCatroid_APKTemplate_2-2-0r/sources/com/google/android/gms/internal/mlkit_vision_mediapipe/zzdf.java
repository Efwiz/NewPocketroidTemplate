package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public class zzdf {
    private long zza;

    public static zzdf zza(long j) {
        return new zzdf(j);
    }

    private final native void zzb(long j);

    private final native long zzc(long j);

    private final native long zzd(long j);

    public long zza() {
        return this.zza;
    }

    public final long zzb() {
        return zzd(this.zza);
    }

    public final zzdf zzc() {
        return new zzdf(zzc(this.zza));
    }

    public void zzd() {
        long j = this.zza;
        if (j != 0) {
            zzb(j);
            this.zza = 0L;
        }
    }

    private zzdf(long j) {
        this.zza = j;
    }
}
