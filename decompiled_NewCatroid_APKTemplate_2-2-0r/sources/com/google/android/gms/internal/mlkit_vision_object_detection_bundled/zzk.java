package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
class zzk<K, V> extends zzv<K, V> implements zzaz<K, V> {
    protected zzk(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzv
    /* bridge */ /* synthetic */ Collection zza() {
        throw null;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzv
    final Collection<V> zzb(K k, Collection<V> collection) {
        return zzf(k, (List) collection, null);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzaz
    public final List<V> zzc(K k) {
        return (List<V>) super.zze(k);
    }
}
