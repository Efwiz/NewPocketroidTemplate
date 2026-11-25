package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.util.ByteBufferUtil;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttBinaryData {
    public static final int EMPTY_LENGTH = 2;
    public static final int MAX_LENGTH = 65535;

    public static byte[] decode(ByteBuf byteBuf) {
        int unsignedShort;
        if (byteBuf.readableBytes() < 2 || byteBuf.readableBytes() < (unsignedShort = byteBuf.readUnsignedShort())) {
            return null;
        }
        byte[] bArr = new byte[unsignedShort];
        byteBuf.readBytes(bArr);
        return bArr;
    }

    public static ByteBuffer decode(ByteBuf byteBuf, boolean z) {
        int unsignedShort;
        if (byteBuf.readableBytes() < 2 || byteBuf.readableBytes() < (unsignedShort = byteBuf.readUnsignedShort())) {
            return null;
        }
        ByteBuffer byteBufferAllocate = ByteBufferUtil.allocate(unsignedShort, z);
        byteBuf.readBytes(byteBufferAllocate);
        byteBufferAllocate.position(0);
        return byteBufferAllocate;
    }

    public static void encode(byte[] bArr, ByteBuf byteBuf) {
        byteBuf.writeShort(bArr.length);
        byteBuf.writeBytes(bArr);
    }

    public static void encode(ByteBuffer byteBuffer, ByteBuf byteBuf) {
        byteBuf.writeShort(byteBuffer.remaining());
        byteBuf.writeBytes(byteBuffer.duplicate());
    }

    public static void encodeEmpty(ByteBuf byteBuf) {
        byteBuf.writeShort(0);
    }

    public static boolean isInRange(byte[] bArr) {
        return bArr.length <= 65535;
    }

    public static boolean isInRange(ByteBuffer byteBuffer) {
        return byteBuffer.remaining() <= 65535;
    }

    public static int encodedLength(byte[] bArr) {
        return bArr.length + 2;
    }

    public static int encodedLength(ByteBuffer byteBuffer) {
        return byteBuffer.remaining() + 2;
    }

    private MqttBinaryData() {
    }
}
