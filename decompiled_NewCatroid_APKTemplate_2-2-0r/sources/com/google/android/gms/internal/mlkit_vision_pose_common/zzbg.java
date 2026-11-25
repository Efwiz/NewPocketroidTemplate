package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzbg<E> extends AbstractSet<E> {
    zzbg() {
    }

    @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean removeAll(Collection<?> collection) {
        zzh.zza(collection);
        if (collection instanceof zzbf) {
            collection = ((zzbf) collection).zza();
        }
        if ((collection instanceof Set) && collection.size() > size()) {
            Iterator<E> it = iterator();
            zzh.zza(collection);
            boolean z = false;
            while (it.hasNext()) {
                if (collection.contains(it.next())) {
                    it.remove();
                    z = true;
                }
            }
            return z;
        }
        return zzbh.zza(this, collection.iterator());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> collection) {
        return super.retainAll((Collection) zzh.zza(collection));
    }
}
