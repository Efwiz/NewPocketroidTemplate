package org.bouncycastle.crypto.tls;

import java.io.IOException;

/* JADX INFO: loaded from: classes13.dex */
interface DTLSHandshakeRetransmit {
    void receivedHandshakeRecord(int i, byte[] bArr, int i2, int i3) throws IOException;
}
