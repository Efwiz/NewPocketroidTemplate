package com.hivemq.client.internal.mqtt.codec.encoder;

import com.hivemq.client.internal.mqtt.MqttClientConnectionConfig;
import com.hivemq.client.internal.mqtt.ioc.ConnectionScope;
import com.hivemq.client.internal.mqtt.message.MqttMessage;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import javax.inject.Inject;

/* JADX INFO: loaded from: classes10.dex */
@ConnectionScope
public class MqttEncoder extends ChannelDuplexHandler {
    public static final String NAME = "encoder";
    private final MqttMessageEncoders encoders;
    private boolean inRead = false;
    private boolean pendingFlush = false;
    private final MqttEncoderContext context = new MqttEncoderContext(ByteBufAllocator.DEFAULT);

    @Override // io.netty.channel.ChannelHandlerAdapter
    public boolean isSharable() {
        return false;
    }

    @Inject
    MqttEncoder(MqttMessageEncoders mqttMessageEncoders) {
        this.encoders = mqttMessageEncoders;
    }

    public void onConnected(MqttClientConnectionConfig mqttClientConnectionConfig) {
        this.context.setMaximumPacketSize(mqttClientConnectionConfig.getSendMaximumPacketSize());
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) {
        if (obj instanceof MqttMessage) {
            MqttMessage mqttMessage = (MqttMessage) obj;
            MqttMessageEncoder<?> mqttMessageEncoder = this.encoders.get(mqttMessage.getType().getCode());
            if (mqttMessageEncoder == null) {
                throw new UnsupportedOperationException();
            }
            channelHandlerContext.write(mqttMessageEncoder.castAndEncode(mqttMessage, this.context), channelPromise);
            return;
        }
        channelHandlerContext.write(obj, channelPromise);
    }

    @Override // io.netty.channel.ChannelDuplexHandler, io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) {
        if (this.inRead) {
            this.pendingFlush = true;
        } else {
            channelHandlerContext.flush();
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
        this.inRead = true;
        channelHandlerContext.fireChannelRead(obj);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.fireChannelReadComplete();
        this.inRead = false;
        if (this.pendingFlush) {
            this.pendingFlush = false;
            channelHandlerContext.flush();
        }
    }
}
