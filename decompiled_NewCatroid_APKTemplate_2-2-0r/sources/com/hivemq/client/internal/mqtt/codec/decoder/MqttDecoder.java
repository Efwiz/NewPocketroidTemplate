package com.hivemq.client.internal.mqtt.codec.decoder;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectUtil;
import com.hivemq.client.internal.mqtt.ioc.ConnectionScope;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictions;
import com.hivemq.client.mqtt.exceptions.MqttDecodeException;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import javax.inject.Inject;

/* JADX INFO: loaded from: classes10.dex */
@ConnectionScope
public class MqttDecoder extends ByteToMessageDecoder {
    private static final int MIN_FIXED_HEADER_LENGTH = 2;
    public static final String NAME = "decoder";
    private final MqttDecoderContext context;
    private final MqttMessageDecoders decoders;

    @Override // io.netty.channel.ChannelHandlerAdapter
    public boolean isSharable() {
        return false;
    }

    @Inject
    MqttDecoder(MqttMessageDecoders mqttMessageDecoders, MqttClientConfig mqttClientConfig, MqttConnect mqttConnect) {
        this.decoders = mqttMessageDecoders;
        MqttConnectRestrictions restrictions = mqttConnect.getRestrictions();
        this.context = new MqttDecoderContext(restrictions.getMaximumPacketSize(), restrictions.getTopicAliasMaximum(), restrictions.isRequestProblemInformation(), restrictions.isRequestResponseInformation(), mqttClientConfig.getAdvancedConfig().isValidatePayloadFormat(), false, false, false);
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        if (byteBuf.readableBytes() < 2) {
            return;
        }
        int i = byteBuf.readerIndex();
        short unsignedByte = byteBuf.readUnsignedByte();
        int i2 = unsignedByte >> 4;
        int i3 = unsignedByte & 15;
        int iDecode = MqttVariableByteInteger.decode(byteBuf);
        try {
            if (iDecode < 0) {
                if (iDecode == -1) {
                    byteBuf.readerIndex(i);
                    return;
                }
                throw new MqttDecoderException("malformed remaining length");
            }
            int i4 = byteBuf.readerIndex();
            if ((i4 - i) + iDecode > this.context.getMaximumPacketSize()) {
                throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PACKET_TOO_LARGE, "incoming packet exceeded maximum packet size");
            }
            int iWriterIndex = byteBuf.writerIndex();
            int i5 = i4 + iDecode;
            if (iWriterIndex < i5) {
                byteBuf.readerIndex(i);
                return;
            }
            MqttMessageDecoder mqttMessageDecoder = this.decoders.get(i2);
            if (mqttMessageDecoder == null) {
                throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "must not receive this packet type");
            }
            byteBuf.writerIndex(i5);
            list.add(mqttMessageDecoder.decode(i3, byteBuf, this.context));
            byteBuf.writerIndex(iWriterIndex);
        } catch (MqttDecoderException e) {
            byteBuf.clear();
            Object objFromCode = Mqtt5MessageType.fromCode(i2);
            StringBuilder sb = new StringBuilder("Exception while decoding ");
            if (objFromCode == null) {
                objFromCode = GrsBaseInfo.CountryCodeSource.UNKNOWN;
            }
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), e.getReasonCode(), new MqttDecodeException(sb.append(objFromCode).append(": ").append(e.getMessage()).toString()));
        }
    }
}
