package com.google.android.gms.internal.mlkit_vision_text;

import java.util.AbstractSet;
import java.util.Collection;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzch<E> extends AbstractSet<E> {
    zzch() {
    }

    @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean removeAll(Collection<?> collection) {
        return zzci.zzb(this, collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> collection) {
        collection.getClass();
        return super.retainAll(collection);
    }
}
