package com.hivemq.client.internal.mqtt.codec.decoder;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;

/* JADX INFO: loaded from: classes10.dex */
public class MqttDecoderContext {
    private final boolean directBufferAuth;
    private final boolean directBufferCorrelationData;
    private final boolean directBufferPayload;
    private final int maximumPacketSize;
    private final boolean problemInformationRequested;
    private final boolean responseInformationRequested;
    private final MqttTopicImpl[] topicAliasMapping;
    private final boolean validatePayloadFormat;

    MqttDecoderContext(int i, int i2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.maximumPacketSize = i;
        this.topicAliasMapping = i2 == 0 ? null : new MqttTopicImpl[i2];
        this.problemInformationRequested = z;
        this.responseInformationRequested = z2;
        this.validatePayloadFormat = z3;
        this.directBufferPayload = z4;
        this.directBufferAuth = z5;
        this.directBufferCorrelationData = z6;
    }

    public int getMaximumPacketSize() {
        return this.maximumPacketSize;
    }

    public MqttTopicImpl[] getTopicAliasMapping() {
        return this.topicAliasMapping;
    }

    public boolean isProblemInformationRequested() {
        return this.problemInformationRequested;
    }

    public boolean isResponseInformationRequested() {
        return this.responseInformationRequested;
    }

    public boolean validatePayloadFormat() {
        return this.validatePayloadFormat;
    }

    public boolean useDirectBufferPayload() {
        return this.directBufferPayload;
    }

    public boolean useDirectBufferAuth() {
        return this.directBufferAuth;
    }

    public boolean useDirectBufferCorrelationData() {
        return this.directBufferCorrelationData;
    }
}
