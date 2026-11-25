package com.hivemq.client.internal.mqtt.message.connect.connack.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAckRestrictions;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAckReturnCode;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckReasonCode;
import io.reactivex.functions.Function;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3ConnAckView implements Mqtt3ConnAck {
    public static final Function<Mqtt5ConnAck, Mqtt3ConnAck> MAPPER = new Function() { // from class: com.hivemq.client.internal.mqtt.message.connect.connack.mqtt3.Mqtt3ConnAckView$$ExternalSyntheticLambda0
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return Mqtt3ConnAckView.of((Mqtt5ConnAck) obj);
        }
    };
    private final MqttConnAck delegate;

    public static MqttConnAck delegate(Mqtt3ConnAckReturnCode mqtt3ConnAckReturnCode, boolean z) {
        return new MqttConnAck(delegateReasonCode(mqtt3ConnAckReturnCode), z, -1L, -1, null, null, MqttConnAckRestrictions.DEFAULT, null, null, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    private static Mqtt5ConnAckReasonCode delegateReasonCode(Mqtt3ConnAckReturnCode mqtt3ConnAckReturnCode) {
        switch (AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$mqtt3$message$connect$connack$Mqtt3ConnAckReturnCode[mqtt3ConnAckReturnCode.ordinal()]) {
            case 1:
                return Mqtt5ConnAckReasonCode.SUCCESS;
            case 2:
                return Mqtt5ConnAckReasonCode.UNSUPPORTED_PROTOCOL_VERSION;
            case 3:
                return Mqtt5ConnAckReasonCode.CLIENT_IDENTIFIER_NOT_VALID;
            case 4:
                return Mqtt5ConnAckReasonCode.SERVER_UNAVAILABLE;
            case 5:
                return Mqtt5ConnAckReasonCode.BAD_USER_NAME_OR_PASSWORD;
            case 6:
                return Mqtt5ConnAckReasonCode.NOT_AUTHORIZED;
            default:
                throw new IllegalStateException();
        }
    }

    /* JADX INFO: renamed from: com.hivemq.client.internal.mqtt.message.connect.connack.mqtt3.Mqtt3ConnAckView$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$connect$connack$Mqtt3ConnAckReturnCode;
        static final /* synthetic */ int[] $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$connect$connack$Mqtt5ConnAckReasonCode;

        static {
            int[] iArr = new int[Mqtt5ConnAckReasonCode.values().length];
            $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$connect$connack$Mqtt5ConnAckReasonCode = iArr;
            try {
                iArr[Mqtt5ConnAckReasonCode.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$connect$connack$Mqtt5ConnAckReasonCode[Mqtt5ConnAckReasonCode.UNSUPPORTED_PROTOCOL_VERSION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$connect$connack$Mqtt5ConnAckReasonCode[Mqtt5ConnAckReasonCode.CLIENT_IDENTIFIER_NOT_VALID.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$connect$connack$Mqtt5ConnAckReasonCode[Mqtt5ConnAckReasonCode.SERVER_UNAVAILABLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$connect$connack$Mqtt5ConnAckReasonCode[Mqtt5ConnAckReasonCode.BAD_USER_NAME_OR_PASSWORD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$connect$connack$Mqtt5ConnAckReasonCode[Mqtt5ConnAckReasonCode.NOT_AUTHORIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[Mqtt3ConnAckReturnCode.values().length];
            $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$connect$connack$Mqtt3ConnAckReturnCode = iArr2;
            try {
                iArr2[Mqtt3ConnAckReturnCode.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$connect$connack$Mqtt3ConnAckReturnCode[Mqtt3ConnAckReturnCode.UNSUPPORTED_PROTOCOL_VERSION.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$connect$connack$Mqtt3ConnAckReturnCode[Mqtt3ConnAckReturnCode.IDENTIFIER_REJECTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$connect$connack$Mqtt3ConnAckReturnCode[Mqtt3ConnAckReturnCode.SERVER_UNAVAILABLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$connect$connack$Mqtt3ConnAckReturnCode[Mqtt3ConnAckReturnCode.BAD_USER_NAME_OR_PASSWORD.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$connect$connack$Mqtt3ConnAckReturnCode[Mqtt3ConnAckReturnCode.NOT_AUTHORIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }

    private static Mqtt3ConnAckReturnCode viewReasonCode(Mqtt5ConnAckReasonCode mqtt5ConnAckReasonCode) {
        switch (AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$mqtt5$message$connect$connack$Mqtt5ConnAckReasonCode[mqtt5ConnAckReasonCode.ordinal()]) {
            case 1:
                return Mqtt3ConnAckReturnCode.SUCCESS;
            case 2:
                return Mqtt3ConnAckReturnCode.UNSUPPORTED_PROTOCOL_VERSION;
            case 3:
                return Mqtt3ConnAckReturnCode.IDENTIFIER_REJECTED;
            case 4:
                return Mqtt3ConnAckReturnCode.SERVER_UNAVAILABLE;
            case 5:
                return Mqtt3ConnAckReturnCode.BAD_USER_NAME_OR_PASSWORD;
            case 6:
                return Mqtt3ConnAckReturnCode.NOT_AUTHORIZED;
            default:
                throw new IllegalStateException();
        }
    }

    public static Mqtt3ConnAckView of(Mqtt5ConnAck mqtt5ConnAck) {
        return new Mqtt3ConnAckView((MqttConnAck) mqtt5ConnAck);
    }

    public static Mqtt3ConnAckView of(MqttConnAck mqttConnAck) {
        return new Mqtt3ConnAckView(mqttConnAck);
    }

    private Mqtt3ConnAckView(MqttConnAck mqttConnAck) {
        this.delegate = mqttConnAck;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck
    public Mqtt3ConnAckReturnCode getReturnCode() {
        return viewReasonCode((Mqtt5ConnAckReasonCode) this.delegate.getReasonCode());
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck
    public boolean isSessionPresent() {
        return this.delegate.isSessionPresent();
    }

    public MqttConnAck getDelegate() {
        return this.delegate;
    }

    private String toAttributeString() {
        return "returnCode=" + getReturnCode() + ", sessionPresent=" + isSessionPresent();
    }

    public String toString() {
        return "MqttConnAck{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Mqtt3ConnAckView) {
            return this.delegate.equals(((Mqtt3ConnAckView) obj).delegate);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }
}
