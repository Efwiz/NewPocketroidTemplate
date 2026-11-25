package com.android.apksig;

import com.android.apksig.apk.ApkFormatException;
import com.android.apksig.apk.ApkUtils;
import com.android.apksig.internal.apk.ApkSigningBlockUtils;
import com.android.apksig.internal.apk.SignatureAlgorithm;
import com.android.apksig.internal.apk.SignatureInfo;
import com.android.apksig.internal.apk.v3.V3SchemeSigner;
import com.android.apksig.internal.apk.v3.V3SigningCertificateLineage;
import com.android.apksig.internal.util.ByteBufferUtils;
import com.android.apksig.internal.util.Pair;
import com.android.apksig.internal.util.RandomAccessFileDataSink;
import com.android.apksig.util.DataSink;
import com.android.apksig.util.DataSource;
import com.android.apksig.util.DataSources;
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
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SigningCertificateLineage {
    private static final int CURRENT_VERSION = 1;
    private static final int FIRST_VERSION = 1;
    public static final int MAGIC = 1056913873;
    private static final int PAST_CERT_AUTH = 16;
    private static final int PAST_CERT_INSTALLED_DATA = 1;
    private static final int PAST_CERT_PERMISSION = 4;
    private static final int PAST_CERT_ROLLBACK = 8;
    private static final int PAST_CERT_SHARED_USER_ID = 2;
    private final int mMinSdkVersion;
    private final List<V3SigningCertificateLineage.SigningCertificateNode> mSigningLineage;

    /* JADX INFO: Access modifiers changed from: private */
    public static int calculateDefaultFlags() {
        return 23;
    }

    private SigningCertificateLineage(int minSdkVersion, List<V3SigningCertificateLineage.SigningCertificateNode> list) {
        this.mMinSdkVersion = minSdkVersion;
        this.mSigningLineage = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SigningCertificateLineage createSigningLineage(int minSdkVersion, SignerConfig parent, SignerCapabilities parentCapabilities, SignerConfig child, SignerCapabilities childCapabilities) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateEncodingException {
        return new SigningCertificateLineage(minSdkVersion, new ArrayList()).spawnFirstDescendant(parent, parentCapabilities).spawnDescendant(parent, child, childCapabilities);
    }

    public static SigningCertificateLineage readFromBytes(byte[] lineageBytes) throws IOException {
        return readFromDataSource(DataSources.asDataSource(ByteBuffer.wrap(lineageBytes)));
    }

    public static SigningCertificateLineage readFromFile(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("file == null");
        }
        return readFromDataSource(DataSources.asDataSource(new RandomAccessFile(file, "r")));
    }

    public static SigningCertificateLineage readFromDataSource(DataSource dataSource) throws IOException {
        if (dataSource == null) {
            throw new NullPointerException("dataSource == null");
        }
        ByteBuffer byteBuffer = dataSource.getByteBuffer(0L, (int) dataSource.size());
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        return read(byteBuffer);
    }

    public static SigningCertificateLineage readFromV3AttributeValue(byte[] attrValue) throws IOException {
        List<V3SigningCertificateLineage.SigningCertificateNode> signingCertificateLineage = V3SigningCertificateLineage.readSigningCertificateLineage(ByteBuffer.wrap(attrValue).order(ByteOrder.LITTLE_ENDIAN));
        return new SigningCertificateLineage(calculateMinSdkVersion(signingCertificateLineage), signingCertificateLineage);
    }

    public static SigningCertificateLineage readFromApkFile(File apkFile) throws IOException, ApkFormatException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(apkFile, "r");
        try {
            SigningCertificateLineage fromApkDataSource = readFromApkDataSource(DataSources.asDataSource(randomAccessFile, 0L, randomAccessFile.length()));
            randomAccessFile.close();
            return fromApkDataSource;
        } catch (Throwable th) {
            try {
                randomAccessFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static SigningCertificateLineage readFromApkDataSource(DataSource apk) throws IOException, ApkFormatException {
        try {
            ApkUtils.ZipSections zipSectionsFindZipSections = ApkUtils.findZipSections(apk);
            ArrayList arrayList = new ArrayList();
            try {
                arrayList.add(ApkSigningBlockUtils.findSignature(apk, zipSectionsFindZipSections, 462663009, new ApkSigningBlockUtils.Result(31)));
            } catch (ApkSigningBlockUtils.SignatureNotFoundException unused) {
            }
            try {
                arrayList.add(ApkSigningBlockUtils.findSignature(apk, zipSectionsFindZipSections, -262969152, new ApkSigningBlockUtils.Result(3)));
            } catch (ApkSigningBlockUtils.SignatureNotFoundException unused2) {
            }
            if (arrayList.isEmpty()) {
                throw new IllegalArgumentException("The provided APK does not contain a valid V3 signature block.");
            }
            ArrayList arrayList2 = new ArrayList(1);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(((SignatureInfo) it.next()).signatureBlock);
                while (lengthPrefixedSlice.hasRemaining()) {
                    try {
                        arrayList2.add(readFromSignedData(ApkSigningBlockUtils.getLengthPrefixedSlice(ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice))));
                    } catch (IllegalArgumentException unused3) {
                    }
                }
            }
            if (arrayList2.isEmpty()) {
                throw new IllegalArgumentException("The provided APK does not contain a valid lineage.");
            }
            if (arrayList2.size() > 1) {
                return consolidateLineages(arrayList2);
            }
            return (SigningCertificateLineage) arrayList2.get(0);
        } catch (ZipFormatException e) {
            throw new ApkFormatException(e.getMessage());
        }
    }

    public static SigningCertificateLineage readFromSignedData(ByteBuffer signedData) throws IOException, ApkFormatException {
        ApkSigningBlockUtils.getLengthPrefixedSlice(signedData);
        ApkSigningBlockUtils.getLengthPrefixedSlice(signedData);
        signedData.getInt();
        signedData.getInt();
        ByteBuffer lengthPrefixedSlice = ApkSigningBlockUtils.getLengthPrefixedSlice(signedData);
        ArrayList arrayList = new ArrayList(1);
        while (lengthPrefixedSlice.hasRemaining()) {
            ByteBuffer lengthPrefixedSlice2 = ApkSigningBlockUtils.getLengthPrefixedSlice(lengthPrefixedSlice);
            if (lengthPrefixedSlice2.getInt() == 1000370060) {
                arrayList.add(readFromV3AttributeValue(ByteBufferUtils.toByteArray(lengthPrefixedSlice2)));
            }
        }
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("The signed data does not contain a valid lineage.");
        }
        if (arrayList.size() > 1) {
            return consolidateLineages(arrayList);
        }
        return (SigningCertificateLineage) arrayList.get(0);
    }

    public byte[] getBytes() {
        return write().array();
    }

    public void writeToFile(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("file == null");
        }
        writeToDataSink(new RandomAccessFileDataSink(new RandomAccessFile(file, "rw")));
    }

    public void writeToDataSink(DataSink dataSink) throws IOException {
        if (dataSink == null) {
            throw new NullPointerException("dataSink == null");
        }
        dataSink.consume(write());
    }

    public SigningCertificateLineage spawnDescendant(SignerConfig parent, SignerConfig child) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateEncodingException {
        if (parent == null || child == null) {
            throw new NullPointerException("can't add new descendant to lineage with null inputs");
        }
        return spawnDescendant(parent, child, new SignerCapabilities.Builder().build());
    }

    public SigningCertificateLineage spawnDescendant(SignerConfig parent, SignerConfig child, SignerCapabilities childCapabilities) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateEncodingException {
        if (parent == null) {
            throw new NullPointerException("parent == null");
        }
        if (child == null) {
            throw new NullPointerException("child == null");
        }
        if (childCapabilities == null) {
            throw new NullPointerException("childCapabilities == null");
        }
        if (this.mSigningLineage.isEmpty()) {
            throw new IllegalArgumentException("Cannot spawn descendant signing certificate on an empty SigningCertificateLineage: no parent node");
        }
        List<V3SigningCertificateLineage.SigningCertificateNode> list = this.mSigningLineage;
        V3SigningCertificateLineage.SigningCertificateNode signingCertificateNode = list.get(list.size() - 1);
        if (!Arrays.equals(signingCertificateNode.signingCert.getEncoded(), parent.getCertificate().getEncoded())) {
            throw new IllegalArgumentException("SignerConfig Certificate containing private key to sign the new SigningCertificateLineage record does not match the existing most recent record");
        }
        SignatureAlgorithm signatureAlgorithm = getSignatureAlgorithm(parent);
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(V3SigningCertificateLineage.encodeSignedData(child.getCertificate(), signatureAlgorithm.getId()));
        byteBufferWrap.position(4);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteBufferWrap.remaining());
        byteBufferAllocate.put(byteBufferWrap);
        byte[] bArrArray = byteBufferAllocate.array();
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(parent.getCertificate());
        ApkSigningBlockUtils.SignerConfig signerConfig = new ApkSigningBlockUtils.SignerConfig();
        signerConfig.privateKey = parent.getPrivateKey();
        signerConfig.certificates = arrayList;
        signerConfig.signatureAlgorithms = Collections.singletonList(signatureAlgorithm);
        List<Pair<Integer, byte[]>> listGenerateSignaturesOverData = ApkSigningBlockUtils.generateSignaturesOverData(signerConfig, bArrArray);
        SignatureAlgorithm signatureAlgorithmFindById = SignatureAlgorithm.findById(listGenerateSignaturesOverData.get(0).getFirst().intValue());
        byte[] second = listGenerateSignaturesOverData.get(0).getSecond();
        signingCertificateNode.sigAlgorithm = signatureAlgorithmFindById;
        V3SigningCertificateLineage.SigningCertificateNode signingCertificateNode2 = new V3SigningCertificateLineage.SigningCertificateNode(child.getCertificate(), signatureAlgorithmFindById, null, second, childCapabilities.getFlags());
        ArrayList arrayList2 = new ArrayList(this.mSigningLineage);
        arrayList2.add(signingCertificateNode2);
        return new SigningCertificateLineage(this.mMinSdkVersion, arrayList2);
    }

    public int size() {
        return this.mSigningLineage.size();
    }

    private SignatureAlgorithm getSignatureAlgorithm(SignerConfig parent) throws InvalidKeyException {
        return V3SchemeSigner.getSuggestedSignatureAlgorithms(parent.getCertificate().getPublicKey(), this.mMinSdkVersion, false, false).get(0);
    }

    private SigningCertificateLineage spawnFirstDescendant(SignerConfig parent, SignerCapabilities signerCapabilities) {
        if (!this.mSigningLineage.isEmpty()) {
            throw new IllegalStateException("SigningCertificateLineage already has its first node");
        }
        try {
            getSignatureAlgorithm(parent);
            return new SigningCertificateLineage(this.mMinSdkVersion, Collections.singletonList(new V3SigningCertificateLineage.SigningCertificateNode(parent.getCertificate(), null, null, new byte[0], signerCapabilities.getFlags())));
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("Algorithm associated with first signing certificate invalid on desired platform versions", e);
        }
    }

    private static SigningCertificateLineage read(ByteBuffer inputByteBuffer) throws IOException {
        ApkSigningBlockUtils.checkByteOrderLittleEndian(inputByteBuffer);
        if (inputByteBuffer.remaining() < 8) {
            throw new IllegalArgumentException("Improper SigningCertificateLineage format: insufficient data for header.");
        }
        if (inputByteBuffer.getInt() != 1056913873) {
            throw new IllegalArgumentException("Improper SigningCertificateLineage format: MAGIC header mismatch.");
        }
        return read(inputByteBuffer, inputByteBuffer.getInt());
    }

    private static SigningCertificateLineage read(ByteBuffer inputByteBuffer, int version) throws IOException {
        if (version == 1) {
            try {
                List<V3SigningCertificateLineage.SigningCertificateNode> signingCertificateLineage = V3SigningCertificateLineage.readSigningCertificateLineage(ApkSigningBlockUtils.getLengthPrefixedSlice(inputByteBuffer));
                return new SigningCertificateLineage(calculateMinSdkVersion(signingCertificateLineage), signingCertificateLineage);
            } catch (ApkFormatException e) {
                throw new IOException("Unable to read list of signing certificate nodes in SigningCertificateLineage", e);
            }
        }
        throw new IllegalArgumentException("Improper SigningCertificateLineage format: unrecognized version.");
    }

    private static int calculateMinSdkVersion(List<V3SigningCertificateLineage.SigningCertificateNode> nodes) {
        int minSdkVersion;
        if (nodes == null) {
            throw new IllegalArgumentException("Can't calculate minimum SDK version of null nodes");
        }
        int i = 28;
        for (V3SigningCertificateLineage.SigningCertificateNode signingCertificateNode : nodes) {
            if (signingCertificateNode.sigAlgorithm != null && (minSdkVersion = signingCertificateNode.sigAlgorithm.getMinSdkVersion()) > i) {
                i = minSdkVersion;
            }
        }
        return i;
    }

    private ByteBuffer write() {
        byte[] bArrEncodeSigningCertificateLineage = V3SigningCertificateLineage.encodeSigningCertificateLineage(this.mSigningLineage);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrEncodeSigningCertificateLineage.length + 12);
        byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
        byteBufferAllocate.putInt(MAGIC);
        byteBufferAllocate.putInt(1);
        byteBufferAllocate.putInt(bArrEncodeSigningCertificateLineage.length);
        byteBufferAllocate.put(bArrEncodeSigningCertificateLineage);
        byteBufferAllocate.flip();
        return byteBufferAllocate;
    }

    public byte[] encodeSigningCertificateLineage() {
        return V3SigningCertificateLineage.encodeSigningCertificateLineage(this.mSigningLineage);
    }

    public List<DefaultApkSignerEngine.SignerConfig> sortSignerConfigs(List<DefaultApkSignerEngine.SignerConfig> signerConfigs) {
        if (signerConfigs == null) {
            throw new NullPointerException("signerConfigs == null");
        }
        ArrayList arrayList = new ArrayList(signerConfigs.size());
        for (int i = 0; i < this.mSigningLineage.size(); i++) {
            for (int i2 = 0; i2 < signerConfigs.size(); i2++) {
                DefaultApkSignerEngine.SignerConfig signerConfig = signerConfigs.get(i2);
                if (this.mSigningLineage.get(i).signingCert.equals(signerConfig.getCertificates().get(0))) {
                    arrayList.add(signerConfig);
                    break;
                }
            }
        }
        if (arrayList.size() == signerConfigs.size()) {
            return arrayList;
        }
        throw new IllegalArgumentException("SignerConfigs supplied which are not present in the SigningCertificateLineage");
    }

    public SignerCapabilities getSignerCapabilities(SignerConfig config) {
        if (config == null) {
            throw new NullPointerException("config == null");
        }
        return getSignerCapabilities(config.getCertificate());
    }

    public SignerCapabilities getSignerCapabilities(X509Certificate cert) {
        if (cert == null) {
            throw new NullPointerException("cert == null");
        }
        for (int i = 0; i < this.mSigningLineage.size(); i++) {
            V3SigningCertificateLineage.SigningCertificateNode signingCertificateNode = this.mSigningLineage.get(i);
            if (signingCertificateNode.signingCert.equals(cert)) {
                return new SignerCapabilities.Builder(signingCertificateNode.flags).build();
            }
        }
        throw new IllegalArgumentException("Certificate (" + cert.getSubjectDN() + ") not found in the SigningCertificateLineage");
    }

    public void updateSignerCapabilities(SignerConfig config, SignerCapabilities capabilities) {
        if (config == null) {
            throw new NullPointerException("config == null");
        }
        X509Certificate certificate = config.getCertificate();
        for (int i = 0; i < this.mSigningLineage.size(); i++) {
            V3SigningCertificateLineage.SigningCertificateNode signingCertificateNode = this.mSigningLineage.get(i);
            if (signingCertificateNode.signingCert.equals(certificate)) {
                signingCertificateNode.flags = new SignerCapabilities.Builder(signingCertificateNode.flags).setCallerConfiguredCapabilities(capabilities).build().getFlags();
                return;
            }
        }
        throw new IllegalArgumentException("Certificate (" + certificate.getSubjectDN() + ") not found in the SigningCertificateLineage");
    }

    public List<X509Certificate> getCertificatesInLineage() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mSigningLineage.size(); i++) {
            arrayList.add(this.mSigningLineage.get(i).signingCert);
        }
        return arrayList;
    }

    public boolean isSignerInLineage(SignerConfig config) {
        if (config == null) {
            throw new NullPointerException("config == null");
        }
        return isCertificateInLineage(config.getCertificate());
    }

    public boolean isCertificateInLineage(X509Certificate cert) {
        if (cert == null) {
            throw new NullPointerException("cert == null");
        }
        for (int i = 0; i < this.mSigningLineage.size(); i++) {
            if (this.mSigningLineage.get(i).signingCert.equals(cert)) {
                return true;
            }
        }
        return false;
    }

    public SigningCertificateLineage getSubLineage(X509Certificate x509Certificate) {
        if (x509Certificate == null) {
            throw new NullPointerException("x509Certificate == null");
        }
        for (int i = 0; i < this.mSigningLineage.size(); i++) {
            if (this.mSigningLineage.get(i).signingCert.equals(x509Certificate)) {
                return new SigningCertificateLineage(this.mMinSdkVersion, new ArrayList(this.mSigningLineage.subList(0, i + 1)));
            }
        }
        throw new IllegalArgumentException("Certificate not found in SigningCertificateLineage");
    }

    public static SigningCertificateLineage consolidateLineages(List<SigningCertificateLineage> lineages) {
        if (lineages == null || lineages.isEmpty()) {
            return null;
        }
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < lineages.size(); i3++) {
            int size = lineages.get(i3).size();
            if (size > i2) {
                i = i3;
                i2 = size;
            }
        }
        List<V3SigningCertificateLineage.SigningCertificateNode> list = lineages.get(i).mSigningLineage;
        for (int i4 = 0; i4 < lineages.size(); i4++) {
            if (i4 != i) {
                List<V3SigningCertificateLineage.SigningCertificateNode> list2 = lineages.get(i4).mSigningLineage;
                if (!list2.equals(list.subList(0, list2.size()))) {
                    throw new IllegalArgumentException("Inconsistent SigningCertificateLineages. Not all lineages are subsets of each other.");
                }
            }
        }
        return lineages.get(i);
    }

    public static class SignerCapabilities {
        private final int mCallerConfiguredFlags;
        private final int mFlags;

        private SignerCapabilities(int flags) {
            this(flags, 0);
        }

        private SignerCapabilities(int flags, int callerConfiguredFlags) {
            this.mFlags = flags;
            this.mCallerConfiguredFlags = callerConfiguredFlags;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getFlags() {
            return this.mFlags;
        }

        public boolean equals(SignerCapabilities other) {
            return this.mFlags == other.mFlags;
        }

        public boolean hasInstalledData() {
            return (this.mFlags & 1) != 0;
        }

        public boolean hasSharedUid() {
            return (this.mFlags & 2) != 0;
        }

        public boolean hasPermission() {
            return (this.mFlags & 4) != 0;
        }

        public boolean hasRollback() {
            return (this.mFlags & 8) != 0;
        }

        public boolean hasAuth() {
            return (this.mFlags & 16) != 0;
        }

        public static class Builder {
            private int mCallerConfiguredFlags;
            private int mFlags;

            public Builder() {
                this.mFlags = SigningCertificateLineage.calculateDefaultFlags();
            }

            public Builder(int flags) {
                this.mFlags = flags;
            }

            public Builder setInstalledData(boolean enabled) {
                this.mCallerConfiguredFlags |= 1;
                if (enabled) {
                    this.mFlags |= 1;
                } else {
                    this.mFlags &= -2;
                }
                return this;
            }

            public Builder setSharedUid(boolean enabled) {
                this.mCallerConfiguredFlags |= 2;
                if (enabled) {
                    this.mFlags |= 2;
                } else {
                    this.mFlags &= -3;
                }
                return this;
            }

            public Builder setPermission(boolean enabled) {
                this.mCallerConfiguredFlags |= 4;
                if (enabled) {
                    this.mFlags |= 4;
                } else {
                    this.mFlags &= -5;
                }
                return this;
            }

            public Builder setRollback(boolean enabled) {
                this.mCallerConfiguredFlags |= 8;
                if (enabled) {
                    this.mFlags |= 8;
                } else {
                    this.mFlags &= -9;
                }
                return this;
            }

            public Builder setAuth(boolean enabled) {
                this.mCallerConfiguredFlags |= 16;
                if (enabled) {
                    this.mFlags |= 16;
                } else {
                    this.mFlags &= -17;
                }
                return this;
            }

            public Builder setCallerConfiguredCapabilities(SignerCapabilities capabilities) {
                this.mFlags = (capabilities.mCallerConfiguredFlags & capabilities.mFlags) | (this.mFlags & (~capabilities.mCallerConfiguredFlags));
                return this;
            }

            public SignerCapabilities build() {
                return new SignerCapabilities(this.mFlags, this.mCallerConfiguredFlags);
            }
        }
    }

    public static class SignerConfig {
        private final X509Certificate mCertificate;
        private final PrivateKey mPrivateKey;

        private SignerConfig(PrivateKey privateKey, X509Certificate certificate) {
            this.mPrivateKey = privateKey;
            this.mCertificate = certificate;
        }

        public PrivateKey getPrivateKey() {
            return this.mPrivateKey;
        }

        public X509Certificate getCertificate() {
            return this.mCertificate;
        }

        public static class Builder {
            private final X509Certificate mCertificate;
            private final PrivateKey mPrivateKey;

            public Builder(PrivateKey privateKey, X509Certificate certificate) {
                this.mPrivateKey = privateKey;
                this.mCertificate = certificate;
            }

            public SignerConfig build() {
                return new SignerConfig(this.mPrivateKey, this.mCertificate);
            }
        }
    }

    public static class Builder {
        private int mMinSdkVersion;
        private SignerCapabilities mNewCapabilities;
        private final SignerConfig mNewSignerConfig;
        private SignerCapabilities mOriginalCapabilities;
        private final SignerConfig mOriginalSignerConfig;

        public Builder(SignerConfig originalSignerConfig, SignerConfig newSignerConfig) {
            if (originalSignerConfig == null || newSignerConfig == null) {
                throw new NullPointerException("Can't pass null SignerConfigs when constructing a new SigningCertificateLineage");
            }
            this.mOriginalSignerConfig = originalSignerConfig;
            this.mNewSignerConfig = newSignerConfig;
        }

        public Builder setMinSdkVersion(int minSdkVersion) {
            this.mMinSdkVersion = minSdkVersion;
            return this;
        }

        public Builder setOriginalCapabilities(SignerCapabilities signerCapabilities) {
            if (signerCapabilities == null) {
                throw new NullPointerException("signerCapabilities == null");
            }
            this.mOriginalCapabilities = signerCapabilities;
            return this;
        }

        public Builder setNewCapabilities(SignerCapabilities signerCapabilities) {
            if (signerCapabilities == null) {
                throw new NullPointerException("signerCapabilities == null");
            }
            this.mNewCapabilities = signerCapabilities;
            return this;
        }

        public SigningCertificateLineage build() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, CertificateEncodingException {
            if (this.mMinSdkVersion < 28) {
                this.mMinSdkVersion = 28;
            }
            if (this.mOriginalCapabilities == null) {
                this.mOriginalCapabilities = new SignerCapabilities.Builder().build();
            }
            if (this.mNewCapabilities == null) {
                this.mNewCapabilities = new SignerCapabilities.Builder().build();
            }
            return SigningCertificateLineage.createSigningLineage(this.mMinSdkVersion, this.mOriginalSignerConfig, this.mOriginalCapabilities, this.mNewSignerConfig, this.mNewCapabilities);
        }
    }
}
