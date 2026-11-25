package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzeg {
    private int zza;
    private int zzb;
    private boolean zzc;

    public static long zza(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    static zzeg zza(byte[] bArr, int i, int i2, boolean z) {
        zzei zzeiVar = new zzei(bArr, i2);
        try {
            zzeiVar.zza(i2);
            return zzeiVar;
        } catch (zzfi e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int zzb(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public abstract int zza();

    public abstract int zza(int i) throws zzfi;

    private zzeg() {
        this.zza = 100;
        this.zzb = Integer.MAX_VALUE;
        this.zzc = false;
    }
}
