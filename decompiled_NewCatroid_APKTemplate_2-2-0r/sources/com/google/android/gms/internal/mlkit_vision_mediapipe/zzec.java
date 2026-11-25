package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzec {
    private final zzel zza;
    private final byte[] zzb;

    private zzec(int i) {
        byte[] bArr = new byte[i];
        this.zzb = bArr;
        this.zza = zzel.zza(bArr);
    }

    public final zzdu zza() {
        if (this.zza.zza() != 0) {
            throw new IllegalStateException("Did not write as much data as expected.");
        }
        return new zzee(this.zzb);
    }

    public final zzel zzb() {
        return this.zza;
    }

    /* synthetic */ zzec(int i, zzdx zzdxVar) {
        this(i);
    }
}
