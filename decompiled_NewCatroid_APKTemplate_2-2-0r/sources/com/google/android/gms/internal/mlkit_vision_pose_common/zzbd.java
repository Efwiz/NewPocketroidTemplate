package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzbd<K, V> extends AbstractMap<K, V> {

    @NullableDecl
    private transient Set<Map.Entry<K, V>> zza;

    @NullableDecl
    private transient Set<K> zzb;

    @NullableDecl
    private transient Collection<V> zzc;

    zzbd() {
    }

    abstract Set<Map.Entry<K, V>> zza();

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.zza;
        if (set != null) {
            return set;
        }
        Set<Map.Entry<K, V>> setZza = zza();
        this.zza = setZza;
        return setZza;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        Set<K> set = this.zzb;
        if (set != null) {
            return set;
        }
        zzbb zzbbVar = new zzbb(this);
        this.zzb = zzbbVar;
        return zzbbVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Collection<V> values() {
        Collection<V> collection = this.zzc;
        if (collection != null) {
            return collection;
        }
        zzba zzbaVar = new zzba(this);
        this.zzc = zzbaVar;
        return zzbaVar;
    }
}
