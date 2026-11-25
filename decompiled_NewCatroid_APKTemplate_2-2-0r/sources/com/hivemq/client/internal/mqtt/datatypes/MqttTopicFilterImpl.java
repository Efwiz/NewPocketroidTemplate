package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import io.netty.buffer.ByteBuf;
import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public class MqttTopicFilterImpl extends MqttUtf8StringImpl implements MqttTopicFilter {
    static final int WILDCARD_CHECK_FAILURE = -1;
    private static final int WILDCARD_CHECK_STATE_BEFORE = 1;
    private static final int WILDCARD_CHECK_STATE_MULTI_LEVEL = 2;
    private static final int WILDCARD_CHECK_STATE_NOT_BEFORE = 0;
    private static final int WILDCARD_CHECK_STATE_SINGLE_LEVEL = 3;
    private static final int WILDCARD_FLAG_MULTI_LEVEL = 1;
    private static final int WILDCARD_FLAG_SINGLE_LEVEL = 2;
    final int wildcardFlags;

    int getFilterByteStart() {
        return 0;
    }

    public boolean isShared() {
        return false;
    }

    public static MqttTopicFilterImpl of(String str) {
        Checks.notEmpty(str, "Topic filter");
        checkLength(str, "Topic filter");
        checkWellFormed(str, "Topic filter");
        if (MqttSharedTopicFilterImpl.isShared(str)) {
            return MqttSharedTopicFilterImpl.ofInternal(str);
        }
        return new MqttTopicFilterImpl(str, validateWildcards(str, 0));
    }

    public static MqttTopicFilterImpl of(MqttTopicImpl mqttTopicImpl) {
        return new MqttTopicFilterImpl(mqttTopicImpl.toString(), 0);
    }

    public static MqttTopicFilterImpl of(MqttSharedTopicFilterImpl mqttSharedTopicFilterImpl) {
        return new MqttTopicFilterImpl(mqttSharedTopicFilterImpl.getTopicFilterString(), mqttSharedTopicFilterImpl.wildcardFlags);
    }

    public static MqttTopicFilterImpl of(byte[] bArr) {
        if (bArr.length == 0 || !MqttBinaryData.isInRange(bArr) || isWellFormed(bArr)) {
            return null;
        }
        if (MqttSharedTopicFilterImpl.isShared(bArr)) {
            return MqttSharedTopicFilterImpl.ofInternal(bArr);
        }
        int iValidateWildcards = validateWildcards(bArr, 0);
        if (iValidateWildcards == -1) {
            return null;
        }
        return new MqttTopicFilterImpl(bArr, iValidateWildcards);
    }

    public static MqttTopicFilterImpl decode(ByteBuf byteBuf) {
        byte[] bArrDecode = MqttBinaryData.decode(byteBuf);
        if (bArrDecode == null) {
            return null;
        }
        return of(bArrDecode);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0026  */
    static int validateWildcards(byte[] bArr, int i) {
        int i2 = 0;
        char c = 1;
        while (i < bArr.length) {
            byte b = bArr[i];
            if (c != 0) {
                if (c != 1) {
                    if (c != 2) {
                        if (c == 3) {
                            if (b != 47) {
                            }
                            c = 1;
                        } else {
                            continue;
                        }
                    }
                    return -1;
                }
                if (b == 35) {
                    i2 |= 1;
                    c = 2;
                } else if (b == 43) {
                    i2 |= 2;
                    c = 3;
                } else if (b != 47) {
                    c = 0;
                } else {
                    c = 1;
                }
            } else {
                if (b == 43 || b == 35) {
                    return -1;
                }
                if (b == 47) {
                    c = 1;
                }
            }
            i++;
        }
        return i2;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0074  */
    static int validateWildcards(String str, int i) {
        int i2 = 0;
        char c = 1;
        for (int i3 = i; i3 < str.length(); i3++) {
            char cCharAt = str.charAt(i3);
            if (c == 0) {
                if (cCharAt == '+' || cCharAt == '#') {
                    throw new IllegalArgumentException("Topic filter [" + str.substring(i) + "] contains misplaced wildcard characters. Wildcard (" + cCharAt + ") at index " + (i3 - i) + " must follow a topic level separator.");
                }
                if (cCharAt == '/') {
                    c = 1;
                }
            } else if (c != 1) {
                if (c == 2) {
                    throw new IllegalArgumentException("Topic filter [" + str.substring(i) + "] contains misplaced wildcard characters. Multi level wildcard (#) must be the last character.");
                }
                if (c == 3) {
                    if (cCharAt != '/') {
                        throw new IllegalArgumentException("Topic filter [" + str.substring(i) + "] contains misplaced wildcard characters. Single level wildcard (+) at index " + ((i3 - i) - 1) + " must be followed by a topic level separator.");
                    }
                    c = 1;
                } else {
                    continue;
                }
            } else if (cCharAt == '#') {
                i2 |= 1;
                c = 2;
            } else if (cCharAt == '+') {
                i2 |= 2;
                c = 3;
            } else if (cCharAt != '/') {
                c = 0;
            } else {
                c = 1;
            }
        }
        return i2;
    }

    MqttTopicFilterImpl(byte[] bArr, int i) {
        super(bArr);
        this.wildcardFlags = i;
    }

    MqttTopicFilterImpl(String str, int i) {
        super(str);
        this.wildcardFlags = i;
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilter
    public ImmutableList<String> getLevels() {
        return MqttTopicImpl.splitLevels(getTopicFilterString());
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilter
    public boolean containsWildcards() {
        return this.wildcardFlags != 0;
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilter
    public boolean containsMultiLevelWildcard() {
        return (this.wildcardFlags & 1) != 0;
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilter
    public boolean containsSingleLevelWildcard() {
        return (this.wildcardFlags & 2) != 0;
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilter
    public MqttSharedTopicFilterImpl share(String str) {
        return MqttSharedTopicFilterImpl.of(str, this);
    }

    public String getTopicFilterString() {
        return toString();
    }

    public byte[] getPrefix() {
        int filterByteStart = getFilterByteStart();
        if (filterByteStart == 0) {
            return null;
        }
        return Arrays.copyOfRange(toBinary(), 0, filterByteStart - 1);
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilter
    public boolean matches(MqttTopic mqttTopic) {
        return matches(MqttChecks.topic(mqttTopic));
    }

    public boolean matches(MqttTopicImpl mqttTopicImpl) {
        return matches(toBinary(), getFilterByteStart(), mqttTopicImpl.toBinary());
    }

    private static boolean matches(byte[] bArr, int i, byte[] bArr2) {
        int i2 = 0;
        while (i < bArr.length) {
            int i3 = i + 1;
            byte b = bArr[i];
            if (b == 35) {
                return true;
            }
            if (b == 43) {
                while (i2 < bArr2.length && bArr2[i2] != 47) {
                    i2++;
                }
            } else {
                if (i2 == bArr2.length) {
                    return b == 47 && i + 2 == bArr.length && bArr[i3] == 35;
                }
                int i4 = i2 + 1;
                if (bArr2[i2] != b) {
                    return false;
                }
                i2 = i4;
            }
            i = i3;
        }
        return i == bArr.length && i2 == bArr2.length;
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilter
    public boolean matches(MqttTopicFilter mqttTopicFilter) {
        return matches(MqttChecks.topicFilter(mqttTopicFilter));
    }

    public boolean matches(MqttTopicFilterImpl mqttTopicFilterImpl) {
        return matches(toBinary(), getFilterByteStart(), mqttTopicFilterImpl.toBinary(), mqttTopicFilterImpl.getFilterByteStart());
    }

    private static boolean matches(byte[] bArr, int i, byte[] bArr2, int i2) {
        while (i < bArr.length) {
            int i3 = i + 1;
            byte b = bArr[i];
            if (b == 35) {
                return true;
            }
            if (b == 43) {
                if (bArr2[i2] == 35) {
                    return false;
                }
                while (i2 < bArr2.length && bArr2[i2] != 47) {
                    i2++;
                }
            } else {
                if (i2 == bArr2.length) {
                    return b == 47 && i + 2 == bArr.length && bArr[i3] == 35;
                }
                int i4 = i2 + 1;
                if (bArr2[i2] != b) {
                    return false;
                }
                i2 = i4;
            }
            i = i3;
        }
        return i == bArr.length && i2 == bArr2.length;
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttTopicFilter
    public MqttTopicFilterImplBuilder.Default extend() {
        return new MqttTopicFilterImplBuilder.Default(this);
    }
}
