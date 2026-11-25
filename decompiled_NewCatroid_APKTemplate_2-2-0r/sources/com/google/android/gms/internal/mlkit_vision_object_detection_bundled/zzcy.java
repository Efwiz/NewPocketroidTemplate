package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcy extends AbstractSet<Map.Entry> {
    final /* synthetic */ zzdd zza;

    zzcy(zzdd zzddVar) {
        this.zza = zzddVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.zza.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return (obj instanceof Map.Entry) && this.zza.zzb((Map.Entry) obj) != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry> iterator() {
        return new zzcx(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        zzdc zzdcVarZzb;
        if (!(obj instanceof Map.Entry) || (zzdcVarZzb = this.zza.zzb((Map.Entry) obj)) == null) {
            return false;
        }
        this.zza.zze(zzdcVarZzb, true);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zza.zzc;
    }
}
