package com.hivemq.client.mqtt.exceptions;

import com.hivemq.client.internal.util.AsyncRuntimeException;

/* JADX INFO: loaded from: classes10.dex */
public class ConnectionFailedException extends AsyncRuntimeException {
    public ConnectionFailedException(String str) {
        super(str);
    }

    public ConnectionFailedException(Throwable th) {
        super(th);
    }

    private ConnectionFailedException(ConnectionFailedException connectionFailedException) {
        super((AsyncRuntimeException) connectionFailedException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public ConnectionFailedException copy() {
        return new ConnectionFailedException(this);
    }
}
