package com.google.android.gms.internal.mlkit_language_id;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdq extends zzdx {
    private final int zzc;
    private final int zzd;

    zzdq(byte[] bArr, int i, int i2) {
        super(bArr);
        zzb(i, i + i2, bArr.length);
        this.zzc = i;
        this.zzd = i2;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdx, com.google.android.gms.internal.mlkit_language_id.zzdn
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

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdx, com.google.android.gms.internal.mlkit_language_id.zzdn
    final byte zzb(int i) {
        return this.zzb[this.zzc + i];
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdx, com.google.android.gms.internal.mlkit_language_id.zzdn
    public final int zza() {
        return this.zzd;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdx
    protected final int zze() {
        return this.zzc;
    }
}
