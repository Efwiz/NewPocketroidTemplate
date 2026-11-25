package com.google.android.gms.internal.mlkit_vision_text;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzae extends zzbx {
    final /* synthetic */ zzag zza;

    zzae(zzag zzagVar) {
        this.zza = zzagVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbx, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
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
        return new zzaf(this.zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbx, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!contains(obj)) {
            return false;
        }
        zzao.zzo(this.zza.zzb, ((Map.Entry) obj).getKey());
        return true;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzbx
    final Map zza() {
        return this.zza;
    }
}
