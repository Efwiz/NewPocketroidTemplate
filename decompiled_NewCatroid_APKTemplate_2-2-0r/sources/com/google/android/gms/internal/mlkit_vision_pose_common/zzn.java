package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzn<K, V> extends zzay<K, Collection<V>> {
    private final /* synthetic */ zzk zza;

    zzn(zzk zzkVar) {
        this.zza = zzkVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzay
    final Map<K, Collection<V>> zza() {
        return this.zza;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry<K, Collection<V>>> iterator() {
        return new zzm(this.zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzay, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return zzab.zza(this.zza.zza.entrySet(), obj);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzay, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!contains(obj)) {
            return false;
        }
        this.zza.zzb.zzc(((Map.Entry) obj).getKey());
        return true;
    }
}
