package com.google.android.gms.internal.gtm;

import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzxs implements Iterator<String> {
    final Iterator<String> zza;
    final /* synthetic */ zzxt zzb;

    zzxs(zzxt zzxtVar) {
        this.zzb = zzxtVar;
        this.zza = zzxtVar.zza.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ String next() {
        return this.zza.next();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
