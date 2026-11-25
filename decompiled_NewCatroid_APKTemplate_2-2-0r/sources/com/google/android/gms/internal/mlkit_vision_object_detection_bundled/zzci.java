package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzci extends zzcj implements Iterable<zzcj> {
    private final List<zzcj> zza = new ArrayList();

    public final boolean equals(Object obj) {
        if (obj != this) {
            return (obj instanceof zzci) && ((zzci) obj).zza.equals(this.zza);
        }
        return true;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    @Override // java.lang.Iterable
    public final Iterator<zzcj> iterator() {
        return this.zza.iterator();
    }

    public final void zza(zzcj zzcjVar) {
        this.zza.add(zzcjVar);
    }
}
