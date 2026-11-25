package com.hivemq.client.internal.mqtt.codec.encoder;

import io.netty.buffer.ByteBufAllocator;

/* JADX INFO: loaded from: classes10.dex */
public class MqttEncoderContext {
    private final ByteBufAllocator allocator;
    private int maximumPacketSize = 268435460;

    MqttEncoderContext(ByteBufAllocator byteBufAllocator) {
        this.allocator = byteBufAllocator;
    }

    public ByteBufAllocator getAllocator() {
        return this.allocator;
    }

    public int getMaximumPacketSize() {
        return this.maximumPacketSize;
    }

    void setMaximumPacketSize(int i) {
        this.maximumPacketSize = i;
    }
}
