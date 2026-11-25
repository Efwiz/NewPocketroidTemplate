package com.android.apksig.internal.apk.v3;

import com.android.apksig.SigningCertificateLineage;
import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.android.apksig.internal.apk.ContentDigestAlgorithm;
import com.android.apksig.internal.apk.SignatureAlgorithm;
import com.android.apksig.internal.util.Pair;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.RunnablesExecutor;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.interfaces.ECKey;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

/* JADX INFO: loaded from: classes.dex */
public class V3SchemeSigner {
    public static final int APK_SIGNATURE_SCHEME_V3_BLOCK_ID = -262969152;
    public static final int PROOF_OF_ROTATION_ATTR_ID = 1000370060;
    private final DataSource mBeforeCentralDir;
    private final int mBlockId;
    private final DataSource mCentralDir;
    private final DataSource mEocd;
    private final RunnablesExecutor mExecutor;
    private final OptionalInt mOptionalRotationMinSdkVersion;
    private final boolean mRotationTargetsDevRelease;
    private final List<ApkSigningBlockUtils.SignerConfig> mSignerConfigs;

    private V3SchemeSigner(DataSource beforeCentralDir, DataSource centralDir, DataSource eocd, List<ApkSigningBlockUtils.SignerConfig> signerConfigs, RunnablesExecutor executor, int blockId, OptionalInt optionalRotationMinSdkVersion, boolean rotationTargetsDevRelease) {
        this.mBeforeCentralDir = beforeCentralDir;
        this.mCentralDir = centralDir;
        this.mEocd = eocd;
        this.mSignerConfigs = signerConfigs;
        this.mExecutor = executor;
        this.mBlockId = blockId;
        this.mOptionalRotationMinSdkVersion = optionalRotationMinSdkVersion;
        this.mRotationTargetsDevRelease = rotationTargetsDevRelease;
    }

    public static List<SignatureAlgorithm> getSuggestedSignatureAlgorithms(PublicKey signingKey, int minSdkVersion, boolean verityEnabled, boolean deterministicDsaSigning) throws InvalidKeyException {
        SignatureAlgorithm signatureAlgorithm;
        String algorithm = signingKey.getAlgorithm();
        if ("RSA".equalsIgnoreCase(algorithm)) {
            if (((RSAKey) signingKey).getModulus().bitLength() <= 3072) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(SignatureAlgorithm.RSA_PKCS1_V1_5_WITH_SHA256);
                if (verityEnabled) {
                    arrayList.add(SignatureAlgorithm.VERITY_RSA_PKCS1_V1_5_WITH_SHA256);
                }
                return arrayList;
            }
            return Collections.singletonList(SignatureAlgorithm.RSA_PKCS1_V1_5_WITH_SHA512);
        }
        if ("DSA".equalsIgnoreCase(algorithm)) {
            ArrayList arrayList2 = new ArrayList();
            if (deterministicDsaSigning) {
                signatureAlgorithm = SignatureAlgorithm.DETDSA_WITH_SHA256;
            } else {
                signatureAlgorithm = SignatureAlgorithm.DSA_WITH_SHA256;
            }
            arrayList2.add(signatureAlgorithm);
            if (verityEnabled) {
                arrayList2.add(SignatureAlgorithm.VERITY_DSA_WITH_SHA256);
            }
            return arrayList2;
        }
        if ("EC".equalsIgnoreCase(algorithm)) {
            if (((ECKey) signingKey).getParams().getOrder().bitLength() <= 256) {
                ArrayList arrayList3 = new ArrayList();
                arrayList3.add(SignatureAlgorithm.ECDSA_WITH_SHA256);
                if (verityEnabled) {
                    arrayList3.add(SignatureAlgorithm.VERITY_ECDSA_WITH_SHA256);
                }
                return arrayList3;
            }
            return Collections.singletonList(SignatureAlgorithm.ECDSA_WITH_SHA512);
        }
        throw new InvalidKeyException("Unsupported key algorithm: " + algorithm);
    }

    public static ApkSigningBlockUtils.SigningSchemeBlockAndDigests generateApkSignatureSchemeV3Block(RunnablesExecutor executor, DataSource beforeCentralDir, DataSource centralDir, DataSource eocd, List<ApkSigningBlockUtils.SignerConfig> signerConfigs) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException {
        return new Builder(beforeCentralDir, centralDir, eocd, signerConfigs).setRunnablesExecutor(executor).setBlockId(-262969152).build().generateApkSignatureSchemeV3BlockAndDigests();
    }

    public static byte[] generateV3SignerAttribute(SigningCertificateLineage signingCertificateLineage) {
        byte[] bArrEncodeSigningCertificateLineage = signingCertificateLineage.encodeSigningCertificateLineage();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrEncodeSigningCertificateLineage.length + 8);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.putInt(bArrEncodeSigningCertificateLineage.length + 4);
        byteBufferAllocate.putInt(1000370060);
        byteBufferAllocate.put(bArrEncodeSigningCertificateLineage);
        return byteBufferAllocate.array();
    }

    private static byte[] generateV3RotationMinSdkVersionStrippingProtectionAttribute(int rotationMinSdkVersion) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(12);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.putInt(8);
        byteBufferAllocate.putInt(V3SchemeConstants.ROTATION_MIN_SDK_VERSION_ATTR_ID);
        byteBufferAllocate.putInt(rotationMinSdkVersion);
        return byteBufferAllocate.array();
    }

    private static byte[] generateV31RotationTargetsDevReleaseAttribute() {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(8);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.putInt(4);
        byteBufferAllocate.putInt(V3SchemeConstants.ROTATION_ON_DEV_RELEASE_ATTR_ID);
        return byteBufferAllocate.array();
    }

    public ApkSigningBlockUtils.SigningSchemeBlockAndDigests generateApkSignatureSchemeV3BlockAndDigests() throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException {
        Pair<List<ApkSigningBlockUtils.SignerConfig>, Map<ContentDigestAlgorithm, byte[]>> pairComputeContentDigests = ApkSigningBlockUtils.computeContentDigests(this.mExecutor, this.mBeforeCentralDir, this.mCentralDir, this.mEocd, this.mSignerConfigs);
        return new ApkSigningBlockUtils.SigningSchemeBlockAndDigests(generateApkSignatureSchemeV3Block(pairComputeContentDigests.getSecond()), pairComputeContentDigests.getSecond());
    }

    private Pair<byte[], Integer> generateApkSignatureSchemeV3Block(Map<ContentDigestAlgorithm, byte[]> contentDigests) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        ArrayList arrayList = new ArrayList(this.mSignerConfigs.size());
        Iterator<ApkSigningBlockUtils.SignerConfig> it = this.mSignerConfigs.iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
            try {
                arrayList.add(generateSignerBlock(it.next(), contentDigests));
            } catch (InvalidKeyException e) {
                throw new InvalidKeyException("Signer #" + i + " failed", e);
            } catch (SignatureException e2) {
                throw new SignatureException("Signer #" + i + " failed", e2);
            }
        }
        return Pair.of(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(new byte[][]{ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(arrayList)}), Integer.valueOf(this.mBlockId));
    }

    private byte[] generateSignerBlock(ApkSigningBlockUtils.SignerConfig signerConfig, Map<ContentDigestAlgorithm, byte[]> contentDigests) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if (signerConfig.certificates.isEmpty()) {
            throw new SignatureException("No certificates configured for signer");
        }
        byte[] bArrEncodePublicKey = ApkSigningBlockUtils.encodePublicKey(signerConfig.certificates.get(0).getPublicKey());
        V3SignatureSchemeBlock.SignedData signedData = new V3SignatureSchemeBlock.SignedData();
        try {
            signedData.certificates = ApkSigningBlockUtils.encodeCertificates(signerConfig.certificates);
            ArrayList arrayList = new ArrayList(signerConfig.signatureAlgorithms.size());
            for (SignatureAlgorithm signatureAlgorithm : signerConfig.signatureAlgorithms) {
                ContentDigestAlgorithm contentDigestAlgorithm = signatureAlgorithm.getContentDigestAlgorithm();
                byte[] bArr = contentDigests.get(contentDigestAlgorithm);
                if (bArr == null) {
                    throw new RuntimeException(contentDigestAlgorithm + " content digest for " + signatureAlgorithm + " not computed");
                }
                arrayList.add(Pair.of(Integer.valueOf(signatureAlgorithm.getId()), bArr));
            }
            signedData.digests = arrayList;
            signedData.minSdkVersion = signerConfig.minSdkVersion;
            signedData.maxSdkVersion = signerConfig.maxSdkVersion;
            signedData.additionalAttributes = generateAdditionalAttributes(signerConfig);
            V3SignatureSchemeBlock.Signer signer = new V3SignatureSchemeBlock.Signer();
            signer.signedData = encodeSignedData(signedData);
            signer.minSdkVersion = signerConfig.minSdkVersion;
            signer.maxSdkVersion = signerConfig.maxSdkVersion;
            signer.publicKey = bArrEncodePublicKey;
            signer.signatures = ApkSigningBlockUtils.generateSignaturesOverData(signerConfig, signer.signedData);
            return encodeSigner(signer);
        } catch (CertificateEncodingException e) {
            throw new SignatureException("Failed to encode certificates", e);
        }
    }

    private byte[] encodeSigner(V3SignatureSchemeBlock.Signer signer) {
        byte[] bArrEncodeAsLengthPrefixedElement = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(signer.signedData);
        byte[] bArrEncodeAsLengthPrefixedElement2 = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(signer.signatures));
        byte[] bArrEncodeAsLengthPrefixedElement3 = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(signer.publicKey);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrEncodeAsLengthPrefixedElement.length + 8 + bArrEncodeAsLengthPrefixedElement2.length + bArrEncodeAsLengthPrefixedElement3.length);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement);
        byteBufferAllocate.putInt(signer.minSdkVersion);
        byteBufferAllocate.putInt(signer.maxSdkVersion);
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement2);
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement3);
        return byteBufferAllocate.array();
    }

    private byte[] encodeSignedData(V3SignatureSchemeBlock.SignedData signedData) {
        byte[] bArrEncodeAsLengthPrefixedElement = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(signedData.digests));
        byte[] bArrEncodeAsLengthPrefixedElement2 = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(signedData.certificates));
        byte[] bArrEncodeAsLengthPrefixedElement3 = ApkSigningBlockUtils.encodeAsLengthPrefixedElement(signedData.additionalAttributes);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrEncodeAsLengthPrefixedElement.length + bArrEncodeAsLengthPrefixedElement2.length + 8 + bArrEncodeAsLengthPrefixedElement3.length);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement);
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement2);
        byteBufferAllocate.putInt(signedData.minSdkVersion);
        byteBufferAllocate.putInt(signedData.maxSdkVersion);
        byteBufferAllocate.put(bArrEncodeAsLengthPrefixedElement3);
        return byteBufferAllocate.array();
    }

    private byte[] generateAdditionalAttributes(ApkSigningBlockUtils.SignerConfig signerConfig) {
        if (signerConfig.mSigningCertificateLineage != null) {
            byte[] bArrGenerateV3SignerAttribute = generateV3SignerAttribute(signerConfig.mSigningCertificateLineage);
            if (!this.mRotationTargetsDevRelease || this.mBlockId != 462663009) {
                return bArrGenerateV3SignerAttribute;
            }
            byte[] bArrGenerateV31RotationTargetsDevReleaseAttribute = generateV31RotationTargetsDevReleaseAttribute();
            byte[] bArr = new byte[bArrGenerateV3SignerAttribute.length + bArrGenerateV31RotationTargetsDevReleaseAttribute.length];
            System.arraycopy(bArrGenerateV3SignerAttribute, 0, bArr, 0, bArrGenerateV3SignerAttribute.length);
            System.arraycopy(bArrGenerateV31RotationTargetsDevReleaseAttribute, 0, bArr, bArrGenerateV3SignerAttribute.length, bArrGenerateV31RotationTargetsDevReleaseAttribute.length);
            return bArr;
        }
        if (this.mOptionalRotationMinSdkVersion.isPresent()) {
            return generateV3RotationMinSdkVersionStrippingProtectionAttribute(this.mOptionalRotationMinSdkVersion.getAsInt());
        }
        return new byte[0];
    }

    private static final class V3SignatureSchemeBlock {

        private static final class Signer {
            public int maxSdkVersion;
            public int minSdkVersion;
            public byte[] publicKey;
            public List<Pair<Integer, byte[]>> signatures;
            public byte[] signedData;

            private Signer() {
            }
        }

        private V3SignatureSchemeBlock() {
        }

        private static final class SignedData {
            public byte[] additionalAttributes;
            public List<byte[]> certificates;
            public List<Pair<Integer, byte[]>> digests;
            public int maxSdkVersion;
            public int minSdkVersion;

            private SignedData() {
            }
        }
    }

    public static class Builder {
        private final DataSource mBeforeCentralDir;
        private final DataSource mCentralDir;
        private final DataSource mEocd;
        private final List<ApkSigningBlockUtils.SignerConfig> mSignerConfigs;
        private RunnablesExecutor mExecutor = RunnablesExecutor.MULTI_THREADED;
        private int mBlockId = -262969152;
        private OptionalInt mOptionalRotationMinSdkVersion = OptionalInt.empty();
        private boolean mRotationTargetsDevRelease = false;

        public Builder(DataSource beforeCentralDir, DataSource centralDir, DataSource eocd, List<ApkSigningBlockUtils.SignerConfig> signerConfigs) {
            this.mBeforeCentralDir = beforeCentralDir;
            this.mCentralDir = centralDir;
            this.mEocd = eocd;
            this.mSignerConfigs = signerConfigs;
        }

        public Builder setRunnablesExecutor(RunnablesExecutor executor) {
            this.mExecutor = executor;
            return this;
        }

        public Builder setBlockId(int blockId) {
            this.mBlockId = blockId;
            return this;
        }

        public Builder setRotationMinSdkVersion(int rotationMinSdkVersion) {
            this.mOptionalRotationMinSdkVersion = OptionalInt.of(rotationMinSdkVersion);
            return this;
        }

        public Builder setRotationTargetsDevRelease(boolean enabled) {
            this.mRotationTargetsDevRelease = enabled;
            return this;
        }

        public V3SchemeSigner build() {
            return new V3SchemeSigner(this.mBeforeCentralDir, this.mCentralDir, this.mEocd, this.mSignerConfigs, this.mExecutor, this.mBlockId, this.mOptionalRotationMinSdkVersion, this.mRotationTargetsDevRelease);
        }
    }
}
