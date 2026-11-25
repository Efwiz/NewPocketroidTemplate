package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzah<T> implements Iterator<T> {
    private int zza;
    private int zzb;
    private int zzc;
    private final /* synthetic */ zzaa zzd;

    private zzah(zzaa zzaaVar) {
        this.zzd = zzaaVar;
        this.zza = zzaaVar.zzf;
        this.zzb = zzaaVar.zzd();
        this.zzc = -1;
    }

    abstract T zza(int i);

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.zzb >= 0;
    }

    @Override // java.util.Iterator
    public T next() {
        zza();
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i = this.zzb;
        this.zzc = i;
        T tZza = zza(i);
        this.zzb = this.zzd.zza(this.zzb);
        return tZza;
    }

    @Override // java.util.Iterator
    public void remove() {
        zza();
        zzh.zza(this.zzc >= 0, "no calls to next() since the last call to remove()");
        this.zza += 32;
        zzaa zzaaVar = this.zzd;
        zzaaVar.remove(zzaaVar.zzb[this.zzc]);
        this.zzb = zzaa.zzb(this.zzb, this.zzc);
        this.zzc = -1;
    }

    private final void zza() {
        if (this.zzd.zzf != this.zza) {
            throw new ConcurrentModificationException();
        }
    }

    /* synthetic */ zzah(zzaa zzaaVar, zzad zzadVar) {
        this(zzaaVar);
    }
}
