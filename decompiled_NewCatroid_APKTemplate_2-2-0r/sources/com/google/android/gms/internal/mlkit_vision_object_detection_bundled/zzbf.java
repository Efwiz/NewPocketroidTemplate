package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzbf<K, V> extends AbstractMap<K, V> {

    @CheckForNull
    private transient Set<Map.Entry<K, V>> zza;

    @CheckForNull
    private transient Set<K> zzb;

    @CheckForNull
    private transient Collection<V> zzc;

    zzbf() {
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
        zzbd zzbdVar = new zzbd(this);
        this.zzb = zzbdVar;
        return zzbdVar;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection<V> values() {
        Collection<V> collection = this.zzc;
        if (collection != null) {
            return collection;
        }
        zzbe zzbeVar = new zzbe(this);
        this.zzc = zzbeVar;
        return zzbeVar;
    }

    abstract Set<Map.Entry<K, V>> zza();
}
