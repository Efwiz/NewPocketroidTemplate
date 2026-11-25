package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzge implements zzgb {
    zzge() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgb
    public final Map<?, ?> zza(Object obj) {
        return (zzgc) obj;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgb
    public final zzfz<?, ?> zzf(Object obj) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgb
    public final Map<?, ?> zzb(Object obj) {
        return (zzgc) obj;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgb
    public final boolean zzc(Object obj) {
        return !((zzgc) obj).zzd();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgb
    public final Object zzd(Object obj) {
        ((zzgc) obj).zzc();
        return obj;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgb
    public final Object zze(Object obj) {
        return zzgc.zza().zzb();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgb
    public final Object zza(Object obj, Object obj2) {
        zzgc zzgcVarZzb = (zzgc) obj;
        zzgc zzgcVar = (zzgc) obj2;
        if (!zzgcVar.isEmpty()) {
            if (!zzgcVarZzb.zzd()) {
                zzgcVarZzb = zzgcVarZzb.zzb();
            }
            zzgcVarZzb.zza(zzgcVar);
        }
        return zzgcVarZzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzgb
    public final int zza(int i, Object obj, Object obj2) {
        zzgc zzgcVar = (zzgc) obj;
        if (zzgcVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzgcVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
