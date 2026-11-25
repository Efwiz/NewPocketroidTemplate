package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.util.ByteArrayUtil;
import java.util.Arrays;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes10.dex */
public class MqttTopicIterator extends MqttTopicLevel {
    private final int allEnd;
    private int end;
    private int start;

    public static MqttTopicIterator of(MqttTopicImpl mqttTopicImpl) {
        byte[] binary = mqttTopicImpl.toBinary();
        return new MqttTopicIterator(binary, -1, -1, binary.length);
    }

    public static MqttTopicIterator of(MqttTopicFilterImpl mqttTopicFilterImpl) {
        byte[] binary = mqttTopicFilterImpl.toBinary();
        int filterByteStart = mqttTopicFilterImpl.getFilterByteStart() - 1;
        return new MqttTopicIterator(binary, filterByteStart, filterByteStart, mqttTopicFilterImpl.containsMultiLevelWildcard() ? binary.length - 2 : binary.length);
    }

    private MqttTopicIterator(byte[] bArr, int i, int i2, int i3) {
        super(bArr);
        this.start = i;
        this.end = i2;
        this.allEnd = i3;
    }

    @Override // com.hivemq.client.internal.util.ByteArray
    protected int getStart() {
        return this.start;
    }

    @Override // com.hivemq.client.internal.util.ByteArray
    protected int getEnd() {
        return this.end;
    }

    public boolean hasNext() {
        return this.end != this.allEnd;
    }

    public boolean hasMultiLevelWildcard() {
        return this.allEnd != this.array.length;
    }

    public MqttTopicIterator fork() {
        return new MqttTopicIterator(this.array, this.start, this.end, this.allEnd);
    }

    public MqttTopicLevel next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        this.start = this.end + 1;
        this.end = ByteArrayUtil.indexOf(this.array, this.start, (byte) 47);
        return this;
    }

    @Override // com.hivemq.client.internal.mqtt.datatypes.MqttTopicLevel
    public MqttTopicLevel trim() {
        if (!hasNext()) {
            return MqttTopicLevel.of(this.array, this.start, this.end);
        }
        int i = this.start;
        int i2 = this.end;
        int i3 = this.allEnd;
        this.end = i3;
        this.start = i3;
        return new MqttTopicLevels(Arrays.copyOfRange(this.array, i, this.allEnd), i2 - i);
    }

    public boolean forwardIfEqual(MqttTopicLevels mqttTopicLevels) {
        byte[] array = mqttTopicLevels.getArray();
        int end = mqttTopicLevels.getEnd();
        int length = (this.end + array.length) - end;
        int i = this.allEnd;
        if (length > i) {
            return false;
        }
        if ((length != i && this.array[length] != 47) || !ByteArrayUtil.equals(this.array, this.end + 1, length, array, end + 1, array.length)) {
            return false;
        }
        this.end = length;
        this.start = length;
        return true;
    }

    public int forwardWhileEqual(MqttTopicLevels mqttTopicLevels) {
        boolean z;
        boolean z2;
        if (!hasNext()) {
            return mqttTopicLevels.getEnd();
        }
        int i = this.end;
        int end = mqttTopicLevels.getEnd();
        int i2 = i + 1;
        int i3 = end + 1;
        byte[] array = mqttTopicLevels.getArray();
        while (true) {
            z = i2 == this.allEnd;
            z2 = i3 == array.length;
            if (z2 || z) {
                break;
            }
            byte b = array[i3];
            if (this.array[i2] == b) {
                if (b == 47) {
                    i = i2;
                    end = i3;
                }
                i2++;
                i3++;
            }
            this.end = i;
            this.start = i;
            return end;
        }
        if ((z2 || array[i3] == 47) && (z || this.array[i2] == 47)) {
            i = i2;
            end = i3;
        }
        this.end = i;
        this.start = i;
        return end;
    }

    public boolean forwardIfMatch(MqttTopicLevels mqttTopicLevels) {
        if (!hasNext()) {
            return false;
        }
        int i = this.end + 1;
        int end = mqttTopicLevels.getEnd() + 1;
        byte[] array = mqttTopicLevels.getArray();
        while (true) {
            boolean z = i == this.allEnd;
            if (end == array.length) {
                if (!z && this.array[i] != 47) {
                    return false;
                }
                this.end = i;
                this.start = i;
                return true;
            }
            if (z) {
                return false;
            }
            byte b = array[end];
            if (this.array[i] == b) {
                i++;
            } else {
                if (b != 43) {
                    return false;
                }
                while (i < this.allEnd && this.array[i] != 47) {
                    i++;
                }
            }
            end++;
        }
    }
}
