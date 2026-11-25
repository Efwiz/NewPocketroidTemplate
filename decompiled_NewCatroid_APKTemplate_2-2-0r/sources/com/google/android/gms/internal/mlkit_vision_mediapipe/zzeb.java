package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzeb extends zzee {
    private final int zzc;
    private final int zzd;

    zzeb(byte[] bArr, int i, int i2) {
        super(bArr);
        zzb(i, i + i2, bArr.length);
        this.zzc = i;
        this.zzd = i2;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzee, com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
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

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzee, com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
    final byte zzb(int i) {
        return this.zzb[this.zzc + i];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzee, com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
    public final int zza() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzee
    protected final int zze() {
        return this.zzc;
    }
}
