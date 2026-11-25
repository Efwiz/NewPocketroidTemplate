package com.hivemq.client.internal.mqtt.datatypes;

import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttVariableByteInteger {
    private static final int CONTINUATION_BIT_MASK = 128;
    public static final int FOUR_BYTES_MAX_VALUE = 268435455;
    public static final int MAXIMUM_PACKET_SIZE_LIMIT = 268435460;
    private static final int MAX_SHIFT = 21;
    public static final int NOT_ENOUGH_BYTES = -1;
    public static final int NOT_MINIMUM_BYTES = -3;
    private static final int ONE_BYTE_MAX_VALUE = 127;
    private static final int THREE_BYTES_MAX_VALUE = 2097151;
    public static final int TOO_LARGE = -2;
    private static final int TWO_BYTES_MAX_VALUE = 16383;
    private static final byte VALUE_BITS = 7;
    private static final int VALUE_MASK = 127;

    public static int encodedLength(int i) {
        if (i <= 127) {
            return 1;
        }
        if (i > 16383) {
            return i > THREE_BYTES_MAX_VALUE ? 4 : 3;
        }
        return 2;
    }

    public static boolean isInRange(int i) {
        return i >= 0 && i <= 268435455;
    }

    public static int decode(ByteBuf byteBuf) {
        byte b = 0;
        int i = 0;
        while (b <= 21) {
            if (!byteBuf.isReadable()) {
                return -1;
            }
            byte b2 = byteBuf.readByte();
            i += (b2 & 127) << b;
            b = (byte) (b + 7);
            if ((b2 & 128) == 0) {
                if (b <= 7 || b2 != 0) {
                    return i;
                }
                return -3;
            }
        }
        return -2;
    }

    public static void encode(int i, ByteBuf byteBuf) {
        do {
            int i2 = i & 127;
            i >>>= 7;
            if (i > 0) {
                i2 |= 128;
            }
            byteBuf.writeByte(i2);
        } while (i > 0);
    }

    private MqttVariableByteInteger() {
    }
}
