package com.koushikdutta.async.http.server;

import android.content.Context;
import android.text.TextUtils;
import com.google.common.net.HttpHeaders;
import com.koushikdutta.async.AsyncSSLSocket;
import com.koushikdutta.async.AsyncSSLSocketWrapper;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncServerSocket;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.ListenCallback;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.HttpUtil;
import com.koushikdutta.async.http.Multimap;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.WebSocketImpl;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import com.koushikdutta.async.util.StreamUtility;
import com.reandroid.apkeditor.Options;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLContext;
import org.apache.xml.serialize.Method;

/* JADX INFO: loaded from: classes10.dex */
public class AsyncHttpServer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static Hashtable<Integer, String> mCodes;
    static Hashtable<String, String> mContentTypes = new Hashtable<>();
    CompletedCallback mCompletedCallback;
    ArrayList<AsyncServerSocket> mListeners = new ArrayList<>();
    ListenCallback mListenCallback = new AnonymousClass1();
    final Hashtable<String, ArrayList<Pair>> mActions = new Hashtable<>();

    public interface WebSocketRequestCallback {
        void onConnected(WebSocket webSocket, AsyncHttpServerRequest asyncHttpServerRequest);
    }

    protected boolean onRequest(AsyncHttpServerRequest asyncHttpServerRequest, AsyncHttpServerResponse asyncHttpServerResponse) {
        return false;
    }

    public AsyncHttpServer() {
        mContentTypes.put("js", "application/javascript");
        mContentTypes.put(Options.TYPE_JSON, "application/json");
        mContentTypes.put("png", "image/png");
        mContentTypes.put("jpg", "image/jpeg");
        mContentTypes.put(Method.HTML, "text/html");
        mContentTypes.put("css", "text/css");
        mContentTypes.put("mp4", "video/mp4");
        mContentTypes.put("mov", "video/quicktime");
        mContentTypes.put("wmv", "video/x-ms-wmv");
    }

    public void stop() {
        ArrayList<AsyncServerSocket> arrayList = this.mListeners;
        if (arrayList != null) {
            Iterator<AsyncServerSocket> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().stop();
            }
        }
    }

    protected void onRequest(HttpServerRequestCallback httpServerRequestCallback, AsyncHttpServerRequest asyncHttpServerRequest, AsyncHttpServerResponse asyncHttpServerResponse) {
        if (httpServerRequestCallback != null) {
            httpServerRequestCallback.onRequest(asyncHttpServerRequest, asyncHttpServerResponse);
        }
    }

    protected AsyncHttpRequestBody onUnknownBody(Headers headers) {
        return new UnknownRequestBody(headers.get("Content-Type"));
    }

    /* JADX INFO: renamed from: com.koushikdutta.async.http.server.AsyncHttpServer$1, reason: invalid class name */
    class AnonymousClass1 implements ListenCallback {
        AnonymousClass1() {
        }

        /* JADX INFO: renamed from: com.koushikdutta.async.http.server.AsyncHttpServer$1$1, reason: invalid class name and collision with other inner class name */
        class C01331 extends AsyncHttpServerRequestImpl {
            String fullPath;
            boolean hasContinued;
            HttpServerRequestCallback match;
            String path;
            boolean requestComplete;
            AsyncHttpServerResponseImpl res;
            boolean responseComplete;
            final /* synthetic */ AsyncSocket val$socket;

            C01331(AsyncSocket asyncSocket) {
                this.val$socket = asyncSocket;
            }

            @Override // com.koushikdutta.async.http.server.AsyncHttpServerRequestImpl
            protected AsyncHttpRequestBody onUnknownBody(Headers headers) {
                return AsyncHttpServer.this.onUnknownBody(headers);
            }

            @Override // com.koushikdutta.async.http.server.AsyncHttpServerRequestImpl
            protected void onHeadersReceived() {
                Headers headers = getHeaders();
                if (!this.hasContinued && "100-continue".equals(headers.get("Expect"))) {
                    pause();
                    Util.writeAll(this.mSocket, "HTTP/1.1 100 Continue\r\n\r\n".getBytes(), new CompletedCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.1.1.1
                        @Override // com.koushikdutta.async.callback.CompletedCallback
                        public void onCompleted(Exception exc) {
                            C01331.this.resume();
                            if (exc != null) {
                                C01331.this.report(exc);
                            } else {
                                C01331.this.hasContinued = true;
                                C01331.this.onHeadersReceived();
                            }
                        }
                    });
                    return;
                }
                String[] strArrSplit = getStatusLine().split(" ");
                String str = strArrSplit[1];
                this.fullPath = str;
                this.path = str.split("\\?")[0];
                this.method = strArrSplit[0];
                synchronized (AsyncHttpServer.this.mActions) {
                    ArrayList<Pair> arrayList = AsyncHttpServer.this.mActions.get(this.method);
                    if (arrayList != null) {
                        for (Pair pair : arrayList) {
                            Matcher matcher = pair.regex.matcher(this.path);
                            if (matcher.matches()) {
                                this.mMatcher = matcher;
                                this.match = pair.callback;
                                break;
                            }
                        }
                    }
                }
                this.res = new AsyncHttpServerResponseImpl(this.val$socket, this) { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.1.1.2
                    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponseImpl
                    protected void report(Exception exc) {
                        super.report(exc);
                        if (exc != null) {
                            C01331.this.val$socket.setDataCallback(new DataCallback.NullDataCallback());
                            C01331.this.val$socket.setEndCallback(new CompletedCallback.NullCompletedCallback());
                            C01331.this.val$socket.close();
                        }
                    }

                    @Override // com.koushikdutta.async.http.server.AsyncHttpServerResponseImpl
                    protected void onEnd() {
                        super.onEnd();
                        this.mSocket.setEndCallback(null);
                        C01331.this.responseComplete = true;
                        C01331.this.handleOnCompleted();
                    }
                };
                boolean zOnRequest = AsyncHttpServer.this.onRequest(this, this.res);
                if (this.match == null && !zOnRequest) {
                    this.res.code(404);
                    this.res.end();
                } else if (!getBody().readFullyOnRequest()) {
                    AsyncHttpServer.this.onRequest(this.match, this, this.res);
                } else if (this.requestComplete) {
                    AsyncHttpServer.this.onRequest(this.match, this, this.res);
                }
            }

            @Override // com.koushikdutta.async.http.server.AsyncHttpServerRequestImpl, com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                if (this.res.code() == 101) {
                    return;
                }
                this.requestComplete = true;
                super.onCompleted(exc);
                this.mSocket.setDataCallback(new DataCallback.NullDataCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.1.1.3
                    @Override // com.koushikdutta.async.callback.DataCallback.NullDataCallback, com.koushikdutta.async.callback.DataCallback
                    public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                        super.onDataAvailable(dataEmitter, byteBufferList);
                        C01331.this.mSocket.close();
                    }
                });
                handleOnCompleted();
                if (getBody().readFullyOnRequest()) {
                    AsyncHttpServer.this.onRequest(this.match, this, this.res);
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void handleOnCompleted() {
                if (this.requestComplete && this.responseComplete) {
                    if (HttpUtil.isKeepAlive(Protocol.HTTP_1_1, getHeaders())) {
                        AnonymousClass1.this.onAccepted(this.val$socket);
                    } else {
                        this.val$socket.close();
                    }
                }
            }

            @Override // com.koushikdutta.async.http.server.AsyncHttpServerRequest
            public String getPath() {
                return this.path;
            }

            @Override // com.koushikdutta.async.http.server.AsyncHttpServerRequest
            public Multimap getQuery() {
                String[] strArrSplit = this.fullPath.split("\\?", 2);
                if (strArrSplit.length < 2) {
                    return new Multimap();
                }
                return Multimap.parseQuery(strArrSplit[1]);
            }
        }

        @Override // com.koushikdutta.async.callback.ListenCallback
        public void onAccepted(AsyncSocket asyncSocket) {
            new C01331(asyncSocket).setSocket(asyncSocket);
            asyncSocket.resume();
        }

        @Override // com.koushikdutta.async.callback.CompletedCallback
        public void onCompleted(Exception exc) {
            AsyncHttpServer.this.report(exc);
        }

        @Override // com.koushikdutta.async.callback.ListenCallback
        public void onListening(AsyncServerSocket asyncServerSocket) {
            AsyncHttpServer.this.mListeners.add(asyncServerSocket);
        }
    }

    public AsyncServerSocket listen(AsyncServer asyncServer, int i) {
        return asyncServer.listen(null, i, this.mListenCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void report(Exception exc) {
        CompletedCallback completedCallback = this.mCompletedCallback;
        if (completedCallback != null) {
            completedCallback.onCompleted(exc);
        }
    }

    public AsyncServerSocket listen(int i) {
        return listen(AsyncServer.getDefault(), i);
    }

    public void listenSecure(final int i, final SSLContext sSLContext) {
        AsyncServer.getDefault().listen(null, i, new ListenCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.2
            @Override // com.koushikdutta.async.callback.ListenCallback
            public void onAccepted(AsyncSocket asyncSocket) {
                AsyncSSLSocketWrapper.handshake(asyncSocket, null, i, sSLContext.createSSLEngine(), null, null, false, new AsyncSSLSocketWrapper.HandshakeCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.2.1
                    @Override // com.koushikdutta.async.AsyncSSLSocketWrapper.HandshakeCallback
                    public void onHandshakeCompleted(Exception exc, AsyncSSLSocket asyncSSLSocket) {
                        if (asyncSSLSocket != null) {
                            AsyncHttpServer.this.mListenCallback.onAccepted(asyncSSLSocket);
                        }
                    }
                });
            }

            @Override // com.koushikdutta.async.callback.ListenCallback
            public void onListening(AsyncServerSocket asyncServerSocket) {
                AsyncHttpServer.this.mListenCallback.onListening(asyncServerSocket);
            }

            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                AsyncHttpServer.this.mListenCallback.onCompleted(exc);
            }
        });
    }

    public ListenCallback getListenCallback() {
        return this.mListenCallback;
    }

    public void setErrorCallback(CompletedCallback completedCallback) {
        this.mCompletedCallback = completedCallback;
    }

    public CompletedCallback getErrorCallback() {
        return this.mCompletedCallback;
    }

    private static class Pair {
        HttpServerRequestCallback callback;
        Pattern regex;

        private Pair() {
        }

        /* synthetic */ Pair(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public void removeAction(String str, String str2) {
        synchronized (this.mActions) {
            ArrayList<Pair> arrayList = this.mActions.get(str);
            if (arrayList == null) {
                return;
            }
            for (int i = 0; i < arrayList.size(); i++) {
                if (str2.equals(arrayList.get(i).regex.toString())) {
                    arrayList.remove(i);
                    return;
                }
            }
        }
    }

    public void addAction(String str, String str2, HttpServerRequestCallback httpServerRequestCallback) {
        Pair pair = new Pair(null);
        pair.regex = Pattern.compile("^" + str2);
        pair.callback = httpServerRequestCallback;
        synchronized (this.mActions) {
            ArrayList<Pair> arrayList = this.mActions.get(str);
            if (arrayList == null) {
                arrayList = new ArrayList<>();
                this.mActions.put(str, arrayList);
            }
            arrayList.add(pair);
        }
    }

    public void websocket(String str, WebSocketRequestCallback webSocketRequestCallback) {
        websocket(str, null, webSocketRequestCallback);
    }

    public void websocket(String str, final String str2, final WebSocketRequestCallback webSocketRequestCallback) {
        get(str, new HttpServerRequestCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.3
            @Override // com.koushikdutta.async.http.server.HttpServerRequestCallback
            public void onRequest(AsyncHttpServerRequest asyncHttpServerRequest, AsyncHttpServerResponse asyncHttpServerResponse) {
                String str3 = asyncHttpServerRequest.getHeaders().get("Connection");
                boolean z = false;
                if (str3 != null) {
                    for (String str4 : str3.split(",")) {
                        if ("Upgrade".equalsIgnoreCase(str4.trim())) {
                            z = true;
                            break;
                        }
                    }
                }
                if (!"websocket".equalsIgnoreCase(asyncHttpServerRequest.getHeaders().get("Upgrade")) || !z) {
                    asyncHttpServerResponse.code(404);
                    asyncHttpServerResponse.end();
                    return;
                }
                if (!TextUtils.equals(str2, asyncHttpServerRequest.getHeaders().get(HttpHeaders.SEC_WEBSOCKET_PROTOCOL))) {
                    asyncHttpServerResponse.code(404);
                    asyncHttpServerResponse.end();
                } else {
                    webSocketRequestCallback.onConnected(new WebSocketImpl(asyncHttpServerRequest, asyncHttpServerResponse), asyncHttpServerRequest);
                }
            }
        });
    }

    public void get(String str, HttpServerRequestCallback httpServerRequestCallback) {
        addAction("GET", str, httpServerRequestCallback);
    }

    public void post(String str, HttpServerRequestCallback httpServerRequestCallback) {
        addAction("POST", str, httpServerRequestCallback);
    }

    public static android.util.Pair<Integer, InputStream> getAssetStream(Context context, String str) {
        try {
            InputStream inputStreamOpen = context.getAssets().open(str);
            return new android.util.Pair<>(Integer.valueOf(inputStreamOpen.available()), inputStreamOpen);
        } catch (IOException unused) {
            return null;
        }
    }

    static {
        Hashtable<Integer, String> hashtable = new Hashtable<>();
        mCodes = hashtable;
        hashtable.put(200, "OK");
        mCodes.put(202, "Accepted");
        mCodes.put(206, "Partial Content");
        mCodes.put(101, "Switching Protocols");
        mCodes.put(301, "Moved Permanently");
        mCodes.put(302, "Found");
        mCodes.put(404, "Not Found");
    }

    public static String getContentType(String str) {
        String strTryGetContentType = tryGetContentType(str);
        return strTryGetContentType != null ? strTryGetContentType : "text/plain";
    }

    public static String tryGetContentType(String str) {
        int iLastIndexOf = str.lastIndexOf(".");
        if (iLastIndexOf == -1) {
            return null;
        }
        String str2 = mContentTypes.get(str.substring(iLastIndexOf + 1));
        if (str2 != null) {
            return str2;
        }
        return null;
    }

    public void directory(Context context, String str, final String str2) {
        final Context applicationContext = context.getApplicationContext();
        addAction("GET", str, new HttpServerRequestCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.4
            @Override // com.koushikdutta.async.http.server.HttpServerRequestCallback
            public void onRequest(AsyncHttpServerRequest asyncHttpServerRequest, final AsyncHttpServerResponse asyncHttpServerResponse) {
                String strReplaceAll = asyncHttpServerRequest.getMatcher().replaceAll("");
                android.util.Pair<Integer, InputStream> assetStream = AsyncHttpServer.getAssetStream(applicationContext, str2 + strReplaceAll);
                if (assetStream == null || assetStream.second == null) {
                    asyncHttpServerResponse.code(404);
                    asyncHttpServerResponse.end();
                    return;
                }
                final InputStream inputStream = (InputStream) assetStream.second;
                asyncHttpServerResponse.getHeaders().set("Content-Length", String.valueOf(assetStream.first));
                asyncHttpServerResponse.code(200);
                asyncHttpServerResponse.getHeaders().add("Content-Type", AsyncHttpServer.getContentType(str2 + strReplaceAll));
                Util.pump(inputStream, asyncHttpServerResponse, new CompletedCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.4.1
                    @Override // com.koushikdutta.async.callback.CompletedCallback
                    public void onCompleted(Exception exc) {
                        asyncHttpServerResponse.end();
                        StreamUtility.closeQuietly(inputStream);
                    }
                });
            }
        });
        addAction("HEAD", str, new HttpServerRequestCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.5
            @Override // com.koushikdutta.async.http.server.HttpServerRequestCallback
            public void onRequest(AsyncHttpServerRequest asyncHttpServerRequest, AsyncHttpServerResponse asyncHttpServerResponse) {
                String strReplaceAll = asyncHttpServerRequest.getMatcher().replaceAll("");
                android.util.Pair<Integer, InputStream> assetStream = AsyncHttpServer.getAssetStream(applicationContext, str2 + strReplaceAll);
                if (assetStream == null || assetStream.second == null) {
                    asyncHttpServerResponse.code(404);
                    asyncHttpServerResponse.end();
                    return;
                }
                StreamUtility.closeQuietly((InputStream) assetStream.second);
                asyncHttpServerResponse.getHeaders().set("Content-Length", String.valueOf(assetStream.first));
                asyncHttpServerResponse.code(200);
                asyncHttpServerResponse.getHeaders().add("Content-Type", AsyncHttpServer.getContentType(str2 + strReplaceAll));
                asyncHttpServerResponse.writeHead();
                asyncHttpServerResponse.end();
            }
        });
    }

    public void directory(String str, File file) {
        directory(str, file, false);
    }

    public void directory(String str, final File file, final boolean z) {
        addAction("GET", str, new HttpServerRequestCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.6
            @Override // com.koushikdutta.async.http.server.HttpServerRequestCallback
            public void onRequest(AsyncHttpServerRequest asyncHttpServerRequest, final AsyncHttpServerResponse asyncHttpServerResponse) {
                File file2 = new File(file, asyncHttpServerRequest.getMatcher().replaceAll(""));
                if (file2.isDirectory() && z) {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    for (File file3 : file2.listFiles()) {
                        if (file3.isDirectory()) {
                            arrayList.add(file3);
                        } else {
                            arrayList2.add(file3);
                        }
                    }
                    Comparator<File> comparator = new Comparator<File>() { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.6.1
                        @Override // java.util.Comparator
                        public int compare(File file4, File file5) {
                            return file4.getName().compareTo(file5.getName());
                        }
                    };
                    Collections.sort(arrayList, comparator);
                    Collections.sort(arrayList2, comparator);
                    arrayList2.addAll(0, arrayList);
                    return;
                }
                if (!file2.isFile()) {
                    asyncHttpServerResponse.code(404);
                    asyncHttpServerResponse.end();
                    return;
                }
                try {
                    FileInputStream fileInputStream = new FileInputStream(file2);
                    asyncHttpServerResponse.code(200);
                    Util.pump(fileInputStream, asyncHttpServerResponse, new CompletedCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServer.6.2
                        @Override // com.koushikdutta.async.callback.CompletedCallback
                        public void onCompleted(Exception exc) {
                            asyncHttpServerResponse.end();
                        }
                    });
                } catch (FileNotFoundException unused) {
                    asyncHttpServerResponse.code(404);
                    asyncHttpServerResponse.end();
                }
            }
        });
    }

    public static String getResponseCodeDescription(int i) {
        String str = mCodes.get(Integer.valueOf(i));
        return str == null ? "Unknown" : str;
    }
}
