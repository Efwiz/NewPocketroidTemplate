package org.bouncycastle.crypto.engines;

/* JADX INFO: loaded from: classes13.dex */
public class SEEDWrapEngine extends RFC3394WrapEngine {
    public SEEDWrapEngine() {
        super(new SEEDEngine());
    }
}
