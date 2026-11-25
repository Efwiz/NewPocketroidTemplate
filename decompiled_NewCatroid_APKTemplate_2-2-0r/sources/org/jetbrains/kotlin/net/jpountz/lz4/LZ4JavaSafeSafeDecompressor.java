package org.jetbrains.kotlin.net.jpountz.lz4;

import java.nio.ByteBuffer;
import org.jetbrains.kotlin.net.jpountz.util.ByteBufferUtils;
import org.jetbrains.kotlin.net.jpountz.util.SafeUtils;

/* JADX INFO: loaded from: classes2.dex */
final class LZ4JavaSafeSafeDecompressor extends LZ4SafeDecompressor {
    public static final LZ4SafeDecompressor INSTANCE = new LZ4JavaSafeSafeDecompressor();

    LZ4JavaSafeSafeDecompressor() {
    }

    @Override // org.jetbrains.kotlin.net.jpountz.lz4.LZ4SafeDecompressor, org.jetbrains.kotlin.net.jpountz.lz4.LZ4UnknownSizeDecompressor
    public int decompress(byte[] bArr, int i, int i2, byte[] bArr2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        SafeUtils.checkRange(bArr, i, i2);
        SafeUtils.checkRange(bArr2, i3, i4);
        int i9 = 1;
        if (i4 == 0) {
            if (i2 == 1 && SafeUtils.readByte(bArr, i) == 0) {
                return 0;
            }
            throw new LZ4Exception("Output buffer too small");
        }
        int i10 = i + i2;
        int i11 = i3 + i4;
        int i12 = i;
        int i13 = i3;
        while (true) {
            byte b = SafeUtils.readByte(bArr, i12);
            i5 = i12 + i9;
            i6 = (b & 255) >>> 4;
            if (i6 == 15) {
                byte b2 = -1;
                while (i5 < i10) {
                    int i14 = i5 + 1;
                    byte b3 = SafeUtils.readByte(bArr, i5);
                    if (b3 != -1) {
                        b2 = b3;
                        i5 = i14;
                        break;
                    }
                    i6 += 255;
                    b2 = b3;
                    i5 = i14;
                }
                i6 += b2 & 255;
            }
            i7 = i13 + i6;
            int i15 = i11 - 8;
            if (i7 > i15 || (i8 = i5 + i6) > i10 - 8) {
                break;
            }
            LZ4SafeUtils.wildArraycopy(bArr, i5, bArr2, i13, i6);
            int shortLE = SafeUtils.readShortLE(bArr, i8);
            int i16 = i8 + 2;
            int i17 = i7 - shortLE;
            if (i17 < i3) {
                throw new LZ4Exception("Malformed input at " + i16);
            }
            int i18 = b & 15;
            if (i18 == 15) {
                byte b4 = -1;
                while (i16 < i10) {
                    int i19 = i16 + 1;
                    byte b5 = SafeUtils.readByte(bArr, i16);
                    if (b5 != -1) {
                        i16 = i19;
                        b4 = b5;
                        break;
                    }
                    i18 += 255;
                    i16 = i19;
                    b4 = b5;
                }
                i18 += b4 & 255;
            }
            int i20 = i18 + 4;
            i13 = i7 + i20;
            if (i13 <= i15) {
                LZ4SafeUtils.wildIncrementalCopy(bArr2, i17, i7, i13);
            } else {
                if (i13 > i11) {
                    throw new LZ4Exception("Malformed input at " + i16);
                }
                LZ4SafeUtils.safeIncrementalCopy(bArr2, i17, i7, i20);
            }
            i12 = i16;
            i9 = 1;
        }
        if (i7 > i11) {
            throw new LZ4Exception();
        }
        if (i5 + i6 != i10) {
            throw new LZ4Exception("Malformed input at " + i5);
        }
        LZ4SafeUtils.safeArraycopy(bArr, i5, bArr2, i13, i6);
        return i7 - i3;
    }

    @Override // org.jetbrains.kotlin.net.jpountz.lz4.LZ4SafeDecompressor
    public int decompress(ByteBuffer byteBuffer, int i, int i2, ByteBuffer byteBuffer2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9 = i;
        if (byteBuffer.hasArray() && byteBuffer2.hasArray()) {
            return decompress(byteBuffer.array(), byteBuffer.arrayOffset() + i9, i2, byteBuffer2.array(), i3 + byteBuffer2.arrayOffset(), i4);
        }
        ByteBuffer byteBufferInNativeByteOrder = ByteBufferUtils.inNativeByteOrder(byteBuffer);
        ByteBuffer byteBufferInNativeByteOrder2 = ByteBufferUtils.inNativeByteOrder(byteBuffer2);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder, i9, i2);
        ByteBufferUtils.checkRange(byteBufferInNativeByteOrder2, i3, i4);
        int i10 = 1;
        if (i4 == 0) {
            if (i2 == 1 && ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i9) == 0) {
                return 0;
            }
            throw new LZ4Exception("Output buffer too small");
        }
        int i11 = i2 + i9;
        int i12 = i4 + i3;
        int i13 = i3;
        while (true) {
            byte b = ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i9);
            i5 = i9 + i10;
            i6 = (b & 255) >>> 4;
            if (i6 == 15) {
                byte b2 = -1;
                while (i5 < i11) {
                    int i14 = i5 + 1;
                    byte b3 = ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i5);
                    if (b3 != -1) {
                        b2 = b3;
                        i5 = i14;
                        break;
                    }
                    i6 += 255;
                    b2 = b3;
                    i5 = i14;
                }
                i6 += b2 & 255;
            }
            i7 = i13 + i6;
            int i15 = i12 - 8;
            if (i7 > i15 || (i8 = i5 + i6) > i11 - 8) {
                break;
            }
            LZ4ByteBufferUtils.wildArraycopy(byteBufferInNativeByteOrder, i5, byteBufferInNativeByteOrder2, i13, i6);
            int shortLE = ByteBufferUtils.readShortLE(byteBufferInNativeByteOrder, i8);
            int i16 = i8 + 2;
            int i17 = i7 - shortLE;
            if (i17 < i3) {
                throw new LZ4Exception("Malformed input at " + i16);
            }
            int i18 = b & 15;
            if (i18 == 15) {
                byte b4 = -1;
                while (i16 < i11) {
                    int i19 = i16 + 1;
                    byte b5 = ByteBufferUtils.readByte(byteBufferInNativeByteOrder, i16);
                    if (b5 != -1) {
                        i16 = i19;
                        b4 = b5;
                        break;
                    }
                    i18 += 255;
                    i16 = i19;
                    b4 = b5;
                }
                i18 += b4 & 255;
            }
            int i20 = i18 + 4;
            i13 = i7 + i20;
            if (i13 <= i15) {
                LZ4ByteBufferUtils.wildIncrementalCopy(byteBufferInNativeByteOrder2, i17, i7, i13);
            } else {
                if (i13 > i12) {
                    throw new LZ4Exception("Malformed input at " + i16);
                }
                LZ4ByteBufferUtils.safeIncrementalCopy(byteBufferInNativeByteOrder2, i17, i7, i20);
            }
            i9 = i16;
            i10 = 1;
        }
        if (i7 > i12) {
            throw new LZ4Exception();
        }
        if (i5 + i6 != i11) {
            throw new LZ4Exception("Malformed input at " + i5);
        }
        LZ4ByteBufferUtils.safeArraycopy(byteBufferInNativeByteOrder, i5, byteBufferInNativeByteOrder2, i13, i6);
        return i7 - i3;
    }
}
