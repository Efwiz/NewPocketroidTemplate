package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzl extends zzbc {
    final /* synthetic */ zzn zza;

    zzl(zzn zznVar) {
        this.zza = zznVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzbc, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(@CheckForNull Object obj) {
        Set setEntrySet = this.zza.zza.entrySet();
        setEntrySet.getClass();
        try {
            return setEntrySet.contains(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry> iterator() {
        return new zzm(this.zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzbc, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(@CheckForNull Object obj) {
        if (!contains(obj)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        entry.getClass();
        zzv.zzk(this.zza.zzb, entry.getKey());
        return true;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzbc
    final Map zza() {
        return this.zza;
    }
}
