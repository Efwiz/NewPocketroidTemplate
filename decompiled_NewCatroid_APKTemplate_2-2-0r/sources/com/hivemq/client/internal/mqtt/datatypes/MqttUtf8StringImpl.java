package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.Utf8Util;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/* JADX INFO: loaded from: classes10.dex */
public class MqttUtf8StringImpl implements MqttUtf8String {
    public static final MqttUtf8StringImpl PROTOCOL_NAME = new MqttUtf8StringImpl("MQTT".getBytes(StandardCharsets.UTF_8));
    private byte[] binary;
    private int conversions;
    private String string;

    public static MqttUtf8StringImpl of(String str) {
        return of(str, "UTF-8 encoded string");
    }

    public static MqttUtf8StringImpl of(String str, String str2) {
        Checks.notNull(str, str2);
        checkLength(str, str2);
        checkWellFormed(str, str2);
        return new MqttUtf8StringImpl(str);
    }

    public static MqttUtf8StringImpl of(byte[] bArr) {
        if (!MqttBinaryData.isInRange(bArr) || isWellFormed(bArr)) {
            return null;
        }
        return new MqttUtf8StringImpl(bArr);
    }

    public static MqttUtf8StringImpl decode(ByteBuf byteBuf) {
        byte[] bArrDecode = MqttBinaryData.decode(byteBuf);
        if (bArrDecode == null) {
            return null;
        }
        return of(bArrDecode);
    }

    static boolean isWellFormed(byte[] bArr) {
        if (Utf8Util.isWellFormed(bArr) != 0) {
            return true;
        }
        for (byte b : bArr) {
            if (b == 0) {
                return true;
            }
        }
        return false;
    }

    static void checkWellFormed(String str, String str2) {
        boolean zIsHighSurrogate = false;
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == 0) {
                throw new IllegalArgumentException(str2 + " [" + str + "] must not contain null character (U+0000), found at index " + i + ".");
            }
            if (zIsHighSurrogate != Character.isLowSurrogate(cCharAt)) {
                throw new IllegalArgumentException(str2 + " [" + str + "] must not contain unmatched UTF-16 surrogate, found at index " + i + ".");
            }
            zIsHighSurrogate = Character.isHighSurrogate(cCharAt);
        }
        if (zIsHighSurrogate) {
            throw new IllegalArgumentException(str2 + " [" + str + "] must not contain unmatched UTF-16 surrogate, found at index " + (str.length() - 1) + ".");
        }
    }

    static void checkLength(String str, String str2) {
        int iEncodedLength;
        if (str.length() * 3 > 65535 && (iEncodedLength = Utf8Util.encodedLength(str)) > 65535) {
            throw new IllegalArgumentException(str2 + " [" + str.substring(0, 10) + "...] must not be longer than 65535 bytes, but was " + iEncodedLength + " bytes.");
        }
    }

    MqttUtf8StringImpl(byte[] bArr) {
        this.binary = bArr;
    }

    MqttUtf8StringImpl(String str) {
        this.string = str;
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttUtf8String
    public boolean containsShouldNotCharacters() {
        String string = toString();
        boolean z = false;
        for (int i = 0; i < string.length(); i++) {
            char cCharAt = string.charAt(i);
            if (Character.isISOControl(cCharAt)) {
                return true;
            }
            if (z) {
                if ((cCharAt & 57342) == 57342) {
                    return true;
                }
                z = false;
            } else if (cCharAt < 55359) {
                continue;
            } else {
                if ((cCharAt >= 64976 && cCharAt <= 65007) || (cCharAt & 65534) == 65534) {
                    return true;
                }
                if (cCharAt == 55359 || cCharAt == 55423 || cCharAt == 55487 || cCharAt == 55551 || cCharAt == 55615 || cCharAt == 55679 || cCharAt == 55743 || cCharAt == 55807 || cCharAt == 55871 || cCharAt == 55935 || cCharAt == 55999 || cCharAt == 56063 || cCharAt == 56127 || cCharAt == 56191 || cCharAt == 56255 || cCharAt == 56319) {
                    z = true;
                }
            }
        }
        return false;
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttUtf8String
    public ByteBuffer toByteBuffer() {
        return ByteBuffer.wrap(toBinary()).asReadOnlyBuffer();
    }

    byte[] toBinary() {
        byte[] bytes = this.binary;
        if (bytes == null) {
            String str = this.string;
            if (str == null) {
                return toBinary();
            }
            bytes = str.getBytes(StandardCharsets.UTF_8);
            this.binary = bytes;
            int i = this.conversions + 1;
            this.conversions = i;
            if (i < 3) {
                this.string = null;
            }
        }
        return bytes;
    }

    public String toString() {
        String str = this.string;
        if (str != null) {
            return str;
        }
        byte[] bArr = this.binary;
        if (bArr == null) {
            return toString();
        }
        String str2 = new String(bArr, StandardCharsets.UTF_8);
        this.string = str2;
        int i = this.conversions + 1;
        this.conversions = i;
        if (i < 3) {
            this.binary = null;
        }
        return str2;
    }

    public void encode(ByteBuf byteBuf) {
        MqttBinaryData.encode(toBinary(), byteBuf);
    }

    public int encodedLength() {
        return MqttBinaryData.encodedLength(toBinary());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttUtf8StringImpl)) {
            return false;
        }
        MqttUtf8StringImpl mqttUtf8StringImpl = (MqttUtf8StringImpl) obj;
        String str = this.string;
        String str2 = mqttUtf8StringImpl.string;
        if (str != null && str2 != null) {
            return str.equals(str2);
        }
        byte[] bArr = this.binary;
        byte[] bArr2 = mqttUtf8StringImpl.binary;
        if (bArr != null && bArr2 != null) {
            return Arrays.equals(bArr, bArr2);
        }
        return toString().equals(mqttUtf8StringImpl.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(MqttUtf8String mqttUtf8String) {
        return toString().compareTo(mqttUtf8String.toString());
    }
}
