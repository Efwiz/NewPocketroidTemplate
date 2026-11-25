package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzalw {
    static int zza(byte[] bArr, int i, zzalv zzalvVar) throws zzaoe {
        int iZzj = zzj(bArr, i, zzalvVar);
        int i2 = zzalvVar.zza;
        if (i2 < 0) {
            throw zzaoe.zzf();
        }
        if (i2 > bArr.length - iZzj) {
            throw zzaoe.zzj();
        }
        if (i2 == 0) {
            zzalvVar.zzc = zzamk.zzb;
            return iZzj;
        }
        zzalvVar.zzc = zzamk.zzs(bArr, iZzj, i2);
        return iZzj + i2;
    }

    static int zzb(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    static int zzc(zzapz zzapzVar, byte[] bArr, int i, int i2, int i3, zzalv zzalvVar) throws IOException {
        zzapi zzapiVar = (zzapi) zzapzVar;
        Object objZze = zzapiVar.zze();
        int iZzc = zzapiVar.zzc(objZze, bArr, i, i2, i3, zzalvVar);
        zzapiVar.zzf(objZze);
        zzalvVar.zzc = objZze;
        return iZzc;
    }

    static int zzd(zzapz zzapzVar, byte[] bArr, int i, int i2, zzalv zzalvVar) throws IOException {
        int iZzk = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZzk = zzk(i3, bArr, iZzk, zzalvVar);
            i3 = zzalvVar.zza;
        }
        int i4 = iZzk;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzaoe.zzj();
        }
        Object objZze = zzapzVar.zze();
        int i5 = i3 + i4;
        zzapzVar.zzi(objZze, bArr, i4, i5, zzalvVar);
        zzapzVar.zzf(objZze);
        zzalvVar.zzc = objZze;
        return i5;
    }

    static int zze(zzapz<?> zzapzVar, int i, byte[] bArr, int i2, int i3, zzaob<?> zzaobVar, zzalv zzalvVar) throws IOException {
        int iZzd = zzd(zzapzVar, bArr, i2, i3, zzalvVar);
        zzaobVar.add(zzalvVar.zzc);
        while (iZzd < i3) {
            int iZzj = zzj(bArr, iZzd, zzalvVar);
            if (i != zzalvVar.zza) {
                break;
            }
            iZzd = zzd(zzapzVar, bArr, iZzj, i3, zzalvVar);
            zzaobVar.add(zzalvVar.zzc);
        }
        return iZzd;
    }

    static int zzf(byte[] bArr, int i, zzaob<?> zzaobVar, zzalv zzalvVar) throws IOException {
        zzant zzantVar = (zzant) zzaobVar;
        int iZzj = zzj(bArr, i, zzalvVar);
        int i2 = zzalvVar.zza + iZzj;
        while (iZzj < i2) {
            iZzj = zzj(bArr, iZzj, zzalvVar);
            zzantVar.zzg(zzalvVar.zza);
        }
        if (iZzj == i2) {
            return iZzj;
        }
        throw zzaoe.zzj();
    }

    static int zzg(byte[] bArr, int i, zzalv zzalvVar) throws zzaoe {
        int iZzj = zzj(bArr, i, zzalvVar);
        int i2 = zzalvVar.zza;
        if (i2 < 0) {
            throw zzaoe.zzf();
        }
        if (i2 == 0) {
            zzalvVar.zzc = "";
            return iZzj;
        }
        zzalvVar.zzc = new String(bArr, iZzj, i2, zzaoc.zza);
        return iZzj + i2;
    }

    static int zzh(byte[] bArr, int i, zzalv zzalvVar) throws zzaoe {
        int iZzj = zzj(bArr, i, zzalvVar);
        int i2 = zzalvVar.zza;
        if (i2 < 0) {
            throw zzaoe.zzf();
        }
        if (i2 == 0) {
            zzalvVar.zzc = "";
            return iZzj;
        }
        zzalvVar.zzc = zzari.zzg(bArr, iZzj, i2);
        return iZzj + i2;
    }

    static int zzi(int i, byte[] bArr, int i2, int i3, zzaqu zzaquVar, zzalv zzalvVar) throws zzaoe {
        if ((i >>> 3) == 0) {
            throw zzaoe.zzc();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzm = zzm(bArr, i2, zzalvVar);
            zzaquVar.zzh(i, Long.valueOf(zzalvVar.zzb));
            return iZzm;
        }
        if (i4 == 1) {
            zzaquVar.zzh(i, Long.valueOf(zzo(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZzj = zzj(bArr, i2, zzalvVar);
            int i5 = zzalvVar.zza;
            if (i5 < 0) {
                throw zzaoe.zzf();
            }
            if (i5 > bArr.length - iZzj) {
                throw zzaoe.zzj();
            }
            if (i5 == 0) {
                zzaquVar.zzh(i, zzamk.zzb);
            } else {
                zzaquVar.zzh(i, zzamk.zzs(bArr, iZzj, i5));
            }
            return iZzj + i5;
        }
        if (i4 != 3) {
            if (i4 != 5) {
                throw zzaoe.zzc();
            }
            zzaquVar.zzh(i, Integer.valueOf(zzb(bArr, i2)));
            return i2 + 4;
        }
        int i6 = (i & (-8)) | 4;
        zzaqu zzaquVarZze = zzaqu.zze();
        int i7 = 0;
        while (i2 < i3) {
            int iZzj2 = zzj(bArr, i2, zzalvVar);
            int i8 = zzalvVar.zza;
            if (i8 == i6) {
                i7 = i8;
                i2 = iZzj2;
                break;
            }
            i7 = i8;
            i2 = zzi(i8, bArr, iZzj2, i3, zzaquVarZze, zzalvVar);
        }
        if (i2 > i3 || i7 != i6) {
            throw zzaoe.zzg();
        }
        zzaquVar.zzh(i, zzaquVarZze);
        return i2;
    }

    static int zzj(byte[] bArr, int i, zzalv zzalvVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zzk(b, bArr, i2, zzalvVar);
        }
        zzalvVar.zza = b;
        return i2;
    }

    static int zzk(int i, byte[] bArr, int i2, zzalv zzalvVar) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzalvVar.zza = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & 127) << 7);
        int i6 = i2 + 2;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzalvVar.zza = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & 127) << 14);
        int i8 = i2 + 3;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzalvVar.zza = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & 127) << 21);
        int i10 = i2 + 4;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzalvVar.zza = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & 127) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzalvVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzl(int i, byte[] bArr, int i2, int i3, zzaob<?> zzaobVar, zzalv zzalvVar) {
        zzant zzantVar = (zzant) zzaobVar;
        int iZzj = zzj(bArr, i2, zzalvVar);
        zzantVar.zzg(zzalvVar.zza);
        while (iZzj < i3) {
            int iZzj2 = zzj(bArr, iZzj, zzalvVar);
            if (i != zzalvVar.zza) {
                break;
            }
            iZzj = zzj(bArr, iZzj2, zzalvVar);
            zzantVar.zzg(zzalvVar.zza);
        }
        return iZzj;
    }

    static int zzm(byte[] bArr, int i, zzalv zzalvVar) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            zzalvVar.zzb = j;
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
        zzalvVar.zzb = j2;
        return i3;
    }

    static long zzo(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    static int zzn(int i, byte[] bArr, int i2, int i3, zzalv zzalvVar) throws zzaoe {
        if ((i >>> 3) == 0) {
            throw zzaoe.zzc();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            return zzm(bArr, i2, zzalvVar);
        }
        if (i4 == 1) {
            return i2 + 8;
        }
        if (i4 == 2) {
            return zzj(bArr, i2, zzalvVar) + zzalvVar.zza;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                return i2 + 4;
            }
            throw zzaoe.zzc();
        }
        int i5 = (i & (-8)) | 4;
        int i6 = 0;
        while (i2 < i3) {
            i2 = zzj(bArr, i2, zzalvVar);
            i6 = zzalvVar.zza;
            if (i6 == i5) {
                break;
            }
            i2 = zzn(i6, bArr, i2, i3, zzalvVar);
        }
        if (i2 > i3 || i6 != i5) {
            throw zzaoe.zzg();
        }
        return i2;
    }
}
