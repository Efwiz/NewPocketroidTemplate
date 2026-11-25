package com.android.apksig;

import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.apk.ApkSigResult;
import com.android.apksig.internal.apk.ApkSignerInfo;
import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.android.apksig.internal.apk.ContentDigestAlgorithm;
import com.android.apksig.internal.apk.SignatureAlgorithm;
import com.android.apksig.internal.apk.SignatureInfo;
import com.android.apksig.internal.apk.SignatureNotFoundException;
import com.android.apksig.internal.apk.stamp.V2SourceStampVerifier;
import com.android.apksig.internal.apk.v1.V1SchemeVerifier;
import com.android.apksig.internal.apk.v2.V2SchemeVerifier;
import com.android.apksig.internal.apk.v3.V3SchemeVerifier;
import com.android.apksig.internal.apk.v4.V4SchemeVerifier;
import com.android.apksig.internal.zip.CentralDirectoryRecord;
import com.android.apksig.internal.zip.LocalFileRecord;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.DataSources;
import com.android.apksig.util.RunnablesExecutor;
import com.android.apksig.zip.ZipFormatException;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.function.ToIntFunction;

/* JADX INFO: loaded from: classes.dex */
public class ApkVerifier {
    private static final Map<Integer, String> SUPPORTED_APK_SIG_SCHEME_NAMES = loadSupportedApkSigSchemeNames();
    private final DataSource mApkDataSource;
    private final File mApkFile;
    private final int mMaxSdkVersion;
    private final Integer mMinSdkVersion;
    private final File mV4SignatureFile;

    private static int getMinimumSignatureSchemeVersionForTargetSdk(int targetSdkVersion) {
        return targetSdkVersion >= 30 ? 2 : 1;
    }

    private static Map<Integer, String> loadSupportedApkSigSchemeNames() {
        HashMap map = new HashMap(2);
        map.put(2, "APK Signature Scheme v2");
        map.put(3, "APK Signature Scheme v3");
        return map;
    }

    private ApkVerifier(File apkFile, DataSource apkDataSource, File v4SignatureFile, Integer minSdkVersion, int maxSdkVersion) {
        this.mApkFile = apkFile;
        this.mApkDataSource = apkDataSource;
        this.mV4SignatureFile = v4SignatureFile;
        this.mMinSdkVersion = minSdkVersion;
        this.mMaxSdkVersion = maxSdkVersion;
    }

    public Result verify() throws Throwable {
        RandomAccessFile randomAccessFile = null;
        try {
            DataSource dataSourceAsDataSource = this.mApkDataSource;
            if (dataSourceAsDataSource == null) {
                if (this.mApkFile != null) {
                    RandomAccessFile randomAccessFile2 = new RandomAccessFile(this.mApkFile, "r");
                    try {
                        dataSourceAsDataSource = DataSources.asDataSource(randomAccessFile2, 0L, randomAccessFile2.length());
                        randomAccessFile = randomAccessFile2;
                    } catch (Throwable th) {
                        th = th;
                        randomAccessFile = randomAccessFile2;
                        if (randomAccessFile != null) {
                            randomAccessFile.close();
                        }
                        throw th;
                    }
                } else {
                    throw new IllegalStateException("APK not provided");
                }
            }
            Result resultVerify = verify(dataSourceAsDataSource);
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            return resultVerify;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private Result verify(DataSource apk) throws NoSuchAlgorithmException, IOException, ApkFormatException {
        int i;
        Map<Integer, String> map;
        int i2;
        ByteBuffer androidManifestFromApk;
        List<CentralDirectoryRecord> list;
        int i3;
        int i4;
        int targetSdkVersionFromBinaryAndroidManifest;
        int minimumSignatureSchemeVersionForTargetSdk;
        X509Certificate x509Certificate;
        CentralDirectoryRecord next;
        int iOrElse;
        int i5 = this.mMaxSdkVersion;
        try {
            ApkUtils.ZipSections zipSectionsFindZipSections = ApkUtils.findZipSections(apk);
            int iVerifyAndGetMinSdkVersion = verifyAndGetMinSdkVersion(apk, zipSectionsFindZipSections);
            Result result = new Result();
            HashMap map2 = new HashMap();
            Map<Integer, String> supportedSchemeNames = getSupportedSchemeNames(i5);
            HashSet hashSet = new HashSet(2);
            if (i5 >= 24) {
                RunnablesExecutor runnablesExecutor = RunnablesExecutor.SINGLE_THREADED;
                if (i5 >= 33) {
                    try {
                        ApkSigningBlockUtils.Result resultVerify = new V3SchemeVerifier.Builder(apk, zipSectionsFindZipSections, Math.max(iVerifyAndGetMinSdkVersion, 33), i5).setRunnablesExecutor(runnablesExecutor).setBlockId(462663009).build().verify();
                        hashSet.add(31);
                        iOrElse = resultVerify.signers.stream().mapToInt(new ToIntFunction() { // from class: com.android.apksig.ApkVerifier$$ExternalSyntheticLambda0
                            @Override // java.util.function.ToIntFunction
                            public final int applyAsInt(Object obj) {
                                return ((ApkSigningBlockUtils.Result.SignerInfo) obj).minSdkVersion;
                            }
                        }).min().orElse(0);
                        try {
                            result.mergeFrom(resultVerify);
                            map2.put(31, getApkContentDigestsFromSigningSchemeResult(resultVerify));
                        } catch (ApkSigningBlockUtils.SignatureNotFoundException unused) {
                        }
                    } catch (ApkSigningBlockUtils.SignatureNotFoundException unused2) {
                        iOrElse = 0;
                    }
                    if (result.containsErrors()) {
                        return result;
                    }
                } else {
                    iOrElse = 0;
                }
                if (iVerifyAndGetMinSdkVersion < 33 || hashSet.isEmpty()) {
                    try {
                        V3SchemeVerifier.Builder blockId = new V3SchemeVerifier.Builder(apk, zipSectionsFindZipSections, Math.max(iVerifyAndGetMinSdkVersion, 28), i5).setRunnablesExecutor(runnablesExecutor).setBlockId(-262969152);
                        if (iOrElse > 0) {
                            blockId.setRotationMinSdkVersion(iOrElse);
                        }
                        ApkSigningBlockUtils.Result resultVerify2 = blockId.build().verify();
                        try {
                            hashSet.add(3);
                            result.mergeFrom(resultVerify2);
                            map2.put(3, getApkContentDigestsFromSigningSchemeResult(resultVerify2));
                        } catch (ApkSigningBlockUtils.SignatureNotFoundException unused3) {
                            if (hashSet.contains(31)) {
                                result.addError(Issue.V31_BLOCK_FOUND_WITHOUT_V3_BLOCK, new Object[0]);
                            }
                        }
                    } catch (ApkSigningBlockUtils.SignatureNotFoundException unused4) {
                    }
                    if (result.containsErrors()) {
                        return result;
                    }
                }
                if (iVerifyAndGetMinSdkVersion < 28 || hashSet.isEmpty()) {
                    try {
                        map = supportedSchemeNames;
                        i2 = 24;
                        i = 2;
                        try {
                            ApkSigningBlockUtils.Result resultVerify3 = V2SchemeVerifier.verify(runnablesExecutor, apk, zipSectionsFindZipSections, supportedSchemeNames, hashSet, Math.max(iVerifyAndGetMinSdkVersion, 24), i5);
                            hashSet.add(2);
                            result.mergeFrom(resultVerify3);
                            map2.put(2, getApkContentDigestsFromSigningSchemeResult(resultVerify3));
                        } catch (ApkSigningBlockUtils.SignatureNotFoundException unused5) {
                        }
                    } catch (ApkSigningBlockUtils.SignatureNotFoundException unused6) {
                        map = supportedSchemeNames;
                        i = 2;
                        i2 = 24;
                    }
                    if (result.containsErrors()) {
                        return result;
                    }
                } else {
                    map = supportedSchemeNames;
                    i2 = 24;
                    i = 2;
                }
                File file = this.mV4SignatureFile;
                if (file != null) {
                    ApkSigningBlockUtils.Result resultVerify4 = V4SchemeVerifier.verify(apk, file);
                    hashSet.add(4);
                    result.mergeFrom(resultVerify4);
                    if (result.containsErrors()) {
                        return result;
                    }
                }
            } else {
                i = 2;
                map = supportedSchemeNames;
                i2 = 24;
            }
            if (i5 >= 26) {
                ByteBuffer androidManifestFromApk2 = getAndroidManifestFromApk(apk, zipSectionsFindZipSections);
                int targetSandboxVersionFromBinaryAndroidManifest = ApkUtils.getTargetSandboxVersionFromBinaryAndroidManifest(androidManifestFromApk2.slice());
                if (targetSandboxVersionFromBinaryAndroidManifest > 1 && hashSet.isEmpty()) {
                    result.addError(Issue.NO_SIG_FOR_TARGET_SANDBOX_VERSION, Integer.valueOf(targetSandboxVersionFromBinaryAndroidManifest));
                }
                androidManifestFromApk = androidManifestFromApk2;
            } else {
                androidManifestFromApk = null;
            }
            List<CentralDirectoryRecord> zipCentralDirectory = V1SchemeVerifier.parseZipCentralDirectory(apk, zipSectionsFindZipSections);
            if (iVerifyAndGetMinSdkVersion < i2 || hashSet.isEmpty()) {
                list = zipCentralDirectory;
                i3 = 1;
                result.mergeFrom(V1SchemeVerifier.verify(apk, zipSectionsFindZipSections, map, hashSet, iVerifyAndGetMinSdkVersion, i5));
                map2.put(1, getApkContentDigestFromV1SigningScheme(list, apk, zipSectionsFindZipSections));
            } else {
                list = zipCentralDirectory;
                i3 = 1;
            }
            if (result.containsErrors()) {
                return result;
            }
            try {
                Iterator<CentralDirectoryRecord> it = list.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!"stamp-cert-sha256".equals(next.getName()));
                if (next != null) {
                    result.mergeFrom(V2SourceStampVerifier.verify(apk, zipSectionsFindZipSections, LocalFileRecord.getUncompressedData(apk, next, zipSectionsFindZipSections.getZipCentralDirectoryOffset()), map2, Math.max(iVerifyAndGetMinSdkVersion, 30), i5));
                }
                i4 = 0;
            } catch (SignatureNotFoundException unused7) {
                i4 = 0;
                result.addWarning(Issue.SOURCE_STAMP_SIG_MISSING, new Object[0]);
            } catch (ZipFormatException e) {
                throw new ApkFormatException("Failed to read APK", e);
            }
            if (result.containsErrors()) {
                return result;
            }
            if (result.isVerifiedUsingV1Scheme() && result.isVerifiedUsingV2Scheme()) {
                ArrayList<Result.V1SchemeSignerInfo> arrayList = new ArrayList(result.getV1SchemeSigners());
                ArrayList<Result.V2SchemeSignerInfo> arrayList2 = new ArrayList(result.getV2SchemeSigners());
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                for (Result.V1SchemeSignerInfo v1SchemeSignerInfo : arrayList) {
                    try {
                        arrayList3.add(new ByteArray(v1SchemeSignerInfo.getCertificate().getEncoded()));
                    } catch (CertificateEncodingException e2) {
                        throw new IllegalStateException("Failed to encode JAR signer " + v1SchemeSignerInfo.getName() + " certs", e2);
                    }
                }
                for (Result.V2SchemeSignerInfo v2SchemeSignerInfo : arrayList2) {
                    try {
                        arrayList4.add(new ByteArray(v2SchemeSignerInfo.getCertificate().getEncoded()));
                    } catch (CertificateEncodingException e3) {
                        throw new IllegalStateException("Failed to encode APK Signature Scheme v2 signer (index: " + v2SchemeSignerInfo.getIndex() + ") certs", e3);
                    }
                }
                for (int i6 = i4; i6 < arrayList3.size(); i6++) {
                    if (!arrayList4.contains((ByteArray) arrayList3.get(i6))) {
                        ((Result.V1SchemeSignerInfo) arrayList.get(i6)).addError(Issue.V2_SIG_MISSING, new Object[i4]);
                        break;
                    }
                }
                for (int i7 = i4; i7 < arrayList4.size(); i7++) {
                    if (!arrayList3.contains((ByteArray) arrayList4.get(i7))) {
                        ((Result.V2SchemeSignerInfo) arrayList2.get(i7)).addError(Issue.JAR_SIG_MISSING, new Object[i4]);
                        break;
                    }
                }
            }
            if (result.isVerifiedUsingV3Scheme() && (result.isVerifiedUsingV1Scheme() || result.isVerifiedUsingV2Scheme())) {
                SigningCertificateLineage signingCertificateLineage = result.getSigningCertificateLineage();
                if (result.isVerifiedUsingV1Scheme()) {
                    List<Result.V1SchemeSignerInfo> v1SchemeSigners = result.getV1SchemeSigners();
                    if (v1SchemeSigners.size() != i3) {
                        result.addError(Issue.V3_SIG_MULTIPLE_PAST_SIGNERS, new Object[i4]);
                    }
                    x509Certificate = (X509Certificate) v1SchemeSigners.get(i4).mCertChain.get(i4);
                } else {
                    List<Result.V2SchemeSignerInfo> v2SchemeSigners = result.getV2SchemeSigners();
                    if (v2SchemeSigners.size() != i3) {
                        result.addError(Issue.V3_SIG_MULTIPLE_PAST_SIGNERS, new Object[i4]);
                    }
                    x509Certificate = (X509Certificate) v2SchemeSigners.get(i4).mCerts.get(i4);
                }
                if (signingCertificateLineage == null) {
                    List<Result.V3SchemeSignerInfo> v3SchemeSigners = result.getV3SchemeSigners();
                    if (v3SchemeSigners.size() != i3) {
                        result.addError(Issue.V3_SIG_MULTIPLE_SIGNERS, new Object[i4]);
                    }
                    try {
                        if (!Arrays.equals(x509Certificate.getEncoded(), ((X509Certificate) v3SchemeSigners.get(i4).mCerts.get(i4)).getEncoded())) {
                            result.addError(Issue.V3_SIG_PAST_SIGNERS_MISMATCH, new Object[i4]);
                        }
                    } catch (CertificateEncodingException e4) {
                        throw new RuntimeException("Failed to encode APK Signature Scheme v3 signer cert", e4);
                    }
                } else {
                    try {
                        if (signingCertificateLineage.getSubLineage(x509Certificate).size() != i3) {
                            result.addError(Issue.V3_SIG_PAST_SIGNERS_MISMATCH, new Object[i4]);
                        }
                    } catch (IllegalArgumentException unused8) {
                        result.addError(Issue.V3_SIG_PAST_SIGNERS_MISMATCH, new Object[i4]);
                    }
                }
            }
            if (result.isVerifiedUsingV4Scheme()) {
                List<Result.V4SchemeSignerInfo> v4SchemeSigners = result.getV4SchemeSigners();
                List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> contentDigests = v4SchemeSigners.get(i4).getContentDigests();
                if (contentDigests.size() != i3) {
                    result.addError(Issue.V4_SIG_V2_V3_DIGESTS_MISMATCH, new Object[i4]);
                }
                byte[] value = contentDigests.get(i4).getValue();
                if (result.isVerifiedUsingV3Scheme()) {
                    if (v4SchemeSigners.size() != (result.isVerifiedUsingV31Scheme() ? i : i3)) {
                        result.addError(Issue.V4_SIG_MULTIPLE_SIGNERS, new Object[i4]);
                    }
                    checkV4Signer(result.getV3SchemeSigners(), v4SchemeSigners.get(i4).mCerts, value, result);
                    if (result.isVerifiedUsingV31Scheme()) {
                        checkV4Signer(result.getV31SchemeSigners(), v4SchemeSigners.get(i3).mCerts, value, result);
                    }
                } else if (result.isVerifiedUsingV2Scheme()) {
                    if (v4SchemeSigners.size() != i3) {
                        result.addError(Issue.V4_SIG_MULTIPLE_SIGNERS, new Object[i4]);
                    }
                    List<Result.V2SchemeSignerInfo> v2SchemeSigners2 = result.getV2SchemeSigners();
                    if (v2SchemeSigners2.size() != i3) {
                        result.addError(Issue.V4_SIG_MULTIPLE_SIGNERS, new Object[i4]);
                    }
                    checkV4Certificate(v4SchemeSigners.get(i4).mCerts, v2SchemeSigners2.get(i4).mCerts, result);
                    if (!Arrays.equals(value, pickBestDigestForV4(v2SchemeSigners2.get(i4).getContentDigests()))) {
                        result.addError(Issue.V4_SIG_V2_V3_DIGESTS_MISMATCH, new Object[i4]);
                    }
                } else {
                    throw new RuntimeException("V4 signature must be also verified with V2/V3");
                }
            }
            if (androidManifestFromApk == null) {
                try {
                    androidManifestFromApk = getAndroidManifestFromApk(apk, zipSectionsFindZipSections);
                } catch (ApkFormatException unused9) {
                }
            }
            if (androidManifestFromApk != null && (minimumSignatureSchemeVersionForTargetSdk = getMinimumSignatureSchemeVersionForTargetSdk((targetSdkVersionFromBinaryAndroidManifest = ApkUtils.getTargetSdkVersionFromBinaryAndroidManifest(androidManifestFromApk.slice())))) > i3 && i5 >= targetSdkVersionFromBinaryAndroidManifest && (minimumSignatureSchemeVersionForTargetSdk == i ? !result.isVerifiedUsingV2Scheme() : minimumSignatureSchemeVersionForTargetSdk == 3) && !result.isVerifiedUsingV3Scheme() && !result.isVerifiedUsingV31Scheme()) {
                result.addError(Issue.MIN_SIG_SCHEME_FOR_TARGET_SDK_NOT_MET, Integer.valueOf(targetSdkVersionFromBinaryAndroidManifest), Integer.valueOf(minimumSignatureSchemeVersionForTargetSdk));
            }
            if (result.containsErrors()) {
                return result;
            }
            result.setVerified();
            if (result.isVerifiedUsingV31Scheme()) {
                List<Result.V3SchemeSignerInfo> v31SchemeSigners = result.getV31SchemeSigners();
                result.addSignerCertificate(v31SchemeSigners.get(v31SchemeSigners.size() - i3).getCertificate());
            } else if (result.isVerifiedUsingV3Scheme()) {
                List<Result.V3SchemeSignerInfo> v3SchemeSigners2 = result.getV3SchemeSigners();
                result.addSignerCertificate(v3SchemeSigners2.get(v3SchemeSigners2.size() - i3).getCertificate());
            } else if (result.isVerifiedUsingV2Scheme()) {
                Iterator<Result.V2SchemeSignerInfo> it2 = result.getV2SchemeSigners().iterator();
                while (it2.hasNext()) {
                    result.addSignerCertificate(it2.next().getCertificate());
                }
            } else if (result.isVerifiedUsingV1Scheme()) {
                Iterator<Result.V1SchemeSignerInfo> it3 = result.getV1SchemeSigners().iterator();
                while (it3.hasNext()) {
                    result.addSignerCertificate(it3.next().getCertificate());
                }
            } else {
                throw new RuntimeException("APK verified, but has not verified using any of v1, v2 or v3 schemes");
            }
            return result;
        } catch (ZipFormatException e5) {
            throw new ApkFormatException("Malformed APK: not a ZIP archive", e5);
        }
    }

    private int verifyAndGetMinSdkVersion(DataSource apk, ApkUtils.ZipSections zipSections) throws IOException, ApkFormatException {
        Integer num = this.mMinSdkVersion;
        if (num != null) {
            if (num.intValue() < 0) {
                throw new IllegalArgumentException("minSdkVersion must not be negative: " + this.mMinSdkVersion);
            }
            Integer num2 = this.mMinSdkVersion;
            if (num2 != null && num2.intValue() > this.mMaxSdkVersion) {
                throw new IllegalArgumentException("minSdkVersion (" + this.mMinSdkVersion + ") > maxSdkVersion (" + this.mMaxSdkVersion + Constant.AFTER_QUTO);
            }
            return this.mMinSdkVersion.intValue();
        }
        int minSdkVersionFromBinaryAndroidManifest = ApkUtils.getMinSdkVersionFromBinaryAndroidManifest(getAndroidManifestFromApk(apk, zipSections).slice());
        if (minSdkVersionFromBinaryAndroidManifest <= this.mMaxSdkVersion) {
            return minSdkVersionFromBinaryAndroidManifest;
        }
        throw new IllegalArgumentException("minSdkVersion from APK (" + minSdkVersionFromBinaryAndroidManifest + ") > maxSdkVersion (" + this.mMaxSdkVersion + Constant.AFTER_QUTO);
    }

    private static Map<Integer, String> getSupportedSchemeNames(int maxSdkVersion) {
        if (maxSdkVersion >= 28) {
            return SUPPORTED_APK_SIG_SCHEME_NAMES;
        }
        if (maxSdkVersion >= 24) {
            HashMap map = new HashMap(1);
            map.put(2, SUPPORTED_APK_SIG_SCHEME_NAMES.get(2));
            return map;
        }
        return Collections.emptyMap();
    }

    public Result verifySourceStamp() {
        return verifySourceStamp(null);
    }

    public Result verifySourceStamp(String expectedCertDigest) throws Throwable {
        RandomAccessFile randomAccessFile = null;
        try {
            try {
                DataSource dataSourceAsDataSource = this.mApkDataSource;
                if (dataSourceAsDataSource == null) {
                    if (this.mApkFile != null) {
                        RandomAccessFile randomAccessFile2 = new RandomAccessFile(this.mApkFile, "r");
                        try {
                            dataSourceAsDataSource = DataSources.asDataSource(randomAccessFile2, 0L, randomAccessFile2.length());
                            randomAccessFile = randomAccessFile2;
                        } catch (IOException e) {
                            e = e;
                            randomAccessFile = randomAccessFile2;
                            Result resultCreateSourceStampResultWithError = createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, Issue.UNEXPECTED_EXCEPTION, e);
                            if (randomAccessFile != null) {
                                try {
                                    randomAccessFile.close();
                                } catch (IOException unused) {
                                }
                            }
                            return resultCreateSourceStampResultWithError;
                        } catch (Throwable th) {
                            th = th;
                            randomAccessFile = randomAccessFile2;
                            if (randomAccessFile != null) {
                                try {
                                    randomAccessFile.close();
                                } catch (IOException unused2) {
                                }
                            }
                            throw th;
                        }
                    } else {
                        throw new IllegalStateException("APK not provided");
                    }
                }
                Result resultVerifySourceStamp = verifySourceStamp(dataSourceAsDataSource, expectedCertDigest);
                if (randomAccessFile != null) {
                    try {
                        randomAccessFile.close();
                    } catch (IOException unused3) {
                    }
                }
                return resultVerifySourceStamp;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            e = e2;
        }
    }

    private Result verifySourceStamp(DataSource apk, String expectedCertDigest) {
        CentralDirectoryRecord next;
        Result result;
        HashSet hashSet;
        byte[] bArr;
        int i;
        try {
            ApkUtils.ZipSections zipSectionsFindZipSections = ApkUtils.findZipSections(apk);
            int iVerifyAndGetMinSdkVersion = verifyAndGetMinSdkVersion(apk, zipSectionsFindZipSections);
            List<CentralDirectoryRecord> zipCentralDirectory = V1SchemeVerifier.parseZipCentralDirectory(apk, zipSectionsFindZipSections);
            Iterator<CentralDirectoryRecord> it = zipCentralDirectory.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!"stamp-cert-sha256".equals(next.getName()));
            if (next == null) {
                try {
                    ApkSigningBlockUtils.findSignature(apk, zipSectionsFindZipSections, 1845461005, new ApkSigningBlockUtils.Result(0));
                    return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.STAMP_NOT_VERIFIED, Issue.SOURCE_STAMP_SIGNATURE_BLOCK_WITHOUT_CERT_DIGEST, new Object[0]);
                } catch (ApkSigningBlockUtils.SignatureNotFoundException unused) {
                    return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.STAMP_MISSING, Issue.SOURCE_STAMP_CERT_DIGEST_AND_SIG_BLOCK_MISSING, new Object[0]);
                }
            }
            byte[] uncompressedData = LocalFileRecord.getUncompressedData(apk, next, zipSectionsFindZipSections.getZipCentralDirectoryOffset());
            if (expectedCertDigest != null) {
                String hex = ApkSigningBlockUtils.toHex(uncompressedData);
                if (!expectedCertDigest.equalsIgnoreCase(hex)) {
                    return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.CERT_DIGEST_MISMATCH, Issue.SOURCE_STAMP_EXPECTED_DIGEST_MISMATCH, hex, expectedCertDigest);
                }
            }
            HashMap map = new HashMap();
            Map<Integer, String> supportedSchemeNames = getSupportedSchemeNames(this.mMaxSdkVersion);
            HashSet hashSet2 = new HashSet(2);
            Result result2 = new Result();
            if (this.mMaxSdkVersion >= 28) {
                result = result2;
                hashSet = hashSet2;
                ApkSigningBlockUtils.Result apkContentDigests = getApkContentDigests(apk, zipSectionsFindZipSections, hashSet2, supportedSchemeNames, map, 3, Math.max(iVerifyAndGetMinSdkVersion, 28));
                if (apkContentDigests != null && apkContentDigests.containsErrors()) {
                    result.mergeFrom(apkContentDigests);
                    return mergeSourceStampResult(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, result);
                }
            } else {
                result = result2;
                hashSet = hashSet2;
            }
            if (this.mMaxSdkVersion < 24 || (iVerifyAndGetMinSdkVersion >= 28 && !hashSet.isEmpty())) {
                bArr = uncompressedData;
                i = 24;
            } else {
                bArr = uncompressedData;
                i = 24;
                ApkSigningBlockUtils.Result apkContentDigests2 = getApkContentDigests(apk, zipSectionsFindZipSections, hashSet, supportedSchemeNames, map, 2, Math.max(iVerifyAndGetMinSdkVersion, 24));
                if (apkContentDigests2 != null && apkContentDigests2.containsErrors()) {
                    result.mergeFrom(apkContentDigests2);
                    return mergeSourceStampResult(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, result);
                }
            }
            if (iVerifyAndGetMinSdkVersion < i || hashSet.isEmpty()) {
                map.put(1, getApkContentDigestFromV1SigningScheme(zipCentralDirectory, apk, zipSectionsFindZipSections));
            }
            ApkSigResult apkSigResultVerify = V2SourceStampVerifier.verify(apk, zipSectionsFindZipSections, bArr, map, iVerifyAndGetMinSdkVersion, this.mMaxSdkVersion);
            result.mergeFrom(apkSigResultVerify);
            if (!apkSigResultVerify.verified) {
                result.setWarningsAsErrors(true);
            } else {
                result.setVerified();
            }
            return result;
        } catch (ApkFormatException e) {
            e = e;
            return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, Issue.MALFORMED_APK, e);
        } catch (SignatureNotFoundException unused2) {
            return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.STAMP_NOT_VERIFIED, Issue.SOURCE_STAMP_SIG_MISSING, new Object[0]);
        } catch (ZipFormatException e2) {
            e = e2;
            return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, Issue.MALFORMED_APK, e);
        } catch (IOException e3) {
            e = e3;
            return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, Issue.MALFORMED_APK, e);
        } catch (NoSuchAlgorithmException e4) {
            return createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus.VERIFICATION_ERROR, Issue.UNEXPECTED_EXCEPTION, e4);
        }
    }

    private static Result createSourceStampResultWithError(Result.SourceStampInfo.SourceStampVerificationStatus verificationStatus, Issue issue, Object... params) {
        Result result = new Result();
        result.addError(issue, params);
        return mergeSourceStampResult(verificationStatus, result);
    }

    private static Result mergeSourceStampResult(Result.SourceStampInfo.SourceStampVerificationStatus verificationStatus, Result result) {
        result.mSourceStampInfo = new Result.SourceStampInfo(verificationStatus);
        return result;
    }

    private ApkSigningBlockUtils.Result getApkContentDigests(DataSource apk, ApkUtils.ZipSections zipSections, Set<Integer> foundApkSigSchemeIds, Map<Integer, String> supportedSchemeNames, Map<Integer, Map<ContentDigestAlgorithm, byte[]>> sigSchemeApkContentDigests, int apkSigSchemeVersion, int minSdkVersion) throws NoSuchAlgorithmException, IOException {
        if (apkSigSchemeVersion != 2 && apkSigSchemeVersion != 3) {
            return null;
        }
        ApkSigningBlockUtils.Result result = new ApkSigningBlockUtils.Result(apkSigSchemeVersion);
        try {
            SignatureInfo signatureInfoFindSignature = ApkSigningBlockUtils.findSignature(apk, zipSections, apkSigSchemeVersion == 3 ? -262969152 : 1896449818, result);
            foundApkSigSchemeIds.add(Integer.valueOf(apkSigSchemeVersion));
            HashSet hashSet = new HashSet(1);
            if (apkSigSchemeVersion == 2) {
                V2SchemeVerifier.parseSigners(signatureInfoFindSignature.signatureBlock, hashSet, supportedSchemeNames, foundApkSigSchemeIds, minSdkVersion, this.mMaxSdkVersion, result);
            } else {
                V3SchemeVerifier.parseSigners(signatureInfoFindSignature.signatureBlock, hashSet, result);
            }
            EnumMap enumMap = new EnumMap(ContentDigestAlgorithm.class);
            Iterator<ApkSigningBlockUtils.Result.SignerInfo> it = result.signers.iterator();
            while (it.hasNext()) {
                for (ApkSigningBlockUtils.Result.SignerInfo.ContentDigest contentDigest : it.next().contentDigests) {
                    SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(contentDigest.getSignatureAlgorithmId());
                    if (signatureAlgorithmFindById != null) {
                        enumMap.put(signatureAlgorithmFindById.getContentDigestAlgorithm(), contentDigest.getValue());
                    }
                }
            }
            sigSchemeApkContentDigests.put(Integer.valueOf(apkSigSchemeVersion), enumMap);
            return result;
        } catch (ApkSigningBlockUtils.SignatureNotFoundException unused) {
            return null;
        }
    }

    private static void checkV4Signer(List<Result.V3SchemeSignerInfo> v3Signers, List<X509Certificate> v4Certs, byte[] digestFromV4, Result result) {
        if (v3Signers.size() != 1) {
            result.addError(Issue.V4_SIG_MULTIPLE_SIGNERS, new Object[0]);
        }
        checkV4Certificate(v4Certs, v3Signers.get(0).mCerts, result);
        if (Arrays.equals(digestFromV4, pickBestDigestForV4(v3Signers.get(0).getContentDigests()))) {
            return;
        }
        result.addError(Issue.V4_SIG_V2_V3_DIGESTS_MISMATCH, new Object[0]);
    }

    private static void checkV4Certificate(List<X509Certificate> v4Certs, List<X509Certificate> v2v3Certs, Result result) {
        try {
            if (Arrays.equals(v2v3Certs.get(0).getEncoded(), v4Certs.get(0).getEncoded())) {
                return;
            }
            result.addError(Issue.V4_SIG_V2_V3_SIGNERS_MISMATCH, new Object[0]);
        } catch (CertificateEncodingException e) {
            throw new RuntimeException("Failed to encode APK signer cert", e);
        }
    }

    private static byte[] pickBestDigestForV4(List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> contentDigests) {
        HashMap map = new HashMap();
        collectApkContentDigests(contentDigests, map);
        return ApkSigningBlockUtils.pickBestDigestForV4(map);
    }

    private static Map<ContentDigestAlgorithm, byte[]> getApkContentDigestsFromSigningSchemeResult(ApkSigningBlockUtils.Result apkSigningSchemeResult) {
        HashMap map = new HashMap();
        Iterator<ApkSigningBlockUtils.Result.SignerInfo> it = apkSigningSchemeResult.signers.iterator();
        while (it.hasNext()) {
            collectApkContentDigests(it.next().contentDigests, map);
        }
        return map;
    }

    private static Map<ContentDigestAlgorithm, byte[]> getApkContentDigestFromV1SigningScheme(List<CentralDirectoryRecord> cdRecords, DataSource apk, ApkUtils.ZipSections zipSections) throws IOException, ApkFormatException {
        CentralDirectoryRecord next;
        EnumMap enumMap = new EnumMap(ContentDigestAlgorithm.class);
        Iterator<CentralDirectoryRecord> it = cdRecords.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!"META-INF/MANIFEST.MF".equals(next.getName()));
        if (next == null) {
            return enumMap;
        }
        try {
            enumMap.put(ContentDigestAlgorithm.SHA256, ApkUtils.computeSha256DigestBytes(LocalFileRecord.getUncompressedData(apk, next, zipSections.getZipCentralDirectoryOffset())));
            return enumMap;
        } catch (ZipFormatException e) {
            throw new ApkFormatException("Failed to read APK", e);
        }
    }

    private static void collectApkContentDigests(List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> contentDigests, Map<ContentDigestAlgorithm, byte[]> apkContentDigests) {
        for (ApkSigningBlockUtils.Result.SignerInfo.ContentDigest contentDigest : contentDigests) {
            SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(contentDigest.getSignatureAlgorithmId());
            if (signatureAlgorithmFindById != null) {
                apkContentDigests.put(signatureAlgorithmFindById.getContentDigestAlgorithm(), contentDigest.getValue());
            }
        }
    }

    private static ByteBuffer getAndroidManifestFromApk(DataSource apk, ApkUtils.ZipSections zipSections) throws IOException, ApkFormatException {
        try {
            return ApkSigner.getAndroidManifestFromApk(V1SchemeVerifier.parseZipCentralDirectory(apk, zipSections), apk.slice(0L, zipSections.getZipCentralDirectoryOffset()));
        } catch (ZipFormatException e) {
            throw new ApkFormatException("Failed to read AndroidManifest.xml", e);
        }
    }

    public static class Result {
        private SigningCertificateLineage mSigningCertificateLineage;
        private SourceStampInfo mSourceStampInfo;
        private boolean mSourceStampVerified;
        private boolean mVerified;
        private boolean mVerifiedUsingV1Scheme;
        private boolean mVerifiedUsingV2Scheme;
        private boolean mVerifiedUsingV31Scheme;
        private boolean mVerifiedUsingV3Scheme;
        private boolean mVerifiedUsingV4Scheme;
        private boolean mWarningsAsErrors;
        private final List<IssueWithParams> mErrors = new ArrayList();
        private final List<IssueWithParams> mWarnings = new ArrayList();
        private final List<X509Certificate> mSignerCerts = new ArrayList();
        private final List<V1SchemeSignerInfo> mV1SchemeSigners = new ArrayList();
        private final List<V1SchemeSignerInfo> mV1SchemeIgnoredSigners = new ArrayList();
        private final List<V2SchemeSignerInfo> mV2SchemeSigners = new ArrayList();
        private final List<V3SchemeSignerInfo> mV3SchemeSigners = new ArrayList();
        private final List<V3SchemeSignerInfo> mV31SchemeSigners = new ArrayList();
        private final List<V4SchemeSignerInfo> mV4SchemeSigners = new ArrayList();

        public boolean isVerified() {
            return this.mVerified;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setVerified() {
            this.mVerified = true;
        }

        public boolean isVerifiedUsingV1Scheme() {
            return this.mVerifiedUsingV1Scheme;
        }

        public boolean isVerifiedUsingV2Scheme() {
            return this.mVerifiedUsingV2Scheme;
        }

        public boolean isVerifiedUsingV3Scheme() {
            return this.mVerifiedUsingV3Scheme;
        }

        public boolean isVerifiedUsingV31Scheme() {
            return this.mVerifiedUsingV31Scheme;
        }

        public boolean isVerifiedUsingV4Scheme() {
            return this.mVerifiedUsingV4Scheme;
        }

        public boolean isSourceStampVerified() {
            return this.mSourceStampVerified;
        }

        public List<X509Certificate> getSignerCertificates() {
            return this.mSignerCerts;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSignerCertificate(X509Certificate cert) {
            this.mSignerCerts.add(cert);
        }

        public List<V1SchemeSignerInfo> getV1SchemeSigners() {
            return this.mV1SchemeSigners;
        }

        public List<V1SchemeSignerInfo> getV1SchemeIgnoredSigners() {
            return this.mV1SchemeIgnoredSigners;
        }

        public List<V2SchemeSignerInfo> getV2SchemeSigners() {
            return this.mV2SchemeSigners;
        }

        public List<V3SchemeSignerInfo> getV3SchemeSigners() {
            return this.mV3SchemeSigners;
        }

        public List<V3SchemeSignerInfo> getV31SchemeSigners() {
            return this.mV31SchemeSigners;
        }

        public List<V4SchemeSignerInfo> getV4SchemeSigners() {
            return this.mV4SchemeSigners;
        }

        public SourceStampInfo getSourceStampInfo() {
            return this.mSourceStampInfo;
        }

        public SigningCertificateLineage getSigningCertificateLineage() {
            return this.mSigningCertificateLineage;
        }

        void addError(Issue msg, Object... parameters) {
            this.mErrors.add(new IssueWithParams(msg, parameters));
        }

        void addWarning(Issue msg, Object... parameters) {
            this.mWarnings.add(new IssueWithParams(msg, parameters));
        }

        void setWarningsAsErrors(boolean value) {
            this.mWarningsAsErrors = value;
        }

        public List<IssueWithParams> getErrors() {
            if (!this.mWarningsAsErrors) {
                return this.mErrors;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mErrors);
            arrayList.addAll(this.mWarnings);
            return arrayList;
        }

        public List<IssueWithParams> getWarnings() {
            return this.mWarnings;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeFrom(V1SchemeVerifier.Result source) {
            this.mVerifiedUsingV1Scheme = source.verified;
            this.mErrors.addAll(source.getErrors());
            this.mWarnings.addAll(source.getWarnings());
            Iterator<V1SchemeVerifier.Result.SignerInfo> it = source.signers.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                this.mV1SchemeSigners.add(new V1SchemeSignerInfo(it.next()));
            }
            Iterator<V1SchemeVerifier.Result.SignerInfo> it2 = source.ignoredSigners.iterator();
            while (it2.hasNext()) {
                this.mV1SchemeIgnoredSigners.add(new V1SchemeSignerInfo(it2.next()));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeFrom(ApkSigResult source) {
            if (source.signatureSchemeVersion == 0) {
                this.mSourceStampVerified = source.verified;
                if (source.mSigners.isEmpty()) {
                    return;
                }
                this.mSourceStampInfo = new SourceStampInfo(source.mSigners.get(0));
                return;
            }
            throw new IllegalArgumentException("Unknown ApkSigResult Signing Block Scheme Id " + source.signatureSchemeVersion);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeFrom(ApkSigningBlockUtils.Result source) {
            int i = source.signatureSchemeVersion;
            if (i == 0) {
                this.mSourceStampVerified = source.verified;
                if (source.signers.isEmpty()) {
                    return;
                }
                this.mSourceStampInfo = new SourceStampInfo(source.signers.get(0));
                return;
            }
            if (i == 31) {
                this.mVerifiedUsingV31Scheme = source.verified;
                Iterator<ApkSigningBlockUtils.Result.SignerInfo> it = source.signers.iterator();
                while (it.hasNext()) {
                    this.mV31SchemeSigners.add(new V3SchemeSignerInfo(it.next()));
                }
                this.mSigningCertificateLineage = source.signingCertificateLineage;
                return;
            }
            if (i == 2) {
                this.mVerifiedUsingV2Scheme = source.verified;
                Iterator<ApkSigningBlockUtils.Result.SignerInfo> it2 = source.signers.iterator();
                while (it2.hasNext()) {
                    this.mV2SchemeSigners.add(new V2SchemeSignerInfo(it2.next()));
                }
                return;
            }
            if (i != 3) {
                if (i == 4) {
                    this.mVerifiedUsingV4Scheme = source.verified;
                    Iterator<ApkSigningBlockUtils.Result.SignerInfo> it3 = source.signers.iterator();
                    while (it3.hasNext()) {
                        this.mV4SchemeSigners.add(new V4SchemeSignerInfo(it3.next()));
                    }
                    return;
                }
                throw new IllegalArgumentException("Unknown Signing Block Scheme Id");
            }
            this.mVerifiedUsingV3Scheme = source.verified;
            Iterator<ApkSigningBlockUtils.Result.SignerInfo> it4 = source.signers.iterator();
            while (it4.hasNext()) {
                this.mV3SchemeSigners.add(new V3SchemeSignerInfo(it4.next()));
            }
            if (this.mSigningCertificateLineage == null) {
                this.mSigningCertificateLineage = source.signingCertificateLineage;
            }
        }

        public boolean containsErrors() {
            if (!this.mErrors.isEmpty()) {
                return true;
            }
            if (this.mWarningsAsErrors && !this.mWarnings.isEmpty()) {
                return true;
            }
            if (!this.mV1SchemeSigners.isEmpty()) {
                for (V1SchemeSignerInfo v1SchemeSignerInfo : this.mV1SchemeSigners) {
                    if (v1SchemeSignerInfo.containsErrors()) {
                        return true;
                    }
                    if (this.mWarningsAsErrors && !v1SchemeSignerInfo.getWarnings().isEmpty()) {
                        return true;
                    }
                }
            }
            if (!this.mV2SchemeSigners.isEmpty()) {
                for (V2SchemeSignerInfo v2SchemeSignerInfo : this.mV2SchemeSigners) {
                    if (v2SchemeSignerInfo.containsErrors()) {
                        return true;
                    }
                    if (this.mWarningsAsErrors && !v2SchemeSignerInfo.getWarnings().isEmpty()) {
                        return true;
                    }
                }
            }
            if (!this.mV3SchemeSigners.isEmpty()) {
                for (V3SchemeSignerInfo v3SchemeSignerInfo : this.mV3SchemeSigners) {
                    if (v3SchemeSignerInfo.containsErrors()) {
                        return true;
                    }
                    if (this.mWarningsAsErrors && !v3SchemeSignerInfo.getWarnings().isEmpty()) {
                        return true;
                    }
                }
            }
            SourceStampInfo sourceStampInfo = this.mSourceStampInfo;
            if (sourceStampInfo == null) {
                return false;
            }
            if (sourceStampInfo.containsErrors()) {
                return true;
            }
            return this.mWarningsAsErrors && !this.mSourceStampInfo.getWarnings().isEmpty();
        }

        public List<IssueWithParams> getAllErrors() {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mErrors);
            if (this.mWarningsAsErrors) {
                arrayList.addAll(this.mWarnings);
            }
            if (!this.mV1SchemeSigners.isEmpty()) {
                for (V1SchemeSignerInfo v1SchemeSignerInfo : this.mV1SchemeSigners) {
                    arrayList.addAll(v1SchemeSignerInfo.mErrors);
                    if (this.mWarningsAsErrors) {
                        arrayList.addAll(v1SchemeSignerInfo.getWarnings());
                    }
                }
            }
            if (!this.mV2SchemeSigners.isEmpty()) {
                for (V2SchemeSignerInfo v2SchemeSignerInfo : this.mV2SchemeSigners) {
                    arrayList.addAll(v2SchemeSignerInfo.mErrors);
                    if (this.mWarningsAsErrors) {
                        arrayList.addAll(v2SchemeSignerInfo.getWarnings());
                    }
                }
            }
            if (!this.mV3SchemeSigners.isEmpty()) {
                for (V3SchemeSignerInfo v3SchemeSignerInfo : this.mV3SchemeSigners) {
                    arrayList.addAll(v3SchemeSignerInfo.mErrors);
                    if (this.mWarningsAsErrors) {
                        arrayList.addAll(v3SchemeSignerInfo.getWarnings());
                    }
                }
            }
            SourceStampInfo sourceStampInfo = this.mSourceStampInfo;
            if (sourceStampInfo != null) {
                arrayList.addAll(sourceStampInfo.getErrors());
                if (this.mWarningsAsErrors) {
                    arrayList.addAll(this.mSourceStampInfo.getWarnings());
                }
            }
            return arrayList;
        }

        public static class V1SchemeSignerInfo {
            private final List<X509Certificate> mCertChain;
            private final List<IssueWithParams> mErrors;
            private final String mName;
            private final String mSignatureBlockFileName;
            private final String mSignatureFileName;
            private final List<IssueWithParams> mWarnings;

            private V1SchemeSignerInfo(V1SchemeVerifier.Result.SignerInfo result) {
                this.mName = result.f64name;
                this.mCertChain = result.certChain;
                this.mSignatureBlockFileName = result.signatureBlockFileName;
                this.mSignatureFileName = result.signatureFileName;
                this.mErrors = result.getErrors();
                this.mWarnings = result.getWarnings();
            }

            public String getName() {
                return this.mName;
            }

            public String getSignatureBlockFileName() {
                return this.mSignatureBlockFileName;
            }

            public String getSignatureFileName() {
                return this.mSignatureFileName;
            }

            public X509Certificate getCertificate() {
                if (this.mCertChain.isEmpty()) {
                    return null;
                }
                return this.mCertChain.get(0);
            }

            public List<X509Certificate> getCertificateChain() {
                return this.mCertChain;
            }

            public boolean containsErrors() {
                return !this.mErrors.isEmpty();
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors;
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void addError(Issue msg, Object... parameters) {
                this.mErrors.add(new IssueWithParams(msg, parameters));
            }
        }

        public static class V2SchemeSignerInfo {
            private final List<X509Certificate> mCerts;
            private final List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> mContentDigests;
            private final List<IssueWithParams> mErrors;
            private final int mIndex;
            private final List<IssueWithParams> mWarnings;

            private V2SchemeSignerInfo(ApkSigningBlockUtils.Result.SignerInfo result) {
                this.mIndex = result.index;
                this.mCerts = result.certs;
                this.mErrors = result.getErrors();
                this.mWarnings = result.getWarnings();
                this.mContentDigests = result.contentDigests;
            }

            public int getIndex() {
                return this.mIndex;
            }

            public X509Certificate getCertificate() {
                if (this.mCerts.isEmpty()) {
                    return null;
                }
                return this.mCerts.get(0);
            }

            public List<X509Certificate> getCertificates() {
                return this.mCerts;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void addError(Issue msg, Object... parameters) {
                this.mErrors.add(new IssueWithParams(msg, parameters));
            }

            public boolean containsErrors() {
                return !this.mErrors.isEmpty();
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors;
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings;
            }

            public List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> getContentDigests() {
                return this.mContentDigests;
            }
        }

        public static class V3SchemeSignerInfo {
            private final List<X509Certificate> mCerts;
            private final List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> mContentDigests;
            private final List<IssueWithParams> mErrors;
            private final int mIndex;
            private final int mMaxSdkVersion;
            private final int mMinSdkVersion;
            private final boolean mRotationTargetsDevRelease;
            private final List<IssueWithParams> mWarnings;

            static /* synthetic */ boolean lambda$new$1(int i) {
                return i == -1029262406;
            }

            private V3SchemeSignerInfo(ApkSigningBlockUtils.Result.SignerInfo result) {
                this.mIndex = result.index;
                this.mCerts = result.certs;
                this.mErrors = result.getErrors();
                this.mWarnings = result.getWarnings();
                this.mContentDigests = result.contentDigests;
                this.mMinSdkVersion = result.minSdkVersion;
                this.mMaxSdkVersion = result.maxSdkVersion;
                this.mRotationTargetsDevRelease = result.additionalAttributes.stream().mapToInt(new ToIntFunction() { // from class: com.android.apksig.ApkVerifier$Result$V3SchemeSignerInfo$$ExternalSyntheticLambda0
                    @Override // java.util.function.ToIntFunction
                    public final int applyAsInt(Object obj) {
                        return ((ApkSigningBlockUtils.Result.SignerInfo.AdditionalAttribute) obj).getId();
                    }
                }).anyMatch(new IntPredicate() { // from class: com.android.apksig.ApkVerifier$Result$V3SchemeSignerInfo$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntPredicate
                    public final boolean test(int i) {
                        return ApkVerifier.Result.V3SchemeSignerInfo.lambda$new$1(i);
                    }
                });
            }

            public int getIndex() {
                return this.mIndex;
            }

            public X509Certificate getCertificate() {
                if (this.mCerts.isEmpty()) {
                    return null;
                }
                return this.mCerts.get(0);
            }

            public List<X509Certificate> getCertificates() {
                return this.mCerts;
            }

            public boolean containsErrors() {
                return !this.mErrors.isEmpty();
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors;
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings;
            }

            public List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> getContentDigests() {
                return this.mContentDigests;
            }

            public int getMinSdkVersion() {
                return this.mMinSdkVersion;
            }

            public int getMaxSdkVersion() {
                return this.mMaxSdkVersion;
            }

            public boolean getRotationTargetsDevRelease() {
                return this.mRotationTargetsDevRelease;
            }
        }

        public static class V4SchemeSignerInfo {
            private final List<X509Certificate> mCerts;
            private final List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> mContentDigests;
            private final List<IssueWithParams> mErrors;
            private final int mIndex;
            private final List<IssueWithParams> mWarnings;

            private V4SchemeSignerInfo(ApkSigningBlockUtils.Result.SignerInfo result) {
                this.mIndex = result.index;
                this.mCerts = result.certs;
                this.mErrors = result.getErrors();
                this.mWarnings = result.getWarnings();
                this.mContentDigests = result.contentDigests;
            }

            public int getIndex() {
                return this.mIndex;
            }

            public X509Certificate getCertificate() {
                if (this.mCerts.isEmpty()) {
                    return null;
                }
                return this.mCerts.get(0);
            }

            public List<X509Certificate> getCertificates() {
                return this.mCerts;
            }

            public boolean containsErrors() {
                return !this.mErrors.isEmpty();
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors;
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings;
            }

            public List<ApkSigningBlockUtils.Result.SignerInfo.ContentDigest> getContentDigests() {
                return this.mContentDigests;
            }
        }

        public static class SourceStampInfo {
            private final List<X509Certificate> mCertificateLineage;
            private final List<X509Certificate> mCertificates;
            private final List<IssueWithParams> mErrors;
            private final List<IssueWithParams> mInfoMessages;
            private final SourceStampVerificationStatus mSourceStampVerificationStatus;
            private final long mTimestamp;
            private final List<IssueWithParams> mWarnings;

            public enum SourceStampVerificationStatus {
                STAMP_VERIFIED,
                STAMP_VERIFICATION_FAILED,
                CERT_DIGEST_MISMATCH,
                STAMP_MISSING,
                STAMP_NOT_VERIFIED,
                VERIFICATION_ERROR
            }

            private SourceStampInfo(ApkSignerInfo result) {
                this.mCertificates = result.certs;
                this.mCertificateLineage = result.certificateLineage;
                List<IssueWithParams> issuesFromVerificationIssues = ApkVerificationIssueAdapter.getIssuesFromVerificationIssues(result.getErrors());
                this.mErrors = issuesFromVerificationIssues;
                List<IssueWithParams> issuesFromVerificationIssues2 = ApkVerificationIssueAdapter.getIssuesFromVerificationIssues(result.getWarnings());
                this.mWarnings = issuesFromVerificationIssues2;
                this.mInfoMessages = ApkVerificationIssueAdapter.getIssuesFromVerificationIssues(result.getInfoMessages());
                if (issuesFromVerificationIssues.isEmpty() && issuesFromVerificationIssues2.isEmpty()) {
                    this.mSourceStampVerificationStatus = SourceStampVerificationStatus.STAMP_VERIFIED;
                } else {
                    this.mSourceStampVerificationStatus = SourceStampVerificationStatus.STAMP_VERIFICATION_FAILED;
                }
                this.mTimestamp = result.timestamp;
            }

            SourceStampInfo(SourceStampVerificationStatus sourceStampVerificationStatus) {
                this.mCertificates = Collections.emptyList();
                this.mCertificateLineage = Collections.emptyList();
                this.mErrors = Collections.emptyList();
                this.mWarnings = Collections.emptyList();
                this.mInfoMessages = Collections.emptyList();
                this.mSourceStampVerificationStatus = sourceStampVerificationStatus;
                this.mTimestamp = 0L;
            }

            public X509Certificate getCertificate() {
                if (this.mCertificates.isEmpty()) {
                    return null;
                }
                return this.mCertificates.get(0);
            }

            public List<X509Certificate> getCertificatesInLineage() {
                return this.mCertificateLineage;
            }

            public boolean containsErrors() {
                return !this.mErrors.isEmpty();
            }

            public boolean containsInfoMessages() {
                return !this.mInfoMessages.isEmpty();
            }

            public List<IssueWithParams> getErrors() {
                return this.mErrors;
            }

            public List<IssueWithParams> getWarnings() {
                return this.mWarnings;
            }

            public List<IssueWithParams> getInfoMessages() {
                return this.mInfoMessages;
            }

            public SourceStampVerificationStatus getSourceStampVerificationStatus() {
                return this.mSourceStampVerificationStatus;
            }

            public long getTimestampEpochSeconds() {
                return this.mTimestamp;
            }
        }
    }

    public enum Issue {
        JAR_SIG_NO_SIGNATURES("No JAR signatures"),
        JAR_SIG_NO_SIGNED_ZIP_ENTRIES("No JAR entries covered by JAR signatures"),
        JAR_SIG_DUPLICATE_ZIP_ENTRY("Duplicate entry: %1$s"),
        JAR_SIG_DUPLICATE_MANIFEST_SECTION("Duplicate section in META-INF/MANIFEST.MF: %1$s"),
        JAR_SIG_UNNNAMED_MANIFEST_SECTION("Malformed META-INF/MANIFEST.MF: invidual section #%1$d does not have a name"),
        JAR_SIG_UNNNAMED_SIG_FILE_SECTION("Malformed %1$s: invidual section #%2$d does not have a name"),
        JAR_SIG_NO_MANIFEST("Missing META-INF/MANIFEST.MF"),
        JAR_SIG_MISSING_ZIP_ENTRY_REFERENCED_IN_MANIFEST("%1$s entry referenced by META-INF/MANIFEST.MF not found in the APK"),
        JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_MANIFEST("No digest for %1$s in META-INF/MANIFEST.MF"),
        JAR_SIG_NO_ZIP_ENTRY_DIGEST_IN_SIG_FILE("No digest for %1$s in %2$s"),
        JAR_SIG_ZIP_ENTRY_NOT_SIGNED("%1$s entry not signed"),
        JAR_SIG_ZIP_ENTRY_SIGNERS_MISMATCH("Entries %1$s and %3$s are signed with different sets of signers : <%2$s> vs <%4$s>"),
        JAR_SIG_ZIP_ENTRY_DIGEST_DID_NOT_VERIFY("%2$s digest of %1$s does not match the digest specified in %3$s. Expected: <%5$s>, actual: <%4$s>"),
        JAR_SIG_MANIFEST_MAIN_SECTION_DIGEST_DID_NOT_VERIFY("%1$s digest of META-INF/MANIFEST.MF main section does not match the digest specified in %2$s. Expected: <%4$s>, actual: <%3$s>"),
        JAR_SIG_MANIFEST_SECTION_DIGEST_DID_NOT_VERIFY("%2$s digest of META-INF/MANIFEST.MF section for %1$s does not match the digest specified in %3$s. Expected: <%5$s>, actual: <%4$s>"),
        JAR_SIG_NO_MANIFEST_DIGEST_IN_SIG_FILE("%1$s does not specify digest of META-INF/MANIFEST.MF. This slows down verification."),
        JAR_SIG_NO_APK_SIG_STRIP_PROTECTION("APK is signed using APK Signature Scheme v2 but these signatures may be stripped without being detected because %1$s does not contain anti-stripping protections."),
        JAR_SIG_MISSING_FILE("Partial JAR signature. Found: %1$s, missing: %2$s"),
        JAR_SIG_VERIFY_EXCEPTION("Failed to verify JAR signature %1$s against %2$s: %3$s"),
        JAR_SIG_UNSUPPORTED_SIG_ALG("JAR signature %1$s uses digest algorithm %5$s and signature algorithm %6$s which is not supported on API Level(s) %4$s for which this APK is being verified"),
        JAR_SIG_PARSE_EXCEPTION("Failed to parse JAR signature %1$s: %2$s"),
        JAR_SIG_MALFORMED_CERTIFICATE("Malformed certificate in JAR signature %1$s: %2$s"),
        JAR_SIG_DID_NOT_VERIFY("JAR signature %1$s did not verify against %2$s"),
        JAR_SIG_NO_SIGNERS("JAR signature %1$s contains no signers"),
        JAR_SIG_DUPLICATE_SIG_FILE_SECTION("Duplicate section in %1$s: %2$s"),
        JAR_SIG_MISSING_VERSION_ATTR_IN_SIG_FILE("Malformed %1$s: missing Signature-Version attribute"),
        JAR_SIG_UNKNOWN_APK_SIG_SCHEME_ID("JAR signature %1$s references unknown APK signature scheme ID: %2$d"),
        JAR_SIG_MISSING_APK_SIG_REFERENCED("JAR signature %1$s indicates the APK is signed using %3$s but no such signature was found. Signature stripped?"),
        JAR_SIG_UNPROTECTED_ZIP_ENTRY("%1$s not protected by signature. Unauthorized modifications to this JAR entry will not be detected. Delete or move the entry outside of META-INF/."),
        JAR_SIG_MISSING("No JAR signature from this signer"),
        NO_SIG_FOR_TARGET_SANDBOX_VERSION("Missing APK Signature Scheme v2 signature required for target sandbox version %1$d"),
        MIN_SIG_SCHEME_FOR_TARGET_SDK_NOT_MET("Target SDK version %1$d requires a minimum of signature scheme v%2$d; the APK is not signed with this or a later signature scheme"),
        V2_SIG_MISSING("No APK Signature Scheme v2 signature from this signer"),
        V2_SIG_MALFORMED_SIGNERS("Malformed list of signers"),
        V2_SIG_MALFORMED_SIGNER("Malformed signer block"),
        V2_SIG_MALFORMED_PUBLIC_KEY("Malformed public key: %1$s"),
        V2_SIG_MALFORMED_CERTIFICATE("Malformed certificate #%2$d: %3$s"),
        V2_SIG_MALFORMED_SIGNATURE("Malformed APK Signature Scheme v2 signature record #%1$d"),
        V2_SIG_MALFORMED_DIGEST("Malformed APK Signature Scheme v2 digest record #%1$d"),
        V2_SIG_MALFORMED_ADDITIONAL_ATTRIBUTE("Malformed additional attribute #%1$d"),
        V2_SIG_UNKNOWN_APK_SIG_SCHEME_ID("APK Signature Scheme v2 signer: %1$s references unknown APK signature scheme ID: %2$d"),
        V2_SIG_MISSING_APK_SIG_REFERENCED("APK Signature Scheme v2 signature %1$s indicates the APK is signed using %2$s but no such signature was found. Signature stripped?"),
        V2_SIG_NO_SIGNERS("No signers in APK Signature Scheme v2 signature"),
        V2_SIG_UNKNOWN_SIG_ALGORITHM("Unknown signature algorithm: %1$#x"),
        V2_SIG_UNKNOWN_ADDITIONAL_ATTRIBUTE("Unknown additional attribute: ID %1$#x"),
        V2_SIG_VERIFY_EXCEPTION("Failed to verify %1$s signature: %2$s"),
        V2_SIG_DID_NOT_VERIFY("%1$s signature over signed-data did not verify"),
        V2_SIG_NO_SIGNATURES("No signatures"),
        V2_SIG_NO_SUPPORTED_SIGNATURES("No supported signatures: %1$s"),
        V2_SIG_NO_CERTIFICATES("No certificates"),
        V2_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD("Public key mismatch between certificate and signature record: <%1$s> vs <%2$s>"),
        V2_SIG_SIG_ALG_MISMATCH_BETWEEN_SIGNATURES_AND_DIGESTS_RECORDS("Signature algorithms mismatch between signatures and digests records: %1$s vs %2$s"),
        V2_SIG_APK_DIGEST_DID_NOT_VERIFY("APK integrity check failed. %1$s digest mismatch. Expected: <%2$s>, actual: <%3$s>"),
        V3_SIG_MALFORMED_SIGNERS("Malformed list of signers"),
        V3_SIG_MALFORMED_SIGNER("Malformed signer block"),
        V3_SIG_MALFORMED_PUBLIC_KEY("Malformed public key: %1$s"),
        V3_SIG_MALFORMED_CERTIFICATE("Malformed certificate #%2$d: %3$s"),
        V3_SIG_MALFORMED_SIGNATURE("Malformed APK Signature Scheme v3 signature record #%1$d"),
        V3_SIG_MALFORMED_DIGEST("Malformed APK Signature Scheme v3 digest record #%1$d"),
        V3_SIG_MALFORMED_ADDITIONAL_ATTRIBUTE("Malformed additional attribute #%1$d"),
        V3_SIG_NO_SIGNERS("No signers in APK Signature Scheme v3 signature"),
        V3_SIG_MULTIPLE_SIGNERS("Multiple APK Signature Scheme v3 signatures found for a single  platform version."),
        V3_SIG_MULTIPLE_PAST_SIGNERS("Multiple signatures found for pre-v3 signing with an APK  Signature Scheme v3 signer.  Only one allowed."),
        V3_SIG_PAST_SIGNERS_MISMATCH("v3 signer differs from v1/v2 signer without proper signing certificate lineage."),
        V3_SIG_UNKNOWN_SIG_ALGORITHM("Unknown signature algorithm: %1$#x"),
        V3_SIG_UNKNOWN_ADDITIONAL_ATTRIBUTE("Unknown additional attribute: ID %1$#x"),
        V3_SIG_VERIFY_EXCEPTION("Failed to verify %1$s signature: %2$s"),
        V3_SIG_INVALID_SDK_VERSIONS("Invalid SDK Version parameter(s) encountered in APK Signature scheme v3 signature: minSdkVersion %1$s maxSdkVersion: %2$s"),
        V3_SIG_DID_NOT_VERIFY("%1$s signature over signed-data did not verify"),
        V3_SIG_NO_SIGNATURES("No signatures"),
        V3_SIG_NO_SUPPORTED_SIGNATURES("No supported signatures"),
        V3_SIG_NO_CERTIFICATES("No certificates"),
        V3_MIN_SDK_VERSION_MISMATCH_BETWEEN_SIGNER_AND_SIGNED_DATA_RECORD("minSdkVersion mismatch between signed data and signature record: <%1$s> vs <%2$s>"),
        V3_MAX_SDK_VERSION_MISMATCH_BETWEEN_SIGNER_AND_SIGNED_DATA_RECORD("maxSdkVersion mismatch between signed data and signature record: <%1$s> vs <%2$s>"),
        V3_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD("Public key mismatch between certificate and signature record: <%1$s> vs <%2$s>"),
        V3_SIG_SIG_ALG_MISMATCH_BETWEEN_SIGNATURES_AND_DIGESTS_RECORDS("Signature algorithms mismatch between signatures and digests records: %1$s vs %2$s"),
        V3_SIG_APK_DIGEST_DID_NOT_VERIFY("APK integrity check failed. %1$s digest mismatch. Expected: <%2$s>, actual: <%3$s>"),
        V3_SIG_POR_DID_NOT_VERIFY("SigningCertificateLineage attribute containd a proof-of-rotation record with signature(s) that did not verify."),
        V3_SIG_MALFORMED_LINEAGE("Failed to parse the SigningCertificateLineage structure in the APK Signature Scheme v3 signature's additional attributes section."),
        V3_SIG_POR_CERT_MISMATCH("APK signing certificate differs from the associated certificate found in the signer's SigningCertificateLineage."),
        V3_INCONSISTENT_SDK_VERSIONS("APK Signature Scheme v3 signers supported min/max SDK versions are not continuous."),
        V3_MISSING_SDK_VERSIONS("APK Signature Scheme v3 signers supported min/max SDK versions do not cover the entire desired range.  Found min:  %1$s max %2$s"),
        V3_INCONSISTENT_LINEAGES("SigningCertificateLineages targeting different platform versions using APK Signature Scheme v3 are not all a part of the same overall lineage."),
        V31_BLOCK_MISSING("The v3 signer indicates key rotation should be supported starting from SDK version %1$s, but a v3.1 block was not found"),
        V31_ROTATION_MIN_SDK_MISMATCH("The v3 signer indicates key rotation should be supported starting from SDK version %1$s, but the v3.1 block targets %2$s for rotation"),
        V31_ROTATION_MIN_SDK_ATTR_MISSING("APK supports key rotation starting from SDK version %1$s, but the v3 signer does not contain the attribute to detect if this signature is stripped"),
        V31_BLOCK_FOUND_WITHOUT_V3_BLOCK("The APK contains a v3.1 signing block without a v3.0 base block"),
        V31_ROTATION_TARGETS_DEV_RELEASE_ATTR_ON_V3_SIGNER("The rotation-targets-dev-release attribute is only supported on v3.1 signers; this attribute will be ignored by the platform in a v3.0 signer"),
        APK_SIG_BLOCK_UNKNOWN_ENTRY_ID("APK Signing Block contains unknown entry: ID %1$#x"),
        V4_SIG_MALFORMED_SIGNERS("V4 signature has malformed signer block"),
        V4_SIG_UNKNOWN_SIG_ALGORITHM("V4 signature has unknown signing algorithm: %1$#x"),
        V4_SIG_NO_SIGNATURES("V4 signature has no signature found"),
        V4_SIG_NO_SUPPORTED_SIGNATURES("V4 signature has no supported signature"),
        V4_SIG_DID_NOT_VERIFY("%1$s signature over signed-data did not verify"),
        V4_SIG_VERIFY_EXCEPTION("Failed to verify %1$s signature: %2$s"),
        V4_SIG_MALFORMED_PUBLIC_KEY("Malformed public key: %1$s"),
        V4_SIG_MALFORMED_CERTIFICATE("V4 signature has malformed certificate"),
        V4_SIG_NO_CERTIFICATE("V4 signature has no certificate"),
        V4_SIG_PUBLIC_KEY_MISMATCH_BETWEEN_CERTIFICATE_AND_SIGNATURES_RECORD("V4 signature has mismatched certificate and signature: <%1$s> vs <%2$s>"),
        V4_SIG_APK_ROOT_DID_NOT_VERIFY("V4 signature's hash tree root (content digest) did not verity"),
        V4_SIG_APK_TREE_DID_NOT_VERIFY("V4 signature's hash tree did not verity"),
        V4_SIG_MULTIPLE_SIGNERS("V4 signature only supports one signer"),
        V4_SIG_V2_V3_SIGNERS_MISMATCH("V4 signature and V2/V3 signature have mismatched certificates"),
        V4_SIG_V2_V3_DIGESTS_MISMATCH("V4 signature and V2/V3 signature have mismatched digests"),
        V4_SIG_VERSION_NOT_CURRENT("V4 signature format version %1$d is different from the tool's current version %2$d"),
        SOURCE_STAMP_CERT_DIGEST_AND_SIG_BLOCK_MISSING("Neither the source stamp certificate digest file nor the signature block are present in the APK"),
        SOURCE_STAMP_SIG_MISSING("No SourceStamp signature"),
        SOURCE_STAMP_MALFORMED_CERTIFICATE("Malformed certificate: %1$s"),
        SOURCE_STAMP_MALFORMED_SIGNATURE("Malformed SourceStamp signature"),
        SOURCE_STAMP_UNKNOWN_SIG_ALGORITHM("Unknown signature algorithm: %1$#x"),
        SOURCE_STAMP_VERIFY_EXCEPTION("Failed to verify %1$s signature: %2$s"),
        SOURCE_STAMP_DID_NOT_VERIFY("%1$s signature over signed-data did not verify"),
        SOURCE_STAMP_NO_SIGNATURE("No signature"),
        SOURCE_STAMP_NO_SUPPORTED_SIGNATURE("Signature(s) {%1$s} not supported: %2$s"),
        SOURCE_STAMP_CERTIFICATE_MISMATCH_BETWEEN_SIGNATURE_BLOCK_AND_APK("Certificate mismatch between SourceStamp block in APK signing block and SourceStamp file in APK: <%1$s> vs <%2$s>"),
        SOURCE_STAMP_SIGNATURE_BLOCK_WITHOUT_CERT_DIGEST("A source stamp signature block was found without a corresponding certificate digest in the APK"),
        SOURCE_STAMP_EXPECTED_DIGEST_MISMATCH("The source stamp certificate digest in the APK, %1$s, does not match the expected digest, %2$s"),
        SOURCE_STAMP_MALFORMED_ATTRIBUTE("Malformed stamp attribute #%1$d"),
        SOURCE_STAMP_UNKNOWN_ATTRIBUTE("Unknown stamp attribute: ID %1$#x"),
        SOURCE_STAMP_MALFORMED_LINEAGE("Failed to parse the SigningCertificateLineage structure in the source stamp attributes section."),
        SOURCE_STAMP_POR_CERT_MISMATCH("APK signing certificate differs from the associated certificate found in the signer's SigningCertificateLineage."),
        SOURCE_STAMP_POR_DID_NOT_VERIFY("Source stamp SigningCertificateLineage attribute contains a proof-of-rotation record with signature(s) that did not verify."),
        SOURCE_STAMP_INVALID_TIMESTAMP("The source stamp timestamp attribute has an invalid value: %1$d"),
        MALFORMED_APK("Malformed APK; the following exception was caught when attempting to parse the APK: %1$s"),
        UNEXPECTED_EXCEPTION("An unexpected exception was caught when verifying the signature: %1$s");

        private final String mFormat;

        Issue(String format) {
            this.mFormat = format;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getFormat() {
            return this.mFormat;
        }
    }

    public static class IssueWithParams extends ApkVerificationIssue {
        private final Issue mIssue;
        private final Object[] mParams;

        public IssueWithParams(Issue issue, Object[] params) {
            super(issue.mFormat, params);
            this.mIssue = issue;
            this.mParams = params;
        }

        public Issue getIssue() {
            return this.mIssue;
        }

        @Override // com.android.apksig.ApkVerificationIssue
        public Object[] getParams() {
            return (Object[]) this.mParams.clone();
        }

        @Override // com.android.apksig.ApkVerificationIssue
        public String toString() {
            return String.format(this.mIssue.getFormat(), this.mParams);
        }
    }

    private static class ByteArray {
        private final byte[] mArray;
        private final int mHashCode;

        private ByteArray(byte[] arr) {
            this.mArray = arr;
            this.mHashCode = Arrays.hashCode(arr);
        }

        public int hashCode() {
            return this.mHashCode;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ByteArray)) {
                return false;
            }
            ByteArray byteArray = (ByteArray) obj;
            return hashCode() == byteArray.hashCode() && Arrays.equals(this.mArray, byteArray.mArray);
        }
    }

    public static class Builder {
        private final DataSource mApkDataSource;
        private final File mApkFile;
        private int mMaxSdkVersion = Integer.MAX_VALUE;
        private Integer mMinSdkVersion;
        private File mV4SignatureFile;

        public Builder(File apk) {
            if (apk == null) {
                throw new NullPointerException("apk == null");
            }
            this.mApkFile = apk;
            this.mApkDataSource = null;
        }

        public Builder(DataSource apk) {
            if (apk == null) {
                throw new NullPointerException("apk == null");
            }
            this.mApkDataSource = apk;
            this.mApkFile = null;
        }

        public Builder setMinCheckedPlatformVersion(int minSdkVersion) {
            this.mMinSdkVersion = Integer.valueOf(minSdkVersion);
            return this;
        }

        public Builder setMaxCheckedPlatformVersion(int maxSdkVersion) {
            this.mMaxSdkVersion = maxSdkVersion;
            return this;
        }

        public Builder setV4SignatureFile(File v4SignatureFile) {
            this.mV4SignatureFile = v4SignatureFile;
            return this;
        }

        public ApkVerifier build() {
            return new ApkVerifier(this.mApkFile, this.mApkDataSource, this.mV4SignatureFile, this.mMinSdkVersion, this.mMaxSdkVersion);
        }
    }

    public static class ApkVerificationIssueAdapter {
        static final Map<Integer, Issue> sVerificationIssueIdToIssue;

        private ApkVerificationIssueAdapter() {
        }

        static {
            HashMap map = new HashMap();
            sVerificationIssueIdToIssue = map;
            map.put(1, Issue.V2_SIG_MALFORMED_SIGNERS);
            map.put(2, Issue.V2_SIG_NO_SIGNERS);
            map.put(3, Issue.V2_SIG_MALFORMED_SIGNER);
            map.put(4, Issue.V2_SIG_MALFORMED_SIGNATURE);
            map.put(5, Issue.V2_SIG_NO_SIGNATURES);
            map.put(6, Issue.V2_SIG_MALFORMED_CERTIFICATE);
            map.put(7, Issue.V2_SIG_NO_CERTIFICATES);
            map.put(8, Issue.V2_SIG_MALFORMED_DIGEST);
            map.put(9, Issue.V3_SIG_MALFORMED_SIGNERS);
            map.put(10, Issue.V3_SIG_NO_SIGNERS);
            map.put(11, Issue.V3_SIG_MALFORMED_SIGNER);
            map.put(12, Issue.V3_SIG_MALFORMED_SIGNATURE);
            map.put(13, Issue.V3_SIG_NO_SIGNATURES);
            map.put(14, Issue.V3_SIG_MALFORMED_CERTIFICATE);
            map.put(15, Issue.V3_SIG_NO_CERTIFICATES);
            map.put(16, Issue.V3_SIG_MALFORMED_DIGEST);
            map.put(17, Issue.SOURCE_STAMP_NO_SIGNATURE);
            map.put(18, Issue.SOURCE_STAMP_MALFORMED_CERTIFICATE);
            map.put(19, Issue.SOURCE_STAMP_UNKNOWN_SIG_ALGORITHM);
            map.put(20, Issue.SOURCE_STAMP_MALFORMED_SIGNATURE);
            map.put(21, Issue.SOURCE_STAMP_DID_NOT_VERIFY);
            map.put(22, Issue.SOURCE_STAMP_VERIFY_EXCEPTION);
            map.put(23, Issue.SOURCE_STAMP_EXPECTED_DIGEST_MISMATCH);
            map.put(24, Issue.SOURCE_STAMP_SIGNATURE_BLOCK_WITHOUT_CERT_DIGEST);
            map.put(25, Issue.SOURCE_STAMP_CERT_DIGEST_AND_SIG_BLOCK_MISSING);
            map.put(26, Issue.SOURCE_STAMP_NO_SUPPORTED_SIGNATURE);
            map.put(27, Issue.SOURCE_STAMP_CERTIFICATE_MISMATCH_BETWEEN_SIGNATURE_BLOCK_AND_APK);
            map.put(28, Issue.MALFORMED_APK);
            map.put(29, Issue.UNEXPECTED_EXCEPTION);
            map.put(30, Issue.SOURCE_STAMP_SIG_MISSING);
            map.put(31, Issue.SOURCE_STAMP_MALFORMED_ATTRIBUTE);
            map.put(32, Issue.SOURCE_STAMP_UNKNOWN_ATTRIBUTE);
            map.put(33, Issue.SOURCE_STAMP_MALFORMED_LINEAGE);
            map.put(34, Issue.SOURCE_STAMP_POR_CERT_MISMATCH);
            map.put(35, Issue.SOURCE_STAMP_POR_DID_NOT_VERIFY);
            map.put(36, Issue.JAR_SIG_NO_SIGNATURES);
            map.put(37, Issue.JAR_SIG_PARSE_EXCEPTION);
            map.put(38, Issue.SOURCE_STAMP_INVALID_TIMESTAMP);
        }

        public static List<IssueWithParams> getIssuesFromVerificationIssues(List<? extends ApkVerificationIssue> verificationIssues) {
            ArrayList arrayList = new ArrayList(verificationIssues.size());
            for (ApkVerificationIssue apkVerificationIssue : verificationIssues) {
                if (apkVerificationIssue instanceof IssueWithParams) {
                    arrayList.add((IssueWithParams) apkVerificationIssue);
                } else {
                    arrayList.add(new IssueWithParams(sVerificationIssueIdToIssue.get(Integer.valueOf(apkVerificationIssue.getIssueId())), apkVerificationIssue.getParams()));
                }
            }
            return arrayList;
        }
    }
}
