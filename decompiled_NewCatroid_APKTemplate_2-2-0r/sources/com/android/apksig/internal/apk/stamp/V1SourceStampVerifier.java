package com.android.apksig.internal.apk.stamp;

import com.android.apksig.ApkVerifier;
import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.android.apksig.internal.apk.ContentDigestAlgorithm;
import com.android.apksig.internal.util.Pair;
import com.android.apksig.util.DataSource;
import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class V1SourceStampVerifier {
    private V1SourceStampVerifier() {
    }

    public static ApkSigningBlockUtils.Result verify(DataSource apk, ApkUtils.ZipSections zipSections, byte[] sourceStampCertificateDigest, Map<ContentDigestAlgorithm, byte[]> apkContentDigests, int minSdkVersion, int maxSdkVersion) throws ApkSigningBlockUtils.SignatureNotFoundException, NoSuchAlgorithmException, IOException {
        ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(0);
        verify(ApkSigningBlockUtils.findSignature(apk, zipSections, 722016414, result).signatureBlock, sourceStampCertificateDigest, apkContentDigests, minSdkVersion, maxSdkVersion, result);
        return result;
    }

    private static void verify(ByteBuffer sourceStampBlock, byte[] sourceStampCertificateDigest, Map<ContentDigestAlgorithm, byte[]> apkContentDigests, int minSdkVersion, int maxSdkVersion, ApkSigningBlockUtils.Result result) throws NoSuchAlgorithmException {
        ApkSigningBlockUtils.Result.SignerInfo signerInfo = new ApkSigningBlockUtils.Result.SignerInfo();
        result.signers.add(signerInfo);
        try {
            SourceStampVerifier.verifyV1SourceStamp(ApkSigningBlockUtils.getLengthPrefixedSlice(sourceStampBlock), CertificateFactory.getInstance("X.509"), signerInfo, ApkSigningBlockUtils.encodeAsSequenceOfLengthPrefixedPairsOfIntAndLengthPrefixedBytes(getApkDigests(apkContentDigests)), sourceStampCertificateDigest, minSdkVersion, maxSdkVersion);
            result.verified = (result.containsErrors() || result.containsWarnings()) ? false : true;
        } catch (ApkFormatException | BufferUnderflowException unused) {
            signerInfo.addWarning(ApkVerifier.Issue.SOURCE_STAMP_MALFORMED_SIGNATURE, new Object[0]);
        } catch (CertificateException e) {
            throw new IllegalStateException("Failed to obtain X.509 CertificateFactory", e);
        }
    }

    private static List<Pair<Integer, byte[]>> getApkDigests(Map<ContentDigestAlgorithm, byte[]> apkContentDigests) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<ContentDigestAlgorithm, byte[]> entry : apkContentDigests.entrySet()) {
            arrayList.add(Pair.of(Integer.valueOf(entry.getKey().getId()), entry.getValue()));
        }
        Collections.sort(arrayList, Comparator.comparing(new V1SourceStampSigner$$ExternalSyntheticLambda0()));
        return arrayList;
    }
}
