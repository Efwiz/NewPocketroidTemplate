package com.koushikdutta.async.http.spdy;

import android.net.Uri;
import android.text.TextUtils;
import com.koushikdutta.async.AsyncSSLSocket;
import com.koushikdutta.async.AsyncSSLSocketWrapper;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.future.MultiFuture;
import com.koushikdutta.async.future.SimpleCancellable;
import com.koushikdutta.async.future.TransformFuture;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpClientMiddleware;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncSSLEngineConfigurator;
import com.koushikdutta.async.http.AsyncSSLSocketMiddleware;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.HttpUtil;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.util.Charsets;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import net.bytebuddy.description.type.TypeDescription;

/* JADX INFO: loaded from: classes10.dex */
public class SpdyMiddleware extends AsyncSSLSocketMiddleware {
    private static final NoSpdyException NO_SPDY = new NoSpdyException();
    Field alpnProtocols;
    Hashtable<String, SpdyConnectionWaiter> connections;
    boolean initialized;
    Method nativeGetAlpnNegotiatedProtocol;
    Method nativeGetNpnNegotiatedProtocol;
    Field npnProtocols;
    Field peerHost;
    Field peerPort;
    boolean spdyEnabled;
    Field sslNativePointer;
    Field sslParameters;
    Field useSni;

    public SpdyMiddleware(AsyncHttpClient asyncHttpClient) {
        super(asyncHttpClient);
        this.connections = new Hashtable<>();
        addEngineConfigurator(new AsyncSSLEngineConfigurator() { // from class: com.koushikdutta.async.http.spdy.SpdyMiddleware.1
            @Override // com.koushikdutta.async.http.AsyncSSLEngineConfigurator
            public SSLEngine createEngine(SSLContext sSLContext, String str, int i) {
                return null;
            }

            @Override // com.koushikdutta.async.http.AsyncSSLEngineConfigurator
            public void configureEngine(SSLEngine sSLEngine, AsyncHttpClientMiddleware.GetSocketData getSocketData, String str, int i) {
                SpdyMiddleware.this.configure(sSLEngine, getSocketData, str, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void configure(SSLEngine sSLEngine, AsyncHttpClientMiddleware.GetSocketData getSocketData, String str, int i) {
        if (!this.initialized && this.spdyEnabled) {
            this.initialized = true;
            try {
                this.peerHost = sSLEngine.getClass().getSuperclass().getDeclaredField("peerHost");
                this.peerPort = sSLEngine.getClass().getSuperclass().getDeclaredField("peerPort");
                Field declaredField = sSLEngine.getClass().getDeclaredField("sslParameters");
                this.sslParameters = declaredField;
                this.npnProtocols = declaredField.getType().getDeclaredField("npnProtocols");
                this.alpnProtocols = this.sslParameters.getType().getDeclaredField("alpnProtocols");
                this.useSni = this.sslParameters.getType().getDeclaredField("useSni");
                this.sslNativePointer = sSLEngine.getClass().getDeclaredField("sslNativePointer");
                String str2 = this.sslParameters.getType().getPackage().getName() + ".NativeCrypto";
                this.nativeGetNpnNegotiatedProtocol = Class.forName(str2, true, this.sslParameters.getType().getClassLoader()).getDeclaredMethod("SSL_get_npn_negotiated_protocol", Long.TYPE);
                this.nativeGetAlpnNegotiatedProtocol = Class.forName(str2, true, this.sslParameters.getType().getClassLoader()).getDeclaredMethod("SSL_get0_alpn_selected", Long.TYPE);
                this.peerHost.setAccessible(true);
                this.peerPort.setAccessible(true);
                this.sslParameters.setAccessible(true);
                this.npnProtocols.setAccessible(true);
                this.alpnProtocols.setAccessible(true);
                this.useSni.setAccessible(true);
                this.sslNativePointer.setAccessible(true);
                this.nativeGetNpnNegotiatedProtocol.setAccessible(true);
                this.nativeGetAlpnNegotiatedProtocol.setAccessible(true);
            } catch (Exception unused) {
                this.sslParameters = null;
                this.npnProtocols = null;
                this.alpnProtocols = null;
                this.useSni = null;
                this.sslNativePointer = null;
                this.nativeGetNpnNegotiatedProtocol = null;
                this.nativeGetAlpnNegotiatedProtocol = null;
            }
        }
        if (canSpdyRequest(getSocketData) && this.sslParameters != null) {
            try {
                byte[] bArrConcatLengthPrefixed = concatLengthPrefixed(Protocol.SPDY_3);
                this.peerHost.set(sSLEngine, str);
                this.peerPort.set(sSLEngine, Integer.valueOf(i));
                Object obj = this.sslParameters.get(sSLEngine);
                this.alpnProtocols.set(obj, bArrConcatLengthPrefixed);
                this.useSni.set(obj, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class SpdyConnectionWaiter extends MultiFuture<AsyncSpdyConnection> {
        SimpleCancellable originalCancellable;

        private SpdyConnectionWaiter() {
            this.originalCancellable = new SimpleCancellable();
        }
    }

    public boolean getSpdyEnabled() {
        return this.spdyEnabled;
    }

    public void setSpdyEnabled(boolean z) {
        this.spdyEnabled = z;
    }

    @Override // com.koushikdutta.async.http.AsyncSSLSocketMiddleware
    public void setSSLContext(SSLContext sSLContext) {
        super.setSSLContext(sSLContext);
        this.initialized = false;
    }

    static byte[] concatLengthPrefixed(Protocol... protocolArr) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8192);
        for (Protocol protocol : protocolArr) {
            if (protocol != Protocol.HTTP_1_0) {
                byteBufferAllocate.put((byte) protocol.toString().length());
                byteBufferAllocate.put(protocol.toString().getBytes(Charsets.UTF_8));
            }
        }
        byteBufferAllocate.flip();
        return new ByteBufferList(byteBufferAllocate).getAllByteArray();
    }

    private static String requestPath(Uri uri) {
        String encodedPath = uri.getEncodedPath();
        if (encodedPath == null) {
            encodedPath = "/";
        } else if (!encodedPath.startsWith("/")) {
            encodedPath = "/" + encodedPath;
        }
        return !TextUtils.isEmpty(uri.getEncodedQuery()) ? encodedPath + TypeDescription.Generic.OfWildcardType.SYMBOL + uri.getEncodedQuery() : encodedPath;
    }

    private static class NoSpdyException extends Exception {
        private NoSpdyException() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void noSpdy(String str) {
        SpdyConnectionWaiter spdyConnectionWaiterRemove = this.connections.remove(str);
        if (spdyConnectionWaiterRemove != null) {
            spdyConnectionWaiterRemove.setComplete((Exception) NO_SPDY);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeConnect(String str, ConnectCallback connectCallback, Exception exc, AsyncSSLSocket asyncSSLSocket) {
        SpdyConnectionWaiter spdyConnectionWaiter = this.connections.get(str);
        if (spdyConnectionWaiter == null || spdyConnectionWaiter.originalCancellable.setComplete()) {
            connectCallback.onConnectCompleted(exc, asyncSSLSocket);
        }
    }

    @Override // com.koushikdutta.async.http.AsyncSSLSocketMiddleware
    protected AsyncSSLSocketWrapper.HandshakeCallback createHandshakeCallback(final AsyncHttpClientMiddleware.GetSocketData getSocketData, final ConnectCallback connectCallback) {
        final String str = (String) getSocketData.state.get("spdykey");
        if (str == null) {
            return super.createHandshakeCallback(getSocketData, connectCallback);
        }
        return new AsyncSSLSocketWrapper.HandshakeCallback() { // from class: com.koushikdutta.async.http.spdy.SpdyMiddleware.2
            @Override // com.koushikdutta.async.AsyncSSLSocketWrapper.HandshakeCallback
            public void onHandshakeCompleted(Exception exc, AsyncSSLSocket asyncSSLSocket) {
                getSocketData.request.logv("checking spdy handshake");
                if (exc != null || SpdyMiddleware.this.nativeGetAlpnNegotiatedProtocol == null) {
                    SpdyMiddleware.this.invokeConnect(str, connectCallback, exc, asyncSSLSocket);
                    SpdyMiddleware.this.noSpdy(str);
                    return;
                }
                try {
                    byte[] bArr = (byte[]) SpdyMiddleware.this.nativeGetAlpnNegotiatedProtocol.invoke(null, Long.valueOf(((Long) SpdyMiddleware.this.sslNativePointer.get(asyncSSLSocket.getSSLEngine())).longValue()));
                    if (bArr == null) {
                        SpdyMiddleware.this.invokeConnect(str, connectCallback, null, asyncSSLSocket);
                        SpdyMiddleware.this.noSpdy(str);
                        return;
                    }
                    String str2 = new String(bArr);
                    Protocol protocol = Protocol.get(str2);
                    if (protocol == null || !protocol.needsSpdyConnection()) {
                        SpdyMiddleware.this.invokeConnect(str, connectCallback, null, asyncSSLSocket);
                        SpdyMiddleware.this.noSpdy(str);
                    } else {
                        try {
                            new AsyncSpdyConnection(asyncSSLSocket, Protocol.get(str2)) { // from class: com.koushikdutta.async.http.spdy.SpdyMiddleware.2.1
                                boolean hasReceivedSettings;

                                @Override // com.koushikdutta.async.http.spdy.AsyncSpdyConnection, com.koushikdutta.async.http.spdy.FrameReader.Handler
                                public void settings(boolean z, Settings settings) {
                                    super.settings(z, settings);
                                    if (this.hasReceivedSettings) {
                                        return;
                                    }
                                    this.hasReceivedSettings = true;
                                    SpdyConnectionWaiter spdyConnectionWaiter = SpdyMiddleware.this.connections.get(str);
                                    if (spdyConnectionWaiter.originalCancellable.setComplete()) {
                                        getSocketData.request.logv("using new spdy connection for host: " + getSocketData.request.getUri().getHost());
                                        SpdyMiddleware.this.newSocket(getSocketData, this, connectCallback);
                                    }
                                    spdyConnectionWaiter.setComplete(this);
                                }
                            }.sendConnectionPreface();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e2) {
                    throw new AssertionError(e2);
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void newSocket(AsyncHttpClientMiddleware.GetSocketData getSocketData, AsyncSpdyConnection asyncSpdyConnection, ConnectCallback connectCallback) {
        AsyncHttpRequest asyncHttpRequest = getSocketData.request;
        getSocketData.protocol = asyncSpdyConnection.protocol.toString();
        AsyncHttpRequestBody body = getSocketData.request.getBody();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Header(Header.TARGET_METHOD, asyncHttpRequest.getMethod()));
        arrayList.add(new Header(Header.TARGET_PATH, requestPath(asyncHttpRequest.getUri())));
        String str = asyncHttpRequest.getHeaders().get("Host");
        if (Protocol.SPDY_3 == asyncSpdyConnection.protocol) {
            arrayList.add(new Header(Header.VERSION, "HTTP/1.1"));
            arrayList.add(new Header(Header.TARGET_HOST, str));
        } else if (Protocol.HTTP_2 == asyncSpdyConnection.protocol) {
            arrayList.add(new Header(Header.TARGET_AUTHORITY, str));
        } else {
            throw new AssertionError();
        }
        arrayList.add(new Header(Header.TARGET_SCHEME, asyncHttpRequest.getUri().getScheme()));
        Multimap multiMap = asyncHttpRequest.getHeaders().getMultiMap();
        for (String str2 : multiMap.keySet()) {
            if (!SpdyTransport.isProhibitedHeader(asyncSpdyConnection.protocol, str2)) {
                Iterator it = ((List) multiMap.get(str2)).iterator();
                while (it.hasNext()) {
                    arrayList.add(new Header(str2.toLowerCase(Locale.US), (String) it.next()));
                }
            }
        }
        asyncHttpRequest.logv("\n" + asyncHttpRequest);
        connectCallback.onConnectCompleted(null, asyncSpdyConnection.newStream(arrayList, body != null, true));
    }

    private boolean canSpdyRequest(AsyncHttpClientMiddleware.GetSocketData getSocketData) {
        return getSocketData.request.getBody() == null;
    }

    @Override // com.koushikdutta.async.http.AsyncSSLSocketMiddleware, com.koushikdutta.async.http.AsyncSocketMiddleware
    protected ConnectCallback wrapCallback(AsyncHttpClientMiddleware.GetSocketData getSocketData, Uri uri, int i, boolean z, ConnectCallback connectCallback) {
        final ConnectCallback connectCallbackWrapCallback = super.wrapCallback(getSocketData, uri, i, z, connectCallback);
        final String str = (String) getSocketData.state.get("spdykey");
        return str == null ? connectCallbackWrapCallback : new ConnectCallback() { // from class: com.koushikdutta.async.http.spdy.SpdyMiddleware.3
            @Override // com.koushikdutta.async.callback.ConnectCallback
            public void onConnectCompleted(Exception exc, AsyncSocket asyncSocket) {
                SpdyConnectionWaiter spdyConnectionWaiterRemove;
                if (exc != null && (spdyConnectionWaiterRemove = SpdyMiddleware.this.connections.remove(str)) != null) {
                    spdyConnectionWaiterRemove.setComplete(exc);
                }
                connectCallbackWrapCallback.onConnectCompleted(exc, asyncSocket);
            }
        };
    }

    @Override // com.koushikdutta.async.http.AsyncSocketMiddleware, com.koushikdutta.async.http.SimpleMiddleware, com.koushikdutta.async.http.AsyncHttpClientMiddleware
    public Cancellable getSocket(final AsyncHttpClientMiddleware.GetSocketData getSocketData) {
        Uri uri = getSocketData.request.getUri();
        int schemePort = getSchemePort(getSocketData.request.getUri());
        if (schemePort == -1) {
            return null;
        }
        if (!this.spdyEnabled) {
            return super.getSocket(getSocketData);
        }
        if (!canSpdyRequest(getSocketData)) {
            return super.getSocket(getSocketData);
        }
        String str = uri.getHost() + schemePort;
        SpdyConnectionWaiter spdyConnectionWaiter = this.connections.get(str);
        if (spdyConnectionWaiter != null) {
            if (spdyConnectionWaiter.tryGetException() instanceof NoSpdyException) {
                return super.getSocket(getSocketData);
            }
            if (spdyConnectionWaiter.tryGet() != null && !spdyConnectionWaiter.tryGet().socket.isOpen()) {
                this.connections.remove(str);
                spdyConnectionWaiter = null;
            }
        }
        if (spdyConnectionWaiter == null) {
            getSocketData.state.put("spdykey", str);
            Cancellable socket = super.getSocket(getSocketData);
            if (socket.isDone() || socket.isCancelled()) {
                return socket;
            }
            SpdyConnectionWaiter spdyConnectionWaiter2 = new SpdyConnectionWaiter();
            this.connections.put(str, spdyConnectionWaiter2);
            return spdyConnectionWaiter2.originalCancellable;
        }
        getSocketData.request.logv("waiting for potential spdy connection for host: " + getSocketData.request.getUri().getHost());
        final SimpleCancellable simpleCancellable = new SimpleCancellable();
        spdyConnectionWaiter.setCallback((FutureCallback) new FutureCallback<AsyncSpdyConnection>() { // from class: com.koushikdutta.async.http.spdy.SpdyMiddleware.4
            @Override // com.koushikdutta.async.future.FutureCallback
            public void onCompleted(Exception exc, AsyncSpdyConnection asyncSpdyConnection) {
                if (exc instanceof NoSpdyException) {
                    getSocketData.request.logv("spdy not available");
                    simpleCancellable.setParent(SpdyMiddleware.super.getSocket(getSocketData));
                    return;
                }
                if (exc != null) {
                    if (simpleCancellable.setComplete()) {
                        getSocketData.connectCallback.onConnectCompleted(exc, null);
                    }
                } else {
                    getSocketData.request.logv("using existing spdy connection for host: " + getSocketData.request.getUri().getHost());
                    if (simpleCancellable.setComplete()) {
                        SpdyMiddleware spdyMiddleware = SpdyMiddleware.this;
                        AsyncHttpClientMiddleware.GetSocketData getSocketData2 = getSocketData;
                        spdyMiddleware.newSocket(getSocketData2, asyncSpdyConnection, getSocketData2.connectCallback);
                    }
                }
            }
        });
        return simpleCancellable;
    }

    @Override // com.koushikdutta.async.http.SimpleMiddleware, com.koushikdutta.async.http.AsyncHttpClientMiddleware
    public boolean exchangeHeaders(final AsyncHttpClientMiddleware.OnExchangeHeaderData onExchangeHeaderData) {
        if (!(onExchangeHeaderData.socket instanceof AsyncSpdyConnection.SpdySocket)) {
            return super.exchangeHeaders(onExchangeHeaderData);
        }
        if (onExchangeHeaderData.request.getBody() != null) {
            onExchangeHeaderData.response.sink(onExchangeHeaderData.socket);
        }
        onExchangeHeaderData.sendHeadersCallback.onCompleted(null);
        final AsyncSpdyConnection.SpdySocket spdySocket = (AsyncSpdyConnection.SpdySocket) onExchangeHeaderData.socket;
        ((AnonymousClass6) spdySocket.headers().then(new TransformFuture<Headers, List<Header>>() { // from class: com.koushikdutta.async.http.spdy.SpdyMiddleware.6
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.koushikdutta.async.future.TransformFuture
            public void transform(List<Header> list) throws Exception {
                Headers headers = new Headers();
                for (Header header : list) {
                    headers.add(header.f215name.utf8(), header.value.utf8());
                }
                String[] strArrSplit = headers.remove(Header.RESPONSE_STATUS.utf8()).split(" ", 2);
                onExchangeHeaderData.response.code(Integer.parseInt(strArrSplit[0]));
                if (strArrSplit.length == 2) {
                    onExchangeHeaderData.response.message(strArrSplit[1]);
                }
                onExchangeHeaderData.response.protocol(headers.remove(Header.VERSION.utf8()));
                onExchangeHeaderData.response.headers(headers);
                setComplete(headers);
            }
        })).setCallback((FutureCallback) new FutureCallback<Headers>() { // from class: com.koushikdutta.async.http.spdy.SpdyMiddleware.5
            @Override // com.koushikdutta.async.future.FutureCallback
            public void onCompleted(Exception exc, Headers headers) {
                onExchangeHeaderData.receiveHeadersCallback.onCompleted(exc);
                AsyncSpdyConnection.SpdySocket spdySocket2 = spdySocket;
                onExchangeHeaderData.response.emitter(HttpUtil.getBodyDecoder(spdySocket2, spdySocket2.getConnection().protocol, headers, false));
            }
        });
        return true;
    }

    @Override // com.koushikdutta.async.http.SimpleMiddleware, com.koushikdutta.async.http.AsyncHttpClientMiddleware
    public void onRequestSent(AsyncHttpClientMiddleware.OnRequestSentData onRequestSentData) {
        if ((onRequestSentData.socket instanceof AsyncSpdyConnection.SpdySocket) && onRequestSentData.request.getBody() != null) {
            onRequestSentData.response.sink().end();
        }
    }
}
