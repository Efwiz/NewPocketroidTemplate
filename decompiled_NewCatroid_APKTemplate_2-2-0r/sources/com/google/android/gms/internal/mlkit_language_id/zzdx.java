package com.google.android.gms.internal.mlkit_language_id;

import java.io.IOException;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
class zzdx extends zzdu {
    protected final byte[] zzb;

    zzdx(byte[] bArr) {
        bArr.getClass();
        this.zzb = bArr;
    }

    protected int zze() {
        return 0;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdn
    public byte zza(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdn
    byte zzb(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdn
    public int zza() {
        return this.zzb.length;
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdn
    public final zzdn zza(int i, int i2) {
        int iZzb = zzb(0, i2, zza());
        if (iZzb == 0) {
            return zzdn.zza;
        }
        return new zzdq(this.zzb, zze(), iZzb);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdn
    final void zza(zzdk zzdkVar) throws IOException {
        zzdkVar.zza(this.zzb, zze(), zza());
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdn
    protected final String zza(Charset charset) {
        return new String(this.zzb, zze(), zza(), charset);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdn
    public final boolean zzc() {
        int iZze = zze();
        return zzhp.zza(this.zzb, iZze, zza() + iZze);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdn
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzdn) || zza() != ((zzdn) obj).zza()) {
            return false;
        }
        if (zza() == 0) {
            return true;
        }
        if (obj instanceof zzdx) {
            zzdx zzdxVar = (zzdx) obj;
            int iZzd = zzd();
            int iZzd2 = zzdxVar.zzd();
            if (iZzd == 0 || iZzd2 == 0 || iZzd == iZzd2) {
                return zza(zzdxVar, 0, zza());
            }
            return false;
        }
        return obj.equals(this);
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdu
    final boolean zza(zzdn zzdnVar, int i, int i2) {
        if (i2 > zzdnVar.zza()) {
            throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(i2).append(zza()).toString());
        }
        if (i2 > zzdnVar.zza()) {
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: 0, ").append(i2).append(", ").append(zzdnVar.zza()).toString());
        }
        if (zzdnVar instanceof zzdx) {
            zzdx zzdxVar = (zzdx) zzdnVar;
            byte[] bArr = this.zzb;
            byte[] bArr2 = zzdxVar.zzb;
            int iZze = zze() + i2;
            int iZze2 = zze();
            int iZze3 = zzdxVar.zze();
            while (iZze2 < iZze) {
                if (bArr[iZze2] != bArr2[iZze3]) {
                    return false;
                }
                iZze2++;
                iZze3++;
            }
            return true;
        }
        return zzdnVar.zza(0, i2).equals(zza(0, i2));
    }

    @Override // com.google.android.gms.internal.mlkit_language_id.zzdn
    protected final int zza(int i, int i2, int i3) {
        return zzeq.zza(i, this.zzb, zze(), i3);
    }
}
