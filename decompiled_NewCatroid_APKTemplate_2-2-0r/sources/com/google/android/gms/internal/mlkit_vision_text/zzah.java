package com.google.android.gms.internal.mlkit_vision_text;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzah implements Iterator {

    @NullableDecl
    Map.Entry zza;
    final /* synthetic */ Iterator zzb;
    final /* synthetic */ zzai zzc;

    zzah(zzai zzaiVar, Iterator it) {
        this.zzc = zzaiVar;
        this.zzb = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zzb.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        Map.Entry entry = (Map.Entry) this.zzb.next();
        this.zza = entry;
        return entry.getKey();
    }

    @Override // java.util.Iterator
    public final void remove() {
        zzaa.zza(this.zza != null, "no calls to next() since the last call to remove()");
        Collection collection = (Collection) this.zza.getValue();
        this.zzb.remove();
        zzao.zzn(this.zzc.zza, collection.size());
        collection.clear();
        this.zza = null;
    }
}
