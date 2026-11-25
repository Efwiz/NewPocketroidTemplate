package com.hivemq.client.internal.netty;

import io.netty.channel.Channel;
import io.netty.channel.DefaultChannelPromise;

/* JADX INFO: loaded from: classes10.dex */
public class DefaultContextPromise<C> extends DefaultChannelPromise implements ContextFuture.Promise<C> {
    private final C context;

    public DefaultContextPromise(Channel channel, C c) {
        super(channel);
        this.context = c;
    }

    @Override // com.hivemq.client.internal.netty.ContextFuture
    public C getContext() {
        return this.context;
    }
}
