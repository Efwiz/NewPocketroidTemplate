package org.jetbrains.kotlin.com.intellij.util.io;

import androidx.collection.ScatterMapKt;
import com.gaurav.avnc.vnc.XKeySym;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.nio.charset.Charset;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes3.dex */
public final class Murmur3_32Hash {
    public static final Murmur3_32Hash MURMUR3_32 = new Murmur3_32Hash(0);
    private final int seed;

    private static long charToThreeUtf8Bytes(char c) {
        return (((c & MangleConstant.Q_MARK) | 128) << 16) | (((c >>> '\f') | 480) & 255) | ((((c >>> 6) & 63) | 128) << 8);
    }

    private static long charToTwoUtf8Bytes(char c) {
        return (((c & MangleConstant.Q_MARK) | 128) << 8) | (((c >>> 6) | XKeySym.XK_Amacron) & 255);
    }

    private static long codePointToFourUtf8Bytes(int i) {
        return ((((long) (i >>> 18)) | 240) & 255) | ((((long) ((i >>> 12) & 63)) | 128) << 8) | ((((long) ((i >>> 6) & 63)) | 128) << 16) | ((((long) (i & 63)) | 128) << 24);
    }

    private static int fMix(int i, int i2) {
        int i3 = i ^ i2;
        int i4 = (i3 ^ (i3 >>> 16)) * (-2048144789);
        int i5 = (i4 ^ (i4 >>> 13)) * (-1028477387);
        return i5 ^ (i5 >>> 16);
    }

    private static int fromBytes(byte b, byte b2, byte b3, byte b4) {
        return (b << 24) | ((b2 & 255) << 16) | ((b3 & 255) << 8) | (b4 & 255);
    }

    private static int toInt(byte b) {
        return b & 255;
    }

    public Murmur3_32Hash(int i) {
        this.seed = i;
    }

    public int hashString(CharSequence charSequence, int i, int i2) {
        int iMixH1 = this.seed;
        int i3 = 0;
        while (true) {
            int i4 = i + 4;
            if (i4 > i2) {
                break;
            }
            char cCharAt = charSequence.charAt(i);
            char cCharAt2 = charSequence.charAt(i + 1);
            char cCharAt3 = charSequence.charAt(i + 2);
            char cCharAt4 = charSequence.charAt(i + 3);
            if (cCharAt >= 128 || cCharAt2 >= 128 || cCharAt3 >= 128 || cCharAt4 >= 128) {
                break;
            }
            iMixH1 = mixH1(iMixH1, mixK1((cCharAt2 << '\b') | cCharAt | (cCharAt3 << 16) | (cCharAt4 << 24)));
            i3 += 4;
            i = i4;
        }
        long jCharToThreeUtf8Bytes = 0;
        int i5 = 0;
        while (i < i2) {
            char cCharAt5 = charSequence.charAt(i);
            if (cCharAt5 < 128) {
                jCharToThreeUtf8Bytes |= ((long) cCharAt5) << i5;
                i5 += 8;
                i3++;
            } else if (cCharAt5 < 2048) {
                jCharToThreeUtf8Bytes |= charToTwoUtf8Bytes(cCharAt5) << i5;
                i5 += 16;
                i3 += 2;
            } else if (cCharAt5 < 55296 || cCharAt5 > 57343) {
                jCharToThreeUtf8Bytes |= charToThreeUtf8Bytes(cCharAt5) << i5;
                i5 += 24;
                i3 += 3;
            } else {
                int iCodePointAt = Character.codePointAt(charSequence, i);
                if (iCodePointAt == cCharAt5) {
                    byte[] bytes = charSequence.toString().getBytes(Charset.forName("UTF-8"));
                    return hashBytes(bytes, 0, bytes.length);
                }
                i++;
                jCharToThreeUtf8Bytes |= codePointToFourUtf8Bytes(iCodePointAt) << i5;
                i3 += 4;
            }
            if (i5 >= 32) {
                iMixH1 = mixH1(iMixH1, mixK1((int) jCharToThreeUtf8Bytes));
                jCharToThreeUtf8Bytes >>>= 32;
                i5 -= 32;
            }
            i++;
        }
        return fMix(mixK1((int) jCharToThreeUtf8Bytes) ^ iMixH1, i3);
    }

    public int hashBytes(byte[] bArr, int i, int i2) {
        checkPositionIndexes(i, i + i2, bArr.length);
        int iMixH1 = this.seed;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i4 + 4;
            if (i5 > i2) {
                break;
            }
            iMixH1 = mixH1(iMixH1, mixK1(getIntLittleEndian(bArr, i4 + i)));
            i4 = i5;
        }
        int i6 = i4;
        int i7 = 0;
        while (i6 < i2) {
            i3 ^= toInt(bArr[i + i6]) << i7;
            i6++;
            i7 += 8;
        }
        return fMix(mixK1(i3) ^ iMixH1, i2);
    }

    private static int getIntLittleEndian(byte[] bArr, int i) {
        return fromBytes(bArr[i + 3], bArr[i + 2], bArr[i + 1], bArr[i]);
    }

    private static int mixK1(int i) {
        return Integer.rotateLeft(i * ScatterMapKt.MurmurHashC1, 15) * 461845907;
    }

    private static int mixH1(int i, int i2) {
        return (Integer.rotateLeft(i ^ i2, 13) * 5) - 430675100;
    }

    private static void checkPositionIndexes(int i, int i2, int i3) {
        if (i < 0 || i2 < i || i2 > i3) {
            throw new IndexOutOfBoundsException(badPositionIndexes(i, i2, i3));
        }
    }

    private static String badPositionIndex(int i, int i2, String str) {
        if (i < 0) {
            return str + " (" + i + ") must not be negative";
        }
        if (i2 >= 0) {
            return str + " (" + i + ") must not be greater than size (" + i2 + Constant.AFTER_QUTO;
        }
        throw new IllegalArgumentException("negative size: " + i2);
    }

    private static String badPositionIndexes(int i, int i2, int i3) {
        if (i < 0 || i > i3) {
            return badPositionIndex(i, i3, "start index");
        }
        if (i2 < 0 || i2 > i3) {
            return badPositionIndex(i2, i3, "end index");
        }
        return "end index (" + i2 + ") must not be less than start index (" + i + Constant.AFTER_QUTO;
    }
}
