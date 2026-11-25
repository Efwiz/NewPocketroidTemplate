package com.hivemq.client.internal.mqtt;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import com.hivemq.client.internal.mqtt.exceptions.MqttClientStateExceptions;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribeBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.AsyncRuntimeException;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5SubAckException;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5UnsubAckException;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAckReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAckReasonCode;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
public class MqttBlockingClient implements Mqtt5BlockingClient {
    private final MqttRxClient delegate;

    static Mqtt5SubAck handleSubAck(Mqtt5SubAck mqtt5SubAck) {
        Iterator<Mqtt5SubAckReasonCode> it = mqtt5SubAck.getReasonCodes().iterator();
        while (it.hasNext()) {
            if (it.next().isError()) {
                throw new Mqtt5SubAckException(mqtt5SubAck, "SUBACK contains at least one error code.");
            }
        }
        return mqtt5SubAck;
    }

    static Mqtt5UnsubAck handleUnsubAck(Mqtt5UnsubAck mqtt5UnsubAck) {
        Iterator<Mqtt5UnsubAckReasonCode> it = mqtt5UnsubAck.getReasonCodes().iterator();
        while (it.hasNext()) {
            if (it.next().isError()) {
                throw new Mqtt5UnsubAckException(mqtt5UnsubAck, "UNSUBACK contains at least one error code.");
            }
        }
        return mqtt5UnsubAck;
    }

    MqttBlockingClient(MqttRxClient mqttRxClient) {
        this.delegate = mqttRxClient;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public Mqtt5ConnAck connect() {
        return connect(MqttConnect.DEFAULT);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public Mqtt5ConnAck connect(Mqtt5Connect mqtt5Connect) {
        try {
            return this.delegate.connectUnsafe(MqttChecks.connect(mqtt5Connect)).blockingGet();
        } catch (RuntimeException e) {
            throw AsyncRuntimeException.fillInStackTrace(e);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public MqttConnectBuilder.Send<Mqtt5ConnAck> connectWith() {
        return new MqttConnectBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.MqttBlockingClient$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.connect((MqttConnect) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public Mqtt5SubAck subscribe(Mqtt5Subscribe mqtt5Subscribe) {
        MqttSubscribe mqttSubscribeSubscribe = MqttChecks.subscribe(mqtt5Subscribe);
        try {
            if (!getState().isConnectedOrReconnect()) {
                throw MqttClientStateExceptions.notConnected();
            }
            return handleSubAck(this.delegate.subscribeUnsafe(mqttSubscribeSubscribe).blockingGet());
        } catch (RuntimeException e) {
            throw AsyncRuntimeException.fillInStackTrace(e);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public MqttSubscribeBuilder.Send<Mqtt5SubAck> subscribeWith() {
        return new MqttSubscribeBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.MqttBlockingClient$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.subscribe((MqttSubscribe) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public Mqtt5BlockingClient.Mqtt5Publishes publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter) {
        return publishes(mqttGlobalPublishFilter, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public Mqtt5BlockingClient.Mqtt5Publishes publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, boolean z) {
        Checks.notNull(mqttGlobalPublishFilter, "Global publish filter");
        return new MqttPublishes(this.delegate.publishesUnsafe(mqttGlobalPublishFilter, z));
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public Mqtt5UnsubAck unsubscribe(Mqtt5Unsubscribe mqtt5Unsubscribe) {
        MqttUnsubscribe mqttUnsubscribeUnsubscribe = MqttChecks.unsubscribe(mqtt5Unsubscribe);
        try {
            if (!getState().isConnectedOrReconnect()) {
                throw MqttClientStateExceptions.notConnected();
            }
            return handleUnsubAck(this.delegate.unsubscribeUnsafe(mqttUnsubscribeUnsubscribe).blockingGet());
        } catch (RuntimeException e) {
            throw AsyncRuntimeException.fillInStackTrace(e);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public MqttUnsubscribeBuilder.Send<Mqtt5UnsubAck> unsubscribeWith() {
        return new MqttUnsubscribeBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.MqttBlockingClient$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.unsubscribe((MqttUnsubscribe) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public Mqtt5PublishResult publish(Mqtt5Publish mqtt5Publish) {
        try {
            return this.delegate.publishUnsafe(MqttChecks.publish(mqtt5Publish)).blockingGet();
        } catch (RuntimeException e) {
            throw AsyncRuntimeException.fillInStackTrace(e);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public MqttPublishBuilder.Send<Mqtt5PublishResult> publishWith() {
        return new MqttPublishBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.MqttBlockingClient$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.publish((MqttPublish) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public void reauth() {
        try {
            this.delegate.reauthUnsafe().blockingAwait();
        } catch (RuntimeException e) {
            throw AsyncRuntimeException.fillInStackTrace(e);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public void disconnect() {
        disconnect(MqttDisconnect.DEFAULT);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public void disconnect(Mqtt5Disconnect mqtt5Disconnect) {
        try {
            this.delegate.disconnectUnsafe(MqttChecks.disconnect(mqtt5Disconnect)).blockingAwait();
        } catch (RuntimeException e) {
            throw AsyncRuntimeException.fillInStackTrace(e);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
    public MqttDisconnectBuilder.SendVoid disconnectWith() {
        return new MqttDisconnectBuilder.SendVoid(new Consumer() { // from class: com.hivemq.client.internal.mqtt.MqttBlockingClient$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.disconnect((MqttDisconnect) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client, com.hivemq.client.mqtt.MqttClient
    public MqttClientConfig getConfig() {
        return this.delegate.getConfig();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client
    public MqttRxClient toRx() {
        return this.delegate;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client
    public MqttAsyncClient toAsync() {
        return this.delegate.toAsync();
    }

    private static class MqttPublishes implements Mqtt5BlockingClient.Mqtt5Publishes, FlowableSubscriber<Mqtt5Publish> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Throwable error;
        private Mqtt5Publish queuedPublish;
        private final AtomicReference<Subscription> subscription = new AtomicReference<>();
        private final LinkedList<Entry> entries = new LinkedList<>();

        MqttPublishes(Flowable<Mqtt5Publish> flowable) {
            flowable.subscribe((FlowableSubscriber<? super Mqtt5Publish>) this);
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.subscription, null, subscription)) {
                subscription.request(1L);
            } else {
                subscription.cancel();
            }
        }

        private void request() {
            this.subscription.get().request(1L);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Mqtt5Publish mqtt5Publish) {
            boolean zM;
            synchronized (this.entries) {
                if (this.error != null) {
                    return;
                }
                do {
                    Entry entryPoll = this.entries.poll();
                    if (entryPoll != null) {
                        zM = LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(entryPoll.result, null, mqtt5Publish);
                        entryPoll.latch.countDown();
                    } else {
                        this.queuedPublish = mqtt5Publish;
                        return;
                    }
                } while (!zM);
                request();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            onError(new IllegalStateException());
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            synchronized (this.entries) {
                if (this.error != null) {
                    return;
                }
                this.error = th;
                while (true) {
                    Entry entryPoll = this.entries.poll();
                    if (entryPoll == null) {
                        return;
                    }
                    entryPoll.result.set(th);
                    entryPoll.latch.countDown();
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient.Mqtt5Publishes
        public Mqtt5Publish receive() throws InterruptedException {
            synchronized (this.entries) {
                Throwable th = this.error;
                if (th != null) {
                    throw handleError(th);
                }
                Mqtt5Publish mqtt5PublishReceiveNowUnsafe = receiveNowUnsafe();
                if (mqtt5PublishReceiveNowUnsafe != null) {
                    return mqtt5PublishReceiveNowUnsafe;
                }
                InterruptedException interruptedException = null;
                Entry entry = new Entry();
                this.entries.offer(entry);
                try {
                    entry.latch.await();
                } catch (InterruptedException e) {
                    interruptedException = e;
                }
                Object andSet = entry.result.getAndSet(Entry.CANCELLED);
                if (andSet instanceof Mqtt5Publish) {
                    return (Mqtt5Publish) andSet;
                }
                if (andSet instanceof Throwable) {
                    throw handleError((Throwable) andSet);
                }
                if (interruptedException != null) {
                    throw interruptedException;
                }
                throw new InterruptedException();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient.Mqtt5Publishes
        public Optional<Mqtt5Publish> receive(long j, TimeUnit timeUnit) throws InterruptedException {
            if (j < 0) {
                throw new IllegalArgumentException("Timeout must be greater than 0.");
            }
            Checks.notNull(timeUnit, "Time unit");
            synchronized (this.entries) {
                Throwable th = this.error;
                if (th != null) {
                    throw handleError(th);
                }
                Mqtt5Publish mqtt5PublishReceiveNowUnsafe = receiveNowUnsafe();
                if (mqtt5PublishReceiveNowUnsafe != null) {
                    return Optional.of(mqtt5PublishReceiveNowUnsafe);
                }
                InterruptedException interruptedException = null;
                Entry entry = new Entry();
                this.entries.offer(entry);
                try {
                    entry.latch.await(j, timeUnit);
                } catch (InterruptedException e) {
                    interruptedException = e;
                }
                Object andSet = entry.result.getAndSet(Entry.CANCELLED);
                if (andSet instanceof Mqtt5Publish) {
                    return Optional.of((Mqtt5Publish) andSet);
                }
                if (andSet instanceof Throwable) {
                    throw handleError((Throwable) andSet);
                }
                if (interruptedException != null) {
                    throw interruptedException;
                }
                return Optional.empty();
            }
        }

        @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient.Mqtt5Publishes
        public Optional<Mqtt5Publish> receiveNow() {
            Mqtt5Publish mqtt5PublishReceiveNowUnsafe;
            synchronized (this.entries) {
                Throwable th = this.error;
                if (th != null) {
                    throw handleError(th);
                }
                mqtt5PublishReceiveNowUnsafe = receiveNowUnsafe();
            }
            return Optional.ofNullable(mqtt5PublishReceiveNowUnsafe);
        }

        private Mqtt5Publish receiveNowUnsafe() {
            Mqtt5Publish mqtt5Publish = this.queuedPublish;
            if (mqtt5Publish == null) {
                return null;
            }
            this.queuedPublish = null;
            request();
            return mqtt5Publish;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient.Mqtt5Publishes, java.lang.AutoCloseable
        public void close() {
            Subscription andSet = this.subscription.getAndSet(SubscriptionHelper.CANCELLED);
            if (andSet != null) {
                andSet.cancel();
            }
            synchronized (this.entries) {
                if (this.error != null) {
                    return;
                }
                this.error = new CancellationException();
                while (true) {
                    Entry entryPoll = this.entries.poll();
                    if (entryPoll == null) {
                        return;
                    }
                    entryPoll.result.set(this.error);
                    entryPoll.latch.countDown();
                }
            }
        }

        private RuntimeException handleError(Throwable th) {
            if (th instanceof RuntimeException) {
                return AsyncRuntimeException.fillInStackTrace((RuntimeException) th);
            }
            throw new RuntimeException(th);
        }

        private static class Entry {
            static final Object CANCELLED = new Object();
            final CountDownLatch latch;
            final AtomicReference<Object> result;

            private Entry() {
                this.latch = new CountDownLatch(1);
                this.result = new AtomicReference<>();
            }
        }
    }
}
