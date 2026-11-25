package com.koushikdutta.async.http;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.DataSink;
import com.koushikdutta.async.FilteredDataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.http.body.AsyncHttpRequestBody;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes10.dex */
abstract class AsyncHttpResponseImpl extends FilteredDataEmitter implements AsyncSocket, AsyncHttpResponse, AsyncHttpClientMiddleware.ResponseHead {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    int code;
    protected Headers mHeaders;
    private AsyncHttpRequest mRequest;
    DataSink mSink;
    private AsyncSocket mSocket;
    String message;
    String protocol;
    private CompletedCallback mReporter = new CompletedCallback() { // from class: com.koushikdutta.async.http.AsyncHttpResponseImpl.2
        @Override // com.koushikdutta.async.callback.CompletedCallback
        public void onCompleted(Exception exc) {
            if (exc != null && !AsyncHttpResponseImpl.this.mCompleted) {
                AsyncHttpResponseImpl.this.report(new ConnectionClosedException("connection closed before response completed.", exc));
            } else {
                AsyncHttpResponseImpl.this.report(exc);
            }
        }
    };
    boolean mCompleted = false;
    private boolean mFirstWrite = true;

    protected void onHeadersReceived() {
    }

    protected void onRequestCompleted(Exception exc) {
    }

    @Override // com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public AsyncSocket socket() {
        return this.mSocket;
    }

    @Override // com.koushikdutta.async.http.AsyncHttpResponse
    public AsyncHttpRequest getRequest() {
        return this.mRequest;
    }

    void setSocket(AsyncSocket asyncSocket) {
        this.mSocket = asyncSocket;
        if (asyncSocket == null) {
            return;
        }
        asyncSocket.setEndCallback(this.mReporter);
    }

    protected void onHeadersSent() {
        AsyncHttpRequestBody body = this.mRequest.getBody();
        if (body != null) {
            body.write(this.mRequest, this, new CompletedCallback() { // from class: com.koushikdutta.async.http.AsyncHttpResponseImpl.1
                @Override // com.koushikdutta.async.callback.CompletedCallback
                public void onCompleted(Exception exc) {
                    AsyncHttpResponseImpl.this.onRequestCompleted(exc);
                }
            });
        } else {
            onRequestCompleted(null);
        }
    }

    @Override // com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public DataEmitter emitter() {
        return getDataEmitter();
    }

    @Override // com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public AsyncHttpClientMiddleware.ResponseHead emitter(DataEmitter dataEmitter) {
        setDataEmitter(dataEmitter);
        return this;
    }

    private void terminate() {
        this.mSocket.setDataCallback(new DataCallback.NullDataCallback() { // from class: com.koushikdutta.async.http.AsyncHttpResponseImpl.3
            @Override // com.koushikdutta.async.callback.DataCallback.NullDataCallback, com.koushikdutta.async.callback.DataCallback
            public void onDataAvailable(DataEmitter dataEmitter, ByteBufferList byteBufferList) {
                super.onDataAvailable(dataEmitter, byteBufferList);
                AsyncHttpResponseImpl.this.mSocket.close();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.koushikdutta.async.DataEmitterBase
    public void report(Exception exc) {
        super.report(exc);
        terminate();
        this.mSocket.setWriteableCallback(null);
        this.mSocket.setClosedCallback(null);
        this.mSocket.setEndCallback(null);
        this.mCompleted = true;
    }

    @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter
    public void close() {
        super.close();
        terminate();
    }

    public AsyncHttpResponseImpl(AsyncHttpRequest asyncHttpRequest) {
        this.mRequest = asyncHttpRequest;
    }

    @Override // com.koushikdutta.async.http.AsyncHttpResponse, com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public Headers headers() {
        return this.mHeaders;
    }

    @Override // com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public AsyncHttpClientMiddleware.ResponseHead headers(Headers headers) {
        this.mHeaders = headers;
        return this;
    }

    @Override // com.koushikdutta.async.http.AsyncHttpResponse, com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public int code() {
        return this.code;
    }

    @Override // com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public AsyncHttpClientMiddleware.ResponseHead code(int i) {
        this.code = i;
        return this;
    }

    @Override // com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public AsyncHttpClientMiddleware.ResponseHead protocol(String str) {
        this.protocol = str;
        return this;
    }

    @Override // com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public AsyncHttpClientMiddleware.ResponseHead message(String str) {
        this.message = str;
        return this;
    }

    @Override // com.koushikdutta.async.http.AsyncHttpResponse, com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public String protocol() {
        return this.protocol;
    }

    @Override // com.koushikdutta.async.http.AsyncHttpResponse, com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public String message() {
        return this.message;
    }

    public String toString() {
        Headers headers = this.mHeaders;
        if (headers == null) {
            return super.toString();
        }
        return headers.toPrefixString(this.protocol + " " + this.code + " " + this.message);
    }

    private void assertContent() {
        if (this.mFirstWrite) {
            this.mFirstWrite = false;
        }
    }

    @Override // com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public DataSink sink() {
        return this.mSink;
    }

    @Override // com.koushikdutta.async.http.AsyncHttpClientMiddleware.ResponseHead
    public AsyncHttpClientMiddleware.ResponseHead sink(DataSink dataSink) {
        this.mSink = dataSink;
        return this;
    }

    @Override // com.koushikdutta.async.DataSink
    public void write(ByteBufferList byteBufferList) {
        assertContent();
        this.mSink.write(byteBufferList);
    }

    @Override // com.koushikdutta.async.DataSink
    public void end() {
        throw new AssertionError("end called?");
    }

    @Override // com.koushikdutta.async.DataSink
    public void setWriteableCallback(WritableCallback writableCallback) {
        this.mSink.setWriteableCallback(writableCallback);
    }

    @Override // com.koushikdutta.async.DataSink
    public WritableCallback getWriteableCallback() {
        return this.mSink.getWriteableCallback();
    }

    @Override // com.koushikdutta.async.DataSink
    public boolean isOpen() {
        return this.mSink.isOpen();
    }

    @Override // com.koushikdutta.async.DataSink
    public void setClosedCallback(CompletedCallback completedCallback) {
        this.mSink.setClosedCallback(completedCallback);
    }

    @Override // com.koushikdutta.async.DataSink
    public CompletedCallback getClosedCallback() {
        return this.mSink.getClosedCallback();
    }

    @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitter, com.koushikdutta.async.DataSink
    public AsyncServer getServer() {
        return this.mSocket.getServer();
    }

    @Override // com.koushikdutta.async.FilteredDataEmitter, com.koushikdutta.async.DataEmitterBase, com.koushikdutta.async.DataEmitter
    public String charset() {
        String string;
        Multimap semicolonDelimited = Multimap.parseSemicolonDelimited(headers().get("Content-Type"));
        if (semicolonDelimited == null || (string = semicolonDelimited.getString("charset")) == null || !Charset.isSupported(string)) {
            return null;
        }
        return string;
    }
}
