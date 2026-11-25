package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzamt extends zzamx {
    private final byte[] zza;
    private final int zzb;
    private int zzc;

    zzamt(byte[] bArr, int i, int i2) {
        super(null);
        if (bArr == null) {
            throw new NullPointerException("buffer");
        }
        int length = bArr.length;
        if (((length - i2) | i2) < 0) {
            throw new IllegalArgumentException(String.format("Array range is invalid. Buffer.length=%d, offset=%d, length=%d", Integer.valueOf(length), 0, Integer.valueOf(i2)));
        }
        this.zza = bArr;
        this.zzc = 0;
        this.zzb = i2;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzV() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzX(int i, boolean z) throws IOException {
        zzx(i << 3);
        zzW(z ? (byte) 1 : (byte) 0);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzY(byte[] bArr, int i, int i2) throws IOException {
        zzx(i2);
        zze(bArr, 0, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx, com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaly
    public final void zza(byte[] bArr, int i, int i2) throws IOException {
        zze(bArr, i, i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final int zzb() {
        return this.zzb - this.zzc;
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
        zzx((i << 3) | 5);
        zzk(i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzk(int i) throws IOException {
        try {
            byte[] bArr = this.zza;
            int i2 = this.zzc;
            int i3 = i2 + 1;
            this.zzc = i3;
            bArr[i2] = (byte) (i & 255);
            int i4 = i2 + 2;
            this.zzc = i4;
            bArr[i3] = (byte) ((i >> 8) & 255);
            int i5 = i2 + 3;
            this.zzc = i5;
            bArr[i4] = (byte) ((i >> 16) & 255);
            this.zzc = i2 + 4;
            bArr[i5] = (byte) ((i >> 24) & 255);
        } catch (IndexOutOfBoundsException e) {
            throw new zzamu(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zzc), Integer.valueOf(this.zzb), 1), e);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzl(int i, long j) throws IOException {
        zzx((i << 3) | 1);
        zzm(j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzm(long j) throws IOException {
        try {
            byte[] bArr = this.zza;
            int i = this.zzc;
            int i2 = i + 1;
            this.zzc = i2;
            bArr[i] = (byte) (((int) j) & 255);
            int i3 = i + 2;
            this.zzc = i3;
            bArr[i2] = (byte) (((int) (j >> 8)) & 255);
            int i4 = i + 3;
            this.zzc = i4;
            bArr[i3] = (byte) (((int) (j >> 16)) & 255);
            int i5 = i + 4;
            this.zzc = i5;
            bArr[i4] = (byte) (((int) (j >> 24)) & 255);
            int i6 = i + 5;
            this.zzc = i6;
            bArr[i5] = (byte) (((int) (j >> 32)) & 255);
            int i7 = i + 6;
            this.zzc = i7;
            bArr[i6] = (byte) (((int) (j >> 40)) & 255);
            int i8 = i + 7;
            this.zzc = i8;
            bArr[i7] = (byte) (((int) (j >> 48)) & 255);
            this.zzc = i + 8;
            bArr[i8] = (byte) (((int) (j >> 56)) & 255);
        } catch (IndexOutOfBoundsException e) {
            throw new zzamu(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zzc), Integer.valueOf(this.zzb), 1), e);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzn(int i, int i2) throws IOException {
        zzx(i << 3);
        zzo(i2);
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
        int i = this.zzc;
        try {
            int iZzM = zzM(str.length() * 3);
            int iZzM2 = zzM(str.length());
            if (iZzM2 != iZzM) {
                zzx(zzari.zze(str));
                byte[] bArr = this.zza;
                int i2 = this.zzc;
                this.zzc = zzari.zzd(str, bArr, i2, this.zzb - i2);
                return;
            }
            int i3 = i + iZzM2;
            this.zzc = i3;
            int iZzd = zzari.zzd(str, this.zza, i3, this.zzb - i3);
            this.zzc = i;
            zzx((iZzd - i) - iZzM2);
            this.zzc = iZzd;
        } catch (zzarh e) {
            this.zzc = i;
            zzR(str, e);
        } catch (IndexOutOfBoundsException e2) {
            throw new zzamu(e2);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzv(int i, int i2) throws IOException {
        zzx((i << 3) | i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzw(int i, int i2) throws IOException {
        zzx(i << 3);
        zzx(i2);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzx(int i) throws IOException {
        if (zzamx.zzb) {
            int i2 = zzalr.zza;
        }
        while ((i & (-128)) != 0) {
            try {
                byte[] bArr = this.zza;
                int i3 = this.zzc;
                this.zzc = i3 + 1;
                bArr[i3] = (byte) ((i & 127) | 128);
                i >>>= 7;
            } catch (IndexOutOfBoundsException e) {
                throw new zzamu(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zzc), Integer.valueOf(this.zzb), 1), e);
            }
        }
        byte[] bArr2 = this.zza;
        int i4 = this.zzc;
        this.zzc = i4 + 1;
        bArr2[i4] = (byte) i;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzy(int i, long j) throws IOException {
        zzx(i << 3);
        zzz(j);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzW(byte b) throws IOException {
        try {
            byte[] bArr = this.zza;
            int i = this.zzc;
            this.zzc = i + 1;
            bArr[i] = b;
        } catch (IndexOutOfBoundsException e) {
            throw new zzamu(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zzc), Integer.valueOf(this.zzb), 1), e);
        }
    }

    public final void zze(byte[] bArr, int i, int i2) throws IOException {
        try {
            System.arraycopy(bArr, i, this.zza, this.zzc, i2);
            this.zzc += i2;
        } catch (IndexOutOfBoundsException e) {
            throw new zzamu(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zzc), Integer.valueOf(this.zzb), Integer.valueOf(i2)), e);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzamx
    public final void zzz(long j) throws IOException {
        if (zzamx.zzb && this.zzb - this.zzc >= 10) {
            while ((j & (-128)) != 0) {
                byte[] bArr = this.zza;
                int i = this.zzc;
                this.zzc = i + 1;
                zzard.zzn(bArr, i, (byte) ((((int) j) & 127) | 128));
                j >>>= 7;
            }
            byte[] bArr2 = this.zza;
            int i2 = this.zzc;
            this.zzc = i2 + 1;
            zzard.zzn(bArr2, i2, (byte) j);
            return;
        }
        while ((j & (-128)) != 0) {
            try {
                byte[] bArr3 = this.zza;
                int i3 = this.zzc;
                this.zzc = i3 + 1;
                bArr3[i3] = (byte) ((((int) j) & 127) | 128);
                j >>>= 7;
            } catch (IndexOutOfBoundsException e) {
                throw new zzamu(String.format("Pos: %d, limit: %d, len: %d", Integer.valueOf(this.zzc), Integer.valueOf(this.zzb), 1), e);
            }
        }
        byte[] bArr4 = this.zza;
        int i4 = this.zzc;
        this.zzc = i4 + 1;
        bArr4[i4] = (byte) j;
    }
}
