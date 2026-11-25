package com.google.android.gms.internal.gtm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzth extends zztj {
    private final InputStream zze;
    private final byte[] zzf;
    private int zzg;
    private int zzh;
    private int zzi;
    private int zzj;
    private int zzk;
    private int zzl;

    /* synthetic */ zzth(InputStream inputStream, int i, zztg zztgVar) {
        super(null);
        this.zzl = Integer.MAX_VALUE;
        zzvi.zzf(inputStream, "input");
        this.zze = inputStream;
        this.zzf = new byte[4096];
        this.zzg = 0;
        this.zzi = 0;
        this.zzk = 0;
    }

    private final List<byte[]> zzu(int i) throws IOException {
        ArrayList arrayList = new ArrayList();
        while (i > 0) {
            int iMin = Math.min(i, 4096);
            byte[] bArr = new byte[iMin];
            int i2 = 0;
            while (i2 < iMin) {
                int i3 = this.zze.read(bArr, i2, iMin - i2);
                if (i3 == -1) {
                    throw zzvk.zzj();
                }
                this.zzk += i3;
                i2 += i3;
            }
            i -= iMin;
            arrayList.add(bArr);
        }
        return arrayList;
    }

    private final void zzv() {
        int i = this.zzg + this.zzh;
        this.zzg = i;
        int i2 = this.zzk + i;
        int i3 = this.zzl;
        if (i2 <= i3) {
            this.zzh = 0;
            return;
        }
        int i4 = i2 - i3;
        this.zzh = i4;
        this.zzg = i - i4;
    }

    private final void zzw(int i) throws IOException {
        if (zzx(i)) {
            return;
        }
        if (i <= (Integer.MAX_VALUE - this.zzk) - this.zzi) {
            throw zzvk.zzj();
        }
        throw zzvk.zzi();
    }

    private final boolean zzx(int i) throws IOException {
        int i2 = this.zzi;
        int i3 = this.zzg;
        if (i2 + i <= i3) {
            StringBuilder sb = new StringBuilder(77);
            sb.append("refillBuffer() called when ");
            sb.append(i);
            sb.append(" bytes were already available in buffer");
            throw new IllegalStateException(sb.toString());
        }
        int i4 = this.zzk;
        if (i > (Integer.MAX_VALUE - i4) - i2 || i4 + i2 + i > this.zzl) {
            return false;
        }
        if (i2 > 0) {
            if (i3 > i2) {
                byte[] bArr = this.zzf;
                System.arraycopy(bArr, i2, bArr, 0, i3 - i2);
            }
            i4 = this.zzk + i2;
            this.zzk = i4;
            i3 = this.zzg - i2;
            this.zzg = i3;
            this.zzi = 0;
        }
        try {
            int i5 = this.zze.read(this.zzf, i3, Math.min(4096 - i3, (Integer.MAX_VALUE - i4) - i3));
            if (i5 == 0 || i5 < -1 || i5 > 4096) {
                String strValueOf = String.valueOf(this.zze.getClass());
                StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 91);
                sb2.append(strValueOf);
                sb2.append("#read(byte[]) returned invalid result: ");
                sb2.append(i5);
                sb2.append("\nThe InputStream implementation is buggy.");
                throw new IllegalStateException(sb2.toString());
            }
            if (i5 <= 0) {
                return false;
            }
            this.zzg += i5;
            zzv();
            if (this.zzg >= i) {
                return true;
            }
            return zzx(i);
        } catch (zzvk e) {
            e.zzk();
            throw e;
        }
    }

    private final byte[] zzy(int i, boolean z) throws IOException {
        byte[] bArrZzz = zzz(i);
        if (bArrZzz != null) {
            return bArrZzz;
        }
        int i2 = this.zzi;
        int i3 = this.zzg;
        int i4 = i3 - i2;
        this.zzk += i3;
        this.zzi = 0;
        this.zzg = 0;
        List<byte[]> listZzu = zzu(i - i4);
        byte[] bArr = new byte[i];
        System.arraycopy(this.zzf, i2, bArr, 0, i4);
        for (byte[] bArr2 : listZzu) {
            int length = bArr2.length;
            System.arraycopy(bArr2, 0, bArr, i4, length);
            i4 += length;
        }
        return bArr;
    }

    private final byte[] zzz(int i) throws IOException {
        if (i == 0) {
            return zzvi.zzc;
        }
        if (i < 0) {
            throw zzvk.zzf();
        }
        int i2 = this.zzk;
        int i3 = this.zzi;
        int i4 = i2 + i3 + i;
        if ((-2147483647) + i4 > 0) {
            throw zzvk.zzi();
        }
        int i5 = this.zzl;
        if (i4 > i5) {
            zzr((i5 - i2) - i3);
            throw zzvk.zzj();
        }
        int i6 = this.zzg - i3;
        int i7 = i - i6;
        if (i7 >= 4096) {
            try {
                if (i7 > this.zze.available()) {
                    return null;
                }
            } catch (zzvk e) {
                e.zzk();
                throw e;
            }
        }
        byte[] bArr = new byte[i];
        System.arraycopy(this.zzf, this.zzi, bArr, 0, i6);
        this.zzk += this.zzg;
        this.zzi = 0;
        this.zzg = 0;
        while (i6 < i) {
            try {
                int i8 = this.zze.read(bArr, i6, i - i6);
                if (i8 == -1) {
                    throw zzvk.zzj();
                }
                this.zzk += i8;
                i6 += i8;
            } catch (zzvk e2) {
                e2.zzk();
                throw e2;
            }
        }
        return bArr;
    }

    @Override // com.google.android.gms.internal.gtm.zztj
    public final int zza() {
        return this.zzk + this.zzi;
    }

    @Override // com.google.android.gms.internal.gtm.zztj
    public final int zzb(int i) throws zzvk {
        if (i < 0) {
            throw zzvk.zzf();
        }
        int i2 = i + this.zzk + this.zzi;
        int i3 = this.zzl;
        if (i2 > i3) {
            throw zzvk.zzj();
        }
        this.zzl = i2;
        zzv();
        return i3;
    }

    @Override // com.google.android.gms.internal.gtm.zztj
    public final int zzc() throws IOException {
        if (zzi()) {
            this.zzj = 0;
            return 0;
        }
        int iZzn = zzn();
        this.zzj = iZzn;
        if ((iZzn >>> 3) != 0) {
            return iZzn;
        }
        throw zzvk.zzc();
    }

    @Override // com.google.android.gms.internal.gtm.zztj
    public final zztd zzd() throws IOException {
        int iZzn = zzn();
        int i = this.zzg;
        int i2 = this.zzi;
        if (iZzn <= i - i2 && iZzn > 0) {
            zztd zztdVarZzn = zztd.zzn(this.zzf, i2, iZzn);
            this.zzi += iZzn;
            return zztdVarZzn;
        }
        if (iZzn == 0) {
            return zztd.zzb;
        }
        byte[] bArrZzz = zzz(iZzn);
        if (bArrZzz != null) {
            return zztd.zzm(bArrZzz);
        }
        int i3 = this.zzi;
        int i4 = this.zzg;
        int i5 = i4 - i3;
        this.zzk += i4;
        this.zzi = 0;
        this.zzg = 0;
        List<byte[]> listZzu = zzu(iZzn - i5);
        byte[] bArr = new byte[iZzn];
        System.arraycopy(this.zzf, i3, bArr, 0, i5);
        for (byte[] bArr2 : listZzu) {
            int length = bArr2.length;
            System.arraycopy(bArr2, 0, bArr, i5, length);
            i5 += length;
        }
        return zztd.zzp(bArr);
    }

    @Override // com.google.android.gms.internal.gtm.zztj
    public final String zze() throws IOException {
        int iZzn = zzn();
        if (iZzn > 0) {
            int i = this.zzg;
            int i2 = this.zzi;
            if (iZzn <= i - i2) {
                String str = new String(this.zzf, i2, iZzn, zzvi.zza);
                this.zzi += iZzn;
                return str;
            }
        }
        if (iZzn == 0) {
            return "";
        }
        if (iZzn > this.zzg) {
            return new String(zzy(iZzn, false), zzvi.zza);
        }
        zzw(iZzn);
        String str2 = new String(this.zzf, this.zzi, iZzn, zzvi.zza);
        this.zzi += iZzn;
        return str2;
    }

    @Override // com.google.android.gms.internal.gtm.zztj
    public final String zzf() throws IOException {
        byte[] bArrZzy;
        int iZzn = zzn();
        int i = this.zzi;
        int i2 = this.zzg;
        if (iZzn <= i2 - i && iZzn > 0) {
            bArrZzy = this.zzf;
            this.zzi = i + iZzn;
        } else {
            if (iZzn == 0) {
                return "";
            }
            i = 0;
            if (iZzn <= i2) {
                zzw(iZzn);
                bArrZzy = this.zzf;
                this.zzi = iZzn;
            } else {
                bArrZzy = zzy(iZzn, false);
            }
        }
        return zzyd.zzd(bArrZzy, i, iZzn);
    }

    @Override // com.google.android.gms.internal.gtm.zztj
    public final void zzg(int i) throws zzvk {
        if (this.zzj != i) {
            throw zzvk.zzb();
        }
    }

    @Override // com.google.android.gms.internal.gtm.zztj
    public final void zzh(int i) {
        this.zzl = i;
        zzv();
    }

    @Override // com.google.android.gms.internal.gtm.zztj
    public final boolean zzi() throws IOException {
        return this.zzi == this.zzg && !zzx(1);
    }

    @Override // com.google.android.gms.internal.gtm.zztj
    public final boolean zzj() throws IOException {
        return zzp() != 0;
    }

    public final byte zzl() throws IOException {
        if (this.zzi == this.zzg) {
            zzw(1);
        }
        byte[] bArr = this.zzf;
        int i = this.zzi;
        this.zzi = i + 1;
        return bArr[i];
    }

    public final int zzm() throws IOException {
        int i = this.zzi;
        if (this.zzg - i < 4) {
            zzw(4);
            i = this.zzi;
        }
        byte[] bArr = this.zzf;
        this.zzi = i + 4;
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    public final long zzo() throws IOException {
        int i = this.zzi;
        if (this.zzg - i < 8) {
            zzw(8);
            i = this.zzi;
        }
        byte[] bArr = this.zzf;
        this.zzi = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    final long zzq() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte bZzl = zzl();
            j |= ((long) (bZzl & 127)) << i;
            if ((bZzl & 128) == 0) {
                return j;
            }
        }
        throw zzvk.zze();
    }

    public final int zzn() throws IOException {
        int i;
        int i2 = this.zzi;
        int i3 = this.zzg;
        if (i3 != i2) {
            byte[] bArr = this.zzf;
            int i4 = i2 + 1;
            byte b = bArr[i2];
            if (b >= 0) {
                this.zzi = i4;
                return b;
            }
            if (i3 - i4 >= 9) {
                int i5 = i2 + 2;
                int i6 = (bArr[i4] << 7) ^ b;
                if (i6 < 0) {
                    i = i6 ^ (-128);
                } else {
                    int i7 = i2 + 3;
                    int i8 = (bArr[i5] << 14) ^ i6;
                    if (i8 >= 0) {
                        i = i8 ^ 16256;
                    } else {
                        int i9 = i2 + 4;
                        int i10 = i8 ^ (bArr[i7] << 21);
                        if (i10 < 0) {
                            i = (-2080896) ^ i10;
                        } else {
                            i7 = i2 + 5;
                            byte b2 = bArr[i9];
                            int i11 = (i10 ^ (b2 << 28)) ^ 266354560;
                            if (b2 < 0) {
                                i9 = i2 + 6;
                                if (bArr[i7] < 0) {
                                    i7 = i2 + 7;
                                    if (bArr[i9] < 0) {
                                        i9 = i2 + 8;
                                        if (bArr[i7] < 0) {
                                            i7 = i2 + 9;
                                            if (bArr[i9] < 0) {
                                                int i12 = i2 + 10;
                                                if (bArr[i7] >= 0) {
                                                    i5 = i12;
                                                    i = i11;
                                                }
                                            }
                                        }
                                    }
                                }
                                i = i11;
                            }
                            i = i11;
                        }
                        i5 = i9;
                    }
                    i5 = i7;
                }
                this.zzi = i5;
                return i;
            }
        }
        return (int) zzq();
    }

    @Override // com.google.android.gms.internal.gtm.zztj
    public final boolean zzk(int i) throws IOException {
        int iZzc;
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.zzg - this.zzi < 10) {
                while (i3 < 10) {
                    if (zzl() < 0) {
                        i3++;
                    }
                }
                throw zzvk.zze();
            }
            while (i3 < 10) {
                byte[] bArr = this.zzf;
                int i4 = this.zzi;
                this.zzi = i4 + 1;
                if (bArr[i4] < 0) {
                    i3++;
                }
            }
            throw zzvk.zze();
            return true;
        }
        if (i2 == 1) {
            zzr(8);
            return true;
        }
        if (i2 == 2) {
            zzr(zzn());
            return true;
        }
        if (i2 != 3) {
            if (i2 == 4) {
                return false;
            }
            if (i2 != 5) {
                throw zzvk.zza();
            }
            zzr(4);
            return true;
        }
        do {
            iZzc = zzc();
            if (iZzc == 0) {
                break;
            }
        } while (zzk(iZzc));
        zzg(((i >>> 3) << 3) | 4);
        return true;
    }

    public final void zzr(int i) throws IOException {
        int i2 = this.zzg;
        int i3 = this.zzi;
        int i4 = i2 - i3;
        if (i <= i4 && i >= 0) {
            this.zzi = i3 + i;
            return;
        }
        if (i < 0) {
            throw zzvk.zzf();
        }
        int i5 = this.zzk;
        int i6 = i5 + i3;
        int i7 = this.zzl;
        if (i6 + i > i7) {
            zzr((i7 - i5) - i3);
            throw zzvk.zzj();
        }
        this.zzk = i6;
        this.zzg = 0;
        this.zzi = 0;
        while (i4 < i) {
            try {
                long j = i - i4;
                try {
                    long jSkip = this.zze.skip(j);
                    if (jSkip < 0 || jSkip > j) {
                        String strValueOf = String.valueOf(this.zze.getClass());
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 92);
                        sb.append(strValueOf);
                        sb.append("#skip returned invalid result: ");
                        sb.append(jSkip);
                        sb.append("\nThe InputStream implementation is buggy.");
                        throw new IllegalStateException(sb.toString());
                    }
                    if (jSkip == 0) {
                        break;
                    } else {
                        i4 += (int) jSkip;
                    }
                } catch (zzvk e) {
                    e.zzk();
                    throw e;
                }
            } catch (Throwable th) {
                this.zzk += i4;
                zzv();
                throw th;
            }
        }
        this.zzk += i4;
        zzv();
        if (i4 >= i) {
            return;
        }
        int i8 = this.zzg;
        int i9 = i8 - this.zzi;
        this.zzi = i8;
        zzw(1);
        while (true) {
            int i10 = i - i9;
            int i11 = this.zzg;
            if (i10 <= i11) {
                this.zzi = i10;
                return;
            } else {
                i9 += i11;
                this.zzi = i11;
                zzw(1);
            }
        }
    }

    public final long zzp() throws IOException {
        long j;
        long j2;
        int i = this.zzi;
        int i2 = this.zzg;
        if (i2 != i) {
            byte[] bArr = this.zzf;
            int i3 = i + 1;
            byte b = bArr[i];
            if (b >= 0) {
                this.zzi = i3;
                return b;
            }
            if (i2 - i3 >= 9) {
                int i4 = i + 2;
                int i5 = (bArr[i3] << 7) ^ b;
                if (i5 < 0) {
                    j = i5 ^ (-128);
                } else {
                    int i6 = i + 3;
                    int i7 = (bArr[i4] << 14) ^ i5;
                    if (i7 >= 0) {
                        j = i7 ^ 16256;
                    } else {
                        int i8 = i + 4;
                        int i9 = i7 ^ (bArr[i6] << 21);
                        if (i9 < 0) {
                            long j3 = (-2080896) ^ i9;
                            i4 = i8;
                            j = j3;
                        } else {
                            i6 = i + 5;
                            long j4 = ((long) i9) ^ (((long) bArr[i8]) << 28);
                            if (j4 >= 0) {
                                j = j4 ^ 266354560;
                            } else {
                                i4 = i + 6;
                                long j5 = (((long) bArr[i6]) << 35) ^ j4;
                                if (j5 < 0) {
                                    j2 = -34093383808L;
                                } else {
                                    int i10 = i + 7;
                                    long j6 = j5 ^ (((long) bArr[i4]) << 42);
                                    if (j6 >= 0) {
                                        j = j6 ^ 4363953127296L;
                                    } else {
                                        i4 = i + 8;
                                        j5 = j6 ^ (((long) bArr[i10]) << 49);
                                        if (j5 < 0) {
                                            j2 = -558586000294016L;
                                        } else {
                                            i10 = i + 9;
                                            long j7 = (j5 ^ (((long) bArr[i4]) << 56)) ^ 71499008037633920L;
                                            if (j7 < 0) {
                                                i4 = i + 10;
                                                if (bArr[i10] >= 0) {
                                                    j = j7;
                                                }
                                            } else {
                                                j = j7;
                                            }
                                        }
                                    }
                                    i4 = i10;
                                }
                                j = j5 ^ j2;
                            }
                        }
                    }
                    i4 = i6;
                }
                this.zzi = i4;
                return j;
            }
        }
        return zzq();
    }
}
