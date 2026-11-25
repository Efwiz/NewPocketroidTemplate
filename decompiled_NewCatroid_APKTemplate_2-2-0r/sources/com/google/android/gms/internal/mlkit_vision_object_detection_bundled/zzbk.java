package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.AbstractMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbk extends zzau<Map.Entry> {
    final /* synthetic */ zzbl zza;

    zzbk(zzbl zzblVar) {
        this.zza = zzblVar;
    }

    @Override // java.util.List
    public final /* bridge */ /* synthetic */ Object get(int i) {
        zzh.zza(i, this.zza.zzc, "index");
        int i2 = i + i;
        Object obj = this.zza.zzb[i2];
        obj.getClass();
        Object obj2 = this.zza.zzb[i2 + 1];
        obj2.getClass();
        return new AbstractMap.SimpleImmutableEntry(obj, obj2);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.zza.zzc;
    }
}
