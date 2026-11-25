package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.util.ByteArrayUtil;
import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public class MqttTopicLevels extends MqttTopicLevel {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final int firstEnd;

    public static MqttTopicLevels concat(MqttTopicLevel mqttTopicLevel, MqttTopicLevel mqttTopicLevel2) {
        byte[] array = mqttTopicLevel.trim().getArray();
        byte[] array2 = mqttTopicLevel2.trim().getArray();
        byte[] bArr = new byte[array.length + 1 + array2.length];
        System.arraycopy(array, 0, bArr, 0, array.length);
        bArr[array.length] = 47;
        System.arraycopy(array2, 0, bArr, array.length + 1, array2.length);
        return new MqttTopicLevels(bArr, mqttTopicLevel.length());
    }

    MqttTopicLevels(byte[] bArr, int i) {
        super(bArr);
        this.firstEnd = i;
    }

    @Override // com.hivemq.client.internal.util.ByteArray
    protected int getEnd() {
        return this.firstEnd;
    }

    public MqttTopicLevel before(int i) {
        if (i == this.array.length) {
            return this;
        }
        if (i == this.firstEnd) {
            return MqttTopicLevel.of(this.array, 0, this.firstEnd);
        }
        return new MqttTopicLevels(Arrays.copyOfRange(this.array, 0, i), this.firstEnd);
    }

    public MqttTopicLevel after(int i) {
        int i2 = i + 1;
        int iIndexOf = ByteArrayUtil.indexOf(this.array, i2, (byte) 47);
        if (iIndexOf == this.array.length) {
            return MqttTopicLevel.of(this.array, i2, this.array.length);
        }
        return new MqttTopicLevels(Arrays.copyOfRange(this.array, i2, this.array.length), iIndexOf - i2);
    }
}
