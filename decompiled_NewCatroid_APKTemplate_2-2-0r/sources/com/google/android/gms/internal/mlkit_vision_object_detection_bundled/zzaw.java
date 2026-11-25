package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzaw<K, V> implements Map<K, V>, Serializable {

    @CheckForNull
    private transient zzax<Map.Entry<K, V>> zza;

    @CheckForNull
    private transient zzax<K> zzb;

    @CheckForNull
    private transient zzap<V> zzc;

    zzaw() {
    }

    public static <K, V> zzaw<K, V> zzc() {
        return (zzaw<K, V>) zzbo.zza;
    }

    @Override // java.util.Map
    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final boolean containsKey(@CheckForNull Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public final boolean containsValue(@CheckForNull Object obj) {
        return values().contains(obj);
    }

    @Override // java.util.Map
    public final boolean equals(@CheckForNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    @Override // java.util.Map
    @CheckForNull
    public abstract V get(@CheckForNull Object obj);

    @Override // java.util.Map
    @CheckForNull
    public final V getOrDefault(@CheckForNull Object obj, @CheckForNull V v) {
        V v2 = get(obj);
        return v2 != null ? v2 : v;
    }

    @Override // java.util.Map
    public final int hashCode() {
        return zzbq.zza(entrySet());
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Set keySet() {
        zzax<K> zzaxVar = this.zzb;
        if (zzaxVar != null) {
            return zzaxVar;
        }
        zzax<K> zzaxVarZze = zze();
        this.zzb = zzaxVarZze;
        return zzaxVarZze;
    }

    @Override // java.util.Map
    @CheckForNull
    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    @CheckForNull
    @Deprecated
    public final V remove(@CheckForNull Object obj) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        int size = size();
        zzaa.zza(size, "size");
        StringBuilder sb = new StringBuilder((int) Math.min(((long) size) * 8, 1073741824L));
        sb.append('{');
        boolean z = true;
        for (Map.Entry<K, V> entry : entrySet()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            z = false;
        }
        sb.append('}');
        return sb.toString();
    }

    abstract zzap<V> zza();

    @Override // java.util.Map
    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public final zzap<V> values() {
        zzap<V> zzapVar = this.zzc;
        if (zzapVar != null) {
            return zzapVar;
        }
        zzap<V> zzapVarZza = zza();
        this.zzc = zzapVarZza;
        return zzapVarZza;
    }

    abstract zzax<Map.Entry<K, V>> zzd();

    abstract zzax<K> zze();

    @Override // java.util.Map
    /* JADX INFO: renamed from: zzf, reason: merged with bridge method [inline-methods] */
    public final zzax<Map.Entry<K, V>> entrySet() {
        zzax<Map.Entry<K, V>> zzaxVar = this.zza;
        if (zzaxVar != null) {
            return zzaxVar;
        }
        zzax<Map.Entry<K, V>> zzaxVarZzd = zzd();
        this.zza = zzaxVarZzd;
        return zzaxVarZzd;
    }
}
