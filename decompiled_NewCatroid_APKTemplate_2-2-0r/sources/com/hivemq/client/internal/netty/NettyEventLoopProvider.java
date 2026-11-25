package com.hivemq.client.internal.netty;

import com.hivemq.client.internal.logging.InternalLogger;
import com.hivemq.client.internal.logging.InternalLoggerFactory;
import com.hivemq.client.internal.util.ClassUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFactory;
import io.netty.channel.EventLoop;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/* JADX INFO: loaded from: classes10.dex */
public class NettyEventLoopProvider {
    public static final NettyEventLoopProvider INSTANCE;
    private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(NettyEventLoopProvider.class);
    private final ChannelFactory<?> channelFactory;
    private final Map<Executor, Entry> entries;
    private final BiFunction<Integer, Executor, MultithreadEventLoopGroup> eventLoopGroupFactory;

    /* JADX INFO: renamed from: $r8$lambda$Ad7O8y8zLYvHeeC2CWx-vE6mB5c, reason: not valid java name */
    public static /* synthetic */ NioEventLoopGroup m2723$r8$lambda$Ad7O8y8zLYvHeeC2CWxvE6mB5c(int i, Executor executor) {
        return new NioEventLoopGroup(i, executor);
    }

    /* JADX INFO: renamed from: $r8$lambda$s3b65oOK7nPJjhAnp_I8-7b6NSs, reason: not valid java name */
    public static /* synthetic */ NioSocketChannel m2724$r8$lambda$s3b65oOK7nPJjhAnp_I87b6NSs() {
        return new NioSocketChannel();
    }

    static {
        if (!ClassUtil.isAvailable("io.netty.channel.epoll.Epoll")) {
            INSTANCE = nioEventLoopProvider();
        } else {
            INSTANCE = EpollHolder.eventLoopProvider();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static NettyEventLoopProvider nioEventLoopProvider() {
        return new NettyEventLoopProvider(new BiFunction() { // from class: com.hivemq.client.internal.netty.NettyEventLoopProvider$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return NettyEventLoopProvider.m2723$r8$lambda$Ad7O8y8zLYvHeeC2CWxvE6mB5c(((Integer) obj).intValue(), (Executor) obj2);
            }
        }, new ChannelFactory() { // from class: com.hivemq.client.internal.netty.NettyEventLoopProvider$$ExternalSyntheticLambda1
            @Override // io.netty.channel.ChannelFactory, io.netty.bootstrap.ChannelFactory
            public final Channel newChannel() {
                return NettyEventLoopProvider.m2724$r8$lambda$s3b65oOK7nPJjhAnp_I87b6NSs();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class EpollHolder {
        public static /* synthetic */ EpollSocketChannel $r8$lambda$B9zOnUcJHC08WQFE3NqbkJENZs8() {
            return new EpollSocketChannel();
        }

        public static /* synthetic */ EpollEventLoopGroup $r8$lambda$OLp0XNqFsYlc7M9dk2wztbeAJ4M(int i, Executor executor) {
            return new EpollEventLoopGroup(i, executor);
        }

        private EpollHolder() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static NettyEventLoopProvider eventLoopProvider() {
            if (!Epoll.isAvailable()) {
                return NettyEventLoopProvider.nioEventLoopProvider();
            }
            return new NettyEventLoopProvider(new BiFunction() { // from class: com.hivemq.client.internal.netty.NettyEventLoopProvider$EpollHolder$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return NettyEventLoopProvider.EpollHolder.$r8$lambda$OLp0XNqFsYlc7M9dk2wztbeAJ4M(((Integer) obj).intValue(), (Executor) obj2);
                }
            }, new ChannelFactory() { // from class: com.hivemq.client.internal.netty.NettyEventLoopProvider$EpollHolder$$ExternalSyntheticLambda1
                @Override // io.netty.channel.ChannelFactory, io.netty.bootstrap.ChannelFactory
                public final Channel newChannel() {
                    return NettyEventLoopProvider.EpollHolder.$r8$lambda$B9zOnUcJHC08WQFE3NqbkJENZs8();
                }
            });
        }
    }

    private NettyEventLoopProvider(BiFunction<Integer, Executor, MultithreadEventLoopGroup> biFunction, ChannelFactory<?> channelFactory) {
        this.entries = new HashMap();
        this.eventLoopGroupFactory = biFunction;
        this.channelFactory = channelFactory;
    }

    public synchronized EventLoop acquireEventLoop(Executor executor, int i) {
        Entry entry;
        MultithreadEventLoopGroup multithreadEventLoopGroupApply;
        entry = this.entries.get(executor);
        if (entry == null) {
            if (executor == null) {
                multithreadEventLoopGroupApply = this.eventLoopGroupFactory.apply(Integer.valueOf(i), new ThreadPerTaskExecutor(new DefaultThreadFactory("com.hivemq.client.mqtt", 10)));
            } else if (executor instanceof MultithreadEventLoopGroup) {
                MultithreadEventLoopGroup multithreadEventLoopGroup = (MultithreadEventLoopGroup) executor;
                if (i != 0 && multithreadEventLoopGroup.executorCount() != i) {
                    LOGGER.warn("Tried to use a different amount of Netty threads for the provided event loop. Using {} threads instead of {}", Integer.valueOf(multithreadEventLoopGroup.executorCount()), Integer.valueOf(i));
                }
                multithreadEventLoopGroupApply = multithreadEventLoopGroup;
            } else {
                multithreadEventLoopGroupApply = this.eventLoopGroupFactory.apply(Integer.valueOf(i), executor);
            }
            entry = new Entry(multithreadEventLoopGroupApply);
            this.entries.put(executor, entry);
        } else {
            if (i != 0 && entry.eventLoopGroup.executorCount() != i) {
                LOGGER.warn("Tried to use a different amount of Netty threads for the same executor. Using {} threads instead of {}", Integer.valueOf(entry.eventLoopGroup.executorCount()), Integer.valueOf(i));
            }
            entry.referenceCount++;
        }
        return entry.eventLoopGroup.next();
    }

    public synchronized void releaseEventLoop(Executor executor) {
        Entry entry = this.entries.get(executor);
        int i = entry.referenceCount - 1;
        entry.referenceCount = i;
        if (i == 0) {
            this.entries.remove(executor);
            if (!(executor instanceof MultithreadEventLoopGroup)) {
                entry.eventLoopGroup.shutdownGracefully(0L, 0L, TimeUnit.MILLISECONDS);
            }
        }
    }

    public ChannelFactory<?> getChannelFactory() {
        return this.channelFactory;
    }

    private static class Entry {
        final MultithreadEventLoopGroup eventLoopGroup;
        int referenceCount;

        private Entry(MultithreadEventLoopGroup multithreadEventLoopGroup) {
            this.referenceCount = 1;
            this.eventLoopGroup = multithreadEventLoopGroup;
        }
    }
}
