package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhd extends zzhj {
    private final /* synthetic */ zzhc zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private zzhd(zzhc zzhcVar) {
        super(zzhcVar, null);
        this.zza = zzhcVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhj, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzhe(this.zza, null);
    }

    /* synthetic */ zzhd(zzhc zzhcVar, zzhb zzhbVar) {
        this(zzhcVar);
    }
}
