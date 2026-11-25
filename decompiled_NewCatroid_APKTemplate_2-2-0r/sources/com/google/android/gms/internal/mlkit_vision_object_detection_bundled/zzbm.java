package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Iterator;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbm<K> extends zzax<K> {
    private final transient zzaw<K, ?> zza;
    private final transient zzau<K> zzb;

    zzbm(zzaw<K, ?> zzawVar, zzau<K> zzauVar) {
        this.zza = zzawVar;
        this.zzb = zzauVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        return this.zza.get(obj) != null;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzax, com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* bridge */ /* synthetic */ Iterator iterator() {
        return this.zzb.listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zza.size();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap
    final int zza(Object[] objArr, int i) {
        return this.zzb.zza(objArr, 0);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzax, com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzap
    /* JADX INFO: renamed from: zzd */
    public final zzbs<K> iterator() {
        return this.zzb.listIterator(0);
    }
}
