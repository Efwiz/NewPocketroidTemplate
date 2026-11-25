package ar.com.hjg.pngj.pixels;

import java.nio.ByteOrder;

/* JADX INFO: loaded from: classes.dex */
public final class DeflaterEstimatorLz4 {
    static final int COPY_LENGTH = 8;
    static final int HASH_LOG = 12;
    static final int HASH_LOG_64K = 13;
    static final int HASH_LOG_HC = 15;
    static final int HASH_TABLE_SIZE = 4096;
    static final int HASH_TABLE_SIZE_64K = 8192;
    static final int HASH_TABLE_SIZE_HC = 32768;
    static final int LAST_LITERALS = 5;
    static final int LZ4_64K_LIMIT = 65547;
    static final int MAX_DISTANCE = 65536;
    static final int MEMORY_USAGE = 14;
    static final int MF_LIMIT = 12;
    static final int MIN_LENGTH = 13;
    static final int MIN_MATCH = 4;
    static final int ML_BITS = 4;
    static final int ML_MASK = 15;
    static final int NOT_COMPRESSIBLE_DETECTION_LEVEL = 6;
    static final int OPTIMAL_ML = 18;
    static final int RUN_BITS = 4;
    static final int RUN_MASK = 15;
    static final ByteOrder NATIVE_BYTE_ORDER = ByteOrder.nativeOrder();
    static final int SKIP_STRENGTH = Math.max(6, 2);

    static int hash(int i) {
        return (i * (-1640531535)) >>> 20;
    }

    static int hash64k(int i) {
        return (i * (-1640531535)) >>> 19;
    }

    public int compressEstim(byte[] bArr, int i, int i2) {
        if (i2 < 10) {
            return i2;
        }
        int i3 = (65545 + i2) / 65546;
        int i4 = i2 / i3;
        if (i4 >= 65546 || i4 * i3 > i2 || i3 < 1 || i4 < 1) {
            throw new RuntimeException("?? " + i2);
        }
        int iCompress64k = 0;
        int i5 = i2;
        int i6 = 0;
        while (i5 > 0) {
            if (i5 > i4) {
                i5 = i4;
            }
            iCompress64k += compress64k(bArr, i, i5);
            i += i5;
            i6 += i5;
            i5 = i2 - i6;
        }
        return i6 == i2 ? iCompress64k : (int) (((((double) iCompress64k) / ((double) i6)) * ((double) i2)) + 0.5d);
    }

    public int compressEstim(byte[] bArr) {
        return compressEstim(bArr, 0, bArr.length);
    }

    static int compress64k(byte[] bArr, int i, int i2) {
        int i3;
        int i4 = i + i2;
        int i5 = i4 - 5;
        int i6 = i4 - 12;
        int i7 = 0;
        if (i2 >= 13) {
            short[] sArr = new short[8192];
            int i8 = i + 1;
            int i9 = i;
            int i10 = 0;
            loop0: while (true) {
                int i11 = (1 << SKIP_STRENGTH) + 3;
                while (true) {
                    int i12 = i11 + 1;
                    int i13 = (i11 >>> SKIP_STRENGTH) + i8;
                    if (i13 > i6) {
                        i = i9;
                        break loop0;
                    }
                    int iHash64k = hash64k(readInt(bArr, i8));
                    i3 = readShort(sArr, iHash64k) + i;
                    writeShort(sArr, iHash64k, i8 - i);
                    if (readIntEquals(bArr, i3, i8)) {
                        break;
                    }
                    i8 = i13;
                    i11 = i12;
                }
                int iCommonBytesBackward = commonBytesBackward(bArr, i3, i8, i, i9);
                int i14 = i8 - iCommonBytesBackward;
                int i15 = i3 - iCommonBytesBackward;
                int i16 = i14 - i9;
                int i17 = i10 + 1;
                if (i16 >= 15) {
                    if (i16 > 15) {
                        i17 += (i16 - 15) / 255;
                    }
                    i17++;
                }
                int i18 = i17 + i16;
                while (true) {
                    i10 = i18 + 2;
                    int i19 = i14 + 4;
                    int iCommonBytes = commonBytes(bArr, i15 + 4, i19, i5);
                    i14 = i19 + iCommonBytes;
                    if (iCommonBytes >= 15) {
                        if (iCommonBytes >= 270) {
                            i10 += (iCommonBytes - 15) / 255;
                        }
                        i10++;
                    }
                    if (i14 > i6) {
                        i = i14;
                        break loop0;
                    }
                    int i20 = i14 - 2;
                    writeShort(sArr, hash64k(readInt(bArr, i20)), i20 - i);
                    int iHash64k2 = hash64k(readInt(bArr, i14));
                    i15 = i + readShort(sArr, iHash64k2);
                    writeShort(sArr, iHash64k2, i14 - i);
                    if (!readIntEquals(bArr, i14, i15)) {
                        break;
                    }
                    i18 = i10 + 1;
                }
                i9 = i14;
                i8 = i14 + 1;
            }
            i7 = i10;
        }
        int i21 = i4 - i;
        if (i21 >= 270) {
            i7 += (i21 - 15) / 255;
        }
        return i7 + 1 + i21;
    }

    static final int maxCompressedLength(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("length must be >= 0, got " + i);
        }
        return i + (i / 255) + 16;
    }

    static int readShortLittleEndian(byte[] bArr, int i) {
        return ((bArr[i + 1] & 255) << 8) | (bArr[i] & 255);
    }

    static boolean readIntEquals(byte[] bArr, int i, int i2) {
        return bArr[i] == bArr[i2] && bArr[i + 1] == bArr[i2 + 1] && bArr[i + 2] == bArr[i2 + 2] && bArr[i + 3] == bArr[i2 + 3];
    }

    static int commonBytes(byte[] bArr, int i, int i2, int i3) {
        int i4 = 0;
        while (i2 < i3) {
            int i5 = i + 1;
            int i6 = i2 + 1;
            if (bArr[i] != bArr[i2]) {
                break;
            }
            i4++;
            i = i5;
            i2 = i6;
        }
        return i4;
    }

    static int commonBytesBackward(byte[] bArr, int i, int i2, int i3, int i4) {
        int i5 = 0;
        while (i > i3 && i2 > i4) {
            i--;
            i2--;
            if (bArr[i] != bArr[i2]) {
                break;
            }
            i5++;
        }
        return i5;
    }

    static int readShort(short[] sArr, int i) {
        return sArr[i] & 65535;
    }

    static byte readByte(byte[] bArr, int i) {
        return bArr[i];
    }

    static void checkRange(byte[] bArr, int i) {
        if (i < 0 || i >= bArr.length) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
    }

    static void checkRange(byte[] bArr, int i, int i2) {
        checkLength(i2);
        if (i2 > 0) {
            checkRange(bArr, i);
            checkRange(bArr, (i + i2) - 1);
        }
    }

    static void checkLength(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("lengths must be >= 0");
        }
    }

    static int readIntBE(byte[] bArr, int i) {
        return (bArr[i + 3] & 255) | ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8);
    }

    static int readIntLE(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    static int readInt(byte[] bArr, int i) {
        if (NATIVE_BYTE_ORDER == ByteOrder.BIG_ENDIAN) {
            return readIntBE(bArr, i);
        }
        return readIntLE(bArr, i);
    }

    static void writeShort(short[] sArr, int i, int i2) {
        sArr[i] = (short) i2;
    }
}
