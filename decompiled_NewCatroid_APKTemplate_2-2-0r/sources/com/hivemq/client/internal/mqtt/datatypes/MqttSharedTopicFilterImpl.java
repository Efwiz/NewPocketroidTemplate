package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.util.ByteArrayUtil;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttSharedTopicFilter;

/* JADX INFO: loaded from: classes10.dex */
public class MqttSharedTopicFilterImpl extends MqttTopicFilterImpl implements MqttSharedTopicFilter {
    private static final int SHARE_PREFIX_LENGTH = MqttSharedTopicFilter.SHARE_PREFIX.length();
    private int filterByteStart;
    private int filterCharStart;

    @Override // com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl, com.hivemq.client.mqtt.datatypes.MqttTopicFilter
    public boolean isShared() {
        return true;
    }

    static boolean isShared(String str) {
        return str.startsWith(MqttSharedTopicFilter.SHARE_PREFIX);
    }

    static boolean isShared(byte[] bArr) {
        if (bArr.length < SHARE_PREFIX_LENGTH) {
            return false;
        }
        for (int i = 0; i < SHARE_PREFIX_LENGTH; i++) {
            if (bArr[i] != MqttSharedTopicFilter.SHARE_PREFIX.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    static MqttSharedTopicFilterImpl ofInternal(String str) {
        char cCharAt;
        int i = SHARE_PREFIX_LENGTH;
        while (i < str.length() && (cCharAt = str.charAt(i)) != '/') {
            if (cCharAt == '#') {
                throw new IllegalArgumentException(shareNameNoMultiLevelWildcard(getShareName(str), i));
            }
            if (cCharAt == '+') {
                throw new IllegalArgumentException(shareNameNoSingleLevelWildcard(getShareName(str), i));
            }
            i++;
        }
        if (i == SHARE_PREFIX_LENGTH) {
            throw new IllegalArgumentException("Share name must be at least one character long.");
        }
        if (i >= str.length() - 1) {
            throw new IllegalArgumentException("Topic filter must be at least one character long.");
        }
        return new MqttSharedTopicFilterImpl(str, i, validateWildcards(str, i + 1));
    }

    static MqttSharedTopicFilterImpl ofInternal(byte[] bArr) {
        int iValidateWildcards;
        byte b;
        int i = SHARE_PREFIX_LENGTH;
        while (i < bArr.length && (b = bArr[i]) != 47) {
            if (b == 35 || b == 43) {
                return null;
            }
            i++;
        }
        if (i == SHARE_PREFIX_LENGTH || i >= bArr.length - 1 || (iValidateWildcards = validateWildcards(bArr, i + 1)) == -1) {
            return null;
        }
        return new MqttSharedTopicFilterImpl(bArr, i, iValidateWildcards);
    }

    public static MqttSharedTopicFilterImpl of(String str, String str2) {
        checkShareName(str);
        Checks.notEmpty(str2, "Topic filter");
        checkWellFormed(str2, "Topic filter");
        String strSharedTopicFilter = sharedTopicFilter(str, str2);
        checkLength(strSharedTopicFilter, "Shared topic filter");
        return new MqttSharedTopicFilterImpl(strSharedTopicFilter, shareNameCharEnd(str), validateWildcards(str2, 0));
    }

    public static MqttSharedTopicFilterImpl of(String str, MqttTopicFilterImpl mqttTopicFilterImpl) {
        checkShareName(str);
        String strSharedTopicFilter = sharedTopicFilter(str, mqttTopicFilterImpl.getTopicFilterString());
        checkLength(strSharedTopicFilter, "Shared topic filter");
        return new MqttSharedTopicFilterImpl(strSharedTopicFilter, shareNameCharEnd(str), mqttTopicFilterImpl.wildcardFlags);
    }

    private static void checkShareName(String str) {
        Checks.notEmpty(str, "Share name");
        checkWellFormed(str, "Share name");
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '#') {
                throw new IllegalArgumentException(shareNameNoMultiLevelWildcard(str, i));
            }
            if (cCharAt == '+') {
                throw new IllegalArgumentException(shareNameNoSingleLevelWildcard(str, i));
            }
            if (cCharAt == '/') {
                throw new IllegalArgumentException("Share name [" + str + "] must not contain topic level separator (/), found at index: " + i + ".");
            }
        }
    }

    private static String getShareName(String str) {
        int i = SHARE_PREFIX_LENGTH;
        int iIndexOf = str.indexOf(47, i);
        if (iIndexOf == -1) {
            iIndexOf = str.length();
        }
        return str.substring(i, iIndexOf);
    }

    private static String shareNameNoMultiLevelWildcard(String str, int i) {
        return "Share name [" + str + "] must not contain multi level wildcard (#), found at index " + i + ".";
    }

    private static String shareNameNoSingleLevelWildcard(String str, int i) {
        return "Share name [" + str + "] must not contain single level wildcard (+), found at index " + i + ".";
    }

    private static String sharedTopicFilter(String str, String str2) {
        return MqttSharedTopicFilter.SHARE_PREFIX + str + '/' + str2;
    }

    private static int shareNameCharEnd(String str) {
        return SHARE_PREFIX_LENGTH + str.length();
    }

    private MqttSharedTopicFilterImpl(byte[] bArr, int i, int i2) {
        super(bArr, i2);
        this.filterByteStart = i + 1;
        this.filterCharStart = -1;
    }

    private MqttSharedTopicFilterImpl(String str, int i, int i2) {
        super(str, i2);
        this.filterByteStart = -1;
        this.filterCharStart = i + 1;
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttSharedTopicFilter
    public String getShareName() {
        return toString().substring(SHARE_PREFIX_LENGTH, getFilterCharStart() - 1);
    }

    @Override // com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl
    public String getTopicFilterString() {
        return toString().substring(getFilterCharStart());
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttSharedTopicFilter
    public MqttTopicFilterImpl getTopicFilter() {
        return MqttTopicFilterImpl.of(this);
    }

    @Override // com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl
    int getFilterByteStart() {
        if (this.filterByteStart == -1) {
            this.filterByteStart = ByteArrayUtil.indexOf(toBinary(), SHARE_PREFIX_LENGTH + 1, (byte) 47) + 1;
        }
        return this.filterByteStart;
    }

    private int getFilterCharStart() {
        if (this.filterCharStart == -1) {
            this.filterCharStart = toString().indexOf(47, SHARE_PREFIX_LENGTH + 1) + 1;
        }
        return this.filterCharStart;
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttSharedTopicFilter
    public MqttTopicFilterImplBuilder.SharedDefault extendShared() {
        return new MqttTopicFilterImplBuilder.SharedDefault(this);
    }
}
