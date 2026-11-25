package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzara extends zzarc {
    zzara(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarc
    public final double zza(Object obj, long j) {
        return Double.longBitsToDouble(zzk(obj, j));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarc
    public final float zzb(Object obj, long j) {
        return Float.intBitsToFloat(zzj(obj, j));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarc
    public final void zzc(Object obj, long j, boolean z) {
        if (zzard.zzb) {
            zzard.zzD(obj, j, z ? (byte) 1 : (byte) 0);
        } else {
            zzard.zzE(obj, j, z ? (byte) 1 : (byte) 0);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarc
    public final void zzd(Object obj, long j, byte b) {
        if (zzard.zzb) {
            zzard.zzD(obj, j, b);
        } else {
            zzard.zzE(obj, j, b);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarc
    public final void zze(Object obj, long j, double d) {
        zzo(obj, j, Double.doubleToLongBits(d));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarc
    public final void zzf(Object obj, long j, float f) {
        zzn(obj, j, Float.floatToIntBits(f));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzarc
    public final boolean zzg(Object obj, long j) {
        return zzard.zzb ? zzard.zzt(obj, j) : zzard.zzu(obj, j);
    }
}
