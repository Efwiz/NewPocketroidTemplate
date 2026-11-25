package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.Iterator;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaqx implements Iterator<String> {
    final Iterator<String> zza;
    final /* synthetic */ zzaqy zzb;

    zzaqx(zzaqy zzaqyVar) {
        this.zzb = zzaqyVar;
        this.zza = zzaqyVar.zza.iterator();
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
