package com.google.android.gms.internal.clearcut;

import com.gaurav.avnc.vnc.XKeySym;
import java.nio.ByteBuffer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes9.dex */
final class zzfj extends zzfg {
    zzfj() {
    }

    private static int zza(byte[] bArr, int i, long j, int i2) {
        if (i2 == 0) {
            return zzff.zzam(i);
        }
        if (i2 == 1) {
            return zzff.zzp(i, zzfd.zza(bArr, j));
        }
        if (i2 == 2) {
            return zzff.zzd(i, zzfd.zza(bArr, j), zzfd.zza(bArr, j + 1));
        }
        throw new AssertionError();
    }

    @Override // com.google.android.gms.internal.clearcut.zzfg
    final int zzb(int i, byte[] bArr, int i2, int i3) {
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
                if (zzfd.zza(bArr, j2) < 0) {
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
                bZza = zzfd.zza(bArr, j4);
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
                    if (zzfd.zza(bArr, j4) <= -65) {
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
                byte bZza2 = zzfd.zza(bArr, j4);
                if (bZza2 <= -65 && (((bZza << 28) + (bZza2 + 112)) >> 30) == 0) {
                    long j8 = j4 + 2;
                    if (zzfd.zza(bArr, j7) <= -65) {
                        j4 += 3;
                        if (zzfd.zza(bArr, j8) > -65) {
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
            byte bZza3 = zzfd.zza(bArr, j4);
            if (bZza3 <= -65 && ((bZza != -32 || bZza3 >= -96) && (bZza != -19 || bZza3 < -96))) {
                j4 += 2;
                if (zzfd.zza(bArr, j9) > -65) {
                }
            }
            return -1;
        }
    }

    @Override // com.google.android.gms.internal.clearcut.zzfg
    final int zzb(CharSequence charSequence, byte[] bArr, int i, int i2) {
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
            zzfd.zza(bArr, j3, (byte) cCharAt);
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
                        if (j3 > j4 - 4) {
                            if (55296 > cCharAt2 || cCharAt2 > 57343 || ((i3 = i4 + 1) != length && Character.isSurrogatePair(cCharAt2, charSequence.charAt(i3)))) {
                                throw new ArrayIndexOutOfBoundsException(new StringBuilder(46).append(str2).append(cCharAt2).append(str).append(j3).toString());
                            }
                            throw new zzfi(i4, length);
                        }
                        int i5 = i4 + 1;
                        if (i5 != length) {
                            char cCharAt3 = charSequence.charAt(i5);
                            if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                j2 = 1;
                                zzfd.zza(bArr, j3, (byte) ((codePoint >>> 18) | 240));
                                zzfd.zza(bArr, j3 + 1, (byte) (((codePoint >>> 12) & 63) | 128));
                                long j5 = j3 + 3;
                                zzfd.zza(bArr, j3 + 2, (byte) (((codePoint >>> 6) & 63) | 128));
                                j3 += 4;
                                zzfd.zza(bArr, j5, (byte) ((codePoint & 63) | 128));
                                i4 = i5;
                            } else {
                                i4 = i5;
                            }
                        }
                        throw new zzfi(i4 - 1, length);
                    }
                    zzfd.zza(bArr, j3, (byte) ((cCharAt2 >>> '\f') | 480));
                    long j6 = j3 + 2;
                    zzfd.zza(bArr, j3 + 1, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                    j3 += 3;
                    zzfd.zza(bArr, j6, (byte) ((cCharAt2 & MangleConstant.Q_MARK) | 128));
                } else {
                    str = str3;
                    str2 = str4;
                    long j7 = j3 + j;
                    zzfd.zza(bArr, j3, (byte) ((cCharAt2 >>> 6) | XKeySym.XK_Amacron));
                    j3 += 2;
                    zzfd.zza(bArr, j7, (byte) ((cCharAt2 & MangleConstant.Q_MARK) | 128));
                }
                j2 = 1;
            } else {
                zzfd.zza(bArr, j3, (byte) cCharAt2);
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

    @Override // com.google.android.gms.internal.clearcut.zzfg
    final void zzb(CharSequence charSequence, ByteBuffer byteBuffer) {
        long j;
        char c;
        int i;
        long j2;
        int i2;
        int i3;
        char c2;
        char cCharAt;
        long jZzb = zzfd.zzb(byteBuffer);
        long jPosition = ((long) byteBuffer.position()) + jZzb;
        long jLimit = ((long) byteBuffer.limit()) + jZzb;
        int length = charSequence.length();
        if (length > jLimit - jPosition) {
            throw new ArrayIndexOutOfBoundsException(new StringBuilder(37).append("Failed writing ").append(charSequence.charAt(length - 1)).append(" at index ").append(byteBuffer.limit()).toString());
        }
        int i4 = 0;
        while (true) {
            j = 1;
            c = 128;
            if (i4 >= length || (cCharAt = charSequence.charAt(i4)) >= 128) {
                break;
            }
            zzfd.zza(jPosition, (byte) cCharAt);
            i4++;
            jPosition = 1 + jPosition;
        }
        if (i4 == length) {
            i = (int) (jPosition - jZzb);
        } else {
            while (i4 < length) {
                char cCharAt2 = charSequence.charAt(i4);
                if (cCharAt2 >= c || jPosition >= jLimit) {
                    if (cCharAt2 >= 2048 || jPosition > jLimit - 2) {
                        j2 = jZzb;
                        if ((cCharAt2 >= 55296 && 57343 >= cCharAt2) || jPosition > jLimit - 3) {
                            if (jPosition > jLimit - 4) {
                                if (55296 <= cCharAt2 && cCharAt2 <= 57343 && ((i2 = i4 + 1) == length || !Character.isSurrogatePair(cCharAt2, charSequence.charAt(i2)))) {
                                    throw new zzfi(i4, length);
                                }
                                throw new ArrayIndexOutOfBoundsException(new StringBuilder(46).append("Failed writing ").append(cCharAt2).append(" at index ").append(jPosition).toString());
                            }
                            i3 = i4 + 1;
                            if (i3 != length) {
                                char cCharAt3 = charSequence.charAt(i3);
                                if (Character.isSurrogatePair(cCharAt2, cCharAt3)) {
                                    int codePoint = Character.toCodePoint(cCharAt2, cCharAt3);
                                    zzfd.zza(jPosition, (byte) ((codePoint >>> 18) | 240));
                                    c2 = 128;
                                    zzfd.zza(jPosition + 1, (byte) (((codePoint >>> 12) & 63) | 128));
                                    long j3 = jPosition + 3;
                                    zzfd.zza(jPosition + 2, (byte) (((codePoint >>> 6) & 63) | 128));
                                    jPosition += 4;
                                    zzfd.zza(j3, (byte) ((codePoint & 63) | 128));
                                } else {
                                    i4 = i3;
                                }
                            }
                            throw new zzfi(i4 - 1, length);
                        }
                        long j4 = jPosition + j;
                        zzfd.zza(jPosition, (byte) ((cCharAt2 >>> '\f') | 480));
                        long j5 = jPosition + 2;
                        zzfd.zza(j4, (byte) (((cCharAt2 >>> 6) & 63) | 128));
                        jPosition += 3;
                        zzfd.zza(j5, (byte) ((cCharAt2 & MangleConstant.Q_MARK) | 128));
                    } else {
                        j2 = jZzb;
                        long j6 = jPosition + j;
                        zzfd.zza(jPosition, (byte) ((cCharAt2 >>> 6) | XKeySym.XK_Amacron));
                        jPosition += 2;
                        zzfd.zza(j6, (byte) ((cCharAt2 & MangleConstant.Q_MARK) | 128));
                    }
                    i3 = i4;
                    c2 = 128;
                } else {
                    zzfd.zza(jPosition, (byte) cCharAt2);
                    j2 = jZzb;
                    i3 = i4;
                    c2 = c;
                    jPosition += j;
                }
                c = c2;
                jZzb = j2;
                j = 1;
                i4 = i3 + 1;
            }
            i = (int) (jPosition - jZzb);
        }
        byteBuffer.position(i);
    }
}
