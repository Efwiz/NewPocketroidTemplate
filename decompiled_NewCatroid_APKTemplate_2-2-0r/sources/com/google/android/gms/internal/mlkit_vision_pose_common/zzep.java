package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzep {
    public static zzbm.zzae zza(int i, int i2) {
        zzbm.zzae.zza zzaVar;
        zzbm.zzae.zzb zzbVarZza = zzbm.zzae.zza();
        if (i == -1) {
            zzaVar = zzbm.zzae.zza.BITMAP;
        } else if (i == 35) {
            zzaVar = zzbm.zzae.zza.YUV_420_888;
        } else if (i == 842094169) {
            zzaVar = zzbm.zzae.zza.YV12;
        } else if (i != 16) {
            zzaVar = i != 17 ? zzbm.zzae.zza.UNKNOWN_FORMAT : zzbm.zzae.zza.NV21;
        } else {
            zzaVar = zzbm.zzae.zza.NV16;
        }
        return (zzbm.zzae) ((zzgd) zzbVarZza.zza(zzaVar).zza(i2).zzh());
    }
}
