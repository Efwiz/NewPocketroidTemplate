package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzx<K, V> implements zzbh<K, V> {

    @CheckForNull
    private transient Set<K> zza;

    @CheckForNull
    private transient Map<K, Collection<V>> zzb;

    zzx() {
    }

    public final boolean equals(@CheckForNull Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzbh) {
            return zzn().equals(((zzbh) obj).zzn());
        }
        return false;
    }

    public final int hashCode() {
        return zzn().hashCode();
    }

    public final String toString() {
        return ((zzn) zzn()).zza.toString();
    }

    abstract Map<K, Collection<V>> zzh();

    abstract Set<K> zzi();

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzbh
    public boolean zzm(K k, V v) {
        throw null;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzbh
    public final Map<K, Collection<V>> zzn() {
        Map<K, Collection<V>> map = this.zzb;
        if (map != null) {
            return map;
        }
        Map<K, Collection<V>> mapZzh = zzh();
        this.zzb = mapZzh;
        return mapZzh;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzbh
    public final Set<K> zzo() {
        Set<K> set = this.zza;
        if (set != null) {
            return set;
        }
        Set<K> setZzi = zzi();
        this.zza = setZzi;
        return setZzi;
    }
}
