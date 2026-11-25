package com.android.apksig.internal.apk;

import com.android.apksig.internal.util.Pair;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public enum SignatureAlgorithm {
    RSA_PSS_WITH_SHA256(257, ContentDigestAlgorithm.CHUNKED_SHA256, "RSA", Pair.of("SHA256withRSA/PSS", new PSSParameterSpec(McElieceCCA2KeyGenParameterSpec.SHA256, "MGF1", MGF1ParameterSpec.SHA256, 32, 1)), 24, 23),
    RSA_PSS_WITH_SHA512(258, ContentDigestAlgorithm.CHUNKED_SHA512, "RSA", Pair.of("SHA512withRSA/PSS", new PSSParameterSpec(McElieceCCA2KeyGenParameterSpec.SHA512, "MGF1", MGF1ParameterSpec.SHA512, 64, 1)), 24, 23),
    RSA_PKCS1_V1_5_WITH_SHA256(259, ContentDigestAlgorithm.CHUNKED_SHA256, "RSA", Pair.of("SHA256withRSA", null), 24, 1),
    RSA_PKCS1_V1_5_WITH_SHA512(260, ContentDigestAlgorithm.CHUNKED_SHA512, "RSA", Pair.of("SHA512withRSA", null), 24, 1),
    ECDSA_WITH_SHA256(513, ContentDigestAlgorithm.CHUNKED_SHA256, "EC", Pair.of("SHA256withECDSA", null), 24, 11),
    ECDSA_WITH_SHA512(514, ContentDigestAlgorithm.CHUNKED_SHA512, "EC", Pair.of("SHA512withECDSA", null), 24, 11),
    DSA_WITH_SHA256(769, ContentDigestAlgorithm.CHUNKED_SHA256, "DSA", Pair.of("SHA256withDSA", null), 24, 1),
    DETDSA_WITH_SHA256(769, ContentDigestAlgorithm.CHUNKED_SHA256, "DSA", Pair.of("SHA256withDetDSA", null), 24, 1),
    VERITY_RSA_PKCS1_V1_5_WITH_SHA256(1057, ContentDigestAlgorithm.VERITY_CHUNKED_SHA256, "RSA", Pair.of("SHA256withRSA", null), 28, 1),
    VERITY_ECDSA_WITH_SHA256(1059, ContentDigestAlgorithm.VERITY_CHUNKED_SHA256, "EC", Pair.of("SHA256withECDSA", null), 28, 11),
    VERITY_DSA_WITH_SHA256(1061, ContentDigestAlgorithm.VERITY_CHUNKED_SHA256, "DSA", Pair.of("SHA256withDSA", null), 28, 1);

    private final ContentDigestAlgorithm mContentDigestAlgorithm;
    private final int mId;
    private final String mJcaKeyAlgorithm;
    private final int mJcaSigAlgMinSdkVersion;
    private final Pair<String, ? extends AlgorithmParameterSpec> mJcaSignatureAlgAndParams;
    private final int mMinSdkVersion;

    SignatureAlgorithm(int id2, ContentDigestAlgorithm contentDigestAlgorithm, String jcaKeyAlgorithm, Pair jcaSignatureAlgAndParams, int minSdkVersion, int jcaSigAlgMinSdkVersion) {
        this.mId = id2;
        this.mContentDigestAlgorithm = contentDigestAlgorithm;
        this.mJcaKeyAlgorithm = jcaKeyAlgorithm;
        this.mJcaSignatureAlgAndParams = jcaSignatureAlgAndParams;
        this.mMinSdkVersion = minSdkVersion;
        this.mJcaSigAlgMinSdkVersion = jcaSigAlgMinSdkVersion;
    }

    public int getId() {
        return this.mId;
    }

    public ContentDigestAlgorithm getContentDigestAlgorithm() {
        return this.mContentDigestAlgorithm;
    }

    public String getJcaKeyAlgorithm() {
        return this.mJcaKeyAlgorithm;
    }

    public Pair<String, ? extends AlgorithmParameterSpec> getJcaSignatureAlgorithmAndParams() {
        return this.mJcaSignatureAlgAndParams;
    }

    public int getMinSdkVersion() {
        return this.mMinSdkVersion;
    }

    public int getJcaSigAlgMinSdkVersion() {
        return this.mJcaSigAlgMinSdkVersion;
    }

    public static SignatureAlgorithm findById(int id2) {
        for (SignatureAlgorithm signatureAlgorithm : values()) {
            if (signatureAlgorithm.getId() == id2) {
                return signatureAlgorithm;
            }
        }
        return null;
    }
}
