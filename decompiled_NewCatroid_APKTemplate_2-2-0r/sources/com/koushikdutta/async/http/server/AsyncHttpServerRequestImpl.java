package com.koushikdutta.async.http.server;

import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.FilteredDataEmitter;
import com.koushikdutta.async.LineEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.Headers;
import com.koushikdutta.async.http.HttpUtil;
import com.koushikdutta.async.http.Protocol;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import java.util.regex.Matcher;

/* JADX INFO: loaded from: classes10.dex */
public abstract class AsyncHttpServerRequestImpl extends FilteredDataEmitter implements AsyncHttpServerRequest, CompletedCallback {
    AsyncHttpRequestBody mBody;
    Matcher mMatcher;
    AsyncSocket mSocket;
    String method;
    private String statusLine;
    private Headers mRawHeaders = new Headers();
    private CompletedCallback mReporter = new CompletedCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServerRequestImpl.1
        @Override // com.koushikdutta.async.callback.CompletedCallback
        public void onCompleted(Exception exc) {
            AsyncHttpServerRequestImpl.this.onCompleted(exc);
        }
    };
    LineEmitter.StringCallback mHeaderCallback = new LineEmitter.StringCallback() { // from class: com.koushikdutta.async.http.server.AsyncHttpServerRequestImpl.2
        @Override // com.koushikdutta.async.LineEmitter.StringCallback
        public void onStringAvailable(String str) {
            try {
                if (AsyncHttpServerRequestImpl.this.statusLine == null) {
                    AsyncHttpServerRequestImpl.this.statusLine = str;
                    if (AsyncHttpServerRequestImpl.this.statusLine.contains("HTTP/")) {
                        return;
                    }
                    AsyncHttpServerRequestImpl.this.onNotHttp();
                    AsyncHttpServerRequestImpl.this.mSocket.setDataCallback(null);
                    return;
                }
                if (!"\r".equals(str)) {
                    AsyncHttpServerRequestImpl.this.mRawHeaders.addLine(str);
                    return;
                }
                DataEmitter bodyDecoder = HttpUtil.getBodyDecoder(AsyncHttpServerRequestImpl.this.mSocket, Protocol.HTTP_1_1, AsyncHttpServerRequestImpl.this.mRawHeaders, true);
                AsyncHttpServerRequestImpl asyncHttpServerRequestImpl = AsyncHttpServerRequestImpl.this;
                asyncHttpServerRequestImpl.mBody = HttpUtil.getBody(bodyDecoder, asyncHttpServerRequestImpl.mReporter, AsyncHttpServerRequestImpl.this.mRawHeaders);
                if (AsyncHttpServerRequestImpl.this.mBody == null) {
                    AsyncHttpServerRequestImpl asyncHttpServerRequestImpl2 = AsyncHttpServerRequestImpl.this;
                    asyncHttpServerRequestImpl2.mBody = asyncHttpServerRequestImpl2.onUnknownBody(asyncHttpServerRequestImpl2.mRawHeaders);
                    if (AsyncHttpServerRequestImpl.this.mBody == null) {
                        AsyncHttpServerRequestImpl.this.mBody = new UnknownRequestBody(AsyncHttpServerRequestImpl.this.mRawHeaders.get("Content-Type"));
                    }
                }
                AsyncHttpServerRequestImpl.this.mBody.parse(bodyDecoder, AsyncHttpServerRequestImpl.this.mReporter);
                AsyncHttpServerRequestImpl.this.onHeadersReceived();
            } catch (Exception e) {
                AsyncHttpServerRequestImpl.this.onCompleted(e);
            }
        }
    };

    protected abstract void onHeadersReceived();

    protected AsyncHttpRequestBody onUnknownBody(Headers headers) {
        return null;
    }

    public String getStatusLine() {
        return this.statusLine;
    }

    public void onCompleted(Exception exc) {
        report(exc);
    }

    protected void onNotHttp() {
        System.out.println("not http!");
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerRequest
    public String getMethod() {
        return this.method;
    }

    void setSocket(AsyncSocket asyncSocket) {
        this.mSocket = asyncSocket;
        LineEmitter lineEmitter = new LineEmitter();
        this.mSocket.setDataCallback(lineEmitter);
        lineEmitter.setLineCallback(this.mHeaderCallback);
        this.mSocket.setEndCallback(new CompletedCallback.NullCompletedCallback());
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerRequest
    public AsyncSocket getSocket() {
        return this.mSocket;
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerRequest
    public Headers getHeaders() {
        return this.mRawHeaders;
    }

    @Override // com.koushikdutta.async.DataEmitterBase, com.koushikdutta.async.DataEmitter
    public void setDataCallback(DataCallback dataCallback) {
        this.mSocket.setDataCallback(dataCallback);
    }

    @Override // com.koushikdutta.async.DataEmitterBase, com.koushikdutta.async.DataEmitter
    public DataCallback getDataCallback() {
        return this.mSocket.getDataCallback();
    }

    @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter
    public boolean isChunked() {
        return this.mSocket.isChunked();
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerRequest
    public Matcher getMatcher() {
        return this.mMatcher;
    }

    @Override // com.koushikdutta.async.http.server.AsyncHttpServerRequest
    public AsyncHttpRequestBody getBody() {
        return this.mBody;
    }

    @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter
    public void pause() {
        this.mSocket.pause();
    }

    @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter
    public void resume() {
        this.mSocket.resume();
    }

    @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter
    public boolean isPaused() {
        return this.mSocket.isPaused();
    }

    public String toString() {
        Headers headers = this.mRawHeaders;
        if (headers == null) {
            return super.toString();
        }
        return headers.toPrefixString(this.statusLine);
    }
}
