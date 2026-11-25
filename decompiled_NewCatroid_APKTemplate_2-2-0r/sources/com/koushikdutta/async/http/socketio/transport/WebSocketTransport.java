package com.koushikdutta.async.http.socketio.transport;

import com.koushikdutta.async.AsyncServer;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.WebSocket;

/* JADX INFO: loaded from: classes10.dex */
public class WebSocketTransport implements SocketIOTransport {
    private String sessionId;
    private SocketIOTransport.StringCallback stringCallback;
    private WebSocket webSocket;

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public boolean heartbeats() {
        return true;
    }

    public WebSocketTransport(WebSocket webSocket, String str) {
        this.webSocket = webSocket;
        this.sessionId = str;
        webSocket.setDataCallback(new DataCallback.NullDataCallback());
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public boolean isConnected() {
        return this.webSocket.isOpen();
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public void setClosedCallback(CompletedCallback completedCallback) {
        this.webSocket.setClosedCallback(completedCallback);
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public void disconnect() {
        this.webSocket.close();
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public AsyncServer getServer() {
        return this.webSocket.getServer();
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public void send(String str) {
        this.webSocket.send(str);
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public void setStringCallback(final SocketIOTransport.StringCallback stringCallback) {
        if (this.stringCallback == stringCallback) {
            return;
        }
        if (stringCallback == null) {
            this.webSocket.setStringCallback(null);
        } else {
            this.webSocket.setStringCallback(new WebSocket.StringCallback() { // from class: com.koushikdutta.async.http.socketio.transport.WebSocketTransport.1
                @Override // com.koushikdutta.async.http.WebSocket.StringCallback
                public void onStringAvailable(String str) {
                    stringCallback.onStringAvailable(str);
                }
            });
        }
        this.stringCallback = stringCallback;
    }

    @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport
    public String getSessionId() {
        return this.sessionId;
    }
}
