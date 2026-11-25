package com.google.android.gms.internal.mlkit_vision_face;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
class zzad<K, V> extends zzao<K, V> implements zzbp<K, V> {
    protected zzad(Map<K, Collection<V>> map) {
        super(map);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzao
    final Collection<V> zza(K k, Collection<V> collection) {
        return zzg(k, (List) collection, null);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzbp
    public final List<V> zzb(@NullableDecl K k) {
        return (List<V>) super.zzf(k);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzao
    /* bridge */ /* synthetic */ Collection zzc() {
        throw null;
    }
}
