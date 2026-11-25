package com.google.android.gms.internal.gtm;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzsm {
    static int zza(byte[] bArr, int i, zzsl zzslVar) throws zzvk {
        int iZzj = zzj(bArr, i, zzslVar);
        int i2 = zzslVar.zza;
        if (i2 < 0) {
            throw zzvk.zzf();
        }
        if (i2 > bArr.length - iZzj) {
            throw zzvk.zzj();
        }
        if (i2 == 0) {
            zzslVar.zzc = zztd.zzb;
            return iZzj;
        }
        zzslVar.zzc = zztd.zzn(bArr, iZzj, i2);
        return iZzj + i2;
    }

    static int zzb(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    static int zzc(zzwx zzwxVar, byte[] bArr, int i, int i2, int i3, zzsl zzslVar) throws IOException {
        zzwn zzwnVar = (zzwn) zzwxVar;
        Object objZze = zzwnVar.zze();
        int iZzc = zzwnVar.zzc(objZze, bArr, i, i2, i3, zzslVar);
        zzwnVar.zzf(objZze);
        zzslVar.zzc = objZze;
        return iZzc;
    }

    static int zzd(zzwx zzwxVar, byte[] bArr, int i, int i2, zzsl zzslVar) throws IOException {
        int iZzk = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZzk = zzk(i3, bArr, iZzk, zzslVar);
            i3 = zzslVar.zza;
        }
        int i4 = iZzk;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzvk.zzj();
        }
        Object objZze = zzwxVar.zze();
        int i5 = i3 + i4;
        zzwxVar.zzi(objZze, bArr, i4, i5, zzslVar);
        zzwxVar.zzf(objZze);
        zzslVar.zzc = objZze;
        return i5;
    }

    static int zze(zzwx<?> zzwxVar, int i, byte[] bArr, int i2, int i3, zzvh<?> zzvhVar, zzsl zzslVar) throws IOException {
        int iZzd = zzd(zzwxVar, bArr, i2, i3, zzslVar);
        zzvhVar.add(zzslVar.zzc);
        while (iZzd < i3) {
            int iZzj = zzj(bArr, iZzd, zzslVar);
            if (i != zzslVar.zza) {
                break;
            }
            iZzd = zzd(zzwxVar, bArr, iZzj, i3, zzslVar);
            zzvhVar.add(zzslVar.zzc);
        }
        return iZzd;
    }

    static int zzf(byte[] bArr, int i, zzvh<?> zzvhVar, zzsl zzslVar) throws IOException {
        zzva zzvaVar = (zzva) zzvhVar;
        int iZzj = zzj(bArr, i, zzslVar);
        int i2 = zzslVar.zza + iZzj;
        while (iZzj < i2) {
            iZzj = zzj(bArr, iZzj, zzslVar);
            zzvaVar.zzh(zzslVar.zza);
        }
        if (iZzj == i2) {
            return iZzj;
        }
        throw zzvk.zzj();
    }

    static int zzg(byte[] bArr, int i, zzsl zzslVar) throws zzvk {
        int iZzj = zzj(bArr, i, zzslVar);
        int i2 = zzslVar.zza;
        if (i2 < 0) {
            throw zzvk.zzf();
        }
        if (i2 == 0) {
            zzslVar.zzc = "";
            return iZzj;
        }
        zzslVar.zzc = new String(bArr, iZzj, i2, zzvi.zza);
        return iZzj + i2;
    }

    static int zzh(byte[] bArr, int i, zzsl zzslVar) throws zzvk {
        int iZzj = zzj(bArr, i, zzslVar);
        int i2 = zzslVar.zza;
        if (i2 < 0) {
            throw zzvk.zzf();
        }
        if (i2 == 0) {
            zzslVar.zzc = "";
            return iZzj;
        }
        zzslVar.zzc = zzyd.zzd(bArr, iZzj, i2);
        return iZzj + i2;
    }

    static int zzi(int i, byte[] bArr, int i2, int i3, zzxp zzxpVar, zzsl zzslVar) throws zzvk {
        if ((i >>> 3) == 0) {
            throw zzvk.zzc();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzm = zzm(bArr, i2, zzslVar);
            zzxpVar.zzh(i, Long.valueOf(zzslVar.zzb));
            return iZzm;
        }
        if (i4 == 1) {
            zzxpVar.zzh(i, Long.valueOf(zzo(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZzj = zzj(bArr, i2, zzslVar);
            int i5 = zzslVar.zza;
            if (i5 < 0) {
                throw zzvk.zzf();
            }
            if (i5 > bArr.length - iZzj) {
                throw zzvk.zzj();
            }
            if (i5 == 0) {
                zzxpVar.zzh(i, zztd.zzb);
            } else {
                zzxpVar.zzh(i, zztd.zzn(bArr, iZzj, i5));
            }
            return iZzj + i5;
        }
        if (i4 != 3) {
            if (i4 != 5) {
                throw zzvk.zzc();
            }
            zzxpVar.zzh(i, Integer.valueOf(zzb(bArr, i2)));
            return i2 + 4;
        }
        int i6 = (i & (-8)) | 4;
        zzxp zzxpVarZze = zzxp.zze();
        int i7 = 0;
        while (i2 < i3) {
            int iZzj2 = zzj(bArr, i2, zzslVar);
            int i8 = zzslVar.zza;
            if (i8 == i6) {
                i7 = i8;
                i2 = iZzj2;
                break;
            }
            i7 = i8;
            i2 = zzi(i8, bArr, iZzj2, i3, zzxpVarZze, zzslVar);
        }
        if (i2 > i3 || i7 != i6) {
            throw zzvk.zzg();
        }
        zzxpVar.zzh(i, zzxpVarZze);
        return i2;
    }

    static int zzj(byte[] bArr, int i, zzsl zzslVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zzk(b, bArr, i2, zzslVar);
        }
        zzslVar.zza = b;
        return i2;
    }

    static int zzk(int i, byte[] bArr, int i2, zzsl zzslVar) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzslVar.zza = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & 127) << 7);
        int i6 = i2 + 2;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzslVar.zza = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & 127) << 14);
        int i8 = i2 + 3;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzslVar.zza = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & 127) << 21);
        int i10 = i2 + 4;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzslVar.zza = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & 127) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzslVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzl(int i, byte[] bArr, int i2, int i3, zzvh<?> zzvhVar, zzsl zzslVar) {
        zzva zzvaVar = (zzva) zzvhVar;
        int iZzj = zzj(bArr, i2, zzslVar);
        zzvaVar.zzh(zzslVar.zza);
        while (iZzj < i3) {
            int iZzj2 = zzj(bArr, iZzj, zzslVar);
            if (i != zzslVar.zza) {
                break;
            }
            iZzj = zzj(bArr, iZzj2, zzslVar);
            zzvaVar.zzh(zzslVar.zza);
        }
        return iZzj;
    }

    static int zzm(byte[] bArr, int i, zzsl zzslVar) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            zzslVar.zzb = j;
            return i2;
        }
        int i3 = i + 2;
        byte b = bArr[i2];
        long j2 = (j & 127) | (((long) (b & 127)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= ((long) (b2 & 127)) << i4;
            b = b2;
            i3 = i5;
        }
        zzslVar.zzb = j2;
        return i3;
    }

    static long zzo(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    static int zzn(int i, byte[] bArr, int i2, int i3, zzsl zzslVar) throws zzvk {
        if ((i >>> 3) == 0) {
            throw zzvk.zzc();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            return zzm(bArr, i2, zzslVar);
        }
        if (i4 == 1) {
            return i2 + 8;
        }
        if (i4 == 2) {
            return zzj(bArr, i2, zzslVar) + zzslVar.zza;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                return i2 + 4;
            }
            throw zzvk.zzc();
        }
        int i5 = (i & (-8)) | 4;
        int i6 = 0;
        while (i2 < i3) {
            i2 = zzj(bArr, i2, zzslVar);
            i6 = zzslVar.zza;
            if (i6 == i5) {
                break;
            }
            i2 = zzn(i6, bArr, i2, i3, zzslVar);
        }
        if (i2 > i3 || i6 != i5) {
            throw zzvk.zzg();
        }
        return i2;
    }
}
