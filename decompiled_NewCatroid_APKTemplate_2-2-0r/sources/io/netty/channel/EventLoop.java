package io.netty.channel;

import io.netty.util.concurrent.OrderedEventExecutor;

/* JADX INFO: loaded from: classes11.dex */
public interface EventLoop extends OrderedEventExecutor, EventLoopGroup {
    EventLoopGroup parent();
}
