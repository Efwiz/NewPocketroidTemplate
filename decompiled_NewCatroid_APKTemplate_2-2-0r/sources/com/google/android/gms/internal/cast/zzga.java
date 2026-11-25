package com.google.android.gms.internal.cast;

import java.util.Iterator;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzga extends zzfu {
    private final transient zzft zza;
    private final transient zzfq zzb;

    zzga(zzft zzftVar, zzfq zzfqVar) {
        this.zza = zzftVar;
        this.zzb = zzfqVar;
    }

    @Override // com.google.android.gms.internal.cast.zzfm, java.util.AbstractCollection, java.util.Collection
    public final boolean contains(@CheckForNull Object obj) {
        return this.zza.get(obj) != null;
    }

    @Override // com.google.android.gms.internal.cast.zzfu, com.google.android.gms.internal.cast.zzfm, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Iterator iterator() {
        return this.zzb.listIterator(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zza.size();
    }

    @Override // com.google.android.gms.internal.cast.zzfm
    final int zza(Object[] objArr, int i) {
        return this.zzb.zza(objArr, 0);
    }

    @Override // com.google.android.gms.internal.cast.zzfu, com.google.android.gms.internal.cast.zzfm
    public final zzfq zzd() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.cast.zzfu, com.google.android.gms.internal.cast.zzfm
    /* JADX INFO: renamed from: zze */
    public final zzgg iterator() {
        return this.zzb.listIterator(0);
    }

    @Override // com.google.android.gms.internal.cast.zzfm
    final boolean zzf() {
        throw null;
    }
}
