package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzih extends zzin {
    private final /* synthetic */ zzig zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private zzih(zzig zzigVar) {
        super(zzigVar, null);
        this.zza = zzigVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzin, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry<K, V>> iterator() {
        return new zzii(this.zza, null);
    }

    /* synthetic */ zzih(zzig zzigVar, zzif zzifVar) {
        this(zzigVar);
    }
}
