package com.koushikdutta.async.http.socketio;

import android.net.Uri;
import android.text.TextUtils;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.future.Cancellable;
import com.koushikdutta.async.future.DependentCancellable;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.async.future.SimpleFuture;
import com.koushikdutta.async.future.TransformFuture;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.socketio.transport.SocketIOTransport;
import com.koushikdutta.async.http.socketio.transport.WebSocketTransport;
import com.koushikdutta.async.http.socketio.transport.XHRPollingTransport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import kotlin.time.DurationKt;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes10.dex */
class SocketIOConnection {
    int ackCount;
    Cancellable connecting;
    int heartbeat;
    AsyncHttpClient httpClient;
    long reconnectDelay;
    SocketIORequest request;
    SocketIOTransport transport;
    ArrayList<SocketIOClient> clients = new ArrayList<>();
    Hashtable<String, Acknowledge> acknowledges = new Hashtable<>();

    private interface SelectCallback {
        void onSelect(SocketIOClient socketIOClient);
    }

    public SocketIOConnection(AsyncHttpClient asyncHttpClient, SocketIORequest socketIORequest) {
        this.httpClient = asyncHttpClient;
        this.request = socketIORequest;
        this.reconnectDelay = socketIORequest.config.reconnectDelay;
    }

    public boolean isConnected() {
        SocketIOTransport socketIOTransport = this.transport;
        return socketIOTransport != null && socketIOTransport.isConnected();
    }

    public void emitRaw(int i, SocketIOClient socketIOClient, String str, Acknowledge acknowledge) {
        String str2 = "";
        if (acknowledge != null) {
            StringBuilder sb = new StringBuilder("");
            int i2 = this.ackCount;
            this.ackCount = i2 + 1;
            String string = sb.append(i2).toString();
            String str3 = string + Marker.ANY_NON_NULL_MARKER;
            this.acknowledges.put(string, acknowledge);
            str2 = str3;
        }
        this.transport.send(String.format(Locale.ENGLISH, "%d:%s:%s:%s", Integer.valueOf(i), str2, socketIOClient.endpoint, str));
    }

    public void connect(SocketIOClient socketIOClient) {
        if (!this.clients.contains(socketIOClient)) {
            this.clients.add(socketIOClient);
        }
        this.transport.send(String.format(Locale.ENGLISH, "1::%s", socketIOClient.endpoint));
    }

    public void disconnect(SocketIOClient socketIOClient) {
        boolean z;
        this.clients.remove(socketIOClient);
        Iterator<SocketIOClient> it = this.clients.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = true;
                break;
            } else if (TextUtils.equals(it.next().endpoint, socketIOClient.endpoint) || TextUtils.isEmpty(socketIOClient.endpoint)) {
                z = false;
                break;
            }
        }
        SocketIOTransport socketIOTransport = this.transport;
        if (z && socketIOTransport != null) {
            socketIOTransport.send(String.format(Locale.ENGLISH, "0::%s", socketIOClient.endpoint));
        }
        if (this.clients.size() > 0 || socketIOTransport == null) {
            return;
        }
        socketIOTransport.setStringCallback(null);
        socketIOTransport.setClosedCallback(null);
        socketIOTransport.disconnect();
        this.transport = null;
    }

    void reconnect(DependentCancellable dependentCancellable) {
        if (isConnected()) {
            return;
        }
        Cancellable cancellable = this.connecting;
        if (cancellable != null && !cancellable.isDone() && !this.connecting.isCancelled()) {
            if (dependentCancellable != null) {
                dependentCancellable.setParent(this.connecting);
            }
        } else {
            this.request.logi("Reconnecting socket.io");
            SimpleFuture callback = ((AnonymousClass2) this.httpClient.executeString(this.request, null).then(new TransformFuture<SocketIOTransport, String>() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.2
                /* JADX INFO: Access modifiers changed from: protected */
                @Override // com.koushikdutta.async.future.TransformFuture
                public void transform(String str) throws Exception {
                    String[] strArrSplit = str.split(":");
                    final String str2 = strArrSplit[0];
                    if (!"".equals(strArrSplit[1])) {
                        SocketIOConnection.this.heartbeat = (Integer.parseInt(strArrSplit[1]) / 2) * 1000;
                    } else {
                        SocketIOConnection.this.heartbeat = 0;
                    }
                    HashSet hashSet = new HashSet(Arrays.asList(strArrSplit[3].split(",")));
                    final SimpleFuture simpleFuture = new SimpleFuture();
                    if (hashSet.contains("websocket")) {
                        SocketIOConnection.this.httpClient.websocket(Uri.parse(SocketIOConnection.this.request.getUri().toString()).buildUpon().appendPath("websocket").appendPath(str2).build().toString(), (String) null, (AsyncHttpClient.WebSocketConnectCallback) null).setCallback(new FutureCallback<WebSocket>() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.2.1
                            @Override // com.koushikdutta.async.future.FutureCallback
                            public void onCompleted(Exception exc, WebSocket webSocket) {
                                if (exc != null) {
                                    simpleFuture.setComplete(exc);
                                } else {
                                    simpleFuture.setComplete(new WebSocketTransport(webSocket, str2));
                                }
                            }
                        });
                    } else if (hashSet.contains("xhr-polling")) {
                        simpleFuture.setComplete(new XHRPollingTransport(SocketIOConnection.this.httpClient, Uri.parse(SocketIOConnection.this.request.getUri().toString()).buildUpon().appendPath("xhr-polling").appendPath(str2).build().toString(), str2));
                    } else {
                        throw new SocketIOException("transport not supported");
                    }
                    setComplete((Future) simpleFuture);
                }
            })).setCallback((FutureCallback) new FutureCallback<SocketIOTransport>() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.1
                @Override // com.koushikdutta.async.future.FutureCallback
                public void onCompleted(Exception exc, SocketIOTransport socketIOTransport) {
                    if (exc != null) {
                        SocketIOConnection.this.reportDisconnect(exc);
                        return;
                    }
                    SocketIOConnection socketIOConnection = SocketIOConnection.this;
                    socketIOConnection.reconnectDelay = socketIOConnection.request.config.reconnectDelay;
                    SocketIOConnection.this.transport = socketIOTransport;
                    SocketIOConnection.this.attach();
                }
            });
            this.connecting = callback;
            if (dependentCancellable != null) {
                dependentCancellable.setParent(callback);
            }
        }
    }

    void setupHeartbeat() {
        new Runnable() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.3
            @Override // java.lang.Runnable
            public void run() {
                SocketIOTransport socketIOTransport = SocketIOConnection.this.transport;
                if (SocketIOConnection.this.heartbeat <= 0 || socketIOTransport == null || !socketIOTransport.isConnected()) {
                    return;
                }
                socketIOTransport.send("2:::");
                socketIOTransport.getServer().postDelayed(this, SocketIOConnection.this.heartbeat);
            }
        }.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void select(String str, SelectCallback selectCallback) {
        for (SocketIOClient socketIOClient : this.clients) {
            if (str == null || TextUtils.equals(socketIOClient.endpoint, str)) {
                selectCallback.onSelect(socketIOClient);
            }
        }
    }

    private void delayReconnect() {
        if (this.transport != null || this.clients.size() == 0) {
            return;
        }
        Iterator<SocketIOClient> it = this.clients.iterator();
        while (it.hasNext()) {
            if (it.next().disconnected) {
                this.httpClient.getServer().postDelayed(new Runnable() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.4
                    @Override // java.lang.Runnable
                    public void run() {
                        SocketIOConnection.this.reconnect(null);
                    }
                }, nextReconnectDelay(this.reconnectDelay));
                this.reconnectDelay *= 2;
                if (this.request.config.reconnectDelayMax > 0) {
                    this.reconnectDelay = Math.min(this.reconnectDelay, this.request.config.reconnectDelayMax);
                    return;
                }
                return;
            }
        }
    }

    private long nextReconnectDelay(long j) {
        return (j < 2 || j > DurationKt.MAX_MILLIS || !this.request.config.randomizeReconnectDelay) ? j : (j >> 1) + ((long) (j * Math.random()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportDisconnect(final Exception exc) {
        if (exc != null) {
            this.request.loge("socket.io disconnected", exc);
        } else {
            this.request.logi("socket.io disconnected");
        }
        select(null, new SelectCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.5
            @Override // com.koushikdutta.async.http.socketio.SocketIOConnection.SelectCallback
            public void onSelect(SocketIOClient socketIOClient) {
                if (socketIOClient.connected) {
                    socketIOClient.disconnected = true;
                    DisconnectCallback disconnectCallback = socketIOClient.getDisconnectCallback();
                    if (disconnectCallback != null) {
                        disconnectCallback.onDisconnect(exc);
                        return;
                    }
                    return;
                }
                ConnectCallback connectCallback = socketIOClient.connectCallback;
                if (connectCallback != null) {
                    connectCallback.onConnectCompleted(exc, socketIOClient);
                }
            }
        });
        delayReconnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportConnect(String str) {
        select(str, new SelectCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.6
            @Override // com.koushikdutta.async.http.socketio.SocketIOConnection.SelectCallback
            public void onSelect(SocketIOClient socketIOClient) {
                if (socketIOClient.isConnected()) {
                    return;
                }
                if (!socketIOClient.connected) {
                    socketIOClient.connected = true;
                    ConnectCallback connectCallback = socketIOClient.connectCallback;
                    if (connectCallback != null) {
                        connectCallback.onConnectCompleted(null, socketIOClient);
                        return;
                    }
                    return;
                }
                if (socketIOClient.disconnected) {
                    socketIOClient.disconnected = false;
                    ReconnectCallback reconnectCallback = socketIOClient.reconnectCallback;
                    if (reconnectCallback != null) {
                        reconnectCallback.onReconnect();
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportJson(String str, final JSONObject jSONObject, final Acknowledge acknowledge) {
        select(str, new SelectCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.7
            @Override // com.koushikdutta.async.http.socketio.SocketIOConnection.SelectCallback
            public void onSelect(SocketIOClient socketIOClient) {
                JSONCallback jSONCallback = socketIOClient.jsonCallback;
                if (jSONCallback != null) {
                    jSONCallback.onJSON(jSONObject, acknowledge);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportString(String str, final String str2, final Acknowledge acknowledge) {
        select(str, new SelectCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.8
            @Override // com.koushikdutta.async.http.socketio.SocketIOConnection.SelectCallback
            public void onSelect(SocketIOClient socketIOClient) {
                StringCallback stringCallback = socketIOClient.stringCallback;
                if (stringCallback != null) {
                    stringCallback.onString(str2, acknowledge);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportEvent(String str, final String str2, final JSONArray jSONArray, final Acknowledge acknowledge) {
        select(str, new SelectCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.9
            @Override // com.koushikdutta.async.http.socketio.SocketIOConnection.SelectCallback
            public void onSelect(SocketIOClient socketIOClient) {
                socketIOClient.onEvent(str2, jSONArray, acknowledge);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportError(String str, final String str2) {
        select(str, new SelectCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.10
            @Override // com.koushikdutta.async.http.socketio.SocketIOConnection.SelectCallback
            public void onSelect(SocketIOClient socketIOClient) {
                ErrorCallback errorCallback = socketIOClient.errorCallback;
                if (errorCallback != null) {
                    errorCallback.onError(str2);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Acknowledge acknowledge(String str, final String str2) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        final String strReplaceAll = str.replaceAll("\\+$", "");
        return new Acknowledge() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.11
            @Override // com.koushikdutta.async.http.socketio.Acknowledge
            public void acknowledge(JSONArray jSONArray) {
                String str3;
                if (jSONArray == null) {
                    str3 = "";
                } else {
                    str3 = Marker.ANY_NON_NULL_MARKER + jSONArray.toString();
                }
                SocketIOTransport socketIOTransport = SocketIOConnection.this.transport;
                if (socketIOTransport == null) {
                    final SocketIOException socketIOException = new SocketIOException("not connected to server");
                    SocketIOConnection.this.select(str2, new SelectCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.11.1
                        @Override // com.koushikdutta.async.http.socketio.SocketIOConnection.SelectCallback
                        public void onSelect(SocketIOClient socketIOClient) {
                            ExceptionCallback exceptionCallback = socketIOClient.exceptionCallback;
                            if (exceptionCallback != null) {
                                exceptionCallback.onException(socketIOException);
                            }
                        }
                    });
                } else {
                    socketIOTransport.send(String.format(Locale.ENGLISH, "6:::%s%s", strReplaceAll, str3));
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attach() {
        if (this.transport.heartbeats()) {
            setupHeartbeat();
        }
        this.transport.setClosedCallback(new CompletedCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.12
            @Override // com.koushikdutta.async.callback.CompletedCallback
            public void onCompleted(Exception exc) {
                SocketIOConnection.this.transport = null;
                SocketIOConnection.this.reportDisconnect(exc);
            }
        });
        this.transport.setStringCallback(new SocketIOTransport.StringCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.13
            @Override // com.koushikdutta.async.http.socketio.transport.SocketIOTransport.StringCallback
            public void onStringAvailable(String str) {
                try {
                    String[] strArrSplit = str.split(":", 4);
                    switch (Integer.parseInt(strArrSplit[0])) {
                        case 0:
                            SocketIOConnection.this.transport.disconnect();
                            SocketIOConnection.this.reportDisconnect(null);
                            return;
                        case 1:
                            SocketIOConnection.this.reportConnect(strArrSplit[2]);
                            return;
                        case 2:
                            SocketIOConnection.this.transport.send("2::");
                            return;
                        case 3:
                            SocketIOConnection socketIOConnection = SocketIOConnection.this;
                            String str2 = strArrSplit[2];
                            socketIOConnection.reportString(str2, strArrSplit[3], socketIOConnection.acknowledge(strArrSplit[1], str2));
                            return;
                        case 4:
                            JSONObject jSONObject = new JSONObject(strArrSplit[3]);
                            SocketIOConnection socketIOConnection2 = SocketIOConnection.this;
                            String str3 = strArrSplit[2];
                            socketIOConnection2.reportJson(str3, jSONObject, socketIOConnection2.acknowledge(strArrSplit[1], str3));
                            return;
                        case 5:
                            JSONObject jSONObject2 = new JSONObject(strArrSplit[3]);
                            String string = jSONObject2.getString("name");
                            JSONArray jSONArrayOptJSONArray = jSONObject2.optJSONArray("args");
                            SocketIOConnection socketIOConnection3 = SocketIOConnection.this;
                            String str4 = strArrSplit[2];
                            socketIOConnection3.reportEvent(str4, string, jSONArrayOptJSONArray, socketIOConnection3.acknowledge(strArrSplit[1], str4));
                            return;
                        case 6:
                            String[] strArrSplit2 = strArrSplit[3].split("\\+", 2);
                            Acknowledge acknowledgeRemove = SocketIOConnection.this.acknowledges.remove(strArrSplit2[0]);
                            if (acknowledgeRemove == null) {
                                return;
                            }
                            acknowledgeRemove.acknowledge(strArrSplit2.length == 2 ? new JSONArray(strArrSplit2[1]) : null);
                            return;
                        case 7:
                            SocketIOConnection.this.reportError(strArrSplit[2], strArrSplit[3]);
                            return;
                        case 8:
                            return;
                        default:
                            throw new SocketIOException("unknown code");
                    }
                } catch (Exception e) {
                    SocketIOConnection.this.transport.setClosedCallback(null);
                    SocketIOConnection.this.transport.disconnect();
                    SocketIOConnection.this.transport = null;
                    SocketIOConnection.this.reportDisconnect(e);
                }
            }
        });
        select(null, new SelectCallback() { // from class: com.koushikdutta.async.http.socketio.SocketIOConnection.14
            @Override // com.koushikdutta.async.http.socketio.SocketIOConnection.SelectCallback
            public void onSelect(SocketIOClient socketIOClient) {
                if (TextUtils.isEmpty(socketIOClient.endpoint)) {
                    return;
                }
                SocketIOConnection.this.connect(socketIOClient);
            }
        });
    }
}
