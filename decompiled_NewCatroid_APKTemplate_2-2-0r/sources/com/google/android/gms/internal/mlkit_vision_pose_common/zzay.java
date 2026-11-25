package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzay<K, V> extends zzbg<Map.Entry<K, V>> {
    zzay() {
    }

    abstract Map<K, V> zza();

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return zza().size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        zza().clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object objZza = zzax.zza(zza(), key);
            if (zze.zza(objZza, entry.getValue()) && (objZza != null || zza().containsKey(key))) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean isEmpty() {
        return zza().isEmpty();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object obj) {
        if (contains(obj)) {
            return zza().keySet().remove(((Map.Entry) obj).getKey());
        }
        return false;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzbg, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean removeAll(Collection<?> collection) {
        try {
            return super.removeAll((Collection) zzh.zza(collection));
        } catch (UnsupportedOperationException unused) {
            return zzbh.zza(this, collection.iterator());
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzbg, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean retainAll(Collection<?> collection) {
        int i;
        try {
            return super.retainAll((Collection) zzh.zza(collection));
        } catch (UnsupportedOperationException unused) {
            int size = collection.size();
            if (size < 3) {
                zzy.zza(size, "expectedSize");
                i = size + 1;
            } else {
                i = size < 1073741824 ? (int) ((size / 0.75f) + 1.0f) : Integer.MAX_VALUE;
            }
            HashSet hashSet = new HashSet(i);
            for (Object obj : collection) {
                if (contains(obj)) {
                    hashSet.add(((Map.Entry) obj).getKey());
                }
            }
            return zza().keySet().retainAll(hashSet);
        }
    }
}
