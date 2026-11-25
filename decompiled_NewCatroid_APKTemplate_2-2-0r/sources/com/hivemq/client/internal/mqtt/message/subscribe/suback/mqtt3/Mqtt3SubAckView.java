package com.hivemq.client.internal.mqtt.message.subscribe.suback.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.subscribe.suback.MqttSubAck;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAckReturnCode;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAckReasonCode;
import io.reactivex.functions.Function;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3SubAckView implements Mqtt3SubAck {
    public static final Function<Mqtt5SubAck, Mqtt3SubAck> MAPPER = new Function() { // from class: com.hivemq.client.internal.mqtt.message.subscribe.suback.mqtt3.Mqtt3SubAckView$$ExternalSyntheticLambda0
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return Mqtt3SubAckView.of((Mqtt5SubAck) obj);
        }
    };
    private final MqttSubAck delegate;

    public static MqttSubAck delegate(int i, ImmutableList<Mqtt3SubAckReturnCode> immutableList) {
        return new MqttSubAck(i, delegateReturnCodes(immutableList), null, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    private static ImmutableList<Mqtt5SubAckReasonCode> delegateReturnCodes(ImmutableList<Mqtt3SubAckReturnCode> immutableList) {
        ImmutableList.Builder builder = ImmutableList.builder(immutableList.size());
        for (int i = 0; i < immutableList.size(); i++) {
            builder.add(delegateReturnCode(immutableList.get(i)));
        }
        return builder.build();
    }

    private static Mqtt5SubAckReasonCode delegateReturnCode(Mqtt3SubAckReturnCode mqtt3SubAckReturnCode) {
        int i = AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$mqtt3$message$subscribe$suback$Mqtt3SubAckReturnCode[mqtt3SubAckReturnCode.ordinal()];
        if (i == 1) {
            return Mqtt5SubAckReasonCode.GRANTED_QOS_0;
        }
        if (i == 2) {
            return Mqtt5SubAckReasonCode.GRANTED_QOS_1;
        }
        if (i == 3) {
            return Mqtt5SubAckReasonCode.GRANTED_QOS_2;
        }
        if (i == 4) {
            return Mqtt5SubAckReasonCode.UNSPECIFIED_ERROR;
        }
        throw new IllegalStateException();
    }

    private static ImmutableList<Mqtt3SubAckReturnCode> viewReasonCodes(ImmutableList<Mqtt5SubAckReasonCode> immutableList) {
        ImmutableList.Builder builder = ImmutableList.builder(immutableList.size());
        for (int i = 0; i < immutableList.size(); i++) {
            builder.add(viewReasonCode(immutableList.get(i)));
        }
        return builder.build();
    }

    /* JADX INFO: renamed from: com.hivemq.client.internal.mqtt.message.subscribe.suback.mqtt3.Mqtt3SubAckView$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$subscribe$suback$Mqtt3SubAckReturnCode;
        static final /* synthetic */ int[] $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$subscribe$suback$Mqtt5SubAckReasonCode;

        static {
            int[] iArr = new int[Mqtt5SubAckReasonCode.values().length];
            $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$subscribe$suback$Mqtt5SubAckReasonCode = iArr;
            try {
                iArr[Mqtt5SubAckReasonCode.GRANTED_QOS_0.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$subscribe$suback$Mqtt5SubAckReasonCode[Mqtt5SubAckReasonCode.GRANTED_QOS_1.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$subscribe$suback$Mqtt5SubAckReasonCode[Mqtt5SubAckReasonCode.GRANTED_QOS_2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$subscribe$suback$Mqtt5SubAckReasonCode[Mqtt5SubAckReasonCode.UNSPECIFIED_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[Mqtt3SubAckReturnCode.values().length];
            $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$subscribe$suback$Mqtt3SubAckReturnCode = iArr2;
            try {
                iArr2[Mqtt3SubAckReturnCode.SUCCESS_MAXIMUM_QOS_0.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$subscribe$suback$Mqtt3SubAckReturnCode[Mqtt3SubAckReturnCode.SUCCESS_MAXIMUM_QOS_1.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$subscribe$suback$Mqtt3SubAckReturnCode[Mqtt3SubAckReturnCode.SUCCESS_MAXIMUM_QOS_2.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt3$message$subscribe$suback$Mqtt3SubAckReturnCode[Mqtt3SubAckReturnCode.FAILURE.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    private static Mqtt3SubAckReturnCode viewReasonCode(Mqtt5SubAckReasonCode mqtt5SubAckReasonCode) {
        int i = AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$mqtt5$message$subscribe$suback$Mqtt5SubAckReasonCode[mqtt5SubAckReasonCode.ordinal()];
        if (i == 1) {
            return Mqtt3SubAckReturnCode.SUCCESS_MAXIMUM_QOS_0;
        }
        if (i == 2) {
            return Mqtt3SubAckReturnCode.SUCCESS_MAXIMUM_QOS_1;
        }
        if (i == 3) {
            return Mqtt3SubAckReturnCode.SUCCESS_MAXIMUM_QOS_2;
        }
        if (i == 4) {
            return Mqtt3SubAckReturnCode.FAILURE;
        }
        throw new IllegalStateException();
    }

    public static Mqtt3SubAckView of(Mqtt5SubAck mqtt5SubAck) {
        return new Mqtt3SubAckView((MqttSubAck) mqtt5SubAck);
    }

    public static Mqtt3SubAckView of(MqttSubAck mqttSubAck) {
        return new Mqtt3SubAckView(mqttSubAck);
    }

    private Mqtt3SubAckView(MqttSubAck mqttSubAck) {
        this.delegate = mqttSubAck;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck
    public ImmutableList<Mqtt3SubAckReturnCode> getReturnCodes() {
        return viewReasonCodes(this.delegate.getReasonCodes());
    }

    public MqttSubAck getDelegate() {
        return this.delegate;
    }

    private String toAttributeString() {
        return "returnCodes=" + getReturnCodes();
    }

    public String toString() {
        return "MqttSubAck{" + toAttributeString() + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Mqtt3SubAckView) {
            return this.delegate.equals(((Mqtt3SubAckView) obj).delegate);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }
}
