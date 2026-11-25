package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzp<K, V> extends zzbb<K, Collection<V>> {
    final /* synthetic */ zzl zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzp(zzl zzlVar, Map<K, Collection<V>> map) {
        super(map);
        this.zza = zzlVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzbb, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<K> iterator() {
        return new zzo(this, this.zzb.entrySet().iterator());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzbb, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        int size;
        Collection collection = (Collection) this.zzb.remove(obj);
        if (collection != null) {
            size = collection.size();
            collection.clear();
            zzl.zzb(this.zza, size);
        } else {
            size = 0;
        }
        return size > 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzbb, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        zzav.zza(iterator());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean containsAll(Collection<?> collection) {
        return this.zzb.keySet().containsAll(collection);
    }

    @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
    public final boolean equals(@NullableDecl Object obj) {
        return this == obj || this.zzb.keySet().equals(obj);
    }

    @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.zzb.keySet().hashCode();
    }
}
