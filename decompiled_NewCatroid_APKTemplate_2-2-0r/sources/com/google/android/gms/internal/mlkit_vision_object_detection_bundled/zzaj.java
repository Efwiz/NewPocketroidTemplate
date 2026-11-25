package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaj extends AbstractCollection {
    final /* synthetic */ zzak zza;

    zzaj(zzak zzakVar) {
        this.zza = zzakVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        this.zza.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        zzak zzakVar = this.zza;
        Map mapZzl = zzakVar.zzl();
        return mapZzl != null ? mapZzl.values().iterator() : new zzad(zzakVar);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.zza.size();
    }
}
