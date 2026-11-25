package io.netty.channel;

import io.netty.util.concurrent.AbstractEventExecutor;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.SystemPropertyUtil;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SingleThreadEventLoop extends SingleThreadEventExecutor implements EventLoop {
    protected static final int DEFAULT_MAX_PENDING_TASKS = Math.max(16, SystemPropertyUtil.getInt("io.netty.eventLoop.maxPendingTasks", Integer.MAX_VALUE));
    private final Queue<Runnable> tailTasks;

    public int registeredChannels() {
        return -1;
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, ThreadFactory threadFactory, boolean z) {
        this(eventLoopGroup, threadFactory, z, DEFAULT_MAX_PENDING_TASKS, RejectedExecutionHandlers.reject());
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, Executor executor, boolean z) {
        this(eventLoopGroup, executor, z, DEFAULT_MAX_PENDING_TASKS, RejectedExecutionHandlers.reject());
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, ThreadFactory threadFactory, boolean z, int i, RejectedExecutionHandler rejectedExecutionHandler) {
        super(eventLoopGroup, threadFactory, z, i, rejectedExecutionHandler);
        this.tailTasks = newTaskQueue(i);
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, Executor executor, boolean z, int i, RejectedExecutionHandler rejectedExecutionHandler) {
        super(eventLoopGroup, executor, z, i, rejectedExecutionHandler);
        this.tailTasks = newTaskQueue(i);
    }

    protected SingleThreadEventLoop(EventLoopGroup eventLoopGroup, Executor executor, boolean z, Queue<Runnable> queue, Queue<Runnable> queue2, RejectedExecutionHandler rejectedExecutionHandler) {
        super(eventLoopGroup, executor, z, queue, rejectedExecutionHandler);
        this.tailTasks = (Queue) ObjectUtil.checkNotNull(queue2, "tailTaskQueue");
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor, io.netty.channel.EventLoop
    public EventLoopGroup parent() {
        return (EventLoopGroup) super.parent();
    }

    @Override // io.netty.util.concurrent.AbstractEventExecutor, io.netty.util.concurrent.EventExecutor, io.netty.util.concurrent.EventExecutorGroup, io.netty.channel.EventLoopGroup
    public EventLoop next() {
        return (EventLoop) super.next();
    }

    @Override // io.netty.channel.EventLoopGroup
    public ChannelFuture register(Channel channel) {
        return register(new DefaultChannelPromise(channel, this));
    }

    @Override // io.netty.channel.EventLoopGroup
    public ChannelFuture register(ChannelPromise channelPromise) {
        ObjectUtil.checkNotNull(channelPromise, "promise");
        channelPromise.channel().unsafe().register(this, channelPromise);
        return channelPromise;
    }

    @Override // io.netty.channel.EventLoopGroup
    @Deprecated
    public ChannelFuture register(Channel channel, ChannelPromise channelPromise) {
        ObjectUtil.checkNotNull(channelPromise, "promise");
        ObjectUtil.checkNotNull(channel, "channel");
        channel.unsafe().register(this, channelPromise);
        return channelPromise;
    }

    public final void executeAfterEventLoopIteration(Runnable runnable) {
        ObjectUtil.checkNotNull(runnable, "task");
        if (isShutdown()) {
            reject();
        }
        if (!this.tailTasks.offer(runnable)) {
            reject(runnable);
        }
        if ((runnable instanceof AbstractEventExecutor.LazyRunnable) || !wakesUpForTask(runnable)) {
            return;
        }
        wakeup(inEventLoop());
    }

    final boolean removeAfterEventLoopIterationTask(Runnable runnable) {
        return this.tailTasks.remove(ObjectUtil.checkNotNull(runnable, "task"));
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected void afterRunningAllTasks() {
        runAllTasksFrom(this.tailTasks);
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected boolean hasTasks() {
        return super.hasTasks() || !this.tailTasks.isEmpty();
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    public int pendingTasks() {
        return super.pendingTasks() + this.tailTasks.size();
    }
}
