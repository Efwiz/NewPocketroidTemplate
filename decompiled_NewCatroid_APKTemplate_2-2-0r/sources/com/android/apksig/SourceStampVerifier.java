package com.android.apksig;

import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkUtilsLite;
import com.android.apksig.internal.apk.ApkSigResult;
import com.android.apksig.internal.apk.ApkSignerInfo;
import com.android.apksig.internal.apk.ApkSigningBlockUtilsLite;
import com.android.apksig.internal.apk.ContentDigestAlgorithm;
import com.android.apksig.internal.apk.SignatureAlgorithm;
import com.android.apksig.internal.apk.SignatureInfo;
import com.android.apksig.internal.apk.SignatureNotFoundException;
import com.android.apksig.internal.apk.stamp.V2SourceStampVerifier;
import com.android.apksig.internal.util.GuaranteedEncodedFormX509Certificate;
import com.android.apksig.internal.zip.CentralDirectoryRecord;
import com.android.apksig.internal.zip.LocalFileRecord;
import com.android.apksig.internal.zip.ZipUtils;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.DataSources;
import com.android.apksig.zip.ZipFormatException;
import com.android.apksig.zip.ZipSections;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SourceStampVerifier {
    private final DataSource mApkDataSource;
    private final File mApkFile;
    private final int mMaxSdkVersion;
    private final int mMinSdkVersion;

    private SourceStampVerifier(File apkFile, DataSource apkDataSource, int minSdkVersion, int maxSdkVersion) {
        this.mApkFile = apkFile;
        this.mApkDataSource = apkDataSource;
        this.mMinSdkVersion = minSdkVersion;
        this.mMaxSdkVersion = maxSdkVersion;
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
                            Result result = new Result();
                            result.addVerificationError(29, e);
                            if (randomAccessFile != null) {
                                try {
                                    randomAccessFile.close();
                                } catch (IOException unused) {
                                }
                            }
                            return result;
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
        SignatureInfo signatureInfoFindSignature;
        CentralDirectoryRecord next;
        SignatureInfo signatureInfoFindSignature2;
        Result result = new Result();
        try {
            try {
                ZipSections zipSectionsFindZipSections = ApkUtilsLite.findZipSections(apk);
                List<CentralDirectoryRecord> zipCentralDirectory = ZipUtils.parseZipCentralDirectory(apk, zipSectionsFindZipSections);
                Iterator<CentralDirectoryRecord> it = zipCentralDirectory.iterator();
                do {
                    signatureInfoFindSignature = null;
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!"stamp-cert-sha256".equals(next.getName()));
                int i = 24;
                if (next == null) {
                    try {
                        ApkSigningBlockUtilsLite.findSignature(apk, zipSectionsFindZipSections, 1845461005);
                    } catch (SignatureNotFoundException unused) {
                        i = 25;
                    }
                    result.addVerificationError(i, new Object[0]);
                    return result;
                }
                byte[] uncompressedData = LocalFileRecord.getUncompressedData(apk, next, zipSectionsFindZipSections.getZipCentralDirectoryOffset());
                if (expectedCertDigest != null) {
                    String hex = ApkSigningBlockUtilsLite.toHex(uncompressedData);
                    if (!expectedCertDigest.equalsIgnoreCase(hex)) {
                        result.addVerificationError(23, hex, expectedCertDigest);
                        return result;
                    }
                }
                HashMap map = new HashMap();
                if (this.mMaxSdkVersion >= 28) {
                    try {
                        signatureInfoFindSignature2 = ApkSigningBlockUtilsLite.findSignature(apk, zipSectionsFindZipSections, -262969152);
                    } catch (SignatureNotFoundException unused2) {
                        signatureInfoFindSignature2 = null;
                    }
                    if (signatureInfoFindSignature2 != null) {
                        EnumMap enumMap = new EnumMap(ContentDigestAlgorithm.class);
                        parseSigners(signatureInfoFindSignature2.signatureBlock, 3, enumMap, result);
                        map.put(3, enumMap);
                    }
                }
                if (this.mMaxSdkVersion >= 24 && (this.mMinSdkVersion < 28 || map.isEmpty())) {
                    try {
                        signatureInfoFindSignature = ApkSigningBlockUtilsLite.findSignature(apk, zipSectionsFindZipSections, 1896449818);
                    } catch (SignatureNotFoundException unused3) {
                    }
                    if (signatureInfoFindSignature != null) {
                        EnumMap enumMap2 = new EnumMap(ContentDigestAlgorithm.class);
                        parseSigners(signatureInfoFindSignature.signatureBlock, 2, enumMap2, result);
                        map.put(2, enumMap2);
                    }
                }
                if (this.mMinSdkVersion < 24 || map.isEmpty()) {
                    map.put(1, getApkContentDigestFromV1SigningScheme(zipCentralDirectory, apk, zipSectionsFindZipSections, result));
                }
                result.mergeFrom(V2SourceStampVerifier.verify(apk, zipSectionsFindZipSections, uncompressedData, map, this.mMinSdkVersion, this.mMaxSdkVersion));
                return result;
            } catch (SignatureNotFoundException unused4) {
                result.addVerificationError(30, new Object[0]);
                return result;
            }
        } catch (ApkFormatException e) {
            e = e;
            result.addVerificationError(28, e);
            return result;
        } catch (ZipFormatException e2) {
            e = e2;
            result.addVerificationError(28, e);
            return result;
        } catch (IOException e3) {
            e = e3;
            result.addVerificationError(28, e);
            return result;
        } catch (NoSuchAlgorithmException e4) {
            result.addVerificationError(29, e4);
            return result;
        }
    }

    public static void parseSigners(ByteBuffer apkSignatureSchemeBlock, int apkSigSchemeVersion, Map<ContentDigestAlgorithm, byte[]> apkContentDigests, Result result) {
        boolean z = apkSigSchemeVersion == 2;
        try {
            ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtilsLite.getLengthPrefixedSlice(apkSignatureSchemeBlock);
            if (!lengthPrefixedSlice.hasRemaining()) {
                result.addVerificationWarning(z ? 2 : 10, new Object[0]);
                return;
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                while (lengthPrefixedSlice.hasRemaining()) {
                    Result.SignerInfo signerInfo = new Result.SignerInfo();
                    if (z) {
                        result.addV2Signer(signerInfo);
                    } else {
                        result.addV3Signer(signerInfo);
                    }
                    try {
                        parseSigner(ApkSigningBlockUtilsLite.getLengthPrefixedSlice(lengthPrefixedSlice), apkSigSchemeVersion, certificateFactory, apkContentDigests, signerInfo);
                    } catch (ApkFormatException | BufferUnderflowException unused) {
                        signerInfo.addVerificationWarning(z ? 3 : 11, new Object[0]);
                        return;
                    }
                }
            } catch (CertificateException e) {
                throw new RuntimeException("Failed to obtain X.509 CertificateFactory", e);
            }
        } catch (ApkFormatException unused2) {
            result.addVerificationWarning(z ? 1 : 9, new Object[0]);
        }
    }

    private static void parseSigner(ByteBuffer signerBlock, int apkSigSchemeVersion, CertificateFactory certFactory, Map<ContentDigestAlgorithm, byte[]> apkContentDigests, Result.SignerInfo signerInfo) throws ApkFormatException {
        boolean z = apkSigSchemeVersion == 2;
        ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtilsLite.getLengthPrefixedSlice(signerBlock);
        ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtilsLite.getLengthPrefixedSlice(lengthPrefixedSlice);
        ByteBuffer lengthPrefixedSlice3 = ApkSigningBlockUtilsLite.getLengthPrefixedSlice(lengthPrefixedSlice);
        while (lengthPrefixedSlice2.hasRemaining()) {
            try {
                ByteBuffer lengthPrefixedSlice4 = ApkSigningBlockUtilsLite.getLengthPrefixedSlice(lengthPrefixedSlice2);
                int i = lengthPrefixedSlice4.getInt();
                byte[] lengthPrefixedByteArray = ApkSigningBlockUtilsLite.readLengthPrefixedByteArray(lengthPrefixedSlice4);
                SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(i);
                if (signatureAlgorithmFindById != null) {
                    apkContentDigests.put(signatureAlgorithmFindById.getContentDigestAlgorithm(), lengthPrefixedByteArray);
                }
            } catch (ApkFormatException | BufferUnderflowException unused) {
                signerInfo.addVerificationWarning(z ? 8 : 16, new Object[0]);
                return;
            }
        }
        if (lengthPrefixedSlice3.hasRemaining()) {
            byte[] lengthPrefixedByteArray2 = ApkSigningBlockUtilsLite.readLengthPrefixedByteArray(lengthPrefixedSlice3);
            try {
                signerInfo.setSigningCertificate(new GuaranteedEncodedFormX509Certificate((X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(lengthPrefixedByteArray2)), lengthPrefixedByteArray2));
            } catch (CertificateException unused2) {
                signerInfo.addVerificationWarning(z ? 6 : 14, new Object[0]);
                return;
            }
        }
        if (signerInfo.getSigningCertificate() == null) {
            signerInfo.addVerificationWarning(z ? 7 : 15, new Object[0]);
        }
    }

    private static Map<ContentDigestAlgorithm, byte[]> getApkContentDigestFromV1SigningScheme(List<CentralDirectoryRecord> cdRecords, DataSource apk, ZipSections zipSections, Result result) throws IOException, ApkFormatException {
        ArrayList<CentralDirectoryRecord> arrayList = new ArrayList(1);
        EnumMap enumMap = new EnumMap(ContentDigestAlgorithm.class);
        CentralDirectoryRecord centralDirectoryRecord = null;
        for (CentralDirectoryRecord centralDirectoryRecord2 : cdRecords) {
            String name2 = centralDirectoryRecord2.getName();
            if (name2 != null) {
                if (centralDirectoryRecord == null && "META-INF/MANIFEST.MF".equals(name2)) {
                    centralDirectoryRecord = centralDirectoryRecord2;
                } else if (name2.startsWith("META-INF/") && (name2.endsWith(".RSA") || name2.endsWith(".DSA") || name2.endsWith(".EC"))) {
                    arrayList.add(centralDirectoryRecord2);
                }
            }
        }
        if (centralDirectoryRecord == null) {
            return enumMap;
        }
        if (arrayList.isEmpty()) {
            result.addVerificationWarning(36, new Object[0]);
        } else {
            for (CentralDirectoryRecord centralDirectoryRecord3 : arrayList) {
                try {
                    for (Certificate certificate : CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(LocalFileRecord.getUncompressedData(apk, centralDirectoryRecord3, zipSections.getZipCentralDirectoryOffset())))) {
                        if (certificate instanceof X509Certificate) {
                            Result.SignerInfo signerInfo = new Result.SignerInfo();
                            signerInfo.setSigningCertificate((X509Certificate) certificate);
                            result.addV1Signer(signerInfo);
                            break;
                        }
                    }
                } catch (ZipFormatException e) {
                    throw new ApkFormatException("Failed to read APK", e);
                } catch (CertificateException e2) {
                    result.addVerificationWarning(37, centralDirectoryRecord3.getName(), e2);
                }
            }
        }
        try {
            enumMap.put(ContentDigestAlgorithm.SHA256, ApkUtilsLite.computeSha256DigestBytes(LocalFileRecord.getUncompressedData(apk, centralDirectoryRecord, zipSections.getZipCentralDirectoryOffset())));
            return enumMap;
        } catch (ZipFormatException e3) {
            throw new ApkFormatException("Failed to read APK", e3);
        }
    }

    public static class Result {
        private final List<List<SignerInfo>> mAllSchemeSigners;
        private final List<ApkVerificationIssue> mErrors;
        private SourceStampInfo mSourceStampInfo;
        private final List<SignerInfo> mV1SchemeSigners;
        private final List<SignerInfo> mV2SchemeSigners;
        private final List<SignerInfo> mV3SchemeSigners;
        private boolean mVerified;
        private final List<ApkVerificationIssue> mWarnings;

        public Result() {
            ArrayList arrayList = new ArrayList();
            this.mV1SchemeSigners = arrayList;
            ArrayList arrayList2 = new ArrayList();
            this.mV2SchemeSigners = arrayList2;
            ArrayList arrayList3 = new ArrayList();
            this.mV3SchemeSigners = arrayList3;
            this.mAllSchemeSigners = Arrays.asList(arrayList, arrayList2, arrayList3);
            this.mErrors = new ArrayList();
            this.mWarnings = new ArrayList();
        }

        void addVerificationError(int errorId, Object... params) {
            this.mErrors.add(new ApkVerificationIssue(errorId, params));
        }

        void addVerificationWarning(int warningId, Object... params) {
            this.mWarnings.add(new ApkVerificationIssue(warningId, params));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addV1Signer(SignerInfo signerInfo) {
            this.mV1SchemeSigners.add(signerInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addV2Signer(SignerInfo signerInfo) {
            this.mV2SchemeSigners.add(signerInfo);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addV3Signer(SignerInfo signerInfo) {
            this.mV3SchemeSigners.add(signerInfo);
        }

        public boolean isVerified() {
            return this.mVerified;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void mergeFrom(ApkSigResult source) {
            if (source.signatureSchemeVersion == 0) {
                this.mVerified = source.verified;
                if (source.mSigners.isEmpty()) {
                    return;
                }
                this.mSourceStampInfo = new SourceStampInfo(source.mSigners.get(0));
                return;
            }
            throw new IllegalArgumentException("Unknown ApkSigResult Signing Block Scheme Id " + source.signatureSchemeVersion);
        }

        public List<SignerInfo> getV1SchemeSigners() {
            return this.mV1SchemeSigners;
        }

        public List<SignerInfo> getV2SchemeSigners() {
            return this.mV2SchemeSigners;
        }

        public List<SignerInfo> getV3SchemeSigners() {
            return this.mV3SchemeSigners;
        }

        public SourceStampInfo getSourceStampInfo() {
            return this.mSourceStampInfo;
        }

        public boolean containsErrors() {
            if (!this.mErrors.isEmpty()) {
                return true;
            }
            Iterator<List<SignerInfo>> it = this.mAllSchemeSigners.iterator();
            while (it.hasNext()) {
                Iterator<SignerInfo> it2 = it.next().iterator();
                while (it2.hasNext()) {
                    if (it2.next().containsErrors()) {
                        return true;
                    }
                }
            }
            SourceStampInfo sourceStampInfo = this.mSourceStampInfo;
            return sourceStampInfo != null && sourceStampInfo.containsErrors();
        }

        public List<ApkVerificationIssue> getErrors() {
            return this.mErrors;
        }

        public List<ApkVerificationIssue> getWarnings() {
            return this.mWarnings;
        }

        public List<ApkVerificationIssue> getAllErrors() {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mErrors);
            Iterator<List<SignerInfo>> it = this.mAllSchemeSigners.iterator();
            while (it.hasNext()) {
                Iterator<SignerInfo> it2 = it.next().iterator();
                while (it2.hasNext()) {
                    arrayList.addAll(it2.next().getErrors());
                }
            }
            SourceStampInfo sourceStampInfo = this.mSourceStampInfo;
            if (sourceStampInfo != null) {
                arrayList.addAll(sourceStampInfo.getErrors());
            }
            return arrayList;
        }

        public List<ApkVerificationIssue> getAllWarnings() {
            ArrayList arrayList = new ArrayList();
            arrayList.addAll(this.mWarnings);
            Iterator<List<SignerInfo>> it = this.mAllSchemeSigners.iterator();
            while (it.hasNext()) {
                Iterator<SignerInfo> it2 = it.next().iterator();
                while (it2.hasNext()) {
                    arrayList.addAll(it2.next().getWarnings());
                }
            }
            SourceStampInfo sourceStampInfo = this.mSourceStampInfo;
            if (sourceStampInfo != null) {
                arrayList.addAll(sourceStampInfo.getWarnings());
            }
            return arrayList;
        }

        public static class SignerInfo {
            private X509Certificate mSigningCertificate;
            private final List<ApkVerificationIssue> mErrors = new ArrayList();
            private final List<ApkVerificationIssue> mWarnings = new ArrayList();

            void setSigningCertificate(X509Certificate signingCertificate) {
                this.mSigningCertificate = signingCertificate;
            }

            void addVerificationError(int errorId, Object... params) {
                this.mErrors.add(new ApkVerificationIssue(errorId, params));
            }

            void addVerificationWarning(int warningId, Object... params) {
                this.mWarnings.add(new ApkVerificationIssue(warningId, params));
            }

            public X509Certificate getSigningCertificate() {
                return this.mSigningCertificate;
            }

            public List<ApkVerificationIssue> getErrors() {
                return this.mErrors;
            }

            public List<ApkVerificationIssue> getWarnings() {
                return this.mWarnings;
            }

            public boolean containsErrors() {
                return !this.mErrors.isEmpty();
            }
        }

        public static class SourceStampInfo {
            private static final boolean mWarningsAsErrors = true;
            private final List<X509Certificate> mCertificateLineage;
            private final List<X509Certificate> mCertificates;
            private final List<ApkVerificationIssue> mErrors;
            private final List<ApkVerificationIssue> mInfoMessages;
            private final long mTimestamp;
            private final List<ApkVerificationIssue> mWarnings;

            private SourceStampInfo(ApkSignerInfo result) {
                ArrayList arrayList = new ArrayList();
                this.mErrors = arrayList;
                ArrayList arrayList2 = new ArrayList();
                this.mWarnings = arrayList2;
                ArrayList arrayList3 = new ArrayList();
                this.mInfoMessages = arrayList3;
                this.mCertificates = result.certs;
                this.mCertificateLineage = result.certificateLineage;
                arrayList.addAll(result.getErrors());
                arrayList2.addAll(result.getWarnings());
                arrayList3.addAll(result.getInfoMessages());
                this.mTimestamp = result.timestamp;
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
                return (this.mErrors.isEmpty() && this.mWarnings.isEmpty()) ? false : true;
            }

            public boolean containsInfoMessages() {
                return !this.mInfoMessages.isEmpty();
            }

            public List<ApkVerificationIssue> getErrors() {
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(this.mErrors);
                arrayList.addAll(this.mWarnings);
                return arrayList;
            }

            public List<ApkVerificationIssue> getWarnings() {
                return this.mWarnings;
            }

            public List<ApkVerificationIssue> getInfoMessages() {
                return this.mInfoMessages;
            }

            public long getTimestampEpochSeconds() {
                return this.mTimestamp;
            }
        }
    }

    public static class Builder {
        private final DataSource mApkDataSource;
        private final File mApkFile;
        private int mMinSdkVersion = 1;
        private int mMaxSdkVersion = Integer.MAX_VALUE;

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
            this.mMinSdkVersion = minSdkVersion;
            return this;
        }

        public Builder setMaxCheckedPlatformVersion(int maxSdkVersion) {
            this.mMaxSdkVersion = maxSdkVersion;
            return this;
        }

        public SourceStampVerifier build() {
            return new SourceStampVerifier(this.mApkFile, this.mApkDataSource, this.mMinSdkVersion, this.mMaxSdkVersion);
        }
    }
}
