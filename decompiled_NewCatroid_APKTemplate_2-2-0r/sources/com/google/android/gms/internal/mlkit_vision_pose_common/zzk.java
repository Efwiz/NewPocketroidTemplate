package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzk<K, V> extends zzbd<K, Collection<V>> {
    final transient Map<K, Collection<V>> zza;
    final /* synthetic */ zzl zzb;

    zzk(zzl zzlVar, Map<K, Collection<V>> map) {
        this.zzb = zzlVar;
        this.zza = map;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzbd
    protected final Set<Map.Entry<K, Collection<V>>> zza() {
        return new zzn(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        return zzax.zzb(this.zza, obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzbd, java.util.AbstractMap, java.util.Map
    public final Set<K> keySet() {
        return this.zzb.zzh();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.zza.size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean equals(@NullableDecl Object obj) {
        return this == obj || this.zza.equals(obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        return this.zza.hashCode();
    }

    @Override // java.util.AbstractMap
    public final String toString() {
        return this.zza.toString();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        if (this.zza == this.zzb.zza) {
            this.zzb.zzc();
        } else {
            zzav.zza(new zzm(this));
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final /* synthetic */ Object remove(Object obj) {
        Collection<V> collectionRemove = this.zza.remove(obj);
        if (collectionRemove == null) {
            return null;
        }
        Collection<V> collectionZzb = this.zzb.zzb();
        collectionZzb.addAll(collectionRemove);
        zzl.zzb(this.zzb, collectionRemove.size());
        collectionRemove.clear();
        return collectionZzb;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final /* synthetic */ Object get(Object obj) {
        Collection<V> collection = (Collection) zzax.zza(this.zza, obj);
        if (collection == null) {
            return null;
        }
        return this.zzb.zza(obj, (Collection) collection);
    }
}
