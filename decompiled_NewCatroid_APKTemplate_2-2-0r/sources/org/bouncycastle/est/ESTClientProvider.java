package org.bouncycastle.est;

/* JADX INFO: loaded from: classes13.dex */
public interface ESTClientProvider {
    boolean isTrusted();

    ESTClient makeClient() throws ESTException;
}
