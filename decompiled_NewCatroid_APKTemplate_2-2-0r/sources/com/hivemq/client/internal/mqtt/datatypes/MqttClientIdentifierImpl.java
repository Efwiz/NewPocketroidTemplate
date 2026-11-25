package com.hivemq.client.internal.mqtt.datatypes;

import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttClientIdentifier;
import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientIdentifierImpl extends MqttUtf8StringImpl implements MqttClientIdentifier {
    private static final int MUST_BE_ALLOWED_BY_SERVER_MAX_BYTES = 23;
    private static final int MUST_BE_ALLOWED_BY_SERVER_MIN_BYTES = 1;
    public static final MqttClientIdentifierImpl REQUEST_CLIENT_IDENTIFIER_FROM_SERVER = new MqttClientIdentifierImpl(new byte[0]);

    public static MqttClientIdentifierImpl of(String str) {
        Checks.notNull(str, "Client identifier");
        checkLength(str, "Client identifier");
        checkWellFormed(str, "Client identifier");
        return new MqttClientIdentifierImpl(str);
    }

    public static MqttClientIdentifierImpl of(byte[] bArr) {
        if (!MqttBinaryData.isInRange(bArr) || isWellFormed(bArr)) {
            return null;
        }
        return new MqttClientIdentifierImpl(bArr);
    }

    public static MqttClientIdentifierImpl decode(ByteBuf byteBuf) {
        byte[] bArrDecode = MqttBinaryData.decode(byteBuf);
        if (bArrDecode == null) {
            return null;
        }
        return of(bArrDecode);
    }

    private MqttClientIdentifierImpl(byte[] bArr) {
        super(bArr);
    }

    private MqttClientIdentifierImpl(String str) {
        super(str);
    }

    @Override // com.hivemq.client.mqtt.datatypes.MqttClientIdentifier
    public boolean mustBeAllowedByServer() {
        byte[] binary = toBinary();
        int length = binary.length;
        if (length < 1 || length > 23) {
            return false;
        }
        for (byte b : binary) {
            if ((b < 97 || b > 122) && ((b < 65 || b > 90) && (b < 48 || b > 57))) {
                return false;
            }
        }
        return true;
    }
}
