package com.hivemq.client.mqtt.mqtt3;

import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilder;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilder;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3AsyncClient extends Mqtt3Client {

    public interface Mqtt3SubscribeAndCallbackBuilder extends Mqtt3SubscribeBuilderBase<Complete> {

        public interface Call {

            public interface Ex extends Call {
                Ex executor(Executor executor);

                Ex manualAcknowledgement(boolean z);
            }

            Ex callback(Consumer<Mqtt3Publish> consumer);

            CompletableFuture<Mqtt3SubAck> send();
        }

        public interface Complete extends Mqtt3SubscribeAndCallbackBuilder, Call, Mqtt3SubscribeBuilderBase<Complete> {
        }

        public interface Start extends Mqtt3SubscribeAndCallbackBuilder, Mqtt3SubscribeBuilderBase.Start<Complete, Complete> {

            public interface Complete extends Start, Complete, Mqtt3SubscribeBuilderBase.Start.Complete<Complete, Complete> {
            }
        }
    }

    CompletableFuture<Mqtt3ConnAck> connect();

    CompletableFuture<Mqtt3ConnAck> connect(Mqtt3Connect mqtt3Connect);

    Mqtt3ConnectBuilder.Send<CompletableFuture<Mqtt3ConnAck>> connectWith();

    CompletableFuture<Void> disconnect();

    CompletableFuture<Mqtt3Publish> publish(Mqtt3Publish mqtt3Publish);

    Mqtt3PublishBuilder.Send<CompletableFuture<Mqtt3Publish>> publishWith();

    void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt3Publish> consumer);

    void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt3Publish> consumer, Executor executor);

    void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt3Publish> consumer, Executor executor, boolean z);

    void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt3Publish> consumer, boolean z);

    CompletableFuture<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe);

    CompletableFuture<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe, Consumer<Mqtt3Publish> consumer);

    CompletableFuture<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe, Consumer<Mqtt3Publish> consumer, Executor executor);

    CompletableFuture<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe, Consumer<Mqtt3Publish> consumer, Executor executor, boolean z);

    CompletableFuture<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe, Consumer<Mqtt3Publish> consumer, boolean z);

    Mqtt3SubscribeAndCallbackBuilder.Start subscribeWith();

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client
    default Mqtt3AsyncClient toAsync() {
        return this;
    }

    CompletableFuture<Void> unsubscribe(Mqtt3Unsubscribe mqtt3Unsubscribe);

    Mqtt3UnsubscribeBuilder.Send.Start<CompletableFuture<Void>> unsubscribeWith();
}
