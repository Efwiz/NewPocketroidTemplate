package com.hivemq.client.mqtt.mqtt5.auth;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5Auth;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthBuilder;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuthBuilder;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5EnhancedAuthMechanism {
    MqttUtf8String getMethod();

    int getTimeout();

    CompletableFuture<Void> onAuth(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5Connect mqtt5Connect, Mqtt5EnhancedAuthBuilder mqtt5EnhancedAuthBuilder);

    void onAuthError(Mqtt5ClientConfig mqtt5ClientConfig, Throwable th);

    void onAuthRejected(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5ConnAck mqtt5ConnAck);

    CompletableFuture<Boolean> onAuthSuccess(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5ConnAck mqtt5ConnAck);

    CompletableFuture<Boolean> onContinue(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5Auth mqtt5Auth, Mqtt5AuthBuilder mqtt5AuthBuilder);

    CompletableFuture<Void> onReAuth(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5AuthBuilder mqtt5AuthBuilder);

    void onReAuthError(Mqtt5ClientConfig mqtt5ClientConfig, Throwable th);

    void onReAuthRejected(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5Disconnect mqtt5Disconnect);

    CompletableFuture<Boolean> onReAuthSuccess(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5Auth mqtt5Auth);

    static /* synthetic */ Boolean lambda$onServerReAuth$0(Void r0) {
        return true;
    }

    default CompletableFuture<Boolean> onServerReAuth(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5Auth mqtt5Auth, Mqtt5AuthBuilder mqtt5AuthBuilder) {
        return onReAuth(mqtt5ClientConfig, mqtt5AuthBuilder).thenApply(new Function() { // from class: com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Mqtt5EnhancedAuthMechanism.lambda$onServerReAuth$0((Void) obj);
            }
        });
    }
}
