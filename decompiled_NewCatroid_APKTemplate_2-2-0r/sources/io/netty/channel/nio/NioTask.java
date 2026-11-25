package io.netty.channel.nio;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

/* JADX INFO: loaded from: classes11.dex */
public interface NioTask<C extends SelectableChannel> {
    void channelReady(C c, SelectionKey selectionKey) throws Exception;

    void channelUnregistered(C c, Throwable th) throws Exception;
}
