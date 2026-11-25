package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdx extends zzdz {
    private int zza = 0;
    private final int zzb;
    private final /* synthetic */ zzdu zzc;

    zzdx(zzdu zzduVar) {
        this.zzc = zzduVar;
        this.zzb = zzduVar.zza();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza < this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzed
    public final byte zza() {
        int i = this.zza;
        if (i >= this.zzb) {
            throw new NoSuchElementException();
        }
        this.zza = i + 1;
        return this.zzc.zzb(i);
    }
}
