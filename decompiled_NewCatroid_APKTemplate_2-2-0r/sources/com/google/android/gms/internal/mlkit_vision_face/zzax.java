package com.google.android.gms.internal.mlkit_vision_face;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzax<T> implements Iterator<T> {
    int zzb;
    int zzc;
    int zzd = -1;
    final /* synthetic */ zzbb zze;

    /* synthetic */ zzax(zzbb zzbbVar, zzat zzatVar) {
        this.zze = zzbbVar;
        this.zzb = zzbbVar.zzf;
        this.zzc = zzbbVar.zzf();
    }

    private final void zzb() {
        if (this.zze.zzf != this.zzb) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzc >= 0;
    }

    @Override // java.util.Iterator
    public final T next() {
        zzb();
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int i = this.zzc;
        this.zzd = i;
        T tZza = zza(i);
        this.zzc = this.zze.zzg(this.zzc);
        return tZza;
    }

    @Override // java.util.Iterator
    public final void remove() {
        zzb();
        zzaa.zza(this.zzd >= 0, "no calls to next() since the last call to remove()");
        this.zzb += 32;
        zzbb zzbbVar = this.zze;
        zzbbVar.remove(zzbbVar.zzb[this.zzd]);
        this.zzc--;
        this.zzd = -1;
    }

    abstract T zza(int i);
}
