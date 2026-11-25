package com.android.apksig.internal.apk.v4;

import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.android.apksig.internal.apk.ContentDigestAlgorithm;
import com.android.apksig.internal.apk.SignatureAlgorithm;
import com.android.apksig.internal.apk.v2.V2SchemeVerifier;
import com.android.apksig.internal.apk.v3.V3SchemeSigner;
import com.android.apksig.internal.apk.v3.V3SchemeVerifier;
import com.android.apksig.internal.util.Pair;
import com.android.apksig.util.DataSource;
import com.android.apksig.zip.ZipFormatException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class V4SchemeSigner {
    private V4SchemeSigner() {
    }

    public static class SignerConfig {
        public final ApkSigningBlockUtils.SignerConfig v41Config;
        public final ApkSigningBlockUtils.SignerConfig v4Config;

        public SignerConfig(List<ApkSigningBlockUtils.SignerConfig> v4Configs, List<ApkSigningBlockUtils.SignerConfig> v41Configs) throws InvalidKeyException {
            if (v4Configs == null || v4Configs.size() != 1) {
                throw new InvalidKeyException("Only accepting one signer config for V4 Signature.");
            }
            if (v41Configs != null && v41Configs.size() != 1) {
                throw new InvalidKeyException("Only accepting one signer config for V4.1 Signature.");
            }
            this.v4Config = v4Configs.get(0);
            this.v41Config = v41Configs != null ? v41Configs.get(0) : null;
        }
    }

    public static List<SignatureAlgorithm> getSuggestedSignatureAlgorithms(PublicKey signingKey, int minSdkVersion, boolean apkSigningBlockPaddingSupported, boolean deterministicDsaSigning) throws InvalidKeyException {
        List<SignatureAlgorithm> suggestedSignatureAlgorithms = V3SchemeSigner.getSuggestedSignatureAlgorithms(signingKey, minSdkVersion, apkSigningBlockPaddingSupported, deterministicDsaSigning);
        ListIterator<SignatureAlgorithm> listIterator = suggestedSignatureAlgorithms.listIterator();
        while (listIterator.hasNext()) {
            if (!isSupported(listIterator.next().getContentDigestAlgorithm(), false)) {
                listIterator.remove();
            }
        }
        return suggestedSignatureAlgorithms;
    }

    public static void generateV4Signature(DataSource apkContent, SignerConfig signerConfig, File outputFile) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        Pair<V4Signature, byte[]> pairGenerateV4Signature = generateV4Signature(apkContent, signerConfig);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            try {
                pairGenerateV4Signature.getFirst().writeTo(fileOutputStream);
                V4Signature.writeBytes(fileOutputStream, pairGenerateV4Signature.getSecond());
                fileOutputStream.close();
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            outputFile.delete();
            throw e;
        }
    }

    public static Pair<V4Signature, byte[]> generateV4Signature(DataSource apkContent, SignerConfig signerConfig) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        long size = apkContent.size();
        byte[] apkDigest = getApkDigest(apkContent);
        ApkSigningBlockUtils.VerityTreeAndDigest verityTreeAndDigestComputeChunkVerityTreeAndDigest = ApkSigningBlockUtils.computeChunkVerityTreeAndDigest(apkContent);
        ContentDigestAlgorithm contentDigestAlgorithm = verityTreeAndDigestComputeChunkVerityTreeAndDigest.contentDigestAlgorithm;
        byte[] bArr = verityTreeAndDigestComputeChunkVerityTreeAndDigest.rootHash;
        byte[] bArr2 = verityTreeAndDigestComputeChunkVerityTreeAndDigest.tree;
        Pair<Integer, Byte> pairConvertToV4HashingInfo = convertToV4HashingInfo(contentDigestAlgorithm);
        try {
            return Pair.of(generateSignature(signerConfig, new V4Signature.HashingInfo(pairConvertToV4HashingInfo.getFirst().intValue(), pairConvertToV4HashingInfo.getSecond().byteValue(), null, bArr), apkDigest, null, size), bArr2);
        } catch (InvalidKeyException | SignatureException | CertificateEncodingException e) {
            throw new InvalidKeyException("Signer failed", e);
        }
    }

    private static V4Signature.SigningInfo generateSigningInfo(ApkSigningBlockUtils.SignerConfig signerConfig, V4Signature.HashingInfo hashingInfo, byte[] apkDigest, byte[] additionalData, long fileSize) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateEncodingException {
        if (signerConfig.certificates.isEmpty()) {
            throw new SignatureException("No certificates configured for signer");
        }
        if (signerConfig.certificates.size() != 1) {
            throw new CertificateEncodingException("Should only have one certificate");
        }
        PublicKey publicKey = signerConfig.certificates.get(0).getPublicKey();
        byte[] bArr = ApkSigningBlockUtils.encodeCertificates(signerConfig.certificates).get(0);
        List<Pair<Integer, byte[]>> listGenerateSignaturesOverData = ApkSigningBlockUtils.generateSignaturesOverData(signerConfig, V4Signature.getSignedData(fileSize, hashingInfo, new V4Signature.SigningInfo(apkDigest, bArr, additionalData, publicKey.getEncoded(), -1, null)));
        if (listGenerateSignaturesOverData.size() != 1) {
            throw new SignatureException("Should only be one signature generated");
        }
        return new V4Signature.SigningInfo(apkDigest, bArr, additionalData, publicKey.getEncoded(), listGenerateSignaturesOverData.get(0).getFirst().intValue(), listGenerateSignaturesOverData.get(0).getSecond());
    }

    private static V4Signature generateSignature(SignerConfig signerConfig, V4Signature.HashingInfo hashingInfo, byte[] apkDigest, byte[] additionalData, long fileSize) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateEncodingException {
        V4Signature.SigningInfos signingInfos;
        V4Signature.SigningInfo signingInfoGenerateSigningInfo = generateSigningInfo(signerConfig.v4Config, hashingInfo, apkDigest, additionalData, fileSize);
        if (signerConfig.v41Config != null) {
            signingInfos = new V4Signature.SigningInfos(signingInfoGenerateSigningInfo, new V4Signature.SigningInfoBlock(462663009, generateSigningInfo(signerConfig.v41Config, hashingInfo, apkDigest, additionalData, fileSize).toByteArray()));
        } else {
            signingInfos = new V4Signature.SigningInfos(signingInfoGenerateSigningInfo);
        }
        return new V4Signature(2, hashingInfo.toByteArray(), signingInfos.toByteArray());
    }

    private static byte[] getApkDigest(DataSource apk) throws IOException {
        try {
            ApkUtils.ZipSections zipSectionsFindZipSections = ApkUtils.findZipSections(apk);
            try {
                return getBestV3Digest(apk, zipSectionsFindZipSections);
            } catch (SignatureException e) {
                try {
                    return getBestV2Digest(apk, zipSectionsFindZipSections);
                } catch (SignatureException e2) {
                    throw new IOException("Failed to obtain v2/v3 digest, v3 exception: " + e + ", v2 exception: " + e2);
                }
            }
        } catch (ZipFormatException e3) {
            throw new IOException("Malformed APK: not a ZIP archive", e3);
        }
    }

    private static byte[] getBestV3Digest(DataSource apk, ApkUtils.ZipSections zipSections) throws SignatureException {
        HashSet hashSet = new HashSet(1);
        ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(3);
        try {
            V3SchemeVerifier.parseSigners(ApkSigningBlockUtils.findSignature(apk, zipSections, -262969152, result).signatureBlock, hashSet, result);
            if (result.signers.size() != 1) {
                throw new SignatureException("Should only have one signer, errors: " + result.getErrors());
            }
            ApkSigningBlockUtils.Result.SignerInfo signerInfo = result.signers.get(0);
            if (signerInfo.containsErrors()) {
                throw new SignatureException("Parsing failed: " + signerInfo.getErrors());
            }
            return pickBestDigest(result.signers.get(0).contentDigests);
        } catch (Exception e) {
            throw new SignatureException("Failed to extract and parse v3 block", e);
        }
    }

    private static byte[] getBestV2Digest(DataSource apk, ApkUtils.ZipSections zipSections) throws SignatureException {
        HashSet hashSet = new HashSet(1);
        HashSet hashSet2 = new HashSet(1);
        ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(2);
        try {
            V2SchemeVerifier.parseSigners(ApkSigningBlockUtils.findSignature(apk, zipSections, 1896449818, result).signatureBlock, hashSet, Collections.emptyMap(), hashSet2, Integer.MAX_VALUE, Integer.MAX_VALUE, result);
            if (result.signers.size() != 1) {
                throw new SignatureException("Should only have one signer, errors: " + result.getErrors());
            }
            ApkSigningBlockUtils.Result.SignerInfo signerInfo = result.signers.get(0);
            if (signerInfo.containsErrors()) {
                throw new SignatureException("Parsing failed: " + signerInfo.getErrors());
            }
            return pickBestDigest(signerInfo.contentDigests);
        } catch (Exception e) {
            throw new SignatureException("Failed to extract and parse v2 block", e);
        }
    }

    private static byte[] pickBestDigest(List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> contentDigests) throws SignatureException {
        int iDigestAlgorithmSortingOrder;
        if (contentDigests == null || contentDigests.isEmpty()) {
            throw new SignatureException("Should have at least one digest");
        }
        int i = -1;
        byte[] value = null;
        for (ApkSigningBlockUtils.Result.SignerInfo.ContentDigest contentDigest : contentDigests) {
            ContentDigestAlgorithm contentDigestAlgorithm = SignatureAlgorithm.findById(contentDigest.getSignatureAlgorithmId()).getContentDigestAlgorithm();
            if (isSupported(contentDigestAlgorithm, true) && i < (iDigestAlgorithmSortingOrder = digestAlgorithmSortingOrder(contentDigestAlgorithm))) {
                value = contentDigest.getValue();
                i = iDigestAlgorithmSortingOrder;
            }
        }
        if (value != null) {
            return value;
        }
        throw new SignatureException("Failed to find a supported digest in the source APK");
    }

    /* JADX INFO: renamed from: com.android.apksig.internal.apk.v4.V4SchemeSigner$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm;

        static {
            int[] iArr = new int[ContentDigestAlgorithm.values().length];
            $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm = iArr;
            try {
                iArr[ContentDigestAlgorithm.CHUNKED_SHA256.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm[ContentDigestAlgorithm.VERITY_CHUNKED_SHA256.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm[ContentDigestAlgorithm.CHUNKED_SHA512.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public static int digestAlgorithmSortingOrder(ContentDigestAlgorithm contentDigestAlgorithm) {
        int i = AnonymousClass1.$SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm[contentDigestAlgorithm.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i != 2) {
            return i != 3 ? -1 : 2;
        }
        return 1;
    }

    private static boolean isSupported(final ContentDigestAlgorithm contentDigestAlgorithm, boolean forV3Digest) {
        if (contentDigestAlgorithm == null) {
            return false;
        }
        if (contentDigestAlgorithm == ContentDigestAlgorithm.CHUNKED_SHA256 || contentDigestAlgorithm == ContentDigestAlgorithm.CHUNKED_SHA512) {
            return true;
        }
        return forV3Digest && contentDigestAlgorithm == ContentDigestAlgorithm.VERITY_CHUNKED_SHA256;
    }

    private static Pair<Integer, Byte> convertToV4HashingInfo(ContentDigestAlgorithm algorithm) throws NoSuchAlgorithmException {
        if (AnonymousClass1.$SwitchMap$com$android$apksig$internal$apk$ContentDigestAlgorithm[algorithm.ordinal()] == 2) {
            return Pair.of(1, (byte) 12);
        }
        throw new NoSuchAlgorithmException("Invalid hash algorithm, only SHA2-256 over 4 KB chunks supported.");
    }
}
