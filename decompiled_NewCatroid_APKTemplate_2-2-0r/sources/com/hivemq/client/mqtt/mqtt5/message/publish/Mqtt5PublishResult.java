package com.hivemq.client.mqtt.mqtt5.message.publish;

import com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAck;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubComp;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRec;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRel;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5PublishResult {

    public interface Mqtt5Qos1Result extends Mqtt5PublishResult {
        Mqtt5PubAck getPubAck();
    }

    public interface Mqtt5Qos2CompleteResult extends Mqtt5Qos2Result {
        Mqtt5PubComp getPubComp();

        Mqtt5PubRel getPubRel();
    }

    public interface Mqtt5Qos2Result extends Mqtt5PublishResult {
        Mqtt5PubRec getPubRec();
    }

    Optional<Throwable> getError();

    Mqtt5Publish getPublish();
}
