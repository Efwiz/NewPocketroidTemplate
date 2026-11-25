package com.hivemq.client.internal.mqtt.mqtt3;

import com.hivemq.client.internal.mqtt.MqttBlockingClient;
import com.hivemq.client.internal.mqtt.exceptions.mqtt3.Mqtt3ExceptionFactory;
import com.hivemq.client.internal.mqtt.message.connect.connack.mqtt3.Mqtt3ConnAckView;
import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectView;
import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder;
import com.hivemq.client.internal.mqtt.message.disconnect.mqtt3.Mqtt3DisconnectView;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishView;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeView;
import com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.suback.mqtt3.Mqtt3SubAckView;
import com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeView;
import com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeViewBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConfig;
import com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5MessageException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3BlockingClientView implements Mqtt3BlockingClient {
    private final Mqtt3ClientConfigView clientConfig;
    private final MqttBlockingClient delegate;

    Mqtt3BlockingClientView(MqttBlockingClient mqttBlockingClient) {
        this.delegate = mqttBlockingClient;
        this.clientConfig = new Mqtt3ClientConfigView(mqttBlockingClient.getConfig());
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public Mqtt3ConnAck connect() {
        return connect(Mqtt3ConnectView.DEFAULT);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public Mqtt3ConnAck connect(Mqtt3Connect mqtt3Connect) {
        try {
            return Mqtt3ConnAckView.of(this.delegate.connect(MqttChecks.connect(mqtt3Connect)));
        } catch (Mqtt5MessageException e) {
            throw Mqtt3ExceptionFactory.mapWithStackTrace(e);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public Mqtt3ConnectViewBuilder.Send<Mqtt3ConnAck> connectWith() {
        return new Mqtt3ConnectViewBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3BlockingClientView$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.connect((Mqtt3ConnectView) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public Mqtt3SubAck subscribe(Mqtt3Subscribe mqtt3Subscribe) {
        try {
            return Mqtt3SubAckView.of(this.delegate.subscribe(MqttChecks.subscribe(mqtt3Subscribe)));
        } catch (Mqtt5MessageException e) {
            throw Mqtt3ExceptionFactory.mapWithStackTrace(e);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public Mqtt3SubscribeViewBuilder.Send<Mqtt3SubAck> subscribeWith() {
        return new Mqtt3SubscribeViewBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3BlockingClientView$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.subscribe((Mqtt3SubscribeView) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public Mqtt3BlockingClient.Mqtt3Publishes publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter) {
        return publishes(mqttGlobalPublishFilter, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public Mqtt3BlockingClient.Mqtt3Publishes publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, boolean z) {
        Checks.notNull(mqttGlobalPublishFilter, "Global publish filter");
        return new Mqtt3PublishesView(this.delegate.publishes(mqttGlobalPublishFilter, z));
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public void unsubscribe(Mqtt3Unsubscribe mqtt3Unsubscribe) {
        try {
            this.delegate.unsubscribe(MqttChecks.unsubscribe(mqtt3Unsubscribe));
        } catch (Mqtt5MessageException e) {
            throw Mqtt3ExceptionFactory.mapWithStackTrace(e);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public Mqtt3UnsubscribeViewBuilder.SendVoid unsubscribeWith() {
        return new Mqtt3UnsubscribeViewBuilder.SendVoid(new Consumer() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3BlockingClientView$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.unsubscribe((Mqtt3UnsubscribeView) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public void publish(Mqtt3Publish mqtt3Publish) {
        try {
            this.delegate.publish(MqttChecks.publish(mqtt3Publish));
        } catch (Mqtt5MessageException e) {
            throw Mqtt3ExceptionFactory.mapWithStackTrace(e);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public Mqtt3PublishViewBuilder.SendVoid publishWith() {
        return new Mqtt3PublishViewBuilder.SendVoid(new Consumer() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3BlockingClientView$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.publish((Mqtt3PublishView) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient
    public void disconnect() {
        try {
            this.delegate.disconnect(Mqtt3DisconnectView.DELEGATE);
        } catch (Mqtt5MessageException e) {
            throw Mqtt3ExceptionFactory.mapWithStackTrace(e);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client, com.hivemq.client.mqtt.MqttClient
    public Mqtt3ClientConfig getConfig() {
        return this.clientConfig;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client
    public Mqtt3RxClient toRx() {
        return new Mqtt3RxClientView(this.delegate.toRx());
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client
    public Mqtt3AsyncClient toAsync() {
        return new Mqtt3AsyncClientView(this.delegate.toAsync());
    }

    private static class Mqtt3PublishesView implements Mqtt3BlockingClient.Mqtt3Publishes {
        private final Mqtt5BlockingClient.Mqtt5Publishes delegate;

        Mqtt3PublishesView(Mqtt5BlockingClient.Mqtt5Publishes mqtt5Publishes) {
            this.delegate = mqtt5Publishes;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient.Mqtt3Publishes
        public Mqtt3Publish receive() throws InterruptedException {
            try {
                return Mqtt3PublishView.of(this.delegate.receive());
            } catch (RuntimeException e) {
                throw Mqtt3ExceptionFactory.mapWithStackTrace(e);
            }
        }

        @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient.Mqtt3Publishes
        public Optional<Mqtt3Publish> receive(long j, TimeUnit timeUnit) throws InterruptedException {
            if (j < 0) {
                throw new IllegalArgumentException("Timeout must be greater than 0.");
            }
            Checks.notNull(timeUnit, "Time unit");
            try {
                return this.delegate.receive(j, timeUnit).map(Mqtt3PublishView.JAVA_MAPPER);
            } catch (RuntimeException e) {
                throw Mqtt3ExceptionFactory.mapWithStackTrace(e);
            }
        }

        @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient.Mqtt3Publishes
        public Optional<Mqtt3Publish> receiveNow() {
            try {
                return this.delegate.receiveNow().map(Mqtt3PublishView.JAVA_MAPPER);
            } catch (RuntimeException e) {
                throw Mqtt3ExceptionFactory.mapWithStackTrace(e);
            }
        }

        @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient.Mqtt3Publishes, java.lang.AutoCloseable
        public void close() {
            this.delegate.close();
        }
    }
}
