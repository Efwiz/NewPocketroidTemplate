package com.google.android.gms.internal.mlkit_vision_face;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzba extends AbstractCollection {
    final /* synthetic */ zzbb zza;

    zzba(zzbb zzbbVar) {
        this.zza = zzbbVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        this.zza.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        zzbb zzbbVar = this.zza;
        Map mapZzc = zzbbVar.zzc();
        return mapZzc != null ? mapZzc.values().iterator() : new zzav(zzbbVar);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.zza.size();
    }
}
