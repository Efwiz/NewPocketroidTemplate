package com.google.android.gms.internal.mlkit_vision_face;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzaf implements Iterator<Map.Entry> {
    final Iterator<Map.Entry> zza;

    @NullableDecl
    Collection zzb;
    final /* synthetic */ zzag zzc;

    zzaf(zzag zzagVar) {
        this.zzc = zzagVar;
        this.zza = zzagVar.zza.entrySet().iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ Map.Entry next() {
        Map.Entry next = this.zza.next();
        this.zzb = (Collection) next.getValue();
        zzag zzagVar = this.zzc;
        Object key = next.getKey();
        return new zzbh(key, zzagVar.zzb.zza(key, (Collection) next.getValue()));
    }

    @Override // java.util.Iterator
    public final void remove() {
        zzaa.zza(this.zzb != null, "no calls to next() since the last call to remove()");
        this.zza.remove();
        zzao.zzn(this.zzc.zzb, this.zzb.size());
        this.zzb.clear();
        this.zzb = null;
    }
}
