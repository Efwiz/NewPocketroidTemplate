package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.AbstractSet;
import java.util.Collection;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzbp<E> extends AbstractSet<E> {
    zzbp() {
    }

    @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean removeAll(Collection<?> collection) {
        return zzbq.zzb(this, collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> collection) {
        collection.getClass();
        return super.retainAll(collection);
    }
}
