package com.hivemq.client.internal.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.GenericFutureListener;

/* JADX INFO: loaded from: classes10.dex */
public interface ContextFuture<C> extends ChannelFuture {

    public interface Listener<C> extends GenericFutureListener<ContextFuture<? extends C>> {
    }

    public interface Promise<C> extends ChannelPromise, ContextFuture<C> {
    }

    C getContext();
}
