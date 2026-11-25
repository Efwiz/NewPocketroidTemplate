package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.gaurav.avnc.vnc.XKeySym;
import com.hivemq.client.internal.logging.InternalLogger;
import com.hivemq.client.internal.logging.InternalLoggerFactory;
import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttClientConnectionConfig;
import com.hivemq.client.internal.mqtt.advanced.interceptor.MqttClientInterceptors;
import com.hivemq.client.internal.mqtt.exceptions.MqttClientStateExceptions;
import com.hivemq.client.internal.mqtt.handler.MqttSessionAwareHandler;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectUtil;
import com.hivemq.client.internal.mqtt.ioc.ClientScope;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult;
import com.hivemq.client.internal.mqtt.message.publish.MqttStatefulPublish;
import com.hivemq.client.internal.mqtt.message.publish.puback.MqttPubAck;
import com.hivemq.client.internal.mqtt.message.publish.pubcomp.MqttPubComp;
import com.hivemq.client.internal.mqtt.message.publish.pubrec.MqttPubRec;
import com.hivemq.client.internal.mqtt.message.publish.pubrel.MqttPubRel;
import com.hivemq.client.internal.mqtt.message.publish.pubrel.MqttPubRelBuilder;
import com.hivemq.client.internal.netty.ContextFuture;
import com.hivemq.client.internal.netty.DefaultContextPromise;
import com.hivemq.client.internal.util.Ranges;
import com.hivemq.client.internal.util.collections.IntIndex;
import com.hivemq.client.internal.util.collections.NodeList;
import com.hivemq.client.mqtt.MqttClientState;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.exceptions.ConnectionClosedException;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos1.Mqtt5OutgoingQos1Interceptor;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2.Mqtt5OutgoingQos2Interceptor;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5PubAckException;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5PubRecException;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecReasonCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.functions.Function;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.ToIntFunction;
import javax.inject.Inject;
import org.jctools.queues.SpscUnboundedArrayQueue;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
@ClientScope
public class MqttOutgoingQosHandler extends MqttSessionAwareHandler implements FlowableSubscriber<MqttPublishWithFlow>, Runnable, ContextFuture.Listener<MqttPublishWithFlow> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MAX_CONCURRENT_PUBLISH_FLOWABLES = 64;
    public static final String NAME = "qos.outgoing";
    private static final boolean QOS_2_COMPLETE_RESULT = false;
    private final MqttClientConfig clientConfig;
    private MqttPublishWithFlow currentPending;
    private MqttPubOrRelWithFlow resendPending;
    private int sendMaximum;
    private int shrinkRequests;
    private Subscription subscription;
    private MqttTopicAliasMapping topicAliasMapping;
    private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(MqttOutgoingQosHandler.class);
    private static final IntIndex.Spec<MqttPubOrRelWithFlow> INDEX_SPEC = new IntIndex.Spec<>(new ToIntFunction() { // from class: com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttOutgoingQosHandler$$ExternalSyntheticLambda1
        @Override // java.util.function.ToIntFunction
        public final int applyAsInt(Object obj) {
            return ((MqttPubOrRelWithFlow) obj).packetIdentifier;
        }
    });
    private final SpscUnboundedArrayQueue<MqttPublishWithFlow> queue = new SpscUnboundedArrayQueue<>(32);
    private final AtomicInteger queuedCounter = new AtomicInteger();
    private final NodeList<MqttPubOrRelWithFlow> pending = new NodeList<>();
    private final Ranges packetIdentifiers = new Ranges(1, 0);
    private final IntIndex<MqttPubOrRelWithFlow> pendingIndex = new IntIndex<>(INDEX_SPEC);
    private final MqttPublishFlowables publishFlowables = new MqttPublishFlowables();

    static /* synthetic */ Publisher lambda$onSessionStartOrResume$1(Flowable flowable) throws Exception {
        return flowable;
    }

    @Inject
    MqttOutgoingQosHandler(MqttClientConfig mqttClientConfig) {
        this.clientConfig = mqttClientConfig;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.MqttSessionAwareHandler
    public void onSessionStartOrResume(MqttClientConnectionConfig mqttClientConnectionConfig, EventLoop eventLoop) {
        int i = this.sendMaximum;
        int iMin = Math.min(mqttClientConnectionConfig.getSendMaximum(), XKeySym.XK_braille_dot_5);
        this.sendMaximum = iMin;
        this.packetIdentifiers.resize(iMin);
        if (i == 0) {
            this.publishFlowables.flatMap(new Function() { // from class: com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttOutgoingQosHandler$$ExternalSyntheticLambda0
                @Override // io.reactivex.functions.Function
                public final Object apply(Object obj) {
                    return MqttOutgoingQosHandler.lambda$onSessionStartOrResume$1((Flowable) obj);
                }
            }, true, 64, Math.min(iMin, Flowable.bufferSize())).subscribe((FlowableSubscriber<? super R>) this);
            this.subscription.request(iMin);
        } else {
            int i2 = (iMin - i) - this.shrinkRequests;
            if (i2 > 0) {
                this.shrinkRequests = 0;
                this.subscription.request(i2);
            } else {
                this.shrinkRequests = -i2;
            }
        }
        this.topicAliasMapping = mqttClientConnectionConfig.getSendTopicAliasMapping();
        this.pendingIndex.clear();
        MqttPubOrRelWithFlow mqttPubOrRelWithFlow = (MqttPubOrRelWithFlow) this.pending.getFirst();
        this.resendPending = mqttPubOrRelWithFlow;
        if (mqttPubOrRelWithFlow != null || this.queuedCounter.get() > 0) {
            eventLoop.execute(this);
        }
        super.onSessionStartOrResume(mqttClientConnectionConfig, eventLoop);
    }

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override // org.reactivestreams.Subscriber
    public void onNext(MqttPublishWithFlow mqttPublishWithFlow) {
        this.queue.offer(mqttPublishWithFlow);
        if (this.queuedCounter.getAndIncrement() == 0) {
            mqttPublishWithFlow.getAckFlow().getEventLoop().execute(this);
        }
    }

    @Override // org.reactivestreams.Subscriber
    public void onComplete() {
        LOGGER.error("MqttPublishFlowables is global and must never complete. This must not happen and is a bug.");
    }

    @Override // org.reactivestreams.Subscriber
    public void onError(Throwable th) {
        LOGGER.error("MqttPublishFlowables is global and must never error. This must not happen and is a bug.", th);
    }

    void request(long j) {
        int i = this.shrinkRequests;
        if (i == 0) {
            this.subscription.request(j);
            return;
        }
        long j2 = i;
        if (j > j2) {
            this.shrinkRequests = 0;
            this.subscription.request(j - j2);
        } else {
            this.shrinkRequests = (int) (((long) i) - j);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!this.hasSession) {
            if (isRepublishIfSessionExpired()) {
                return;
            }
            clearQueued(MqttClientStateExceptions.notConnected());
            return;
        }
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext == null) {
            return;
        }
        Channel channel = channelHandlerContext.channel();
        int size = this.sendMaximum - this.pendingIndex.size();
        MqttPubOrRelWithFlow next = this.resendPending;
        int i = 0;
        int i2 = 0;
        while (next != null && i2 < size && channel.isWritable()) {
            resend(channelHandlerContext, next);
            i2++;
            next = next.getNext();
            this.resendPending = next;
        }
        while (i2 < size && channel.isWritable()) {
            MqttPublishWithFlow mqttPublishWithFlow = (MqttPublishWithFlow) this.queue.poll();
            if (mqttPublishWithFlow == null) {
                break;
            }
            writePublish(channelHandlerContext, mqttPublishWithFlow);
            i2++;
            i++;
        }
        if (i2 > 0) {
            boolean zIsWritable = channel.isWritable();
            channelHandlerContext.flush();
            if (i <= 0 || this.queuedCounter.addAndGet(-i) <= 0 || !zIsWritable) {
                return;
            }
            channel.eventLoop().execute(this);
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) {
        Channel channel = channelHandlerContext.channel();
        if (channel.isWritable()) {
            channel.eventLoop().execute(this);
        }
        channelHandlerContext.fireChannelWritabilityChanged();
    }

    private void resend(ChannelHandlerContext channelHandlerContext, MqttPubOrRelWithFlow mqttPubOrRelWithFlow) {
        this.pendingIndex.put(mqttPubOrRelWithFlow);
        if (mqttPubOrRelWithFlow instanceof MqttPublishWithFlow) {
            MqttPublishWithFlow mqttPublishWithFlow = (MqttPublishWithFlow) mqttPubOrRelWithFlow;
            writeQos1Or2Publish(channelHandlerContext, mqttPublishWithFlow.getPublish().createStateful(mqttPublishWithFlow.packetIdentifier, true, this.topicAliasMapping), mqttPublishWithFlow);
        } else {
            writePubRel(channelHandlerContext, ((MqttPubRelWithFlow) mqttPubOrRelWithFlow).getPubRel());
        }
    }

    private void writePublish(ChannelHandlerContext channelHandlerContext, MqttPublishWithFlow mqttPublishWithFlow) {
        if (mqttPublishWithFlow.getPublish().getQos() == MqttQos.AT_MOST_ONCE) {
            writeQos0Publish(channelHandlerContext, mqttPublishWithFlow);
        } else {
            writeQos1Or2Publish(channelHandlerContext, mqttPublishWithFlow);
        }
    }

    private void writeQos0Publish(ChannelHandlerContext channelHandlerContext, MqttPublishWithFlow mqttPublishWithFlow) {
        channelHandlerContext.write(mqttPublishWithFlow.getPublish().createStateful(-1, false, this.topicAliasMapping), new DefaultContextPromise(channelHandlerContext.channel(), mqttPublishWithFlow)).addListener((GenericFutureListener<? extends Future<? super Void>>) this);
    }

    @Override // io.netty.util.concurrent.GenericFutureListener
    public void operationComplete(ContextFuture<? extends MqttPublishWithFlow> contextFuture) {
        MqttPublishWithFlow context = contextFuture.getContext();
        MqttPublish publish = context.getPublish();
        MqttAckFlow ackFlow = context.getAckFlow();
        Throwable thCause = contextFuture.cause();
        if (!(thCause instanceof IOException)) {
            ackFlow.onNext(new MqttPublishResult(publish, thCause));
        } else {
            ackFlow.onNext(new MqttPublishResult(publish, new ConnectionClosedException(thCause)));
            contextFuture.channel().pipeline().fireExceptionCaught(thCause);
        }
    }

    private void writeQos1Or2Publish(ChannelHandlerContext channelHandlerContext, MqttPublishWithFlow mqttPublishWithFlow) {
        int id2 = this.packetIdentifiers.getId();
        if (id2 < 0) {
            LOGGER.error("No Packet Identifier available for QoS 1 or 2 PUBLISH. This must not happen and is a bug.");
            return;
        }
        mqttPublishWithFlow.packetIdentifier = id2;
        this.pendingIndex.put(mqttPublishWithFlow);
        this.pending.add(mqttPublishWithFlow);
        writeQos1Or2Publish(channelHandlerContext, mqttPublishWithFlow.getPublish().createStateful(id2, false, this.topicAliasMapping), mqttPublishWithFlow);
    }

    private void writeQos1Or2Publish(ChannelHandlerContext channelHandlerContext, MqttStatefulPublish mqttStatefulPublish, MqttPublishWithFlow mqttPublishWithFlow) {
        this.currentPending = mqttPublishWithFlow;
        channelHandlerContext.write(mqttStatefulPublish, channelHandlerContext.voidPromise());
        this.currentPending = null;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
        if (obj instanceof MqttPubAck) {
            readPubAck(channelHandlerContext, (MqttPubAck) obj);
            return;
        }
        if (obj instanceof MqttPubRec) {
            readPubRec(channelHandlerContext, (MqttPubRec) obj);
        } else if (obj instanceof MqttPubComp) {
            readPubComp(channelHandlerContext, (MqttPubComp) obj);
        } else {
            channelHandlerContext.fireChannelRead(obj);
        }
    }

    private void readPubAck(ChannelHandlerContext channelHandlerContext, MqttPubAck mqttPubAck) {
        MqttPubOrRelWithFlow mqttPubOrRelWithFlowRemove = this.pendingIndex.remove(mqttPubAck.getPacketIdentifier());
        if (mqttPubOrRelWithFlowRemove == null) {
            error(channelHandlerContext, "PUBACK contained unknown packet identifier");
            return;
        }
        if (!(mqttPubOrRelWithFlowRemove instanceof MqttPublishWithFlow)) {
            this.pendingIndex.put(mqttPubOrRelWithFlowRemove);
            error(channelHandlerContext, "PUBACK must not be received for a PUBREL");
            return;
        }
        MqttPublishWithFlow mqttPublishWithFlow = (MqttPublishWithFlow) mqttPubOrRelWithFlowRemove;
        MqttPublish publish = mqttPublishWithFlow.getPublish();
        if (publish.getQos() != MqttQos.AT_LEAST_ONCE) {
            this.pendingIndex.put(mqttPubOrRelWithFlowRemove);
            error(channelHandlerContext, "PUBACK must not be received for a QoS 2 PUBLISH");
        } else {
            completePending(channelHandlerContext, mqttPublishWithFlow);
            onPubAck(publish, mqttPubAck);
            mqttPublishWithFlow.getAckFlow().onNext(new MqttPublishResult.MqttQos1Result(publish, ((Mqtt5PubAckReasonCode) mqttPubAck.getReasonCode()).isError() ? new Mqtt5PubAckException(mqttPubAck, "PUBACK contained an Error Code") : null, mqttPubAck));
        }
    }

    private void readPubRec(ChannelHandlerContext channelHandlerContext, MqttPubRec mqttPubRec) {
        int packetIdentifier = mqttPubRec.getPacketIdentifier();
        MqttPubOrRelWithFlow mqttPubOrRelWithFlow = this.pendingIndex.get(packetIdentifier);
        if (mqttPubOrRelWithFlow == null) {
            error(channelHandlerContext, "PUBREC contained unknown packet identifier");
            return;
        }
        if (!(mqttPubOrRelWithFlow instanceof MqttPublishWithFlow)) {
            error(channelHandlerContext, "PUBREC must not be received when the PUBREL has already been sent");
            return;
        }
        MqttPublishWithFlow mqttPublishWithFlow = (MqttPublishWithFlow) mqttPubOrRelWithFlow;
        MqttPublish publish = mqttPublishWithFlow.getPublish();
        if (publish.getQos() != MqttQos.EXACTLY_ONCE) {
            error(channelHandlerContext, "PUBREC must not be received for a QoS 1 PUBLISH");
            return;
        }
        MqttAckFlow ackFlow = mqttPublishWithFlow.getAckFlow();
        if (((Mqtt5PubRecReasonCode) mqttPubRec.getReasonCode()).isError()) {
            this.pendingIndex.remove(packetIdentifier);
            completePending(channelHandlerContext, mqttPublishWithFlow);
            onPubRecError(publish, mqttPubRec);
            ackFlow.onNext(new MqttPublishResult.MqttQos2Result(publish, new Mqtt5PubRecException(mqttPubRec, "PUBREC contained an Error Code"), mqttPubRec));
            return;
        }
        MqttPubRel mqttPubRelBuildPubRel = buildPubRel(publish, mqttPubRec);
        MqttPubRelWithFlow.MqttQos2IntermediateWithFlow mqttQos2IntermediateWithFlow = new MqttPubRelWithFlow.MqttQos2IntermediateWithFlow(mqttPubRelBuildPubRel, ackFlow);
        replacePending(mqttPublishWithFlow, mqttQos2IntermediateWithFlow);
        ackFlow.onNext(new MqttPublishResult.MqttQos2IntermediateResult(publish, mqttPubRec, mqttQos2IntermediateWithFlow));
        writePubRel(channelHandlerContext, mqttPubRelBuildPubRel);
        channelHandlerContext.flush();
    }

    private void writePubRel(ChannelHandlerContext channelHandlerContext, MqttPubRel mqttPubRel) {
        channelHandlerContext.write(mqttPubRel, channelHandlerContext.voidPromise());
    }

    private void replacePending(MqttPublishWithFlow mqttPublishWithFlow, MqttPubRelWithFlow mqttPubRelWithFlow) {
        mqttPubRelWithFlow.packetIdentifier = mqttPublishWithFlow.packetIdentifier;
        this.pendingIndex.put(mqttPubRelWithFlow);
        this.pending.replace(mqttPublishWithFlow, mqttPubRelWithFlow);
    }

    private void readPubComp(ChannelHandlerContext channelHandlerContext, MqttPubComp mqttPubComp) {
        MqttPubOrRelWithFlow mqttPubOrRelWithFlowRemove = this.pendingIndex.remove(mqttPubComp.getPacketIdentifier());
        if (mqttPubOrRelWithFlowRemove == null) {
            error(channelHandlerContext, "PUBCOMP contained unknown packet identifier");
            return;
        }
        if (!(mqttPubOrRelWithFlowRemove instanceof MqttPubRelWithFlow)) {
            this.pendingIndex.put(mqttPubOrRelWithFlowRemove);
            if (((MqttPublishWithFlow) mqttPubOrRelWithFlowRemove).getPublish().getQos() == MqttQos.AT_LEAST_ONCE) {
                error(channelHandlerContext, "PUBCOMP must not be received for a QoS 1 PUBLISH");
                return;
            } else {
                error(channelHandlerContext, "PUBCOMP must not be received when the PUBREL has not been sent yet");
                return;
            }
        }
        MqttPubRelWithFlow mqttPubRelWithFlow = (MqttPubRelWithFlow) mqttPubOrRelWithFlowRemove;
        MqttPubRel pubRel = mqttPubRelWithFlow.getPubRel();
        MqttAckFlow ackFlow = mqttPubRelWithFlow.getAckFlow();
        completePending(channelHandlerContext, mqttPubRelWithFlow);
        onPubComp(pubRel, mqttPubComp);
        if (((MqttPubRelWithFlow.MqttQos2IntermediateWithFlow) mqttPubRelWithFlow).getAsBoolean()) {
            ackFlow.acknowledged(1L);
        }
    }

    private void completePending(ChannelHandlerContext channelHandlerContext, MqttPubOrRelWithFlow mqttPubOrRelWithFlow) {
        this.pending.remove(mqttPubOrRelWithFlow);
        int i = mqttPubOrRelWithFlow.packetIdentifier;
        this.packetIdentifiers.returnId(i);
        int i2 = this.sendMaximum;
        if (i > i2) {
            this.packetIdentifiers.resize(i2);
        }
        if (this.resendPending != null) {
            channelHandlerContext.channel().eventLoop().execute(this);
        }
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) {
        MqttPublishWithFlow mqttPublishWithFlow;
        if (!(th instanceof IOException) && (mqttPublishWithFlow = this.currentPending) != null) {
            this.pendingIndex.remove(mqttPublishWithFlow.packetIdentifier);
            this.currentPending.getAckFlow().onNext(new MqttPublishResult(this.currentPending.getPublish(), th));
            completePending(channelHandlerContext, this.currentPending);
            this.currentPending = null;
            return;
        }
        channelHandlerContext.fireExceptionCaught(th);
    }

    @Override // com.hivemq.client.internal.mqtt.handler.MqttSessionAwareHandler
    public void onSessionEnd(Throwable th) {
        super.onSessionEnd(th);
        this.pendingIndex.clear();
        this.resendPending = null;
        if (isRepublishIfSessionExpired()) {
            return;
        }
        NodeList.Node first = this.pending.getFirst();
        while (true) {
            MqttPubOrRelWithFlow mqttPubOrRelWithFlow = (MqttPubOrRelWithFlow) first;
            if (mqttPubOrRelWithFlow != null) {
                this.packetIdentifiers.returnId(mqttPubOrRelWithFlow.packetIdentifier);
                if (mqttPubOrRelWithFlow instanceof MqttPublishWithFlow) {
                    mqttPubOrRelWithFlow.getAckFlow().onNext(new MqttPublishResult(((MqttPublishWithFlow) mqttPubOrRelWithFlow).getPublish(), th));
                } else {
                    MqttPubRelWithFlow.MqttQos2IntermediateWithFlow mqttQos2IntermediateWithFlow = (MqttPubRelWithFlow.MqttQos2IntermediateWithFlow) mqttPubOrRelWithFlow;
                    if (mqttQos2IntermediateWithFlow.getAsBoolean()) {
                        mqttQos2IntermediateWithFlow.getAckFlow().acknowledged(1L);
                    }
                }
                first = mqttPubOrRelWithFlow.getNext();
            } else {
                this.pending.clear();
                clearQueued(th);
                return;
            }
        }
    }

    private boolean isRepublishIfSessionExpired() {
        return this.clientConfig.isRepublishIfSessionExpired() && this.clientConfig.getState() != MqttClientState.DISCONNECTED;
    }

    private void clearQueued(Throwable th) {
        int i;
        do {
            i = 0;
            while (true) {
                MqttPublishWithFlow mqttPublishWithFlow = (MqttPublishWithFlow) this.queue.poll();
                if (mqttPublishWithFlow == null) {
                    break;
                }
                mqttPublishWithFlow.getAckFlow().onNext(new MqttPublishResult(mqttPublishWithFlow.getPublish(), th));
                i++;
            }
        } while (this.queuedCounter.addAndGet(-i) != 0);
    }

    private static void error(ChannelHandlerContext channelHandlerContext, String str) {
        MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, str);
    }

    private void onPubAck(MqttPublish mqttPublish, MqttPubAck mqttPubAck) {
        Mqtt5OutgoingQos1Interceptor outgoingQos1Interceptor;
        MqttClientInterceptors interceptors = this.clientConfig.getAdvancedConfig().getInterceptors();
        if (interceptors == null || (outgoingQos1Interceptor = interceptors.getOutgoingQos1Interceptor()) == null) {
            return;
        }
        outgoingQos1Interceptor.onPubAck(this.clientConfig, mqttPublish, mqttPubAck);
    }

    private void onPubRecError(MqttPublish mqttPublish, MqttPubRec mqttPubRec) {
        Mqtt5OutgoingQos2Interceptor outgoingQos2Interceptor;
        MqttClientInterceptors interceptors = this.clientConfig.getAdvancedConfig().getInterceptors();
        if (interceptors == null || (outgoingQos2Interceptor = interceptors.getOutgoingQos2Interceptor()) == null) {
            return;
        }
        outgoingQos2Interceptor.onPubRecError(this.clientConfig, mqttPublish, mqttPubRec);
    }

    private MqttPubRel buildPubRel(MqttPublish mqttPublish, MqttPubRec mqttPubRec) {
        Mqtt5OutgoingQos2Interceptor outgoingQos2Interceptor;
        MqttPubRelBuilder mqttPubRelBuilder = new MqttPubRelBuilder(mqttPubRec);
        MqttClientInterceptors interceptors = this.clientConfig.getAdvancedConfig().getInterceptors();
        if (interceptors != null && (outgoingQos2Interceptor = interceptors.getOutgoingQos2Interceptor()) != null) {
            outgoingQos2Interceptor.onPubRec(this.clientConfig, mqttPublish, mqttPubRec, mqttPubRelBuilder);
        }
        return mqttPubRelBuilder.build();
    }

    private void onPubComp(MqttPubRel mqttPubRel, MqttPubComp mqttPubComp) {
        Mqtt5OutgoingQos2Interceptor outgoingQos2Interceptor;
        MqttClientInterceptors interceptors = this.clientConfig.getAdvancedConfig().getInterceptors();
        if (interceptors == null || (outgoingQos2Interceptor = interceptors.getOutgoingQos2Interceptor()) == null) {
            return;
        }
        outgoingQos2Interceptor.onPubComp(this.clientConfig, mqttPubRel, mqttPubComp);
    }

    MqttPublishFlowables getPublishFlowables() {
        return this.publishFlowables;
    }
}
