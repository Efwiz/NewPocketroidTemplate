package com.google.android.gms.internal.mlkit_vision_mediapipe;

import com.gaurav.avnc.vnc.XKeySym;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzie extends zzhz {
    zzie() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhz
    final int zza(int i, byte[] bArr, int i2, int i3) {
        int i4;
        if ((i2 | i3 | (bArr.length - i3)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("Array length=%d, index=%d, limit=%d", Integer.valueOf(bArr.length), Integer.valueOf(i2), Integer.valueOf(i3)));
        }
        long j = i2;
        int i5 = (int) (((long) i3) - j);
        if (i5 >= 16) {
            long j2 = j;
            i4 = 0;
            while (true) {
                if (i4 >= i5) {
                    i4 = i5;
                    break;
                }
                long j3 = j2 + 1;
                if (zzhv.zza(bArr, j2) < 0) {
                    break;
                }
                i4++;
                j2 = j3;
            }
        } else {
            i4 = 0;
        }
        int i6 = i5 - i4;
        long j4 = j + ((long) i4);
        while (true) {
            byte bZza = 0;
            while (i6 > 0) {
                long j5 = j4 + 1;
                bZza = zzhv.zza(bArr, j4);
                if (bZza < 0) {
                    j4 = j5;
                    break;
                }
                i6--;
                j4 = j5;
            }
            if (i6 == 0) {
                return 0;
            }
            int i7 = i6 - 1;
            if (bZza < -32) {
                if (i7 == 0) {
                    return bZza;
                }
                i6 -= 2;
                if (bZza >= -62) {
                    long j6 = j4 + 1;
                    if (zzhv.zza(bArr, j4) <= -65) {
                        j4 = j6;
                    }
                }
                return -1;
            }
            if (bZza >= -16) {
                if (i7 < 3) {
                    return zza(bArr, bZza, j4, i7);
                }
                i6 -= 4;
                long j7 = j4 + 1;
                byte bZza2 = zzhv.zza(bArr, j4);
                if (bZza2 <= -65 && (((bZza << 28) + (bZza2 + 112)) >> 30) == 0) {
                    long j8 = j4 + 2;
                    if (zzhv.zza(bArr, j7) <= -65) {
                        j4 += 3;
                        if (zzhv.zza(bArr, j8) > -65) {
                        }
                    }
                }
                return -1;
            }
            if (i7 < 2) {
                return zza(bArr, bZza, j4, i7);
            }
            i6 -= 3;
            long j9 = j4 + 1;
            byte bZza3 = zzhv.zza(bArr, j4);
            if (bZza3 <= -65 && ((bZza != -32 || bZza3 >= -96) && (bZza != -19 || bZza3 < -96))) {
                j4 += 2;
                if (zzhv.zza(bArr, j9) > -65) {
                }
            }
            return -1;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhz
    final String zzb(byte[] bArr, int i, int i2) throws zzfi {
        if ((i | i2 | ((bArr.length - i) - i2)) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(i2)));
        }
        int i3 = i + i2;
        char[] cArr = new char[i2];
        int i4 = 0;
        while (i < i3) {
            byte bZza = zzhv.zza(bArr, i);
            if (!zzia.zzd(bZza)) {
                break;
            }
            i++;
            zzia.zzb(bZza, cArr, i4);
            i4++;
        }
        int i5 = i4;
        while (i < i3) {
            int i6 = i + 1;
            byte bZza2 = zzhv.zza(bArr, i);
            if (zzia.zzd(bZza2)) {
                int i7 = i5 + 1;
                zzia.zzb(bZza2, cArr, i5);
                while (i6 < i3) {
                    byte bZza3 = zzhv.zza(bArr, i6);
                    if (!zzia.zzd(bZza3)) {
                        break;
                    }
                    i6++;
                    zzia.zzb(bZza3, cArr, i7);
                    i7++;
                }
                i5 = i7;
                i = i6;
            } else if (zzia.zze(bZza2)) {
                if (i6 >= i3) {
                    throw zzfi.zzf();
                }
                i += 2;
                zzia.zzb(bZza2, zzhv.zza(bArr, i6), cArr, i5);
                i5++;
            } else if (zzia.zzf(bZza2)) {
                if (i6 < i3 - 1) {
                    int i8 = i + 2;
                    i += 3;
                    zzia.zzb(bZza2, zzhv.zza(bArr, i6), zzhv.zza(bArr, i8), cArr, i5);
                    i5++;
                } else {
                    throw zzfi.zzf();
                }
            } else {
                if (i6 >= i3 - 2) {
                    throw zzfi.zzf();
                }
                byte bZza4 = zzhv.zza(bArr, i6);
                int i9 = i + 3;
                byte bZza5 = zzhv.zza(bArr, i + 2);
                i += 4;
                zzia.zzb(bZza2, bZza4, bZza5, zzhv.zza(bArr, i9), cArr, i5);
                i5 += 2;
            }
        }
        return new String(cArr, 0, i5);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzhz
    final int zza(CharSequence charSequence, byte[] bArr, int i, int i2) {
        long j;
        String str;
        String str2;
        int i3;
        long j2;
        char cCharAt;
        long j3 = i;
        long j4 = ((long) i2) + j3;
        int length = charSequence.length();
        String str3 = " at index ";
        String str4 = "Failed writing ";
        if (length > i2 || bArr.length - i2 < i) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(37).append("Failed writing ").append(charSequence.charAt(length - 1)).append(" at index ").append(i + i2).toString());
        }
        int i4 = 0;
        while (true) {
            j = 1;
            if (i4 >= length || (cCharAt = charSequence.charAt(i4)) >= 128) {
                break;
            }
            zzhv.zza(bArr, j3, (byte) cCharAt);
            i4++;
            j3 = 1 + j3;
        }
        if (i4 == length) {
            return (int) j3;
        }
        while (i4 < length) {
            char cCharAt2 = charSequence.charAt(i4);
            if (cCharAt2 >= 128 || j3 >= j4) {
                if (cCharAt2 >= 2048 || j3 > j4 - 2) {
                    str = str3;
                    str2 = str4;
                    if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || j3 > j4 - 3) {
                        if (j3 <= j4 - 4) {
                            int i5 = i4 + 1;
                            if (i5 != length) {
                                char cCharAt3 = charSequence.charAt(i5);
                                if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                    int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                    j2 = 1;
                                    zzhv.zza(bArr, j3, (byte) ((codePoint >>> 18) | 240));
                                    zzhv.zza(bArr, j3 + 1, (byte) (((codePoint >>> 12) & 63) | 128));
                                    long j5 = j3 + 3;
                                    zzhv.zza(bArr, j3 + 2, (byte) (((codePoint >>> 6) & 63) | 128));
                                    j3 += 4;
                                    zzhv.zza(bArr, j5, (byte) ((codePoint & 63) | 128));
                                    i4 = i5;
                                } else {
                                    i4 = i5;
                                }
                            }
                            throw new zzib(i4 - 1, length);
                        }
                        if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i3 = i4 + 1) == length || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i3)))) {
                            throw new zzib(i4, length);
                        }
                        throw new ArrayIndexOutOfBoundsException(new StringBuilder(46).append(str2).append(cCharAt2).append(str).append(j3).toString());
                    }
                    zzhv.zza(bArr, j3, (byte) ((cCharAt2 >>> '\f') | 480));
                    long j6 = j3 + 2;
                    zzhv.zza(bArr, j3 + 1, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                    j3 += 3;
                    zzhv.zza(bArr, j6, (byte) ((cCharAt2 & MangleConstant.Q_MARK) | 128));
                } else {
                    str = str3;
                    str2 = str4;
                    long j7 = j3 + j;
                    zzhv.zza(bArr, j3, (byte) ((cCharAt2 >>> 6) | XKeySym.XK_Amacron));
                    j3 += 2;
                    zzhv.zza(bArr, j7, (byte) ((cCharAt2 & MangleConstant.Q_MARK) | 128));
                }
                j2 = 1;
            } else {
                zzhv.zza(bArr, j3, (byte) cCharAt2);
                str2 = str4;
                j2 = j;
                j3 += j;
                str = str3;
            }
            i4++;
            str3 = str;
            str4 = str2;
            j = j2;
            j4 = j4;
        }
        return (int) j3;
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        if (i2 == 0) {
            return zzhy.zzb(i);
        }
        if (i2 == 1) {
            return zzhy.zzb(i, zzhv.zza(bArr, j));
        }
        if (i2 == 2) {
            return zzhy.zzb(i, zzhv.zza(bArr, j), zzhv.zza(bArr, j + 1));
        }
        throw new AssertionError();
    }
}
