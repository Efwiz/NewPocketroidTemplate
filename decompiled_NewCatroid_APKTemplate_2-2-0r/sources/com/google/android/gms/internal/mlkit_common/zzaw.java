package com.google.android.gms.internal.mlkit_common;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaw {
    private int zza;
    private final zzaz zzb = zzaz.DEFAULT;

    public final zzaw zza(int i) {
        this.zza = i;
        return this;
    }

    public final zzba zzb() {
        return new zzav(this.zza, this.zzb);
    }
}
