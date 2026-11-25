package com.hivemq.client.mqtt.exceptions;

import com.hivemq.client.internal.util.AsyncRuntimeException;

/* JADX INFO: loaded from: classes10.dex */
public class ConnectionClosedException extends AsyncRuntimeException {
    public ConnectionClosedException(String str) {
        super(str);
    }

    public ConnectionClosedException(Throwable th) {
        super(th);
    }

    private ConnectionClosedException(ConnectionClosedException connectionClosedException) {
        super((AsyncRuntimeException) connectionClosedException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public ConnectionClosedException copy() {
        return new ConnectionClosedException(this);
    }
}
