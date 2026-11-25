package com.android.apksig.internal.apk.v2;

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

/* JADX INFO: loaded from: classes.dex */
public abstract class V2SchemeSigner {
    public static final int APK_SIGNATURE_SCHEME_V2_BLOCK_ID = 1896449818;

    private V2SchemeSigner() {
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

    public static ApkSigningBlockUtils.SigningSchemeBlockAndDigests generateApkSignatureSchemeV2Block(RunnablesExecutor executor, DataSource beforeCentralDir, DataSource centralDir, DataSource eocd, List<ApkSigningBlockUtils.SignerConfig> signerConfigs, boolean v3SigningEnabled) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException {
        return generateApkSignatureSchemeV2Block(executor, beforeCentralDir, centralDir, eocd, signerConfigs, v3SigningEnabled, null);
    }

    public static ApkSigningBlockUtils.SigningSchemeBlockAndDigests generateApkSignatureSchemeV2Block(RunnablesExecutor executor, DataSource beforeCentralDir, DataSource centralDir, DataSource eocd, List<ApkSigningBlockUtils.SignerConfig> signerConfigs, boolean v3SigningEnabled, List<byte[]> preservedV2SignerBlocks) throws SignatureException, NoSuchAlgorithmException, IOException, InvalidKeyException {
        Pair<List<ApkSigningBlockUtils.SignerConfig>, Map<ContentDigestAlgorithm, byte[]>> pairComputeContentDigests = ApkSigningBlockUtils.computeContentDigests(executor, beforeCentralDir, centralDir, eocd, signerConfigs);
        return new ApkSigningBlockUtils.SigningSchemeBlockAndDigests(generateApkSignatureSchemeV2Block(pairComputeContentDigests.getFirst(), pairComputeContentDigests.getSecond(), v3SigningEnabled, preservedV2SignerBlocks), pairComputeContentDigests.getSecond());
    }

    private static Pair<byte[], Integer> generateApkSignatureSchemeV2Block(List<ApkSigningBlockUtils.SignerConfig> signerConfigs, Map<ContentDigestAlgorithm, byte[]> contentDigests, boolean v3SigningEnabled, List<byte[]> preservedV2SignerBlocks) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        ArrayList arrayList = new ArrayList(signerConfigs.size());
        if (preservedV2SignerBlocks != null && preservedV2SignerBlocks.size() > 0) {
            arrayList.addAll(preservedV2SignerBlocks);
        }
        Iterator<ApkSigningBlockUtils.SignerConfig> it = signerConfigs.iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
            try {
                arrayList.add(generateSignerBlock(it.next(), contentDigests, v3SigningEnabled));
            } catch (InvalidKeyException e) {
                throw new InvalidKeyException("Signer #" + i + " failed", e);
            } catch (SignatureException e2) {
                throw new SignatureException("Signer #" + i + " failed", e2);
            }
        }
        return Pair.of(ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(new byte[][]{ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(arrayList)}), 1896449818);
    }

    private static byte[] generateSignerBlock(ApkSigningBlockUtils.SignerConfig signerConfig, Map<ContentDigestAlgorithm, byte[]> contentDigests, boolean v3SigningEnabled) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        if (signerConfig.certificates.isEmpty()) {
            throw new SignatureException("No certificates configured for signer");
        }
        byte[] bArrEncodePublicKey = ApkSigningBlockUtils.encodePublicKey(signerConfig.certificates.get(0).getPublicKey());
        V2SignatureSchemeBlock.SignedData signedData = new V2SignatureSchemeBlock.SignedData();
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
            signedData.additionalAttributes = generateAdditionalAttributes(v3SigningEnabled);
            V2SignatureSchemeBlock.Signer signer = new V2SignatureSchemeBlock.Signer();
            signer.signedData = ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(new byte[][]{ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(signedData.digests), ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(signedData.certificates), signedData.additionalAttributes, new byte[0]});
            signer.publicKey = bArrEncodePublicKey;
            signer.signatures = new ArrayList();
            signer.signatures = ApkSigningBlockUtils.generateSignaturesOverData(signerConfig, signer.signedData);
            return ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedElements(new byte[][]{signer.signedData, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(signer.signatures), signer.publicKey});
        } catch (CertificateEncodingException e) {
            throw new SignatureException("Failed to encode certificates", e);
        }
    }

    private static byte[] generateAdditionalAttributes(boolean v3SigningEnabled) {
        if (v3SigningEnabled) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(12);
            byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
            byteBufferAllocate.putInt(8);
            byteBufferAllocate.putInt(V2SchemeConstants.STRIPPING_PROTECTION_ATTR_ID);
            byteBufferAllocate.putInt(3);
            return byteBufferAllocate.array();
        }
        return new byte[0];
    }

    private static final class V2SignatureSchemeBlock {

        private static final class Signer {
            public byte[] publicKey;
            public List<Pair<Integer, byte[]>> signatures;
            public byte[] signedData;

            private Signer() {
            }
        }

        private V2SignatureSchemeBlock() {
        }

        private static final class SignedData {
            public byte[] additionalAttributes;
            public List<byte[]> certificates;
            public List<Pair<Integer, byte[]>> digests;

            private SignedData() {
            }
        }
    }
}
