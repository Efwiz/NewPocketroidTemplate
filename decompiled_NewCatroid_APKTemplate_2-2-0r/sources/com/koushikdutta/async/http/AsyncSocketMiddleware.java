package com.koushikdutta.async.http;

import android.net.Uri;
import com.koushikdutta.async.ArrayDeque;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.ContinuationCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.Continuation;
import com.koushikdutta.async.future.SimpleCancellable;
import com.koushikdutta.async.future.TransformFuture;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Hashtable;
import java.util.Locale;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncSocketMiddleware extends SimpleMiddleware {
    boolean connectAllAddresses;
    Hashtable<String, ConnectionInfo> connectionInfo;
    int idleTimeoutMs;
    protected AsyncHttpClient mClient;
    int maxConnectionCount;
    int port;
    InetSocketAddress proxyAddress;
    String proxyHost;
    int proxyPort;
    String scheme;

    protected ConnectCallback wrapCallback(AsyncHttpClientMiddleware.GetSocketData getSocketData, Uri uri, int i, boolean z, ConnectCallback connectCallback) {
        return connectCallback;
    }

    public AsyncSocketMiddleware(AsyncHttpClient asyncHttpClient, String str, int i) {
        this.idleTimeoutMs = 300000;
        this.connectionInfo = new Hashtable<>();
        this.maxConnectionCount = Integer.MAX_VALUE;
        this.mClient = asyncHttpClient;
        this.scheme = str;
        this.port = i;
    }

    public void setIdleTimeoutMs(int i) {
        this.idleTimeoutMs = i;
    }

    public int getSchemePort(Uri uri) {
        if (uri.getScheme() == null || !uri.getScheme().equals(this.scheme)) {
            return -1;
        }
        if (uri.getPort() == -1) {
            return this.port;
        }
        return uri.getPort();
    }

    public AsyncSocketMiddleware(AsyncHttpClient asyncHttpClient) {
        this(asyncHttpClient, "http", 80);
    }

    public boolean getConnectAllAddresses() {
        return this.connectAllAddresses;
    }

    public void setConnectAllAddresses(boolean z) {
        this.connectAllAddresses = z;
    }

    public void disableProxy() {
        this.proxyPort = -1;
        this.proxyHost = null;
        this.proxyAddress = null;
    }

    public void enableProxy(String str, int i) {
        this.proxyHost = str;
        this.proxyPort = i;
        this.proxyAddress = null;
    }

    String computeLookup(Uri uri, int i, String str, int i2) {
        String str2;
        if (str != null) {
            str2 = str + ":" + i2;
        } else {
            str2 = "";
        }
        if (str != null) {
            str2 = str + ":" + i2;
        }
        return uri.getScheme() + "//" + uri.getHost() + ":" + i + "?proxy=" + str2;
    }

    class IdleSocketHolder {
        long idleTime = System.currentTimeMillis();
        AsyncSocket socket;

        public IdleSocketHolder(AsyncSocket asyncSocket) {
            this.socket = asyncSocket;
        }
    }

    static class ConnectionInfo {
        int openCount;
        ArrayDeque<AsyncHttpClientMiddleware.GetSocketData> queue = new ArrayDeque<>();
        ArrayDeque<IdleSocketHolder> sockets = new ArrayDeque<>();

        ConnectionInfo() {
        }
    }

    public int getMaxConnectionCount() {
        return this.maxConnectionCount;
    }

    public void setMaxConnectionCount(int i) {
        this.maxConnectionCount = i;
    }

    @Override // com.koushikdutta.async.http.SimpleMiddleware, com.koushikdutta.async.http.AsyncHttpClientMiddleware
    public Cancellable getSocket(AsyncHttpClientMiddleware.GetSocketData getSocketData) {
        String host;
        int proxyPort;
        Uri uri = getSocketData.request.getUri();
        int schemePort = getSchemePort(getSocketData.request.getUri());
        if (schemePort == -1) {
            return null;
        }
        getSocketData.state.put("socket-owner", this);
        ConnectionInfo orCreateConnectionInfo = getOrCreateConnectionInfo(computeLookup(uri, schemePort, getSocketData.request.getProxyHost(), getSocketData.request.getProxyPort()));
        synchronized (this) {
            if (orCreateConnectionInfo.openCount >= this.maxConnectionCount) {
                SimpleCancellable simpleCancellable = new SimpleCancellable();
                orCreateConnectionInfo.queue.add(getSocketData);
                return simpleCancellable;
            }
            boolean z = true;
            orCreateConnectionInfo.openCount++;
            while (!orCreateConnectionInfo.sockets.isEmpty()) {
                IdleSocketHolder idleSocketHolderPop = orCreateConnectionInfo.sockets.pop();
                AsyncSocket asyncSocket = idleSocketHolderPop.socket;
                if (idleSocketHolderPop.idleTime + ((long) this.idleTimeoutMs) < System.currentTimeMillis()) {
                    asyncSocket.setClosedCallback(null);
                    asyncSocket.close();
                } else if (asyncSocket.isOpen()) {
                    getSocketData.request.logd("Reusing keep-alive socket");
                    getSocketData.connectCallback.onConnectCompleted(null, asyncSocket);
                    SimpleCancellable simpleCancellable2 = new SimpleCancellable();
                    simpleCancellable2.setComplete();
                    return simpleCancellable2;
                }
            }
            if (!this.connectAllAddresses || this.proxyHost != null || getSocketData.request.getProxyHost() != null) {
                getSocketData.request.logd("Connecting socket");
                if (getSocketData.request.getProxyHost() == null && this.proxyHost != null) {
                    getSocketData.request.enableProxy(this.proxyHost, this.proxyPort);
                }
                if (getSocketData.request.getProxyHost() != null) {
                    host = getSocketData.request.getProxyHost();
                    proxyPort = getSocketData.request.getProxyPort();
                } else {
                    host = uri.getHost();
                    z = false;
                    proxyPort = schemePort;
                }
                if (z) {
                    getSocketData.request.logv("Using proxy: " + host + ":" + proxyPort);
                }
                return this.mClient.getServer().connectSocket(host, proxyPort, wrapCallback(getSocketData, uri, schemePort, z, getSocketData.connectCallback));
            }
            getSocketData.request.logv("Resolving domain and connecting to all available addresses");
            return (Cancellable) this.mClient.getServer().getAllByName(uri.getHost()).then(new AnonymousClass1(getSocketData, uri, schemePort));
        }
    }

    /* JADX INFO: renamed from: com.koushikdutta.async.http.AsyncSocketMiddleware$1, reason: invalid class name */
    class AnonymousClass1 extends TransformFuture<AsyncSocket, InetAddress[]> {
        Exception lastException;
        final /* synthetic */ AsyncHttpClientMiddleware.GetSocketData val$data;
        final /* synthetic */ int val$port;
        final /* synthetic */ Uri val$uri;

        AnonymousClass1(AsyncHttpClientMiddleware.GetSocketData getSocketData, Uri uri, int i) {
            this.val$data = getSocketData;
            this.val$uri = uri;
            this.val$port = i;
        }

        @Override // com.koushikdutta.async.future.TransformFuture
        protected void error(Exception exc) {
            super.error(exc);
            AsyncSocketMiddleware asyncSocketMiddleware = AsyncSocketMiddleware.this;
            AsyncHttpClientMiddleware.GetSocketData getSocketData = this.val$data;
            asyncSocketMiddleware.wrapCallback(getSocketData, this.val$uri, this.val$port, false, getSocketData.connectCallback).onConnectCompleted(exc, null);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.koushikdutta.async.future.TransformFuture
        public void transform(InetAddress[] inetAddressArr) throws Exception {
            Continuation continuation = new Continuation(new CompletedCallback() { // from class: com.koushikdutta.async.http.AsyncSocketMiddleware.1.1
                @Override // com.koushikdutta.async.callback.CompletedCallback
                public void onCompleted(Exception exc) {
                    if (AnonymousClass1.this.lastException == null) {
                        AnonymousClass1.this.lastException = new ConnectionFailedException("Unable to connect to remote address");
                    }
                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                    if (anonymousClass1.setComplete(anonymousClass1.lastException)) {
                        AsyncSocketMiddleware.this.wrapCallback(AnonymousClass1.this.val$data, AnonymousClass1.this.val$uri, AnonymousClass1.this.val$port, false, AnonymousClass1.this.val$data.connectCallback).onConnectCompleted(AnonymousClass1.this.lastException, null);
                    }
                }
            });
            for (final InetAddress inetAddress : inetAddressArr) {
                final String str = String.format(Locale.ENGLISH, "%s:%s", inetAddress, Integer.valueOf(this.val$port));
                continuation.add(new ContinuationCallback() { // from class: com.koushikdutta.async.http.AsyncSocketMiddleware.1.2
                    @Override // com.koushikdutta.async.callback.ContinuationCallback
                    public void onContinue(Continuation continuation2, final CompletedCallback completedCallback) throws Exception {
                        AnonymousClass1.this.val$data.request.logv("attempting connection to " + str);
                        AsyncSocketMiddleware.this.mClient.getServer().connectSocket(new InetSocketAddress(inetAddress, AnonymousClass1.this.val$port), AsyncSocketMiddleware.this.wrapCallback(AnonymousClass1.this.val$data, AnonymousClass1.this.val$uri, AnonymousClass1.this.val$port, false, new ConnectCallback() { // from class: com.koushikdutta.async.http.AsyncSocketMiddleware.1.2.1
                            @Override // com.koushikdutta.async.callback.ConnectCallback
                            public void onConnectCompleted(Exception exc, AsyncSocket asyncSocket) {
                                if (AnonymousClass1.this.isDone()) {
                                    AnonymousClass1.this.lastException = new Exception("internal error during connect to " + str);
                                    completedCallback.onCompleted(null);
                                } else if (exc != null) {
                                    AnonymousClass1.this.lastException = exc;
                                    completedCallback.onCompleted(null);
                                } else if (AnonymousClass1.this.isDone() || AnonymousClass1.this.isCancelled()) {
                                    AnonymousClass1.this.val$data.request.logd("Recycling extra socket leftover from cancelled operation");
                                    AsyncSocketMiddleware.this.idleSocket(asyncSocket);
                                    AsyncSocketMiddleware.this.recycleSocket(asyncSocket, AnonymousClass1.this.val$data.request);
                                } else if (AnonymousClass1.this.setComplete(null, asyncSocket)) {
                                    AnonymousClass1.this.val$data.connectCallback.onConnectCompleted(null, asyncSocket);
                                }
                            }
                        }));
                    }
                });
            }
            continuation.start();
        }
    }

    private ConnectionInfo getOrCreateConnectionInfo(String str) {
        ConnectionInfo connectionInfo = this.connectionInfo.get(str);
        if (connectionInfo != null) {
            return connectionInfo;
        }
        ConnectionInfo connectionInfo2 = new ConnectionInfo();
        this.connectionInfo.put(str, connectionInfo2);
        return connectionInfo2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void maybeCleanupConnectionInfo(String str) {
        ConnectionInfo connectionInfo = this.connectionInfo.get(str);
        if (connectionInfo == null) {
            return;
        }
        while (!connectionInfo.sockets.isEmpty()) {
            IdleSocketHolder idleSocketHolderPeekLast = connectionInfo.sockets.peekLast();
            AsyncSocket asyncSocket = idleSocketHolderPeekLast.socket;
            if (idleSocketHolderPeekLast.idleTime + ((long) this.idleTimeoutMs) > System.currentTimeMillis()) {
                break;
            }
            connectionInfo.sockets.pop();
            asyncSocket.setClosedCallback(null);
            asyncSocket.close();
        }
        if (connectionInfo.openCount == 0 && connectionInfo.queue.isEmpty() && connectionInfo.sockets.isEmpty()) {
            this.connectionInfo.remove(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recycleSocket(AsyncSocket asyncSocket, AsyncHttpRequest asyncHttpRequest) {
        final ArrayDeque<IdleSocketHolder> arrayDeque;
        if (asyncSocket == null) {
            return;
        }
        Uri uri = asyncHttpRequest.getUri();
        final String strComputeLookup = computeLookup(uri, getSchemePort(uri), asyncHttpRequest.getProxyHost(), asyncHttpRequest.getProxyPort());
        final IdleSocketHolder idleSocketHolder = new IdleSocketHolder(asyncSocket);
        synchronized (this) {
            arrayDeque = getOrCreateConnectionInfo(strComputeLookup).sockets;
            arrayDeque.push(idleSocketHolder);
        }
        asyncSocket.setClosedCallback(new CompletedCallback() { // from class: com.koushikdutta.async.http.AsyncSocketMiddleware.2
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                synchronized (AsyncSocketMiddleware.this) {
                    arrayDeque.remove(idleSocketHolder);
                    AsyncSocketMiddleware.this.maybeCleanupConnectionInfo(strComputeLookup);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void idleSocket(final AsyncSocket asyncSocket) {
        asyncSocket.setEndCallback(new CompletedCallback() { // from class: com.koushikdutta.async.http.AsyncSocketMiddleware.3
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                asyncSocket.setClosedCallback(null);
                asyncSocket.close();
            }
        });
        asyncSocket.setWriteableCallback(null);
        asyncSocket.setDataCallback(new DataCallback.NullDataCallback() { // from class: com.koushikdutta.async.http.AsyncSocketMiddleware.4
            @Override // com.koushikdutta.async.callback.DataCallback.NullDataCallback, com.koushikdutta.async.callback.DataCallback
            public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                super.onDataAvailable(dataEmitter, byteBufferList);
                byteBufferList.recycle();
                asyncSocket.setClosedCallback(null);
                asyncSocket.close();
            }
        });
    }

    private void nextConnection(AsyncHttpRequest asyncHttpRequest) {
        Uri uri = asyncHttpRequest.getUri();
        String strComputeLookup = computeLookup(uri, getSchemePort(uri), asyncHttpRequest.getProxyHost(), asyncHttpRequest.getProxyPort());
        synchronized (this) {
            ConnectionInfo connectionInfo = this.connectionInfo.get(strComputeLookup);
            if (connectionInfo == null) {
                return;
            }
            connectionInfo.openCount--;
            while (connectionInfo.openCount < this.maxConnectionCount && connectionInfo.queue.size() > 0) {
                AsyncHttpClientMiddleware.GetSocketData getSocketDataRemove = connectionInfo.queue.remove();
                SimpleCancellable simpleCancellable = (SimpleCancellable) getSocketDataRemove.socketCancellable;
                if (!simpleCancellable.isCancelled()) {
                    simpleCancellable.setParent(getSocket(getSocketDataRemove));
                }
            }
            maybeCleanupConnectionInfo(strComputeLookup);
        }
    }

    @Override // com.koushikdutta.async.http.SimpleMiddleware, com.koushikdutta.async.http.AsyncHttpClientMiddleware
    public void onResponseComplete(AsyncHttpClientMiddleware.OnResponseCompleteDataOnRequestSentData onResponseCompleteDataOnRequestSentData) {
        if (onResponseCompleteDataOnRequestSentData.state.get("socket-owner") != this) {
            return;
        }
        try {
            idleSocket(onResponseCompleteDataOnRequestSentData.socket);
            if (onResponseCompleteDataOnRequestSentData.exception != null || !onResponseCompleteDataOnRequestSentData.socket.isOpen()) {
                onResponseCompleteDataOnRequestSentData.request.logv("closing out socket (exception)");
                onResponseCompleteDataOnRequestSentData.socket.setClosedCallback(null);
                onResponseCompleteDataOnRequestSentData.socket.close();
            } else {
                if (HttpUtil.isKeepAlive(onResponseCompleteDataOnRequestSentData.response.protocol(), onResponseCompleteDataOnRequestSentData.response.headers()) && HttpUtil.isKeepAlive(Protocol.HTTP_1_1, onResponseCompleteDataOnRequestSentData.request.getHeaders())) {
                    onResponseCompleteDataOnRequestSentData.request.logd("Recycling keep-alive socket");
                    recycleSocket(onResponseCompleteDataOnRequestSentData.socket, onResponseCompleteDataOnRequestSentData.request);
                    return;
                }
                onResponseCompleteDataOnRequestSentData.request.logv("closing out socket (not keep alive)");
                onResponseCompleteDataOnRequestSentData.socket.setClosedCallback(null);
                onResponseCompleteDataOnRequestSentData.socket.close();
            }
        } finally {
            nextConnection(onResponseCompleteDataOnRequestSentData.request);
        }
    }
}
