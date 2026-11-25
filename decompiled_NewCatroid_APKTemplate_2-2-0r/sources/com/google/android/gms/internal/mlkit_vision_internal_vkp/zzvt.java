package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzvt<K, V> implements Map.Entry<K, V> {
    zzvt<K, V> zza;
    zzvt<K, V> zzb;
    zzvt<K, V> zzc;
    zzvt<K, V> zzd;
    zzvt<K, V> zze;
    final K zzf;
    V zzg;
    int zzh;

    zzvt() {
        this.zzf = null;
        this.zze = this;
        this.zzd = this;
    }

    zzvt(zzvt<K, V> zzvtVar, K k, zzvt<K, V> zzvtVar2, zzvt<K, V> zzvtVar3) {
        this.zza = zzvtVar;
        this.zzf = k;
        this.zzh = 1;
        this.zzd = zzvtVar2;
        this.zze = zzvtVar3;
        zzvtVar3.zzd = this;
        zzvtVar2.zze = this;
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
