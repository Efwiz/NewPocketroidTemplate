package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: classes10.dex */
public class MqttTopicImpl extends MqttUtf8StringImpl implements MqttTopic {
    public static MqttTopicImpl of(String str) {
        return of(str, "Topic");
    }

    public static MqttTopicImpl of(String str, String str2) {
        Checks.notEmpty(str, str2);
        checkLength(str, str2);
        checkWellFormed(str, str2);
        return new MqttTopicImpl(str);
    }

    public static MqttTopicImpl of(byte[] bArr) {
        if (bArr.length == 0 || !MqttBinaryData.isInRange(bArr) || isWellFormed(bArr)) {
            return null;
        }
        return new MqttTopicImpl(bArr);
    }

    public static MqttTopicImpl decode(ByteBuf byteBuf) {
        byte[] bArrDecode = MqttBinaryData.decode(byteBuf);
        if (bArrDecode == null) {
            return null;
        }
        return of(bArrDecode);
    }

    static boolean isWellFormed(byte[] bArr) {
        return MqttUtf8StringImpl.isWellFormed(bArr) || containsWildcardCharacters(bArr);
    }

    static void checkWellFormed(String str, String str2) {
        MqttUtf8StringImpl.checkWellFormed(str, str2);
        checkNoWildcardCharacters(str, str2);
    }

    private static boolean containsWildcardCharacters(byte[] bArr) {
        for (byte b : bArr) {
            if (b == 35 || b == 43) {
                return true;
            }
        }
        return false;
    }

    private static void checkNoWildcardCharacters(String str, String str2) {
        int iIndexOf = str.indexOf(35);
        if (iIndexOf != -1) {
            throw new IllegalArgumentException(str2 + " [" + str + "] must not contain multi level wildcard (#), found at index " + iIndexOf + ".");
        }
        int iIndexOf2 = str.indexOf(43);
        if (iIndexOf2 != -1) {
            throw new IllegalArgumentException(str2 + " [" + str + "] must not contain single level wildcard (+), found at index " + iIndexOf2 + ".");
        }
    }

    static ImmutableList<String> splitLevels(String str) {
        ImmutableList.Builder builder = ImmutableList.builder();
        int i = 0;
        while (true) {
            int iIndexOf = str.indexOf(47, i);
            if (iIndexOf == -1) {
                builder.add(str.substring(i));
                return builder.build();
            }
            builder.add(str.substring(i, iIndexOf));
            i = iIndexOf + 1;
        }
    }

    private MqttTopicImpl(byte[] bArr) {
        super(bArr);
    }

    private MqttTopicImpl(String str) {
        super(str);
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttTopic
    public ImmutableList<String> getLevels() {
        return splitLevels(toString());
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttTopic
    public MqttTopicFilterImpl filter() {
        return MqttTopicFilterImpl.of(this);
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttTopic
    public MqttTopicImplBuilder.Default extend() {
        return new MqttTopicImplBuilder.Default(this);
    }
}
