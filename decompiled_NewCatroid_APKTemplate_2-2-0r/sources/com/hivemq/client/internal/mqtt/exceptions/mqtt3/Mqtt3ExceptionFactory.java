package com.hivemq.client.internal.mqtt.exceptions.mqtt3;

import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.internal.mqtt.message.connect.connack.mqtt3.Mqtt3ConnAckView;
import com.hivemq.client.internal.mqtt.message.subscribe.suback.MqttSubAck;
import com.hivemq.client.internal.mqtt.message.subscribe.suback.mqtt3.Mqtt3SubAckView;
import com.hivemq.client.mqtt.exceptions.MqttSessionExpiredException;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3ConnAckException;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3DisconnectException;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3MessageException;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3PubAckException;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3PubRecException;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3SubAckException;
import com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3UnsubAckException;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5MessageException;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import io.reactivex.functions.Function;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3ExceptionFactory {
    public static final Function<Throwable, Throwable> MAPPER = new Function() { // from class: com.hivemq.client.internal.mqtt.exceptions.mqtt3.Mqtt3ExceptionFactory$$ExternalSyntheticLambda0
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return Mqtt3ExceptionFactory.map((Throwable) obj);
        }
    };
    public static final java.util.function.Function<Throwable, Throwable> MAPPER_JAVA = new java.util.function.Function() { // from class: com.hivemq.client.internal.mqtt.exceptions.mqtt3.Mqtt3ExceptionFactory$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Mqtt3ExceptionFactory.map((Throwable) obj);
        }
    };

    public static Throwable map(Throwable th) {
        return th instanceof RuntimeException ? map((RuntimeException) th) : th;
    }

    public static RuntimeException map(RuntimeException runtimeException) {
        if (runtimeException instanceof Mqtt5MessageException) {
            return map((Mqtt5MessageException) runtimeException);
        }
        if (runtimeException instanceof MqttSessionExpiredException) {
            Throwable cause = runtimeException.getCause();
            if (cause instanceof Mqtt5MessageException) {
                return new MqttSessionExpiredException(runtimeException.getMessage(), map((Mqtt5MessageException) cause));
            }
        }
        return runtimeException;
    }

    public static RuntimeException mapWithStackTrace(RuntimeException runtimeException) {
        RuntimeException map = map(runtimeException);
        if (map != runtimeException) {
            map.setStackTrace(runtimeException.getStackTrace());
        }
        return map;
    }

    public static Mqtt3MessageException map(Mqtt5MessageException mqtt5MessageException) {
        Mqtt5Message mqttMessage = mqtt5MessageException.getMqttMessage();
        String message = mqtt5MessageException.getMessage();
        Throwable cause = mqtt5MessageException.getCause();
        switch (AnonymousClass1.$SwitchMap$com$hivemq$client$mqtt$mqtt5$message$Mqtt5MessageType[mqttMessage.getType().ordinal()]) {
            case 1:
                return new Mqtt3ConnAckException(Mqtt3ConnAckView.of((MqttConnAck) mqttMessage), message, cause);
            case 2:
                return new Mqtt3DisconnectException(message, cause);
            case 3:
                return new Mqtt3PubAckException(message, cause);
            case 4:
                return new Mqtt3PubRecException(message, cause);
            case 5:
                return new Mqtt3SubAckException(Mqtt3SubAckView.of((MqttSubAck) mqttMessage), message, cause);
            case 6:
                return new Mqtt3UnsubAckException(message, cause);
            default:
                throw new IllegalStateException();
        }
    }

    /* JADX INFO: renamed from: com.hivemq.client.internal.mqtt.exceptions.mqtt3.Mqtt3ExceptionFactory$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$Mqtt5MessageType;

        static {
            int[] iArr = new int[Mqtt5MessageType.values().length];
            $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$Mqtt5MessageType = iArr;
            try {
                iArr[Mqtt5MessageType.CONNACK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$Mqtt5MessageType[Mqtt5MessageType.DISCONNECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$Mqtt5MessageType[Mqtt5MessageType.PUBACK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$Mqtt5MessageType[Mqtt5MessageType.PUBREC.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$Mqtt5MessageType[Mqtt5MessageType.SUBACK.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$hivemq$client$mqtt$mqtt5$message$Mqtt5MessageType[Mqtt5MessageType.UNSUBACK.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private Mqtt3ExceptionFactory() {
    }
}
