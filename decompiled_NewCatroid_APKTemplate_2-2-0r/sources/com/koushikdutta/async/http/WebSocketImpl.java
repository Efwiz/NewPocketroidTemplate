package com.koushikdutta.async.http;

import android.text.TextUtils;
import android.util.Base64;
import com.google.common.net.HttpHeaders;
import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.AsyncSocket;
import com.koushikdutta.async.BufferedDataSink;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.callback.WritableCallback;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.UUID;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

/* JADX INFO: loaded from: classes10.dex */
public class WebSocketImpl implements WebSocket {
    static final String MAGIC = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private DataCallback mDataCallback;
    CompletedCallback mExceptionCallback;
    HybiParser mParser;
    private WebSocket.PingCallback mPingCallback;
    private WebSocket.PongCallback mPongCallback;
    BufferedDataSink mSink;
    private AsyncSocket mSocket;
    private WebSocket.StringCallback mStringCallback;
    private LinkedList<ByteBufferList> pending;

    @Override // com.koushikdutta.async.DataEmitter
    public String charset() {
        return null;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isChunked() {
        return false;
    }

    @Override // com.koushikdutta.async.DataSink
    public void end() {
        this.mSocket.end();
    }

    private static byte[] toByteArray(UUID uuid) {
        byte[] bArr = new byte[16];
        ByteBuffer.wrap(bArr).asLongBuffer().put(new long[]{uuid.getMostSignificantBits(), uuid.getLeastSignificantBits()});
        return bArr;
    }

    private static String SHA1(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(McElieceCCA2KeyGenParameterSpec.SHA1);
            messageDigest.update(str.getBytes("iso-8859-1"), 0, str.length());
            return Base64.encodeToString(messageDigest.digest(), 2);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addAndEmit(ByteBufferList byteBufferList) {
        if (this.pending == null) {
            Util.emitAllData(this, byteBufferList);
            if (byteBufferList.remaining() > 0) {
                LinkedList<ByteBufferList> linkedList = new LinkedList<>();
                this.pending = linkedList;
                linkedList.add(byteBufferList);
                return;
            }
            return;
        }
        while (!isPaused()) {
            ByteBufferList byteBufferListRemove = this.pending.remove();
            Util.emitAllData(this, byteBufferListRemove);
            if (byteBufferListRemove.remaining() > 0) {
                this.pending.add(0, byteBufferListRemove);
            }
        }
        if (this.pending.size() == 0) {
            this.pending = null;
        }
    }

    private void setupParser(boolean z, boolean z2) {
        HybiParser hybiParser = new HybiParser(this.mSocket) { // from class: com.koushikdutta.async.http.WebSocketImpl.1
            @Override // com.koushikdutta.async.http.HybiParser
            protected void report(Exception exc) {
                if (WebSocketImpl.this.mExceptionCallback != null) {
                    WebSocketImpl.this.mExceptionCallback.onCompleted(exc);
                }
            }

            @Override // com.koushikdutta.async.http.HybiParser
            protected void onMessage(byte[] bArr) {
                WebSocketImpl.this.addAndEmit(new ByteBufferList(bArr));
            }

            @Override // com.koushikdutta.async.http.HybiParser
            protected void onMessage(String str) {
                if (WebSocketImpl.this.mStringCallback != null) {
                    WebSocketImpl.this.mStringCallback.onStringAvailable(str);
                }
            }

            @Override // com.koushikdutta.async.http.HybiParser
            protected void onDisconnect(int i, String str) {
                WebSocketImpl.this.mSocket.close();
            }

            @Override // com.koushikdutta.async.http.HybiParser
            protected void sendFrame(byte[] bArr) {
                WebSocketImpl.this.mSink.write(new ByteBufferList(bArr));
            }

            @Override // com.koushikdutta.async.http.HybiParser
            protected void onPing(String str) {
                if (WebSocketImpl.this.mPingCallback != null) {
                    WebSocketImpl.this.mPingCallback.onPingReceived(str);
                }
            }

            @Override // com.koushikdutta.async.http.HybiParser
            protected void onPong(String str) {
                if (WebSocketImpl.this.mPongCallback != null) {
                    WebSocketImpl.this.mPongCallback.onPongReceived(str);
                }
            }
        };
        this.mParser = hybiParser;
        hybiParser.setMasking(z);
        this.mParser.setDeflate(z2);
        if (this.mSocket.isPaused()) {
            this.mSocket.resume();
        }
    }

    public WebSocketImpl(AsyncHttpServerRequest asyncHttpServerRequest, AsyncHttpServerResponse asyncHttpServerResponse) {
        this(asyncHttpServerRequest.getSocket());
        String strSHA1 = SHA1(asyncHttpServerRequest.getHeaders().get(HttpHeaders.SEC_WEBSOCKET_KEY) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11");
        asyncHttpServerRequest.getHeaders().get("Origin");
        asyncHttpServerResponse.code(101);
        asyncHttpServerResponse.getHeaders().set("Upgrade", "WebSocket");
        asyncHttpServerResponse.getHeaders().set("Connection", "Upgrade");
        asyncHttpServerResponse.getHeaders().set(HttpHeaders.SEC_WEBSOCKET_ACCEPT, strSHA1);
        String str = asyncHttpServerRequest.getHeaders().get(HttpHeaders.SEC_WEBSOCKET_PROTOCOL);
        if (!TextUtils.isEmpty(str)) {
            asyncHttpServerResponse.getHeaders().set(HttpHeaders.SEC_WEBSOCKET_PROTOCOL, str);
        }
        asyncHttpServerResponse.writeHead();
        setupParser(false, false);
    }

    public static void addWebSocketUpgradeHeaders(AsyncHttpRequest asyncHttpRequest, String str) {
        Headers headers = asyncHttpRequest.getHeaders();
        String strEncodeToString = Base64.encodeToString(toByteArray(UUID.randomUUID()), 2);
        headers.set(HttpHeaders.SEC_WEBSOCKET_VERSION, "13");
        headers.set(HttpHeaders.SEC_WEBSOCKET_KEY, strEncodeToString);
        headers.set(HttpHeaders.SEC_WEBSOCKET_EXTENSIONS, "x-webkit-deflate-frame");
        headers.set("Connection", "Upgrade");
        headers.set("Upgrade", "websocket");
        if (str != null) {
            headers.set(HttpHeaders.SEC_WEBSOCKET_PROTOCOL, str);
        }
        headers.set("Pragma", "no-cache");
        headers.set("Cache-Control", "no-cache");
        if (TextUtils.isEmpty(asyncHttpRequest.getHeaders().get("User-Agent"))) {
            asyncHttpRequest.getHeaders().set("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.15 Safari/537.36");
        }
    }

    public WebSocketImpl(AsyncSocket asyncSocket) {
        this.mSocket = asyncSocket;
        this.mSink = new BufferedDataSink(this.mSocket);
    }

    public static WebSocket finishHandshake(Headers headers, AsyncHttpResponse asyncHttpResponse) {
        String str;
        String str2;
        if (asyncHttpResponse == null || asyncHttpResponse.code() != 101 || !"websocket".equalsIgnoreCase(asyncHttpResponse.headers().get("Upgrade")) || (str = asyncHttpResponse.headers().get(HttpHeaders.SEC_WEBSOCKET_ACCEPT)) == null || (str2 = headers.get(HttpHeaders.SEC_WEBSOCKET_KEY)) == null || !str.equalsIgnoreCase(SHA1(str2 + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").trim())) {
            return null;
        }
        String str3 = headers.get(HttpHeaders.SEC_WEBSOCKET_EXTENSIONS);
        boolean z = str3 != null && str3.equals("x-webkit-deflate-frame");
        WebSocketImpl webSocketImpl = new WebSocketImpl(asyncHttpResponse.detachSocket());
        webSocketImpl.setupParser(true, z);
        return webSocketImpl;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void close() {
        this.mSocket.close();
    }

    @Override // com.koushikdutta.async.DataSink
    public void setClosedCallback(CompletedCallback completedCallback) {
        this.mSocket.setClosedCallback(completedCallback);
    }

    @Override // com.koushikdutta.async.DataSink
    public CompletedCallback getClosedCallback() {
        return this.mSocket.getClosedCallback();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void setEndCallback(CompletedCallback completedCallback) {
        this.mExceptionCallback = completedCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public CompletedCallback getEndCallback() {
        return this.mExceptionCallback;
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public void send(byte[] bArr) {
        this.mSink.write(new ByteBufferList(this.mParser.frame(bArr)));
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public void send(byte[] bArr, int i, int i2) {
        this.mSink.write(new ByteBufferList(this.mParser.frame(bArr, i, i2)));
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public void send(String str) {
        this.mSink.write(new ByteBufferList(this.mParser.frame(str)));
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public void ping(String str) {
        this.mSink.write(new ByteBufferList(ByteBuffer.wrap(this.mParser.pingFrame(str))));
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public void pong(String str) {
        this.mSink.write(new ByteBufferList(ByteBuffer.wrap(this.mParser.pongFrame(str))));
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public void setStringCallback(WebSocket.StringCallback stringCallback) {
        this.mStringCallback = stringCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void setDataCallback(DataCallback dataCallback) {
        this.mDataCallback = dataCallback;
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public WebSocket.StringCallback getStringCallback() {
        return this.mStringCallback;
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public void setPingCallback(WebSocket.PingCallback pingCallback) {
        this.mPingCallback = pingCallback;
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public void setPongCallback(WebSocket.PongCallback pongCallback) {
        this.mPongCallback = pongCallback;
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public WebSocket.PongCallback getPongCallback() {
        return this.mPongCallback;
    }

    @Override // com.koushikdutta.async.DataEmitter
    public DataCallback getDataCallback() {
        return this.mDataCallback;
    }

    @Override // com.koushikdutta.async.DataSink
    public boolean isOpen() {
        return this.mSocket.isOpen();
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public boolean isBuffering() {
        return this.mSink.remaining() > 0;
    }

    @Override // com.koushikdutta.async.DataSink
    public void write(ByteBufferList byteBufferList) {
        send(byteBufferList.getAllByteArray());
    }

    @Override // com.koushikdutta.async.DataSink
    public void setWriteableCallback(WritableCallback writableCallback) {
        this.mSink.setWriteableCallback(writableCallback);
    }

    @Override // com.koushikdutta.async.DataSink
    public WritableCallback getWriteableCallback() {
        return this.mSink.getWriteableCallback();
    }

    @Override // com.koushikdutta.async.http.WebSocket
    public AsyncSocket getSocket() {
        return this.mSocket;
    }

    @Override // com.koushikdutta.async.AsyncSocket, com.koushikdutta.async.DataEmitter, com.koushikdutta.async.DataSink
    public AsyncServer getServer() {
        return this.mSocket.getServer();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void pause() {
        this.mSocket.pause();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public void resume() {
        this.mSocket.resume();
    }

    @Override // com.koushikdutta.async.DataEmitter
    public boolean isPaused() {
        return this.mSocket.isPaused();
    }
}
