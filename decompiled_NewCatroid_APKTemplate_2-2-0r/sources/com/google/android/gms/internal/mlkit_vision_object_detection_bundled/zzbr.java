package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Iterator;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzbr<F, T> implements Iterator<T> {
    final Iterator<? extends F> zza;

    zzbr(Iterator<? extends F> it) {
        it.getClass();
        this.zza = it;
    }

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

    abstract T zza(F f);
}
