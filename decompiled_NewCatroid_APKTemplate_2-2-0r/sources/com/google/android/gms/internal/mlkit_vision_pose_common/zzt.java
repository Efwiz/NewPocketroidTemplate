package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/* JADX INFO: Add missing generic type declarations: [V] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
class zzt<V> implements Iterator<V> {
    final Iterator<V> zza;
    private final Collection<V> zzb;
    private final /* synthetic */ zzq zzc;

    zzt(zzq zzqVar) {
        this.zzc = zzqVar;
        this.zzb = zzqVar.zzb;
        this.zza = zzl.zzb((Collection) zzqVar.zzb);
    }

    zzt(zzq zzqVar, Iterator<V> it) {
        this.zzc = zzqVar;
        this.zzb = zzqVar.zzb;
        this.zza = it;
    }

    final void zza() {
        this.zzc.zza();
        if (this.zzc.zzb != this.zzb) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        zza();
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public V next() {
        zza();
        return this.zza.next();
    }

    @Override // java.util.Iterator
    public void remove() {
        this.zza.remove();
        zzl.zzb(this.zzc.zzd);
        this.zzc.zzb();
    }
}
