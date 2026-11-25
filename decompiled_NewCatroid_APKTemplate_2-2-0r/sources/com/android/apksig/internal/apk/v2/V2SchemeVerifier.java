package com.android.apksig.internal.apk.v2;

import com.android.apksig.ApkVerifier;
import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.android.apksig.internal.apk.ContentDigestAlgorithm;
import com.android.apksig.internal.apk.SignatureAlgorithm;
import com.android.apksig.internal.apk.SignatureInfo;
import com.android.apksig.internal.util.ByteBufferUtils;
import com.android.apksig.internal.util.GuaranteedEncodedFormX509Certificate;
import com.android.apksig.internal.util.X509CertificateUtils;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.RunnablesExecutor;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class V2SchemeVerifier {
    private V2SchemeVerifier() {
    }

    public static ApkSigningBlockUtils.Result verify(RunnablesExecutor executor, DataSource apk, ApkUtils.ZipSections zipSections, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundSigSchemeIds, int minSdkVersion, int maxSdkVersion) throws ApkSigningBlockUtils.SignatureNotFoundException, NoSuchAlgorithmException, IOException, ApkFormatException {
        ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(2);
        SignatureInfo signatureInfoFindSignature = ApkSigningBlockUtils.findSignature(apk, zipSections, 1896449818, result);
        verify(executor, apk.slice(0L, signatureInfoFindSignature.apkSigningBlockOffset), signatureInfoFindSignature.signatureBlock, apk.slice(signatureInfoFindSignature.centralDirOffset, signatureInfoFindSignature.eocdOffset - signatureInfoFindSignature.centralDirOffset), signatureInfoFindSignature.eocd, supportedApkSigSchemeNames, foundSigSchemeIds, minSdkVersion, maxSdkVersion, result);
        return result;
    }

    private static void verify(RunnablesExecutor executor, DataSource beforeApkSigningBlock, ByteBuffer apkSignatureSchemeV2Block, DataSource centralDir, ByteBuffer eocd, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundSigSchemeIds, int minSdkVersion, int maxSdkVersion, ApkSigningBlockUtils.Result result) throws NoSuchAlgorithmException, IOException {
        HashSet hashSet = new HashSet(1);
        parseSigners(apkSignatureSchemeV2Block, hashSet, supportedApkSigSchemeNames, foundSigSchemeIds, minSdkVersion, maxSdkVersion, result);
        if (result.containsErrors()) {
            return;
        }
        ApkSigningBlockUtils.verifyIntegrity(executor, beforeApkSigningBlock, centralDir, eocd, hashSet, result);
        if (result.containsErrors()) {
            return;
        }
        result.verified = true;
    }

    public static void parseSigners(ByteBuffer apkSignatureSchemeV2Block, Set<ContentDigestAlgorithm> contentDigestsToVerify, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundApkSigSchemeIds, int minSdkVersion, int maxSdkVersion, ApkSigningBlockUtils.Result result) throws NoSuchAlgorithmException {
        try {
            ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(apkSignatureSchemeV2Block);
            if (!lengthPrefixedSlice.hasRemaining()) {
                result.addError(ApkVerifier.Issue.V2_SIG_NO_SIGNERS, new Object[0]);
                return;
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                int i = 0;
                while (lengthPrefixedSlice.hasRemaining()) {
                    int i2 = i + 1;
                    ApkSigningBlockUtils.Result.SignerInfo signerInfo = new ApkSigningBlockUtils.Result.SignerInfo();
                    signerInfo.index = i;
                    result.signers.add(signerInfo);
                    try {
                        parseSigner(ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice), certificateFactory, signerInfo, contentDigestsToVerify, supportedApkSigSchemeNames, foundApkSigSchemeIds, minSdkVersion, maxSdkVersion);
                        i = i2;
                    } catch (ApkFormatException | BufferUnderflowException unused) {
                        signerInfo.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_SIGNER, new Object[0]);
                        return;
                    }
                }
            } catch (CertificateException e) {
                throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e);
            }
        } catch (ApkFormatException unused2) {
            result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_SIGNERS, new Object[0]);
        }
    }

    private static void parseSigner(ByteBuffer signerBlock, CertificateFactory certFactory, ApkSigningBlockUtils.Result.SignerInfo result, Set<ContentDigestAlgorithm> contentDigestsToVerify, Map<Integer, String> supportedApkSigSchemeNames, Set<Integer> foundApkSigSchemeIds, int minSdkVersion, int maxSdkVersion) throws NoSuchAlgorithmException, ApkFormatException {
        byte[] encoded;
        ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(signerBlock);
        byte[] bArr = new byte[lengthPrefixedSlice.remaining()];
        lengthPrefixedSlice.get(bArr);
        lengthPrefixedSlice.flip();
        result.signedData = bArr;
        ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtils.getLengthPrefixedSlice(signerBlock);
        byte[] lengthPrefixedByteArray = ApkSigningBlockUtils.readLengthPrefixedByteArray(signerBlock);
        ArrayList arrayList = new ArrayList(1);
        int i = 0;
        int i2 = 0;
        while (lengthPrefixedSlice2.hasRemaining()) {
            i2++;
            try {
                ByteBuffer lengthPrefixedSlice3 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice2);
                int i3 = lengthPrefixedSlice3.getInt();
                byte[] lengthPrefixedByteArray2 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice3);
                result.signatures.add(new ApkSigningBlockUtils.Result.SignerInfo.Signature(i3, lengthPrefixedByteArray2));
                SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(i3);
                if (signatureAlgorithmFindById == null) {
                    result.addWarning(ApkVerifier.Issue.V2_SIG_UNKNOWN_SIG_ALGORITHM, Integer.valueOf(i3));
                } else {
                    arrayList.add(new ApkSigningBlockUtils.SupportedSignature(signatureAlgorithmFindById, lengthPrefixedByteArray2));
                }
            } catch (ApkFormatException | BufferUnderflowException unused) {
                result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_SIGNATURE, Integer.valueOf(i2));
                return;
            }
        }
        if (result.signatures.isEmpty()) {
            result.addError(ApkVerifier.Issue.V2_SIG_NO_SIGNATURES, new Object[0]);
            return;
        }
        try {
            for (ApkSigningBlockUtils.SupportedSignature supportedSignature : ApkSigningBlockUtils.getSignaturesToVerify(arrayList, minSdkVersion, maxSdkVersion)) {
                SignatureAlgorithm signatureAlgorithm = supportedSignature.algorithm;
                String first = signatureAlgorithm.getJcaSignatureAlgorithmAndParams().getFirst();
                AlgorithmParameterSpec second = signatureAlgorithm.getJcaSignatureAlgorithmAndParams().getSecond();
                try {
                    PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(signatureAlgorithm.getJcaKeyAlgorithm()).generatePublic(new X509EncodedKeySpec(lengthPrefixedByteArray));
                    try {
                        Signature signature = Signature.getInstance(first);
                        signature.initVerify(publicKeyGeneratePublic);
                        if (second != null) {
                            signature.setParameter(second);
                        }
                        lengthPrefixedSlice.position(0);
                        signature.update(lengthPrefixedSlice);
                        byte[] bArr2 = supportedSignature.signature;
                        if (!signature.verify(bArr2)) {
                            result.addError(ApkVerifier.Issue.V2_SIG_DID_NOT_VERIFY, signatureAlgorithm);
                            return;
                        } else {
                            result.verifiedSignatures.put(signatureAlgorithm, bArr2);
                            contentDigestsToVerify.add(signatureAlgorithm.getContentDigestAlgorithm());
                        }
                    } catch (InvalidAlgorithmParameterException | InvalidKeyException | SignatureException e) {
                        result.addError(ApkVerifier.Issue.V2_SIG_VERIFY_EXCEPTION, signatureAlgorithm, e);
                        return;
                    }
                } catch (Exception e2) {
                    result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_PUBLIC_KEY, e2);
                    return;
                }
            }
            lengthPrefixedSlice.position(0);
            ByteBuffer lengthPrefixedSlice4 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            ByteBuffer lengthPrefixedSlice5 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            ByteBuffer lengthPrefixedSlice6 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            int i4 = -1;
            while (lengthPrefixedSlice5.hasRemaining()) {
                int i5 = i4 + 1;
                byte[] lengthPrefixedByteArray3 = ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice5);
                try {
                    result.certs.add(new GuaranteedEncodedFormX509Certificate(X509CertificateUtils.generateCertificate(lengthPrefixedByteArray3, certFactory), lengthPrefixedByteArray3));
                    i4 = i5;
                } catch (CertificateException e3) {
                    result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_CERTIFICATE, Integer.valueOf(i5), Integer.valueOf(i4 + 2), e3);
                    return;
                }
            }
            if (result.certs.isEmpty()) {
                result.addError(ApkVerifier.Issue.V2_SIG_NO_CERTIFICATES, new Object[0]);
                return;
            }
            X509Certificate x509Certificate = result.certs.get(0);
            try {
                encoded = ApkSigningBlockUtils.encodePublicKey(x509Certificate.getPublicKey());
            } catch (InvalidKeyException e4) {
                System.out.println("Caught an exception encoding the public key: " + e4);
                e4.printStackTrace();
                encoded = x509Certificate.getPublicKey().getEncoded();
            }
            if (!Arrays.equals(lengthPrefixedByteArray, encoded)) {
                result.addError(ApkVerifier.Issue.V2_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD, ApkSigningBlockUtils.toHex(encoded), ApkSigningBlockUtils.toHex(lengthPrefixedByteArray));
                return;
            }
            int i6 = 0;
            while (lengthPrefixedSlice4.hasRemaining()) {
                i6++;
                try {
                    ByteBuffer lengthPrefixedSlice7 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice4);
                    result.contentDigests.add(new ApkSigningBlockUtils.Result.SignerInfo.ContentDigest(lengthPrefixedSlice7.getInt(), ApkSigningBlockUtils.readLengthPrefixedByteArray(lengthPrefixedSlice7)));
                } catch (ApkFormatException | BufferUnderflowException unused2) {
                    result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_DIGEST, Integer.valueOf(i6));
                    return;
                }
            }
            ArrayList arrayList2 = new ArrayList(result.signatures.size());
            Iterator<ApkSigningBlockUtils.Result.SignerInfo.Signature> it = result.signatures.iterator();
            while (it.hasNext()) {
                arrayList2.add(Integer.valueOf(it.next().getAlgorithmId()));
            }
            ArrayList arrayList3 = new ArrayList(result.contentDigests.size());
            Iterator<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> it2 = result.contentDigests.iterator();
            while (it2.hasNext()) {
                arrayList3.add(Integer.valueOf(it2.next().getSignatureAlgorithmId()));
            }
            if (!arrayList2.equals(arrayList3)) {
                result.addError(ApkVerifier.Issue.V2_SIG_SIG_ALG_MISMATCH_BETWEEN_SIGNATURES_AND_DIGESTS_RECORDS, arrayList2, arrayList3);
                return;
            }
            Set<Integer> setKeySet = supportedApkSigSchemeNames.keySet();
            HashSet hashSet = new HashSet(1);
            while (lengthPrefixedSlice6.hasRemaining()) {
                i++;
                try {
                    ByteBuffer lengthPrefixedSlice8 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice6);
                    int i7 = lengthPrefixedSlice8.getInt();
                    byte[] byteArray = ByteBufferUtils.toByteArray(lengthPrefixedSlice8);
                    result.additionalAttributes.add(new ApkSigningBlockUtils.Result.SignerInfo.AdditionalAttribute(i7, byteArray));
                    if (i7 == -1091571699) {
                        int i8 = ByteBuffer.wrap(byteArray).order(ByteOrder.LITTLE_ENDIAN).getInt();
                        if (setKeySet.contains(Integer.valueOf(i8))) {
                            hashSet.add(Integer.valueOf(i8));
                        } else {
                            result.addWarning(ApkVerifier.Issue.V2_SIG_UNKNOWN_APK_SIG_SCHEME_ID, Integer.valueOf(result.index), Integer.valueOf(i8));
                        }
                    } else {
                        result.addWarning(ApkVerifier.Issue.V2_SIG_UNKNOWN_ADDITIONAL_ATTRIBUTE, Integer.valueOf(i7));
                    }
                } catch (ApkFormatException | BufferUnderflowException unused3) {
                    result.addError(ApkVerifier.Issue.V2_SIG_MALFORMED_ADDITIONAL_ATTRIBUTE, Integer.valueOf(i));
                    return;
                }
            }
            Iterator it3 = hashSet.iterator();
            while (it3.hasNext()) {
                int iIntValue = ((Integer) it3.next()).intValue();
                if (!foundApkSigSchemeIds.contains(Integer.valueOf(iIntValue))) {
                    result.addError(ApkVerifier.Issue.V2_SIG_MISSING_APK_SIG_REFERENCED, Integer.valueOf(result.index), supportedApkSigSchemeNames.get(Integer.valueOf(iIntValue)));
                }
            }
        } catch (ApkSigningBlockUtils.NoSupportedSignaturesException e5) {
            result.addError(ApkVerifier.Issue.V2_SIG_NO_SUPPORTED_SIGNATURES, e5);
        }
    }
}
