package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Iterator;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzja implements Iterator<String> {
    private Iterator<String> zza;
    private final /* synthetic */ zziy zzb;

    zzja(zziy zziyVar) {
        this.zzb = zziyVar;
        this.zza = zziyVar.zza.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ String next() {
        return this.zza.next();
    }
}
