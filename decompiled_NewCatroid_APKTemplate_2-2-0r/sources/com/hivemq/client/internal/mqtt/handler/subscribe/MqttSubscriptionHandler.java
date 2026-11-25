package com.hivemq.client.internal.mqtt.handler.subscribe;

import com.gaurav.avnc.vnc.XKeySym;
import com.hivemq.client.internal.logging.InternalLogger;
import com.hivemq.client.internal.logging.InternalLoggerFactory;
import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttClientConnectionConfig;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.handler.MqttSessionAwareHandler;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectUtil;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttGlobalIncomingPublishFlow;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlows;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlow;
import com.hivemq.client.internal.mqtt.ioc.ClientScope;
import com.hivemq.client.internal.mqtt.message.MqttCommonReasonCode;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttStatefulSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.suback.MqttSubAck;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttStatefulUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback.MqttUnsubAck;
import com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback.mqtt3.Mqtt3UnsubAckView;
import com.hivemq.client.internal.util.Ranges;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.internal.util.collections.IntIndex;
import com.hivemq.client.internal.util.collections.NodeList;
import com.hivemq.client.mqtt.MqttClientState;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5SubAckException;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5UnsubAckException;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAckReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAckReasonCode;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.ToIntFunction;
import javax.inject.Inject;

/* JADX INFO: loaded from: classes10.dex */
@ClientScope
public class MqttSubscriptionHandler extends MqttSessionAwareHandler implements Runnable {
    public static final int MAX_SUB_PENDING = 10;
    public static final String NAME = "subscription";
    private final MqttClientConfig clientConfig;
    private MqttSubOrUnsubWithFlow currentPending;
    private final MqttIncomingPublishFlows incomingPublishFlows;
    private MqttSubOrUnsubWithFlow sendPending;
    private boolean subscriptionIdentifiersAvailable;
    private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(MqttSubscriptionHandler.class);
    private static final IntIndex.Spec<MqttSubOrUnsubWithFlow> INDEX_SPEC = new IntIndex.Spec<>(new ToIntFunction() { // from class: com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler$$ExternalSyntheticLambda3
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(Object obj) {
            return ((MqttSubOrUnsubWithFlow) obj).packetIdentifier;
        }
    }, 4);
    private final NodeList<MqttSubOrUnsubWithFlow> pending = new NodeList<>();
    private int nextSubscriptionIdentifier = 1;
    private final IntIndex<MqttSubOrUnsubWithFlow> pendingIndex = new IntIndex<>(INDEX_SPEC);
    private final Ranges packetIdentifiers = new Ranges(XKeySym.XK_braille_dot_6, 65535);

    @Inject
    MqttSubscriptionHandler(MqttClientConfig mqttClientConfig, MqttIncomingPublishFlows mqttIncomingPublishFlows) {
        this.clientConfig = mqttClientConfig;
        this.incomingPublishFlows = mqttIncomingPublishFlows;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.MqttSessionAwareHandler
    public void onSessionStartOrResume(MqttClientConnectionConfig mqttClientConnectionConfig, EventLoop eventLoop) {
        this.subscriptionIdentifiersAvailable = mqttClientConnectionConfig.areSubscriptionIdentifiersAvailable();
        if (!this.hasSession) {
            this.incomingPublishFlows.getSubscriptions().forEach(new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.m2715x968f8c96((Integer) obj, (List) obj2);
                }
            });
        }
        this.pendingIndex.clear();
        MqttSubOrUnsubWithFlow mqttSubOrUnsubWithFlow = (MqttSubOrUnsubWithFlow) this.pending.getFirst();
        this.sendPending = mqttSubOrUnsubWithFlow;
        if (mqttSubOrUnsubWithFlow != null) {
            eventLoop.execute(this);
        }
        super.onSessionStartOrResume(mqttClientConnectionConfig, eventLoop);
    }

    /* JADX INFO: renamed from: lambda$onSessionStartOrResume$1$com-hivemq-client-internal-mqtt-handler-subscribe-MqttSubscriptionHandler, reason: not valid java name */
    /* synthetic */ void m2715x968f8c96(Integer num, List list) {
        this.pending.addFirst(new MqttSubscribeWithFlow(new MqttSubscribe(ImmutableList.copyOf((Collection) list), MqttUserPropertiesImpl.NO_USER_PROPERTIES), num.intValue(), null));
    }

    public void subscribe(final MqttSubscribe mqttSubscribe, final MqttSubscriptionFlow<MqttSubAck> mqttSubscriptionFlow) {
        mqttSubscriptionFlow.getEventLoop().execute(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2716xd9f0eb0c(mqttSubscriptionFlow, mqttSubscribe);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$subscribe$2$com-hivemq-client-internal-mqtt-handler-subscribe-MqttSubscriptionHandler, reason: not valid java name */
    /* synthetic */ void m2716xd9f0eb0c(MqttSubscriptionFlow mqttSubscriptionFlow, MqttSubscribe mqttSubscribe) {
        if (mqttSubscriptionFlow.init()) {
            int i = this.nextSubscriptionIdentifier;
            this.nextSubscriptionIdentifier = i + 1;
            this.incomingPublishFlows.subscribe(mqttSubscribe, i, mqttSubscriptionFlow instanceof MqttSubscribedPublishFlow ? (MqttSubscribedPublishFlow) mqttSubscriptionFlow : null);
            queue(new MqttSubscribeWithFlow(mqttSubscribe, i, mqttSubscriptionFlow));
        }
    }

    public void unsubscribe(final MqttUnsubscribe mqttUnsubscribe, final MqttSubOrUnsubAckFlow<MqttUnsubAck> mqttSubOrUnsubAckFlow) {
        mqttSubOrUnsubAckFlow.getEventLoop().execute(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2718xc9d96c14(mqttSubOrUnsubAckFlow, mqttUnsubscribe);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$unsubscribe$3$com-hivemq-client-internal-mqtt-handler-subscribe-MqttSubscriptionHandler, reason: not valid java name */
    /* synthetic */ void m2718xc9d96c14(MqttSubOrUnsubAckFlow mqttSubOrUnsubAckFlow, MqttUnsubscribe mqttUnsubscribe) {
        if (mqttSubOrUnsubAckFlow.init()) {
            queue(new MqttUnsubscribeWithFlow(mqttUnsubscribe, mqttSubOrUnsubAckFlow));
        }
    }

    public void subscribeGlobal(final MqttGlobalIncomingPublishFlow mqttGlobalIncomingPublishFlow) {
        mqttGlobalIncomingPublishFlow.getEventLoop().execute(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m2717xbdbcb611(mqttGlobalIncomingPublishFlow);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$subscribeGlobal$4$com-hivemq-client-internal-mqtt-handler-subscribe-MqttSubscriptionHandler, reason: not valid java name */
    /* synthetic */ void m2717xbdbcb611(MqttGlobalIncomingPublishFlow mqttGlobalIncomingPublishFlow) {
        if (mqttGlobalIncomingPublishFlow.init()) {
            this.incomingPublishFlows.subscribeGlobal(mqttGlobalIncomingPublishFlow);
        }
    }

    private void queue(MqttSubOrUnsubWithFlow mqttSubOrUnsubWithFlow) {
        this.pending.add(mqttSubOrUnsubWithFlow);
        if (this.sendPending == null) {
            this.sendPending = mqttSubOrUnsubWithFlow;
            run();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext == null) {
            return;
        }
        MqttSubOrUnsubWithFlow next = this.sendPending;
        int i = 0;
        while (next != null && this.pendingIndex.size() < 10) {
            if (next.packetIdentifier == 0) {
                int id2 = this.packetIdentifiers.getId();
                if (id2 == -1) {
                    LOGGER.error("No Packet Identifier available for (UN)SUBSCRIBE. This must not happen and is a bug.");
                    return;
                }
                next.packetIdentifier = id2;
            }
            this.pendingIndex.put(next);
            if (this.sendPending instanceof MqttSubscribeWithFlow) {
                writeSubscribe(channelHandlerContext, (MqttSubscribeWithFlow) next);
            } else {
                writeUnsubscribe(channelHandlerContext, (MqttUnsubscribeWithFlow) next);
            }
            i++;
            next = next.getNext();
            this.sendPending = next;
        }
        if (i > 0) {
            channelHandlerContext.flush();
        }
    }

    private void writeSubscribe(ChannelHandlerContext channelHandlerContext, MqttSubscribeWithFlow mqttSubscribeWithFlow) {
        MqttStatefulSubscribe mqttStatefulSubscribeCreateStateful = mqttSubscribeWithFlow.subscribe.createStateful(mqttSubscribeWithFlow.packetIdentifier, this.subscriptionIdentifiersAvailable ? mqttSubscribeWithFlow.subscriptionIdentifier : -1);
        this.currentPending = mqttSubscribeWithFlow;
        channelHandlerContext.write(mqttStatefulSubscribeCreateStateful, channelHandlerContext.voidPromise());
        this.currentPending = null;
    }

    private void writeUnsubscribe(ChannelHandlerContext channelHandlerContext, MqttUnsubscribeWithFlow mqttUnsubscribeWithFlow) {
        MqttStatefulUnsubscribe mqttStatefulUnsubscribeCreateStateful = mqttUnsubscribeWithFlow.unsubscribe.createStateful(mqttUnsubscribeWithFlow.packetIdentifier);
        this.currentPending = mqttUnsubscribeWithFlow;
        channelHandlerContext.write(mqttStatefulUnsubscribeCreateStateful, channelHandlerContext.voidPromise());
        this.currentPending = null;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
        if (obj instanceof MqttSubAck) {
            readSubAck(channelHandlerContext, (MqttSubAck) obj);
        } else if (obj instanceof MqttUnsubAck) {
            readUnsubAck(channelHandlerContext, (MqttUnsubAck) obj);
        } else {
            channelHandlerContext.fireChannelRead(obj);
        }
    }

    private void readSubAck(ChannelHandlerContext channelHandlerContext, MqttSubAck mqttSubAck) {
        String str;
        MqttSubOrUnsubWithFlow mqttSubOrUnsubWithFlowRemove = this.pendingIndex.remove(mqttSubAck.getPacketIdentifier());
        if (mqttSubOrUnsubWithFlowRemove == null) {
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "Unknown packet identifier for SUBACK");
            return;
        }
        if (!(mqttSubOrUnsubWithFlowRemove instanceof MqttSubscribeWithFlow)) {
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "SUBACK received for an UNSUBSCRIBE");
            return;
        }
        MqttSubscribeWithFlow mqttSubscribeWithFlow = (MqttSubscribeWithFlow) mqttSubOrUnsubWithFlowRemove;
        MqttSubscriptionFlow<MqttSubAck> flow = mqttSubscribeWithFlow.getFlow();
        ImmutableList<Mqtt5SubAckReasonCode> reasonCodes = mqttSubAck.getReasonCodes();
        boolean z = mqttSubscribeWithFlow.subscribe.getSubscriptions().size() != reasonCodes.size();
        boolean zAllErrors = MqttCommonReasonCode.allErrors(mqttSubAck.getReasonCodes());
        this.incomingPublishFlows.subAck(mqttSubscribeWithFlow.subscribe, mqttSubscribeWithFlow.subscriptionIdentifier, reasonCodes);
        if (flow != null) {
            if (!z && !zAllErrors) {
                if (!flow.isCancelled()) {
                    flow.onSuccess(mqttSubAck);
                } else {
                    LOGGER.warn("Subscribe was successful but the SubAck flow has been cancelled");
                }
            } else {
                if (z) {
                    str = "Count of Reason Codes in SUBACK does not match count of subscriptions in SUBSCRIBE";
                } else {
                    str = "SUBACK contains only Error Codes";
                }
                if (!flow.isCancelled()) {
                    flow.onError(new Mqtt5SubAckException(mqttSubAck, str));
                } else {
                    LOGGER.warn(str.concat(" but the SubAck flow has been cancelled"));
                }
            }
        }
        completePending(mqttSubscribeWithFlow);
    }

    private void readUnsubAck(ChannelHandlerContext channelHandlerContext, MqttUnsubAck mqttUnsubAck) {
        String str;
        MqttSubOrUnsubWithFlow mqttSubOrUnsubWithFlowRemove = this.pendingIndex.remove(mqttUnsubAck.getPacketIdentifier());
        if (mqttSubOrUnsubWithFlowRemove == null) {
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "Unknown packet identifier for UNSUBACK");
            return;
        }
        if (!(mqttSubOrUnsubWithFlowRemove instanceof MqttUnsubscribeWithFlow)) {
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "UNSUBACK received for a SUBSCRIBE");
            return;
        }
        MqttUnsubscribeWithFlow mqttUnsubscribeWithFlow = (MqttUnsubscribeWithFlow) mqttSubOrUnsubWithFlowRemove;
        MqttSubOrUnsubAckFlow<MqttUnsubAck> flow = mqttUnsubscribeWithFlow.getFlow();
        ImmutableList<Mqtt5UnsubAckReasonCode> reasonCodes = mqttUnsubAck.getReasonCodes();
        boolean z = mqttUnsubscribeWithFlow.unsubscribe.getTopicFilters().size() != reasonCodes.size();
        boolean zAllErrors = MqttCommonReasonCode.allErrors(mqttUnsubAck.getReasonCodes());
        if (reasonCodes == Mqtt3UnsubAckView.REASON_CODES_ALL_SUCCESS || (!z && !zAllErrors)) {
            this.incomingPublishFlows.unsubscribe(mqttUnsubscribeWithFlow.unsubscribe, reasonCodes);
            if (!flow.isCancelled()) {
                flow.onSuccess(mqttUnsubAck);
            } else {
                LOGGER.warn("Unsubscribe was successful but the UnsubAck flow has been cancelled");
            }
        } else {
            if (z) {
                str = "Count of Reason Codes in UNSUBACK does not match count of Topic Filters in UNSUBSCRIBE";
            } else {
                str = "UNSUBACK contains only Error Codes";
            }
            if (!flow.isCancelled()) {
                flow.onError(new Mqtt5UnsubAckException(mqttUnsubAck, str));
            } else {
                LOGGER.warn(str.concat(" but the UnsubAck flow has been cancelled"));
            }
        }
        completePending(mqttUnsubscribeWithFlow);
    }

    private void completePending(MqttSubOrUnsubWithFlow mqttSubOrUnsubWithFlow) {
        this.pending.remove(mqttSubOrUnsubWithFlow);
        this.packetIdentifiers.returnId(mqttSubOrUnsubWithFlow.packetIdentifier);
        run();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) {
        MqttSubOrUnsubWithFlow mqttSubOrUnsubWithFlow;
        if (!(th instanceof IOException) && (mqttSubOrUnsubWithFlow = this.currentPending) != null) {
            this.pending.remove(mqttSubOrUnsubWithFlow);
            this.packetIdentifiers.returnId(this.currentPending.packetIdentifier);
            this.pendingIndex.remove(this.currentPending.packetIdentifier);
            MqttSubscriptionFlow<?> flow = this.currentPending.getFlow();
            if (flow != null) {
                flow.onError(th);
            }
            MqttSubOrUnsubWithFlow mqttSubOrUnsubWithFlow2 = this.currentPending;
            if (mqttSubOrUnsubWithFlow2 instanceof MqttSubscribeWithFlow) {
                MqttSubscribeWithFlow mqttSubscribeWithFlow = (MqttSubscribeWithFlow) mqttSubOrUnsubWithFlow2;
                this.incomingPublishFlows.subAck(mqttSubscribeWithFlow.subscribe, mqttSubscribeWithFlow.subscriptionIdentifier, ImmutableList.of(Mqtt5SubAckReasonCode.UNSPECIFIED_ERROR));
            }
            this.currentPending = null;
            return;
        }
        channelHandlerContext.fireExceptionCaught(th);
    }

    @Override // com.hivemq.client.internal.mqtt.handler.MqttSessionAwareHandler
    public void onSessionEnd(Throwable th) {
        super.onSessionEnd(th);
        this.pendingIndex.clear();
        this.sendPending = null;
        NodeList.Node first = this.pending.getFirst();
        while (true) {
            MqttSubOrUnsubWithFlow mqttSubOrUnsubWithFlow = (MqttSubOrUnsubWithFlow) first;
            if (mqttSubOrUnsubWithFlow == null || mqttSubOrUnsubWithFlow.packetIdentifier == 0) {
                break;
            }
            this.packetIdentifiers.returnId(mqttSubOrUnsubWithFlow.packetIdentifier);
            mqttSubOrUnsubWithFlow.packetIdentifier = 0;
            first = mqttSubOrUnsubWithFlow.getNext();
        }
        if (this.clientConfig.isResubscribeIfSessionExpired() && this.clientConfig.getState() != MqttClientState.DISCONNECTED) {
            return;
        }
        this.incomingPublishFlows.clear(th);
        NodeList.Node first2 = this.pending.getFirst();
        while (true) {
            MqttSubOrUnsubWithFlow mqttSubOrUnsubWithFlow2 = (MqttSubOrUnsubWithFlow) first2;
            if (mqttSubOrUnsubWithFlow2 != null) {
                MqttSubscriptionFlow<?> flow = mqttSubOrUnsubWithFlow2.getFlow();
                if (flow != null) {
                    flow.onError(th);
                }
                first2 = mqttSubOrUnsubWithFlow2.getNext();
            } else {
                this.pending.clear();
                this.nextSubscriptionIdentifier = 1;
                return;
            }
        }
    }
}
