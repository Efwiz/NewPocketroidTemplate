package org.jetbrains.kotlin.net.jpountz.lz4;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.jetbrains.kotlin.net.jpountz.util.ByteBufferUtils;
import org.jetbrains.kotlin.net.jpountz.util.SafeUtils;

/* JADX INFO: loaded from: classes2.dex */
final class LZ4JavaSafeCompressor extends LZ4Compressor {
    public static final LZ4Compressor INSTANCE = new LZ4JavaSafeCompressor();

    LZ4JavaSafeCompressor() {
    }

    static int compress64k(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = i;
        int i8 = i7 + i2;
        int i9 = i8 - 5;
        int i10 = i8 - 12;
        if (i2 >= 13) {
            short[] sArr = new short[8192];
            int i11 = i7 + 1;
            i5 = i3;
            int i12 = i7;
            loop0: while (true) {
                int i13 = 1;
                int i14 = 1 << LZ4Constants.SKIP_STRENGTH;
                while (true) {
                    int i15 = i13 + i11;
                    int i16 = i14 + 1;
                    int i17 = i14 >>> LZ4Constants.SKIP_STRENGTH;
                    if (i15 > i10) {
                        i7 = i12;
                        break loop0;
                    }
                    int iHash64k = LZ4Utils.hash64k(SafeUtils.readInt(bArr, i11));
                    i6 = SafeUtils.readShort(sArr, iHash64k) + i7;
                    SafeUtils.writeShort(sArr, iHash64k, i11 - i7);
                    if (LZ4SafeUtils.readIntEquals(bArr, i6, i11)) {
                        break;
                    }
                    i11 = i15;
                    i14 = i16;
                    i13 = i17;
                }
                int iCommonBytesBackward = LZ4SafeUtils.commonBytesBackward(bArr, i6, i11, i7, i12);
                int i18 = i11 - iCommonBytesBackward;
                int i19 = i6 - iCommonBytesBackward;
                int i20 = i18 - i12;
                int iWriteLen = i5 + 1;
                if (iWriteLen + i20 + 8 + (i20 >>> 8) > i4) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                if (i20 >= 15) {
                    SafeUtils.writeByte(bArr2, i5, 240);
                    iWriteLen = LZ4SafeUtils.writeLen(i20 - 15, bArr2, iWriteLen);
                } else {
                    SafeUtils.writeByte(bArr2, i5, i20 << 4);
                }
                LZ4SafeUtils.wildArraycopy(bArr, i12, bArr2, iWriteLen, i20);
                int i21 = iWriteLen + i20;
                while (true) {
                    SafeUtils.writeShortLE(bArr2, i21, (short) (i18 - i19));
                    int iWriteLen2 = i21 + 2;
                    int i22 = i18 + 4;
                    int iCommonBytes = LZ4SafeUtils.commonBytes(bArr, i19 + 4, i22, i9);
                    if (i21 + 8 + (iCommonBytes >>> 8) > i4) {
                        throw new LZ4Exception("maxDestLen is too small");
                    }
                    i18 = i22 + iCommonBytes;
                    if (iCommonBytes >= 15) {
                        SafeUtils.writeByte(bArr2, i5, SafeUtils.readByte(bArr2, i5) | 15);
                        iWriteLen2 = LZ4SafeUtils.writeLen(iCommonBytes - 15, bArr2, iWriteLen2);
                    } else {
                        SafeUtils.writeByte(bArr2, i5, iCommonBytes | SafeUtils.readByte(bArr2, i5));
                    }
                    i5 = iWriteLen2;
                    if (i18 > i10) {
                        i7 = i18;
                        break loop0;
                    }
                    int i23 = i18 - 2;
                    SafeUtils.writeShort(sArr, LZ4Utils.hash64k(SafeUtils.readInt(bArr, i23)), i23 - i7);
                    int iHash64k2 = LZ4Utils.hash64k(SafeUtils.readInt(bArr, i18));
                    i19 = i7 + SafeUtils.readShort(sArr, iHash64k2);
                    SafeUtils.writeShort(sArr, iHash64k2, i18 - i7);
                    if (!LZ4SafeUtils.readIntEquals(bArr, i18, i19)) {
                        break;
                    }
                    SafeUtils.writeByte(bArr2, i5, 0);
                    i21 = i5 + 1;
                }
                i12 = i18;
                i11 = i18 + 1;
            }
        } else {
            i5 = i3;
        }
        return LZ4SafeUtils.lastLiterals(bArr, i7, i8 - i7, bArr2, i5, i4) - i3;
    }

    @Override // org.jetbrains.kotlin.net.jpountz.lz4.LZ4Compressor
    public int compress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8 = i;
        SafeUtils.checkRange(bArr, i, i2);
        SafeUtils.checkRange(bArr2, i3, i4);
        int i9 = i3 + i4;
        if (i2 < 65547) {
            return compress64k(bArr, i, i2, bArr2, i3, i9);
        }
        int i10 = i2 + i8;
        int i11 = i10 - 5;
        int i12 = i10 - 12;
        int i13 = i8 + 1;
        int[] iArr = new int[4096];
        Arrays.fill(iArr, i8);
        int i14 = i3;
        int i15 = i8;
        loop0: while (true) {
            int i16 = 1;
            int i17 = 1 << LZ4Constants.SKIP_STRENGTH;
            while (true) {
                int i18 = i16 + i13;
                int i19 = i17 + 1;
                int i20 = i17 >>> LZ4Constants.SKIP_STRENGTH;
                if (i18 > i12) {
                    i5 = i15;
                    break loop0;
                }
                int iHash = LZ4Utils.hash(SafeUtils.readInt(bArr, i13));
                i6 = SafeUtils.readInt(iArr, iHash);
                i7 = i13 - i6;
                SafeUtils.writeInt(iArr, iHash, i13);
                if (i7 >= 65536 || !LZ4SafeUtils.readIntEquals(bArr, i6, i13)) {
                    i8 = i;
                    i13 = i18;
                    i17 = i19;
                    i16 = i20;
                }
            }
            int iCommonBytesBackward = LZ4SafeUtils.commonBytesBackward(bArr, i6, i13, i8, i15);
            int i21 = i13 - iCommonBytesBackward;
            int i22 = i6 - iCommonBytesBackward;
            int i23 = i21 - i15;
            int iWriteLen = i14 + 1;
            if (iWriteLen + i23 + 8 + (i23 >>> 8) > i9) {
                throw new LZ4Exception("maxDestLen is too small");
            }
            if (i23 >= 15) {
                SafeUtils.writeByte(bArr2, i14, 240);
                iWriteLen = LZ4SafeUtils.writeLen(i23 - 15, bArr2, iWriteLen);
            } else {
                SafeUtils.writeByte(bArr2, i14, i23 << 4);
            }
            LZ4SafeUtils.wildArraycopy(bArr, i15, bArr2, iWriteLen, i23);
            int i24 = iWriteLen + i23;
            while (true) {
                SafeUtils.writeShortLE(bArr2, i24, i7);
                int iWriteLen2 = i24 + 2;
                int i25 = i21 + 4;
                int iCommonBytes = LZ4SafeUtils.commonBytes(bArr, i22 + 4, i25, i11);
                if (i24 + 8 + (iCommonBytes >>> 8) > i9) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                i21 = i25 + iCommonBytes;
                if (iCommonBytes >= 15) {
                    SafeUtils.writeByte(bArr2, i14, SafeUtils.readByte(bArr2, i14) | 15);
                    iWriteLen2 = LZ4SafeUtils.writeLen(iCommonBytes - 15, bArr2, iWriteLen2);
                } else {
                    SafeUtils.writeByte(bArr2, i14, iCommonBytes | SafeUtils.readByte(bArr2, i14));
                }
                i14 = iWriteLen2;
                if (i21 > i12) {
                    i5 = i21;
                    break loop0;
                }
                int i26 = i21 - 2;
                SafeUtils.writeInt(iArr, LZ4Utils.hash(SafeUtils.readInt(bArr, i26)), i26);
                int iHash2 = LZ4Utils.hash(SafeUtils.readInt(bArr, i21));
                i22 = SafeUtils.readInt(iArr, iHash2);
                SafeUtils.writeInt(iArr, iHash2, i21);
                i7 = i21 - i22;
                if (i7 >= 65536 || !LZ4SafeUtils.readIntEquals(bArr, i22, i21)) {
                    break;
                }
                i24 = i14 + 1;
                SafeUtils.writeByte(bArr2, i14, 0);
            }
            i15 = i21;
            i13 = i21 + 1;
            i8 = i;
        }
        return LZ4SafeUtils.lastLiterals(bArr, i5, i10 - i5, bArr2, i14, i9) - i3;
    }

    static int compress64k(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        int i5;
        int i6;
        int i7 = i;
        int i8 = i7 + i2;
        int i9 = i8 - 5;
        int i10 = i8 - 12;
        if (i2 >= 13) {
            short[] sArr = new short[8192];
            int i11 = i7 + 1;
            i5 = i3;
            int i12 = i7;
            loop0: while (true) {
                int i13 = 1;
                int i14 = 1 << LZ4Constants.SKIP_STRENGTH;
                while (true) {
                    int i15 = i13 + i11;
                    int i16 = i14 + 1;
                    int i17 = i14 >>> LZ4Constants.SKIP_STRENGTH;
                    if (i15 > i10) {
                        i7 = i12;
                        break loop0;
                    }
                    int iHash64k = LZ4Utils.hash64k(ByteBufferUtils.readInt(byteBuffer, i11));
                    i6 = SafeUtils.readShort(sArr, iHash64k) + i7;
                    SafeUtils.writeShort(sArr, iHash64k, i11 - i7);
                    if (LZ4ByteBufferUtils.readIntEquals(byteBuffer, i6, i11)) {
                        break;
                    }
                    i11 = i15;
                    i14 = i16;
                    i13 = i17;
                }
                int iCommonBytesBackward = LZ4ByteBufferUtils.commonBytesBackward(byteBuffer, i6, i11, i7, i12);
                int i18 = i11 - iCommonBytesBackward;
                int i19 = i6 - iCommonBytesBackward;
                int i20 = i18 - i12;
                int iWriteLen = i5 + 1;
                if (iWriteLen + i20 + 8 + (i20 >>> 8) > i4) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                if (i20 >= 15) {
                    ByteBufferUtils.writeByte(byteBuffer2, i5, 240);
                    iWriteLen = LZ4ByteBufferUtils.writeLen(i20 - 15, byteBuffer2, iWriteLen);
                } else {
                    ByteBufferUtils.writeByte(byteBuffer2, i5, i20 << 4);
                }
                LZ4ByteBufferUtils.wildArraycopy(byteBuffer, i12, byteBuffer2, iWriteLen, i20);
                int i21 = iWriteLen + i20;
                while (true) {
                    ByteBufferUtils.writeShortLE(byteBuffer2, i21, (short) (i18 - i19));
                    int iWriteLen2 = i21 + 2;
                    int i22 = i18 + 4;
                    int iCommonBytes = LZ4ByteBufferUtils.commonBytes(byteBuffer, i19 + 4, i22, i9);
                    if (i21 + 8 + (iCommonBytes >>> 8) > i4) {
                        throw new LZ4Exception("maxDestLen is too small");
                    }
                    i18 = i22 + iCommonBytes;
                    if (iCommonBytes >= 15) {
                        ByteBufferUtils.writeByte(byteBuffer2, i5, ByteBufferUtils.readByte(byteBuffer2, i5) | 15);
                        iWriteLen2 = LZ4ByteBufferUtils.writeLen(iCommonBytes - 15, byteBuffer2, iWriteLen2);
                    } else {
                        ByteBufferUtils.writeByte(byteBuffer2, i5, iCommonBytes | ByteBufferUtils.readByte(byteBuffer2, i5));
                    }
                    i5 = iWriteLen2;
                    if (i18 > i10) {
                        i7 = i18;
                        break loop0;
                    }
                    int i23 = i18 - 2;
                    SafeUtils.writeShort(sArr, LZ4Utils.hash64k(ByteBufferUtils.readInt(byteBuffer, i23)), i23 - i7);
                    int iHash64k2 = LZ4Utils.hash64k(ByteBufferUtils.readInt(byteBuffer, i18));
                    i19 = i7 + SafeUtils.readShort(sArr, iHash64k2);
                    SafeUtils.writeShort(sArr, iHash64k2, i18 - i7);
                    if (!LZ4ByteBufferUtils.readIntEquals(byteBuffer, i18, i19)) {
                        break;
                    }
                    ByteBufferUtils.writeByte(byteBuffer2, i5, 0);
                    i21 = i5 + 1;
                }
                i12 = i18;
                i11 = i18 + 1;
            }
        } else {
            i5 = i3;
        }
        return LZ4ByteBufferUtils.lastLiterals(byteBuffer, i7, i8 - i7, byteBuffer2, i5, i4) - i3;
    }

    @Override // org.jetbrains.kotlin.net.jpountz.lz4.LZ4Compressor
    public int compress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8 = i;
        if (byteBuffer.hasArray() && byteBuffer2.hasArray()) {
            return compress(byteBuffer.array(), i8 + byteBuffer.arrayOffset(), i2, byteBuffer2.array(), i3 + byteBuffer2.arrayOffset(), i4);
        }
        ByteBuffer byteBufferInNativeByteOrder = ByteBufferUtils.inNativeByteOrder(byteBuffer);
        ByteBuffer byteBufferInNativeByteOrder2 = ByteBufferUtils.inNativeByteOrder(byteBuffer2);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder, i8, i2);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder2, i3, i4);
        int i9 = i3 + i4;
        if (i2 < 65547) {
            return compress64k(byteBufferInNativeByteOrder, i, i2, byteBufferInNativeByteOrder2, i3, i9);
        }
        int i10 = i8 + i2;
        int i11 = i10 - 5;
        int i12 = i10 - 12;
        int i13 = i8 + 1;
        int[] iArr = new int[4096];
        Arrays.fill(iArr, i8);
        int i14 = i8;
        int i15 = i3;
        loop0: while (true) {
            int i16 = 1;
            int i17 = 1 << LZ4Constants.SKIP_STRENGTH;
            while (true) {
                int i18 = i16 + i13;
                int i19 = i17 + 1;
                int i20 = i17 >>> LZ4Constants.SKIP_STRENGTH;
                if (i18 > i12) {
                    i5 = i14;
                    break loop0;
                }
                int iHash = LZ4Utils.hash(ByteBufferUtils.readInt(byteBufferInNativeByteOrder, i13));
                i6 = SafeUtils.readInt(iArr, iHash);
                i7 = i13 - i6;
                SafeUtils.writeInt(iArr, iHash, i13);
                if (i7 >= 65536 || !LZ4ByteBufferUtils.readIntEquals(byteBufferInNativeByteOrder, i6, i13)) {
                    i16 = i20;
                    i8 = i;
                    i13 = i18;
                    i17 = i19;
                }
            }
            int iCommonBytesBackward = LZ4ByteBufferUtils.commonBytesBackward(byteBufferInNativeByteOrder, i6, i13, i8, i14);
            int i21 = i13 - iCommonBytesBackward;
            int i22 = i6 - iCommonBytesBackward;
            int i23 = i21 - i14;
            int iWriteLen = i15 + 1;
            if (iWriteLen + i23 + 8 + (i23 >>> 8) > i9) {
                throw new LZ4Exception("maxDestLen is too small");
            }
            if (i23 >= 15) {
                ByteBufferUtils.writeByte(byteBufferInNativeByteOrder2, i15, 240);
                iWriteLen = LZ4ByteBufferUtils.writeLen(i23 - 15, byteBufferInNativeByteOrder2, iWriteLen);
            } else {
                ByteBufferUtils.writeByte(byteBufferInNativeByteOrder2, i15, i23 << 4);
            }
            LZ4ByteBufferUtils.wildArraycopy(byteBufferInNativeByteOrder, i14, byteBufferInNativeByteOrder2, iWriteLen, i23);
            int i24 = iWriteLen + i23;
            int i25 = i21;
            while (true) {
                ByteBufferUtils.writeShortLE(byteBufferInNativeByteOrder2, i24, i7);
                int iWriteLen2 = i24 + 2;
                int i26 = i25 + 4;
                int iCommonBytes = LZ4ByteBufferUtils.commonBytes(byteBufferInNativeByteOrder, i22 + 4, i26, i11);
                if (i24 + 8 + (iCommonBytes >>> 8) > i9) {
                    throw new LZ4Exception("maxDestLen is too small");
                }
                i25 = i26 + iCommonBytes;
                if (iCommonBytes >= 15) {
                    ByteBufferUtils.writeByte(byteBufferInNativeByteOrder2, i15, ByteBufferUtils.readByte(byteBufferInNativeByteOrder2, i15) | 15);
                    iWriteLen2 = LZ4ByteBufferUtils.writeLen(iCommonBytes - 15, byteBufferInNativeByteOrder2, iWriteLen2);
                } else {
                    ByteBufferUtils.writeByte(byteBufferInNativeByteOrder2, i15, iCommonBytes | ByteBufferUtils.readByte(byteBufferInNativeByteOrder2, i15));
                }
                i15 = iWriteLen2;
                if (i25 > i12) {
                    i5 = i25;
                    break loop0;
                }
                int i27 = i25 - 2;
                SafeUtils.writeInt(iArr, LZ4Utils.hash(ByteBufferUtils.readInt(byteBufferInNativeByteOrder, i27)), i27);
                int iHash2 = LZ4Utils.hash(ByteBufferUtils.readInt(byteBufferInNativeByteOrder, i25));
                i22 = SafeUtils.readInt(iArr, iHash2);
                SafeUtils.writeInt(iArr, iHash2, i25);
                int i28 = i25 - i22;
                if (i28 >= 65536 || !LZ4ByteBufferUtils.readIntEquals(byteBufferInNativeByteOrder, i22, i25)) {
                    i13 = i25 + 1;
                    i14 = i25;
                    i8 = i;
                } else {
                    i24 = i15 + 1;
                    ByteBufferUtils.writeByte(byteBufferInNativeByteOrder2, i15, 0);
                    i7 = i28;
                }
            }
        }
        return LZ4ByteBufferUtils.lastLiterals(byteBufferInNativeByteOrder, i5, i10 - i5, byteBufferInNativeByteOrder2, i15, i9) - i3;
    }
}
