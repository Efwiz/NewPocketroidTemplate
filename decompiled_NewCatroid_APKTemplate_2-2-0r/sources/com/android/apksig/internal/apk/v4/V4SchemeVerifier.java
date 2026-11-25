package com.android.apksig.internal.apk.v4;

import com.android.apksig.ApkVerifier;
import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.android.apksig.internal.apk.ContentDigestAlgorithm;
import com.android.apksig.internal.apk.SignatureAlgorithm;
import com.android.apksig.internal.util.GuaranteedEncodedFormX509Certificate;
import com.android.apksig.internal.util.X509CertificateUtils;
import com.android.apksig.util.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public abstract class V4SchemeVerifier {
    private V4SchemeVerifier() {
    }

    public static ApkSigningBlockUtils.Result verify(DataSource apk, File v4SignatureFile) throws NoSuchAlgorithmException, IOException {
        FileInputStream fileInputStream = new FileInputStream(v4SignatureFile);
        try {
            V4Signature from = V4Signature.readFrom(fileInputStream);
            byte[] bytes = V4Signature.readBytes(fileInputStream);
            fileInputStream.close();
            ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(4);
            if (from == null) {
                result.addError(ApkVerifier.Issue.V4_SIG_NO_SIGNATURES, "Signature file does not contain a v4 signature.");
                return result;
            }
            if (from.version != 2) {
                result.addWarning(ApkVerifier.Issue.V4_SIG_VERSION_NOT_CURRENT, Integer.valueOf(from.version), 2);
            }
            V4Signature.HashingInfo hashingInfoFromByteArray = V4Signature.HashingInfo.fromByteArray(from.hashingInfo);
            V4Signature.SigningInfos signingInfosFromByteArray = V4Signature.SigningInfos.fromByteArray(from.signingInfos);
            V4Signature.SigningInfo signingInfo = signingInfosFromByteArray.signingInfo;
            ApkSigningBlockUtils.Result.SignerInfo andVerifySignatureBlock = parseAndVerifySignatureBlock(signingInfo, V4Signature.getSignedData(apk.size(), hashingInfoFromByteArray, signingInfo));
            result.signers.add(andVerifySignatureBlock);
            if (result.containsErrors()) {
                return result;
            }
            for (V4Signature.SigningInfoBlock signingInfoBlock : signingInfosFromByteArray.signingInfoBlocks) {
                V4Signature.SigningInfo signingInfoFromByteArray = V4Signature.SigningInfo.fromByteArray(signingInfoBlock.signingInfo);
                result.signers.add(parseAndVerifySignatureBlock(signingInfoFromByteArray, V4Signature.getSignedData(apk.size(), hashingInfoFromByteArray, signingInfoFromByteArray)));
                if (result.containsErrors()) {
                    return result;
                }
            }
            verifyRootHashAndTree(apk, andVerifySignatureBlock, hashingInfoFromByteArray.rawRootHash, bytes);
            if (!result.containsErrors()) {
                result.verified = true;
            }
            return result;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private static ApkSigningBlockUtils.Result.SignerInfo parseAndVerifySignatureBlock(V4Signature.SigningInfo signingInfo, final byte[] signedData) throws NoSuchAlgorithmException {
        byte[] encoded;
        ApkSigningBlockUtils.Result.SignerInfo signerInfo = new ApkSigningBlockUtils.Result.SignerInfo();
        signerInfo.index = 0;
        int i = signingInfo.signatureAlgorithmId;
        byte[] bArr = signingInfo.signature;
        signerInfo.signatures.add(new ApkSigningBlockUtils.Result.SignerInfo.Signature(i, bArr));
        SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(i);
        if (signatureAlgorithmFindById == null) {
            signerInfo.addError(ApkVerifier.Issue.V4_SIG_UNKNOWN_SIG_ALGORITHM, Integer.valueOf(i));
            return signerInfo;
        }
        String first = signatureAlgorithmFindById.getJcaSignatureAlgorithmAndParams().getFirst();
        AlgorithmParameterSpec second = signatureAlgorithmFindById.getJcaSignatureAlgorithmAndParams().getSecond();
        String jcaKeyAlgorithm = signatureAlgorithmFindById.getJcaKeyAlgorithm();
        byte[] bArr2 = signingInfo.publicKey;
        try {
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(jcaKeyAlgorithm).generatePublic(new X509EncodedKeySpec(bArr2));
            try {
                Signature signature = Signature.getInstance(first);
                signature.initVerify(publicKeyGeneratePublic);
                if (second != null) {
                    signature.setParameter(second);
                }
                signature.update(signedData);
                if (!signature.verify(bArr)) {
                    signerInfo.addError(ApkVerifier.Issue.V4_SIG_DID_NOT_VERIFY, signatureAlgorithmFindById);
                    return signerInfo;
                }
                signerInfo.verifiedSignatures.put(signatureAlgorithmFindById, bArr);
                if (signingInfo.certificate == null) {
                    signerInfo.addError(ApkVerifier.Issue.V4_SIG_NO_CERTIFICATE, new Object[0]);
                    return signerInfo;
                }
                try {
                    GuaranteedEncodedFormX509Certificate guaranteedEncodedFormX509Certificate = new GuaranteedEncodedFormX509Certificate(X509CertificateUtils.generateCertificate(signingInfo.certificate), signingInfo.certificate);
                    signerInfo.certs.add(guaranteedEncodedFormX509Certificate);
                    try {
                        encoded = ApkSigningBlockUtils.encodePublicKey(guaranteedEncodedFormX509Certificate.getPublicKey());
                    } catch (InvalidKeyException e) {
                        System.out.println("Caught an exception encoding the public key: " + e);
                        e.printStackTrace();
                        encoded = guaranteedEncodedFormX509Certificate.getPublicKey().getEncoded();
                    }
                    if (!Arrays.equals(bArr2, encoded)) {
                        signerInfo.addError(ApkVerifier.Issue.V4_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD, ApkSigningBlockUtils.toHex(encoded), ApkSigningBlockUtils.toHex(bArr2));
                        return signerInfo;
                    }
                    signerInfo.contentDigests.add(new ApkSigningBlockUtils.Result.SignerInfo.ContentDigest(0, signingInfo.apkDigest));
                    return signerInfo;
                } catch (CertificateException e2) {
                    signerInfo.addError(ApkVerifier.Issue.V4_SIG_MALFORMED_CERTIFICATE, e2);
                    return signerInfo;
                }
            } catch (InvalidAlgorithmParameterException | InvalidKeyException | SignatureException e3) {
                signerInfo.addError(ApkVerifier.Issue.V4_SIG_VERIFY_EXCEPTION, signatureAlgorithmFindById, e3);
                return signerInfo;
            }
        } catch (Exception e4) {
            signerInfo.addError(ApkVerifier.Issue.V4_SIG_MALFORMED_PUBLIC_KEY, e4);
            return signerInfo;
        }
    }

    private static void verifyRootHashAndTree(DataSource apkContent, ApkSigningBlockUtils.Result.SignerInfo signerInfo, byte[] expectedDigest, byte[] expectedTree) throws NoSuchAlgorithmException, IOException {
        ApkSigningBlockUtils.VerityTreeAndDigest verityTreeAndDigestComputeChunkVerityTreeAndDigest = ApkSigningBlockUtils.computeChunkVerityTreeAndDigest(apkContent);
        ContentDigestAlgorithm contentDigestAlgorithm = verityTreeAndDigestComputeChunkVerityTreeAndDigest.contentDigestAlgorithm;
        byte[] bArr = verityTreeAndDigestComputeChunkVerityTreeAndDigest.rootHash;
        byte[] bArr2 = verityTreeAndDigestComputeChunkVerityTreeAndDigest.tree;
        if (!Arrays.equals(expectedDigest, bArr)) {
            signerInfo.addError(ApkVerifier.Issue.V4_SIG_APK_ROOT_DID_NOT_VERIFY, contentDigestAlgorithm, ApkSigningBlockUtils.toHex(expectedDigest), ApkSigningBlockUtils.toHex(bArr));
        } else if (expectedTree != null && !Arrays.equals(expectedTree, bArr2)) {
            signerInfo.addError(ApkVerifier.Issue.V4_SIG_APK_TREE_DID_NOT_VERIFY, contentDigestAlgorithm, ApkSigningBlockUtils.toHex(expectedDigest), ApkSigningBlockUtils.toHex(bArr));
        } else {
            signerInfo.verifiedContentDigests.put(contentDigestAlgorithm, bArr);
        }
    }
}
