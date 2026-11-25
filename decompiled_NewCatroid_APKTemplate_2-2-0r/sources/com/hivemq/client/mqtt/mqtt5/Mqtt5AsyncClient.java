package com.hivemq.client.mqtt.mqtt5;

import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilder;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilder;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5AsyncClient extends Mqtt5Client {

    public interface Mqtt5SubscribeAndCallbackBuilder extends Mqtt5SubscribeBuilderBase<Complete> {

        public interface Call {

            public interface Ex extends Call {
                Ex executor(Executor executor);

                Ex manualAcknowledgement(boolean z);
            }

            Ex callback(Consumer<Mqtt5Publish> consumer);

            CompletableFuture<Mqtt5SubAck> send();
        }

        public interface Complete extends Mqtt5SubscribeAndCallbackBuilder, Call, Mqtt5SubscribeBuilderBase.Complete<Complete> {
        }

        public interface Start extends Mqtt5SubscribeAndCallbackBuilder, Mqtt5SubscribeBuilderBase.Start<Complete, Complete> {

            public interface Complete extends Start, Complete, Mqtt5SubscribeBuilderBase.Start.Complete<Complete, Complete> {
            }
        }
    }

    CompletableFuture<Mqtt5ConnAck> connect();

    CompletableFuture<Mqtt5ConnAck> connect(Mqtt5Connect mqtt5Connect);

    Mqtt5ConnectBuilder.Send<CompletableFuture<Mqtt5ConnAck>> connectWith();

    CompletableFuture<Void> disconnect();

    CompletableFuture<Void> disconnect(Mqtt5Disconnect mqtt5Disconnect);

    Mqtt5DisconnectBuilder.Send<CompletableFuture<Void>> disconnectWith();

    CompletableFuture<Mqtt5PublishResult> publish(Mqtt5Publish mqtt5Publish);

    Mqtt5PublishBuilder.Send<CompletableFuture<Mqtt5PublishResult>> publishWith();

    void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt5Publish> consumer);

    void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt5Publish> consumer, Executor executor);

    void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt5Publish> consumer, Executor executor, boolean z);

    void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt5Publish> consumer, boolean z);

    CompletableFuture<Void> reauth();

    CompletableFuture<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe);

    CompletableFuture<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe, Consumer<Mqtt5Publish> consumer);

    CompletableFuture<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe, Consumer<Mqtt5Publish> consumer, Executor executor);

    CompletableFuture<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe, Consumer<Mqtt5Publish> consumer, Executor executor, boolean z);

    CompletableFuture<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe, Consumer<Mqtt5Publish> consumer, boolean z);

    Mqtt5SubscribeAndCallbackBuilder.Start subscribeWith();

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client
    default Mqtt5AsyncClient toAsync() {
        return this;
    }

    CompletableFuture<Mqtt5UnsubAck> unsubscribe(Mqtt5Unsubscribe mqtt5Unsubscribe);

    Mqtt5UnsubscribeBuilder.Send.Start<CompletableFuture<Mqtt5UnsubAck>> unsubscribeWith();
}
