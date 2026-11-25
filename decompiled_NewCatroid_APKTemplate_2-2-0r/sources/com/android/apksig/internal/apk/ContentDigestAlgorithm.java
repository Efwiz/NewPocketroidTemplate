package com.android.apksig.internal.apk;

import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public enum ContentDigestAlgorithm {
    CHUNKED_SHA256(1, McElieceCCA2KeyGenParameterSpec.SHA256, 32),
    CHUNKED_SHA512(2, McElieceCCA2KeyGenParameterSpec.SHA512, 64),
    VERITY_CHUNKED_SHA256(3, McElieceCCA2KeyGenParameterSpec.SHA256, 32),
    SHA256(4, McElieceCCA2KeyGenParameterSpec.SHA256, 32);

    private final int mChunkDigestOutputSizeBytes;
    private final int mId;
    private final String mJcaMessageDigestAlgorithm;

    ContentDigestAlgorithm(int id2, String jcaMessageDigestAlgorithm, int chunkDigestOutputSizeBytes) {
        this.mId = id2;
        this.mJcaMessageDigestAlgorithm = jcaMessageDigestAlgorithm;
        this.mChunkDigestOutputSizeBytes = chunkDigestOutputSizeBytes;
    }

    public int getId() {
        return this.mId;
    }

    String getJcaMessageDigestAlgorithm() {
        return this.mJcaMessageDigestAlgorithm;
    }

    int getChunkDigestOutputSizeBytes() {
        return this.mChunkDigestOutputSizeBytes;
    }
}
