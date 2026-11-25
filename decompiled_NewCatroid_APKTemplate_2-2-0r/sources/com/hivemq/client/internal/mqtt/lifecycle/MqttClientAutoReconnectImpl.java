package com.hivemq.client.internal.mqtt.lifecycle;

import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnect;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttDisconnectSource;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientAutoReconnectImpl implements MqttClientAutoReconnect {
    public static final MqttClientAutoReconnectImpl DEFAULT;
    static final long DEFAULT_MAX_DELAY_NANOS;
    static final long DEFAULT_START_DELAY_NANOS;
    private final long initialDelayNanos;
    private final long maxDelayNanos;

    static {
        long nanos = TimeUnit.SECONDS.toNanos(1L);
        DEFAULT_START_DELAY_NANOS = nanos;
        long nanos2 = TimeUnit.SECONDS.toNanos(120L);
        DEFAULT_MAX_DELAY_NANOS = nanos2;
        DEFAULT = new MqttClientAutoReconnectImpl(nanos, nanos2);
    }

    MqttClientAutoReconnectImpl(long j, long j2) {
        this.initialDelayNanos = j;
        this.maxDelayNanos = j2;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedListener
    public void onDisconnected(MqttClientDisconnectedContext mqttClientDisconnectedContext) {
        if (mqttClientDisconnectedContext.getSource() != MqttDisconnectSource.USER) {
            com.hivemq.client.mqtt.lifecycle.MqttClientReconnector reconnector = mqttClientDisconnectedContext.getReconnector();
            long jMin = (long) Math.min(this.initialDelayNanos * Math.pow(2.0d, reconnector.getAttempts()), this.maxDelayNanos);
            reconnector.reconnect(true).delay(jMin + ((long) (((jMin / 4.0d) / 2.147483647E9d) * ((double) ThreadLocalRandom.current().nextInt()))), TimeUnit.NANOSECONDS);
        }
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnect
    public long getInitialDelay(TimeUnit timeUnit) {
        return timeUnit.convert(this.initialDelayNanos, TimeUnit.NANOSECONDS);
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnect
    public long getMaxDelay(TimeUnit timeUnit) {
        return timeUnit.convert(this.maxDelayNanos, TimeUnit.NANOSECONDS);
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnect
    public MqttClientAutoReconnectImplBuilder.Default extend() {
        return new MqttClientAutoReconnectImplBuilder.Default(this);
    }
}
