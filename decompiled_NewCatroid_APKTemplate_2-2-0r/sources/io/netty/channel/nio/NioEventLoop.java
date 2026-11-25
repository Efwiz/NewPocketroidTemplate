package io.netty.channel.nio;

import io.netty.channel.ChannelException;
import io.netty.channel.EventLoopException;
import io.netty.channel.EventLoopTaskQueueFactory;
import io.netty.channel.SelectStrategy;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.util.IntSupplier;
import io.netty.util.concurrent.RejectedExecutionHandler;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.ReflectionUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.compress.harmony.pack200.PackingOptions;

/* JADX INFO: loaded from: classes11.dex */
public final class NioEventLoop extends SingleThreadEventLoop {
    private static final long AWAKE = -1;
    private static final int CLEANUP_INTERVAL = 256;
    private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
    private static final long NONE = Long.MAX_VALUE;
    private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
    private int cancelledKeys;
    private volatile int ioRatio;
    private boolean needsToSelectAgain;
    private final AtomicLong nextWakeupNanos;
    private final SelectorProvider provider;
    private final IntSupplier selectNowSupplier;
    private final SelectStrategy selectStrategy;
    private SelectedSelectionKeySet selectedKeys;
    private Selector selector;
    private Selector unwrappedSelector;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) NioEventLoop.class);
    private static final boolean DISABLE_KEY_SET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);

    static {
        if (SystemPropertyUtil.get("sun.nio.ch.bugLevel") == null) {
            try {
                AccessController.doPrivileged(new PrivilegedAction<Void>() { // from class: io.netty.channel.nio.NioEventLoop.2
                    @Override // java.security.PrivilegedAction
                    public Void run() {
                        System.setProperty("sun.nio.ch.bugLevel", "");
                        return null;
                    }
                });
            } catch (SecurityException e) {
                logger.debug("Unable to get/set System Property: sun.nio.ch.bugLevel", (Throwable) e);
            }
        }
        int i = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
        int i2 = i >= 3 ? i : 0;
        SELECTOR_AUTO_REBUILD_THRESHOLD = i2;
        InternalLogger internalLogger = logger;
        if (internalLogger.isDebugEnabled()) {
            internalLogger.debug("-Dio.netty.noKeySetOptimization: {}", Boolean.valueOf(DISABLE_KEY_SET_OPTIMIZATION));
            internalLogger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", Integer.valueOf(i2));
        }
    }

    NioEventLoop(NioEventLoopGroup nioEventLoopGroup, Executor executor, SelectorProvider selectorProvider, SelectStrategy selectStrategy, RejectedExecutionHandler rejectedExecutionHandler, EventLoopTaskQueueFactory eventLoopTaskQueueFactory) {
        super(nioEventLoopGroup, executor, false, newTaskQueue(eventLoopTaskQueueFactory), newTaskQueue(eventLoopTaskQueueFactory), rejectedExecutionHandler);
        this.selectNowSupplier = new IntSupplier() { // from class: io.netty.channel.nio.NioEventLoop.1
            @Override // io.netty.util.IntSupplier
            public int get() throws Exception {
                return NioEventLoop.this.selectNow();
            }
        };
        this.nextWakeupNanos = new AtomicLong(-1L);
        this.ioRatio = 50;
        this.provider = (SelectorProvider) ObjectUtil.checkNotNull(selectorProvider, "selectorProvider");
        this.selectStrategy = (SelectStrategy) ObjectUtil.checkNotNull(selectStrategy, "selectStrategy");
        SelectorTuple selectorTupleOpenSelector = openSelector();
        this.selector = selectorTupleOpenSelector.selector;
        this.unwrappedSelector = selectorTupleOpenSelector.unwrappedSelector;
    }

    private static Queue<Runnable> newTaskQueue(EventLoopTaskQueueFactory eventLoopTaskQueueFactory) {
        if (eventLoopTaskQueueFactory == null) {
            return newTaskQueue0(DEFAULT_MAX_PENDING_TASKS);
        }
        return eventLoopTaskQueueFactory.newTaskQueue(DEFAULT_MAX_PENDING_TASKS);
    }

    private static final class SelectorTuple {
        final Selector selector;
        final Selector unwrappedSelector;

        SelectorTuple(Selector selector) {
            this.unwrappedSelector = selector;
            this.selector = selector;
        }

        SelectorTuple(Selector selector, Selector selector2) {
            this.unwrappedSelector = selector;
            this.selector = selector2;
        }
    }

    private SelectorTuple openSelector() {
        try {
            final AbstractSelector abstractSelectorOpenSelector = this.provider.openSelector();
            if (DISABLE_KEY_SET_OPTIMIZATION) {
                return new SelectorTuple(abstractSelectorOpenSelector);
            }
            Object objDoPrivileged = AccessController.doPrivileged(new PrivilegedAction<Object>() { // from class: io.netty.channel.nio.NioEventLoop.3
                @Override // java.security.PrivilegedAction
                public Object run() {
                    try {
                        return Class.forName("sun.nio.ch.SelectorImpl", false, PlatformDependent.getSystemClassLoader());
                    } catch (Throwable th) {
                        return th;
                    }
                }
            });
            if (objDoPrivileged instanceof Class) {
                final Class cls = (Class) objDoPrivileged;
                if (cls.isAssignableFrom(abstractSelectorOpenSelector.getClass())) {
                    final SelectedSelectionKeySet selectedSelectionKeySet = new SelectedSelectionKeySet();
                    Object objDoPrivileged2 = AccessController.doPrivileged(new PrivilegedAction<Object>() { // from class: io.netty.channel.nio.NioEventLoop.4
                        @Override // java.security.PrivilegedAction
                        public Object run() {
                            try {
                                Field declaredField = cls.getDeclaredField("selectedKeys");
                                Field declaredField2 = cls.getDeclaredField("publicSelectedKeys");
                                if (PlatformDependent.javaVersion() >= 9 && PlatformDependent.hasUnsafe()) {
                                    long jObjectFieldOffset = PlatformDependent.objectFieldOffset(declaredField);
                                    long jObjectFieldOffset2 = PlatformDependent.objectFieldOffset(declaredField2);
                                    if (jObjectFieldOffset != -1 && jObjectFieldOffset2 != -1) {
                                        PlatformDependent.putObject(abstractSelectorOpenSelector, jObjectFieldOffset, selectedSelectionKeySet);
                                        PlatformDependent.putObject(abstractSelectorOpenSelector, jObjectFieldOffset2, selectedSelectionKeySet);
                                        return null;
                                    }
                                }
                                Throwable thTrySetAccessible = ReflectionUtil.trySetAccessible(declaredField, true);
                                if (thTrySetAccessible != null) {
                                    return thTrySetAccessible;
                                }
                                Throwable thTrySetAccessible2 = ReflectionUtil.trySetAccessible(declaredField2, true);
                                if (thTrySetAccessible2 != null) {
                                    return thTrySetAccessible2;
                                }
                                declaredField.set(abstractSelectorOpenSelector, selectedSelectionKeySet);
                                declaredField2.set(abstractSelectorOpenSelector, selectedSelectionKeySet);
                                return null;
                            } catch (IllegalAccessException e) {
                                return e;
                            } catch (NoSuchFieldException e2) {
                                return e2;
                            }
                        }
                    });
                    if (objDoPrivileged2 instanceof Exception) {
                        this.selectedKeys = null;
                        logger.trace("failed to instrument a special java.util.Set into: {}", abstractSelectorOpenSelector, (Exception) objDoPrivileged2);
                        return new SelectorTuple(abstractSelectorOpenSelector);
                    }
                    this.selectedKeys = selectedSelectionKeySet;
                    logger.trace("instrumented a special java.util.Set into: {}", abstractSelectorOpenSelector);
                    return new SelectorTuple(abstractSelectorOpenSelector, new SelectedSelectionKeySetSelector(abstractSelectorOpenSelector, selectedSelectionKeySet));
                }
            }
            if (objDoPrivileged instanceof Throwable) {
                logger.trace("failed to instrument a special java.util.Set into: {}", abstractSelectorOpenSelector, (Throwable) objDoPrivileged);
            }
            return new SelectorTuple(abstractSelectorOpenSelector);
        } catch (IOException e) {
            throw new ChannelException("failed to open a new selector", e);
        }
    }

    public SelectorProvider selectorProvider() {
        return this.provider;
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected Queue<Runnable> newTaskQueue(int i) {
        return newTaskQueue0(i);
    }

    private static Queue<Runnable> newTaskQueue0(int i) {
        if (i == Integer.MAX_VALUE) {
            return PlatformDependent.newMpscQueue();
        }
        return PlatformDependent.newMpscQueue(i);
    }

    public void register(final SelectableChannel selectableChannel, final int i, final NioTask<?> nioTask) {
        ObjectUtil.checkNotNull(selectableChannel, "ch");
        if (i == 0) {
            throw new IllegalArgumentException("interestOps must be non-zero.");
        }
        if (((~selectableChannel.validOps()) & i) != 0) {
            throw new IllegalArgumentException("invalid interestOps: " + i + "(validOps: " + selectableChannel.validOps() + ')');
        }
        ObjectUtil.checkNotNull(nioTask, "task");
        if (isShutdown()) {
            throw new IllegalStateException("event loop shut down");
        }
        if (inEventLoop()) {
            register0(selectableChannel, i, nioTask);
            return;
        }
        try {
            submit(new Runnable() { // from class: io.netty.channel.nio.NioEventLoop.5
                @Override // java.lang.Runnable
                public void run() {
                    NioEventLoop.this.register0(selectableChannel, i, nioTask);
                }
            }).sync();
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void register0(SelectableChannel selectableChannel, int i, NioTask<?> nioTask) {
        try {
            selectableChannel.register(this.unwrappedSelector, i, nioTask);
        } catch (Exception e) {
            throw new EventLoopException("failed to register a channel", e);
        }
    }

    public int getIoRatio() {
        return this.ioRatio;
    }

    public void setIoRatio(int i) {
        if (i <= 0 || i > 100) {
            throw new IllegalArgumentException("ioRatio: " + i + " (expected: 0 < ioRatio <= 100)");
        }
        this.ioRatio = i;
    }

    public void rebuildSelector() {
        if (!inEventLoop()) {
            execute(new Runnable() { // from class: io.netty.channel.nio.NioEventLoop.6
                @Override // java.lang.Runnable
                public void run() {
                    NioEventLoop.this.rebuildSelector0();
                }
            });
        } else {
            rebuildSelector0();
        }
    }

    @Override // io.netty.channel.SingleThreadEventLoop
    public int registeredChannels() {
        return this.selector.keys().size() - this.cancelledKeys;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rebuildSelector0() {
        Selector selector = this.selector;
        if (selector == null) {
            return;
        }
        try {
            SelectorTuple selectorTupleOpenSelector = openSelector();
            int i = 0;
            for (SelectionKey selectionKey : selector.keys()) {
                Object objAttachment = selectionKey.attachment();
                try {
                    if (selectionKey.isValid() && selectionKey.channel().keyFor(selectorTupleOpenSelector.unwrappedSelector) == null) {
                        int iInterestOps = selectionKey.interestOps();
                        selectionKey.cancel();
                        SelectionKey selectionKeyRegister = selectionKey.channel().register(selectorTupleOpenSelector.unwrappedSelector, iInterestOps, objAttachment);
                        if (objAttachment instanceof AbstractNioChannel) {
                            ((AbstractNioChannel) objAttachment).selectionKey = selectionKeyRegister;
                        }
                        i++;
                    }
                } catch (Exception e) {
                    logger.warn("Failed to re-register a Channel to the new Selector.", (Throwable) e);
                    if (objAttachment instanceof AbstractNioChannel) {
                        AbstractNioChannel abstractNioChannel = (AbstractNioChannel) objAttachment;
                        abstractNioChannel.unsafe().close(abstractNioChannel.unsafe().voidPromise());
                    } else {
                        invokeChannelUnregistered((NioTask) objAttachment, selectionKey, e);
                    }
                }
            }
            this.selector = selectorTupleOpenSelector.selector;
            this.unwrappedSelector = selectorTupleOpenSelector.unwrappedSelector;
            try {
                selector.close();
            } catch (Throwable th) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Failed to close the old Selector.", th);
                }
            }
            InternalLogger internalLogger = logger;
            if (internalLogger.isInfoEnabled()) {
                internalLogger.info("Migrated " + i + " channel(s) to the new Selector.");
            }
        } catch (Exception e2) {
            logger.warn("Failed to create a new Selector.", (Throwable) e2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x00fe A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:104:0x0002 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:105:0x0002 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:23:0x0047 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:30:0x0057 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:31:0x0059 A[Catch: all -> 0x00aa, CancelledKeyException -> 0x00ac, TRY_LEAVE, TryCatch #9 {CancelledKeyException -> 0x00ac, all -> 0x00aa, blocks: (B:20:0x003d, B:29:0x0052, B:41:0x008b, B:46:0x0095, B:48:0x009d, B:27:0x004e, B:28:0x0051, B:31:0x0059, B:33:0x0060, B:35:0x0071, B:36:0x007f, B:37:0x0080), top: B:94:0x003d }] */
    /* JADX WARN: Code duplicated, block: B:37:0x0080 A[Catch: all -> 0x00aa, CancelledKeyException -> 0x00ac, TryCatch #9 {CancelledKeyException -> 0x00ac, all -> 0x00aa, blocks: (B:20:0x003d, B:29:0x0052, B:41:0x008b, B:46:0x0095, B:48:0x009d, B:27:0x004e, B:28:0x0051, B:31:0x0059, B:33:0x0060, B:35:0x0071, B:36:0x007f, B:37:0x0080), top: B:94:0x003d }] */
    /* JADX WARN: Code duplicated, block: B:44:0x0092  */
    /* JADX WARN: Code duplicated, block: B:46:0x0095 A[Catch: all -> 0x00aa, CancelledKeyException -> 0x00ac, TryCatch #9 {CancelledKeyException -> 0x00ac, all -> 0x00aa, blocks: (B:20:0x003d, B:29:0x0052, B:41:0x008b, B:46:0x0095, B:48:0x009d, B:27:0x004e, B:28:0x0051, B:31:0x0059, B:33:0x0060, B:35:0x0071, B:36:0x007f, B:37:0x0080), top: B:94:0x003d }] */
    /* JADX WARN: Code duplicated, block: B:48:0x009d A[Catch: all -> 0x00aa, CancelledKeyException -> 0x00ac, TRY_LEAVE, TryCatch #9 {CancelledKeyException -> 0x00ac, all -> 0x00aa, blocks: (B:20:0x003d, B:29:0x0052, B:41:0x008b, B:46:0x0095, B:48:0x009d, B:27:0x004e, B:28:0x0051, B:31:0x0059, B:33:0x0060, B:35:0x0071, B:36:0x007f, B:37:0x0080), top: B:94:0x003d }] */
    /* JADX WARN: Code duplicated, block: B:73:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:87:0x0049 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:96:0x0107 A[SYNTHETIC] */
    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected void run() {
        int i;
        Throwable th;
        CancelledKeyException e;
        InternalLogger internalLogger;
        long jNextScheduledTaskDeadlineNanos;
        int i2;
        boolean zRunAllTasks;
        long jNanoTime;
        InternalLogger internalLogger2;
        while (true) {
            int i3 = 0;
            while (true) {
                try {
                    try {
                        try {
                            int iCalculateStrategy = this.selectStrategy.calculateStrategy(this.selectNowSupplier, hasTasks());
                            try {
                                try {
                                    if (iCalculateStrategy != -3) {
                                        if (iCalculateStrategy == -2) {
                                            continue;
                                        } else {
                                            if (iCalculateStrategy != -1) {
                                            }
                                            i = i3 + 1;
                                            this.cancelledKeys = 0;
                                            this.needsToSelectAgain = false;
                                            i2 = this.ioRatio;
                                            if (i2 == 100) {
                                                if (iCalculateStrategy > 0) {
                                                    try {
                                                        processSelectedKeys();
                                                    } catch (Throwable th2) {
                                                        runAllTasks();
                                                        throw th2;
                                                    }
                                                }
                                                zRunAllTasks = runAllTasks();
                                            } else if (iCalculateStrategy > 0) {
                                                jNanoTime = System.nanoTime();
                                                try {
                                                    processSelectedKeys();
                                                    zRunAllTasks = runAllTasks(((System.nanoTime() - jNanoTime) * ((long) (100 - i2))) / ((long) i2));
                                                } catch (Throwable th3) {
                                                    runAllTasks(((System.nanoTime() - jNanoTime) * ((long) (100 - i2))) / ((long) i2));
                                                    throw th3;
                                                }
                                            } else {
                                                zRunAllTasks = runAllTasks(0L);
                                            }
                                            if (!zRunAllTasks || iCalculateStrategy > 0) {
                                                if (i > 3) {
                                                    internalLogger2 = logger;
                                                    if (internalLogger2.isDebugEnabled()) {
                                                        internalLogger2.debug("Selector.select() returned prematurely {} times in a row for Selector {}.", Integer.valueOf(i3), this.selector);
                                                    }
                                                }
                                            } else if (!unexpectedSelectorWakeup(i)) {
                                                i3 = i;
                                                if (isShuttingDown()) {
                                                    closeAll();
                                                    if (confirmShutdown()) {
                                                        return;
                                                    }
                                                } else {
                                                    continue;
                                                }
                                            }
                                            i = 0;
                                            i3 = i;
                                            if (isShuttingDown()) {
                                                closeAll();
                                                if (confirmShutdown()) {
                                                    return;
                                                }
                                            } else {
                                                continue;
                                            }
                                        }
                                    }
                                    this.cancelledKeys = 0;
                                    this.needsToSelectAgain = false;
                                    i2 = this.ioRatio;
                                    if (i2 == 100) {
                                        if (iCalculateStrategy > 0) {
                                            processSelectedKeys();
                                        }
                                        zRunAllTasks = runAllTasks();
                                    } else if (iCalculateStrategy > 0) {
                                        jNanoTime = System.nanoTime();
                                        processSelectedKeys();
                                        zRunAllTasks = runAllTasks(((System.nanoTime() - jNanoTime) * ((long) (100 - i2))) / ((long) i2));
                                    } else {
                                        zRunAllTasks = runAllTasks(0L);
                                    }
                                    if (zRunAllTasks) {
                                        if (i > 3) {
                                            internalLogger2 = logger;
                                            if (internalLogger2.isDebugEnabled()) {
                                                internalLogger2.debug("Selector.select() returned prematurely {} times in a row for Selector {}.", Integer.valueOf(i3), this.selector);
                                            }
                                        }
                                        i = 0;
                                    } else {
                                        if (i > 3) {
                                            internalLogger2 = logger;
                                            if (internalLogger2.isDebugEnabled()) {
                                                internalLogger2.debug("Selector.select() returned prematurely {} times in a row for Selector {}.", Integer.valueOf(i3), this.selector);
                                            }
                                        }
                                        i = 0;
                                    }
                                } catch (CancelledKeyException e2) {
                                    e = e2;
                                    internalLogger = logger;
                                    if (internalLogger.isDebugEnabled()) {
                                        internalLogger.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector {} - JDK bug?", this.selector, e);
                                    }
                                } catch (Throwable th4) {
                                    th = th4;
                                    handleLoopException(th);
                                }
                                if (!hasTasks()) {
                                    iCalculateStrategy = select(jNextScheduledTaskDeadlineNanos);
                                }
                                this.nextWakeupNanos.lazySet(-1L);
                                i = i3 + 1;
                            } catch (Throwable th5) {
                                this.nextWakeupNanos.lazySet(-1L);
                                throw th5;
                            }
                            jNextScheduledTaskDeadlineNanos = nextScheduledTaskDeadlineNanos();
                            if (jNextScheduledTaskDeadlineNanos == -1) {
                                jNextScheduledTaskDeadlineNanos = Long.MAX_VALUE;
                            }
                            this.nextWakeupNanos.set(jNextScheduledTaskDeadlineNanos);
                        } catch (IOException e3) {
                            rebuildSelector0();
                            try {
                                handleLoopException(e3);
                            } catch (CancelledKeyException e4) {
                                e = e4;
                                i = 0;
                                internalLogger = logger;
                                if (internalLogger.isDebugEnabled()) {
                                    internalLogger.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector {} - JDK bug?", this.selector, e);
                                }
                                i3 = i;
                                if (isShuttingDown()) {
                                    closeAll();
                                    if (confirmShutdown()) {
                                        return;
                                    }
                                } else {
                                    continue;
                                }
                            } catch (Throwable th6) {
                                th = th6;
                                i = 0;
                                handleLoopException(th);
                                i3 = i;
                                if (isShuttingDown()) {
                                    closeAll();
                                    if (confirmShutdown()) {
                                        return;
                                    }
                                } else {
                                    continue;
                                }
                            }
                        }
                    } catch (CancelledKeyException e5) {
                        i = i3;
                        e = e5;
                    } catch (Throwable th7) {
                        i = i3;
                        th = th7;
                    }
                    if (isShuttingDown()) {
                        closeAll();
                        if (confirmShutdown()) {
                            return;
                        }
                    } else {
                        continue;
                    }
                } catch (Throwable th8) {
                    handleLoopException(th8);
                }
                i3 = i;
            }
        }
    }

    private boolean unexpectedSelectorWakeup(int i) {
        if (Thread.interrupted()) {
            InternalLogger internalLogger = logger;
            if (internalLogger.isDebugEnabled()) {
                internalLogger.debug("Selector.select() returned prematurely because Thread.currentThread().interrupt() was called. Use NioEventLoop.shutdownGracefully() to shutdown the NioEventLoop.");
            }
            return true;
        }
        int i2 = SELECTOR_AUTO_REBUILD_THRESHOLD;
        if (i2 <= 0 || i < i2) {
            return false;
        }
        logger.warn("Selector.select() returned prematurely {} times in a row; rebuilding Selector {}.", Integer.valueOf(i), this.selector);
        rebuildSelector();
        return true;
    }

    private static void handleLoopException(Throwable th) {
        logger.warn("Unexpected exception in the selector loop.", th);
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException unused) {
        }
    }

    private void processSelectedKeys() {
        if (this.selectedKeys != null) {
            processSelectedKeysOptimized();
        } else {
            processSelectedKeysPlain(this.selector.selectedKeys());
        }
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected void cleanup() {
        try {
            this.selector.close();
        } catch (IOException e) {
            logger.warn("Failed to close a selector.", (Throwable) e);
        }
    }

    void cancel(SelectionKey selectionKey) {
        selectionKey.cancel();
        int i = this.cancelledKeys + 1;
        this.cancelledKeys = i;
        if (i >= 256) {
            this.cancelledKeys = 0;
            this.needsToSelectAgain = true;
        }
    }

    private void processSelectedKeysPlain(Set<SelectionKey> set) {
        if (set.isEmpty()) {
            return;
        }
        Iterator<SelectionKey> it = set.iterator();
        while (true) {
            SelectionKey next = it.next();
            Object objAttachment = next.attachment();
            it.remove();
            if (objAttachment instanceof AbstractNioChannel) {
                processSelectedKey(next, (AbstractNioChannel) objAttachment);
            } else {
                processSelectedKey(next, (NioTask<SelectableChannel>) objAttachment);
            }
            if (!it.hasNext()) {
                return;
            }
            if (this.needsToSelectAgain) {
                selectAgain();
                Set<SelectionKey> setSelectedKeys = this.selector.selectedKeys();
                if (setSelectedKeys.isEmpty()) {
                    return;
                } else {
                    it = setSelectedKeys.iterator();
                }
            }
        }
    }

    private void processSelectedKeysOptimized() {
        int i = 0;
        while (i < this.selectedKeys.size) {
            SelectionKey selectionKey = this.selectedKeys.keys[i];
            this.selectedKeys.keys[i] = null;
            Object objAttachment = selectionKey.attachment();
            if (objAttachment instanceof AbstractNioChannel) {
                processSelectedKey(selectionKey, (AbstractNioChannel) objAttachment);
            } else {
                processSelectedKey(selectionKey, (NioTask<SelectableChannel>) objAttachment);
            }
            if (this.needsToSelectAgain) {
                this.selectedKeys.reset(i + 1);
                selectAgain();
                i = -1;
            }
            i++;
        }
    }

    private void processSelectedKey(SelectionKey selectionKey, AbstractNioChannel abstractNioChannel) {
        AbstractNioChannel.NioUnsafe nioUnsafeUnsafe = abstractNioChannel.unsafe();
        if (!selectionKey.isValid()) {
            try {
                if (abstractNioChannel.eventLoop() == this) {
                    nioUnsafeUnsafe.close(nioUnsafeUnsafe.voidPromise());
                    return;
                }
                return;
            } catch (Throwable unused) {
                return;
            }
        }
        try {
            int i = selectionKey.readyOps();
            if ((i & 8) != 0) {
                selectionKey.interestOps(selectionKey.interestOps() & (-9));
                nioUnsafeUnsafe.finishConnect();
            }
            if ((i & 4) != 0) {
                abstractNioChannel.unsafe().forceFlush();
            }
            if ((i & 17) != 0 || i == 0) {
                nioUnsafeUnsafe.read();
            }
        } catch (CancelledKeyException unused2) {
            nioUnsafeUnsafe.close(nioUnsafeUnsafe.voidPromise());
        }
    }

    private static void processSelectedKey(SelectionKey selectionKey, NioTask<SelectableChannel> nioTask) {
        try {
            try {
                nioTask.channelReady(selectionKey.channel(), selectionKey);
                if (!selectionKey.isValid()) {
                    invokeChannelUnregistered(nioTask, selectionKey, null);
                }
            } catch (Exception e) {
                selectionKey.cancel();
                invokeChannelUnregistered(nioTask, selectionKey, e);
            }
        } catch (Throwable th) {
            selectionKey.cancel();
            invokeChannelUnregistered(nioTask, selectionKey, null);
            throw th;
        }
    }

    private void closeAll() {
        selectAgain();
        Set<SelectionKey> setKeys = this.selector.keys();
        ArrayList<AbstractNioChannel> arrayList = new ArrayList(setKeys.size());
        for (SelectionKey selectionKey : setKeys) {
            Object objAttachment = selectionKey.attachment();
            if (objAttachment instanceof AbstractNioChannel) {
                arrayList.add((AbstractNioChannel) objAttachment);
            } else {
                selectionKey.cancel();
                invokeChannelUnregistered((NioTask) objAttachment, selectionKey, null);
            }
        }
        for (AbstractNioChannel abstractNioChannel : arrayList) {
            abstractNioChannel.unsafe().close(abstractNioChannel.unsafe().voidPromise());
        }
    }

    private static void invokeChannelUnregistered(NioTask<SelectableChannel> nioTask, SelectionKey selectionKey, Throwable th) {
        try {
            nioTask.channelUnregistered(selectionKey.channel(), th);
        } catch (Exception e) {
            logger.warn("Unexpected exception while running NioTask.channelUnregistered()", (Throwable) e);
        }
    }

    @Override // io.netty.util.concurrent.SingleThreadEventExecutor
    protected void wakeup(boolean z) {
        if (z || this.nextWakeupNanos.getAndSet(-1L) == -1) {
            return;
        }
        this.selector.wakeup();
    }

    @Override // io.netty.util.concurrent.AbstractScheduledEventExecutor
    protected boolean beforeScheduledTaskSubmitted(long j) {
        return j < this.nextWakeupNanos.get();
    }

    @Override // io.netty.util.concurrent.AbstractScheduledEventExecutor
    protected boolean afterScheduledTaskSubmitted(long j) {
        return j < this.nextWakeupNanos.get();
    }

    Selector unwrappedSelector() {
        return this.unwrappedSelector;
    }

    int selectNow() throws IOException {
        return this.selector.selectNow();
    }

    private int select(long j) throws IOException {
        if (j == Long.MAX_VALUE) {
            return this.selector.select();
        }
        long jDeadlineToDelayNanos = deadlineToDelayNanos(j + 995000) / PackingOptions.SEGMENT_LIMIT;
        return jDeadlineToDelayNanos <= 0 ? this.selector.selectNow() : this.selector.select(jDeadlineToDelayNanos);
    }

    private void selectAgain() {
        this.needsToSelectAgain = false;
        try {
            this.selector.selectNow();
        } catch (Throwable th) {
            logger.warn("Failed to update SelectionKeys.", th);
        }
    }
}
