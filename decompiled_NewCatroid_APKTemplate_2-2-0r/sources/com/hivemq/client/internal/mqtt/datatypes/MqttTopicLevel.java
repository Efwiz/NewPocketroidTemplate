package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.util.ByteArray;
import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public class MqttTopicLevel extends ByteArray {
    private static final MqttTopicLevel SINGLE_LEVEL_WILDCARD = new MqttTopicLevel(new byte[]{43});

    public MqttTopicLevel trim() {
        return this;
    }

    static MqttTopicLevel of(byte[] bArr, int i, int i2) {
        if (isSingleLevelWildcard(bArr, i, i2)) {
            return SINGLE_LEVEL_WILDCARD;
        }
        return new MqttTopicLevel(Arrays.copyOfRange(bArr, i, i2));
    }

    private static boolean isSingleLevelWildcard(byte[] bArr, int i, int i2) {
        return i2 - i == 1 && bArr[i] == 43;
    }

    MqttTopicLevel(byte[] bArr) {
        super(bArr);
    }

    byte[] getArray() {
        return this.array;
    }

    public boolean isSingleLevelWildcard() {
        return isSingleLevelWildcard(this.array, getStart(), getEnd());
    }

    public static MqttTopicFilterImpl toFilter(byte[] bArr, MqttTopicLevel mqttTopicLevel, boolean z) {
        int length;
        int length2 = bArr != null ? bArr.length + 1 : 0;
        if (mqttTopicLevel != null) {
            length2 += mqttTopicLevel.array.length;
        }
        if (z) {
            if (mqttTopicLevel != null) {
                length2++;
            }
            length2++;
        }
        byte[] bArr2 = new byte[length2];
        if (bArr != null) {
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            int length3 = bArr.length;
            bArr2[length3] = 47;
            length = length3 + 1;
        } else {
            length = 0;
        }
        if (mqttTopicLevel != null) {
            System.arraycopy(mqttTopicLevel.array, 0, bArr2, length, mqttTopicLevel.array.length);
            length += mqttTopicLevel.array.length;
        }
        if (z) {
            if (mqttTopicLevel != null) {
                bArr2[length] = 47;
                length++;
            }
            bArr2[length] = 35;
        }
        return MqttTopicFilterImpl.of(bArr2);
    }
}
