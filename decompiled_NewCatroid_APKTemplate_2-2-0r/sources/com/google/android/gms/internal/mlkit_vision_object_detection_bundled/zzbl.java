package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Iterator;
import java.util.Map;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbl<K, V> extends zzax<Map.Entry<K, V>> {
    private final transient zzaw<K, V> zza;
    private final transient Object[] zzb;
    private final transient int zzc;

    zzbl(zzaw<K, V> zzawVar, Object[] objArr, int i, int i2) {
        this.zza = zzawVar;
        this.zzb = objArr;
        this.zzc = i2;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value != null && value.equals(this.zza.get(key))) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzax, com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* bridge */ /* synthetic */ Iterator iterator() {
        return zzf().listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap
    final int zza(Object[] objArr, int i) {
        return zzf().zza(objArr, 0);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzax, com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap
    /* JADX INFO: renamed from: zzd */
    public final zzbs<Map.Entry<K, V>> iterator() {
        return zzf().listIterator(0);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzax
    final zzau<Map.Entry<K, V>> zzg() {
        return new zzbk(this);
    }
}
