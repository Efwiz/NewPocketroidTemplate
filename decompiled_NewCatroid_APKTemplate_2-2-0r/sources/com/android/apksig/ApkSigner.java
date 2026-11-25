package com.android.apksig;

import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkSigningBlockNotFoundException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.apk.MinSdkVersionException;
import com.android.apksig.internal.util.ByteBufferDataSource;
import com.android.apksig.internal.zip.CentralDirectoryRecord;
import com.android.apksig.internal.zip.EocdRecord;
import com.android.apksig.internal.zip.LocalFileRecord;
import com.android.apksig.internal.zip.ZipUtils;
import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSinks;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.DataSources;
import com.android.apksig.util.ReadableDataSink;
import com.android.apksig.zip.ZipFormatException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ApkSigner {
    private static final short ALIGNMENT_ZIP_EXTRA_DATA_FIELD_HEADER_ID = -9931;
    private static final short ALIGNMENT_ZIP_EXTRA_DATA_FIELD_MIN_SIZE_BYTES = 6;
    private static final short ANDROID_COMMON_PAGE_ALIGNMENT_BYTES = 4096;
    private static final short ANDROID_FILE_ALIGNMENT_BYTES = 4096;
    private static final String ANDROID_MANIFEST_ZIP_ENTRY_NAME = "AndroidManifest.xml";
    private final boolean mAlignFileSize;
    private final String mCreatedBy;
    private final boolean mDebuggableApkPermitted;
    private final boolean mForceSourceStampOverwrite;
    private final DataSource mInputApkDataSource;
    private final File mInputApkFile;
    private final Integer mMinSdkVersion;
    private final boolean mOtherSignersSignaturesPreserved;
    private final DataSink mOutputApkDataSink;
    private final DataSource mOutputApkDataSource;
    private final File mOutputApkFile;
    private final File mOutputV4File;
    private final int mRotationMinSdkVersion;
    private final boolean mRotationTargetsDevRelease;
    private final List<SignerConfig> mSignerConfigs;
    private final ApkSignerEngine mSignerEngine;
    private final SigningCertificateLineage mSigningCertificateLineage;
    private final SignerConfig mSourceStampSignerConfig;
    private final SigningCertificateLineage mSourceStampSigningCertificateLineage;
    private final boolean mSourceStampTimestampEnabled;
    private final boolean mV1SigningEnabled;
    private final boolean mV2SigningEnabled;
    private final boolean mV3SigningEnabled;
    private final boolean mV4ErrorReportingEnabled;
    private final boolean mV4SigningEnabled;
    private final boolean mVerityEnabled;

    /* synthetic */ ApkSigner(List list, SignerConfig signerConfig, SigningCertificateLineage signingCertificateLineage, boolean z, boolean z2, Integer num, int i, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, String str, ApkSignerEngine apkSignerEngine, File file, DataSource dataSource, File file2, DataSink dataSink, DataSource dataSource2, File file3, SigningCertificateLineage signingCertificateLineage2, AnonymousClass1 anonymousClass1) {
        this(list, signerConfig, signingCertificateLineage, z, z2, num, i, z3, z4, z5, z6, z7, z8, z9, z10, z11, z12, str, apkSignerEngine, file, dataSource, file2, dataSink, dataSource2, file3, signingCertificateLineage2);
    }

    private ApkSigner(List<SignerConfig> signerConfigs, SignerConfig sourceStampSignerConfig, SigningCertificateLineage sourceStampSigningCertificateLineage, boolean forceSourceStampOverwrite, boolean sourceStampTimestampEnabled, Integer minSdkVersion, int rotationMinSdkVersion, boolean rotationTargetsDevRelease, boolean v1SigningEnabled, boolean v2SigningEnabled, boolean v3SigningEnabled, boolean v4SigningEnabled, boolean alignFileSize, boolean verityEnabled, boolean v4ErrorReportingEnabled, boolean debuggableApkPermitted, boolean otherSignersSignaturesPreserved, String createdBy, ApkSignerEngine signerEngine, File inputApkFile, DataSource inputApkDataSource, File outputApkFile, DataSink outputApkDataSink, DataSource outputApkDataSource, File outputV4File, SigningCertificateLineage signingCertificateLineage) {
        this.mSignerConfigs = signerConfigs;
        this.mSourceStampSignerConfig = sourceStampSignerConfig;
        this.mSourceStampSigningCertificateLineage = sourceStampSigningCertificateLineage;
        this.mForceSourceStampOverwrite = forceSourceStampOverwrite;
        this.mSourceStampTimestampEnabled = sourceStampTimestampEnabled;
        this.mMinSdkVersion = minSdkVersion;
        this.mRotationMinSdkVersion = rotationMinSdkVersion;
        this.mRotationTargetsDevRelease = rotationTargetsDevRelease;
        this.mV1SigningEnabled = v1SigningEnabled;
        this.mV2SigningEnabled = v2SigningEnabled;
        this.mV3SigningEnabled = v3SigningEnabled;
        this.mV4SigningEnabled = v4SigningEnabled;
        this.mAlignFileSize = alignFileSize;
        this.mVerityEnabled = verityEnabled;
        this.mV4ErrorReportingEnabled = v4ErrorReportingEnabled;
        this.mDebuggableApkPermitted = debuggableApkPermitted;
        this.mOtherSignersSignaturesPreserved = otherSignersSignaturesPreserved;
        this.mCreatedBy = createdBy;
        this.mSignerEngine = signerEngine;
        this.mInputApkFile = inputApkFile;
        this.mInputApkDataSource = inputApkDataSource;
        this.mOutputApkFile = outputApkFile;
        this.mOutputApkDataSink = outputApkDataSink;
        this.mOutputApkDataSource = outputApkDataSource;
        this.mOutputV4File = outputV4File;
        this.mSigningCertificateLineage = signingCertificateLineage;
    }

    public void sign() throws Throwable {
        RandomAccessFile randomAccessFile;
        Throwable th;
        DataSource dataSourceAsDataSource;
        DataSource dataSourceAsDataSource2;
        RandomAccessFile randomAccessFile2 = null;
        try {
            DataSource dataSource = this.mInputApkDataSource;
            if (dataSource != null) {
                dataSourceAsDataSource = dataSource;
                randomAccessFile = null;
            } else if (this.mInputApkFile != null) {
                randomAccessFile = new RandomAccessFile(this.mInputApkFile, "r");
                try {
                    dataSourceAsDataSource = DataSources.asDataSource(randomAccessFile);
                } catch (Throwable th2) {
                    th = th2;
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                    throw th;
                }
            } else {
                throw new IllegalStateException("Input APK not specified");
            }
            try {
                DataSink dataSink = this.mOutputApkDataSink;
                if (dataSink != null) {
                    dataSourceAsDataSource2 = this.mOutputApkDataSource;
                } else if (this.mOutputApkFile != null) {
                    RandomAccessFile randomAccessFile3 = new RandomAccessFile(this.mOutputApkFile, "rw");
                    try {
                        randomAccessFile3.setLength(0L);
                        DataSink dataSinkAsDataSink = DataSinks.asDataSink(randomAccessFile3);
                        dataSourceAsDataSource2 = DataSources.asDataSource(randomAccessFile3);
                        dataSink = dataSinkAsDataSink;
                        randomAccessFile2 = randomAccessFile3;
                    } catch (Throwable th3) {
                        th = th3;
                        randomAccessFile2 = randomAccessFile3;
                        if (randomAccessFile2 != null) {
                            randomAccessFile2.close();
                        }
                        throw th;
                    }
                } else {
                    throw new IllegalStateException("Output APK not specified");
                }
                sign(dataSourceAsDataSource, dataSink, dataSourceAsDataSource2);
                if (randomAccessFile2 != null) {
                    randomAccessFile2.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (Throwable th5) {
            randomAccessFile = null;
            th = th5;
        }
    }

    private void sign(DataSource inputApk, DataSink outputApkOut, DataSource outputApkIn) throws SignatureException, NoSuchAlgorithmException, IOException, InvalidKeyException, ApkFormatException {
        long zipCentralDirectoryOffset;
        DataSource contents;
        DataSource dataSourceSlice;
        List<CentralDirectoryRecord> zipCentralDirectory;
        List<Hints.PatternWithRange> listExtractPinPatterns;
        ArrayList arrayList;
        int minSdkVersionFromApk;
        ApkSignerEngine apkSignerEngine;
        HashMap map;
        Iterator it;
        int i;
        long j;
        byte[] uncompressedData;
        int i2;
        long jOutputDataToOutputApk;
        ApkSigner apkSigner;
        DataSink dataSink;
        boolean z;
        byte b;
        HashMap map2;
        ArrayList arrayList2;
        Hints.ByteRange byteRangeClampToAbsoluteByteRange;
        DataSink dataSink2 = outputApkOut;
        try {
            ApkUtils.ZipSections zipSectionsFindZipSections = ApkUtils.findZipSections(inputApk);
            try {
                ApkUtils.ApkSigningBlock apkSigningBlockFindApkSigningBlock = ApkUtils.findApkSigningBlock(inputApk, zipSectionsFindZipSections);
                zipCentralDirectoryOffset = apkSigningBlockFindApkSigningBlock.getStartOffset();
                try {
                    contents = apkSigningBlockFindApkSigningBlock.getContents();
                    while (true) {
                        zipSectionsFindZipSections = zipSectionsFindZipSections;
                        if (it.hasNext()) {
                            CentralDirectoryRecord centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset = (CentralDirectoryRecord) it.next();
                            String name2 = centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset.getName();
                            if (!Hints.PIN_BYTE_RANGE_ZIP_ENTRY_NAME.equals(name2)) {
                                if ("stamp-cert-sha256".equals(name2)) {
                                    int i3 = i;
                                    try {
                                        uncompressedData = LocalFileRecord.getUncompressedData(dataSourceSlice, centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset, dataSourceSlice.size());
                                        i = i3;
                                    } catch (ZipFormatException unused) {
                                        throw new ApkFormatException("Bad source stamp entry");
                                    }
                                } else {
                                    int i4 = i;
                                    ApkSignerEngine.InputJarEntryInstructions inputJarEntryInstructionsInputJarEntry = apkSignerEngine.inputJarEntry(name2);
                                    int i5 = AnonymousClass1.$SwitchMap$com$android$apksig$ApkSignerEngine$InputJarEntryInstructions$OutputPolicy[inputJarEntryInstructionsInputJarEntry.getOutputPolicy().ordinal()];
                                    byte[] bArr = uncompressedData;
                                    if (i5 == 1) {
                                        z = true;
                                    } else {
                                        if (i5 != 2 && i5 != 3) {
                                            throw new RuntimeException("Unknown output policy: " + inputJarEntryInstructionsInputJarEntry.getOutputPolicy());
                                        }
                                        z = false;
                                    }
                                    long localFileHeaderOffset = centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset.getLocalFileHeaderOffset();
                                    if (localFileHeaderOffset > j) {
                                        long j2 = localFileHeaderOffset - j;
                                        b = -1;
                                        dataSourceSlice.feed(j, j2, outputApkOut);
                                        jOutputDataToOutputApk += j2;
                                        j = localFileHeaderOffset;
                                    } else {
                                        b = -1;
                                    }
                                    try {
                                        LocalFileRecord record = LocalFileRecord.getRecord(dataSourceSlice, centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset, dataSourceSlice.size());
                                        long size = j + record.getSize();
                                        ApkSignerEngine.InspectJarEntryRequest inspectJarEntryRequest = inputJarEntryInstructionsInputJarEntry.getInspectJarEntryRequest();
                                        if (inspectJarEntryRequest != null) {
                                            fulfillInspectInputJarEntryRequest(dataSourceSlice, record, inspectJarEntryRequest);
                                        }
                                        if (z) {
                                            int lastModificationDate = centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset.getLastModificationDate();
                                            int lastModificationTime = centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset.getLastModificationTime();
                                            if (i2 == b || lastModificationDate > i2 || (lastModificationDate == i2 && lastModificationTime > i4)) {
                                                i4 = lastModificationTime;
                                            } else {
                                                lastModificationDate = i2;
                                            }
                                            ApkSignerEngine.InspectJarEntryRequest inspectJarEntryRequestOutputJarEntry = apkSignerEngine.outputJarEntry(name2);
                                            if (inspectJarEntryRequestOutputJarEntry != null) {
                                                fulfillInspectInputJarEntryRequest(dataSourceSlice, record, inspectJarEntryRequestOutputJarEntry);
                                            }
                                            long j3 = jOutputDataToOutputApk;
                                            OutputSizeAndDataOffset outputSizeAndDataOffsetOutputInputJarEntryLfhRecordPreservingDataAlignment = outputInputJarEntryLfhRecordPreservingDataAlignment(dataSourceSlice, record, dataSink2, j3);
                                            int i6 = lastModificationDate;
                                            long j4 = outputSizeAndDataOffsetOutputInputJarEntryLfhRecordPreservingDataAlignment.outputBytes + j3;
                                            long j5 = outputSizeAndDataOffsetOutputInputJarEntryLfhRecordPreservingDataAlignment.dataOffsetBytes + j3;
                                            if (listExtractPinPatterns != null) {
                                                Iterator<Hints.PatternWithRange> it2 = listExtractPinPatterns.iterator();
                                                boolean z2 = false;
                                                while (it2.hasNext()) {
                                                    Hints.PatternWithRange next = it2.next();
                                                    Iterator<Hints.PatternWithRange> it3 = it2;
                                                    if (!next.matcher(centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset.getName()).matches() || (byteRangeClampToAbsoluteByteRange = next.ClampToAbsoluteByteRange(new Hints.ByteRange(j5, j4))) == null) {
                                                        arrayList2 = arrayList;
                                                    } else {
                                                        arrayList2 = arrayList;
                                                        arrayList2.add(byteRangeClampToAbsoluteByteRange);
                                                        z2 = true;
                                                    }
                                                    arrayList = arrayList2;
                                                    it2 = it3;
                                                }
                                                arrayList = arrayList;
                                                if (z2) {
                                                    arrayList.add(new Hints.ByteRange(j3, j5));
                                                }
                                            } else {
                                                arrayList = arrayList;
                                            }
                                            if (j3 != record.getStartOffsetInArchive()) {
                                                centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset = centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset.createWithModifiedLocalFileHeaderOffset(j3);
                                            }
                                            map2 = map;
                                            map2.put(name2, centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset);
                                            jOutputDataToOutputApk = j4;
                                            i2 = i6;
                                        } else {
                                            arrayList = arrayList;
                                            map2 = map;
                                            i2 = i2;
                                        }
                                        i = i4;
                                        map = map2;
                                        zipCentralDirectory = zipCentralDirectory;
                                        j = size;
                                        uncompressedData = bArr;
                                        dataSink2 = outputApkOut;
                                    } catch (ZipFormatException e) {
                                        throw new ApkFormatException("Malformed ZIP entry: " + centralDirectoryRecordCreateWithModifiedLocalFileHeaderOffset.getName(), e);
                                    }
                                }
                            }
                        } else {
                            List<CentralDirectoryRecord> list = zipCentralDirectory;
                            byte[] bArr2 = uncompressedData;
                            int i7 = i2;
                            HashMap map3 = map;
                            int i8 = i;
                            long size2 = dataSourceSlice.size();
                            if (j < size2) {
                                long j6 = size2 - j;
                                dataSourceSlice.feed(j, j6, outputApkOut);
                                jOutputDataToOutputApk += j6;
                            }
                            ArrayList arrayList3 = new ArrayList(list.size() + 10);
                            Iterator<CentralDirectoryRecord> it4 = list.iterator();
                            while (it4.hasNext()) {
                                CentralDirectoryRecord centralDirectoryRecord = (CentralDirectoryRecord) map3.get(it4.next().getName());
                                if (centralDirectoryRecord != null) {
                                    arrayList3.add(centralDirectoryRecord);
                                }
                            }
                            if (i7 == -1) {
                                i7 = 14881;
                                i8 = 0;
                            }
                            if (apkSignerEngine.isEligibleForSourceStamp()) {
                                byte[] bArrGenerateSourceStampCertificateDigest = apkSignerEngine.generateSourceStampCertificateDigest();
                                apkSigner = this;
                                if (apkSigner.mForceSourceStampOverwrite || bArr2 == null || Arrays.equals(bArrGenerateSourceStampCertificateDigest, bArr2)) {
                                    jOutputDataToOutputApk += outputDataToOutputApk("stamp-cert-sha256", bArrGenerateSourceStampCertificateDigest, jOutputDataToOutputApk, arrayList3, i8, i7, outputApkOut);
                                } else {
                                    throw new ApkFormatException(String.format("Cannot generate SourceStamp. APK contains an existing entry with the name: %s, and it is different than the provided source stamp certificate", "stamp-cert-sha256"));
                                }
                            } else {
                                apkSigner = this;
                            }
                            long jOutputDataToOutputApk2 = jOutputDataToOutputApk;
                            if (arrayList != null) {
                                arrayList.add(new Hints.ByteRange(jOutputDataToOutputApk2, Long.MAX_VALUE));
                                byte[] bArrEncodeByteRangeList = Hints.encodeByteRangeList(arrayList);
                                requestOutputEntryInspection(apkSignerEngine, Hints.PIN_BYTE_RANGE_ZIP_ENTRY_NAME, bArrEncodeByteRangeList);
                                jOutputDataToOutputApk2 += outputDataToOutputApk(Hints.PIN_BYTE_RANGE_ZIP_ENTRY_NAME, bArrEncodeByteRangeList, jOutputDataToOutputApk2, arrayList3, i8, i7, outputApkOut);
                            }
                            ApkSignerEngine.OutputJarSignatureRequest outputJarSignatureRequestOutputJarEntries = apkSignerEngine.outputJarEntries();
                            if (outputJarSignatureRequestOutputJarEntries != null) {
                                for (ApkSignerEngine.OutputJarSignatureRequest.JarEntry jarEntry : outputJarSignatureRequestOutputJarEntries.getAdditionalJarEntries()) {
                                    String name3 = jarEntry.getName();
                                    byte[] data = jarEntry.getData();
                                    requestOutputEntryInspection(apkSignerEngine, name3, data);
                                    jOutputDataToOutputApk2 += outputDataToOutputApk(name3, data, jOutputDataToOutputApk2, arrayList3, i8, i7, outputApkOut);
                                }
                                outputJarSignatureRequestOutputJarEntries.done();
                            }
                            Iterator it5 = arrayList3.iterator();
                            long size3 = 0;
                            while (it5.hasNext()) {
                                size3 += (long) ((CentralDirectoryRecord) it5.next()).getSize();
                            }
                            if (size3 > 2147483647L) {
                                throw new IOException("Output ZIP Central Directory too large: " + size3 + " bytes");
                            }
                            ByteBuffer byteBufferAllocate = ByteBuffer.allocate((int) size3);
                            Iterator it6 = arrayList3.iterator();
                            while (it6.hasNext()) {
                                ((CentralDirectoryRecord) it6.next()).copyTo(byteBufferAllocate);
                            }
                            byteBufferAllocate.flip();
                            ByteBufferDataSource byteBufferDataSource = new ByteBufferDataSource(byteBufferAllocate);
                            ByteBuffer byteBufferCreateWithModifiedCentralDirectoryInfo = EocdRecord.createWithModifiedCentralDirectoryInfo(zipSectionsFindZipSections.getZipEndOfCentralDirectory(), arrayList3.size(), byteBufferDataSource.size(), jOutputDataToOutputApk2);
                            ApkSignerEngine.OutputApkSigningBlockRequest2 outputApkSigningBlockRequest2OutputZipSections2 = apkSignerEngine.outputZipSections2(outputApkIn, byteBufferDataSource, DataSources.asDataSource(byteBufferCreateWithModifiedCentralDirectoryInfo));
                            if (outputApkSigningBlockRequest2OutputZipSections2 != null) {
                                int paddingSizeBeforeApkSigningBlock = outputApkSigningBlockRequest2OutputZipSections2.getPaddingSizeBeforeApkSigningBlock();
                                byte[] apkSigningBlock = outputApkSigningBlockRequest2OutputZipSections2.getApkSigningBlock();
                                outputApkSigningBlockRequest2OutputZipSections2.done();
                                long j7 = paddingSizeBeforeApkSigningBlock;
                                long size4 = byteBufferDataSource.size() + jOutputDataToOutputApk2 + j7 + ((long) apkSigningBlock.length) + ((long) byteBufferCreateWithModifiedCentralDirectoryInfo.remaining());
                                if (apkSigner.mAlignFileSize) {
                                    long j8 = size4 % 4096;
                                    if (j8 != 0) {
                                        byteBufferCreateWithModifiedCentralDirectoryInfo = EocdRecord.createWithPaddedComment(byteBufferCreateWithModifiedCentralDirectoryInfo, (int) (4096 - j8));
                                        ApkSignerEngine.OutputApkSigningBlockRequest2 outputApkSigningBlockRequest2OutputZipSections3 = apkSignerEngine.outputZipSections2(outputApkIn, new ByteBufferDataSource(byteBufferAllocate), DataSources.asDataSource(byteBufferCreateWithModifiedCentralDirectoryInfo));
                                        apkSigningBlock = outputApkSigningBlockRequest2OutputZipSections3.getApkSigningBlock();
                                        outputApkSigningBlockRequest2OutputZipSections3.done();
                                    }
                                }
                                dataSink = outputApkOut;
                                dataSink.consume(ByteBuffer.allocate(paddingSizeBeforeApkSigningBlock));
                                dataSink.consume(apkSigningBlock, 0, apkSigningBlock.length);
                                ZipUtils.setZipEocdCentralDirectoryOffset(byteBufferCreateWithModifiedCentralDirectoryInfo, jOutputDataToOutputApk2 + j7 + ((long) apkSigningBlock.length));
                            } else {
                                dataSink = outputApkOut;
                            }
                            byteBufferDataSource.feed(0L, byteBufferDataSource.size(), outputApkOut);
                            dataSink.consume(byteBufferCreateWithModifiedCentralDirectoryInfo);
                            apkSignerEngine.outputDone();
                            if (apkSigner.mV4SigningEnabled) {
                                apkSignerEngine.signV4(outputApkIn, apkSigner.mOutputV4File, !apkSigner.mV4ErrorReportingEnabled);
                                return;
                            }
                            return;
                        }
                    }
                } catch (ApkSigningBlockNotFoundException unused2) {
                    contents = null;
                }
            } catch (ApkSigningBlockNotFoundException unused3) {
                zipCentralDirectoryOffset = -1;
            }
            if (zipCentralDirectoryOffset == -1) {
                zipCentralDirectoryOffset = zipSectionsFindZipSections.getZipCentralDirectoryOffset();
            }
            dataSourceSlice = inputApk.slice(0L, zipCentralDirectoryOffset);
            zipCentralDirectory = parseZipCentralDirectory(getZipCentralDirectory(inputApk, zipSectionsFindZipSections), zipSectionsFindZipSections);
            listExtractPinPatterns = extractPinPatterns(zipCentralDirectory, dataSourceSlice);
            arrayList = listExtractPinPatterns == null ? null : new ArrayList();
            ApkSignerEngine apkSignerEngineBuild = this.mSignerEngine;
            if (apkSignerEngineBuild == null) {
                Integer num = this.mMinSdkVersion;
                if (num != null) {
                    minSdkVersionFromApk = num.intValue();
                } else {
                    minSdkVersionFromApk = getMinSdkVersionFromApk(zipCentralDirectory, dataSourceSlice);
                }
                ArrayList arrayList4 = new ArrayList(this.mSignerConfigs.size());
                for (Iterator<SignerConfig> it7 = this.mSignerConfigs.iterator(); it7.hasNext(); it7 = it7) {
                    SignerConfig next2 = it7.next();
                    arrayList4.add(new DefaultApkSignerEngine.SignerConfig.Builder(next2.getName(), next2.getPrivateKey(), next2.getCertificates(), next2.getDeterministicDsaSigning()).build());
                }
                DefaultApkSignerEngine.Builder rotationTargetsDevRelease = new DefaultApkSignerEngine.Builder(arrayList4, minSdkVersionFromApk).setV1SigningEnabled(this.mV1SigningEnabled).setV2SigningEnabled(this.mV2SigningEnabled).setV3SigningEnabled(this.mV3SigningEnabled).setVerityEnabled(this.mVerityEnabled).setDebuggableApkPermitted(this.mDebuggableApkPermitted).setOtherSignersSignaturesPreserved(this.mOtherSignersSignaturesPreserved).setSigningCertificateLineage(this.mSigningCertificateLineage).setMinSdkVersionForRotation(this.mRotationMinSdkVersion).setRotationTargetsDevRelease(this.mRotationTargetsDevRelease);
                String str = this.mCreatedBy;
                if (str != null) {
                    rotationTargetsDevRelease.setCreatedBy(str);
                }
                if (this.mSourceStampSignerConfig != null) {
                    rotationTargetsDevRelease.setStampSignerConfig(new DefaultApkSignerEngine.SignerConfig.Builder(this.mSourceStampSignerConfig.getName(), this.mSourceStampSignerConfig.getPrivateKey(), this.mSourceStampSignerConfig.getCertificates(), this.mSourceStampSignerConfig.getDeterministicDsaSigning()).build());
                    rotationTargetsDevRelease.setSourceStampTimestampEnabled(this.mSourceStampTimestampEnabled);
                }
                SigningCertificateLineage signingCertificateLineage = this.mSourceStampSigningCertificateLineage;
                if (signingCertificateLineage != null) {
                    rotationTargetsDevRelease.setSourceStampSigningCertificateLineage(signingCertificateLineage);
                }
                apkSignerEngineBuild = rotationTargetsDevRelease.build();
            }
            apkSignerEngine = apkSignerEngineBuild;
            if (contents != null) {
                apkSignerEngine.inputApkSigningBlock(contents);
            }
            ArrayList arrayList5 = new ArrayList(zipCentralDirectory);
            Collections.sort(arrayList5, CentralDirectoryRecord.BY_LOCAL_FILE_HEADER_OFFSET_COMPARATOR);
            map = new HashMap(zipCentralDirectory.size());
            it = arrayList5.iterator();
            i = -1;
            j = 0;
            uncompressedData = null;
            i2 = -1;
            jOutputDataToOutputApk = 0;
        } catch (ZipFormatException e2) {
            throw new ApkFormatException("Malformed APK: not a ZIP archive", e2);
        }
    }

    /* JADX INFO: renamed from: com.android.apksig.ApkSigner$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$android$apksig$ApkSignerEngine$InputJarEntryInstructions$OutputPolicy;

        static {
            int[] iArr = new int[ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.values().length];
            $SwitchMap$com$android$apksig$ApkSignerEngine$InputJarEntryInstructions$OutputPolicy = iArr;
            try {
                iArr[ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.OUTPUT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$apksig$ApkSignerEngine$InputJarEntryInstructions$OutputPolicy[ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.OUTPUT_BY_ENGINE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$apksig$ApkSignerEngine$InputJarEntryInstructions$OutputPolicy[ApkSignerEngine.InputJarEntryInstructions.OutputPolicy.SKIP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static void requestOutputEntryInspection(ApkSignerEngine signerEngine, String entryName, byte[] uncompressedData) throws IOException {
        ApkSignerEngine.InspectJarEntryRequest inspectJarEntryRequestOutputJarEntry = signerEngine.outputJarEntry(entryName);
        if (inspectJarEntryRequestOutputJarEntry != null) {
            inspectJarEntryRequestOutputJarEntry.getDataSink().consume(uncompressedData, 0, uncompressedData.length);
            inspectJarEntryRequestOutputJarEntry.done();
        }
    }

    private static long outputDataToOutputApk(String entryName, byte[] uncompressedData, long localFileHeaderOffset, List<CentralDirectoryRecord> outputCdRecords, int lastModifiedTimeForNewEntries, int lastModifiedDateForNewEntries, DataSink outputApkOut) throws IOException {
        ZipUtils.DeflateResult deflateResultDeflate = ZipUtils.deflate(ByteBuffer.wrap(uncompressedData));
        byte[] bArr = deflateResultDeflate.output;
        long j = deflateResultDeflate.inputCrc32;
        long jOutputRecordWithDeflateCompressedData = LocalFileRecord.outputRecordWithDeflateCompressedData(entryName, lastModifiedTimeForNewEntries, lastModifiedDateForNewEntries, bArr, j, uncompressedData.length, outputApkOut);
        outputCdRecords.add(CentralDirectoryRecord.createWithDeflateCompressedData(entryName, lastModifiedTimeForNewEntries, lastModifiedDateForNewEntries, j, bArr.length, uncompressedData.length, localFileHeaderOffset));
        return jOutputRecordWithDeflateCompressedData;
    }

    private static void fulfillInspectInputJarEntryRequest(DataSource lfhSection, LocalFileRecord localFileRecord, ApkSignerEngine.InspectJarEntryRequest inspectEntryRequest) throws IOException, ApkFormatException {
        try {
            localFileRecord.outputUncompressedData(lfhSection, inspectEntryRequest.getDataSink());
            inspectEntryRequest.done();
        } catch (ZipFormatException e) {
            throw new ApkFormatException("Malformed ZIP entry: " + localFileRecord.getName(), e);
        }
    }

    private static class OutputSizeAndDataOffset {
        public long dataOffsetBytes;
        public long outputBytes;

        public OutputSizeAndDataOffset(long outputBytes, long dataOffsetBytes) {
            this.outputBytes = outputBytes;
            this.dataOffsetBytes = dataOffsetBytes;
        }
    }

    private static OutputSizeAndDataOffset outputInputJarEntryLfhRecordPreservingDataAlignment(DataSource inputLfhSection, LocalFileRecord inputRecord, DataSink outputLfhSection, long outputOffset) throws IOException {
        long startOffsetInArchive = inputRecord.getStartOffsetInArchive();
        if (startOffsetInArchive == outputOffset) {
            return new OutputSizeAndDataOffset(inputRecord.outputRecord(inputLfhSection, outputLfhSection), inputRecord.getDataStartOffsetInRecord());
        }
        int inputJarEntryDataAlignmentMultiple = getInputJarEntryDataAlignmentMultiple(inputRecord);
        if (inputJarEntryDataAlignmentMultiple > 1) {
            long j = inputJarEntryDataAlignmentMultiple;
            if (startOffsetInArchive % j != outputOffset % j) {
                if ((startOffsetInArchive + ((long) inputRecord.getDataStartOffsetInRecord())) % j != 0) {
                    return new OutputSizeAndDataOffset(inputRecord.outputRecord(inputLfhSection, outputLfhSection), inputRecord.getDataStartOffsetInRecord());
                }
                ByteBuffer byteBufferCreateExtraFieldToAlignData = createExtraFieldToAlignData(inputRecord.getExtra(), outputOffset + ((long) inputRecord.getExtraFieldStartOffsetInsideRecord()), inputJarEntryDataAlignmentMultiple);
                return new OutputSizeAndDataOffset(inputRecord.outputRecordWithModifiedExtra(inputLfhSection, byteBufferCreateExtraFieldToAlignData, outputLfhSection), (((long) inputRecord.getDataStartOffsetInRecord()) + ((long) byteBufferCreateExtraFieldToAlignData.remaining())) - ((long) inputRecord.getExtra().remaining()));
            }
        }
        return new OutputSizeAndDataOffset(inputRecord.outputRecord(inputLfhSection, outputLfhSection), inputRecord.getDataStartOffsetInRecord());
    }

    private static int getInputJarEntryDataAlignmentMultiple(LocalFileRecord entry) {
        if (entry.isDataCompressed()) {
            return 1;
        }
        ByteBuffer extra = entry.getExtra();
        if (extra.hasRemaining()) {
            extra.order(ByteOrder.LITTLE_ENDIAN);
            while (extra.remaining() >= 4) {
                short s = extra.getShort();
                int unsignedInt16 = ZipUtils.getUnsignedInt16(extra);
                if (unsignedInt16 > extra.remaining()) {
                    break;
                }
                if (s == -9931) {
                    if (unsignedInt16 < 2) {
                        break;
                    }
                    return ZipUtils.getUnsignedInt16(extra);
                }
                extra.position(extra.position() + unsignedInt16);
            }
        }
        return entry.getName().endsWith(".so") ? 4096 : 4;
    }

    private static ByteBuffer createExtraFieldToAlignData(ByteBuffer original, long extraStartOffset, int dataAlignmentMultiple) {
        if (dataAlignmentMultiple <= 1) {
            return original;
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(original.remaining() + 5 + dataAlignmentMultiple);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        while (original.remaining() >= 4) {
            short s = original.getShort();
            int unsignedInt16 = ZipUtils.getUnsignedInt16(original);
            if (unsignedInt16 > original.remaining()) {
                break;
            }
            if ((s == 0 && unsignedInt16 == 0) || s == -9931) {
                original.position(original.position() + unsignedInt16);
            } else {
                original.position(original.position() - 4);
                int iLimit = original.limit();
                original.limit(original.position() + 4 + unsignedInt16);
                byteBufferAllocate.put(original);
                original.limit(iLimit);
            }
        }
        int iPosition = (dataAlignmentMultiple - ((int) (((extraStartOffset + ((long) byteBufferAllocate.position())) + 6) % ((long) dataAlignmentMultiple)))) % dataAlignmentMultiple;
        byteBufferAllocate.putShort(ALIGNMENT_ZIP_EXTRA_DATA_FIELD_HEADER_ID);
        ZipUtils.putUnsignedInt16(byteBufferAllocate, iPosition + 2);
        ZipUtils.putUnsignedInt16(byteBufferAllocate, dataAlignmentMultiple);
        byteBufferAllocate.position(byteBufferAllocate.position() + iPosition);
        byteBufferAllocate.flip();
        return byteBufferAllocate;
    }

    private static ByteBuffer getZipCentralDirectory(DataSource apk, ApkUtils.ZipSections apkSections) throws IOException, ApkFormatException {
        long zipCentralDirectorySizeBytes = apkSections.getZipCentralDirectorySizeBytes();
        if (zipCentralDirectorySizeBytes > 2147483647L) {
            throw new ApkFormatException("ZIP Central Directory too large: " + zipCentralDirectorySizeBytes);
        }
        ByteBuffer byteBuffer = apk.getByteBuffer(apkSections.getZipCentralDirectoryOffset(), (int) zipCentralDirectorySizeBytes);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return byteBuffer;
    }

    private static List<CentralDirectoryRecord> parseZipCentralDirectory(ByteBuffer cd, ApkUtils.ZipSections apkSections) throws ApkFormatException {
        long zipCentralDirectoryOffset = apkSections.getZipCentralDirectoryOffset();
        int zipCentralDirectoryRecordCount = apkSections.getZipCentralDirectoryRecordCount();
        ArrayList arrayList = new ArrayList(zipCentralDirectoryRecordCount);
        HashSet hashSet = new HashSet(zipCentralDirectoryRecordCount);
        for (int i = 0; i < zipCentralDirectoryRecordCount; i++) {
            int iPosition = cd.position();
            try {
                CentralDirectoryRecord record = CentralDirectoryRecord.getRecord(cd);
                String name2 = record.getName();
                if (!hashSet.add(name2)) {
                    throw new ApkFormatException("Multiple ZIP entries with the same name: " + name2);
                }
                arrayList.add(record);
            } catch (ZipFormatException e) {
                throw new ApkFormatException("Malformed ZIP Central Directory record #" + (i + 1) + " at file offset " + (zipCentralDirectoryOffset + ((long) iPosition)), e);
            }
        }
        if (cd.hasRemaining()) {
            throw new ApkFormatException("Unused space at the end of ZIP Central Directory: " + cd.remaining() + " bytes starting at file offset " + (zipCentralDirectoryOffset + ((long) cd.position())));
        }
        return arrayList;
    }

    private static CentralDirectoryRecord findCdRecord(List<CentralDirectoryRecord> cdRecords, String name2) {
        for (CentralDirectoryRecord centralDirectoryRecord : cdRecords) {
            if (name2.equals(centralDirectoryRecord.getName())) {
                return centralDirectoryRecord;
            }
        }
        return null;
    }

    static ByteBuffer getAndroidManifestFromApk(List<CentralDirectoryRecord> cdRecords, DataSource lhfSection) throws IOException, ApkFormatException, ZipFormatException {
        CentralDirectoryRecord centralDirectoryRecordFindCdRecord = findCdRecord(cdRecords, "AndroidManifest.xml");
        if (centralDirectoryRecordFindCdRecord == null) {
            throw new ApkFormatException("Missing AndroidManifest.xml");
        }
        return ByteBuffer.wrap(LocalFileRecord.getUncompressedData(lhfSection, centralDirectoryRecordFindCdRecord, lhfSection.size()));
    }

    private static List<Hints.PatternWithRange> extractPinPatterns(List<CentralDirectoryRecord> cdRecords, DataSource lhfSection) throws IOException, ApkFormatException {
        CentralDirectoryRecord centralDirectoryRecordFindCdRecord = findCdRecord(cdRecords, Hints.PIN_HINT_ASSET_ZIP_ENTRY_NAME);
        if (centralDirectoryRecordFindCdRecord == null) {
            return null;
        }
        new ArrayList();
        try {
            return Hints.parsePinPatterns(LocalFileRecord.getUncompressedData(lhfSection, centralDirectoryRecordFindCdRecord, lhfSection.size()));
        } catch (ZipFormatException unused) {
            throw new ApkFormatException("Bad " + centralDirectoryRecordFindCdRecord);
        }
    }

    private static int getMinSdkVersionFromApk(List<CentralDirectoryRecord> cdRecords, DataSource lhfSection) throws MinSdkVersionException, IOException {
        try {
            return ApkUtils.getMinSdkVersionFromBinaryAndroidManifest(getAndroidManifestFromApk(cdRecords, lhfSection));
        } catch (ApkFormatException | ZipFormatException e) {
            throw new MinSdkVersionException("Failed to determine APK's minimum supported Android platform version", e);
        }
    }

    public static class SignerConfig {
        private final List<X509Certificate> mCertificates;
        private boolean mDeterministicDsaSigning;
        private final String mName;
        private final PrivateKey mPrivateKey;

        /* synthetic */ SignerConfig(String str, PrivateKey privateKey, List list, boolean z, AnonymousClass1 anonymousClass1) {
            this(str, privateKey, list, z);
        }

        private SignerConfig(String name2, PrivateKey privateKey, List<X509Certificate> certificates, boolean deterministicDsaSigning) {
            this.mName = name2;
            this.mPrivateKey = privateKey;
            this.mCertificates = Collections.unmodifiableList(new ArrayList(certificates));
            this.mDeterministicDsaSigning = deterministicDsaSigning;
        }

        public String getName() {
            return this.mName;
        }

        public PrivateKey getPrivateKey() {
            return this.mPrivateKey;
        }

        public List<X509Certificate> getCertificates() {
            return this.mCertificates;
        }

        public boolean getDeterministicDsaSigning() {
            return this.mDeterministicDsaSigning;
        }

        public static class Builder {
            private final List<X509Certificate> mCertificates;
            private final boolean mDeterministicDsaSigning;
            private final String mName;
            private final PrivateKey mPrivateKey;

            public Builder(String name2, PrivateKey privateKey, List<X509Certificate> certificates) {
                this(name2, privateKey, certificates, false);
            }

            public Builder(String name2, PrivateKey privateKey, List<X509Certificate> certificates, boolean deterministicDsaSigning) {
                if (name2.isEmpty()) {
                    throw new IllegalArgumentException("Empty name");
                }
                this.mName = name2;
                this.mPrivateKey = privateKey;
                this.mCertificates = new ArrayList(certificates);
                this.mDeterministicDsaSigning = deterministicDsaSigning;
            }

            public SignerConfig build() {
                return new SignerConfig(this.mName, this.mPrivateKey, this.mCertificates, this.mDeterministicDsaSigning, null);
            }
        }
    }

    public static class Builder {
        private boolean mAlignFileSize;
        private String mCreatedBy;
        private boolean mDebuggableApkPermitted;
        private boolean mForceSourceStampOverwrite;
        private DataSource mInputApkDataSource;
        private File mInputApkFile;
        private Integer mMinSdkVersion;
        private boolean mOtherSignersSignaturesPreserved;
        private DataSink mOutputApkDataSink;
        private DataSource mOutputApkDataSource;
        private File mOutputApkFile;
        private File mOutputV4File;
        private int mRotationMinSdkVersion;
        private boolean mRotationTargetsDevRelease;
        private final List<SignerConfig> mSignerConfigs;
        private final ApkSignerEngine mSignerEngine;
        private SigningCertificateLineage mSigningCertificateLineage;
        private SignerConfig mSourceStampSignerConfig;
        private SigningCertificateLineage mSourceStampSigningCertificateLineage;
        private boolean mSourceStampTimestampEnabled;
        private boolean mV1SigningEnabled;
        private boolean mV2SigningEnabled;
        private boolean mV3SigningEnabled;
        private boolean mV3SigningExplicitlyDisabled;
        private boolean mV3SigningExplicitlyEnabled;
        private boolean mV4ErrorReportingEnabled;
        private boolean mV4SigningEnabled;
        private boolean mVerityEnabled;

        public Builder(List<SignerConfig> signerConfigs) {
            this.mForceSourceStampOverwrite = false;
            this.mSourceStampTimestampEnabled = true;
            this.mV1SigningEnabled = true;
            this.mV2SigningEnabled = true;
            this.mV3SigningEnabled = true;
            this.mV4SigningEnabled = false;
            this.mAlignFileSize = false;
            this.mVerityEnabled = false;
            this.mV4ErrorReportingEnabled = false;
            this.mDebuggableApkPermitted = true;
            this.mRotationMinSdkVersion = 33;
            this.mRotationTargetsDevRelease = false;
            this.mV3SigningExplicitlyDisabled = false;
            this.mV3SigningExplicitlyEnabled = false;
            if (signerConfigs.isEmpty()) {
                throw new IllegalArgumentException("At least one signer config must be provided");
            }
            if (signerConfigs.size() > 1) {
                this.mV3SigningEnabled = false;
            }
            this.mSignerConfigs = new ArrayList(signerConfigs);
            this.mSignerEngine = null;
        }

        public Builder(ApkSignerEngine signerEngine) {
            this.mForceSourceStampOverwrite = false;
            this.mSourceStampTimestampEnabled = true;
            this.mV1SigningEnabled = true;
            this.mV2SigningEnabled = true;
            this.mV3SigningEnabled = true;
            this.mV4SigningEnabled = false;
            this.mAlignFileSize = false;
            this.mVerityEnabled = false;
            this.mV4ErrorReportingEnabled = false;
            this.mDebuggableApkPermitted = true;
            this.mRotationMinSdkVersion = 33;
            this.mRotationTargetsDevRelease = false;
            this.mV3SigningExplicitlyDisabled = false;
            this.mV3SigningExplicitlyEnabled = false;
            if (signerEngine == null) {
                throw new NullPointerException("signerEngine == null");
            }
            this.mSignerEngine = signerEngine;
            this.mSignerConfigs = null;
        }

        public Builder setSourceStampSignerConfig(SignerConfig sourceStampSignerConfig) {
            this.mSourceStampSignerConfig = sourceStampSignerConfig;
            return this;
        }

        public Builder setSourceStampSigningCertificateLineage(SigningCertificateLineage sourceStampSigningCertificateLineage) {
            this.mSourceStampSigningCertificateLineage = sourceStampSigningCertificateLineage;
            return this;
        }

        public Builder setForceSourceStampOverwrite(boolean force) {
            this.mForceSourceStampOverwrite = force;
            return this;
        }

        public Builder setSourceStampTimestampEnabled(boolean value) {
            this.mSourceStampTimestampEnabled = value;
            return this;
        }

        public Builder setInputApk(File inputApk) {
            if (inputApk == null) {
                throw new NullPointerException("inputApk == null");
            }
            this.mInputApkFile = inputApk;
            this.mInputApkDataSource = null;
            return this;
        }

        public Builder setInputApk(DataSource inputApk) {
            if (inputApk == null) {
                throw new NullPointerException("inputApk == null");
            }
            this.mInputApkDataSource = inputApk;
            this.mInputApkFile = null;
            return this;
        }

        public Builder setOutputApk(File outputApk) {
            if (outputApk == null) {
                throw new NullPointerException("outputApk == null");
            }
            this.mOutputApkFile = outputApk;
            this.mOutputApkDataSink = null;
            this.mOutputApkDataSource = null;
            return this;
        }

        public Builder setOutputApk(ReadableDataSink outputApk) {
            if (outputApk == null) {
                throw new NullPointerException("outputApk == null");
            }
            return setOutputApk(outputApk, outputApk);
        }

        public Builder setOutputApk(DataSink outputApkOut, DataSource outputApkIn) {
            if (outputApkOut == null) {
                throw new NullPointerException("outputApkOut == null");
            }
            if (outputApkIn == null) {
                throw new NullPointerException("outputApkIn == null");
            }
            this.mOutputApkFile = null;
            this.mOutputApkDataSink = outputApkOut;
            this.mOutputApkDataSource = outputApkIn;
            return this;
        }

        public Builder setV4SignatureOutputFile(File v4SignatureOutputFile) {
            if (v4SignatureOutputFile == null) {
                throw new NullPointerException("v4HashRootOutputFile == null");
            }
            this.mOutputV4File = v4SignatureOutputFile;
            return this;
        }

        public Builder setMinSdkVersion(int minSdkVersion) {
            checkInitializedWithoutEngine();
            this.mMinSdkVersion = Integer.valueOf(minSdkVersion);
            return this;
        }

        public Builder setMinSdkVersionForRotation(int minSdkVersion) {
            checkInitializedWithoutEngine();
            if (minSdkVersion < 33) {
                this.mRotationMinSdkVersion = 28;
            } else {
                this.mRotationMinSdkVersion = minSdkVersion;
            }
            return this;
        }

        public Builder setRotationTargetsDevRelease(boolean enabled) {
            checkInitializedWithoutEngine();
            this.mRotationTargetsDevRelease = enabled;
            return this;
        }

        public Builder setV1SigningEnabled(boolean enabled) {
            checkInitializedWithoutEngine();
            this.mV1SigningEnabled = enabled;
            return this;
        }

        public Builder setV2SigningEnabled(boolean enabled) {
            checkInitializedWithoutEngine();
            this.mV2SigningEnabled = enabled;
            return this;
        }

        public Builder setV3SigningEnabled(boolean enabled) {
            checkInitializedWithoutEngine();
            this.mV3SigningEnabled = enabled;
            if (enabled) {
                this.mV3SigningExplicitlyEnabled = true;
            } else {
                this.mV3SigningExplicitlyDisabled = true;
            }
            return this;
        }

        public Builder setV4SigningEnabled(boolean enabled) {
            checkInitializedWithoutEngine();
            this.mV4SigningEnabled = enabled;
            this.mV4ErrorReportingEnabled = enabled;
            return this;
        }

        public Builder setV4ErrorReportingEnabled(boolean enabled) {
            checkInitializedWithoutEngine();
            this.mV4ErrorReportingEnabled = enabled;
            return this;
        }

        public Builder setAlignFileSize(boolean alignFileSize) {
            checkInitializedWithoutEngine();
            this.mAlignFileSize = alignFileSize;
            return this;
        }

        public Builder setVerityEnabled(boolean enabled) {
            checkInitializedWithoutEngine();
            this.mVerityEnabled = enabled;
            return this;
        }

        public Builder setDebuggableApkPermitted(boolean permitted) {
            checkInitializedWithoutEngine();
            this.mDebuggableApkPermitted = permitted;
            return this;
        }

        public Builder setOtherSignersSignaturesPreserved(boolean preserved) {
            checkInitializedWithoutEngine();
            this.mOtherSignersSignaturesPreserved = preserved;
            return this;
        }

        public Builder setCreatedBy(String createdBy) {
            checkInitializedWithoutEngine();
            createdBy.getClass();
            this.mCreatedBy = createdBy;
            return this;
        }

        private void checkInitializedWithoutEngine() {
            if (this.mSignerEngine != null) {
                throw new IllegalStateException("Operation is not available when builder initialized with an engine");
            }
        }

        public Builder setSigningCertificateLineage(SigningCertificateLineage signingCertificateLineage) {
            if (signingCertificateLineage != null) {
                this.mV3SigningEnabled = true;
                this.mSigningCertificateLineage = signingCertificateLineage;
            }
            return this;
        }

        public ApkSigner build() {
            boolean z = this.mV3SigningExplicitlyDisabled;
            if (z && this.mV3SigningExplicitlyEnabled) {
                throw new IllegalStateException("Builder configured to both enable and disable APK Signature Scheme v3 signing");
            }
            if (z) {
                this.mV3SigningEnabled = false;
            }
            if (this.mV3SigningExplicitlyEnabled) {
                this.mV3SigningEnabled = true;
            }
            if (this.mV4SigningEnabled && !this.mV2SigningEnabled && !this.mV3SigningEnabled) {
                if (!this.mV4ErrorReportingEnabled) {
                    this.mV4SigningEnabled = false;
                } else {
                    throw new IllegalStateException("APK Signature Scheme v4 signing requires at least v2 or v3 signing to be enabled");
                }
            }
            return new ApkSigner(this.mSignerConfigs, this.mSourceStampSignerConfig, this.mSourceStampSigningCertificateLineage, this.mForceSourceStampOverwrite, this.mSourceStampTimestampEnabled, this.mMinSdkVersion, this.mRotationMinSdkVersion, this.mRotationTargetsDevRelease, this.mV1SigningEnabled, this.mV2SigningEnabled, this.mV3SigningEnabled, this.mV4SigningEnabled, this.mAlignFileSize, this.mVerityEnabled, this.mV4ErrorReportingEnabled, this.mDebuggableApkPermitted, this.mOtherSignersSignaturesPreserved, this.mCreatedBy, this.mSignerEngine, this.mInputApkFile, this.mInputApkDataSource, this.mOutputApkFile, this.mOutputApkDataSink, this.mOutputApkDataSource, this.mOutputV4File, this.mSigningCertificateLineage, null);
        }
    }
}
