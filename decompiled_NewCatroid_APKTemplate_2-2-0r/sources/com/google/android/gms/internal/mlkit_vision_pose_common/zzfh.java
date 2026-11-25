package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfh {
    private final zzfq zza;
    private final byte[] zzb;

    private zzfh(int i) {
        byte[] bArr = new byte[i];
        this.zzb = bArr;
        this.zza = zzfq.zza(bArr);
    }

    public final zzez zza() {
        this.zza.zzb();
        return new zzfj(this.zzb);
    }

    public final zzfq zzb() {
        return this.zza;
    }

    /* synthetic */ zzfh(int i, zzfc zzfcVar) {
        this(i);
    }
}
