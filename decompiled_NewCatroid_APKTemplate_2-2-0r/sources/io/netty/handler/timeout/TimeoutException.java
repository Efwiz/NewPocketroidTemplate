package io.netty.handler.timeout;

import io.netty.channel.ChannelException;

/* JADX INFO: loaded from: classes11.dex */
public class TimeoutException extends ChannelException {
    private static final long serialVersionUID = 4673641882869672533L;

    @Override // java.lang.Throwable
    public Throwable fillInStackTrace() {
        return this;
    }

    TimeoutException() {
    }

    TimeoutException(boolean z) {
        super(null, null, z);
    }
}
