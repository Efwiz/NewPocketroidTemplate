package com.koushikdutta.async.http.socketio.transport;

import android.net.Uri;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.StringBody;

/* JADX INFO: loaded from: classes10.dex */
public class XHRPollingTransport implements SocketIOTransport {
    private static final String SEPARATOR = "�";
    private AsyncHttpClient client;
    private CompletedCallback closedCallback;
    private boolean connected;
    private String sessionId;
    private Uri sessionUrl;
    private SocketIOTransport.StringCallback stringCallback;

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public boolean heartbeats() {
        return false;
    }

    public XHRPollingTransport(AsyncHttpClient asyncHttpClient, String str, String str2) {
        this.client = asyncHttpClient;
        this.sessionUrl = Uri.parse(str);
        this.sessionId = str2;
        doLongPolling();
        this.connected = true;
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public boolean isConnected() {
        return this.connected;
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public void setClosedCallback(CompletedCallback completedCallback) {
        this.closedCallback = completedCallback;
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public void disconnect() {
        this.connected = false;
        close(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void close(Exception exc) {
        CompletedCallback completedCallback = this.closedCallback;
        if (completedCallback != null) {
            completedCallback.onCompleted(exc);
        }
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public AsyncServer getServer() {
        return this.client.getServer();
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public void send(String str) {
        if (str.startsWith("5")) {
            postMessage(str);
            return;
        }
        AsyncHttpPost asyncHttpPost = new AsyncHttpPost(computedRequestUrl());
        asyncHttpPost.setBody(new StringBody(str));
        this.client.executeString(asyncHttpPost, new AsyncHttpClient.StringCallback() { // from class: com.koushikdutta.async.http.socketio.transport.XHRPollingTransport.1
            @Override // com.koushikdutta.async.callback.ResultCallback
            public void onCompleted(Exception exc, AsyncHttpResponse asyncHttpResponse, String str2) {
                if (exc != null) {
                    XHRPollingTransport.this.close(exc);
                } else {
                    XHRPollingTransport.this.sendResult(str2);
                }
            }
        });
    }

    private void postMessage(String str) {
        if (str.startsWith("5")) {
            AsyncHttpPost asyncHttpPost = new AsyncHttpPost(computedRequestUrl());
            asyncHttpPost.setBody(new StringBody(str));
            this.client.executeString(asyncHttpPost, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doLongPolling() {
        this.client.executeString(new AsyncHttpGet(computedRequestUrl()), new AsyncHttpClient.StringCallback() { // from class: com.koushikdutta.async.http.socketio.transport.XHRPollingTransport.2
            @Override // com.koushikdutta.async.callback.ResultCallback
            public void onCompleted(Exception exc, AsyncHttpResponse asyncHttpResponse, String str) {
                if (exc != null) {
                    XHRPollingTransport.this.close(exc);
                } else {
                    XHRPollingTransport.this.sendResult(str);
                    XHRPollingTransport.this.doLongPolling();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendResult(String str) {
        if (this.stringCallback == null) {
            return;
        }
        if (!str.contains(SEPARATOR)) {
            this.stringCallback.onStringAvailable(str);
            return;
        }
        String[] strArrSplit = str.split(SEPARATOR);
        for (int i = 1; i < strArrSplit.length; i += 2) {
            this.stringCallback.onStringAvailable(strArrSplit[i + 1]);
        }
    }

    private String computedRequestUrl() {
        return this.sessionUrl.buildUpon().appendQueryParameter("t", String.valueOf(System.currentTimeMillis())).build().toString();
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public void setStringCallback(SocketIOTransport.StringCallback stringCallback) {
        this.stringCallback = stringCallback;
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public String getSessionId() {
        return this.sessionId;
    }
}
