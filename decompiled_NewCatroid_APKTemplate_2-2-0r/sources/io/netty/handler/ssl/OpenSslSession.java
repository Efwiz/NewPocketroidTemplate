package io.netty.handler.ssl;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

/* JADX INFO: loaded from: classes11.dex */
interface OpenSslSession extends SSLSession {
    void handshakeFinished() throws SSLException;

    void tryExpandApplicationBufferSize(int i);
}
