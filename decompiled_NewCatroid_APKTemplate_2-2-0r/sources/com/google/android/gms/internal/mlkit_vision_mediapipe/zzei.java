package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzei extends zzeg {
    private final byte[] zza;
    private final boolean zzb;
    private int zzc;
    private int zzd;
    private int zze;
    private int zzf;
    private int zzg;

    private zzei(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzg = Integer.MAX_VALUE;
        this.zza = bArr;
        this.zzc = i2 + i;
        this.zze = i;
        this.zzf = i;
        this.zzb = z;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzeg
    public final int zza(int i) throws zzfi {
        if (i < 0) {
            throw zzfi.zzb();
        }
        int iZza = i + zza();
        int i2 = this.zzg;
        if (iZza > i2) {
            throw zzfi.zza();
        }
        this.zzg = iZza;
        int i3 = this.zzc + this.zzd;
        this.zzc = i3;
        int i4 = i3 - this.zzf;
        if (i4 > iZza) {
            int i5 = i4 - iZza;
            this.zzd = i5;
            this.zzc = i3 - i5;
        } else {
            this.zzd = 0;
        }
        return i2;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzeg
    public final int zza() {
        return this.zze - this.zzf;
    }
}
