package org.bouncycastle.crypto.tls;

import java.io.IOException;

/* JADX INFO: loaded from: classes13.dex */
public interface TlsCipherFactory {
    TlsCipher createCipher(TlsContext tlsContext, int i, int i2) throws IOException;
}
