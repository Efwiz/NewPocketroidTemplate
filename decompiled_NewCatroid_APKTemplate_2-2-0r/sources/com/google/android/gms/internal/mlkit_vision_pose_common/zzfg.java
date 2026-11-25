package com.google.android.gms.internal.mlkit_vision_pose_common;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfg extends zzfj {
    private final int zzc;
    private final int zzd;

    zzfg(byte[] bArr, int i, int i2) {
        super(bArr);
        zzb(i, i + i2, bArr.length);
        this.zzc = i;
        this.zzd = i2;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfj, com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    public final byte zza(int i) {
        int iZza = zza();
        if (((iZza - (i + 1)) | i) >= 0) {
            return this.zzb[this.zzc + i];
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(22).append("Index < 0: ").append(i).toString());
        }
        throw new ArrayIndexOutOfBoundsException(new StringBuilder(40).append("Index > length: ").append(i).append(", ").append(iZza).toString());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfj, com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    final byte zzb(int i) {
        return this.zzb[this.zzc + i];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfj, com.google.android.gms.internal.mlkit_vision_pose_common.zzez
    public final int zza() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzfj
    protected final int zze() {
        return this.zzc;
    }
}
