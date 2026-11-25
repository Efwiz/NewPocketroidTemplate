package com.google.android.gms.internal.mlkit_vision_text;

import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzck<F, T> implements Iterator<T> {
    final Iterator<? extends F> zzb;

    zzck(Iterator<? extends F> it) {
        it.getClass();
        this.zzb = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzb.hasNext();
    }

    @Override // java.util.Iterator
    public final T next() {
        return zza(this.zzb.next());
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.zzb.remove();
    }

    abstract T zza(F f);
}
