package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.io.IOException;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
class zzee extends zzef {
    protected final byte[] zzb;

    zzee(byte[] bArr) {
        bArr.getClass();
        this.zzb = bArr;
    }

    protected int zze() {
        return 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
    public byte zza(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
    byte zzb(int i) {
        return this.zzb[i];
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
    public int zza() {
        return this.zzb.length;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
    public final zzdu zza(int i, int i2) {
        int iZzb = zzb(0, i2, zza());
        if (iZzb == 0) {
            return zzdu.zza;
        }
        return new zzeb(this.zzb, zze(), iZzb);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
    final void zza(zzdv zzdvVar) throws IOException {
        zzdvVar.zza(this.zzb, zze(), zza());
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
    protected final String zza(Charset charset) {
        return new String(this.zzb, zze(), zza(), charset);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
    public final boolean zzc() {
        int iZze = zze();
        return zzhy.zza(this.zzb, iZze, zza() + iZze);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzdu) || zza() != ((zzdu) obj).zza()) {
            return false;
        }
        if (zza() == 0) {
            return true;
        }
        if (obj instanceof zzee) {
            zzee zzeeVar = (zzee) obj;
            int iZzd = zzd();
            int iZzd2 = zzeeVar.zzd();
            if (iZzd == 0 || iZzd2 == 0 || iZzd == iZzd2) {
                return zza(zzeeVar, 0, zza());
            }
            return false;
        }
        return obj.equals(this);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzef
    final boolean zza(zzdu zzduVar, int i, int i2) {
        if (i2 > zzduVar.zza()) {
            throw new IllegalArgumentException(new StringBuilder(40).append("Length too large: ").append(i2).append(zza()).toString());
        }
        if (i2 > zzduVar.zza()) {
            throw new IllegalArgumentException(new StringBuilder(59).append("Ran off end of other: 0, ").append(i2).append(", ").append(zzduVar.zza()).toString());
        }
        if (zzduVar instanceof zzee) {
            zzee zzeeVar = (zzee) zzduVar;
            byte[] bArr = this.zzb;
            byte[] bArr2 = zzeeVar.zzb;
            int iZze = zze() + i2;
            int iZze2 = zze();
            int iZze3 = zzeeVar.zze();
            while (iZze2 < iZze) {
                if (bArr[iZze2] != bArr2[iZze3]) {
                    return false;
                }
                iZze2++;
                iZze3++;
            }
            return true;
        }
        return zzduVar.zza(0, i2).equals(zza(0, i2));
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzdu
    protected final int zza(int i, int i2, int i3) {
        return zzfe.zza(i, this.zzb, zze(), i3);
    }
}
