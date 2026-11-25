package org.jetbrains.kotlin.wasm.ir.convertors;

import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;

/* JADX INFO: compiled from: WasmBinaryToIR.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\n\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\u0019\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bH&¢\u0006\u0002\u0010\rJ\u0012\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\bH\u0002J\u0013\u0010\u0011\u001a\u00020\u0012ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0013\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u0016ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u001aø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001b\u0010\u001cJ\u001f\u0010\u001d\u001a\u00020\u00162\b\b\u0002\u0010\u0010\u001a\u00020\bH\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001e\u0010\u001fJ\u0006\u0010 \u001a\u00020\bJ\u0006\u0010!\u001a\u00020\u000fJ\u0006\u0010\"\u001a\u00020\nJ\u0006\u0010#\u001a\u00020\u0004J\u0013\u0010$\u001a\u00020\u0016ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b%\u0010\u0018J\u0006\u0010&\u001a\u00020'R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/wasm/ir/convertors/ByteReader;", "", "()V", "isEof", "", "()Z", "read", "amount", "", "readByte", "", "readBytes", "", "(Ljava/lang/Integer;)[B", "readSignedLeb128", "", "maxCount", "readUByte", "Lkotlin/UByte;", "readUByte-w2LRezQ", "()B", "readUInt32", "Lkotlin/UInt;", "readUInt32-pVg5ArA", "()I", "readUInt64", "Lkotlin/ULong;", "readUInt64-s-VKNKU", "()J", "readUnsignedLeb128", "readUnsignedLeb128-OGnWXxg", "(I)I", "readVarInt32", "readVarInt64", "readVarInt7", "readVarUInt1", "readVarUInt32", "readVarUInt32-pVg5ArA", "readVarUInt7", "", "wasm.ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ByteReader {
    public abstract boolean isEof();

    public abstract ByteReader read(int amount);

    public abstract byte readByte();

    public abstract byte[] readBytes(Integer amount);

    public static /* synthetic */ byte[] readBytes$default(ByteReader byteReader, Integer num, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: readBytes");
        }
        if ((i & 1) != 0) {
            num = null;
        }
        return byteReader.readBytes(num);
    }

    /* JADX INFO: renamed from: readUByte-w2LRezQ, reason: not valid java name */
    public final byte m7500readUBytew2LRezQ() {
        return UByte.m2916constructorimpl(readByte());
    }

    /* JADX INFO: renamed from: readUInt32-pVg5ArA, reason: not valid java name */
    public final int m7501readUInt32pVg5ArA() {
        return UInt.m2993constructorimpl(UInt.m2993constructorimpl(UInt.m2993constructorimpl(UInt.m2993constructorimpl(m7500readUBytew2LRezQ() & 255) | UInt.m2993constructorimpl(UInt.m2993constructorimpl(m7500readUBytew2LRezQ() & 255) << 8)) | UInt.m2993constructorimpl(UInt.m2993constructorimpl(m7500readUBytew2LRezQ() & 255) << 16)) | UInt.m2993constructorimpl(UInt.m2993constructorimpl(m7500readUBytew2LRezQ() & 255) << 24));
    }

    /* JADX INFO: renamed from: readUInt64-s-VKNKU, reason: not valid java name */
    public final long m7502readUInt64sVKNKU() {
        return ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(((long) m7500readUBytew2LRezQ()) & 255) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(((long) m7500readUBytew2LRezQ()) & 255) << 8)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(((long) m7500readUBytew2LRezQ()) & 255) << 16)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(((long) m7500readUBytew2LRezQ()) & 255) << 24)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(((long) m7500readUBytew2LRezQ()) & 255) << 32)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(((long) m7500readUBytew2LRezQ()) & 255) << 40)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(((long) m7500readUBytew2LRezQ()) & 255) << 48)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(255 & ((long) m7500readUBytew2LRezQ())) << 56));
    }

    public final byte readVarInt7() {
        long signedLeb128$default = readSignedLeb128$default(this, 0, 1, null);
        if (signedLeb128$default < -128 || signedLeb128$default > 127) {
            throw new IllegalStateException("InvalidLeb128Number".toString());
        }
        return (byte) signedLeb128$default;
    }

    public final int readVarInt32() {
        long signedLeb128$default = readSignedLeb128$default(this, 0, 1, null);
        if (signedLeb128$default < -2147483648L || signedLeb128$default > 2147483647L) {
            throw new IllegalStateException("InvalidLeb128Number".toString());
        }
        return (int) signedLeb128$default;
    }

    public final long readVarInt64() {
        return readSignedLeb128(9);
    }

    public final boolean readVarUInt1() {
        int iM7499readUnsignedLeb128OGnWXxg$default = m7499readUnsignedLeb128OGnWXxg$default(this, 0, 1, null);
        if (iM7499readUnsignedLeb128OGnWXxg$default == 1 || iM7499readUnsignedLeb128OGnWXxg$default == 0) {
            return iM7499readUnsignedLeb128OGnWXxg$default == 1;
        }
        throw new IllegalStateException("InvalidLeb128Number".toString());
    }

    public final short readVarUInt7() {
        int iM7499readUnsignedLeb128OGnWXxg$default = m7499readUnsignedLeb128OGnWXxg$default(this, 0, 1, null);
        if (Integer.compareUnsigned(iM7499readUnsignedLeb128OGnWXxg$default, 255) <= 0) {
            return (short) iM7499readUnsignedLeb128OGnWXxg$default;
        }
        throw new IllegalStateException("InvalidLeb128Number".toString());
    }

    /* JADX INFO: renamed from: readVarUInt32-pVg5ArA, reason: not valid java name */
    public final int m7504readVarUInt32pVg5ArA() {
        return m7499readUnsignedLeb128OGnWXxg$default(this, 0, 1, null);
    }

    /* JADX INFO: renamed from: readUnsignedLeb128-OGnWXxg$default, reason: not valid java name */
    public static /* synthetic */ int m7499readUnsignedLeb128OGnWXxg$default(ByteReader byteReader, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: readUnsignedLeb128-OGnWXxg");
        }
        if ((i2 & 1) != 0) {
            i = 4;
        }
        return byteReader.m7503readUnsignedLeb128OGnWXxg(i);
    }

    /* JADX INFO: renamed from: readUnsignedLeb128-OGnWXxg, reason: not valid java name */
    protected final int m7503readUnsignedLeb128OGnWXxg(int maxCount) {
        int i;
        int iM2993constructorimpl = 0;
        int i2 = 0;
        do {
            int iM2993constructorimpl2 = UInt.m2993constructorimpl(UInt.m2993constructorimpl(m7500readUBytew2LRezQ() & 255) & 255);
            iM2993constructorimpl = UInt.m2993constructorimpl(iM2993constructorimpl | UInt.m2993constructorimpl(UInt.m2993constructorimpl(iM2993constructorimpl2 & 127) << (i2 * 7)));
            i2++;
            i = iM2993constructorimpl2 & 128;
            if (UInt.m2993constructorimpl(i) != 128) {
                break;
            }
        } while (i2 <= maxCount);
        if (UInt.m2993constructorimpl(i) != 128) {
            return iM2993constructorimpl;
        }
        throw new IllegalStateException("InvalidLeb128Number".toString());
    }

    static /* synthetic */ long readSignedLeb128$default(ByteReader byteReader, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: readSignedLeb128");
        }
        if ((i2 & 1) != 0) {
            i = 4;
        }
        return byteReader.readSignedLeb128(i);
    }

    private final long readSignedLeb128(int maxCount) {
        byte b;
        int i;
        int i2;
        int i3 = 0;
        long j = -1;
        long j2 = 0;
        do {
            b = readByte();
            i = b & 255;
            j2 |= ((long) (b & 127)) << (i3 * 7);
            j <<= 7;
            i3++;
            i2 = b & 128;
            if (i2 != 128) {
                break;
            }
        } while (i3 <= maxCount);
        if (i2 == 128) {
            throw new IllegalStateException("InvalidLeb128Number".toString());
        }
        if (i3 > maxCount && maxCount == 9) {
            if ((b & 64) == 64) {
                if (((byte) (i | 128)) != -1) {
                    throw new IllegalStateException("InvalidLeb128Number".toString());
                }
            } else if (i != 0) {
                throw new IllegalStateException("InvalidLeb128Number".toString());
            }
        }
        return ((j >> 1) & j2) != 0 ? j2 | j : j2;
    }
}
