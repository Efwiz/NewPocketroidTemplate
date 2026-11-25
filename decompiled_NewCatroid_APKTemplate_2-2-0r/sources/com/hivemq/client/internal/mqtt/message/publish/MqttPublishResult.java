package com.hivemq.client.internal.mqtt.message.publish;

import com.hivemq.client.internal.mqtt.message.publish.puback.MqttPubAck;
import com.hivemq.client.internal.mqtt.message.publish.pubcomp.MqttPubComp;
import com.hivemq.client.internal.mqtt.message.publish.pubrec.MqttPubRec;
import com.hivemq.client.internal.mqtt.message.publish.pubrel.MqttPubRel;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPublishResult implements Mqtt5PublishResult {
    private final Throwable error;
    private final MqttPublish publish;

    public boolean acknowledged() {
        return true;
    }

    public MqttPublishResult(MqttPublish mqttPublish, Throwable th) {
        this.publish = mqttPublish;
        this.error = th;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult
    public MqttPublish getPublish() {
        return this.publish;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult
    public Optional<Throwable> getError() {
        return Optional.ofNullable(this.error);
    }

    public Throwable getRawError() {
        return this.error;
    }

    String toAttributeString() {
        return "publish=" + this.publish + (this.error == null ? "" : ", error=" + this.error);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttPublishResult)) {
            return false;
        }
        MqttPublishResult mqttPublishResult = (MqttPublishResult) obj;
        return mqttPublishResult.canEqual(this) && this.publish.equals(mqttPublishResult.publish) && Objects.equals(this.error, mqttPublishResult.error);
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof MqttPublishResult;
    }

    public int hashCode() {
        return (this.publish.hashCode() * 31) + Objects.hashCode(this.error);
    }

    public String toString() {
        return "MqttPublishResult{" + toAttributeString() + '}';
    }

    public static class MqttQos1Result extends MqttPublishResult implements Mqtt5PublishResult.Mqtt5Qos1Result {
        private final MqttPubAck pubAck;

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult
        public /* bridge */ /* synthetic */ Mqtt5Publish getPublish() {
            return super.getPublish();
        }

        public MqttQos1Result(MqttPublish mqttPublish, Throwable th, MqttPubAck mqttPubAck) {
            super(mqttPublish, th);
            this.pubAck = mqttPubAck;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult.Mqtt5Qos1Result
        public MqttPubAck getPubAck() {
            return this.pubAck;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        String toAttributeString() {
            return super.toAttributeString() + ", pubAck=" + this.pubAck;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        public String toString() {
            return "MqttQos1Result{" + toAttributeString() + '}';
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if ((obj instanceof MqttQos1Result) && super.equals(obj)) {
                return this.pubAck.equals(((MqttQos1Result) obj).pubAck);
            }
            return false;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        protected boolean canEqual(Object obj) {
            return obj instanceof MqttQos1Result;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        public int hashCode() {
            return (super.hashCode() * 31) + this.pubAck.hashCode();
        }
    }

    public static class MqttQos2Result extends MqttPublishResult implements Mqtt5PublishResult.Mqtt5Qos2Result {
        private final MqttPubRec pubRec;

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult
        public /* bridge */ /* synthetic */ Mqtt5Publish getPublish() {
            return super.getPublish();
        }

        public MqttQos2Result(MqttPublish mqttPublish, Throwable th, MqttPubRec mqttPubRec) {
            super(mqttPublish, th);
            this.pubRec = mqttPubRec;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult.Mqtt5Qos2Result
        public MqttPubRec getPubRec() {
            return this.pubRec;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        String toAttributeString() {
            return super.toAttributeString() + ", pubRec=" + this.pubRec;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        public String toString() {
            return "MqttQos2Result{" + toAttributeString() + '}';
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if ((obj instanceof MqttQos2Result) && super.equals(obj)) {
                return this.pubRec.equals(((MqttQos2Result) obj).pubRec);
            }
            return false;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        protected boolean canEqual(Object obj) {
            return obj instanceof MqttQos2Result;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        public int hashCode() {
            return (super.hashCode() * 31) + this.pubRec.hashCode();
        }
    }

    public static class MqttQos2IntermediateResult extends MqttQos2Result {
        private final BooleanSupplier acknowledgedCallback;

        public MqttQos2IntermediateResult(MqttPublish mqttPublish, MqttPubRec mqttPubRec, BooleanSupplier booleanSupplier) {
            super(mqttPublish, null, mqttPubRec);
            this.acknowledgedCallback = booleanSupplier;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        public boolean acknowledged() {
            return this.acknowledgedCallback.getAsBoolean();
        }
    }

    public static class MqttQos2CompleteResult extends MqttQos2Result implements Mqtt5PublishResult.Mqtt5Qos2CompleteResult {
        private final MqttPubComp pubComp;
        private final MqttPubRel pubRel;

        public MqttQos2CompleteResult(MqttPublish mqttPublish, MqttPubRec mqttPubRec, MqttPubRel mqttPubRel, MqttPubComp mqttPubComp) {
            super(mqttPublish, null, mqttPubRec);
            this.pubRel = mqttPubRel;
            this.pubComp = mqttPubComp;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult.Mqtt5Qos2CompleteResult
        public MqttPubRel getPubRel() {
            return this.pubRel;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult.Mqtt5Qos2CompleteResult
        public MqttPubComp getPubComp() {
            return this.pubComp;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult.MqttQos2Result, com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        String toAttributeString() {
            return super.toAttributeString() + ", pubRel=" + this.pubRel + ", pubComp=" + this.pubComp;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult.MqttQos2Result, com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        public String toString() {
            return "MqttQos2CompleteResult{" + toAttributeString() + '}';
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult.MqttQos2Result, com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MqttQos2CompleteResult) || !super.equals(obj)) {
                return false;
            }
            MqttQos2CompleteResult mqttQos2CompleteResult = (MqttQos2CompleteResult) obj;
            return this.pubRel.equals(mqttQos2CompleteResult.pubRel) && this.pubComp.equals(mqttQos2CompleteResult.pubComp);
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult.MqttQos2Result, com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        protected boolean canEqual(Object obj) {
            return obj instanceof MqttQos2CompleteResult;
        }

        @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult.MqttQos2Result, com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult
        public int hashCode() {
            return (((super.hashCode() * 31) + this.pubRel.hashCode()) * 31) + this.pubComp.hashCode();
        }
    }
}
