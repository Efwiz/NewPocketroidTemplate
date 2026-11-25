package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfc extends zzfe {
    private int zza = 0;
    private final int zzb;
    private final /* synthetic */ zzez zzc;

    zzfc(zzez zzezVar) {
        this.zzc = zzezVar;
        this.zzb = zzezVar.zza();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza < this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfi
    public final byte zza() {
        int i = this.zza;
        if (i >= this.zzb) {
            throw new NoSuchElementException();
        }
        this.zza = i + 1;
        return this.zzc.zzb(i);
    }
}
