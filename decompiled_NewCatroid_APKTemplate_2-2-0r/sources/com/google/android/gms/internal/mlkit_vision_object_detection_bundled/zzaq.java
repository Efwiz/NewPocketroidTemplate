package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.io.Serializable;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaq<K, V> extends zzw<K, V> implements Serializable {
    final K zza;
    final V zzb;

    zzaq(K k, V v) {
        this.zza = k;
        this.zzb = v;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzw, java.util.Map.Entry
    public final K getKey() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzw, java.util.Map.Entry
    public final V getValue() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzw, java.util.Map.Entry
    public final V setValue(V v) {
        throw new UnsupportedOperationException();
    }
}
