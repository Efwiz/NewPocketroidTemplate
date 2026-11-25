package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.IOException;
import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzes extends zzeq<zzfb.zze> {
    zzes() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzeq
    final boolean zza(zzgi zzgiVar) {
        return zzgiVar instanceof zzfb.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzeq
    final zzeu<zzfb.zze> zza(Object obj) {
        return ((zzfb.zzb) obj).zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzeq
    final zzeu<zzfb.zze> zzb(Object obj) {
        return ((zzfb.zzb) obj).zza();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzeq
    final void zzc(Object obj) {
        zza(obj).zzb();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzeq
    final int zza(Map.Entry<?, ?> entry) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzeq
    final void zza(zzim zzimVar, Map.Entry<?, ?> entry) throws IOException {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzeq
    final Object zza(zzeo zzeoVar, zzgi zzgiVar, int i) {
        return zzeoVar.zza(zzgiVar, i);
    }
}
