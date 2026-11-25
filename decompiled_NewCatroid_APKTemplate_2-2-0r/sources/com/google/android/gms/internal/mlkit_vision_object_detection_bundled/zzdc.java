package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdc<K, V> implements Map.Entry<K, V> {
    zzdc<K, V> zza;
    zzdc<K, V> zzb;
    zzdc<K, V> zzc;
    zzdc<K, V> zzd;
    zzdc<K, V> zze;
    final K zzf;
    V zzg;
    int zzh;

    zzdc() {
        this.zzf = null;
        this.zze = this;
        this.zzd = this;
    }

    zzdc(zzdc<K, V> zzdcVar, K k, zzdc<K, V> zzdcVar2, zzdc<K, V> zzdcVar3) {
        this.zza = zzdcVar;
        this.zzf = k;
        this.zzh = 1;
        this.zzd = zzdcVar2;
        this.zze = zzdcVar3;
        zzdcVar3.zzd = this;
        zzdcVar2.zze = this;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            K k = this.zzf;
            if (k != null ? k.equals(entry.getKey()) : entry.getKey() == null) {
                V v = this.zzg;
                if (v == null) {
                    if (entry.getValue() == null) {
                        return true;
                    }
                } else if (v.equals(entry.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // java.util.Map.Entry
    public final K getKey() {
        return this.zzf;
    }

    @Override // java.util.Map.Entry
    public final V getValue() {
        return this.zzg;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        K k = this.zzf;
        int iHashCode = k == null ? 0 : k.hashCode();
        V v = this.zzg;
        return iHashCode ^ (v != null ? v.hashCode() : 0);
    }

    @Override // java.util.Map.Entry
    public final V setValue(V v) {
        V v2 = this.zzg;
        this.zzg = v;
        return v2;
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zzf);
        String strValueOf2 = String.valueOf(this.zzg);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(strValueOf2).length());
        sb.append(strValueOf);
        sb.append(ContainerUtils.KEY_VALUE_DELIMITER);
        sb.append(strValueOf2);
        return sb.toString();
    }
}
