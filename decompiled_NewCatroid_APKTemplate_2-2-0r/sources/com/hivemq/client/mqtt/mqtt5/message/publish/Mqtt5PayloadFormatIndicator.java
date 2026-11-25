package com.hivemq.client.mqtt.mqtt5.message.publish;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5PayloadFormatIndicator {
    UNSPECIFIED,
    UTF_8;

    public int getCode() {
        return ordinal();
    }

    public static Mqtt5PayloadFormatIndicator fromCode(int i) {
        Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator = UNSPECIFIED;
        if (i == mqtt5PayloadFormatIndicator.getCode()) {
            return mqtt5PayloadFormatIndicator;
        }
        Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator2 = UTF_8;
        if (i == mqtt5PayloadFormatIndicator2.getCode()) {
            return mqtt5PayloadFormatIndicator2;
        }
        return null;
    }
}
