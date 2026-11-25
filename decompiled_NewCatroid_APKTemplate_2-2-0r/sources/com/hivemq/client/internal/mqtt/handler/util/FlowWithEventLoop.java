package com.hivemq.client.internal.mqtt.handler.util;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import io.netty.channel.EventLoop;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes10.dex */
public abstract class FlowWithEventLoop {
    private static final int STATE_CANCELLED = 3;
    private static final int STATE_DONE = 2;
    private static final int STATE_INIT = 0;
    private static final int STATE_NOT_DONE = 1;
    private final MqttClientConfig clientConfig;
    private final AtomicInteger doneState = new AtomicInteger(0);
    protected final EventLoop eventLoop;

    protected void onCancel() {
    }

    public FlowWithEventLoop(MqttClientConfig mqttClientConfig) {
        this.clientConfig = mqttClientConfig;
        this.eventLoop = mqttClientConfig.acquireEventLoop();
    }

    public boolean init() {
        if (this.doneState.compareAndSet(0, 1)) {
            return true;
        }
        this.clientConfig.releaseEventLoop();
        return false;
    }

    protected boolean setDone() {
        if (!this.doneState.compareAndSet(1, 2)) {
            return false;
        }
        this.clientConfig.releaseEventLoop();
        return true;
    }

    public void cancel() {
        if (this.doneState.getAndSet(3) == 1) {
            onCancel();
            this.clientConfig.releaseEventLoop();
        }
    }

    public void dispose() {
        cancel();
    }

    public boolean isCancelled() {
        return this.doneState.get() == 3;
    }

    public boolean isDisposed() {
        int i = this.doneState.get();
        return i == 2 || i == 3;
    }

    public EventLoop getEventLoop() {
        return this.eventLoop;
    }
}
