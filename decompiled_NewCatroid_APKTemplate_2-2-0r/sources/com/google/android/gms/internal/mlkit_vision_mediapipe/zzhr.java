package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzhr extends zzhp<zzhs, zzhs> {
    zzhr() {
    }

    /* JADX INFO: renamed from: zza, reason: avoid collision after fix types in other method */
    private static void zza2(Object obj, zzhs zzhsVar) {
        ((zzfb) obj).zzb = zzhsVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final void zzb(Object obj) {
        ((zzfb) obj).zzb.zzc();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final /* synthetic */ int zzd(zzhs zzhsVar) {
        return zzhsVar.zze();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final /* synthetic */ int zzc(zzhs zzhsVar) {
        return zzhsVar.zzd();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final /* synthetic */ zzhs zzc(zzhs zzhsVar, zzhs zzhsVar2) {
        zzhs zzhsVar3 = zzhsVar;
        zzhs zzhsVar4 = zzhsVar2;
        return zzhsVar4.equals(zzhs.zza()) ? zzhsVar3 : zzhs.zza(zzhsVar3, zzhsVar4);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final /* synthetic */ void zzb(zzhs zzhsVar, zzim zzimVar) throws IOException {
        zzhsVar.zza(zzimVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final /* synthetic */ void zza(zzhs zzhsVar, zzim zzimVar) throws IOException {
        zzhsVar.zzb(zzimVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final /* synthetic */ void zzb(Object obj, zzhs zzhsVar) {
        zza2(obj, zzhsVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final /* synthetic */ zzhs zza(Object obj) {
        return ((zzfb) obj).zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final /* bridge */ /* synthetic */ void zza(Object obj, zzhs zzhsVar) {
        zza2(obj, zzhsVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final /* synthetic */ zzhs zza() {
        return zzhs.zzb();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final /* synthetic */ void zza(zzhs zzhsVar, int i, zzdu zzduVar) {
        zzhsVar.zza((i << 3) | 2, zzduVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhp
    final /* synthetic */ void zza(zzhs zzhsVar, int i, long j) {
        zzhsVar.zza(i << 3, Long.valueOf(j));
    }
}
