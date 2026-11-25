package com.hivemq.client.internal.mqtt.message.publish.mqtt3;

import com.hivemq.client.internal.mqtt.exceptions.mqtt3.Mqtt3ExceptionFactory;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishResult;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import io.reactivex.functions.Function;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3PublishResultView implements Mqtt3PublishResult {
    public static final Function<Mqtt5PublishResult, Mqtt3PublishResult> MAPPER = new Function() { // from class: com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishResultView$$ExternalSyntheticLambda0
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return Mqtt3PublishResultView.of((Mqtt5PublishResult) obj);
        }
    };
    private final MqttPublishResult delegate;

    public static Mqtt3PublishResultView of(Mqtt5PublishResult mqtt5PublishResult) {
        return new Mqtt3PublishResultView((MqttPublishResult) mqtt5PublishResult);
    }

    private Mqtt3PublishResultView(MqttPublishResult mqttPublishResult) {
        this.delegate = mqttPublishResult;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishResult
    public Mqtt3Publish getPublish() {
        return Mqtt3PublishView.of(this.delegate.getPublish());
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishResult
    public Optional<Throwable> getError() {
        return this.delegate.getError().map(Mqtt3ExceptionFactory.MAPPER_JAVA);
    }

    private String toAttributeString() {
        return "publish=" + getPublish() + (!getError().isPresent() ? "" : ", error=" + getError().get());
    }

    public String toString() {
        return "MqttPublishResult{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Mqtt3PublishResultView) {
            return this.delegate.equals(((Mqtt3PublishResultView) obj).delegate);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }
}
