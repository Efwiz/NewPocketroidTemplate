package org.bouncycastle.crypto.tls;

import org.bouncycastle.crypto.Digest;

/* JADX INFO: loaded from: classes13.dex */
public interface TlsHandshakeHash extends Digest {
    Digest forkPRFHash();

    byte[] getFinalHash(short s);

    void init(TlsContext tlsContext);

    TlsHandshakeHash notifyPRFDetermined();

    void sealHashAlgorithms();

    TlsHandshakeHash stopTracking();

    void trackHashAlgorithm(short s);
}
