package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.logging.InternalLogger;
import com.hivemq.client.internal.logging.InternalLoggerFactory;
import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttClientConnectionConfig;
import com.hivemq.client.internal.mqtt.advanced.interceptor.MqttClientInterceptors;
import com.hivemq.client.internal.mqtt.handler.MqttSessionAwareHandler;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectUtil;
import com.hivemq.client.internal.mqtt.ioc.ClientScope;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttStatefulPublish;
import com.hivemq.client.internal.mqtt.message.publish.puback.MqttPubAck;
import com.hivemq.client.internal.mqtt.message.publish.puback.MqttPubAckBuilder;
import com.hivemq.client.internal.mqtt.message.publish.pubcomp.MqttPubComp;
import com.hivemq.client.internal.mqtt.message.publish.pubcomp.MqttPubCompBuilder;
import com.hivemq.client.internal.mqtt.message.publish.pubrec.MqttPubRec;
import com.hivemq.client.internal.mqtt.message.publish.pubrec.MqttPubRecBuilder;
import com.hivemq.client.internal.mqtt.message.publish.pubrel.MqttPubRel;
import com.hivemq.client.internal.util.collections.IntIndex;
import com.hivemq.client.mqtt.MqttVersion;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos1.Mqtt5IncomingQos1Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2.Mqtt5IncomingQos2Interceptor;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecReasonCode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import java.util.function.ToIntFunction;
import javax.inject.Inject;

/* JADX INFO: loaded from: classes10.dex */
@ClientScope
public class MqttIncomingQosHandler extends MqttSessionAwareHandler {
    public static final String NAME = "qos.incoming";
    private final MqttClientConfig clientConfig;
    private long connectionIndex;
    final MqttIncomingPublishService incomingPublishService;
    private final IntIndex<Object> messages = new IntIndex<>(INDEX_SPEC);
    private int receiveMaximum;
    private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(MqttIncomingQosHandler.class);
    private static final IntIndex.Spec<Object> INDEX_SPEC = new IntIndex.Spec<>(new ToIntFunction() { // from class: com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingQosHandler$$ExternalSyntheticLambda0
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(Object obj) {
            return MqttIncomingQosHandler.lambda$static$0(obj);
        }
    });

    static /* synthetic */ int lambda$static$0(Object obj) {
        if (obj instanceof MqttStatefulPublishWithFlows) {
            return ((MqttStatefulPublishWithFlows) obj).publish.getPacketIdentifier();
        }
        return ((MqttPubRec) obj).getPacketIdentifier();
    }

    @Inject
    MqttIncomingQosHandler(MqttClientConfig mqttClientConfig, MqttIncomingPublishFlows mqttIncomingPublishFlows) {
        this.clientConfig = mqttClientConfig;
        this.incomingPublishService = new MqttIncomingPublishService(this, mqttIncomingPublishFlows);
    }

    @Override // com.hivemq.client.internal.mqtt.handler.MqttSessionAwareHandler
    public void onSessionStartOrResume(MqttClientConnectionConfig mqttClientConnectionConfig, EventLoop eventLoop) {
        this.receiveMaximum = mqttClientConnectionConfig.getReceiveMaximum();
        this.connectionIndex++;
        super.onSessionStartOrResume(mqttClientConnectionConfig, eventLoop);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
        if (obj instanceof MqttStatefulPublish) {
            readPublish(channelHandlerContext, (MqttStatefulPublish) obj);
        } else if (obj instanceof MqttPubRel) {
            readPubRel(channelHandlerContext, (MqttPubRel) obj);
        } else {
            channelHandlerContext.fireChannelRead(obj);
        }
    }

    /* JADX INFO: renamed from: com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingQosHandler$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hivemq$client$mqtt$datatypes$MqttQos;

        static {
            int[] iArr = new int[MqttQos.values().length];
            $SwitchMap$com$hivemq$client$mqtt$datatypes$MqttQos = iArr;
            try {
                iArr[MqttQos.AT_MOST_ONCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$datatypes$MqttQos[MqttQos.AT_LEAST_ONCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$datatypes$MqttQos[MqttQos.EXACTLY_ONCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readPublish(ChannelHandlerContext channelHandlerContext, MqttStatefulPublish mqttStatefulPublish) {
        int i = AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$datatypes$MqttQos[((MqttPublish) mqttStatefulPublish.stateless()).getQos().ordinal()];
        if (i == 1) {
            readPublishQos0(mqttStatefulPublish);
        } else if (i == 2) {
            readPublishQos1(channelHandlerContext, mqttStatefulPublish);
        } else {
            if (i != 3) {
                return;
            }
            readPublishQos2(channelHandlerContext, mqttStatefulPublish);
        }
    }

    private void readPublishQos0(MqttStatefulPublish mqttStatefulPublish) {
        this.incomingPublishService.onPublishQos0(new MqttStatefulPublishWithFlows(mqttStatefulPublish), this.receiveMaximum);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readPublishQos1(ChannelHandlerContext channelHandlerContext, MqttStatefulPublish mqttStatefulPublish) {
        MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows = new MqttStatefulPublishWithFlows(mqttStatefulPublish);
        mqttStatefulPublishWithFlows.connectionIndex = this.connectionIndex;
        Object objPutIfAbsent = this.messages.putIfAbsent(mqttStatefulPublishWithFlows);
        if (objPutIfAbsent == null) {
            if (readNewPublishQos1Or2(channelHandlerContext, mqttStatefulPublishWithFlows)) {
                return;
            }
            this.messages.remove(mqttStatefulPublish.getPacketIdentifier());
            return;
        }
        if (objPutIfAbsent instanceof MqttStatefulPublishWithFlows) {
            MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows2 = (MqttStatefulPublishWithFlows) objPutIfAbsent;
            if (((MqttPublish) mqttStatefulPublishWithFlows2.publish.stateless()).getQos() != MqttQos.AT_LEAST_ONCE) {
                LOGGER.error("QoS 1 PUBLISH ({}) must not carry the same packet identifier as a QoS 2 PUBLISH ({})", mqttStatefulPublish, mqttStatefulPublishWithFlows2.publish);
                MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "QoS 1 PUBLISH must not carry the same packet identifier as a QoS 2 PUBLISH");
                return;
            }
            if (mqttStatefulPublishWithFlows2.connectionIndex == this.connectionIndex) {
                if (this.clientConfig.getMqttVersion() == MqttVersion.MQTT_5_0) {
                    LOGGER.error("QoS 1 PUBLISH ({}) must not be resent ({}) during the same connection", mqttStatefulPublishWithFlows2.publish, mqttStatefulPublish);
                    MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "QoS 1 PUBLISH must not be resent during the same connection");
                    return;
                } else {
                    checkDupFlagSet(channelHandlerContext, mqttStatefulPublish);
                    return;
                }
            }
            this.messages.put(mqttStatefulPublishWithFlows);
            if (readNewPublishQos1Or2(channelHandlerContext, mqttStatefulPublishWithFlows)) {
                return;
            }
            this.messages.put(objPutIfAbsent);
            return;
        }
        LOGGER.error("QoS 1 PUBLISH ({}) must not carry the same packet identifier as a QoS 2 PUBLISH ({})", mqttStatefulPublish, objPutIfAbsent);
        MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "QoS 1 PUBLISH must not carry the same packet identifier as a QoS 2 PUBLISH");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readPublishQos2(ChannelHandlerContext channelHandlerContext, MqttStatefulPublish mqttStatefulPublish) {
        MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows = new MqttStatefulPublishWithFlows(mqttStatefulPublish);
        mqttStatefulPublishWithFlows.connectionIndex = this.connectionIndex;
        Object objPutIfAbsent = this.messages.putIfAbsent(mqttStatefulPublishWithFlows);
        if (objPutIfAbsent == null) {
            if (readNewPublishQos1Or2(channelHandlerContext, mqttStatefulPublishWithFlows)) {
                return;
            }
            this.messages.remove(mqttStatefulPublish.getPacketIdentifier());
            return;
        }
        if (objPutIfAbsent instanceof MqttStatefulPublishWithFlows) {
            MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows2 = (MqttStatefulPublishWithFlows) objPutIfAbsent;
            if (((MqttPublish) mqttStatefulPublishWithFlows2.publish.stateless()).getQos() != MqttQos.EXACTLY_ONCE) {
                if (mqttStatefulPublishWithFlows2.connectionIndex == this.connectionIndex) {
                    LOGGER.error("QoS 2 PUBLISH ({}) must not carry the same packet identifier as a QoS 1 PUBLISH ({})", mqttStatefulPublish, mqttStatefulPublishWithFlows2.publish);
                    MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "QoS 2 PUBLISH must not carry the same packet identifier as a QoS 1 PUBLISH");
                    return;
                } else {
                    this.messages.put(mqttStatefulPublishWithFlows);
                    if (readNewPublishQos1Or2(channelHandlerContext, mqttStatefulPublishWithFlows)) {
                        return;
                    }
                    this.messages.put(objPutIfAbsent);
                    return;
                }
            }
            long j = mqttStatefulPublishWithFlows2.connectionIndex;
            long j2 = this.connectionIndex;
            if (j == j2) {
                if (this.clientConfig.getMqttVersion() == MqttVersion.MQTT_5_0) {
                    LOGGER.error("QoS 2 PUBLISH ({}) must not be resent ({}) during the same connection", mqttStatefulPublishWithFlows2.publish, mqttStatefulPublish);
                    MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "QoS 2 PUBLISH must not be resent during the same connection");
                    return;
                } else {
                    checkDupFlagSet(channelHandlerContext, mqttStatefulPublish);
                    return;
                }
            }
            mqttStatefulPublishWithFlows2.connectionIndex = j2;
            checkDupFlagSet(channelHandlerContext, mqttStatefulPublish);
            return;
        }
        if (checkDupFlagSet(channelHandlerContext, mqttStatefulPublish)) {
            writePubRec(channelHandlerContext, (MqttPubRec) objPutIfAbsent);
        }
    }

    private boolean readNewPublishQos1Or2(ChannelHandlerContext channelHandlerContext, MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows) {
        if (this.incomingPublishService.onPublishQos1Or2(mqttStatefulPublishWithFlows, this.receiveMaximum)) {
            return true;
        }
        LOGGER.error("Received more QoS 1 and/or 2 PUBLISH messages ({}) than allowed by receive maximum ({})", mqttStatefulPublishWithFlows.publish, Integer.valueOf(this.receiveMaximum));
        MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.RECEIVE_MAXIMUM_EXCEEDED, "Received more QoS 1 and/or 2 PUBLISH messages than allowed by receive maximum");
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean checkDupFlagSet(ChannelHandlerContext channelHandlerContext, MqttStatefulPublish mqttStatefulPublish) {
        if (mqttStatefulPublish.isDup()) {
            return true;
        }
        LOGGER.error("DUP flag must be set for a resent PUBLISH ({})", mqttStatefulPublish);
        MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "DUP flag must be set for a resent QoS " + ((MqttPublish) mqttStatefulPublish.stateless()).getQos().getCode() + " PUBLISH");
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    void ack(MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows) {
        int i = AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$datatypes$MqttQos[((MqttPublish) mqttStatefulPublishWithFlows.publish.stateless()).getQos().ordinal()];
        if (i == 2) {
            MqttPubAck mqttPubAckBuildPubAck = buildPubAck(new MqttPubAckBuilder(mqttStatefulPublishWithFlows.publish));
            if (!ack(this.messages.remove(mqttPubAckBuildPubAck.getPacketIdentifier()), mqttStatefulPublishWithFlows) || this.ctx == null) {
                return;
            }
            writePubAck(this.ctx, mqttPubAckBuildPubAck);
            return;
        }
        if (i != 3) {
            return;
        }
        MqttPubRec mqttPubRecBuildPubRec = buildPubRec(new MqttPubRecBuilder(mqttStatefulPublishWithFlows.publish));
        if (!ack(!((Mqtt5PubRecReasonCode) mqttPubRecBuildPubRec.getReasonCode()).isError() ? this.messages.put(mqttPubRecBuildPubRec) : this.messages.remove(mqttPubRecBuildPubRec.getPacketIdentifier()), mqttStatefulPublishWithFlows) || this.ctx == null) {
            return;
        }
        writePubRec(this.ctx, mqttPubRecBuildPubRec);
    }

    private boolean ack(Object obj, MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows) {
        if (obj == mqttStatefulPublishWithFlows) {
            return mqttStatefulPublishWithFlows.connectionIndex == this.connectionIndex;
        }
        if (obj == null) {
            this.messages.remove(mqttStatefulPublishWithFlows.publish.getPacketIdentifier());
        } else {
            this.messages.put(obj);
        }
        return false;
    }

    private void writePubAck(ChannelHandlerContext channelHandlerContext, MqttPubAck mqttPubAck) {
        channelHandlerContext.writeAndFlush(mqttPubAck, channelHandlerContext.voidPromise());
    }

    private void writePubRec(ChannelHandlerContext channelHandlerContext, MqttPubRec mqttPubRec) {
        channelHandlerContext.writeAndFlush(mqttPubRec, channelHandlerContext.voidPromise());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void readPubRel(ChannelHandlerContext channelHandlerContext, MqttPubRel mqttPubRel) {
        Object objRemove = this.messages.remove(mqttPubRel.getPacketIdentifier());
        if (objRemove instanceof MqttPubRec) {
            writePubComp(channelHandlerContext, buildPubComp(new MqttPubCompBuilder(mqttPubRel)));
            return;
        }
        if (objRemove == null) {
            writePubComp(channelHandlerContext, buildPubComp(new MqttPubCompBuilder(mqttPubRel).reasonCode(Mqtt5PubCompReasonCode.PACKET_IDENTIFIER_NOT_FOUND)));
            return;
        }
        MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows = (MqttStatefulPublishWithFlows) objRemove;
        this.messages.put(objRemove);
        if (((MqttPublish) mqttStatefulPublishWithFlows.publish.stateless()).getQos() == MqttQos.EXACTLY_ONCE) {
            LOGGER.error("PUBREL ({}) must not carry the same packet identifier as an unacknowledged QoS 2 PUBLISH ({})", mqttPubRel, mqttStatefulPublishWithFlows.publish);
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "PUBREL must not carry the same packet identifier as an unacknowledged QoS 2 PUBLISH");
        } else {
            LOGGER.error("PUBREL ({}) must not carry the same packet identifier as a QoS 1 PUBLISH ({})", mqttPubRel, mqttStatefulPublishWithFlows.publish);
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "PUBREL must not carry the same packet identifier as a QoS 1 PUBLISH");
        }
    }

    private void writePubComp(ChannelHandlerContext channelHandlerContext, MqttPubComp mqttPubComp) {
        channelHandlerContext.writeAndFlush(mqttPubComp, channelHandlerContext.voidPromise());
    }

    @Override // com.hivemq.client.internal.mqtt.handler.MqttSessionAwareHandler
    public void onSessionEnd(Throwable th) {
        super.onSessionEnd(th);
        this.messages.clear();
    }

    private MqttPubAck buildPubAck(MqttPubAckBuilder mqttPubAckBuilder) {
        Mqtt5IncomingQos1Interceptor incomingQos1Interceptor;
        MqttClientInterceptors interceptors = this.clientConfig.getAdvancedConfig().getInterceptors();
        if (interceptors != null && (incomingQos1Interceptor = interceptors.getIncomingQos1Interceptor()) != null) {
            incomingQos1Interceptor.onPublish(this.clientConfig, (Mqtt5Publish) mqttPubAckBuilder.getPublish().stateless(), mqttPubAckBuilder);
        }
        return mqttPubAckBuilder.build();
    }

    private MqttPubRec buildPubRec(MqttPubRecBuilder mqttPubRecBuilder) {
        Mqtt5IncomingQos2Interceptor incomingQos2Interceptor;
        MqttClientInterceptors interceptors = this.clientConfig.getAdvancedConfig().getInterceptors();
        if (interceptors != null && (incomingQos2Interceptor = interceptors.getIncomingQos2Interceptor()) != null) {
            incomingQos2Interceptor.onPublish(this.clientConfig, (Mqtt5Publish) mqttPubRecBuilder.getPublish().stateless(), mqttPubRecBuilder);
        }
        return mqttPubRecBuilder.build();
    }

    private MqttPubComp buildPubComp(MqttPubCompBuilder mqttPubCompBuilder) {
        Mqtt5IncomingQos2Interceptor incomingQos2Interceptor;
        MqttClientInterceptors interceptors = this.clientConfig.getAdvancedConfig().getInterceptors();
        if (interceptors != null && (incomingQos2Interceptor = interceptors.getIncomingQos2Interceptor()) != null) {
            incomingQos2Interceptor.onPubRel(this.clientConfig, mqttPubCompBuilder.getPubRel(), mqttPubCompBuilder);
        }
        return mqttPubCompBuilder.build();
    }
}
