package com.google.android.gms.internal.mlkit_vision_face;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzbv<K, V> extends AbstractMap<K, V> {

    @NullableDecl
    private transient Set<Map.Entry<K, V>> zza;

    @NullableDecl
    private transient Set<K> zzb;

    @NullableDecl
    private transient Collection<V> zzc;

    zzbv() {
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set<Map.Entry<K, V>> entrySet() {
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
        zzbt zzbtVar = new zzbt(this);
        this.zzb = zzbtVar;
        return zzbtVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection<V> values() {
        Collection<V> collection = this.zzc;
        if (collection != null) {
            return collection;
        }
        zzbu zzbuVar = new zzbu(this);
        this.zzc = zzbuVar;
        return zzbuVar;
    }

    abstract Set<Map.Entry<K, V>> zza();
}
