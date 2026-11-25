package com.koushikdutta.async;

import android.os.Handler;
import android.util.Log;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.ListenCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.future.SimpleFuture;
import com.koushikdutta.async.future.TransformFuture;
import com.koushikdutta.async.util.StreamUtility;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncServer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String LOGTAG = "NIO";
    private static final long QUEUE_EMPTY = Long.MAX_VALUE;
    Thread mAffinity;
    String mName;
    PriorityQueue<Scheduled> mQueue;
    private SelectorWrapper mSelector;
    int postCounter;
    static AsyncServer mInstance = new AsyncServer();
    private static ExecutorService synchronousWorkers = newSynchronousWorkers("AsyncServer-worker-");
    private static final Comparator<InetAddress> ipSorter = new Comparator<InetAddress>() { // from class: com.koushikdutta.async.AsyncServer.8
        @Override // java.util.Comparator
        public int compare(InetAddress inetAddress, InetAddress inetAddress2) {
            boolean z = inetAddress instanceof Inet4Address;
            if (z && (inetAddress2 instanceof Inet4Address)) {
                return 0;
            }
            if ((inetAddress instanceof Inet6Address) && (inetAddress2 instanceof Inet6Address)) {
                return 0;
            }
            return (z && (inetAddress2 instanceof Inet6Address)) ? -1 : 1;
        }
    };
    private static ExecutorService synchronousResolverWorkers = newSynchronousWorkers("AsyncServer-resolver-");
    static final WeakHashMap<Thread, AsyncServer> mServers = new WeakHashMap<>();

    protected void onDataReceived(int i) {
    }

    protected void onDataSent(int i) {
    }

    private static class RunnableWrapper implements Runnable {
        Handler handler;
        boolean hasRun;
        Runnable runnable;
        ThreadQueue threadQueue;

        private RunnableWrapper() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            synchronized (this) {
                if (this.hasRun) {
                    return;
                }
                this.hasRun = true;
                try {
                    this.runnable.run();
                } finally {
                    this.threadQueue.remove(this);
                    this.handler.removeCallbacks(this);
                    this.threadQueue = null;
                    this.handler = null;
                    this.runnable = null;
                }
            }
        }
    }

    public static void post(Handler handler, Runnable runnable) {
        RunnableWrapper runnableWrapper = new RunnableWrapper();
        ThreadQueue orCreateThreadQueue = ThreadQueue.getOrCreateThreadQueue(handler.getLooper().getThread());
        runnableWrapper.threadQueue = orCreateThreadQueue;
        runnableWrapper.handler = handler;
        runnableWrapper.runnable = runnable;
        orCreateThreadQueue.add((Runnable) runnableWrapper);
        handler.post(runnableWrapper);
        orCreateThreadQueue.queueSemaphore.release();
    }

    public static AsyncServer getDefault() {
        return mInstance;
    }

    public boolean isRunning() {
        return this.mSelector != null;
    }

    public AsyncServer() {
        this(null);
    }

    public AsyncServer(String str) {
        this.postCounter = 0;
        this.mQueue = new PriorityQueue<>(1, Scheduler.INSTANCE);
        this.mName = str == null ? "AsyncServer" : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleSocket(AsyncNetworkSocket asyncNetworkSocket) throws ClosedChannelException {
        SelectionKey selectionKeyRegister = asyncNetworkSocket.getChannel().register(this.mSelector.getSelector());
        selectionKeyRegister.attach(asyncNetworkSocket);
        asyncNetworkSocket.setup(this, selectionKeyRegister);
    }

    public void removeAllCallbacks(Object obj) {
        synchronized (this) {
            this.mQueue.remove(obj);
        }
    }

    private static void wakeup(final SelectorWrapper selectorWrapper) {
        synchronousWorkers.execute(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    selectorWrapper.wakeupOnce();
                } catch (Exception unused) {
                    Log.i(AsyncServer.LOGTAG, "Selector Exception? L Preview?");
                }
            }
        });
    }

    public Object postDelayed(Runnable runnable, long j) {
        Scheduled scheduled;
        synchronized (this) {
            long jMin = 0;
            try {
                if (j > 0) {
                    jMin = System.currentTimeMillis() + j;
                } else if (j == 0) {
                    int i = this.postCounter;
                    this.postCounter = i + 1;
                    jMin = i;
                } else if (this.mQueue.size() > 0) {
                    jMin = Math.min(0L, this.mQueue.peek().time - 1);
                }
                PriorityQueue<Scheduled> priorityQueue = this.mQueue;
                scheduled = new Scheduled(runnable, jMin);
                priorityQueue.add(scheduled);
                if (this.mSelector == null) {
                    run(true);
                }
                if (!isAffinityThread()) {
                    wakeup(this.mSelector);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return scheduled;
    }

    public Object postImmediate(Runnable runnable) {
        if (Thread.currentThread() == getAffinity()) {
            runnable.run();
            return null;
        }
        return postDelayed(runnable, -1L);
    }

    public Object post(Runnable runnable) {
        return postDelayed(runnable, 0L);
    }

    public Object post(final CompletedCallback completedCallback, final Exception exc) {
        return post(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.2
            @Override // java.lang.Runnable
            public void run() {
                completedCallback.onCompleted(exc);
            }
        });
    }

    public void run(final Runnable runnable) {
        if (Thread.currentThread() == this.mAffinity) {
            post(runnable);
            lockAndRunQueue(this, this.mQueue);
            return;
        }
        final Semaphore semaphore = new Semaphore(0);
        post(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.3
            @Override // java.lang.Runnable
            public void run() {
                runnable.run();
                semaphore.release();
            }
        });
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Log.e(LOGTAG, "run", e);
        }
    }

    private static class Scheduled {
        public Runnable runnable;
        public long time;

        public Scheduled(Runnable runnable, long j) {
            this.runnable = runnable;
            this.time = j;
        }
    }

    static class Scheduler implements Comparator<Scheduled> {
        public static Scheduler INSTANCE = new Scheduler();

        private Scheduler() {
        }

        @Override // java.util.Comparator
        public int compare(Scheduled scheduled, Scheduled scheduled2) {
            if (scheduled.time == scheduled2.time) {
                return 0;
            }
            return scheduled.time > scheduled2.time ? 1 : -1;
        }
    }

    public void stop() {
        synchronized (this) {
            boolean zIsAffinityThread = isAffinityThread();
            final SelectorWrapper selectorWrapper = this.mSelector;
            if (selectorWrapper == null) {
                return;
            }
            WeakHashMap<Thread, AsyncServer> weakHashMap = mServers;
            synchronized (weakHashMap) {
                weakHashMap.remove(this.mAffinity);
            }
            final Semaphore semaphore = new Semaphore(0);
            this.mQueue.add(new Scheduled(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.4
                @Override // java.lang.Runnable
                public void run() {
                    AsyncServer.shutdownEverything(selectorWrapper);
                    semaphore.release();
                }
            }, 0L));
            selectorWrapper.wakeupOnce();
            shutdownKeys(selectorWrapper);
            this.mQueue = new PriorityQueue<>(1, Scheduler.INSTANCE);
            this.mSelector = null;
            this.mAffinity = null;
            if (zIsAffinityThread) {
                return;
            }
            try {
                semaphore.acquire();
            } catch (Exception unused) {
            }
        }
    }

    private static class ObjectHolder<T> {
        T held;

        private ObjectHolder() {
        }
    }

    public AsyncServerSocket listen(final InetAddress inetAddress, final int i, final ListenCallback listenCallback) {
        final ObjectHolder objectHolder = new ObjectHolder();
        run(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.5
            /* JADX WARN: Type inference failed for: r5v0, types: [T, com.koushikdutta.async.AsyncServer$5$1] */
            @Override // java.lang.Runnable
            public void run() {
                final ServerSocketChannelWrapper serverSocketChannelWrapper;
                IOException e;
                final ServerSocketChannel serverSocketChannelOpen;
                InetSocketAddress inetSocketAddress;
                try {
                    serverSocketChannelOpen = ServerSocketChannel.open();
                    try {
                        serverSocketChannelWrapper = new ServerSocketChannelWrapper(serverSocketChannelOpen);
                        try {
                            if (inetAddress == null) {
                                inetSocketAddress = new InetSocketAddress(i);
                            } else {
                                inetSocketAddress = new InetSocketAddress(inetAddress, i);
                            }
                            serverSocketChannelOpen.socket().bind(inetSocketAddress);
                            final SelectionKey selectionKeyRegister = serverSocketChannelWrapper.register(AsyncServer.this.mSelector.getSelector());
                            selectionKeyRegister.attach(listenCallback);
                            ListenCallback listenCallback2 = listenCallback;
                            ObjectHolder objectHolder2 = objectHolder;
                            ?? r5 = new AsyncServerSocket() { // from class: com.koushikdutta.async.AsyncServer.5.1
                                @Override // com.koushikdutta.async.AsyncServerSocket
                                public int getLocalPort() {
                                    return serverSocketChannelOpen.socket().getLocalPort();
                                }

                                @Override // com.koushikdutta.async.AsyncServerSocket
                                public void stop() {
                                    StreamUtility.closeQuietly(serverSocketChannelWrapper);
                                    try {
                                        selectionKeyRegister.cancel();
                                    } catch (Exception unused) {
                                    }
                                }
                            };
                            objectHolder2.held = r5;
                            listenCallback2.onListening((AsyncServerSocket) r5);
                        } catch (IOException e2) {
                            e = e2;
                            Log.e(AsyncServer.LOGTAG, "wtf", e);
                            StreamUtility.closeQuietly(serverSocketChannelWrapper, serverSocketChannelOpen);
                            listenCallback.onCompleted(e);
                        }
                    } catch (IOException e3) {
                        serverSocketChannelWrapper = null;
                        e = e3;
                    }
                } catch (IOException e4) {
                    serverSocketChannelWrapper = null;
                    e = e4;
                    serverSocketChannelOpen = null;
                }
            }
        });
        return (AsyncServerSocket) objectHolder.held;
    }

    private class ConnectFuture extends SimpleFuture<AsyncNetworkSocket> {
        ConnectCallback callback;
        SocketChannel socket;

        private ConnectFuture() {
        }

        @Override // com.koushikdutta.async.future.SimpleCancellable
        protected void cancelCleanup() {
            super.cancelCleanup();
            try {
                SocketChannel socketChannel = this.socket;
                if (socketChannel != null) {
                    socketChannel.close();
                }
            } catch (IOException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ConnectFuture connectResolvedInetSocketAddress(final InetSocketAddress inetSocketAddress, final ConnectCallback connectCallback) {
        final ConnectFuture connectFuture = new ConnectFuture();
        post(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.6
            @Override // java.lang.Runnable
            public void run() {
                SocketChannel socketChannelOpen;
                if (connectFuture.isCancelled()) {
                    return;
                }
                connectFuture.callback = connectCallback;
                SelectionKey selectionKeyRegister = null;
                try {
                    ConnectFuture connectFuture2 = connectFuture;
                    socketChannelOpen = SocketChannel.open();
                    connectFuture2.socket = socketChannelOpen;
                    try {
                        socketChannelOpen.configureBlocking(false);
                        selectionKeyRegister = socketChannelOpen.register(AsyncServer.this.mSelector.getSelector(), 8);
                        selectionKeyRegister.attach(connectFuture);
                        socketChannelOpen.connect(inetSocketAddress);
                    } catch (Throwable th) {
                        th = th;
                        if (selectionKeyRegister != null) {
                            selectionKeyRegister.cancel();
                        }
                        StreamUtility.closeQuietly(socketChannelOpen);
                        connectFuture.setComplete((Exception) new RuntimeException(th));
                    }
                } catch (Throwable th2) {
                    th = th2;
                    socketChannelOpen = null;
                }
            }
        });
        return connectFuture;
    }

    public Cancellable connectSocket(final InetSocketAddress inetSocketAddress, final ConnectCallback connectCallback) {
        if (!inetSocketAddress.isUnresolved()) {
            return connectResolvedInetSocketAddress(inetSocketAddress, connectCallback);
        }
        final SimpleFuture simpleFuture = new SimpleFuture();
        Future<InetAddress> byName = getByName(inetSocketAddress.getHostName());
        simpleFuture.setParent((Cancellable) byName);
        byName.setCallback(new FutureCallback<InetAddress>() { // from class: com.koushikdutta.async.AsyncServer.7
            @Override // com.koushikdutta.async.future.FutureCallback
            public void onCompleted(Exception exc, InetAddress inetAddress) {
                if (exc == null) {
                    simpleFuture.setComplete((Future) AsyncServer.this.connectResolvedInetSocketAddress(new InetSocketAddress(inetAddress, inetSocketAddress.getPort()), connectCallback));
                } else {
                    connectCallback.onConnectCompleted(exc, null);
                    simpleFuture.setComplete(exc);
                }
            }
        });
        return simpleFuture;
    }

    public Cancellable connectSocket(String str, int i, ConnectCallback connectCallback) {
        return connectSocket(InetSocketAddress.createUnresolved(str, i), connectCallback);
    }

    private static ExecutorService newSynchronousWorkers(String str) {
        return new ThreadPoolExecutor(1, 4, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue(), new NamedThreadFactory(str));
    }

    public Future<InetAddress[]> getAllByName(final String str) {
        final SimpleFuture simpleFuture = new SimpleFuture();
        synchronousResolverWorkers.execute(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    final InetAddress[] allByName = InetAddress.getAllByName(str);
                    Arrays.sort(allByName, AsyncServer.ipSorter);
                    if (allByName == null || allByName.length == 0) {
                        throw new HostnameResolutionException("no addresses for host");
                    }
                    AsyncServer.this.post(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.9.1
                        @Override // java.lang.Runnable
                        public void run() {
                            simpleFuture.setComplete(null, allByName);
                        }
                    });
                } catch (Exception e) {
                    AsyncServer.this.post(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.9.2
                        @Override // java.lang.Runnable
                        public void run() {
                            simpleFuture.setComplete(e, null);
                        }
                    });
                }
            }
        });
        return simpleFuture;
    }

    public Future<InetAddress> getByName(String str) {
        return (Future) getAllByName(str).then(new TransformFuture<InetAddress, InetAddress[]>() { // from class: com.koushikdutta.async.AsyncServer.10
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.koushikdutta.async.future.TransformFuture
            public void transform(InetAddress[] inetAddressArr) throws Exception {
                setComplete(inetAddressArr[0]);
            }
        });
    }

    public AsyncDatagramSocket connectDatagram(final String str, final int i) throws IOException {
        final DatagramChannel datagramChannelOpen = DatagramChannel.open();
        final AsyncDatagramSocket asyncDatagramSocket = new AsyncDatagramSocket();
        asyncDatagramSocket.attach(datagramChannelOpen);
        run(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    InetSocketAddress inetSocketAddress = new InetSocketAddress(str, i);
                    AsyncServer.this.handleSocket(asyncDatagramSocket);
                    datagramChannelOpen.connect(inetSocketAddress);
                } catch (IOException e) {
                    Log.e(AsyncServer.LOGTAG, "Datagram error", e);
                    StreamUtility.closeQuietly(datagramChannelOpen);
                }
            }
        });
        return asyncDatagramSocket;
    }

    public AsyncDatagramSocket openDatagram() throws IOException {
        return openDatagram(null, false);
    }

    public AsyncDatagramSocket openDatagram(final SocketAddress socketAddress, final boolean z) throws IOException {
        final DatagramChannel datagramChannelOpen = DatagramChannel.open();
        final AsyncDatagramSocket asyncDatagramSocket = new AsyncDatagramSocket();
        asyncDatagramSocket.attach(datagramChannelOpen);
        run(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.12
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (z) {
                        datagramChannelOpen.socket().setReuseAddress(z);
                    }
                    datagramChannelOpen.socket().bind(socketAddress);
                    AsyncServer.this.handleSocket(asyncDatagramSocket);
                } catch (IOException e) {
                    Log.e(AsyncServer.LOGTAG, "Datagram error", e);
                    StreamUtility.closeQuietly(datagramChannelOpen);
                }
            }
        });
        return asyncDatagramSocket;
    }

    public AsyncDatagramSocket connectDatagram(final SocketAddress socketAddress) throws IOException {
        final DatagramChannel datagramChannelOpen = DatagramChannel.open();
        final AsyncDatagramSocket asyncDatagramSocket = new AsyncDatagramSocket();
        asyncDatagramSocket.attach(datagramChannelOpen);
        run(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.13
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AsyncServer.this.handleSocket(asyncDatagramSocket);
                    datagramChannelOpen.connect(socketAddress);
                } catch (IOException unused) {
                    StreamUtility.closeQuietly(datagramChannelOpen);
                }
            }
        });
        return asyncDatagramSocket;
    }

    private boolean addMe() {
        WeakHashMap<Thread, AsyncServer> weakHashMap = mServers;
        synchronized (weakHashMap) {
            if (weakHashMap.get(this.mAffinity) != null) {
                return false;
            }
            weakHashMap.put(this.mAffinity, this);
            return true;
        }
    }

    public static AsyncServer getCurrentThreadServer() {
        return mServers.get(Thread.currentThread());
    }

    private void run(boolean z) {
        boolean z2;
        SelectorWrapper selectorWrapper;
        PriorityQueue<Scheduled> priorityQueue;
        synchronized (this) {
            if (this.mSelector != null) {
                Log.i(LOGTAG, "Reentrant call");
                selectorWrapper = this.mSelector;
                priorityQueue = this.mQueue;
                z2 = true;
            } else {
                try {
                    final SelectorWrapper selectorWrapper2 = new SelectorWrapper(SelectorProvider.provider().openSelector());
                    this.mSelector = selectorWrapper2;
                    final PriorityQueue<Scheduled> priorityQueue2 = this.mQueue;
                    if (z) {
                        this.mAffinity = new Thread(this.mName) { // from class: com.koushikdutta.async.AsyncServer.14
                            @Override // java.lang.Thread, java.lang.Runnable
                            public void run() {
                                AsyncServer.run(AsyncServer.this, selectorWrapper2, priorityQueue2);
                            }
                        };
                    } else {
                        this.mAffinity = Thread.currentThread();
                    }
                    if (!addMe()) {
                        try {
                            this.mSelector.close();
                        } catch (Exception unused) {
                        }
                        this.mSelector = null;
                        this.mAffinity = null;
                        return;
                    } else if (z) {
                        this.mAffinity.start();
                        return;
                    } else {
                        z2 = false;
                        selectorWrapper = selectorWrapper2;
                        priorityQueue = priorityQueue2;
                    }
                } catch (IOException unused2) {
                    return;
                }
            }
            if (z2) {
                try {
                    runLoop(this, selectorWrapper, priorityQueue);
                    return;
                } catch (AsyncSelectorException e) {
                    Log.i(LOGTAG, "Selector closed", e);
                    try {
                        selectorWrapper.getSelector().close();
                        return;
                    } catch (Exception unused3) {
                        return;
                    }
                }
            }
            run(this, selectorWrapper, priorityQueue);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void run(AsyncServer asyncServer, SelectorWrapper selectorWrapper, PriorityQueue<Scheduled> priorityQueue) {
        while (true) {
            try {
                runLoop(asyncServer, selectorWrapper, priorityQueue);
            } catch (AsyncSelectorException e) {
                Log.i(LOGTAG, "Selector exception, shutting down", e);
                try {
                    selectorWrapper.getSelector().close();
                } catch (Exception unused) {
                }
            }
            synchronized (asyncServer) {
                if (!selectorWrapper.isOpen() || (selectorWrapper.keys().size() <= 0 && priorityQueue.size() <= 0)) {
                    break;
                }
            }
        }
        shutdownEverything(selectorWrapper);
        if (asyncServer.mSelector == selectorWrapper) {
            asyncServer.mQueue = new PriorityQueue<>(1, Scheduler.INSTANCE);
            asyncServer.mSelector = null;
            asyncServer.mAffinity = null;
        }
        WeakHashMap<Thread, AsyncServer> weakHashMap = mServers;
        synchronized (weakHashMap) {
            weakHashMap.remove(Thread.currentThread());
        }
    }

    private static void shutdownKeys(SelectorWrapper selectorWrapper) {
        try {
            for (SelectionKey selectionKey : selectorWrapper.keys()) {
                StreamUtility.closeQuietly(selectionKey.channel());
                try {
                    selectionKey.cancel();
                } catch (Exception unused) {
                }
            }
        } catch (Exception unused2) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void shutdownEverything(SelectorWrapper selectorWrapper) {
        shutdownKeys(selectorWrapper);
        try {
            selectorWrapper.close();
        } catch (Exception unused) {
        }
    }

    private static long lockAndRunQueue(AsyncServer asyncServer, PriorityQueue<Scheduled> priorityQueue) {
        Scheduled scheduled;
        long j = Long.MAX_VALUE;
        while (true) {
            synchronized (asyncServer) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                scheduled = null;
                if (priorityQueue.size() > 0) {
                    Scheduled scheduledRemove = priorityQueue.remove();
                    if (scheduledRemove.time <= jCurrentTimeMillis) {
                        scheduled = scheduledRemove;
                    } else {
                        j = scheduledRemove.time - jCurrentTimeMillis;
                        priorityQueue.add(scheduledRemove);
                    }
                }
            }
            if (scheduled != null) {
                scheduled.runnable.run();
            } else {
                asyncServer.postCounter = 0;
                return j;
            }
        }
    }

    private static class AsyncSelectorException extends IOException {
        public AsyncSelectorException(Exception exc) {
            super(exc);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.nio.channels.SelectionKey] */
    /* JADX WARN: Type inference failed for: r1v20, types: [com.koushikdutta.async.callback.ConnectCallback] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.nio.channels.SelectionKey] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.koushikdutta.async.callback.ListenCallback] */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v4, types: [java.nio.channels.SelectionKey] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.koushikdutta.async.AsyncNetworkSocket, com.koushikdutta.async.AsyncSocket, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v4, types: [com.koushikdutta.async.AsyncNetworkSocket, com.koushikdutta.async.AsyncSocket, java.lang.Object] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private static void runLoop(AsyncServer asyncServer, SelectorWrapper selectorWrapper, PriorityQueue<Scheduled> priorityQueue) throws AsyncSelectorException {
        ?? r11;
        ?? r1;
        long jLockAndRunQueue = lockAndRunQueue(asyncServer, priorityQueue);
        try {
            synchronized (asyncServer) {
                if (selectorWrapper.selectNow() != 0) {
                    r11 = false;
                } else if (selectorWrapper.keys().size() == 0 && jLockAndRunQueue == Long.MAX_VALUE) {
                    return;
                } else {
                    r11 = true;
                }
                if (r11 != false) {
                    if (jLockAndRunQueue == Long.MAX_VALUE) {
                        selectorWrapper.select();
                    } else {
                        selectorWrapper.select(jLockAndRunQueue);
                    }
                }
                Set<SelectionKey> setSelectedKeys = selectorWrapper.selectedKeys();
                for (SelectionKey selectionKey : setSelectedKeys) {
                    try {
                        SocketChannel socketChannel = null;
                        ?? Register = 0;
                        if (selectionKey.isAcceptable()) {
                            try {
                                SocketChannel socketChannelAccept = ((ServerSocketChannel) selectionKey.channel()).accept();
                                if (socketChannelAccept != null) {
                                    try {
                                        socketChannelAccept.configureBlocking(false);
                                        Register = socketChannelAccept.register(selectorWrapper.getSelector(), 1);
                                        ?? r2 = (ListenCallback) selectionKey.attachment();
                                        ?? asyncNetworkSocket = new AsyncNetworkSocket();
                                        asyncNetworkSocket.attach(socketChannelAccept, (InetSocketAddress) socketChannelAccept.socket().getRemoteSocketAddress());
                                        asyncNetworkSocket.setup(asyncServer, Register);
                                        Register.attach(asyncNetworkSocket);
                                        r2.onAccepted(asyncNetworkSocket);
                                    } catch (IOException unused) {
                                        r1 = Register;
                                        socketChannel = socketChannelAccept;
                                        StreamUtility.closeQuietly(socketChannel);
                                        if (r1 != 0) {
                                            r1.cancel();
                                        }
                                    }
                                }
                            } catch (IOException unused2) {
                                r1 = 0;
                            }
                        } else if (selectionKey.isReadable()) {
                            asyncServer.onDataReceived(((AsyncNetworkSocket) selectionKey.attachment()).onReadable());
                        } else if (selectionKey.isWritable()) {
                            ((AsyncNetworkSocket) selectionKey.attachment()).onDataWritable();
                        } else if (selectionKey.isConnectable()) {
                            ConnectFuture connectFuture = (ConnectFuture) selectionKey.attachment();
                            SocketChannel socketChannel2 = (SocketChannel) selectionKey.channel();
                            selectionKey.interestOps(1);
                            try {
                                socketChannel2.finishConnect();
                                ?? asyncNetworkSocket2 = new AsyncNetworkSocket();
                                asyncNetworkSocket2.setup(asyncServer, selectionKey);
                                asyncNetworkSocket2.attach(socketChannel2, (InetSocketAddress) socketChannel2.socket().getRemoteSocketAddress());
                                selectionKey.attach(asyncNetworkSocket2);
                                try {
                                    if (connectFuture.setComplete(asyncNetworkSocket2)) {
                                        connectFuture.callback.onConnectCompleted(null, asyncNetworkSocket2);
                                    }
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            } catch (IOException e2) {
                                selectionKey.cancel();
                                StreamUtility.closeQuietly(socketChannel2);
                                if (connectFuture.setComplete((Exception) e2)) {
                                    connectFuture.callback.onConnectCompleted(e2, null);
                                }
                            }
                        } else {
                            Log.i(LOGTAG, "wtf");
                            throw new RuntimeException("Unknown key state.");
                        }
                    } catch (CancelledKeyException unused3) {
                    }
                }
                setSelectedKeys.clear();
            }
        } catch (Exception e3) {
            throw new AsyncSelectorException(e3);
        }
    }

    public void dump() {
        post(new Runnable() { // from class: com.koushikdutta.async.AsyncServer.15
            @Override // java.lang.Runnable
            public void run() {
                if (AsyncServer.this.mSelector != null) {
                    Log.i(AsyncServer.LOGTAG, "Key Count: " + AsyncServer.this.mSelector.keys().size());
                    Iterator<SelectionKey> it = AsyncServer.this.mSelector.keys().iterator();
                    while (it.hasNext()) {
                        Log.i(AsyncServer.LOGTAG, "Key: " + it.next());
                    }
                    return;
                }
                Log.i(AsyncServer.LOGTAG, "Server dump not possible. No selector?");
            }
        });
    }

    public Thread getAffinity() {
        return this.mAffinity;
    }

    public boolean isAffinityThread() {
        return this.mAffinity == Thread.currentThread();
    }

    public boolean isAffinityThreadOrStopped() {
        Thread thread = this.mAffinity;
        return thread == null || thread == Thread.currentThread();
    }

    private static class NamedThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final String namePrefix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        NamedThreadFactory(String str) {
            ThreadGroup threadGroup;
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                threadGroup = securityManager.getThreadGroup();
            } else {
                threadGroup = Thread.currentThread().getThreadGroup();
            }
            this.group = threadGroup;
            this.namePrefix = str;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(this.group, runnable, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
            if (thread.isDaemon()) {
                thread.setDaemon(false);
            }
            if (thread.getPriority() != 5) {
                thread.setPriority(5);
            }
            return thread;
        }
    }
}
