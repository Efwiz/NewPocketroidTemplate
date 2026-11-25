package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzm<K, V> implements Iterator<Map.Entry<K, Collection<V>>> {
    private final Iterator<Map.Entry<K, Collection<V>>> zza;

    @NullableDecl
    private Collection<V> zzb;
    private final /* synthetic */ zzk zzc;

    zzm(zzk zzkVar) {
        this.zzc = zzkVar;
        this.zza = zzkVar.zza.entrySet().iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public final void remove() {
        zzh.zza(this.zzb != null, "no calls to next() since the last call to remove()");
        this.zza.remove();
        zzl.zzb(this.zzc.zzb, this.zzb.size());
        this.zzb.clear();
        this.zzb = null;
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        Map.Entry<K, Collection<V>> next = this.zza.next();
        this.zzb = next.getValue();
        zzk zzkVar = this.zzc;
        K key = next.getKey();
        return new zzao(key, zzkVar.zzb.zza((Object) key, (Collection) next.getValue()));
    }
}
