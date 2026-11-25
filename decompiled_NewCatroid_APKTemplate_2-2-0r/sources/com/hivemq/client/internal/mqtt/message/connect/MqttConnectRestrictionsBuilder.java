package com.hivemq.client.internal.mqtt.message.connect;

import com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictionsBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictions;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictionsBuilder;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictionsBuilderBase;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilder;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttConnectRestrictionsBuilder<B extends MqttConnectRestrictionsBuilder<B>> {
    private int maximumPacketSize;
    private int receiveMaximum;
    private boolean requestProblemInformation;
    private boolean requestResponseInformation;
    private int sendMaximum;
    private int sendMaximumPacketSize;
    private int sendTopicAliasMaximum;
    private int topicAliasMaximum;

    abstract B self();

    MqttConnectRestrictionsBuilder() {
        this.receiveMaximum = 65535;
        this.sendMaximum = 65535;
        this.maximumPacketSize = 268435460;
        this.sendMaximumPacketSize = 268435460;
        this.topicAliasMaximum = 0;
        this.sendTopicAliasMaximum = 16;
        this.requestProblemInformation = true;
        this.requestResponseInformation = false;
    }

    MqttConnectRestrictionsBuilder(MqttConnectRestrictions mqttConnectRestrictions) {
        this.receiveMaximum = 65535;
        this.sendMaximum = 65535;
        this.maximumPacketSize = 268435460;
        this.sendMaximumPacketSize = 268435460;
        this.topicAliasMaximum = 0;
        this.sendTopicAliasMaximum = 16;
        this.requestProblemInformation = true;
        this.requestResponseInformation = false;
        this.receiveMaximum = mqttConnectRestrictions.getReceiveMaximum();
        this.sendMaximum = mqttConnectRestrictions.getSendMaximum();
        this.maximumPacketSize = mqttConnectRestrictions.getMaximumPacketSize();
        this.sendMaximumPacketSize = mqttConnectRestrictions.getSendMaximumPacketSize();
        this.topicAliasMaximum = mqttConnectRestrictions.getTopicAliasMaximum();
        this.sendTopicAliasMaximum = mqttConnectRestrictions.getSendTopicAliasMaximum();
        this.requestProblemInformation = mqttConnectRestrictions.isRequestProblemInformation();
        this.requestResponseInformation = mqttConnectRestrictions.isRequestResponseInformation();
    }

    public B receiveMaximum(int i) {
        this.receiveMaximum = Checks.unsignedShortNotZero(i, "Receive maximum");
        return (B) self();
    }

    public B sendMaximum(int i) {
        this.sendMaximum = Checks.unsignedShortNotZero(i, "Send maximum");
        return (B) self();
    }

    public B maximumPacketSize(int i) {
        this.maximumPacketSize = MqttChecks.packetSize(i, "Maximum packet size");
        return (B) self();
    }

    public B sendMaximumPacketSize(int i) {
        this.sendMaximumPacketSize = MqttChecks.packetSize(i, "Send maximum packet size");
        return (B) self();
    }

    public B topicAliasMaximum(int i) {
        this.topicAliasMaximum = Checks.unsignedShort(i, "Topic alias maximum");
        return (B) self();
    }

    public B sendTopicAliasMaximum(int i) {
        this.sendTopicAliasMaximum = Checks.unsignedShort(i, "Send topic alias maximum");
        return (B) self();
    }

    public B requestProblemInformation(boolean z) {
        this.requestProblemInformation = z;
        return (B) self();
    }

    public B requestResponseInformation(boolean z) {
        this.requestResponseInformation = z;
        return (B) self();
    }

    public MqttConnectRestrictions build() {
        return new MqttConnectRestrictions(this.receiveMaximum, this.sendMaximum, this.maximumPacketSize, this.sendMaximumPacketSize, this.topicAliasMaximum, this.sendTopicAliasMaximum, this.requestProblemInformation, this.requestResponseInformation);
    }

    public static class Default extends MqttConnectRestrictionsBuilder<Default> implements Mqtt5ConnectRestrictionsBuilder, Mqtt3ConnectRestrictionsBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictionsBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictionsBuilder
        public /* bridge */ /* synthetic */ Mqtt3ConnectRestrictions build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilder
        /* JADX INFO: renamed from: build, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictions mo2720build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase maximumPacketSize(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.maximumPacketSize(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase receiveMaximum(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.receiveMaximum(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase requestProblemInformation(boolean z) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.requestProblemInformation(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase requestResponseInformation(boolean z) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.requestResponseInformation(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectRestrictionsBuilderBase sendMaximum(int i) {
            return (Mqtt3ConnectRestrictionsBuilderBase) super.sendMaximum(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        /* JADX INFO: renamed from: sendMaximum, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase mo2721sendMaximum(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.sendMaximum(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectRestrictionsBuilderBase sendMaximumPacketSize(int i) {
            return (Mqtt3ConnectRestrictionsBuilderBase) super.sendMaximumPacketSize(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        /* JADX INFO: renamed from: sendMaximumPacketSize, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase mo2722sendMaximumPacketSize(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.sendMaximumPacketSize(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase sendTopicAliasMaximum(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.sendTopicAliasMaximum(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase topicAliasMaximum(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.topicAliasMaximum(i);
        }

        public Default() {
        }

        Default(MqttConnectRestrictions mqttConnectRestrictions) {
            super(mqttConnectRestrictions);
        }
    }

    public static class Nested<P> extends MqttConnectRestrictionsBuilder<Nested<P>> implements Mqtt5ConnectRestrictionsBuilder.Nested<P>, Mqtt3ConnectRestrictionsBuilder.Nested<P> {
        private final Function<? super MqttConnectRestrictions, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictionsBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase maximumPacketSize(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.maximumPacketSize(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase receiveMaximum(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.receiveMaximum(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase requestProblemInformation(boolean z) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.requestProblemInformation(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase requestResponseInformation(boolean z) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.requestResponseInformation(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectRestrictionsBuilderBase sendMaximum(int i) {
            return (Mqtt3ConnectRestrictionsBuilderBase) super.sendMaximum(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        /* JADX INFO: renamed from: sendMaximum */
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase mo2721sendMaximum(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.sendMaximum(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3ConnectRestrictionsBuilderBase sendMaximumPacketSize(int i) {
            return (Mqtt3ConnectRestrictionsBuilderBase) super.sendMaximumPacketSize(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        /* JADX INFO: renamed from: sendMaximumPacketSize */
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase mo2722sendMaximumPacketSize(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.sendMaximumPacketSize(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase sendTopicAliasMaximum(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.sendTopicAliasMaximum(i);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ConnectRestrictionsBuilderBase topicAliasMaximum(int i) {
            return (Mqtt5ConnectRestrictionsBuilderBase) super.topicAliasMaximum(i);
        }

        public Nested(MqttConnectRestrictions mqttConnectRestrictions, Function<? super MqttConnectRestrictions, P> function) {
            super(mqttConnectRestrictions);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilder.Nested, com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictionsBuilder.Nested
        public P applyRestrictions() {
            return this.parentConsumer.apply(build());
        }
    }
}
