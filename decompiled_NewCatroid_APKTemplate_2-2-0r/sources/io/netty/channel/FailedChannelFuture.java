package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;

/* JADX INFO: loaded from: classes11.dex */
final class FailedChannelFuture extends CompleteChannelFuture {
    private final Throwable cause;

    @Override // io.netty.util.concurrent.Future
    public boolean isSuccess() {
        return false;
    }

    FailedChannelFuture(Channel channel, EventExecutor eventExecutor, Throwable th) {
        super(channel, eventExecutor);
        this.cause = (Throwable) ObjectUtil.checkNotNull(th, "cause");
    }

    @Override // io.netty.util.concurrent.Future
    public Throwable cause() {
        return this.cause;
    }

    @Override // io.netty.channel.CompleteChannelFuture, io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    public Future<Void> sync() throws Throwable {
        PlatformDependent.throwException(this.cause);
        return this;
    }

    @Override // io.netty.channel.CompleteChannelFuture, io.netty.util.concurrent.CompleteFuture, io.netty.util.concurrent.Future
    public Future<Void> syncUninterruptibly() throws Throwable {
        PlatformDependent.throwException(this.cause);
        return this;
    }
}
