package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzm implements Iterator<Map.Entry> {
    final Iterator<Map.Entry> zza;

    @CheckForNull
    Collection zzb;
    final /* synthetic */ zzn zzc;

    zzm(zzn zznVar) {
        this.zzc = zznVar;
        this.zza = zznVar.zza.entrySet().iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.zza.hasNext();
    }

    @Override // java.util.Iterator
    public final /* bridge */ /* synthetic */ Map.Entry next() {
        Map.Entry next = this.zza.next();
        this.zzb = (Collection) next.getValue();
        zzn zznVar = this.zzc;
        Object key = next.getKey();
        return new zzaq(key, zznVar.zzb.zzb(key, (Collection) next.getValue()));
    }

    @Override // java.util.Iterator
    public final void remove() {
        zzh.zze(this.zzb != null, "no calls to next() since the last call to remove()");
        this.zza.remove();
        this.zzc.zzb.zzb -= this.zzb.size();
        this.zzb.clear();
        this.zzb = null;
    }
}
