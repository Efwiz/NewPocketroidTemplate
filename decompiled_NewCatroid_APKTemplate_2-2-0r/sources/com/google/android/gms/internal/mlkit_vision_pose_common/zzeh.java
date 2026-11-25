package com.google.android.gms.internal.mlkit_vision_pose_common;

import com.google.firebase.components.Component;
import com.google.firebase.components.Dependency;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzeh implements zzej.zzc {
    public static final Component<?> zza = Component.builder(zzej.zzc.class).add(Dependency.required((Class<?>) zzeg.class)).factory(zzek.zza).build();
    private final zzeg zzb;

    public zzeh(zzeg zzegVar) {
        this.zzb = zzegVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzej.zzc
    public final void zza(zzbm.zzad zzadVar) {
        this.zzb.zza((zzbm.zzad) ((zzgd) zzbm.zzad.zza(zzadVar).zza(zzbm.zzbh.zza(zzadVar.zza()).zza(true)).zzh()));
    }
}
