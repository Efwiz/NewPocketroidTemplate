package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzdb<T> implements Iterator<T> {
    zzdc zza;
    zzdc zzb = null;
    int zzc;
    final /* synthetic */ zzdd zzd;

    zzdb(zzdd zzddVar) {
        this.zzd = zzddVar;
        this.zza = zzddVar.zze.zzd;
        this.zzc = zzddVar.zzd;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza != this.zzd.zze;
    }

    @Override // java.util.Iterator
    public final void remove() {
        zzdc zzdcVar = this.zzb;
        if (zzdcVar == null) {
            throw new IllegalStateException();
        }
        this.zzd.zze(zzdcVar, true);
        this.zzb = null;
        this.zzc = this.zzd.zzd;
    }

    final zzdc zza() {
        zzdc zzdcVar = this.zza;
        zzdd zzddVar = this.zzd;
        if (zzdcVar == zzddVar.zze) {
            throw new NoSuchElementException();
        }
        if (zzddVar.zzd != this.zzc) {
            throw new ConcurrentModificationException();
        }
        this.zza = zzdcVar.zzd;
        this.zzb = zzdcVar;
        return zzdcVar;
    }
}
