package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzamd extends zzamh {
    private final int zzc;
    private final int zzd;

    zzamd(byte[] bArr, int i, int i2) {
        super(bArr);
        zzo(i, i + i2, bArr.length);
        this.zzc = i;
        this.zzd = i2;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamh, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    public final byte zza(int i) {
        zzx(i, this.zzd);
        return this.zza[this.zzc + i];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamh, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    final byte zzb(int i) {
        return this.zza[this.zzc + i];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamh
    protected final int zzc() {
        return this.zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamh, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    public final int zzd() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamh, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamk
    protected final void zze(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zza, this.zzc + i, bArr, i2, i3);
    }
}
