package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.Comparator;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbr implements Comparator<Object> {
    zzbr() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        zzbp.zza zzaVarZzb = zzbp.zza.zzb(obj);
        zzbp.zza zzaVarZzb2 = zzbp.zza.zzb(obj2);
        return zzaVarZzb == zzaVarZzb2 ? zzaVarZzb.zza(obj, obj2) : zzaVarZzb.compareTo(zzaVarZzb2);
    }
}
