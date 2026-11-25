package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoders;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttPingReqEncoder;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5ClientMessageEncoders extends MqttMessageEncoders {
    @Inject
    Mqtt5ClientMessageEncoders(Mqtt5ConnectEncoder mqtt5ConnectEncoder, Mqtt5PublishEncoder mqtt5PublishEncoder, Mqtt5PubAckEncoder mqtt5PubAckEncoder, Mqtt5PubRecEncoder mqtt5PubRecEncoder, Mqtt5PubRelEncoder mqtt5PubRelEncoder, Mqtt5PubCompEncoder mqtt5PubCompEncoder, Mqtt5SubscribeEncoder mqtt5SubscribeEncoder, Mqtt5UnsubscribeEncoder mqtt5UnsubscribeEncoder, MqttPingReqEncoder mqttPingReqEncoder, Mqtt5DisconnectEncoder mqtt5DisconnectEncoder, Mqtt5AuthEncoder mqtt5AuthEncoder) {
        this.encoders[Mqtt5MessageType.CONNECT.getCode()] = mqtt5ConnectEncoder;
        this.encoders[Mqtt5MessageType.PUBLISH.getCode()] = mqtt5PublishEncoder;
        this.encoders[Mqtt5MessageType.PUBACK.getCode()] = mqtt5PubAckEncoder;
        this.encoders[Mqtt5MessageType.PUBREC.getCode()] = mqtt5PubRecEncoder;
        this.encoders[Mqtt5MessageType.PUBREL.getCode()] = mqtt5PubRelEncoder;
        this.encoders[Mqtt5MessageType.PUBCOMP.getCode()] = mqtt5PubCompEncoder;
        this.encoders[Mqtt5MessageType.SUBSCRIBE.getCode()] = mqtt5SubscribeEncoder;
        this.encoders[Mqtt5MessageType.UNSUBSCRIBE.getCode()] = mqtt5UnsubscribeEncoder;
        this.encoders[Mqtt5MessageType.PINGREQ.getCode()] = mqttPingReqEncoder;
        this.encoders[Mqtt5MessageType.DISCONNECT.getCode()] = mqtt5DisconnectEncoder;
        this.encoders[Mqtt5MessageType.AUTH.getCode()] = mqtt5AuthEncoder;
    }
}
