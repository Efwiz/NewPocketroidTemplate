package com.koushikdutta.async.http;

import android.net.Uri;
import android.text.TextUtils;
import com.koushikdutta.async.AsyncSSLException;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ConnectCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.future.SimpleFuture;
import com.koushikdutta.async.http.callback.HttpConnectCallback;
import com.koushikdutta.async.http.callback.RequestCallback;
import com.koushikdutta.async.http.spdy.SpdyMiddleware;
import com.koushikdutta.async.parser.AsyncParser;
import com.koushikdutta.async.parser.ByteBufferListParser;
import com.koushikdutta.async.parser.JSONArrayParser;
import com.koushikdutta.async.parser.JSONObjectParser;
import com.koushikdutta.async.parser.StringParser;
import com.koushikdutta.async.stream.OutputStreamDataCallback;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncHttpClient {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String LOGTAG = "AsyncHttp";
    private static AsyncHttpClient mDefaultInstance;
    HttpTransportMiddleware httpTransportMiddleware;
    final List<AsyncHttpClientMiddleware> mMiddleware = new CopyOnWriteArrayList();
    AsyncServer mServer;
    AsyncSocketMiddleware socketMiddleware;
    SpdyMiddleware sslSocketMiddleware;

    public static abstract class DownloadCallback extends RequestCallbackBase<ByteBufferList> {
    }

    public static abstract class FileCallback extends RequestCallbackBase<File> {
    }

    public static abstract class JSONArrayCallback extends RequestCallbackBase<JSONArray> {
    }

    public static abstract class JSONObjectCallback extends RequestCallbackBase<JSONObject> {
    }

    public static abstract class RequestCallbackBase<T> implements RequestCallback<T> {
        @Override // com.koushikdutta.async.http.callback.RequestCallback
        public void onConnect(AsyncHttpResponse asyncHttpResponse) {
        }

        @Override // com.koushikdutta.async.http.callback.RequestCallback
        public void onProgress(AsyncHttpResponse asyncHttpResponse, long j, long j2) {
        }
    }

    public static abstract class StringCallback extends RequestCallbackBase<String> {
    }

    public interface WebSocketConnectCallback {
        void onCompleted(Exception exc, WebSocket webSocket);
    }

    public static AsyncHttpClient getDefaultInstance() {
        if (mDefaultInstance == null) {
            mDefaultInstance = new AsyncHttpClient(AsyncServer.getDefault());
        }
        return mDefaultInstance;
    }

    public Collection<AsyncHttpClientMiddleware> getMiddleware() {
        return this.mMiddleware;
    }

    public void insertMiddleware(AsyncHttpClientMiddleware asyncHttpClientMiddleware) {
        this.mMiddleware.add(0, asyncHttpClientMiddleware);
    }

    public AsyncHttpClient(AsyncServer asyncServer) {
        this.mServer = asyncServer;
        AsyncSocketMiddleware asyncSocketMiddleware = new AsyncSocketMiddleware(this);
        this.socketMiddleware = asyncSocketMiddleware;
        insertMiddleware(asyncSocketMiddleware);
        SpdyMiddleware spdyMiddleware = new SpdyMiddleware(this);
        this.sslSocketMiddleware = spdyMiddleware;
        insertMiddleware(spdyMiddleware);
        HttpTransportMiddleware httpTransportMiddleware = new HttpTransportMiddleware();
        this.httpTransportMiddleware = httpTransportMiddleware;
        insertMiddleware(httpTransportMiddleware);
        this.sslSocketMiddleware.addEngineConfigurator(new SSLEngineSNIConfigurator());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setupAndroidProxy(AsyncHttpRequest asyncHttpRequest) {
        if (asyncHttpRequest.proxyHost != null) {
            return;
        }
        try {
            List<Proxy> listSelect = ProxySelector.getDefault().select(URI.create(asyncHttpRequest.getUri().toString()));
            if (listSelect.isEmpty()) {
                return;
            }
            Proxy proxy = listSelect.get(0);
            if (proxy.type() == Proxy.Type.HTTP && (proxy.address() instanceof InetSocketAddress)) {
                InetSocketAddress inetSocketAddress = (InetSocketAddress) proxy.address();
                asyncHttpRequest.enableProxy(inetSocketAddress.getHostString(), inetSocketAddress.getPort());
            }
        } catch (Exception unused) {
        }
    }

    public AsyncSocketMiddleware getSocketMiddleware() {
        return this.socketMiddleware;
    }

    public SpdyMiddleware getSSLSocketMiddleware() {
        return this.sslSocketMiddleware;
    }

    public Future<AsyncHttpResponse> execute(AsyncHttpRequest asyncHttpRequest, HttpConnectCallback httpConnectCallback) {
        FutureAsyncHttpResponse futureAsyncHttpResponse = new FutureAsyncHttpResponse();
        execute(asyncHttpRequest, 0, futureAsyncHttpResponse, httpConnectCallback);
        return futureAsyncHttpResponse;
    }

    public Future<AsyncHttpResponse> execute(String str, HttpConnectCallback httpConnectCallback) {
        return execute(new AsyncHttpGet(str), httpConnectCallback);
    }

    private class FutureAsyncHttpResponse extends SimpleFuture<AsyncHttpResponse> {
        public Object scheduled;
        public AsyncSocket socket;
        public Runnable timeoutRunnable;

        private FutureAsyncHttpResponse() {
        }

        @Override // com.koushikdutta.async.future.SimpleFuture, com.koushikdutta.async.future.SimpleCancellable, com.koushikdutta.async.future.Cancellable
        public boolean cancel() {
            if (!super.cancel()) {
                return false;
            }
            AsyncSocket asyncSocket = this.socket;
            if (asyncSocket != null) {
                asyncSocket.setDataCallback(new DataCallback.NullDataCallback());
                this.socket.close();
            }
            if (this.scheduled == null) {
                return true;
            }
            AsyncHttpClient.this.mServer.removeAllCallbacks(this.scheduled);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportConnectedCompleted(FutureAsyncHttpResponse futureAsyncHttpResponse, Exception exc, AsyncHttpResponseImpl asyncHttpResponseImpl, AsyncHttpRequest asyncHttpRequest, HttpConnectCallback httpConnectCallback) {
        boolean complete;
        this.mServer.removeAllCallbacks(futureAsyncHttpResponse.scheduled);
        if (exc != null) {
            asyncHttpRequest.loge("Connection error", exc);
            complete = futureAsyncHttpResponse.setComplete(exc);
        } else {
            asyncHttpRequest.logd("Connection successful");
            complete = futureAsyncHttpResponse.setComplete(asyncHttpResponseImpl);
        }
        if (complete) {
            httpConnectCallback.onConnectCompleted(exc, asyncHttpResponseImpl);
        } else if (asyncHttpResponseImpl != null) {
            asyncHttpResponseImpl.setDataCallback(new DataCallback.NullDataCallback());
            asyncHttpResponseImpl.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void execute(final AsyncHttpRequest asyncHttpRequest, final int i, final FutureAsyncHttpResponse futureAsyncHttpResponse, final HttpConnectCallback httpConnectCallback) {
        if (this.mServer.isAffinityThread()) {
            executeAffinity(asyncHttpRequest, i, futureAsyncHttpResponse, httpConnectCallback);
        } else {
            this.mServer.post(new Runnable() { // from class: com.koushikdutta.async.http.AsyncHttpClient.1
                @Override // java.lang.Runnable
                public void run() {
                    AsyncHttpClient.this.executeAffinity(asyncHttpRequest, i, futureAsyncHttpResponse, httpConnectCallback);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long getTimeoutRemaining(AsyncHttpRequest asyncHttpRequest) {
        return asyncHttpRequest.getTimeout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void copyHeader(AsyncHttpRequest asyncHttpRequest, AsyncHttpRequest asyncHttpRequest2, String str) {
        String str2 = asyncHttpRequest.getHeaders().get(str);
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        asyncHttpRequest2.getHeaders().set(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeAffinity(final AsyncHttpRequest asyncHttpRequest, final int i, final FutureAsyncHttpResponse futureAsyncHttpResponse, final HttpConnectCallback httpConnectCallback) {
        if (i > 15) {
            reportConnectedCompleted(futureAsyncHttpResponse, new RedirectLimitExceededException("too many redirects"), null, asyncHttpRequest, httpConnectCallback);
            return;
        }
        asyncHttpRequest.getUri();
        final AsyncHttpClientMiddleware.OnResponseCompleteDataOnRequestSentData onResponseCompleteDataOnRequestSentData = new AsyncHttpClientMiddleware.OnResponseCompleteDataOnRequestSentData();
        asyncHttpRequest.executionTime = System.currentTimeMillis();
        onResponseCompleteDataOnRequestSentData.request = asyncHttpRequest;
        asyncHttpRequest.logd("Executing request.");
        Iterator<AsyncHttpClientMiddleware> it = this.mMiddleware.iterator();
        while (it.hasNext()) {
            it.next().onRequest(onResponseCompleteDataOnRequestSentData);
        }
        if (asyncHttpRequest.getTimeout() > 0) {
            futureAsyncHttpResponse.timeoutRunnable = new Runnable() { // from class: com.koushikdutta.async.http.AsyncHttpClient.2
                @Override // java.lang.Runnable
                public void run() {
                    if (onResponseCompleteDataOnRequestSentData.socketCancellable != null) {
                        onResponseCompleteDataOnRequestSentData.socketCancellable.cancel();
                        if (onResponseCompleteDataOnRequestSentData.socket != null) {
                            onResponseCompleteDataOnRequestSentData.socket.close();
                        }
                    }
                    AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, new TimeoutException(), null, asyncHttpRequest, httpConnectCallback);
                }
            };
            futureAsyncHttpResponse.scheduled = this.mServer.postDelayed(futureAsyncHttpResponse.timeoutRunnable, getTimeoutRemaining(asyncHttpRequest));
        }
        onResponseCompleteDataOnRequestSentData.connectCallback = new ConnectCallback() { // from class: com.koushikdutta.async.http.AsyncHttpClient.3
            boolean reported;

            @Override // com.koushikdutta.async.callback.ConnectCallback
            public void onConnectCompleted(Exception exc, AsyncSocket asyncSocket) {
                if (this.reported && asyncSocket != null) {
                    asyncSocket.setDataCallback(new DataCallback.NullDataCallback());
                    asyncSocket.setEndCallback(new CompletedCallback.NullCompletedCallback());
                    asyncSocket.close();
                    throw new AssertionError("double connect callback");
                }
                this.reported = true;
                asyncHttpRequest.logv("socket connected");
                if (futureAsyncHttpResponse.isCancelled()) {
                    if (asyncSocket != null) {
                        asyncSocket.close();
                        return;
                    }
                    return;
                }
                if (futureAsyncHttpResponse.timeoutRunnable != null) {
                    AsyncHttpClient.this.mServer.removeAllCallbacks(futureAsyncHttpResponse.scheduled);
                }
                if (exc != null) {
                    AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, exc, null, asyncHttpRequest, httpConnectCallback);
                    return;
                }
                onResponseCompleteDataOnRequestSentData.socket = asyncSocket;
                futureAsyncHttpResponse.socket = asyncSocket;
                AsyncHttpClient.this.executeSocket(asyncHttpRequest, i, futureAsyncHttpResponse, httpConnectCallback, onResponseCompleteDataOnRequestSentData);
            }
        };
        setupAndroidProxy(asyncHttpRequest);
        if (asyncHttpRequest.getBody() != null && asyncHttpRequest.getHeaders().get("Content-Type") == null) {
            asyncHttpRequest.getHeaders().set("Content-Type", asyncHttpRequest.getBody().getContentType());
        }
        Iterator<AsyncHttpClientMiddleware> it2 = this.mMiddleware.iterator();
        while (it2.hasNext()) {
            Cancellable socket = it2.next().getSocket(onResponseCompleteDataOnRequestSentData);
            if (socket != null) {
                onResponseCompleteDataOnRequestSentData.socketCancellable = socket;
                futureAsyncHttpResponse.setParent(socket);
                return;
            }
        }
        reportConnectedCompleted(futureAsyncHttpResponse, new IllegalArgumentException("invalid uri=" + asyncHttpRequest.getUri() + " middlewares=" + this.mMiddleware), null, asyncHttpRequest, httpConnectCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeSocket(final AsyncHttpRequest asyncHttpRequest, final int i, final FutureAsyncHttpResponse futureAsyncHttpResponse, final HttpConnectCallback httpConnectCallback, final AsyncHttpClientMiddleware.OnResponseCompleteDataOnRequestSentData onResponseCompleteDataOnRequestSentData) {
        final AsyncHttpResponseImpl asyncHttpResponseImpl = new AsyncHttpResponseImpl(asyncHttpRequest) { // from class: com.koushikdutta.async.http.AsyncHttpClient.4
            @Override // com.koushikdutta.async.http.AsyncHttpResponseImpl
            protected void onRequestCompleted(Exception exc) {
                if (exc != null) {
                    AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, exc, null, asyncHttpRequest, httpConnectCallback);
                    return;
                }
                asyncHttpRequest.logv("request completed");
                if (futureAsyncHttpResponse.isCancelled()) {
                    return;
                }
                if (futureAsyncHttpResponse.timeoutRunnable != null && this.mHeaders == null) {
                    AsyncHttpClient.this.mServer.removeAllCallbacks(futureAsyncHttpResponse.scheduled);
                    futureAsyncHttpResponse.scheduled = AsyncHttpClient.this.mServer.postDelayed(futureAsyncHttpResponse.timeoutRunnable, AsyncHttpClient.getTimeoutRemaining(asyncHttpRequest));
                }
                Iterator<AsyncHttpClientMiddleware> it = AsyncHttpClient.this.mMiddleware.iterator();
                while (it.hasNext()) {
                    it.next().onRequestSent(onResponseCompleteDataOnRequestSentData);
                }
            }

            @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataTrackingEmitter
            public void setDataEmitter(DataEmitter dataEmitter) {
                onResponseCompleteDataOnRequestSentData.bodyEmitter = dataEmitter;
                Iterator<AsyncHttpClientMiddleware> it = AsyncHttpClient.this.mMiddleware.iterator();
                while (it.hasNext()) {
                    it.next().onBodyDecoder(onResponseCompleteDataOnRequestSentData);
                }
                super.setDataEmitter(onResponseCompleteDataOnRequestSentData.bodyEmitter);
                Headers headers = this.mHeaders;
                int iCode = code();
                if ((iCode == 301 || iCode == 302 || iCode == 307) && asyncHttpRequest.getFollowRedirect()) {
                    String str = headers.get("Location");
                    try {
                        Uri uri = Uri.parse(str);
                        if (uri.getScheme() == null) {
                            uri = Uri.parse(new URL(new URL(asyncHttpRequest.getUri().toString()), str).toString());
                        }
                        AsyncHttpRequest asyncHttpRequest2 = new AsyncHttpRequest(uri, asyncHttpRequest.getMethod().equals("HEAD") ? "HEAD" : "GET");
                        asyncHttpRequest2.executionTime = asyncHttpRequest.executionTime;
                        asyncHttpRequest2.logLevel = asyncHttpRequest.logLevel;
                        asyncHttpRequest2.LOGTAG = asyncHttpRequest.LOGTAG;
                        asyncHttpRequest2.proxyHost = asyncHttpRequest.proxyHost;
                        asyncHttpRequest2.proxyPort = asyncHttpRequest.proxyPort;
                        AsyncHttpClient.setupAndroidProxy(asyncHttpRequest2);
                        AsyncHttpClient.copyHeader(asyncHttpRequest, asyncHttpRequest2, "User-Agent");
                        AsyncHttpClient.copyHeader(asyncHttpRequest, asyncHttpRequest2, "Range");
                        asyncHttpRequest.logi("Redirecting");
                        asyncHttpRequest2.logi("Redirected");
                        AsyncHttpClient.this.execute(asyncHttpRequest2, i + 1, futureAsyncHttpResponse, httpConnectCallback);
                        setDataCallback(new DataCallback.NullDataCallback());
                        return;
                    } catch (Exception e) {
                        AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, e, this, asyncHttpRequest, httpConnectCallback);
                        return;
                    }
                }
                asyncHttpRequest.logv("Final (post cache response) headers:\n" + toString());
                AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, null, this, asyncHttpRequest, httpConnectCallback);
            }

            @Override // com.koushikdutta.async.http.AsyncHttpResponseImpl
            protected void onHeadersReceived() {
                super.onHeadersReceived();
                if (futureAsyncHttpResponse.isCancelled()) {
                    return;
                }
                if (futureAsyncHttpResponse.timeoutRunnable != null) {
                    AsyncHttpClient.this.mServer.removeAllCallbacks(futureAsyncHttpResponse.scheduled);
                }
                asyncHttpRequest.logv("Received headers:\n" + toString());
                Iterator<AsyncHttpClientMiddleware> it = AsyncHttpClient.this.mMiddleware.iterator();
                while (it.hasNext()) {
                    it.next().onHeadersReceived(onResponseCompleteDataOnRequestSentData);
                }
            }

            @Override // com.koushikdutta.async.http.AsyncHttpResponseImpl, com.koushikdutta.async.DataEmitterBase
            protected void report(Exception exc) {
                if (exc != null) {
                    asyncHttpRequest.loge("exception during response", exc);
                }
                if (futureAsyncHttpResponse.isCancelled()) {
                    return;
                }
                if (exc instanceof AsyncSSLException) {
                    asyncHttpRequest.loge("SSL Exception", exc);
                    AsyncSSLException asyncSSLException = (AsyncSSLException) exc;
                    asyncHttpRequest.onHandshakeException(asyncSSLException);
                    if (asyncSSLException.getIgnore()) {
                        return;
                    }
                }
                AsyncSocket asyncSocketSocket = socket();
                if (asyncSocketSocket == null) {
                    return;
                }
                super.report(exc);
                if ((!asyncSocketSocket.isOpen() || exc != null) && headers() == null && exc != null) {
                    AsyncHttpClient.this.reportConnectedCompleted(futureAsyncHttpResponse, exc, null, asyncHttpRequest, httpConnectCallback);
                }
                onResponseCompleteDataOnRequestSentData.exception = exc;
                Iterator<AsyncHttpClientMiddleware> it = AsyncHttpClient.this.mMiddleware.iterator();
                while (it.hasNext()) {
                    it.next().onResponseComplete(onResponseCompleteDataOnRequestSentData);
                }
            }

            @Override // com.koushikdutta.async.http.AsyncHttpResponse
            public AsyncSocket detachSocket() {
                asyncHttpRequest.logd("Detaching socket");
                AsyncSocket asyncSocketSocket = socket();
                if (asyncSocketSocket == null) {
                    return null;
                }
                asyncSocketSocket.setWriteableCallback(null);
                asyncSocketSocket.setClosedCallback(null);
                asyncSocketSocket.setEndCallback(null);
                asyncSocketSocket.setDataCallback(null);
                setSocket(null);
                return asyncSocketSocket;
            }
        };
        onResponseCompleteDataOnRequestSentData.sendHeadersCallback = new CompletedCallback() { // from class: com.koushikdutta.async.http.AsyncHttpClient.5
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                if (exc != null) {
                    asyncHttpResponseImpl.report(exc);
                } else {
                    asyncHttpResponseImpl.onHeadersSent();
                }
            }
        };
        onResponseCompleteDataOnRequestSentData.receiveHeadersCallback = new CompletedCallback() { // from class: com.koushikdutta.async.http.AsyncHttpClient.6
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                if (exc != null) {
                    asyncHttpResponseImpl.report(exc);
                } else {
                    asyncHttpResponseImpl.onHeadersReceived();
                }
            }
        };
        onResponseCompleteDataOnRequestSentData.response = asyncHttpResponseImpl;
        asyncHttpResponseImpl.setSocket(onResponseCompleteDataOnRequestSentData.socket);
        Iterator<AsyncHttpClientMiddleware> it = this.mMiddleware.iterator();
        while (it.hasNext() && !it.next().exchangeHeaders(onResponseCompleteDataOnRequestSentData)) {
        }
    }

    public Future<ByteBufferList> executeByteBufferList(AsyncHttpRequest asyncHttpRequest, DownloadCallback downloadCallback) {
        return execute(asyncHttpRequest, new ByteBufferListParser(), downloadCallback);
    }

    public Future<String> executeString(AsyncHttpRequest asyncHttpRequest, StringCallback stringCallback) {
        return execute(asyncHttpRequest, new StringParser(), stringCallback);
    }

    public Future<JSONObject> executeJSONObject(AsyncHttpRequest asyncHttpRequest, JSONObjectCallback jSONObjectCallback) {
        return execute(asyncHttpRequest, new JSONObjectParser(), jSONObjectCallback);
    }

    public Future<JSONArray> executeJSONArray(AsyncHttpRequest asyncHttpRequest, JSONArrayCallback jSONArrayCallback) {
        return execute(asyncHttpRequest, new JSONArrayParser(), jSONArrayCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> void invokeWithAffinity(RequestCallback<T> requestCallback, SimpleFuture<T> simpleFuture, AsyncHttpResponse asyncHttpResponse, Exception exc, T t) {
        boolean complete;
        if (exc != null) {
            complete = simpleFuture.setComplete(exc);
        } else {
            complete = simpleFuture.setComplete(t);
        }
        if (complete && requestCallback != null) {
            requestCallback.onCompleted(exc, asyncHttpResponse, t);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> void invoke(final RequestCallback<T> requestCallback, final SimpleFuture<T> simpleFuture, final AsyncHttpResponse asyncHttpResponse, final Exception exc, final T t) {
        this.mServer.post(new Runnable() { // from class: com.koushikdutta.async.http.AsyncHttpClient.7
            @Override // java.lang.Runnable
            public void run() {
                AsyncHttpClient.this.invokeWithAffinity(requestCallback, simpleFuture, asyncHttpResponse, exc, t);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeProgress(RequestCallback requestCallback, AsyncHttpResponse asyncHttpResponse, long j, long j2) {
        if (requestCallback != null) {
            requestCallback.onProgress(asyncHttpResponse, j, j2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invokeConnect(RequestCallback requestCallback, AsyncHttpResponse asyncHttpResponse) {
        if (requestCallback != null) {
            requestCallback.onConnect(asyncHttpResponse);
        }
    }

    public Future<File> executeFile(AsyncHttpRequest asyncHttpRequest, String str, FileCallback fileCallback) {
        final File file = new File(str);
        file.getParentFile().mkdirs();
        try {
            final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), 8192);
            final FutureAsyncHttpResponse futureAsyncHttpResponse = new FutureAsyncHttpResponse();
            SimpleFuture<File> simpleFuture = new SimpleFuture<File>() { // from class: com.koushikdutta.async.http.AsyncHttpClient.8
                @Override // com.koushikdutta.async.future.SimpleCancellable
                public void cancelCleanup() {
                    try {
                        futureAsyncHttpResponse.get().setDataCallback(new DataCallback.NullDataCallback());
                        futureAsyncHttpResponse.get().close();
                    } catch (Exception unused) {
                    }
                    try {
                        bufferedOutputStream.close();
                    } catch (Exception unused2) {
                    }
                    file.delete();
                }
            };
            simpleFuture.setParent((Cancellable) futureAsyncHttpResponse);
            execute(asyncHttpRequest, 0, futureAsyncHttpResponse, new AnonymousClass9(bufferedOutputStream, file, fileCallback, simpleFuture));
            return simpleFuture;
        } catch (FileNotFoundException e) {
            SimpleFuture simpleFuture2 = new SimpleFuture();
            simpleFuture2.setComplete((Exception) e);
            return simpleFuture2;
        }
    }

    /* JADX INFO: renamed from: com.koushikdutta.async.http.AsyncHttpClient$9, reason: invalid class name */
    class AnonymousClass9 implements HttpConnectCallback {
        long mDownloaded = 0;
        final /* synthetic */ FileCallback val$callback;
        final /* synthetic */ File val$file;
        final /* synthetic */ OutputStream val$fout;
        final /* synthetic */ SimpleFuture val$ret;

        AnonymousClass9(OutputStream outputStream, File file, FileCallback fileCallback, SimpleFuture simpleFuture) {
            this.val$fout = outputStream;
            this.val$file = file;
            this.val$callback = fileCallback;
            this.val$ret = simpleFuture;
        }

        @Override // com.koushikdutta.async.http.callback.HttpConnectCallback
        public void onConnectCompleted(Exception exc, final AsyncHttpResponse asyncHttpResponse) {
            if (exc == null) {
                AsyncHttpClient.this.invokeConnect(this.val$callback, asyncHttpResponse);
                final long jContentLength = HttpUtil.contentLength(asyncHttpResponse.headers());
                asyncHttpResponse.setDataCallback(new OutputStreamDataCallback(this.val$fout) { // from class: com.koushikdutta.async.http.AsyncHttpClient.9.1
                    @Override // com.koushikdutta.async.stream.OutputStreamDataCallback, com.koushikdutta.async.callback.DataCallback
                    public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                        AnonymousClass9.this.mDownloaded += (long) byteBufferList.remaining();
                        super.onDataAvailable(dataEmitter, byteBufferList);
                        AsyncHttpClient.this.invokeProgress(AnonymousClass9.this.val$callback, asyncHttpResponse, AnonymousClass9.this.mDownloaded, jContentLength);
                    }
                });
                asyncHttpResponse.setEndCallback(new CompletedCallback() { // from class: com.koushikdutta.async.http.AsyncHttpClient.9.2
                    @Override // com.koushikdutta.async.callback.CompletedCallback
                    public void onCompleted(Exception e) {
                        try {
                            AnonymousClass9.this.val$fout.close();
                        } catch (IOException e2) {
                            e = e2;
                        }
                        Exception exc2 = e;
                        if (exc2 == null) {
                            AsyncHttpClient.this.invoke(AnonymousClass9.this.val$callback, AnonymousClass9.this.val$ret, asyncHttpResponse, null, AnonymousClass9.this.val$file);
                        } else {
                            AnonymousClass9.this.val$file.delete();
                            AsyncHttpClient.this.invoke(AnonymousClass9.this.val$callback, AnonymousClass9.this.val$ret, asyncHttpResponse, exc2, null);
                        }
                    }
                });
                return;
            }
            try {
                this.val$fout.close();
            } catch (IOException unused) {
            }
            this.val$file.delete();
            AsyncHttpClient.this.invoke(this.val$callback, this.val$ret, asyncHttpResponse, exc, null);
        }
    }

    public <T> SimpleFuture<T> execute(AsyncHttpRequest asyncHttpRequest, final AsyncParser<T> asyncParser, final RequestCallback<T> requestCallback) {
        FutureAsyncHttpResponse futureAsyncHttpResponse = new FutureAsyncHttpResponse();
        final SimpleFuture<T> simpleFuture = new SimpleFuture<>();
        execute(asyncHttpRequest, 0, futureAsyncHttpResponse, new HttpConnectCallback() { // from class: com.koushikdutta.async.http.AsyncHttpClient.10
            @Override // com.koushikdutta.async.http.callback.HttpConnectCallback
            public void onConnectCompleted(Exception exc, final AsyncHttpResponse asyncHttpResponse) {
                if (exc != null) {
                    AsyncHttpClient.this.invoke(requestCallback, simpleFuture, asyncHttpResponse, exc, null);
                    return;
                }
                AsyncHttpClient.this.invokeConnect(requestCallback, asyncHttpResponse);
                simpleFuture.setParent((Cancellable) asyncParser.parse(asyncHttpResponse).setCallback(new FutureCallback<T>() { // from class: com.koushikdutta.async.http.AsyncHttpClient.10.1
                    @Override // com.koushikdutta.async.future.FutureCallback
                    public void onCompleted(Exception exc2, T t) {
                        AsyncHttpClient.this.invoke(requestCallback, simpleFuture, asyncHttpResponse, exc2, t);
                    }
                }));
            }
        });
        simpleFuture.setParent((Cancellable) futureAsyncHttpResponse);
        return simpleFuture;
    }

    public Future<WebSocket> websocket(final AsyncHttpRequest asyncHttpRequest, String str, final WebSocketConnectCallback webSocketConnectCallback) {
        WebSocketImpl.addWebSocketUpgradeHeaders(asyncHttpRequest, str);
        final SimpleFuture simpleFuture = new SimpleFuture();
        simpleFuture.setParent((Cancellable) execute(asyncHttpRequest, new HttpConnectCallback() { // from class: com.koushikdutta.async.http.AsyncHttpClient.11
            @Override // com.koushikdutta.async.http.callback.HttpConnectCallback
            public void onConnectCompleted(Exception exc, AsyncHttpResponse asyncHttpResponse) {
                WebSocketConnectCallback webSocketConnectCallback2;
                if (exc != null) {
                    if (!simpleFuture.setComplete(exc) || (webSocketConnectCallback2 = webSocketConnectCallback) == null) {
                        return;
                    }
                    webSocketConnectCallback2.onCompleted(exc, null);
                    return;
                }
                WebSocket webSocketFinishHandshake = WebSocketImpl.finishHandshake(asyncHttpRequest.getHeaders(), asyncHttpResponse);
                if (webSocketFinishHandshake == null) {
                    exc = new WebSocketHandshakeException("Unable to complete websocket handshake");
                    if (!simpleFuture.setComplete(exc)) {
                        return;
                    }
                } else if (!simpleFuture.setComplete(webSocketFinishHandshake)) {
                    return;
                }
                WebSocketConnectCallback webSocketConnectCallback3 = webSocketConnectCallback;
                if (webSocketConnectCallback3 != null) {
                    webSocketConnectCallback3.onCompleted(exc, webSocketFinishHandshake);
                }
            }
        }));
        return simpleFuture;
    }

    public Future<WebSocket> websocket(String str, String str2, WebSocketConnectCallback webSocketConnectCallback) {
        return websocket(new AsyncHttpGet(str.replace("ws://", "http://").replace("wss://", "https://")), str2, webSocketConnectCallback);
    }

    public AsyncServer getServer() {
        return this.mServer;
    }
}
