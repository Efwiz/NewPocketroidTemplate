package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzfl {
    private int zza;
    private int zzb;
    private boolean zzc;

    static zzfl zza(byte[] bArr, int i, int i2, boolean z) {
        zzfn zzfnVar = new zzfn(bArr, i2);
        try {
            zzfnVar.zza(i2);
            return zzfnVar;
        } catch (zzgl e) {
            throw new IllegalArgumentException(e);
        }
    }

    public abstract int zza();

    public abstract int zza(int i) throws zzgl;

    private zzfl() {
        this.zza = 100;
        this.zzb = Integer.MAX_VALUE;
        this.zzc = false;
    }
}
