package com.koushikdutta.async.http.socketio;

import android.text.TextUtils;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.SimpleFuture;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.socketio.transport.SocketIOTransport;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes10.dex */
@Deprecated
public class SocketIOClient extends EventEmitter {
    ConnectCallback connectCallback;
    boolean connected;
    SocketIOConnection connection;
    DisconnectCallback disconnectCallback;
    boolean disconnected;
    String endpoint;
    ErrorCallback errorCallback;
    ExceptionCallback exceptionCallback;
    JSONCallback jsonCallback;
    ReconnectCallback reconnectCallback;
    StringCallback stringCallback;

    private void emitRaw(int i, String str, Acknowledge acknowledge) {
        this.connection.emitRaw(i, this, str, acknowledge);
    }

    public void emit(String str, JSONArray jSONArray) {
        emit(str, jSONArray, null);
    }

    public void emit(String str) {
        emit(str, (Acknowledge) null);
    }

    public void emit(JSONObject jSONObject) {
        emit(jSONObject, (Acknowledge) null);
    }

    public void emit(String str, JSONArray jSONArray, Acknowledge acknowledge) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", str);
            jSONObject.put("args", jSONArray);
            emitRaw(5, jSONObject.toString(), acknowledge);
        } catch (Exception unused) {
        }
    }

    public void emit(String str, Acknowledge acknowledge) {
        emitRaw(3, str, acknowledge);
    }

    public void emit(JSONObject jSONObject, Acknowledge acknowledge) {
        emitRaw(4, jSONObject.toString(), acknowledge);
    }

    public void emitEvent(String str) {
        emitEvent(str, null);
    }

    public void emitEvent(String str, Acknowledge acknowledge) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", str);
            emitRaw(5, jSONObject.toString(), acknowledge);
        } catch (Exception unused) {
        }
    }

    public static Future<SocketIOClient> connect(AsyncHttpClient asyncHttpClient, String str, ConnectCallback connectCallback) {
        return connect(asyncHttpClient, new SocketIORequest(str), connectCallback);
    }

    public static Future<SocketIOClient> connect(AsyncHttpClient asyncHttpClient, final SocketIORequest socketIORequest, final ConnectCallback connectCallback) {
        final SimpleFuture simpleFuture = new SimpleFuture();
        final SocketIOConnection socketIOConnection = new SocketIOConnection(asyncHttpClient, socketIORequest);
        socketIOConnection.clients.add(new SocketIOClient(socketIOConnection, "", new ConnectCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOClient.1
            @Override // com.koushikdutta.async.http.socketio.ConnectCallback
            public void onConnectCompleted(Exception exc, SocketIOClient socketIOClient) {
                if (exc != null || TextUtils.isEmpty(socketIORequest.getEndpoint())) {
                    ConnectCallback connectCallback2 = connectCallback;
                    if (connectCallback2 != null) {
                        connectCallback2.onConnectCompleted(exc, socketIOClient);
                    }
                    simpleFuture.setComplete(exc, socketIOClient);
                    return;
                }
                socketIOConnection.clients.remove(socketIOClient);
                socketIOClient.of(socketIORequest.getEndpoint(), new ConnectCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOClient.1.1
                    @Override // com.koushikdutta.async.http.socketio.ConnectCallback
                    public void onConnectCompleted(Exception exc2, SocketIOClient socketIOClient2) {
                        if (connectCallback != null) {
                            connectCallback.onConnectCompleted(exc2, socketIOClient2);
                        }
                        simpleFuture.setComplete(exc2, socketIOClient2);
                    }
                });
            }
        }));
        socketIOConnection.reconnect(simpleFuture);
        return simpleFuture;
    }

    public void setExceptionCallback(ExceptionCallback exceptionCallback) {
        this.exceptionCallback = exceptionCallback;
    }

    public ExceptionCallback getExceptionCallback() {
        return this.exceptionCallback;
    }

    public ErrorCallback getErrorCallback() {
        return this.errorCallback;
    }

    public void setErrorCallback(ErrorCallback errorCallback) {
        this.errorCallback = errorCallback;
    }

    public DisconnectCallback getDisconnectCallback() {
        return this.disconnectCallback;
    }

    public void setDisconnectCallback(DisconnectCallback disconnectCallback) {
        this.disconnectCallback = disconnectCallback;
    }

    public ReconnectCallback getReconnectCallback() {
        return this.reconnectCallback;
    }

    public void setReconnectCallback(ReconnectCallback reconnectCallback) {
        this.reconnectCallback = reconnectCallback;
    }

    public JSONCallback getJSONCallback() {
        return this.jsonCallback;
    }

    public void setJSONCallback(JSONCallback jSONCallback) {
        this.jsonCallback = jSONCallback;
    }

    public StringCallback getStringCallback() {
        return this.stringCallback;
    }

    public void setStringCallback(StringCallback stringCallback) {
        this.stringCallback = stringCallback;
    }

    private SocketIOClient(SocketIOConnection socketIOConnection, String str, ConnectCallback connectCallback) {
        this.endpoint = str;
        this.connection = socketIOConnection;
        this.connectCallback = connectCallback;
    }

    public boolean isConnected() {
        return this.connected && !this.disconnected && this.connection.isConnected();
    }

    public void disconnect() {
        this.connection.disconnect(this);
        DisconnectCallback disconnectCallback = this.disconnectCallback;
        if (disconnectCallback != null) {
            disconnectCallback.onDisconnect(null);
        }
    }

    public void of(String str, ConnectCallback connectCallback) {
        SocketIOConnection socketIOConnection = this.connection;
        socketIOConnection.connect(new SocketIOClient(socketIOConnection, str, connectCallback));
    }

    public void reconnect() {
        this.connection.reconnect(null);
    }

    public SocketIOTransport getTransport() {
        return this.connection.transport;
    }
}
