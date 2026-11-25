package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzvs<T> implements Iterator<T> {
    zzvt zza;
    zzvt zzb = null;
    int zzc;
    final /* synthetic */ zzvu zzd;

    zzvs(zzvu zzvuVar) {
        this.zzd = zzvuVar;
        this.zza = zzvuVar.zze.zzd;
        this.zzc = zzvuVar.zzd;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza != this.zzd.zze;
    }

    @Override // java.util.Iterator
    public final void remove() {
        zzvt zzvtVar = this.zzb;
        if (zzvtVar == null) {
            throw new IllegalStateException();
        }
        this.zzd.zze(zzvtVar, true);
        this.zzb = null;
        this.zzc = this.zzd.zzd;
    }

    final zzvt zza() {
        zzvt zzvtVar = this.zza;
        zzvu zzvuVar = this.zzd;
        if (zzvtVar == zzvuVar.zze) {
            throw new NoSuchElementException();
        }
        if (zzvuVar.zzd != this.zzc) {
            throw new ConcurrentModificationException();
        }
        this.zza = zzvtVar.zzd;
        this.zzb = zzvtVar;
        return zzvtVar;
    }
}
