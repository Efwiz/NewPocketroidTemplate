package com.google.android.gms.internal.mlkit_vision_text;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzca<K, V> extends AbstractMap<K, V> {

    @NullableDecl
    private transient Set<Map.Entry<K, V>> zza;

    @NullableDecl
    private transient Set<K> zzb;

    @NullableDecl
    private transient Collection<V> zzc;

    zzca() {
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
        zzby zzbyVar = new zzby(this);
        this.zzb = zzbyVar;
        return zzbyVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection<V> values() {
        Collection<V> collection = this.zzc;
        if (collection != null) {
            return collection;
        }
        zzbz zzbzVar = new zzbz(this);
        this.zzc = zzbzVar;
        return zzbzVar;
    }

    abstract Set<Map.Entry<K, V>> zza();
}
