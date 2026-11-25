package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Iterator;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzbj<F, T> implements Iterator<T> {
    private final Iterator<? extends F> zza;

    zzbj(Iterator<? extends F> it) {
        this.zza = (Iterator) zzh.zza(it);
    }

    abstract T zza(F f);

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public final T next() {
        return zza(this.zza.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.zza.remove();
    }
}
