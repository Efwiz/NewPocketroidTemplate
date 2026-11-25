package com.hivemq.client.internal.util;

import java.nio.ByteBuffer;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public final class ByteBufferUtil {
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    public static ByteBuffer allocate(int i, boolean z) {
        return z ? ByteBuffer.allocateDirect(i) : ByteBuffer.allocate(i);
    }

    public static ByteBuffer wrap(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return ByteBuffer.wrap(bArr);
    }

    public static ByteBuffer slice(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return null;
        }
        return byteBuffer.slice();
    }

    public static Optional<ByteBuffer> optionalReadOnly(ByteBuffer byteBuffer) {
        return byteBuffer == null ? Optional.empty() : Optional.of(byteBuffer.asReadOnlyBuffer());
    }

    public static byte[] getBytes(ByteBuffer byteBuffer) {
        if (byteBuffer.hasArray()) {
            byte[] bArrArray = byteBuffer.array();
            if (byteBuffer.arrayOffset() == 0 && bArrArray.length == byteBuffer.remaining()) {
                return bArrArray;
            }
        }
        return copyBytes(byteBuffer);
    }

    public static byte[] copyBytes(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] bArr = new byte[byteBuffer.remaining()];
        byteBuffer.duplicate().get(bArr);
        return bArr;
    }

    private ByteBufferUtil() {
    }
}
