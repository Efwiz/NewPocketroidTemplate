package com.hivemq.client.mqtt.mqtt3.message.publish;

import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3PublishResult {
    Optional<Throwable> getError();

    Mqtt3Publish getPublish();
}
