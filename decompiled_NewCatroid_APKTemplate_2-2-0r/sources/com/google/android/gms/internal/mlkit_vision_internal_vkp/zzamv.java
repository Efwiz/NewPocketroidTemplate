package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzamv extends zzams {
    private final OutputStream zzg;

    zzamv(OutputStream outputStream, int i) {
        super(i);
        if (outputStream == null) {
            throw new NullPointerException("out");
        }
        this.zzg = outputStream;
    }

    private final void zzT() throws IOException {
        this.zzg.write(this.zza, 0, this.zzc);
        this.zzc = 0;
    }

    private final void zzU(int i) throws IOException {
        if (this.zzb - this.zzc < i) {
            zzT();
        }
    }

    public final void zzA(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.zzb;
        int i4 = this.zzc;
        int i5 = i3 - i4;
        if (i5 >= i2) {
            System.arraycopy(bArr, i, this.zza, i4, i2);
            this.zzc += i2;
            this.zzd += i2;
            return;
        }
        System.arraycopy(bArr, i, this.zza, i4, i5);
        int i6 = i + i5;
        int i7 = i2 - i5;
        this.zzc = this.zzb;
        this.zzd += i5;
        zzT();
        if (i7 <= this.zzb) {
            System.arraycopy(bArr, i6, this.zza, 0, i7);
            this.zzc = i7;
        } else {
            this.zzg.write(bArr, i6, i7);
        }
        this.zzd += i7;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzV() throws IOException {
        if (this.zzc > 0) {
            zzT();
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzW(byte b) throws IOException {
        if (this.zzc == this.zzb) {
            zzT();
        }
        zzc(b);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzX(int i, boolean z) throws IOException {
        zzU(11);
        zzf(i << 3);
        zzc(z ? (byte) 1 : (byte) 0);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzY(byte[] bArr, int i, int i2) throws IOException {
        zzx(i2);
        zzA(bArr, 0, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaly
    public final void zza(byte[] bArr, int i, int i2) throws IOException {
        zzA(bArr, i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzh(int i, zzamk zzamkVar) throws IOException {
        zzx((i << 3) | 2);
        zzi(zzamkVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzi(zzamk zzamkVar) throws IOException {
        zzx(zzamkVar.zzd());
        zzamkVar.zzm(this);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzj(int i, int i2) throws IOException {
        zzU(14);
        zzf((i << 3) | 5);
        zzd(i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzk(int i) throws IOException {
        zzU(4);
        zzd(i);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzl(int i, long j) throws IOException {
        zzU(18);
        zzf((i << 3) | 1);
        zze(j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzm(long j) throws IOException {
        zzU(8);
        zze(j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzn(int i, int i2) throws IOException {
        zzU(20);
        zzf(i << 3);
        if (i2 >= 0) {
            zzf(i2);
        } else {
            zzg(i2);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzo(int i) throws IOException {
        if (i >= 0) {
            zzx(i);
        } else {
            zzz(i);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    final void zzp(int i, zzapf zzapfVar, zzapz zzapzVar) throws IOException {
        zzx((i << 3) | 2);
        zzalo zzaloVar = (zzalo) zzapfVar;
        int iZzr = zzaloVar.zzr();
        if (iZzr == -1) {
            iZzr = zzapzVar.zza(zzaloVar);
            zzaloVar.zzu(iZzr);
        }
        zzx(iZzr);
        zzapzVar.zzj(zzapfVar, this.zze);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzq(zzapf zzapfVar) throws IOException {
        zzx(zzapfVar.zzy());
        zzapfVar.zzQ(this);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzr(int i, zzapf zzapfVar) throws IOException {
        zzx(11);
        zzw(2, i);
        zzx(26);
        zzq(zzapfVar);
        zzx(12);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzs(int i, zzamk zzamkVar) throws IOException {
        zzx(11);
        zzw(2, i);
        zzh(3, zzamkVar);
        zzx(12);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzt(int i, String str) throws IOException {
        zzx((i << 3) | 2);
        zzu(str);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzu(String str) throws IOException {
        int iZze;
        try {
            int length = str.length() * 3;
            int iZzM = zzM(length);
            int i = iZzM + length;
            int i2 = this.zzb;
            if (i > i2) {
                byte[] bArr = new byte[length];
                int iZzd = zzari.zzd(str, bArr, 0, length);
                zzx(iZzd);
                zzA(bArr, 0, iZzd);
                return;
            }
            if (i > i2 - this.zzc) {
                zzT();
            }
            int iZzM2 = zzM(str.length());
            int i3 = this.zzc;
            try {
                if (iZzM2 == iZzM) {
                    int i4 = i3 + iZzM2;
                    this.zzc = i4;
                    int iZzd2 = zzari.zzd(str, this.zza, i4, this.zzb - i4);
                    this.zzc = i3;
                    iZze = (iZzd2 - i3) - iZzM2;
                    zzf(iZze);
                    this.zzc = iZzd2;
                } else {
                    iZze = zzari.zze(str);
                    zzf(iZze);
                    this.zzc = zzari.zzd(str, this.zza, this.zzc, iZze);
                }
                this.zzd += iZze;
            } catch (zzarh e) {
                this.zzd -= this.zzc - i3;
                this.zzc = i3;
                throw e;
            } catch (ArrayIndexOutOfBoundsException e2) {
                throw new zzamu(e2);
            }
        } catch (zzarh e3) {
            zzR(str, e3);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzv(int i, int i2) throws IOException {
        zzx((i << 3) | i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzw(int i, int i2) throws IOException {
        zzU(20);
        zzf(i << 3);
        zzf(i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzx(int i) throws IOException {
        zzU(5);
        zzf(i);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzy(int i, long j) throws IOException {
        zzU(20);
        zzf(i << 3);
        zzg(j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzz(long j) throws IOException {
        zzU(10);
        zzg(j);
    }
}
