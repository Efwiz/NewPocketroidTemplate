package org.bouncycastle.crypto.prng;

/* JADX INFO: loaded from: classes13.dex */
public interface EntropySource {
    int entropySize();

    byte[] getEntropy();

    boolean isPredictionResistant();
}
