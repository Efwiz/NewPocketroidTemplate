package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Add missing generic type declarations: [K] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzo<K> implements Iterator<K> {

    @NullableDecl
    private Map.Entry<K, Collection<V>> zza;
    private final /* synthetic */ Iterator zzb;
    private final /* synthetic */ zzp zzc;

    zzo(zzp zzpVar, Iterator it) {
        this.zzc = zzpVar;
        this.zzb = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzb.hasNext();
    }

    @Override // java.util.Iterator
    public final K next() {
        Map.Entry<K, Collection<V>> entry = (Map.Entry) this.zzb.next();
        this.zza = entry;
        return entry.getKey();
    }

    @Override // java.util.Iterator
    public final void remove() {
        zzh.zza(this.zza != null, "no calls to next() since the last call to remove()");
        Collection collection = (Collection) this.zza.getValue();
        this.zzb.remove();
        zzl.zzb(this.zzc.zza, collection.size());
        collection.clear();
        this.zza = null;
    }
}
