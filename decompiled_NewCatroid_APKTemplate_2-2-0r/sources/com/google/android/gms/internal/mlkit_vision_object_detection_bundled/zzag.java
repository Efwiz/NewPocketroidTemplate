package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzag<T> implements Iterator<T> {
    int zzb;
    int zzc;
    int zzd = -1;
    final /* synthetic */ zzak zze;

    /* synthetic */ zzag(zzak zzakVar, zzaf zzafVar) {
        this.zze = zzakVar;
        this.zzb = zzakVar.zzf;
        this.zzc = zzakVar.zze();
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
        this.zzc = this.zze.zzf(this.zzc);
        return tZza;
    }

    @Override // java.util.Iterator
    public final void remove() {
        zzb();
        zzh.zze(this.zzd >= 0, "no calls to next() since the last call to remove()");
        this.zzb += 32;
        zzak zzakVar = this.zze;
        zzakVar.remove(zzak.zzg(zzakVar, this.zzd));
        this.zzc--;
        this.zzd = -1;
    }

    abstract T zza(int i);
}
